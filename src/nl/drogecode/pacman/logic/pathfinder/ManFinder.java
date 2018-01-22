/**
 * 
 * A work in progress path finding algorithm, it works, but needs still needs a lot of optimization.
 *
 */
package nl.drogecode.pacman.logic.pathfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.shape.Circle;
import nl.drogecode.pacman.enums.Direction;
import nl.drogecode.pacman.enums.GhostType;
import nl.drogecode.pacman.logic.GameLogic;
import nl.drogecode.pacman.logic.Sleeper;
import nl.drogecode.pacman.objects.BaseObject;
import nl.drogecode.pacman.objects.Intersection;
import nl.drogecode.pacman.objects.ghosts.SmartGhost;

public class ManFinder extends Thread
{
  private Circle tester, inters, clone, manClone;
  private GameLogic logic;
  private SmartGhost sbg;
  private GhostType type;
  private Sleeper sleep;
  private int distanceCounter, currentCounter;
  private double testX, testY, oldTestX, oldTestY;
  private boolean foundMan, updated;
  private ArrayList<Direction> walker, realWalker;
  private ArrayList<Double> manPrevLast;
  private ArrayList<HashMap<Double, Double>> listOfPoints;
  private int intersectionId;

  public ManFinder(SmartGhost sbg, GameLogic logic, GhostType type)
  {
    this.sbg = sbg;
    this.logic = logic;
    this.type = type;
    tester = new Circle();
    clone = new Circle();
    manClone = new Circle();
    tester.setRadius(sbg.getObject().getRadius());
    clone.setRadius(1);
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
    while (sbg.getWalking())
    {
      if (!howFindMan())
      {
        continue;
      }
      Direction currentDir = sbg.getDir();
      SingleDecisionPoint route = new SingleDecisionPoint(true, currentDir);
      listOfPoints = new ArrayList<>();
      route.setPoint(sbg.getSbgX(), sbg.getSbgY());
      setHashInList(sbg.getSbgX(), sbg.getSbgY());

      intersectionId = -1;
      distanceCounter = 0;
      currentCounter = 0;
      foundMan = false;
      walker = new ArrayList<>();

      if (currentDir != null)
      {
        System.out.println("hallo");
      }

      startFindLoop(route);
    }
  }

  private boolean howFindMan()
  {
    ArrayList<Double> lastMan;
    switch (type)
    {
      case BEHIND:
        lastMan = logic.man.getLastBumb();
        break;

      case FRONT:
        lastMan = null;
        break;

      default:
        lastMan = null;
        break;
    }

    if (lastMan.equals(manPrevLast))
    {
      Thread.yield();
      sleep.sleeper(Long.MAX_VALUE);
      return false;
    }
    manPrevLast = lastMan;
    manClone.setCenterX(lastMan.get(0));
    manClone.setCenterY(lastMan.get(1));
    return true;
  }

  private void startFindLoop(SingleDecisionPoint route)
  {
    while (distanceCounter <= 100)
    {
      distanceCounter++;
      runSelfDemandingLoop(route);

      if (foundMan)
      {
        System.out.println("Here is man: " + realWalker);
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
        ret = true;
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
    SingleDecisionPoint thisRoute;

    if (route.getPath(path) == null)
    {
      resetTester(route);
      route.setPath(path);

      thisRoute = route.getPath(path);
      thisRoute.setEnd(walking(path));

      if (currentCounter >= 2 && walker.get(currentCounter - 2).equals(sbg.getMirror(path)))
      {
        thisRoute.setEnd(true);
      }
      if (!thisRoute.getEnd())
      {
        walkTestDirection(path);
        if (checkDoubleDouble())
        {
          setHashInList(oldTestX, oldTestY);
          thisRoute.setIntersectionId(intersectionId);
          thisRoute.setPoint(oldTestX, oldTestY);
          boolean doesChildWork = runSelfDemandingLoop(thisRoute);
          if (doesChildWork)
          {
            thisRoute.unsetAllNextPath();
          }
        }
      }
    }
    else
    {
      thisRoute = route.getPath(path);
      if (!route.getEnd())
      {
        resetTester(thisRoute);
        boolean doesChildWork = runSelfDemandingLoop(thisRoute);
        if (doesChildWork)
        {
          thisRoute.unsetAllNextPath();
        }
      }
    }

    walker.remove(currentCounter - 1);

    return thisRoute.getEnd();
  }

  private void resetTester(SingleDecisionPoint route)
  {
    testX = oldTestX = route.getX();
    testY = oldTestY = route.getY();
    intersectionId = route.getIntersectionId();

    return;
  }

  private int walkTestDirection(Direction path)
  {
    for (int stapCount = 0; stapCount < 1000; stapCount++)
    {
      if (walking(path))
      {
        return stapCount;
      }
      else if (tester.getBoundsInParent().intersects(manClone.getBoundsInParent()))
      {
        distanceCounter = 0;
        foundMan = true;
        updated = true;
        setWalker(walker);
      }

      oldTestX = testX;
      oldTestY = testY;
    }
    return -1;
  }

  private boolean walking(Direction path)
  {
    switch (path)
    {
      case UP:
        testY = testY - sbg.GSPEED;
        break;

      case DOWN:
        testY = testY + sbg.GSPEED;
        break;

      case LEFT:
        testX = testX - sbg.GSPEED;
        break;

      case RIGHT:
        testX = testX + sbg.GSPEED;
        break;
    }
    tester.setCenterX(testX);
    tester.setCenterY(testY);

    return checkTestMove();
  }

  private void setHashInList(double xHash, double yHash)
  {
    HashMap<Double, Double> newList = new HashMap<>();
    newList.put(xHash, yHash);
    listOfPoints.add(newList);
  }

  private boolean checkDoubleDouble()
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

  private boolean checkTestMove()
  {
    if (!sbg.checkBumpWall(tester))
    {
      intersectionId = -1;
      return true;
    }
    if (!checkIntersection())
    {
      oldTestX = testX = inters.getCenterX();
      oldTestY = testY = inters.getCenterY();
      inters = null;
      return true;
    }
    if (!sbg.checkBumpBorder(testX, testY))
    {
      return true;
    }
    return false;
  }

  protected boolean checkIntersection()
  {
    clone.setCenterX(testX);
    clone.setCenterY(testY);
    List<BaseObject> intersections = logic.map.getIntersectionArray();

    for (BaseObject intersection : intersections)
    {
      if (clone.getBoundsInParent().intersects(intersection.getObject().getBoundsInParent()))
      {
        int id = ((Intersection) intersection).getID();
        if (id == intersectionId)
        {
          return true;
        }
        else
        {
          inters = (Circle) intersection.getObject();
          intersectionId = id;
          return false;
        }
      }
    }
    return true;
  }
}
