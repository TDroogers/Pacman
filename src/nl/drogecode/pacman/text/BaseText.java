package nl.drogecode.pacman.text;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BaseText extends Text
{

  public BaseText()
  {
    setFont(new Font(20));
    setFill(Color.WHITE);
  }
}