/**
 * 
 * If some ghost's hit this object it will recalculate it's direction.
 * 
 */
package nl.drogecode.pacman.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Intersection extends BaseObject
{
  private int id;
  private Circle inters;

  public Intersection(int id, double x, double y)
  {
    this(id, x, y, 0, 0, 0, 0);
  }

  public Intersection(int id, double x, double y, int up, int down, int left, int right)
  {
    this.id = id;

    inters = new Circle();
    inters.setFill(Color.RED);
    inters.setCenterX(x);
    inters.setCenterY(y);
    inters.setRadius(2);
  }

  public int getID()
  {
    return id;
  }

  @Override public Circle getObject()
  {
    return inters;
  }
}
