/**
 * 
 * This function is the bridge between the different function's
 * 
 */

package nl.drogecode.pacman.logic;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import nl.drogecode.pacman.objects.BaseObject;
import nl.drogecode.pacman.objects.NpcObject;

public class GameLogic extends GetSetLogic
{

  public void restart()
  {
    Platform.runLater(new Runnable()
    {
      @Override public void run()
      {
        man.restart();
        lifes.restart();
        score.restart();
        map.restart();
      }
    });
  }

  public boolean loseLife()
  {
    Platform.runLater(new Runnable()
    {
      @Override public void run()
      {
        if (!lifes.loseLife())
        {
          man.setDirection(0);
          showDieAllert();
          restart();
        }
        map.loseLife();
        man.restart();
      }
    });
    return true;
  }

  public boolean checkWin()
  {
    if (getCoinsLeft() <= 0)
    {
      Platform.runLater(new Runnable()
      {
        @Override public void run()
        {
          man.setDirection(0);
          showWinAllert();
          restart();
        }
      });
      return true;
    }
    return false;
  }

  private boolean showDieAllert()
  {
    showAllert("You are dead!");
    return true;
  }

  private boolean showWinAllert()
  {
    showAllert("You have won");
    return true;
    /*
     * TODO: hiscore
     */
  }

  private boolean showAllert(String message)
  {
    holdGhosts();
    System.gc();
    Alert a = new Alert(Alert.AlertType.INFORMATION, message);
    a.showAndWait();
    return true;
  }

  private void holdGhosts()
  {
    for (BaseObject ghost : map.getGhostsArray())
    {
      ((NpcObject) ghost).setWalking(false);
    }
  }
}
