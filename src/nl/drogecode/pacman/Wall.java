package nl.drogecode.pacman;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends Rectangle
{
  public void setWall(double x, double y, Character direction, double howFar)
  {
    setWall(x, y, direction, howFar, Color.GRAY);
  }

  public void setWall(double x, double y, Character direction, double howFar, Color color)
  {
    setX(x);
    setY(y);
    setFill(color);
    if (direction.equals('x'))
    {
      setWidth(howFar);
      setHeight(5);
    }
    else if(direction.equals('y'))
    {
      setWidth(5);
      setHeight(howFar);
    }
  }
}
