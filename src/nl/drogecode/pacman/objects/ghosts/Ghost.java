package nl.drogecode.pacman.objects.ghosts;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nl.drogecode.pacman.logic.GameLogic;
import nl.drogecode.pacman.objects.NpcObject;

public abstract class Ghost extends NpcObject
{
  protected Circle ghost;
  protected final double GSPEED = SPEED*0.75;
  
  public Ghost(double x, double y, GameLogic logic)
  {
    super();

    ghost = new Circle();
    ghost.setCenterX(x);
    ghost.setCenterY(y);
    ghost.setFill(Color.GREEN);
    ghost.setRadius(5.0);
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
      if (!checkMove(ghost))
      {
        afterBumb();
        sleep.sleeper(30);
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
      sleep.sleeper(30);
    }
  }
  
  protected abstract void beforeLoop();
  protected abstract void walker();
  protected abstract void afterBumb();
}
