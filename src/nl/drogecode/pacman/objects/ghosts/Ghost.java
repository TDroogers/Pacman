package nl.drogecode.pacman.objects.ghosts;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nl.drogecode.pacman.enums.Direction;
import nl.drogecode.pacman.logic.GameLogic;
import nl.drogecode.pacman.objects.NpcObject;

public abstract class Ghost extends NpcObject
{
  protected Direction dir;
  protected ArrayList<Direction> previus;
  protected Circle ghost;
  protected final double GSPEED = SPEED * 0.75;

  private boolean bumped;

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

  protected void initiateLoop()
  {
    while (!logic.getReady())
    {
      sleep.sleeper();
    }
    maxX = logic.getSceneWidth();
    maxY = logic.getSceneHight();
    x = newX = ghost.getCenterX();
    y = newY = ghost.getCenterY();
    walking = true;
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
        sleep.sleeper(30);
        logic.loseLife();
        break;
      }
      oldX = x;
      oldY = y;
      x = newX;
      y = newY;

      Thread.yield();
      sleep.sleeper(Long.MAX_VALUE);
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

      case RIGHT:
        newX = x + GSPEED;
        break;

      case LEFT:
        newX = x - GSPEED;
        break;
    }
  }

  protected abstract void beforeLoop();

  protected abstract void afterBumb();

  protected abstract void noBumb();

  private boolean checkBumb()
  {

    if (!checkMove(ghost))
    {
      afterBumb();
      sleep.sleeper(30);
      bumped = true;
      intersected = false;
      intersectionId = -1;
      return false;
    }
    else if (intersected)
    {
      afterBumb();
      sleep.sleeper(30);
      bumped = false;
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

}
