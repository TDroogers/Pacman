package nl.drogecode.pacman.logic.pathfinder;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import nl.drogecode.pacman.enums.Direction;

class SingleDecisionPointTest
{
  SingleDecisionPoint sdp = new SingleDecisionPoint();

  @Test void testSingleDecisionPoint()
  {
    assertNotNull(sdp);
    sdp.setPath(Direction.UP);
    assertNotNull(sdp.getPath(Direction.UP));
    assertNotSame(sdp, sdp.getPath(Direction.UP));
    assertTrue(sdp.getPath(Direction.UP) instanceof SingleDecisionPoint);
  }

  @Test void testUnsetAllNextPath()
  {
    sdp.unsetAllNextPath();
    assertNull(sdp.getPath(Direction.UP));
    assertTrue(sdp.getEnd());
    assertTrue(sdp.getFullStop());
  }

}
