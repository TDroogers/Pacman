package nl.drogecode.pacman.objects.ghosts;

import java.util.ArrayList;

import nl.drogecode.pacman.logic.GameLogic;

public class AstarGhost extends Ghost
{
  ArrayList<Object> routMap;
  int distanceCounter, currentCounter;
  private int manIntersection = -2;

  public AstarGhost(double x, double y, GameLogic logic)
  {
    super(x, y, logic);
  }

  protected void findMan()
  {
    Integer newManIntersection = logic.man.getLastIntersectionId();
    if (manIntersection == newManIntersection)
    {
      return;
    }
    manIntersection = newManIntersection;
    distanceCounter = 0;
    currentCounter = 0;

    while (distanceCounter <= 1000)
    {
      runSelfDemandingLoop();
      distanceCounter++;
    }
  }

  @Override protected void beforeLoop()
  {
    if (routMap == null)
    {
      findMan();
    }
  }

  @Override protected void afterBumb()
  {}

  @Override protected void noBumb()
  {}

  private void runSelfDemandingLoop()
  {
    currentCounter++;
    System.out.println("infanateLoop" + distanceCounter + " : " + currentCounter);
    if (distanceCounter > currentCounter)
    {
      runSelfDemandingLoop();
    }
    currentCounter--;
  }
}
