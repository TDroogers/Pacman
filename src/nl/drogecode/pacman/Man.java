package nl.drogecode.pacman;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Man extends Circle
{
  private Stage stage;
  private Thread th;
  private Sleeper sleep = new Sleeper();
  private double x, y, newX, newY, maxX, maxY;
  private int direction;
  private final int SPEED = 3;
  private boolean walking;

  public Man(Stage stage)
  {
    this.stage = stage;
    setCenterX(100);
    setCenterY(100);
    setFill(Color.YELLOW);
    setRadius(5.0);
    
    startMove();
  }
  
  public void setDirection(int newDir)
  {
    direction = newDir;
  }

  /*
   * ===================================================
   * private funcitons
   * ===================================================
   * 
   * loop
   */
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
    maxX = stage.getScene().getWidth();
    maxY = stage.getScene().getHeight();
    walking = true;
    while (walking)
    {
      newX = x = getCenterX();
      newY = y = getCenterY();
      walker();
      if (!checkBumpWall())
      {
        sleep.sleeper(30);
        continue;
      }
      updateMan();
      sleep.sleeper(30);
    }
  }
  
  /*
   * ===================================================
   * Stuff while in loop
   */
  
  private void walker()
  {
    switch(direction)
    {
      case 0:
        newY = y - SPEED;
        break;
      
      case 1:
        newX = x + SPEED;
        break; 
        
      case 2:
        newY = y + SPEED;
        break;
        
      case 3:
        newX = x - SPEED;
        break;
    }
  }

  private boolean checkBumpWall()
  {
    // Has to be side specific, but fine for now.
    if (newX > maxX || newX < 0 || newY > maxY || newY < 25)
    {
      return false;
    }
    return true;
  }
  
  private void updateMan()
  {
    Platform.runLater(new Runnable()
    {
      @Override public void run()
      {
        setCenterX(newX);
        setCenterY(newY);
      }
    });
  }
}
