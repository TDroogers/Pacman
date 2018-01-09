package nl.drogecode.pacman.logic;

import javafx.application.Platform;

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
          restart();
        }
        map.loseLife();
        man.restart();
      }
    });
    return true;
  }
}
