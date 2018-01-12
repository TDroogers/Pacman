package nl.drogecode.pacman.objects.ghosts;

import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nl.drogecode.pacman.logic.GameLogic;
import nl.drogecode.pacman.objects.NpcObject;

public abstract class Ghost extends NpcObject
{
  protected Circle ghost;
  
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
  
  public Circle getGhost()
  {
    return ghost;
  }
  
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
    while (!logic.getReady())
    {
      sleep.sleeper();
    }
    maxX = logic.getSceneWidth();
    maxY = logic.getSceneHight();
    x = newX = ghost.getCenterX();
    y = newY = ghost.getCenterY();
    walking = true;
    while (walking)
    {
      walker();
      if (!checkMove(ghost))
      {
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
  
  protected abstract void walker();
}
