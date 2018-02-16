/**
 * 
 * A working path finder algorithm, for the ghosts to find the fastest route to the man.
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
  private static final int EXTRA_ROUNTS = 5;
  private Circle manClone;
  private GameLogic logic;
  private SmartGhost moving;
  private GhostType type;
  private WalkUntilObstacle walkinUntilObstacle;
  private Sleeper sleep;
  private int distanceCounter, currentCounter, countDownAfterFoundMan, stapsTillMan;
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
    setHashInList(moving.getMovingX(), moving.getMovingY());

    distanceCounter = 0;
    currentCounter = 0;
    countDownAfterFoundMan = EXTRA_ROUNTS;
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
      System.err.println(e);
      return false;
    }
  }

  private void startFindLoop(SingleDecisionPoint route)
  {
    while (distanceCounter < 1000)
    {
      distanceCounter++;
      runSelfDemandingLoop(route);
      if (foundMan && countDownAfterFoundMan-- <= 0)
      {
        updated = true;
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
      thisRoute = newPathNotEnding(route, path, thisRoute);
    }
    return thisRoute;
  }

  private SingleDecisionPoint newPathNotEnding(SingleDecisionPoint route, Direction path, SingleDecisionPoint thisRoute)
  {
    int PreviusRouteStepCount = route.getStapCount();
    if (walkinUntilObstacle.walkTestDirection(path) == -1)
    {
      if (foundMan)
      {
        thisRoute = alreadyAManFound(thisRoute, PreviusRouteStepCount);
      }
      else
      {
        thisRoute = firstManFound(thisRoute, PreviusRouteStepCount);
      }
    }
    else
    {
      thisRoute.setStapCount(PreviusRouteStepCount + walkinUntilObstacle.getStapCount());
    }
    double oldTestX = walkinUntilObstacle.getOldTestX();
    double oldTestY = walkinUntilObstacle.getOldTestY();
    if (checkDoubleDouble(oldTestX, oldTestY))
    {
      thisRoute = noNewDouble(oldTestX, oldTestY, thisRoute);
    }
    return thisRoute;
  }

  private SingleDecisionPoint alreadyAManFound(SingleDecisionPoint thisRoute, int PreviusRouteStepCount)
  {
    thisRoute.setStapCount(PreviusRouteStepCount + walkinUntilObstacle.getStapCount());
    thisRoute.setIsMan(true);
    if (stapsTillMan > thisRoute.getStapCount())
    {
      thisRoute = manFound(thisRoute);
      System.out.println("");
    }
    return thisRoute;
  }

  private SingleDecisionPoint firstManFound(SingleDecisionPoint thisRoute, int PreviusRouteStepCount)
  {
    foundMan = true;
    thisRoute.setStapCount(PreviusRouteStepCount + walkinUntilObstacle.getStapCount());
    thisRoute.setIsMan(true);
    thisRoute = manFound(thisRoute);
    return thisRoute;
  }

  private SingleDecisionPoint manFound(SingleDecisionPoint thisRoute)
  {
    setWalker(walker);
    stapsTillMan = thisRoute.getStapCount();
    thisRoute.setEnd(true);
    return thisRoute;
  }

  private SingleDecisionPoint noNewDouble(double oldTestX, double oldTestY, SingleDecisionPoint thisRoute)
  {
    setHashInList(oldTestX, oldTestY);
    thisRoute.setIntersectionId(walkinUntilObstacle.getIntersectionId());
    thisRoute.setPoint(oldTestX, oldTestY);
    boolean doesChildWork = runSelfDemandingLoop(thisRoute);
    if (doesChildWork)
    {
      thisRoute.unsetAllNextPath();
    }

    return thisRoute;
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
