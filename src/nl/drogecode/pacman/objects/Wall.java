/**
 * 
 * Non moving object, to show the will's on the map.
 * 
 * The Map will create several Wall's
 * 
 */
package nl.drogecode.pacman.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends BaseObject
{
  private Rectangle wall;

  public Wall(double x, double y, Character direction, double howFar)
  {
    this(x, y, direction, howFar, Color.ANTIQUEWHITE);
  }

  public Wall(double x, double y, Character direction, double howFar, Color color)
  {
    wall = new Rectangle();
    wall.setX(x);
    wall.setY(y);
    wall.setFill(color);
    if (direction.equals('x'))
    {
      wall.setWidth(howFar);
      wall.setHeight(5);
    }
    else if (direction.equals('y'))
    {
      wall.setWidth(5);
      wall.setHeight(howFar);
    }
  }

  public Rectangle getObject()
  {
    return wall;
  }
}
