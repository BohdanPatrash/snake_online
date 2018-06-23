package bin;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import java.awt.*;


public class Game{
    private Snake snake;
    private Food food;
    private SnakeList body;
    private SquareDOT body_dot;
    private Button restart_button;
    private Pane gameView = new Pane();
    public Scene gameScene = new Scene(gameView);

    public Game() {
        gameView.setPrefSize(880, 880);
        Main.window.setScene(gameScene);
        Main.window.setX(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2
                -Main.window.getScene().getWidth()/2);
        Main.window.setY(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2
                -Main.window.getScene().getHeight()/2);
        GameAnimation animator = new GameAnimation(snake, food, gameView, body, gameScene);
        animator.start_this();
        animator.start();

    }

}