package nl.drogecode.pacman;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Map
{
  Group root;

  public Map(Group root)
  {
    this.root = root;
    drawWalls();
  }

  private void drawWalls()
  {
    Wall wall1 = new Wall();
    Wall wall2 = new Wall();
    Wall wall3 = new Wall();
    Wall wall4 = new Wall();
    Wall wall5 = new Wall();
    Wall wall6 = new Wall();
    
    wall1.setWall(25, 50, 'x', 100);
    wall2.setWall(25, 50, 'y', 100);
    wall3.setWall(50, 70, 'x', 50, Color.BLUE);
    wall4.setWall(50, 70, 'y', 50);
    wall5.setWall(85, 50, 'x', 50);
    wall6.setWall(85, 100, 'y', 50);

    root.getChildren().add(wall1);
    root.getChildren().add(wall2);
    root.getChildren().add(wall3);
    root.getChildren().add(wall4);
    root.getChildren().add(wall5);
    root.getChildren().add(wall6);
  }
}
