package nl.drogecode.pacman.objects;

import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import nl.drogecode.pacman.*;
import nl.drogecode.pacman.logic.GameLogic;

public class Ghost extends NpcObject
{
  private Circle ghost;
  
  private static Stage stage;
  private static Man man;
  private static GameLogic logic;
  
  public Ghost(double x, double y)
  {
    /*
     * 
     * error, he needs a super.
     * 
     */
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
  
  public static void setStatics(Stage s, Man m, GameLogic l)
  {
    stage = s;
    man = m;
    logic = l;
  }
  
  private void startMove()
  {
    System.out.println(man);
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
    maxX = stage.getScene().getWidth();
    maxY = stage.getScene().getHeight();
    newX = ghost.getCenterX();
    newY = ghost.getCenterY();
    walking = true;
    while (walking)
    {
      walker();
      logic.checkBumpWall(ghost, newX, newY);
      if (!logic.checkBumpBorder(newX, maxX, newY, maxY))
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
    double xchecker = man.getMan().getCenterX() - ghost.getCenterX();
    double ychecker = man.getMan().getCenterY() - ghost.getCenterY();
    
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
