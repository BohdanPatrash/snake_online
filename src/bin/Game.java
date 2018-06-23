package bin;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;


public class Game implements Runnable {
    private Snake snake;
    private Food food;
    private Pane gameView;

    public Game() {
        gameView = new Pane();
        Scene gameScene = new Scene(gameView);
        gameView.setPrefSize(440, 440);
        GameField field = new GameField();
        snake = new Snake(gameView);
        gameScene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE) {
                Main.window.setScene(Main.menu);
            }
            else if(event.getCode() == KeyCode.UP){
                snake.up();
            }
            else if(event.getCode() == KeyCode.LEFT){
                snake.left();
            }
            else if(event.getCode() == KeyCode.DOWN){
                snake.down();
            }
            else if(event.getCode() == KeyCode.RIGHT){
                snake.right();
            }
            event.consume();
        });

        food = new Food();
        gameView.getChildren().addAll(field, food);
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
                }catch (InterruptedException e){}
                if(snake.getHead().getX()==food.getX()&&snake.getHead().getY()==food.getY()){
                    snake.grow();
                    gameView.getChildren().remove(food);
                    food = new Food();
                    gameView.getChildren().add(food);
                }
            }
        };

        animator.start();



    }
}