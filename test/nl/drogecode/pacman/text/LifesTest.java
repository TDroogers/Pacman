package nl.drogecode.pacman.text;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LifesTest
{
  Lifes lifes = new Lifes();

  @Test public void testLoseLife()
  {
    int start = Integer.parseInt(lifes.getText());
    boolean alive = lifes.loseLife();
    int minOne = Integer.parseInt(lifes.getText());
    assertEquals(--start, minOne);
    assertTrue(alive);
    for (int i = Integer.parseInt(lifes.getText()); i > 0; i--)
    {
      alive = lifes.loseLife();
    }
    assertFalse(alive);
  }

  @Test public void testRestart()
  {
    lifes.restart();
    boolean alive = lifes.loseLife();
    assertTrue(alive);
  }
}
