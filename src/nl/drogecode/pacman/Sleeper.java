package nl.drogecode.pacman;

public class Sleeper
{
  public boolean sleeper()
  {
    return sleeper(30);
  }

  public boolean sleeper(int time)
  {
    try
    {
      Thread.sleep(time); // 1000 milliseconds is one second.
    }
    catch (InterruptedException ex)
    {
      System.out.println("Time thread error: " + ex);
      Thread.currentThread().interrupt();
      return false;
    }
    return true;
  }
}
