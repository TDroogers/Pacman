package nl.drogecode.pacman;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Pacman extends Application
{
  Score score;
  Map map;
  Man man;
  GameLogic logic;
  
  public static void main(String[] args)
  {
    launch(args);
  }
  
  @Override public void start(Stage primaryStage)
  {
    Group root = new Group();
    BorderPane pane = new BorderPane();
    Scene scene = getScene(root, pane);
    scene.setFill(Color.BLACK);
    primaryStage.setScene(scene);
    
    /*
     * create objects
     */
    logic = new GameLogic();
    score = new Score();
    map = new Map(root, score);
    man = new Man(primaryStage, map, logic);
    Ghost.setStatics(primaryStage, man, logic);
    
    logic.setStuff(map, man, score);
    
    /*
     * keybord reader
     */
    new Controll(primaryStage, man);

    /*
     * Start building 
     */
    root.getChildren().addAll(pane, man, score);
    primaryStage.setTitle("Pacman");
    primaryStage.setResizable(false);
    primaryStage.show();
  }
  
  private Scene getScene(Group root, BorderPane pane)
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
}
