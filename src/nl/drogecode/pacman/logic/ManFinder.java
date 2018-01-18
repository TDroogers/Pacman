package nl.drogecode.pacman.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.shape.Circle;
import nl.drogecode.pacman.enums.Direction;
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
  private boolean foundMan, newVersion;
  private ArrayList<Direction> walker, realWalker;
  private ArrayList<Double> manPrevLast;
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
    sleep = new Sleeper();
  }

  @Override public void run()
  {
    manClone.setRadius(logic.man.getObject().getRadius());
    realWalker = new ArrayList<>();
    realWalker.add(Direction.RIGHT);
    realWalker.add(Direction.UP);
    realWalker.add(Direction.UP);
    realWalker.add(Direction.UP);
    realWalker.add(Direction.UP);
    realWalker.add(Direction.RIGHT);
    realWalker.add(Direction.UP);
    realWalker.add(Direction.LEFT);
    realWalker.add(Direction.DOWN);
    realWalker.add(Direction.RIGHT);
    realWalker.add(Direction.UP);
    realWalker.add(Direction.RIGHT);
    realWalker.add(Direction.UP);
    realWalker.add(Direction.LEFT);
    realWalker.add(Direction.UP);
    newVersion = true;
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
        sleep.sleeper(30);
        continue;
      }
      manPrevLast = lastMan;
      manClone.setCenterX(lastMan.get(0));
      manClone.setCenterY(lastMan.get(1));
      HashMap<Object, Object> routeMap = getHashMap();
      routeMap.put("x", sbg.getSbgX());
      routeMap.put("y", sbg.getSbgY());

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
  }

  private void runSelfDemandingLoop(HashMap<Object, Object> routeMap)
  {
    currentCounter++;

    if (distanceCounter >= currentCounter)
    {
      walkingPaths(Direction.UP, routeMap);
      walkingPaths(Direction.DOWN, routeMap);
      walkingPaths(Direction.LEFT, routeMap);
      walkingPaths(Direction.RIGHT, routeMap);
    }
    currentCounter--;
  }

  @SuppressWarnings("unchecked") private HashMap<Object, Object> walkingPaths(Direction path,
      HashMap<Object, Object> routeMap)
  {
    walker.add(path);
    HashMap<Object, Object> hashPath;
    if (routeMap.get(path) == null)
    {
      routeMap.put(path, getHashMap());
    }

    hashPath = (HashMap<Object, Object>) routeMap.get(path);
    hashPath.put("end", resetTester(path, routeMap));

    if (currentCounter >= 2 && walker.get(currentCounter - 2).equals(sbg.getMirror(path)))
    {
      hashPath.put("end", (Boolean) true);
    }

    if (hashPath.get("end") == (Boolean) false)
    {
      walkTestDirection(path);
      hashPath.put("x", oldTestX);
      hashPath.put("y", oldTestY);
      runSelfDemandingLoop(hashPath);
    }

    walker.remove(currentCounter - 1);

    return hashPath;
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

  private Boolean resetTester(Direction path, HashMap<Object, Object> routeMap)
  {
    testX = oldTestX = (double) routeMap.get("x");
    testY = oldTestY = (double) routeMap.get("y");

    return walking(path);
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
