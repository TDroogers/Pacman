/**
 * 
 * A work in progress path finding algorithm, it works, but needs still needs a lot of optimization.
 *
 */
package nl.drogecode.pacman.logic.pathfinder;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.shape.Circle;
import nl.drogecode.pacman.enums.Direction;
import nl.drogecode.pacman.enums.GhostType;
import nl.drogecode.pacman.logic.GameLogic;
import nl.drogecode.pacman.logic.Sleeper;
import nl.drogecode.pacman.objects.ghosts.SmartGhost;

public class ManFinder extends Thread
{
  private Circle manClone;
  private GameLogic logic;
  private SmartGhost moving;
  private GhostType type;
  private WalkUntilObstacle walkinUntilObstacle;
  private Sleeper sleep;
  private int distanceCounter, currentCounter;
  private boolean foundMan, updated;
  private ArrayList<Direction> walker, realWalker;
  private volatile ArrayList<Double> manPrevLast;
  private ArrayList<HashMap<Double, Double>> listOfPoints;

  public ManFinder(SmartGhost moving, GameLogic logic, GhostType type)
  {
    this.moving = moving;
    this.logic = logic;
    this.type = type;
    manClone = new Circle();
    realWalker = new ArrayList<>();
    sleep = new Sleeper();
  }

  @Override public void run()
  {
    manClone.setRadius(logic.man.getObject().getRadius());
    logic.setWakeUp(this);
    MainLoop();
  }

  public synchronized ArrayList<Direction> getWalker()
  {
    if (updated)
    {
      updated = false;
      return realWalker;
    }
    else
    {
      return new ArrayList<Direction>();
    }
  }

  private synchronized void setWalker(ArrayList<Direction> realWalker)
  {
    this.realWalker = new ArrayList<>(realWalker);
  }

  private void MainLoop()
  {
    while (moving.getWalking())
    {
      if (!howFindMan())
      {
        Thread.yield();
        sleep.sleeper(Long.MAX_VALUE);
        continue;
      }

      SingleDecisionPoint route = initialSettings();
      startFindLoop(route);
    }
  }

  private boolean howFindMan()
  {
    ArrayList<Double> lastMan;
    switch (type)
    {
      case BEHIND:
        lastMan = new ArrayList<>(logic.man.getLastBumb());
        break;

      case FRONT:
        lastMan = new ArrayList<>(logic.man.getNextBumb());
        break;

      default:
        System.err.println("wrong GhostType in ManFinder");
        System.exit(0);
        lastMan = null;
        break;
    }

    if (lastMan.equals(manPrevLast) | lastMan.isEmpty() | !tryToSetNewManClone(lastMan))
    {
      return false;
    }
    else
    {
      manPrevLast = lastMan;
      return true;
    }
  }

  private SingleDecisionPoint initialSettings()
  {
    Direction currentDir = moving.getDir();
    SingleDecisionPoint route = new SingleDecisionPoint(true, currentDir);
    listOfPoints = new ArrayList<>();
    route.setPoint(moving.getMovingX(), moving.getMovingY());
    // setHashInList(moving.getMovingX(), moving.getMovingY());

    distanceCounter = 0;
    currentCounter = 0;
    foundMan = false;
    walker = new ArrayList<>();
    walkinUntilObstacle = new WalkUntilObstacle(manClone, moving.GSPEED, moving, logic);

    return route;
  }

  private boolean tryToSetNewManClone(ArrayList<Double> lastMan)
  {
    try
    {
      manClone.setCenterX(lastMan.get(0));
      manClone.setCenterY(lastMan.get(1));
      return true;
    }
    catch (IndexOutOfBoundsException e)
    {
      System.out.println(e);
      return false;
    }
  }

  private void startFindLoop(SingleDecisionPoint route)
  {
    while (distanceCounter < 1000)
    {
      distanceCounter++;
      runSelfDemandingLoop(route);
      if (foundMan)
      {
        break;
      }
    }
  }

  private boolean runSelfDemandingLoop(SingleDecisionPoint route)
  {
    currentCounter++;
    boolean ret = false;
    if (distanceCounter >= currentCounter)
    {
      boolean up = walkingPaths(Direction.UP, route);
      boolean down = walkingPaths(Direction.DOWN, route);
      boolean left = walkingPaths(Direction.LEFT, route);
      boolean right = walkingPaths(Direction.RIGHT, route);

      if (up && down && left && right && distanceCounter >= currentCounter + 3)
      {
        // ret = true;
      }
    }
    currentCounter--;
    return ret;
  }

  private boolean walkingPaths(Direction path, SingleDecisionPoint route)
  {
    if (route.getFullStop() == true)
    {
      return true;
    }
    walker.add(path);
    SingleDecisionPoint thisRoute = null;

    if (route.getPath(path) == null)
    {
      thisRoute = newPath(route, path, thisRoute);
    }
    else
    {
      thisRoute = oldPath(route, path, thisRoute);
    }

    walker.remove(currentCounter - 1);

    return thisRoute.getEnd();
  }

  private SingleDecisionPoint newPath(SingleDecisionPoint route, Direction path, SingleDecisionPoint thisRoute)
  {
    walkinUntilObstacle.resetTester(route);
    route.setPath(path);

    thisRoute = route.getPath(path);
    thisRoute.setEnd(walkinUntilObstacle.walking(path));

    if (currentCounter >= 2 && walker.get(currentCounter - 2).equals(moving.getMirror(path)))
    {
      thisRoute.setEnd(true);
    }
    if (!thisRoute.getEnd())
    {
      newPathNotEnding(path, thisRoute);
    }
    return thisRoute;
  }

  private void newPathNotEnding(Direction path, SingleDecisionPoint thisRoute)
  {
    if (walkinUntilObstacle.walkTestDirection(path) == -1)
    {
      distanceCounter = 0;
      foundMan = true;
      updated = true;
      setWalker(walker);
    }
    double oldTestX = walkinUntilObstacle.getOldTestX();
    double oldTestY = walkinUntilObstacle.getOldTestY();
    if (checkDoubleDouble(oldTestX, oldTestY))
    {
      noNewDouble(oldTestX, oldTestY, thisRoute);
    }
  }

  private void noNewDouble(double oldTestX, double oldTestY, SingleDecisionPoint thisRoute)
  {
    setHashInList(oldTestX, oldTestY);
    thisRoute.setIntersectionId(walkinUntilObstacle.getIntersectionId());
    thisRoute.setPoint(oldTestX, oldTestY);
    boolean doesChildWork = runSelfDemandingLoop(thisRoute);
    if (doesChildWork)
    {
      thisRoute.unsetAllNextPath();
    }
  }

  private SingleDecisionPoint oldPath(SingleDecisionPoint route, Direction path, SingleDecisionPoint thisRoute)
  {
    thisRoute = route.getPath(path);
    if (!route.getEnd())
    {
      walkinUntilObstacle.resetTester(thisRoute);
      boolean doesChildWork = runSelfDemandingLoop(thisRoute);
      if (doesChildWork)
      {
        thisRoute.unsetAllNextPath();
      }
    }
    return thisRoute;
  }

  private void setHashInList(double xHash, double yHash)
  {
    HashMap<Double, Double> newList = new HashMap<>();
    newList.put(xHash, yHash);
    listOfPoints.add(newList);
  }

  private boolean checkDoubleDouble(double oldTestX, double oldTestY)
  {
    for (HashMap<Double, Double> hash : listOfPoints)
    {
      if (hash.get(oldTestX) != null && hash.get(oldTestX) == (oldTestY))
      {
        return false;
      }
    }
    return true;
  }
}
