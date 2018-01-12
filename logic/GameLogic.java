package nl.drogecode.pacman.logic;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class GameLogic extends GetSetLogic
{

  public void restart()
  {
    Platform.runLater(new Runnable()
    {
      @Override public void run()
      {
        map.restart();
        man.restart();
        lifes.restart();
        score.restart();
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
    System.out.println(getCoinsLeft());
    return false;
  }

  private boolean showDieAllert()
  {
    Alert a = new Alert(Alert.AlertType.INFORMATION, "You are dead!");
    a.showAndWait();
    return true;
  }

  private boolean showWinAllert()
  {
    Alert a = new Alert(Alert.AlertType.INFORMATION, "You have won");
    a.showAndWait();
    return true;
    /*
     * TODO: hiscore
     */
  }
}
