package nl.drogecode.pacman.logic;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

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
        score.restart();
      }
    });
  }
}
