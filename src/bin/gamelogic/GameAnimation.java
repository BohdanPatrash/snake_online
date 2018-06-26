package bin.gamelogic;

import bin.food.Apple;
import bin.Main;
import bin.snake.Snake;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import java.awt.Toolkit;

public class GameAnimation extends AnimationTimer {

    private Snake snake;
    private Apple food;
    private Pane gameView;
    private Scene gameScene;
    private Label score;
    private long lastUpdate=0;

    GameAnimation(Pane gameView, Scene gameScene){
        this.gameView = gameView;
        this.gameScene = gameScene;
    }

    @Override
    public void handle(long now) {
        if(now - lastUpdate >= 200_000_000) {
            snake.move();

            if (snake.eats(food)) {
                gameView.getChildren().remove(food);
                food = new Apple();
                gameView.getChildren().add(food);
                score.setText("SCORE: " + (snake.getSize() - 5));
            }

            if (snake.hits_border()) {
                loose();
                stop();
            } else if (snake.hit_self()) {
                loose();
                stop();
            }
            lastUpdate = now;
        }
    }

    private void loose() {
        gameView.getChildren().removeAll();
        stop();
        int image_width = 250;
        int image_height = 200;
        ImageView gameover = new ImageView(new Image("images/game_over.png",
                250,
                200,
                false,
                false));
        gameover.setLayoutX(GameField.x/2-image_width/2+20);
        gameover.setLayoutY(GameField.y/2-image_height);
        Button restart_button = new Button(Main.languageProperties.getProperty("restart"));
        restart_button.setPrefSize(90,35);
        restart_button.setLayoutX(GameField.x/2-restart_button.getPrefWidth()/2+20);
        restart_button.setLayoutY(GameField.y/2+10);
        restart_button.setStyle("-fx-background-color: #917337");
        restart_button.setScaleX(1.5);
        restart_button.setScaleY(1.5);
        restart_button.setOnMousePressed(event -> start_this());

        Button menu_button = new Button(Main.languageProperties.getProperty("menu"));
        menu_button.setPrefSize(90,35);
        menu_button.setLayoutX(GameField.x/2-menu_button.getPrefWidth()/2+20);
        menu_button.setLayoutY(GameField.y/2+40+menu_button.getPrefHeight());
        menu_button.setStyle("-fx-background-color: #917337");
        menu_button.setScaleX(1.5);
        menu_button.setScaleY(1.5);
        menu_button.setOnMousePressed(event -> exit_to_menu());

        gameView.getChildren().addAll(new SnakeBackground(), new GameField(),gameover, restart_button, menu_button);
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

    public void start_this() {
        score = new Label(Main.languageProperties.getProperty("score") + " " +0);
        score.setLayoutX(700);
        score.setLayoutY(30);
        snake = new Snake(gameView);
        food = new Apple();
        gameView.getChildren().addAll(new SnakeBackground(), new GameField(), food, score);
        snake.show();
        gameScene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE) {
                exit_to_menu();
                event.consume();
            }
            else if(event.getCode() == KeyCode.UP){
                snake.up();
                event.consume();
            }
            else if(event.getCode() == KeyCode.LEFT){
                snake.left();
                event.consume();
            }
            else if(event.getCode() == KeyCode.DOWN){
                snake.down();
                event.consume();
            }
            else if(event.getCode() == KeyCode.RIGHT){
                snake.right();
                event.consume();
            }
        });
        start();
    }
}
