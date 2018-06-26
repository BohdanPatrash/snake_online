package bin.gamelogic;

import bin.Main;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Game{
    private Pane gameView = new Pane();
    private GameAnimation animator;
    private Scene gameScene = new Scene(gameView);
    public static int x = 850;
    public static int y = 700;

    public Game() {
        gameView.setPrefSize(x, y);
        Main.window.setScene(gameScene);
        Main.window.setX(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2
                -Main.window.getScene().getWidth()/2);
        Main.window.setY(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2
                -Main.window.getScene().getHeight()/2);
        animator = new GameAnimation(gameView, gameScene);
        animator.startThis();
        animator.start();

    }


}