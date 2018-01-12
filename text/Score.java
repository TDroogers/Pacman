package nl.drogecode.pacman.text;

public class Score extends BaseText
{
  public Score()
  {
    super();
    setX(610);
    setY(375);
    setText("0");
  }
  
  public void restart()
  {
    setText("0");
  }
}