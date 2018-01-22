/**
 * 
 */
package nl.drogecode.pacman.objects.ghosts;

import java.util.ArrayList;

import nl.drogecode.pacman.enums.Direction;
import nl.drogecode.pacman.enums.GhostType;
import nl.drogecode.pacman.logic.GameLogic;
import nl.drogecode.pacman.logic.pathfinder.ManFinder;

public abstract class SmartGhost extends Ghost
{

  private ManFinder manFinder;
  private ArrayList<Direction> walker;

  public SmartGhost(double x, double y, GameLogic logic, GhostType type)
  {
    super(x, y, logic);
    walker = new ArrayList<>();
    manFinder = new ManFinder(this, logic, type);
    manFinder.setDaemon(true);
  }

  public double getSbgX()
  {
    return x;
  }

  public double getSbgY()
  {
    return y;
  }

  public Direction getDir()
  {
    return dir;
  }

  protected void findMan()
  {
    ArrayList<Direction> tempWalker = new ArrayList<>(manFinder.getWalker());
    if (!tempWalker.isEmpty())
    {
      walker = tempWalker;
      intersectionId = -1;
    }
    else if (walker.isEmpty())
    {
      walker.add(findManShort());
    }

  }

  protected void nextDir()
  {
    if (walker.size() == 0)
    {
      findMan();
    }
    dir = walker.get(0);
    walker.remove(0);
  }

  @Override protected void beforeLoop()
  {
    manFinder.start();
    findMan();
    nextDir();
  }

  @Override protected void afterBumb()
  {
    findMan();
    nextDir();
    if (inters != null)
    {
      newX = inters.getCenterX();
      newY = inters.getCenterY();
      inters = null;
    }
  }

  @Override protected void noBumb()
  {}
}
