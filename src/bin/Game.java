package bin;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.awt.*;


public class Game{
    private Pane gameView = new Pane();
    public Scene gameScene = new Scene(gameView);
    public static int x = 660;
    public static int y = 660;
    public Game() {
        gameView.setPrefSize(x, y);
        Main.window.setScene(gameScene);
        Main.window.setX(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2
                -Main.window.getScene().getWidth()/2);
        Main.window.setY(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2
                -Main.window.getScene().getHeight()/2);
        GameAnimation animator = new GameAnimation(gameView, gameScene);
        animator.start_this();
        animator.start();

    }

}