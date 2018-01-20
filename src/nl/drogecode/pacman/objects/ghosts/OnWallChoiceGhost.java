/**
 * 
 * On hitting a wall or intersection it will chose a path in the direction of the man.
 * 
 * Does get into infinite loop somtime's.
 * 
 */
package nl.drogecode.pacman.objects.ghosts;

import java.util.ArrayList;

import nl.drogecode.pacman.enums.Direction;
import nl.drogecode.pacman.logic.GameLogic;

public class OnWallChoiceGhost extends Ghost
{
  Direction mirror;

  public OnWallChoiceGhost(double x, double y, GameLogic logic)
  {
    super(x, y, logic);
  }

  @Override protected void beforeLoop()
  {
    findMan();
  }

  @Override protected void afterBumb()
  {
    findMan();
  }

  @Override protected void noBumb()
  {
    previus = new ArrayList<>();

    mirror = getMirror(dir);

    previus.add(mirror);
  }

  protected void findMan()
  {
    double xchecker = logic.man.getXman() - ghost.getCenterX();
    double ychecker = logic.man.getYman() - ghost.getCenterY();

    if (Math.abs(xchecker) >= Math.abs(ychecker) && (previus.isEmpty()
        || (!((previus.contains(Direction.RIGHT) && xchecker > 0) || previus.contains(Direction.LEFT) && xchecker <= 0)
            || (previus.contains(Direction.DOWN) && ychecker > 0 || previus.contains(Direction.UP) && ychecker <= 0))))
    {
      if ((xchecker > 0 || previus.contains(Direction.LEFT)) && !previus.contains(Direction.RIGHT))
      {
        dir = Direction.RIGHT;
      }
      else if (!previus.contains(Direction.LEFT))
      {
        dir = Direction.LEFT;
      }
      else if (previus.contains(Direction.UP))
      {
        dir = Direction.DOWN;
      }
      else
      {
        dir = Direction.UP;
      }
    }
    else
    {
      if ((ychecker > 0 || previus.contains(Direction.UP)) && !previus.contains(Direction.DOWN))
      {
        dir = Direction.DOWN;
      }
      else if (!previus.contains(Direction.UP))
      {
        dir = Direction.UP;
      }
      else if (previus.contains(Direction.LEFT))
      {
        dir = Direction.RIGHT;
      }
      else
      {
        dir = Direction.LEFT;
      }
    }
    if (inters != null)
    {
      newX = inters.getCenterX();
      newY = inters.getCenterY();
      inters = null;
    }

    if (!previus.contains(dir))
    {
      previus.add(dir);
    }
    else
    {
      previus = new ArrayList<>();
    }
  }
  
}
