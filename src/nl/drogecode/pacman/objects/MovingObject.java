/**
 * 
 * The super above all moving objects, this are the Man and all Ghosts on the time of writing.
 * 
 */
package nl.drogecode.pacman.objects;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import nl.drogecode.pacman.enums.Direction;
import nl.drogecode.pacman.logic.GameLogic;
import nl.drogecode.pacman.logic.Sleeper;

public abstract class MovingObject extends BaseObject
{
  protected static GameLogic logic;

  protected Direction dir;
  protected Sleeper sleep;
  protected Thread th;
  protected Circle inters;
  protected double oldX, oldY, x, y, newX, newY, maxX, maxY;
  protected boolean walking, intersected;
  protected volatile int direction, intersectionId = 1;
  protected final int SPEED = 1;

  protected MovingObject()
  {
    sleep = new Sleeper();
  }

  public static boolean setLogic(GameLogic logic)
  {
    MovingObject.logic = logic;
    return true;
  }

  public void setDirection(int newDir)
  {
    direction = newDir;
  }

  public boolean checkBumpBorder(double newX, double newY)
  {
    // Has to be side specific, but fine for now. (In the current map you can never reach the borders)
    if (newX > maxX || newX < 0 || newY > maxY || newY < 25)
    {
      return false;
    }
    return true;
  }

  public boolean checkBumpWall(Circle clone)
  {
    List<Shape> shapes = new ArrayList<>(logic.map.getShapeArray());

    for (Shape shape : shapes)
    {
      if (clone.getBoundsInParent().intersects(shape.getBoundsInParent()))
      {
        return false;
      }
    }
    return true;
  }

  protected boolean checkMove(Circle object)
  {
    Circle clone = getClone(newX, newY);
    checkIntersection(clone);
    clone.setRadius(object.getRadius());
    if (!checkBumpBorder(newX, newY))
    {
      direction = 0;
      return false;
    }
    if (!checkBumpWall(clone))
    {
      direction = 0;
      x = newX = oldX;
      y = newY = oldY;
      return false;
    }
    return true;
  }

  protected boolean moveObject(Circle object)
  {
    double oldX = this.oldX;
    double oldY = this.oldY;
    Platform.runLater(new Runnable()
    {
      @Override public void run()
      {
        object.setCenterX(oldX);
        object.setCenterY(oldY);
      }
    });
    return true;
  }

  protected void startMove()
  {
    if (!walking)
    {
      Task<Void> task = new Task<Void>()
      {
        @Override protected Void call() throws Exception
        {
          waitUntilReady();
          logic.setWakeUp(th);
          initiateLoop();
          return null;
        }
      };
      th = new Thread(task);
      th.setDaemon(true);
      th.start();
    }
  }

  /*
   * 
   * ====================================================
   * 
   * abstract function's
   * 
   */

  protected abstract void initiateLoop();

  /*
   * 
   * =====================================================
   * 
   * private functions
   * 
   */

  private void checkIntersection(Circle clone)
  {
    clone.setRadius(1);
    List<BaseObject> intersections = logic.map.getIntersectionArray();

    for (BaseObject intersection : intersections)
    {

      if (clone.getBoundsInParent().intersects(intersection.getObject().getBoundsInParent()))
      {
        isIntersectedAProblem(intersection);
        return;
      }
    }
  }

  private void isIntersectedAProblem(BaseObject intersection)
  {
    int id = ((Intersection) intersection).getID();
    if (id == intersectionId)
    {
      intersected = false;
      return;
    }
    else
    {
      inters = (Circle) intersection.getObject();
      intersectionId = id;
      intersected = true;
      return;
    }
  }

  private Circle getClone(double newX, double newY)
  {
    Circle clone = new Circle();
    clone.setCenterX(newX);
    clone.setCenterY(newY);

    return clone;
  }

  private void waitUntilReady()
  {
    while (!logic.getReady())
    {
      /*
       * Long.MAX_VALUE does net work yet, logic.setWakeUp() is not garranteed to be ready.
       */
      sleep.sleeper(60);
    }
  }
}
