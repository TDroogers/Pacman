package nl.drogecode.pacman;

import javafx.application.Platform;

public class GameLogic
{
  Map map;
  Man man;
  Score score;

  public GameLogic(Map map, Man man, Score score)
  {
    this.map = map;
    this.man = man;
    this.score = score;
  }

  public void restart()
  {
    Platform.runLater(new Runnable()
    {
      @Override public void run()
      {
        map.restart();
        man.restart();
        score.restart();
      }
    });
  }
}
