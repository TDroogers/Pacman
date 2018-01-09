package nl.drogecode.pacman.text;

public class Lifes extends BaseText
{
  public Lifes()
  {
    super();
    setX(610);
    setY(62);
    setText("3");
  }
  
  public void restart()
  {
    setText("3");
  }
}