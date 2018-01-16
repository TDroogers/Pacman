package nl.drogecode.pacman.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Intersection extends BaseObject
{
  private Circle inters;

  public Intersection(double x, double y)
  {
    inters = new Circle();
    inters.setFill(Color.RED);
    inters.setCenterX(x);
    inters.setCenterY(y);
    inters.setRadius(1);
  }

  @Override public Circle getObject()
  {
    return inters;
  }
}
