package nl.drogecode.pacman;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Man extends Circle implements Cloneable
{
  private volatile Stage stage;
  private volatile Map map;
  private volatile GameLogic logic;
  private volatile Thread th;
  private Sleeper sleep = new Sleeper();
  private volatile double oldX, oldY, x, y, newX, newY, maxX, maxY;
  private volatile int direction;
  private final int SPEED = 2;
  private volatile boolean walking;

  public Man(Stage stage, Map map, GameLogic logic)
  {
    this.stage = stage;
    this.map = map;
    this.logic = logic;
    setFill(Color.YELLOW);
    setRadius(5.0);

    restart();
    startMove();
  }

  public void restart()
  {
    x = newX = oldX = 22;
    y = newY = oldY = 47;
    setCenterX(x);
    setCenterY(y);
    direction = 0;
  }

  public void setDirection(int newDir)
  {
    direction = newDir;
  }

  public double getXman()
  {
    return x;
  }

  public double getYman()
  {
    return y;
  }

  public void startMove()
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
   * ===================================================
   * 
   * private funcitons
   * 
   * ===================================================
   * 
   * loop
   */

  private void initiateLoop() throws CloneNotSupportedException
  {
    maxX = stage.getScene().getWidth();
    maxY = stage.getScene().getHeight();
    newX = getCenterX();
    newY = getCenterY();
    walking = true;
    while (walking)
    {
      walker();
      if (!logic.checkBumpBorder(newX, maxX, newY, maxY))
      {
        System.out.println("wall :(");
        direction = 0;
        sleep.sleeper(30);
        continue;
      }
      if (!logic.checkBumpWall(this, newX, newY))
      {
        direction = 0;
        x = newX = oldX;
        y = newY = oldY;
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
   * ===================================================
   * 
   * Stuff while in loop
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
      }
    });
  }
}
