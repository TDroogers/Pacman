/**
 * 
 * This simpel ghost goes the shortest way to the man, it will not go arround wall's.
 * 
 */
package nl.drogecode.pacman.objects.ghosts;

import nl.drogecode.pacman.logic.GameLogic;

public class XorYGhost extends Ghost
{

  public XorYGhost(double x, double y, GameLogic logic)
  {
    super(x, y, logic);
  }

  @Override protected void walker()
  {
    dir = findManShort();
    super.walker();
  }

  @Override protected void afterBumb()
  {}

  @Override protected void beforeLoop()
  {}

  @Override protected void noBumb()
  {}

}
