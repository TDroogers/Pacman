package nl.drogecode.pacman;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import nl.drogecode.pacman.logic.GameLogic;
import nl.drogecode.pacman.objects.Coin;
import nl.drogecode.pacman.objects.Intersection;
import nl.drogecode.pacman.objects.Wall;
import nl.drogecode.pacman.objects.ghosts.Ghost;
import nl.drogecode.pacman.objects.ghosts.OnWallChoiceGhost;
import nl.drogecode.pacman.text.Score;

public class Map
{
  private Group root;
  private Score score;
  private GameLogic logic;
  private volatile List<Shape> shape;
  private volatile List<Shape> coins;
  private volatile List<Ghost> ghosts;
  private volatile List<Intersection> intersection;
  private volatile int scoreCounter;

  public Map(Group root, Score score, GameLogic logic)
  {
    this.root = root;
    this.score = score;
    this.logic = logic;
    restart();
  }

  public void restart()
  {
    scoreCounter = 0;
    if (coins != null)
    {
      root.getChildren().removeAll(coins);
    }
    drawCoins();

    loseLife();
  }

  public boolean loseLife()
  {
    if (shape != null)
    {
      root.getChildren().removeAll(shape);
    }
    drawWalls();
    drawGhosts();
    drawIntersection();
    return true;
  }

  public List<Shape> getShapeArray()
  {
    return shape;
  }

  public List<Shape> getCoinsArray()
  {
    return coins;
  }

  public List<Ghost> getGhostsArray()
  {
    return ghosts;
  }

  public List<Intersection> getIntersectionArray()
  {
    return intersection;
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
     * Map size: x = 700 y = 400 menu takes 25
     */
    shape = new ArrayList<>();

    shape.add(new Wall(12, 35, 'x', 676, Color.BLUE));
    shape.add(new Wall(10, 35, 'y', 355, Color.BLUE));
    shape.add(new Wall(12, 385, 'x', 676, Color.BLUE));
    shape.add(new Wall(685, 35, 'y', 355, Color.BLUE));

    shape.add(new Wall(30, 55, 'x', 50, Color.ORANGE));
    shape.add(new Wall(30, 55, 'y', 100, Color.ORANGE));
    shape.add(new Wall(80, 55, 'y', 100, Color.ORANGE));
    shape.add(new Wall(30, 150, 'x', 170, Color.ORANGE));
    shape.add(new Wall(135, 150, 'y', 25, Color.ORANGE));

    shape.add(new Wall(100, 130, 'x', 80, Color.AQUA));
    shape.add(new Wall(100, 55, 'y', 80, Color.AQUA));
    shape.add(new Wall(100, 55, 'x', 80, Color.AQUA));
    shape.add(new Wall(275, 35, 'y', 25, Color.AQUA));
    shape.add(new Wall(200, 55, 'x', 80, Color.AQUA));

    shape.add(new Wall(125, 75, 'x', 200, Color.GREEN));
    shape.add(new Wall(125, 75, 'y', 40, Color.GREEN));
    shape.add(new Wall(125, 110, 'x', 75, Color.GREEN));
    shape.add(new Wall(200, 110, 'y', 45, Color.GREEN));

    shape.add(new Wall(220, 95, 'x', 135, Color.CORAL));
    shape.add(new Wall(300, 55, 'x', 80, Color.CORAL));
    shape.add(new Wall(350, 55, 'y', 80, Color.CORAL));
    shape.add(new Wall(375, 55, 'y', 80, Color.CORAL));

    shape.add(new Wall(30, 172, 'y', 100, Color.PURPLE));
    shape.add(new Wall(50, 192, 'y', 85, Color.PURPLE));
    shape.add(new Wall(168, 192, 'y', 30, Color.PURPLE));
    shape.add(new Wall(168, 242, 'y', 35, Color.PURPLE));
    shape.add(new Wall(50, 192, 'x', 120, Color.PURPLE));
    shape.add(new Wall(50, 272, 'x', 120, Color.PURPLE));
    shape.add(new Wall(12, 295, 'x', 150, Color.PURPLE));
    shape.add(new Wall(12, 170, 'x', 100, Color.PURPLE));

    shape.add(new Wall(30, 295, 'y', 75, Color.DARKTURQUOISE));
    shape.add(new Wall(50, 315, 'x', 110, Color.DARKTURQUOISE));
    shape.add(new Wall(50, 335, 'x', 110, Color.DARKTURQUOISE));
    shape.add(new Wall(50, 355, 'x', 110, Color.DARKTURQUOISE));
    shape.add(new Wall(50, 375, 'x', 110, Color.DARKTURQUOISE));
    shape.add(new Wall(190, 295, 'x', 150, Color.DARKTURQUOISE));
    shape.add(new Wall(190, 295, 'y', 70, Color.DARKTURQUOISE));
    shape.add(new Wall(340, 295, 'y', 93, Color.DARKTURQUOISE));

    shape.add(new Wall(600, 40, 'y', 30, Color.BROWN));
    shape.add(new Wall(600, 70, 'x', 85, Color.BROWN));
    shape.add(new Wall(600, 350, 'y', 35, Color.BROWN));
    shape.add(new Wall(600, 350, 'x', 85, Color.BROWN));

    root.getChildren().addAll(shape);
  }

  private void drawCoins()
  {
    coins = new ArrayList<>();

    // Row
    drawCoinRow(340, 71, 'y', 2);
    // drawCoinRow(137, 100, 'x', 3);
    // drawCoinRow(37, 46, 'x', 8);
    // drawCoinRow(220, 46, 'x', 5);
    // drawCoinRow(365, 66, 'y', 6);
    // drawCoinRow(23, 62, 'y', 7);
    // drawCoinRow(23, 180, 'y', 8);
    // drawCoinRow(23, 305, 'y', 6);
    // drawCoinRow(92, 68, 'y', 7);
    // drawCoinRow(115, 123, 'x', 6);
    //
    // drawCoinRow(55, 308, 'x', 9);
    // drawCoinRow(55, 328, 'x', 9);
    // drawCoinRow(55, 348, 'x', 9);
    // drawCoinRow(55, 368, 'x', 9);

    // square
    // drawCoinSquare(62, 205, 9, 6);
    // drawCoinSquare(200, 305, 12, 7);

    root.getChildren().addAll(coins);
  }

  private void drawGhosts()
  {
    ghostKiller();
    ghosts = new ArrayList<>();

    // ghosts.add(new OnWallChoiceGhost(50, 100, logic)); // This ghost will never reache you
    // ghosts.add(new XorYGhost(200, 280, logic));
    // ghosts.add(new RandomGhost(250, 350, logic));
    ghosts.add(new OnWallChoiceGhost(450, 350, logic));

    addGhosts();
  }

  private void drawIntersection()
  {
    intersection = new ArrayList<>();

    intersection.add(new Intersection(189, 48));
    intersection.add(new Intersection(93, 46));
    intersection.add(new Intersection(189, 66));
    intersection.add(new Intersection(366, 143));
    intersection.add(new Intersection(184, 231));
    intersection.add(new Intersection(157, 231));
    intersection.add(new Intersection(172, 287));
    intersection.add(new Intersection(40, 287));
    intersection.add(new Intersection(394, 48));
    intersection.add(new Intersection(212, 88));
    intersection.add(new Intersection(126, 184));
    intersection.add(new Intersection(183, 183));
    intersection.add(new Intersection(292, 65));
    intersection.add(new Intersection(168, 309));
    intersection.add(new Intersection(168, 329));
    intersection.add(new Intersection(168, 349));
    intersection.add(new Intersection(168, 369));
    intersection.add(new Intersection(43, 329));
    intersection.add(new Intersection(43, 349));
    intersection.add(new Intersection(43, 369));

    root.getChildren().addAll(intersection);
  }

  private void drawCoinSquare(double x, double y, int xCount, int yCount)
  {
    for (int i = 0; i < xCount; i++)
    {
      drawCoinRow(x, y, 'y', yCount);
      x += 12;
    }
  }

  private void drawCoinRow(double x, double y, Character c, int count)
  {
    for (int i = 0; i < count; i++)
    {
      logic.setCoinsLeft((byte) 0);
      coins.add(new Coin(x, y));
      switch (c)
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

  private boolean ghostKiller()
  {
    if (ghosts == null)
    {
      return false;
    }
    for (Ghost ghost : ghosts)
    {
      root.getChildren().remove(ghost.getObject());
      ghost.setWalking(false);
    }
    return true;
  }

  private boolean addGhosts()
  {
    for (Ghost ghost : ghosts)
    {
      root.getChildren().add(ghost.getObject());
    }
    return true;
  }
}
