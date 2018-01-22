/**
 * 
 * This ghost will follow you and try's to get behind you.
 * 
 */
package nl.drogecode.pacman.objects.ghosts;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import nl.drogecode.pacman.enums.Direction;
import nl.drogecode.pacman.logic.GameLogic;
import nl.drogecode.pacman.logic.pathfinder.ManFinder;

public class SmartBehindGhost extends Ghost
{
  private ManFinder manFinder;
  private ArrayList<Direction> walker;

  public SmartBehindGhost(double x, double y, GameLogic logic)
  {
    super(x, y, logic);
    walker = new ArrayList<>();
    manFinder = new ManFinder(this, logic);
    manFinder.setDaemon(true);
    ghost.setFill(Color.RED);
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
