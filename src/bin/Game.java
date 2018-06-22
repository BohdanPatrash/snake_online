package bin;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;


public class Game implements Runnable {
    private Snake snake;
    private float interval = 1000.f/20;

    public Game() {
        Pane gameView = new Pane();
        Scene gameScene = new Scene(gameView);
        gameView.setPrefSize(440, 440);
        GameField field = new GameField();

        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Main.window.setScene(Main.menu);
            }
            event.consume();
        });
        snake = new Snake(gameView);

        gameView.getChildren().addAll(field);
        snake.show();
        Main.window.setScene(gameScene);



    }

    @Override
    public void run() {
        //--------------------gameloop------------------
        AnimationTimer animator = new AnimationTimer() {

            @Override
            public void handle(long now) {
                snake.move();
                try {
                    Thread.sleep(200);
                }catch (InterruptedException e){
                }
            }
        };
        animator.start();



    }
}