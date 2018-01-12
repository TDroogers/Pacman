package nl.drogecode.pacman.objects;

import javafx.scene.shape.Circle;

public abstract class NpcObject extends MovingObject
{

  public NpcObject ()
  {
    super();
  }
  
  public boolean setWalking(boolean res)
  {
    walking = res;
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
