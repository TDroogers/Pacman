package nl.drogecode.pacman.logic;

import java.util.ArrayList;

import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import nl.drogecode.pacman.Map;
import nl.drogecode.pacman.Score;
import nl.drogecode.pacman.objects.Man;

public class GetSetLogic
{
  Stage stage;
  Map map;
  Man man;
  Score score;
  boolean ready;

  public void setStuff(Stage stage, Map map, Man man, Score score)
  {
    this.stage = stage;
    this.map = map;
    this.man = man;
    this.score = score;
    ready = true;
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
