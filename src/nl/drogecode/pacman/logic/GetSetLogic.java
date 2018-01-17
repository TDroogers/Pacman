package nl.drogecode.pacman.logic;

import javafx.stage.Stage;
import nl.drogecode.pacman.Map;
import nl.drogecode.pacman.objects.Man;
import nl.drogecode.pacman.text.Lifes;
import nl.drogecode.pacman.text.Score;

public class GetSetLogic
{
  public Stage stage;
  public Map map;
  public Man man;
  protected Score score;
  protected Lifes lifes;
  protected WakeUp wakeUp;
  protected boolean ready;
  protected volatile int coinsLeft;

  public void setStuff(Stage stage, Map map, Man man, Score score, Lifes lifes)
  {
    this.stage = stage;
    this.map = map;
    this.man = man;
    this.score = score;
    this.lifes = lifes;
    this.wakeUp = new WakeUp();
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
   * Wake up a thread
   */
  public void setWakeUp(Thread thread)
  {
    wakeUp.setNewThread(thread);
  }

  public void setStopper(boolean stop)
  {
    wakeUp.setStopper(stop);
  }
}
