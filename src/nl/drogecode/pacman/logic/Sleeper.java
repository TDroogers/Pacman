package nl.drogecode.pacman.logic;

public class Sleeper
{
  public boolean sleeper()
  {
    return sleeper(30L);
  }

  public boolean sleeper(int i)
  {
    return sleeper(Long.valueOf(i));
  }

  public boolean sleeper(Long time)
  {
    try
    {
      Thread.sleep(time); // 1000 milliseconds is one second.
    }
    catch (InterruptedException ex)
    {
      return false;
    }
    return true;
  }
}
