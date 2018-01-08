package nl.drogecode.pacman.objects;

import javafx.stage.Stage;
import nl.drogecode.pacman.Map;
import nl.drogecode.pacman.logic.GameLogic;

public class NpcObject extends MovingObject
{

  public NpcObject (Stage stage, Map map, GameLogic logic)
  {
    super(stage, map, logic);
  }
}
