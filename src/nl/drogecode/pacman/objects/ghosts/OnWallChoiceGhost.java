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

    switch (dir)
    {
      case UP:
        mirror = Direction.DOWN;
        break;

      case DOWN:
        mirror = Direction.UP;
        break;

      case LEFT:
        mirror = Direction.RIGHT;
        break;

      case RIGHT:
        mirror = Direction.LEFT;
        break;
    }

    previus.add(mirror);
  }

  protected void findMan()
  {
    super.findMan();
    if (!previus.contains(dir))
    {
      previus.add(dir);
    }
    else
    {
      previus = new ArrayList<>();
    }
    System.out.println(mirror + " " + dir + " : " + previus);
  }
}
