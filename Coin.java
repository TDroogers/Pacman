package nl.drogecode.pacman;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Coin extends Circle
{
  public Coin(double x, double y)
  {
    setFill(Color.GOLD);
    setCenterX(x);
    setCenterY(y);
    setRadius(3);
  }
}