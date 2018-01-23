package nl.drogecode.pacman.logic.pathfinder;

import java.util.List;

import javafx.scene.shape.Circle;
import nl.drogecode.pacman.enums.Direction;
import nl.drogecode.pacman.logic.GameLogic;
import nl.drogecode.pacman.objects.BaseObject;
import nl.drogecode.pacman.objects.Intersection;
import nl.drogecode.pacman.objects.MovingObject;

public class WalkUntilObstacle
{
  private Circle tester, manClone, inters, clone;
  private double testX, testY, oldTestX, oldTestY, speed;
  private int intersectionId;
  private MovingObject moving;
  private GameLogic logic;

  public WalkUntilObstacle(double speed, MovingObject moving, GameLogic logic)
  {
    this(new Circle(), speed, moving, logic);
  }

  public WalkUntilObstacle(Circle manClone, double speed, MovingObject moving, GameLogic logic)
  {
    this.manClone = manClone;
    this.speed = speed;
    this.moving = moving;
    this.logic = logic;

    intersectionId = -1;

    tester = new Circle();
    tester.setRadius(((Circle) moving.getObject()).getRadius());
    clone = new Circle();
    clone.setRadius(1);
  }

  public double getOldTestX()
  {
    return oldTestX;
  }

  public double getOldTestY()
  {
    return oldTestY;
  }

  public int getIntersectionId()
  {
    return intersectionId;
  }

  public void resetTester(SingleDecisionPoint route)
  {
    testX = oldTestX = route.getX();
    testY = oldTestY = route.getY();
    int intersTemp = route.getIntersectionId();
    if (intersTemp != 0)
    {
      intersectionId = intersTemp;
    }

    return;
  }

  public void resetTester(double x, double y)
  {
    testX = oldTestX = x;
    testY = oldTestY = y;
  }

  public int walkTestDirection(Direction path)
  {
    if (path == null)
    {
      return -3;
    }
    for (int stapCount = 0; stapCount < 1000; stapCount++)
    {
      if (walking(path))
      {
        return stapCount;
      }
      else if (tester.getBoundsInParent().intersects(manClone.getBoundsInParent()))
      {
        return -1;
      }

      oldTestX = testX;
      oldTestY = testY;
    }
    return -2;
  }

  public boolean walking(Direction path)
  {
    switch (path)
    {
      case UP:
        testY = testY - speed;
        break;

      case DOWN:
        testY = testY + speed;
        break;

      case LEFT:
        testX = testX - speed;
        break;

      case RIGHT:
        testX = testX + speed;
        break;
    }
    tester.setCenterX(testX);
    tester.setCenterY(testY);

    return checkTestMove();
  }

  /*
   * 
   * private function's
   * 
   */

  private boolean checkTestMove()
  {
    if (!moving.checkBumpWall(tester))
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
    if (!moving.checkBumpBorder(testX, testY))
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
