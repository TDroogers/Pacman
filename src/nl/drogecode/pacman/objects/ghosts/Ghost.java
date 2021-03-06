/**
 * 
 * Abstract class above all ghost's.
 * 
 */
package nl.drogecode.pacman.objects.ghosts;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nl.drogecode.pacman.enums.Direction;
import nl.drogecode.pacman.logic.GameLogic;
import nl.drogecode.pacman.objects.NpcObject;

public abstract class Ghost extends NpcObject
{
  protected ArrayList<Direction> previus;
  protected Circle ghost;
  protected boolean bumped;
  public final double GSPEED = SPEED * 0.75;

  public Ghost(double x, double y, GameLogic logic)
  {
    super();

    ghost = new Circle();
    ghost.setCenterX(x);
    ghost.setCenterY(y);
    ghost.setFill(Color.GREEN);
    ghost.setRadius(5.0);

    previus = new ArrayList<>();
    startMove();
  }

  public Circle getObject()
  {
    return ghost;
  }

  public Direction getMirror(Direction dir)
  {
    switch (dir)
    {
      case UP:
        return Direction.DOWN;

      case DOWN:
        return Direction.UP;

      case LEFT:
        return Direction.RIGHT;

      case RIGHT:
        return Direction.LEFT;

      default:
        System.err.println("Somthing went wrong with getMirror.");
        return Direction.DOWN;

    }
  }

  protected void initiateLoop()
  {
    beforeLoopAlwais();
    beforeLoop();
    while (walking)
    {
      walker();
      if (!checkBumb())
      {
        continue;
      }
      moveObject(ghost);
      if (checkBumpMan(ghost))
      {
        walking = false;
        yieldLong();
        logic.loseLife();
        break;
      }
      oldX = x;
      oldY = y;
      x = newX;
      y = newY;

      yieldLong();
    }
  }

  protected void walker()
  {

    switch (dir)
    {
      case UP:
        newY = y - GSPEED;
        break;

      case DOWN:
        newY = y + GSPEED;
        break;

      case LEFT:
        newX = x - GSPEED;
        break;

      case RIGHT:
        newX = x + GSPEED;
        break;
    }
  }

  protected Direction findManShort()
  {
    double xchecker = logic.man.getXman() - ghost.getCenterX();
    double ychecker = logic.man.getYman() - ghost.getCenterY();

    if (Math.abs(xchecker) >= Math.abs(ychecker))
    {
      if (xchecker > 0)
      {
        return Direction.RIGHT;
      }
      else
      {
        return Direction.LEFT;
      }
    }
    else
    {
      if (ychecker > 0)
      {
        return Direction.DOWN;
      }
      else
      {
        return Direction.UP;
      }
    }
  }

  protected abstract void beforeLoop();

  protected abstract void afterBumb();

  protected abstract void noBumb();

  private void beforeLoopAlwais()
  {
    while (!logic.getReady())
    {
      Thread.yield();
      sleep.sleeper(60);
    }
    maxX = logic.getSceneWidth();
    maxY = logic.getSceneHight();
    x = oldX = newX = ghost.getCenterX();
    y = oldY = newY = ghost.getCenterY();
    walking = true;
  }

  private boolean checkBumb()
  {
    if (!checkMove(ghost))
    {
      afterBumb();
      Cantmove();
      return false;
    }
    else if (intersected)
    {
      afterBumb();
      hitIntersection();
    }
    else
    {
      if (!bumped)
      {
        noBumb();
      }
      bumped = false;
    }
    return true;
  }

  private void Cantmove()
  {
    bumped = true;
    intersected = false;
    intersectionId = -1;

    yieldLong();
  }

  private void hitIntersection()
  {
    bumped = false;
    yieldLong();
  }

}
