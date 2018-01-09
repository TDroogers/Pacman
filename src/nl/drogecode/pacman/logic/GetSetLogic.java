package nl.drogecode.pacman.logic;

import java.util.ArrayList;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import nl.drogecode.pacman.Map;
import nl.drogecode.pacman.objects.Man;
import nl.drogecode.pacman.text.Lifes;
import nl.drogecode.pacman.text.Score;

public class GetSetLogic
{
  protected Stage stage;
  protected Map map;
  protected Man man;
  protected Score score;
  protected Lifes lifes;
  protected boolean ready;
  protected volatile int coinsLeft;

  public void setStuff(Stage stage, Map map, Man man, Score score, Lifes lifes)
  {
    this.stage = stage;
    this.map = map;
    this.man = man;
    this.score = score;
    this.lifes = lifes;
    ready = true;
  }

  public int getCoinsLeft()
  {
    return coinsLeft;
  }

  public boolean setCoinsLeft(byte coin)
  {
    switch (coin)
    {
      case 0:
        coinsLeft++;
        break;

      case 1:
        coinsLeft--;
        break;

      default:
        return false;
    }
    return true;
  }

  public boolean getReady()
  {
    return ready;
  }

  /*
   * Scene
   */

  public double getSceneWidth()
  {
    return stage.getScene().getWidth();
  }

  public double getSceneHight()
  {
    return stage.getScene().getHeight();
  }

  /*
   * Man
   */

  public Circle getMan()
  {
    return man.getMan();
  }

  public double getXMan()
  {
    return man.getXman();
  }

  public double getYMan()
  {
    return man.getYman();
  }

  /*
   * Map
   */

  public ArrayList<Shape> getCoinArray()
  {
    return map.getCoinsArray();
  }

  public ArrayList<Shape> getWallArray()
  {
    return map.getShapeArray();
  }

  public boolean removeCoin(Shape coin)
  {
    map.remove(coin, 1);
    return true;
  }
}
