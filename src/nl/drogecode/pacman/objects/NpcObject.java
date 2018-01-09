package nl.drogecode.pacman.objects;

import javafx.scene.shape.Circle;
import nl.drogecode.pacman.logic.GameLogic;

public class NpcObject extends MovingObject
{
  protected static boolean restart;

  public NpcObject (GameLogic logic)
  {
    super(logic);
  }
  
  public static boolean setRestart(boolean res)
  {
    restart = res;
    return true;
  }
  
  protected boolean checkBumpMan(Circle ghost)
  {
    Circle man = logic.getMan();

      if (man.getBoundsInParent().intersects(ghost.getBoundsInParent()))
      {
        return true;
      }
      return false;
  }
}
