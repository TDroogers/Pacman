/**
 * 
 * This object will set every disicion point on the roadmap. 
 * 
 */
package nl.drogecode.pacman.logic.pathfinder;

import nl.drogecode.pacman.enums.Direction;

public class SingleDecisionPoint
{
  private SingleDecisionPoint up, down, left, right;
  private int intersectionId;
  private double x, y;
  private boolean end, fullStop;

  public SingleDecisionPoint()
  {}

  /*
   * setters
   */
  protected void setPath(Direction dir)
  {
    switch (dir)
    {
      case UP:
        up = new SingleDecisionPoint();
        break;

      case DOWN:
        down = new SingleDecisionPoint();
        break;

      case LEFT:
        left = new SingleDecisionPoint();
        break;

      case RIGHT:
        right = new SingleDecisionPoint();
        break;
    }
  }

  protected void setIntersectionId(int newIntId)
  {
    intersectionId = newIntId;
  }

  protected void setPoint(double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  protected void setEnd(boolean end)
  {
    this.end = end;
  }

  /*
   * getters
   */
  protected SingleDecisionPoint getPath(Direction dir)
  {
    switch (dir)
    {
      case UP:
        return up;

      case DOWN:
        return down;

      case LEFT:
        return left;

      case RIGHT:
        return right;
    }
    /*
     * will never be reached, but the compiler requires it.
     */
    return null;
  }

  protected int getIntersectionId()
  {
    return intersectionId;
  }

  protected double getX()
  {
    return x;
  }

  protected double getY()
  {
    return y;
  }

  protected boolean getEnd()
  {
    return end;
  }

  protected boolean getFullStop()
  {
    return fullStop;
  }

  /*
   * unset direction
   */
  protected void unsetSingleNextPath(Direction dir)
  {
    switch (dir)
    {
      case UP:
        up = null;
        break;

      case DOWN:
        down = null;
        break;

      case LEFT:
        left = null;
        break;

      case RIGHT:
        right = null;
        break;
    }
  }

  protected void unsetAllNextPath()
  {
    up = down = left = right = null;
    end = true;
    fullStop = true;
  }
}
