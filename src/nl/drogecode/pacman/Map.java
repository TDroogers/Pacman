package nl.drogecode.pacman;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Map
{
  private volatile Group root;
  private volatile Score score;
  private volatile ArrayList<Shape> shape;
  private volatile ArrayList<Shape> coins;
  private volatile ArrayList<Shape> ghosts;
  private volatile int scoreCounter;

  public Map(Group root, Score score)
  {
    this.root = root;
    this.score = score;
    restart();
  }
  
  public void restart()
  {
    scoreCounter = 0;
    if (coins != null)
    {
      root.getChildren().removeAll(coins);
      root.getChildren().removeAll(shape);
      root.getChildren().removeAll(ghosts);
    }
    drawWalls();
    drawCoins();
    drawGhosts();
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
    shape.add(new Wall(30, 55, 'y', 100));
    shape.add(new Wall(80, 55, 'y', 100));
    shape.add(new Wall(30, 150, 'x', 170));
    shape.add(new Wall(100, 130, 'x', 80));
    
    shape.add(new Wall(100, 55, 'y', 80));
    shape.add(new Wall(100, 55, 'x', 80));
    shape.add(new Wall(200, 55, 'x', 80));
    shape.add(new Wall(125, 75, 'x', 200));
    shape.add(new Wall(125, 75, 'y', 40));
    shape.add(new Wall(125, 110, 'x', 75));
    shape.add(new Wall(200, 110, 'y', 45));
    
    shape.add(new Wall(10, 170, 'x', 100));
    shape.add(new Wall(30, 170, 'y', 100));
    
    shape.add(new Wall(600, 350, 'y', 35, Color.BROWN));
    shape.add(new Wall(600, 350, 'x', 85, Color.BROWN));

    root.getChildren().addAll(shape);
  }
  
  private void drawCoins()
  {
    coins = new ArrayList<>();
    
    drawCoinRow(37, 46, 'x', 7);
    drawCoinRow(23, 62, 'y', 7);
    drawCoinRow(23, 180, 'y', 7);
    drawCoinRow(92, 68, 'y', 6);
    drawCoinRow(115, 123, 'x', 5);
    
    root.getChildren().addAll(coins);
  }
  
  private void drawGhosts()
  {
    ghosts = new ArrayList<>();
    
    ghosts.add(new Ghost(50, 100));
    
    root.getChildren().addAll(ghosts);
  }
  
  private void drawCoinRow(double x, double y, Character c, int count)
  {
    for (int i = 0; i<=count;i++)
    {
      coins.add(new Coin(x, y));
      switch(c)
      {
        case 'x':
          x = x + 12;
          break;
          
        case 'y':
          y = y + 12;
          break;
      }
    }
  }
}
