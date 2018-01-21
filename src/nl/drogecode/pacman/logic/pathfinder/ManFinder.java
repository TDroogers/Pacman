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
import nl.drogecode.pacman.logic.GameLogic;
import nl.drogecode.pacman.logic.Sleeper;
import nl.drogecode.pacman.objects.BaseObject;
import nl.drogecode.pacman.objects.Intersection;
import nl.drogecode.pacman.objects.ghosts.SmartBehindGhost;

public class ManFinder extends Thread
{
  private Circle tester, inters, clone, manClone;
  private GameLogic logic;
  private SmartBehindGhost sbg;
  private Sleeper sleep;
  private int distanceCounter, currentCounter;
  private double testX, testY, oldTestX, oldTestY;
  private boolean foundMan;
  private ArrayList<Direction> walker, realWalker;
  private ArrayList<Double> manPrevLast;
  private ArrayList<HashMap<Double, Double>> listOfPoints;
  private int intersectionId;

  public ManFinder(SmartBehindGhost sbg, GameLogic logic)
  {
    this.sbg = sbg;
    this.logic = logic;
    tester = new Circle();
    clone = new Circle();
    manClone = new Circle();
    tester.setRadius(sbg.getObject().getRadius());
    clone.setRadius(1);
    realWalker = new ArrayList<>();
    listOfPoints = new ArrayList<>();
    sleep = new Sleeper();
  }

  @Override public void run()
  {
    manClone.setRadius(logic.man.getObject().getRadius());
    MainLoop();
  }

  public synchronized ArrayList<Direction> getWalker()
  {
    return realWalker;
  }

  private synchronized void setWalker(ArrayList<Direction> realWalker)
  {
    this.realWalker = new ArrayList<>(realWalker);
  }

  private void MainLoop()
  {
    for (;;)
    {
      ArrayList<Double> lastMan = logic.man.getLastBumb();

      if (lastMan.equals(manPrevLast))
      {
        sleep.sleeper(Long.MAX_VALUE);
        continue;
      }
      manPrevLast = lastMan;
      manClone.setCenterX(lastMan.get(0));
      manClone.setCenterY(lastMan.get(1));
      HashMap<Object, Object> routeMap = getHashMap();
      routeMap.put("x", sbg.getSbgX());
      routeMap.put("y", sbg.getSbgY());
      setHashInList(sbg.getSbgX(), sbg.getSbgY());

      distanceCounter = 0;
      currentCounter = 0;
      foundMan = false;
      walker = new ArrayList<>();

      startFindLoop(routeMap);
    }
  }

  private void startFindLoop(HashMap<Object, Object> routeMap)
  {
    while (distanceCounter <= 100)
    {
      distanceCounter++;
      runSelfDemandingLoop(routeMap);

      if (foundMan)
      {
        System.out.println("Here is man: " + realWalker);
        break;
      }
      System.out.println("round: " + distanceCounter);
    }
    System.out.println(listOfPoints);
  }

  private boolean runSelfDemandingLoop(HashMap<Object, Object> routeMap)
  {
    currentCounter++;

    boolean ret = false;
    if (distanceCounter >= currentCounter)
    {
      boolean up = walkingPaths(Direction.UP, routeMap);
      boolean down = walkingPaths(Direction.DOWN, routeMap);
      boolean left = walkingPaths(Direction.LEFT, routeMap);
      boolean right = walkingPaths(Direction.RIGHT, routeMap);

      if (up && down && left && right)
      {
        ret = true;
      }
    }
    currentCounter--;
    return ret;
  }

  @SuppressWarnings("unchecked") private boolean walkingPaths(Direction path, HashMap<Object, Object> routeMap)
  {
    if (routeMap.get(path) == "end")
    {
      return true;
    }
    walker.add(path);
    HashMap<Object, Object> hashPath;
    resetTester(routeMap);

    if (routeMap.get(path) == null)
    {
      routeMap.put(path, getHashMap());
      hashPath = (HashMap<Object, Object>) routeMap.get(path);
      hashPath.put("end", walking(path));

      if (currentCounter >= 2 && walker.get(currentCounter - 2).equals(sbg.getMirror(path)))
      {
        hashPath.put("end", (Boolean) true);
      }
      if (hashPath.get("end") == (Boolean) false)
      {
        walkTestDirection(path);
        boolean check = checkDoubleDouble();
        if (check)
        {
          setHashInList(oldTestX, oldTestY);
          hashPath.put("x", oldTestX);
          hashPath.put("y", oldTestY);
          boolean doesChildWork = runSelfDemandingLoop(hashPath);
          if (doesChildWork)
          {
            hashPath.put("end", (Boolean) true);
            hashPath.put(Direction.UP, "end");
            hashPath.put(Direction.DOWN, "end");
            hashPath.put(Direction.LEFT, "end");
            hashPath.put(Direction.RIGHT, "end");
          }
        }
        else
        {
          System.out.println(currentCounter);
          System.out.println(listOfPoints);
          System.out.println(testX + " " + testY);
          System.out.println(oldTestX + " " + oldTestY);
          hashPath.put("end", (Boolean) true);
        }
      }
    }
    else

    {
      hashPath = (HashMap<Object, Object>) routeMap.get(path);
      boolean doesChildWork = runSelfDemandingLoop(hashPath);
      if (doesChildWork)
      {
        hashPath.put("end", (Boolean) true);
        hashPath.put(Direction.UP, "end");
        hashPath.put(Direction.DOWN, "end");
        hashPath.put(Direction.LEFT, "end");
        hashPath.put(Direction.RIGHT, "end");
      }
    }

    walker.remove(currentCounter - 1);

    return (boolean) hashPath.get("end");
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
        setWalker(walker);
      }

      oldTestX = testX;
      oldTestY = testY;
    }
    return -1;
  }

  private void resetTester(HashMap<Object, Object> routeMap)
  {
    testX = oldTestX = (double) routeMap.get("x");
    testY = oldTestY = (double) routeMap.get("y");

    return;
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

  private HashMap<Object, Object> getHashMap()
  {
    HashMap<Object, Object> newList = new HashMap<>();
    newList.put(Direction.UP, null);
    newList.put(Direction.DOWN, null);
    newList.put(Direction.LEFT, null);
    newList.put(Direction.RIGHT, null);
    newList.put("end", (Boolean) false);
    newList.put("x", 0.0);
    newList.put("y", 0.0);
    return newList;
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
        // System.out.println(listOfPoints.size() + "~~~~" + oldTestX + " : " + oldTestY);
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
