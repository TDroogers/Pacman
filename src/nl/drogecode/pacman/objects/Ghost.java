package nl.drogecode.pacman.objects;

import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import nl.drogecode.pacman.*;

public class Ghost extends Circle
{
  private static Stage stage;
  private static Man man;
  private static GameLogic logic;
  private Sleeper sleep = new Sleeper();
  private Thread th;
  private double oldX, oldY, x, y, newX, newY, maxX, maxY;
  private boolean walking;
  public Ghost(double x, double y)
  {
    setCenterX(x);
    setCenterY(y);
    setFill(Color.GREEN);
    setRadius(5.0);
    startMove();
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
    newX = getCenterX();
    newY = getCenterY();
    walking = true;
    while (walking)
    {
      walker();
      logic.checkBumpWall(this, newX, newY);
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
    double xchecker = man.getCenterX() - getCenterX();
    double ychecker = man.getCenterY() - getCenterY();
    
    if (Math.abs(xchecker) >= Math.abs(ychecker))
    {
      System.out.print("x");
    }
    else
    {
      System.out.println("y");
    }
    
  }
}
