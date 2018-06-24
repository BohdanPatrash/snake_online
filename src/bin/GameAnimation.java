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
    private Apple food;
    private Pane gameView;
    private Scene gameScene;

    GameAnimation(Pane gameView, Scene gameScene){
        this.gameView = gameView;
        this.gameScene = gameScene;
    }

    @Override
    public void handle(long now) {
        snake.move();
        try {
            Thread.sleep(200);
        }catch (InterruptedException e){}

        if(snake.eats(food)){
            gameView.getChildren().remove(food);
            food = new Apple();
            gameView.getChildren().add(food);
        }

        if(snake.hits_border()){
            loose();
            stop();
        }

        if(snake.hit_self()){
            loose();
            stop();
        }

    }

    private void loose() {
        gameView.getChildren().removeAll();
        int image_width = 250;
        int image_height = 200;
        ImageView gameover = new ImageView(new Image("images/game_over.png",
                250,
                200,
                false,
                false));
        gameover.setLayoutX(Game.x/2-image_width/2);
        gameover.setLayoutY(Game.y/2-image_height);
        Button restart_button = new Button("RESTART");
        restart_button.setPrefSize(90,35);
        restart_button.setLayoutX(Game.x/2-restart_button.getPrefWidth()/2);
        restart_button.setLayoutY(Game.y/2+10);
        restart_button.setStyle("-fx-background-color: #917337");
        restart_button.setScaleX(1.5);
        restart_button.setScaleY(1.5);

        restart_button.setOnMousePressed(event -> restart());

        Button menu_button = new Button("MENU");
        menu_button.setPrefSize(90,35);
        menu_button.setLayoutX(Game.x/2-menu_button.getPrefWidth()/2);
        menu_button.setLayoutY(Game.y/2+40+menu_button.getPrefHeight());
        menu_button.setStyle("-fx-background-color: #917337");
        menu_button.setScaleX(1.5);
        menu_button.setScaleY(1.5);

        menu_button.setOnMousePressed(event -> exit_to_menu());

        Music.StopMusic();

        gameView.getChildren().addAll(new GameField(),gameover, restart_button, menu_button);
    }

    private void exit_to_menu() {
        Main.window.setScene(Main.menu);
        Main.window.setX(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2
                -Main.window.getScene().getWidth()/2);
        Main.window.setY(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2
                -Main.window.getScene().getHeight()/2);
        gameView.getChildren().removeAll();
        stop();
    }

    private void restart(){
        gameView.getChildren().removeAll();
        start_this();
    }

    public void start_this() {
        GameField field = new GameField();
        snake = new Snake(gameView);
        food = new Apple();
        gameView.getChildren().addAll(field, food);
        snake.show();
        gameScene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE) {
                exit_to_menu();
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
