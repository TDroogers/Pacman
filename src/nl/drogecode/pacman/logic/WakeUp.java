/**
 * Every SLEEPTIME it will wake the Thread's.
 */
package nl.drogecode.pacman.logic;

import java.util.Timer;
import java.util.TimerTask;

public class WakeUp
{
  private Timer timer;
  private final int SLEEPTIME = 16;
  private boolean stopper;

  public WakeUp()
  {
    timer = new Timer();
  }

  public synchronized void setNewThread(Thread thread)
  {
    timer.schedule(new runningThreadWaker(thread), SLEEPTIME);
  }

  public void setStopper(boolean stop)
  {
    stopper = stop;
  }

  private class runningThreadWaker extends TimerTask
  {
    private Thread thread;

    private runningThreadWaker(Thread thread)
    {
      this.thread = thread;
    }

    @Override public void run()
    {
      timer.schedule(new runningThreadWaker(thread), SLEEPTIME);
      thread.interrupt();

      if (stopper)
      {
        timer.cancel();
      }
    }

  }
}
