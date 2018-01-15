package nl.drogecode.pacman;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nl.drogecode.pacman.logic.Controll;
import nl.drogecode.pacman.logic.GameLogic;
import nl.drogecode.pacman.objects.Man;
import nl.drogecode.pacman.objects.MovingObject;
import nl.drogecode.pacman.text.Lifes;
import nl.drogecode.pacman.text.Score;

public class Pacman extends Application
{

  public static void main(String[] args)
  {
    launch(args);
  }

  @Override public void start(Stage primaryStage)
  {
    /*
     * Setup logic
     */
    GameLogic logic = new GameLogic();
    MovingObject.setLogic(logic);

    /*
     * construct screen stuff
     */
    Group root = new Group();
    BorderPane pane = new BorderPane();
    Scene scene = getScene(root, pane, logic);
    scene.setFill(Color.BLACK);
    primaryStage.setScene(scene);

    /*
     * create objects
     */
    Score score = new Score();
    Lifes lifes = new Lifes();
    Map map = new Map(root, score, logic);
    Man man = new Man(logic);

    logic.setStuff(primaryStage, map, man, score, lifes);

    /*
     * keybord reader
     */
    new Controll(primaryStage, man);
    getMouseLocation(root);

    // exit
    primaryStage.setOnCloseRequest(e ->
    {
      e.consume();
      System.out.println("clean exit :)");
      CloseClick(primaryStage, logic);
    });

    /*
     * Start building
     */
    root.getChildren().addAll(pane, man.getObject(), score, lifes);
    primaryStage.setTitle("Pacman");
    primaryStage.setResizable(false);
    primaryStage.show();

  }

  private Scene getScene(Group root, BorderPane pane, GameLogic logic)
  {
    /*
     * Construct menu.
     */
    MenuBar menuBar = new MenuBar();
    Menu menuFile = new Menu("Options");
    MenuItem add1 = new MenuItem("Settings");
    MenuItem add2 = new MenuItem("Restart");

    /*
     * Make menu doe stuff.
     */
    add1.setOnAction(e -> System.out.println(e));
    add2.setOnAction(e -> logic.restart());
    menuFile.getItems().addAll(add1);
    menuFile.getItems().addAll(add2);
    menuBar.getMenus().addAll(menuFile);

    /*
     * Make menu visable.
     */
    Scene scene = new Scene(root, 700, 400);
    pane.prefHeightProperty().bind(scene.heightProperty());
    pane.prefWidthProperty().bind(scene.widthProperty());
    pane.setTop(menuBar);

    return scene;
  }

  private void getMouseLocation(Group root)
  {
    /*
     * for fast map building!
     */
    root.setOnMouseClicked(new EventHandler<MouseEvent>()
    {
      @Override public void handle(MouseEvent event)
      {
        System.out.println("X: " + event.getSceneX());
        System.out.println("Y: " + event.getSceneY());
        System.out.println("");
      }
    });
  }

  public void CloseClick(Stage stage, GameLogic logic)
  {
    logic.setStopper(true);
    stage.close();
  }
}
