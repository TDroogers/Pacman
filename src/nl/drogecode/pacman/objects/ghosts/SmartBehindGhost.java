/**
 * 
 * This ghost will follow you and try's to get behind you.
 * 
 */
package nl.drogecode.pacman.objects.ghosts;

import javafx.scene.paint.Color;
import nl.drogecode.pacman.enums.GhostType;
import nl.drogecode.pacman.logic.GameLogic;

public class SmartBehindGhost extends SmartGhost
{

  public SmartBehindGhost(double x, double y, GameLogic logic)
  {
    super(x, y, logic, GhostType.BEHIND);
    ghost.setFill(Color.RED);
  }
}
