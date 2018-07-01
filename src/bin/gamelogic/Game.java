package bin.gamelogic;

import bin.Main;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.awt.*;


public class Game{
    private Pane gameView = new Pane();
    private GameAnimation animator;
    private Scene gameScene = new Scene(gameView);
    public static int x = 850;
    public static int y = 700;

    public Game(String ip, int port) {
        gameView.setPrefSize(x, y);
        Main.window.setScene(gameScene);
        Main.window.setX(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2
                -Main.window.getScene().getWidth()/2);
        Main.window.setY(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2
                -Main.window.getScene().getHeight()/2);
        animator = new GameAnimation(gameView, gameScene, ip, port);
        animator.startThis();
        animator.start();

    }


}