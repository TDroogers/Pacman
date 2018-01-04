package nl.drogecode.pacman;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Controll
{

  Stage stage;
  Man man;

  public Controll(Stage stage, Man man)
  {
    this.stage = stage;
    this.man = man;
    keyListner();
  }

  private void keyListner()
  {
    stage.getScene().setOnKeyPressed(e ->
    {
      setKey(e.getCode());
    });
    stage.getScene().setOnKeyReleased(e ->
    {
      setRelease(e.getCode());
    });
  }

  private void setKey(KeyCode keyCode)
  {
    switch (keyCode)
    {
      case UP:
        man.setDirection(1);
        break;
        
      case RIGHT:
        man.setDirection(2);
        break;
        
      case DOWN:
        man.setDirection(3);
        break;
        
      case LEFT:
        man.setDirection(4);
        break;

      default:
        break;
    }
  }

  private void setRelease(KeyCode keyCode)
  {

  }
}
