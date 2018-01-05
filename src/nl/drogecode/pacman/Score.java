package nl.drogecode.pacman;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Score extends Text
{
  public Score()
  {
    setFont(new Font(20));
    setX(610);
    setY(375);
    setText("0");
    setFill(Color.WHITE);
  }
  
  public void restart()
  {
    setText("0");
  }
}
