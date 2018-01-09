package nl.drogecode.pacman.objects;

import java.util.ArrayList;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import nl.drogecode.pacman.Sleeper;
import nl.drogecode.pacman.logic.GameLogic;

public class MovingObject extends BaseObject
{
  protected volatile GameLogic logic;
  
  protected Sleeper sleep = new Sleeper();
  protected Thread th;
  protected double oldX, oldY, x, y, newX, newY, maxX, maxY;
  protected boolean walking;
  protected volatile int direction;
  protected final int SPEED = 2;
  
  public MovingObject (GameLogic logic)
  {
    this.logic = logic;
  }
  

  
  public boolean checkBumpBorder(double newX, double maxX, double newY, double maxY)
  {
    // Has to be side specific, but fine for now. (In the current map you can never reach the borders)
    if (newX > maxX || newX < 0 || newY > maxY || newY < 25)
    {
      return false;
    }
    return true;
  }
  
  public boolean checkBumpWall(Circle a, double newX, double newY)
  {

    Circle clone = new Circle();
    clone.setCenterX(newX);
    clone.setCenterY(newY);
    clone.setRadius(a.getRadius());
    ArrayList<Shape> shapes = logic.getWallArray();

    for (Shape shape : shapes)
    {
      if (clone.getBoundsInParent().intersects(shape.getBoundsInParent()))
      {
        return false;
      }
    }
    return true;
  }
}
