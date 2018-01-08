package nl.drogecode.pacman.objects;

import javafx.stage.Stage;
import nl.drogecode.pacman.Map;
import nl.drogecode.pacman.Sleeper;
import nl.drogecode.pacman.logic.GameLogic;

public class MovingObject extends BaseObject
{
  protected volatile Stage stage;
  protected volatile Map map;
  protected volatile GameLogic logic;
  
  protected Sleeper sleep = new Sleeper();
  protected Thread th;
  protected double oldX, oldY, x, y, newX, newY, maxX, maxY;
  protected boolean walking;
  protected volatile int direction;
  protected final int SPEED = 2;
  
  public MovingObject (Stage stage, Map map, GameLogic logic)
  {

    this.stage = stage;
    this.map = map;
    this.logic = logic;
  }
}
