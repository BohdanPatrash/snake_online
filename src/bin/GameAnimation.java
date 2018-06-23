package bin;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.awt.*;

public class GameAnimation extends AnimationTimer {

    private Snake snake;
    private Food food;
    private Pane gameView;
    private SnakeList body;
    private SquareDOT body_dot;
    private Button restart_button;
    private Scene gameScene;

    GameAnimation(Snake snake, Food food, Pane gameView, SnakeList body, Scene gameScene){
        this.snake = snake;
        this.food = food;
        this.gameView = gameView;
        this.body = body;
        this.gameScene = gameScene;
    }

    @Override
    public void handle(long now) {
        snake.move();
        double x = snake.getHead().getX();
        double y = snake.getHead().getY();
        try {
            Thread.sleep(200);
        }catch (InterruptedException e){}
        if(snake.getHead().getX()==food.getX()&&snake.getHead().getY()==food.getY()){
            snake.grow();
            gameView.getChildren().remove(food);
            food = new Food();
            gameView.getChildren().add(food);
        }
        if(x<0||x>880||y<0||y>880){
            loose();
            stop();
        }
        body_dot = body.getLast();
        for (int i = 0; i <snake.getSize(); i++) {
            if(body_dot.getX()==x&&body_dot.getY()==y){
                loose();
                stop();
            }
            body_dot = body_dot.getPrevious();
        }
    }

    private void loose() {
        gameView.getChildren().removeAll();
        ImageView gameover = new ImageView(new Image("images/game_over.png",
                250,
                200,
                false,
                false));
        gameover.setLayoutX(315);
        gameover.setLayoutY(315);
        this.restart_button = new Button("RESTART");
        restart_button.setLayoutX(410);
        restart_button.setLayoutY(525);
        restart_button.setStyle("-fx-background-color: #917337");
        restart_button.setScaleX(1.5);
        restart_button.setScaleY(1.5);
        restart_button.setOnMousePressed(event -> restart());
        gameView.getChildren().addAll(new GameField(),gameover, restart_button);
    }

    private void restart(){
        gameView.getChildren().removeAll();
        start_this();
    }

    public void start_this() {
        GameField field = new GameField();
        snake = new Snake(gameView);
        body = snake.getBody();
        food = new Food();
        gameView.getChildren().addAll(field, food);
        snake.show();
        gameScene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE) {
                Main.window.setScene(Main.menu);
                Main.window.setX(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2
                        -Main.window.getScene().getWidth()/2);
                Main.window.setY(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2
                        -Main.window.getScene().getHeight()/2);
                gameView.getChildren().removeAll();
                stop();
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
        start();
    }
}
