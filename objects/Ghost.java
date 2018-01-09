package nl.drogecode.pacman.objects;

import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nl.drogecode.pacman.logic.GameLogic;

public class Ghost extends NpcObject
{
  private Circle ghost;
  
  public Ghost(double x, double y, GameLogic logic)
  {
    super(logic);

    ghost = new Circle();
    ghost.setCenterX(x);
    ghost.setCenterY(y);
    ghost.setFill(Color.GREEN);
    ghost.setRadius(5.0);
    startMove();
  }
  
  public Circle getGhost()
  {
    return ghost;
  }
  
  private void startMove()
  {
    Task<Void> task = new Task<Void>()
    {
      @Override protected Void call() throws Exception
      {
        initiateLoop();
        return null;
      }
    };
    th = new Thread(task);
    th.setDaemon(true);
    th.start();
  }
  
  private void initiateLoop()
  {
    while (!logic.getReady())
    {
      sleep.sleeper();
    }
    maxX = logic.getSceneWidth();
    maxY = logic.getSceneHight();
    newX = ghost.getCenterX();
    newY = ghost.getCenterY();
    walking = true;
    while (walking)
    {
      walker();
      checkBumpWall(ghost, newX, newY);
      if (!checkBumpBorder(newX, maxX, newY, maxY))
      {
        sleep.sleeper(30);
        continue;
      }
      oldX = x;
      oldY = y;
      x = newX;
      y = newY;
      sleep.sleeper(30);
    }
  }
  
  private void walker()
  {
    double xchecker = logic.getXMan() - ghost.getCenterX();
    double ychecker = logic.getYMan() - ghost.getCenterY();
    
    if (Math.abs(xchecker) >= Math.abs(ychecker))
    {
      System.out.print("x");
      if (xchecker > 0)
      {
        System.out.println(" Right");
      }
      else
      {
        System.out.println(" Left");
      }
    }
    else
    {
      System.out.print("y");
      if (ychecker > 0)
      {
        System.out.println(" Down");
      }
      else
      {
        System.out.println(" Up");
      }
    }
    
  }
}
