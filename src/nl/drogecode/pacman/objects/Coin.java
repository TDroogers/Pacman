package nl.drogecode.pacman.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Coin extends BaseObject
{
  private Circle coin;

  public Coin(double x, double y)
  {
    coin = new Circle();
    coin.setFill(Color.GOLD);
    coin.setCenterX(x);
    coin.setCenterY(y);
    coin.setRadius(3);
  }

  @Override public Circle getObject()
  {
    return coin;
  }
}
