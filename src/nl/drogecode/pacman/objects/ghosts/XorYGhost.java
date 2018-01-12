package nl.drogecode.pacman.objects.ghosts;

import nl.drogecode.pacman.logic.GameLogic;

public class XorYGhost extends Ghost
{

  public XorYGhost(double x, double y, GameLogic logic)
  {
    super(x, y, logic);
  }
  
  protected void walker()
  {
    double xchecker = logic.getXMan() - ghost.getCenterX();
    double ychecker = logic.getYMan() - ghost.getCenterY();
    
    if (Math.abs(xchecker) >= Math.abs(ychecker))
    {
      if (xchecker > 0)
      {
        newX = x + SPEED*0.75;
      }
      else
      {
        newX = x - SPEED*0.75;
      }
    }
    else
    {
      if (ychecker > 0)
      {
        newY = y + SPEED*0.75;
      }
      else
      {
        newY = y - SPEED*0.75;
      }
    }
    
  }
  
}
