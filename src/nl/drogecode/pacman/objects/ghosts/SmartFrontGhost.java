/**
 * 
 * This ghost will follow you and try's to get in front of you.
 * 
 */
package nl.drogecode.pacman.objects.ghosts;

import javafx.scene.paint.Color;
import nl.drogecode.pacman.enums.GhostType;
import nl.drogecode.pacman.logic.GameLogic;

public class SmartFrontGhost extends SmartGhost
{

  public SmartFrontGhost(double x, double y, GameLogic logic)
  {
    super(x, y, logic, GhostType.FRONT);
    ghost.setFill(Color.PINK);
  }
}
