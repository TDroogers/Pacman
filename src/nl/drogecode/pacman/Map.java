package nl.drogecode.pacman;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import nl.drogecode.pacman.logic.GameLogic;
import nl.drogecode.pacman.objects.Coin;
import nl.drogecode.pacman.objects.Intersection;
import nl.drogecode.pacman.objects.Wall;
import nl.drogecode.pacman.objects.ghosts.Ghost;
import nl.drogecode.pacman.objects.ghosts.OnWallChoiceGhost;
import nl.drogecode.pacman.objects.ghosts.RandomGhost;
import nl.drogecode.pacman.objects.ghosts.XorYGhost;
import nl.drogecode.pacman.text.Score;

public class Map
{
  private Group root;
  private Score score;
  private GameLogic logic;
  private volatile List<Shape> shape;
  private volatile List<Shape> coins;
  private volatile List<Ghost> ghosts;
  private volatile List<Circle> intersection;
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

  public List<Circle> getIntersectionArray()
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

    newWall(12, 35, 'x', 676, Color.BLUE);
    newWall(10, 35, 'y', 355, Color.BLUE);
    newWall(12, 385, 'x', 676, Color.BLUE);
    newWall(685, 35, 'y', 355, Color.BLUE);

    newWall(30, 55, 'x', 50);
    newWall(30, 55, 'y', 100);
    newWall(80, 55, 'y', 100);
    newWall(30, 150, 'x', 170);
    newWall(135, 150, 'y', 25);

    newWall(100, 130, 'x', 80, Color.AQUA);
    newWall(100, 55, 'y', 80, Color.AQUA);
    newWall(100, 55, 'x', 80, Color.AQUA);
    newWall(275, 35, 'y', 25, Color.AQUA);
    newWall(200, 55, 'x', 80, Color.AQUA);

    newWall(125, 75, 'x', 200, Color.GREEN);
    newWall(125, 75, 'y', 40, Color.GREEN);
    newWall(125, 110, 'x', 75, Color.GREEN);
    newWall(200, 110, 'y', 45, Color.GREEN);

    newWall(220, 95, 'x', 135, Color.CORAL);
    newWall(300, 55, 'x', 80, Color.CORAL);
    newWall(350, 55, 'y', 80, Color.CORAL);
    newWall(375, 55, 'y', 80, Color.CORAL);

    newWall(30, 172, 'y', 100, Color.PURPLE);
    newWall(50, 192, 'y', 85, Color.PURPLE);
    newWall(168, 192, 'y', 30, Color.PURPLE);
    newWall(168, 242, 'y', 35, Color.PURPLE);
    newWall(50, 192, 'x', 120, Color.PURPLE);
    newWall(50, 272, 'x', 120, Color.PURPLE);
    newWall(12, 295, 'x', 150, Color.PURPLE);
    newWall(12, 170, 'x', 100, Color.PURPLE);

    newWall(30, 295, 'y', 75, Color.DARKTURQUOISE);
    newWall(50, 315, 'x', 110, Color.DARKTURQUOISE);
    newWall(50, 335, 'x', 110, Color.DARKTURQUOISE);
    newWall(50, 355, 'x', 110, Color.DARKTURQUOISE);
    newWall(50, 375, 'x', 110, Color.DARKTURQUOISE);
    newWall(190, 295, 'x', 150, Color.DARKTURQUOISE);
    newWall(190, 295, 'y', 70, Color.DARKTURQUOISE);
    newWall(340, 295, 'y', 93, Color.DARKTURQUOISE);

    newWall(600, 40, 'y', 30, Color.BROWN);
    newWall(600, 70, 'x', 85, Color.BROWN);
    newWall(600, 350, 'y', 35, Color.BROWN);
    newWall(600, 350, 'x', 85, Color.BROWN);

    root.getChildren().addAll(shape);
  }

  private void drawCoins()
  {
    coins = new ArrayList<>();

    // Row
    drawCoinRow(340, 71, 'y', 2);
    drawCoinRow(137, 100, 'x', 3);
    drawCoinRow(37, 46, 'x', 8);
    drawCoinRow(220, 46, 'x', 5);
    drawCoinRow(365, 66, 'y', 6);
    drawCoinRow(23, 62, 'y', 7);
    drawCoinRow(23, 180, 'y', 8);
    drawCoinRow(23, 305, 'y', 6);
    drawCoinRow(92, 68, 'y', 7);
    drawCoinRow(115, 123, 'x', 6);

    drawCoinRow(55, 308, 'x', 9);
    drawCoinRow(55, 328, 'x', 9);
    drawCoinRow(55, 348, 'x', 9);
    drawCoinRow(55, 368, 'x', 9);

    // square
    drawCoinSquare(62, 205, 9, 6);
    drawCoinSquare(200, 305, 12, 7);

    root.getChildren().addAll(coins);
  }

  private void drawGhosts()
  {
    ghostKiller();
    ghosts = new ArrayList<>();

    ghosts.add(new OnWallChoiceGhost(50, 100, logic)); // This ghost will never reache you
    ghosts.add(new XorYGhost(200, 280, logic));
    ghosts.add(new RandomGhost(250, 350, logic));
    ghosts.add(new OnWallChoiceGhost(450, 350, logic));

    addGhosts();
  }

  private void drawIntersection()
  {
    intersection = new ArrayList<>();

    newIntersection(189, 48);
    newIntersection(93, 46);
    newIntersection(189, 66);
    newIntersection(366, 143);
    newIntersection(184, 231);
    newIntersection(157, 231);
    newIntersection(172, 287);
    newIntersection(41, 287);
    newIntersection(394, 48);
    newIntersection(212, 88);
    newIntersection(125, 182);
    newIntersection(183, 182);
    newIntersection(213, 182);
    newIntersection(292, 65);
    newIntersection(168, 309);
    newIntersection(168, 329);
    newIntersection(168, 349);
    newIntersection(168, 369);
    newIntersection(43, 329);
    newIntersection(43, 349);
    newIntersection(43, 369);

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
      coins.add(new Coin(x, y).getObject());
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

  private void newWall(double x, double y, Character cha, double howFar)
  {
    shape.add(new Wall(x, y, cha, howFar).getObject());
  }

  private void newWall(double x, double y, Character cha, double howFar, Color color)
  {
    shape.add(new Wall(x, y, cha, howFar, color).getObject());
  }

  private void newIntersection(double x, double y)
  {
    intersection.add(new Intersection(x, y).getObject());
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
