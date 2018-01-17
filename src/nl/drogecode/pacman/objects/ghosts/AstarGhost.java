package nl.drogecode.pacman.objects.ghosts;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.shape.Circle;
import nl.drogecode.pacman.enums.Direction;
import nl.drogecode.pacman.logic.GameLogic;

public class AstarGhost extends Ghost
{
  private int distanceCounter, currentCounter, total;
  private int manIntersection = -2;
  private double testX, testY, oldTestX, oldTestY;
  private boolean foundMan;
  private ArrayList<Direction> walker, realWalker;
  private Circle tester;

  public AstarGhost(double x, double y, GameLogic logic)
  {
    super(x, y, logic);
    tester = new Circle();
    tester.setRadius(ghost.getRadius());
  }

  protected void findMan()
  {
    Integer newManIntersection = logic.man.getLastIntersectionId();
    if (manIntersection == newManIntersection)
    {
      return;
    }

    HashMap<Object, Object> routeMap = getHashMap();
    routeMap.put("x", x);
    routeMap.put("y", y);

    manIntersection = newManIntersection;
    distanceCounter = 0;
    currentCounter = 0;
    total = 0;
    walker = new ArrayList<>();

    while (distanceCounter <= 1000)
    {
      distanceCounter++;
      runSelfDemandingLoop(routeMap);
      System.out.println("fast? " + distanceCounter);

      if (foundMan)
      {
        System.out.println("Here is man: " + realWalker);
        break;
      }
    }
  }

  @Override protected void beforeLoop()
  {
    findMan();
  }

  @Override protected void afterBumb()
  {}

  @Override protected void noBumb()
  {}

  private void runSelfDemandingLoop(HashMap<Object, Object> routeMap)
  {
    currentCounter++;
    total++;

    if (distanceCounter >= currentCounter)
    {
      // System.out.println(total + " : " + distanceCounter + " : " + currentCounter);
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
    HashMap<Object, Object> hashPath;
    if (routeMap.get(path) == null)
    {
      routeMap.put(path, getHashMap());
    }

    hashPath = (HashMap<Object, Object>) routeMap.get(path);
    hashPath.put("end", resetTester(path, routeMap));

    walker.add(path);
    if (hashPath.get("end") == (Boolean) false)
    {
      walkTestDirection(path);
      hashPath.put("x", oldTestX);
      hashPath.put("y", oldTestY);
      oldTestX = testX;
      oldTestY = testY;
      System.out.println(walker);
      runSelfDemandingLoop(hashPath);
    }
    else
    {
      System.err.println(path + " kan niet");
    }

    walker.remove(currentCounter - 1);

    return hashPath;
  }

  private int walkTestDirection(Direction path)
  {
    for (int stapCount = 0; stapCount < 1000; stapCount++)
    {
      if (walking(path) || intersected)
      {
        return stapCount;
      }
      else if (tester.getBoundsInParent().intersects(logic.man.getObject().getBoundsInParent()))
      {
        distanceCounter = 0;
        foundMan = true;
        realWalker = walker;
      }

      newX = testX;
      newY = testY;
      moveObject(ghost);
      sleep.sleeper(Long.MAX_VALUE);
    }
    return -1;
  }

  private Boolean resetTester(Direction path, HashMap<Object, Object> routeMap)
  {
    testX = (double) routeMap.get("x");
    testY = (double) routeMap.get("y");

    return walking(path);
  }

  private boolean walking(Direction path)
  {
    switch (path)
    {
      case UP:
        testY = testY - GSPEED;
        break;

      case DOWN:
        testY = testY + GSPEED;
        break;

      case LEFT:
        testX = testX - GSPEED;
        break;

      case RIGHT:
        testX = testX + GSPEED;
        break;
    }
    tester.setCenterX(oldTestX);
    tester.setCenterY(oldTestY);

    return checkTestMove(testX, testY);
  }

  private HashMap<Object, Object> getHashMap()
  {
    HashMap<Object, Object> newList = new HashMap<>();
    newList.put(Direction.UP, null);
    newList.put(Direction.DOWN, null);
    newList.put(Direction.LEFT, null);
    newList.put(Direction.RIGHT, null);
    newList.put("end", (Boolean) false);
    newList.put("x", x);
    newList.put("y", y);
    return newList;
  }

  private boolean checkTestMove(double testX, double testY)
  {
    checkIntersection(tester);
    tester.setRadius(ghost.getRadius());
    if (!checkBumpBorder(testX, testY))
    {
      return true;
    }
    if (!checkBumpWall(tester))
    {
      return true;
    }
    return false;
  }
}
