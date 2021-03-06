/**
 * 
 * The super above all npc's, currently only the ghosts.
 * 
 */
package nl.drogecode.pacman.objects;

import javafx.scene.shape.Circle;

public abstract class NpcObject extends MovingObject
{

  public NpcObject()
  {
    super();
  }

  public boolean getWalking()
  {
    return walking;
  }

  public boolean setWalking(boolean res)
  {
    walking = res;
    return true;
  }

  protected boolean checkBumpMan(Circle ghost)
  {
    Circle man = logic.man.getObject();

    if (man.getBoundsInParent().intersects(ghost.getBoundsInParent()))
    {
      return true;
    }
    return false;
  }
}
