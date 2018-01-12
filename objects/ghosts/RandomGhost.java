package nl.drogecode.pacman.objects.ghosts;

import java.util.Random;

import nl.drogecode.pacman.enums.Direction;
import nl.drogecode.pacman.logic.GameLogic;

public class RandomGhost extends Ghost
{
  Direction dir;
  public RandomGhost(double x, double y, GameLogic logic)
  {
    super(x, y, logic);
  }

  @Override protected void walker()
  {
    switch(dir)
    {
      case UP:
        newY = y + GSPEED;
        break;
        
      case DOWN:
        newY = y - GSPEED;
        break;
        
      case RIGHT:
        newX = x + GSPEED;
        break;
        
      case LEFT:
        newX = x - GSPEED;
        break;
    }
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
}
