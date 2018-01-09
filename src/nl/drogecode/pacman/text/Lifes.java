package nl.drogecode.pacman.text;

public class Lifes extends BaseText
{
  private int current;
  private static final int START = 3;
  public Lifes()
  {
    super();
    setX(610);
    setY(62);
    restart();
  }
  
  public void restart()
  {
    current = START;
    setText(String.valueOf(current));
  }
  
  public boolean loseLife()
  {
    current --;
    System.out.println(current);
    if (current <= 0)
    {
      return false;
    }
    setText(String.valueOf(current));
    return true;
  }
}