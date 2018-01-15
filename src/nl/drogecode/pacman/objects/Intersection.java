package nl.drogecode.pacman.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Intersection extends Circle
{
  public Intersection(double x, double y)
  {
    setFill(Color.RED);
    setCenterX(x);
    setCenterY(y);
    setRadius(2);
  }
}
