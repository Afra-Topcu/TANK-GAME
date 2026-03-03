import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) {
        //create the root container for all game elements
        Pane root = new Pane();
        root.setStyle("-fx-background-color: pink;");//make background pink
        //initialize game controller with the root pane
        GameController gameController =new GameController(root);
        //create the main scene with specified dimensions
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        //set up keyboard event handlers
        scene.setOnKeyPressed(gameController::handleKeyPressed);
        scene.setOnKeyReleased(gameController::handleKeyReleased);

        //configure the primary stage
        primaryStage.setTitle("TANK2025");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        //start to game loop
        gameController.startGame();
    }

    public static void main(String[] args) {
        launch(args);//start the javafx application
    }
}