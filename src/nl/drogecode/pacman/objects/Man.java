package nl.drogecode.pacman.objects;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import nl.drogecode.pacman.*;
import nl.drogecode.pacman.logic.GameLogic;

public class Man extends MovingObject
{
  private Circle man;
  

  public Man(Stage stage, Map map, GameLogic logic)
  {
    super(stage, map, logic);
    man = new Circle();
    man.setFill(Color.YELLOW);
    man.setRadius(5.0);

    restart();
    startMove();
  }
  
  public Circle getMan()
  {
    return man;
  }

  public void restart()
  {
    x = newX = oldX = 22;
    y = newY = oldY = 47;
    man.setCenterX(x);
    man.setCenterY(y);
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
    newX = man.getCenterX();
    newY = man.getCenterY();
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
      if (!logic.checkBumpWall(man, newX, newY))
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
      if (man.getBoundsInParent().intersects(coin.getBoundsInParent()))
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
        man.setCenterX(newX);
        man.setCenterY(newY);
      }
    });
  }
}
