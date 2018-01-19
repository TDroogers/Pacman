/**
 * 
 * On every wall or intersection point it will choce a random directon.
 * 
 */
package nl.drogecode.pacman.objects.ghosts;

import java.util.Random;

import nl.drogecode.pacman.enums.Direction;
import nl.drogecode.pacman.logic.GameLogic;

public class RandomGhost extends Ghost
{
  public RandomGhost(double x, double y, GameLogic logic)
  {
    super(x, y, logic);
  }

  @Override protected void afterBumb()
  {
    randomDirection();
  }

  @Override protected void beforeLoop()
  {
    randomDirection();
  }

  private void randomDirection()
  {
    dir = Direction.values()[new Random().nextInt(Direction.values().length)];
  }

  @Override protected void noBumb()
  {}
}
