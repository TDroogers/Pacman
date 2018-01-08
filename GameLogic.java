package nl.drogecode.pacman;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class GameLogic
{
  Map map;
  Man man;
  Score score;

  public void setStuff(Map map, Man man, Score score)
  {
    this.map = map;
    this.man = man;
    this.score = score;
  }

  public void restart()
  {
    Platform.runLater(new Runnable()
    {
      @Override public void run()
      {
        map.restart();
        man.restart();
        score.restart();
      }
    });
  }
  
  public boolean checkBumpBorder(double newX, double maxX, double newY, double maxY)
  {
    // Has to be side specific, but fine for now.
    if (newX > maxX || newX < 0 || newY > maxY || newY < 25)
    {
      return false;
    }
    return true;
  }
  
  public boolean checkBumpWall(Circle a, double newX, double newY)
  {

    Circle clone = new Circle();
    clone.setCenterX(newX);
    clone.setCenterY(newY);
    clone.setRadius(a.getRadius());
    ArrayList<Shape> shapes = map.getShapeArray();

    for (Shape shape : shapes)
    {
      if (clone.getBoundsInParent().intersects(shape.getBoundsInParent()))
      {
        return false;
      }
    }
    return true;
  }
}
