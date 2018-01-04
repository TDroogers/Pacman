package nl.drogecode.pacman;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Man extends Circle
{
  private Stage stage;
  private Map map;
  private Thread th;
  private Sleeper sleep = new Sleeper();
  private double oldX, oldY, x, y, newX, newY, maxX, maxY;
  private int direction;
  private final int SPEED = 2;
  private boolean walking;

  public Man(Stage stage, Map map)
  {
    this.stage = stage;
    this.map = map;
    setCenterX(22);
    setCenterY(47);
    setFill(Color.YELLOW);
    setRadius(5.0);

    startMove();
  }

  public void setDirection(int newDir)
  {
    direction = newDir;
  }

  /*
   * =================================================== private funcitons
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
      newX = getCenterX();
      newY = getCenterY();
      walker();
      if (!checkBumpBorder())
      {
        sleep.sleeper(30);
        continue;
      }
      updateMan();
      checkBumpCoin();
      oldX = x;
      oldY = y;
      x = newX;
      y = newY;
      sleep.sleeper(30);
    }
  }

  /*
   * =================================================== Stuff while in loop
   */

  private void walker()
  {
    switch (direction)
    {
      case 1:
        newY = y - SPEED;
        break;

      case 2:
        newX = x + SPEED;
        break;

      case 3:
        newY = y + SPEED;
        break;

      case 4:
        newX = x - SPEED;
        break;
    }
  }

  private boolean checkBumpBorder()
  {
    // Has to be side specific, but fine for now.
    if (newX > maxX || newX < 0 || newY > maxY || newY < 25)
    {
      direction = 0;
      return false;
    }
    return true;
  }

  private void checkBumpWall()
  {
    ArrayList<Shape> shapes = map.getShapeArray();

    for (Shape shape : shapes)
    {
      if (getBoundsInParent().intersects(shape.getBoundsInParent()))
      {
        direction = 0;
        setCenterX(oldX);
        setCenterY(oldY);
        return;
      }
    }
  }
  
  private void checkBumpCoin()
  {
    ArrayList<Shape> coins = map.getCoinsArray();
    
    for (Shape coin : coins)
    {
      if (getBoundsInParent().intersects(coin.getBoundsInParent()))
      {
        map.remove(coin, 1);
        coins.remove(coin);
        return;
      }
    }
  }

  private void updateMan()
  {
    Platform.runLater(new Runnable()
    {
      @Override public void run()
      {
        setCenterX(newX);
        setCenterY(newY);
        checkBumpWall();
      }
    });
  }
}
