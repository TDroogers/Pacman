package nl.drogecode.pacman.objects;

import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import nl.drogecode.pacman.Sleeper;
import nl.drogecode.pacman.logic.GameLogic;

public abstract class MovingObject extends BaseObject
{
  protected static GameLogic logic;
  
  protected Sleeper sleep;
  protected Thread th;
  protected double oldX, oldY, x, y, newX, newY, maxX, maxY;
  protected boolean walking;
  protected volatile int direction;
  protected final int SPEED = 2;
  
  protected MovingObject ()
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
  
  protected boolean checkMove(Circle object)
  {
    if (!checkBumpBorder())
    {
      direction = 0;
      return false;
    }
    if (!checkBumpWall(object, newX, newY))
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

    Platform.runLater(new Runnable()
    {
      @Override public void run()
      {
        object.setCenterX(newX);
        object.setCenterY(newY);
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
          sleep.sleeper(60);
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
  
  private boolean checkBumpBorder()
  {
    // Has to be side specific, but fine for now. (In the current map you can never reach the borders)
    if (newX > maxX || newX < 0 || newY > maxY || newY < 25)
    {
      return false;
    }
    return true;
  }

  private boolean checkBumpWall(Circle a, double newX, double newY)
  {

    Circle clone = new Circle();
    clone.setCenterX(newX);
    clone.setCenterY(newY);
    clone.setRadius(a.getRadius());
    List<Shape> shapes = logic.getWallArray();

    for (Shape shape : shapes)
    {
      if (clone.getBoundsInParent().intersects(shape.getBoundsInParent()))
      {
        return false;
      }
    }
    return true;
  }
}
