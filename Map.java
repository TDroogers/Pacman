package nl.drogecode.pacman;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Map
{
  Group root;
  Score score;
  ArrayList<Shape> shape;
  ArrayList<Shape> coins;
  int scoreCounter = 0;

  public Map(Group root, Score score)
  {
    this.root = root;
    this.score = score;
    drawWalls();
    drawCoins();
  }

  public ArrayList<Shape> getShapeArray()
  {
    return shape;
  }
  
  public ArrayList<Shape> getCoinsArray()
  {
    return coins;
  }
  
  public void remove(Shape shape)
  {
    remove(shape, 0);
  }
  
  public void remove(Shape shape, int newPoint)
  {
    scoreCounter += newPoint;
    Platform.runLater(new Runnable()
    {
      @Override public void run()
      {
        root.getChildren().remove(shape);
        score.setText(String.valueOf(scoreCounter));
      }
    });
  }

  private void drawWalls()
  {
    /*
     * Map size:
     * x = 700
     * y = 400
     * menu takes 25
     */
    shape = new ArrayList<>();

    shape.add(new Wall(10, 35, 'x', 680));
    shape.add(new Wall(10, 35, 'y', 355));
    shape.add(new Wall(10, 385, 'x', 680));
    shape.add(new Wall(685, 35, 'y', 355));
    
    shape.add(new Wall(30, 55, 'x', 50));
    shape.add(new Wall(30, 55, 'y', 50));
    shape.add(new Wall(80, 55, 'y', 80));
    
    shape.add(new Wall(100, 55, 'y', 80));
    shape.add(new Wall(100, 55, 'x', 80));
    shape.add(new Wall(200, 55, 'x', 80));
    shape.add(new Wall(150, 75, 'x', 80));
    
    shape.add(new Wall(600, 350, 'y', 35, Color.BROWN));
    shape.add(new Wall(600, 350, 'x', 85, Color.BROWN));

    root.getChildren().addAll(shape);
  }
  
  private void drawCoins()
  {
    coins = new ArrayList<>();
    
    coins.add(new Coin(23, 48));
    coins.add(new Coin(23, 58));
    coins.add(new Coin(23, 68));
    coins.add(new Coin(23, 78));
    coins.add(new Coin(23, 88));
    coins.add(new Coin(23, 98));
    coins.add(new Coin(23, 108));
    
    coins.add(new Coin(33, 48));
    coins.add(new Coin(43, 48));
    coins.add(new Coin(53, 48));
    coins.add(new Coin(63, 48));
    coins.add(new Coin(73, 48));
    coins.add(new Coin(83, 48));
    coins.add(new Coin(93, 48));
    
    root.getChildren().addAll(coins);
  }
}
