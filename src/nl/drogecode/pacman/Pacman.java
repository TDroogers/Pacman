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
import nl.drogecode.pacman.logic.GameLogic;
import nl.drogecode.pacman.objects.Man;
import nl.drogecode.pacman.text.Lifes;
import nl.drogecode.pacman.text.Score;

public class Pacman extends Application
{
  Score score;
  Lifes lifes;
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
    lifes = new Lifes();
    map = new Map(root, score, logic);
    man = new Man(logic);
    
    logic.setStuff(primaryStage, map, man, score);
    
    /*
     * keybord reader
     */
    new Controll(primaryStage, man);

    /*
     * Start building 
     */
    root.getChildren().addAll(pane, man.getMan(), score, lifes);
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
