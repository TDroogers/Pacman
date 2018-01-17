package nl.drogecode.pacman.objects.ghosts;

import nl.drogecode.pacman.enums.Direction;
import nl.drogecode.pacman.logic.GameLogic;

public class XorYGhost extends Ghost
{

  public XorYGhost(double x, double y, GameLogic logic)
  {
    super(x, y, logic);
  }

  @Override protected void walker()
  {
    findMan();
    super.walker();
  }

  protected void findMan()
  {
    double xchecker = logic.man.getXman() - ghost.getCenterX();
    double ychecker = logic.man.getYman() - ghost.getCenterY();

    if (Math.abs(xchecker) >= Math.abs(ychecker))
    {
      if (xchecker > 0)
      {
        dir = Direction.RIGHT;
      }
      else
      {
        dir = Direction.LEFT;
      }
    }
    else
    {
      if (ychecker > 0)
      {
        dir = Direction.DOWN;
      }
      else
      {
        dir = Direction.UP;
      }
    }
  }

  @Override protected void afterBumb()
  {}

  @Override protected void beforeLoop()
  {}

  @Override protected void noBumb()
  {}

}
