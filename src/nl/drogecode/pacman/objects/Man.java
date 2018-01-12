package nl.drogecode.pacman.objects;

import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import nl.drogecode.pacman.logic.GameLogic;

public class Man extends MovingObject
{
  private Circle man;
  

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
        sleep.sleeper(30);
        continue;
      }
      moveObject(man);
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
    List<Shape> coins = logic.getCoinArray();

    for (Shape coin : coins)
    {
      if (man.getBoundsInParent().intersects(coin.getBoundsInParent()))
      {
        logic.removeCoin(coin);
        logic.setCoinsLeft((byte)1);
        logic.checkWin();
        coins.remove(coin);
        return;
      }
    }
  }
}
