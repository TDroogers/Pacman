/**
 * 
 * The man the user controll's.
 * 
 */
package nl.drogecode.pacman.objects;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import nl.drogecode.pacman.logic.GameLogic;

public class Man extends MovingObject
{
  private Circle man;
  private ArrayList<Double> lastMan;

  public Man(GameLogic logic)
  {
    super();
    man = new Circle();
    man.setFill(Color.YELLOW);
    man.setRadius(5.0);

    restart();
    startMove();
  }

  public Circle getObject()
  {
    return man;
  }

  public void restart()
  {
    x = newX = oldX = 22;
    y = newY = oldY = 47;

    fillLastMan();

    man.setCenterX(x);
    man.setCenterY(y);
    direction = 0;
  }

  public double getXman()
  {
    return x;
  }

  public double getYman()
  {
    return y;
  }

  public ArrayList<Double> getLastBumb()
  {
    return lastMan;
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

  protected void initiateLoop()
  {
    maxX = logic.getSceneWidth();
    maxY = logic.getSceneHight();
    newX = man.getCenterX();
    newY = man.getCenterY();
    walking = true;
    while (walking)
    {
      walker();
      if (!checkMove(man))
      {
        afterBumb();
        Thread.yield();
        sleep.sleeper(Long.MAX_VALUE);
        continue;
      }
      moveObject(man);
      checkBumpCoin();
      oldX = x;
      oldY = y;
      x = newX;
      y = newY;

      Thread.yield();
      sleep.sleeper(Long.MAX_VALUE);
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

  private void afterBumb()
  {
    fillLastMan();
  }

  private void fillLastMan()
  {
    lastMan = new ArrayList<>();
    lastMan.add(oldX);
    lastMan.add(oldY);
  }

  private void checkBumpCoin()
  {
    List<Shape> coins = logic.map.getCoinsArray();

    for (Shape coin : coins)
    {
      if (man.getBoundsInParent().intersects(coin.getBoundsInParent()))
      {
        logic.map.remove(coin, 1);
        logic.setCoinsLeft((byte) 1);
        coins.remove(coin);
        logic.checkWin();
        return;
      }
    }
  }
}
