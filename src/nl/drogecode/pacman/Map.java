package nl.drogecode.pacman;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Map
{
  Group root;
  ArrayList<Shape> shape;

  public Map(Group root)
  {
    this.root = root;
    drawWalls();
  }

  public ArrayList<Shape> getShapeAray()
  {
    return shape;
  }

  private void drawWalls()
  {
    shape = new ArrayList<>();

    shape.add(new Wall(10, 35, 'x', 680));
    shape.add(new Wall(10, 35, 'y', 355));
    shape.add(new Wall(10, 385, 'x', 680));
    shape.add(new Wall(685, 35, 'y', 355));
    shape.add(new Wall(30, 55, 'x', 50, Color.BLUE));
    shape.add(new Wall(30, 55, 'y', 50, Color.AZURE));

    root.getChildren().addAll(shape);
  }
}
