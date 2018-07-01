package bin.gamelogic;

import bin.gamelogic.food.Apple;
import bin.Main;
import bin.gamelogic.food.Food;
import bin.gamelogic.snake.Snake;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SoloGame extends AnimationTimer {

    private Pane gameView = new Pane();
    private Scene gameScene = new Scene(gameView);
    public static int x = 850;
    public static int y = 700;
    private String[] splitFood;
    private Snake snake;
    private Label score;
    private long lastUpdate=0;
    private List<Food> food = new ArrayList<>();

    public SoloGame(){
        gameView.setPrefSize(x, y);
        Main.window.setScene(gameScene);
        Main.window.setX(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2
                -Main.window.getScene().getWidth()/2);
        Main.window.setY(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2
                -Main.window.getScene().getHeight()/2);
        startThis();
        start();
    }

    @Override
    public void handle(long now){
        if(now - lastUpdate >= 200_000_000) {
            snake.move();
            Random random = new Random();
            String randomFood = "?";
            double probability = random.nextDouble();
            if (probability < 0.06) {
                Apple temp = new Apple();
                randomFood += " Apple_"+ temp.getCenterX()+"_"+temp.getCenterY();
            }
            splitFood = randomFood.split(" ");
            for (int i = 0; i <splitFood.length ; i++) {
                if(!splitFood[i].equals("?")){
                    spawningFood(splitFood[i]);
                }
            }
            if (snake.eats(food)) {
                score.setText(Main.languageProperties.getProperty("score") + " " +(snake.getSize() - 5));
            }

            if (snake.hitsBorder() || snake.hitSelf()) {
                loose();
                stop();
            }

            lastUpdate = now;
        }
    }

    private void loose() {
        snake.loose();
        gameView.getChildren().clear();
        stop();
        int image_width = 250;
        int image_height = 200;
        ImageView gameover = new ImageView(new Image("bin/images/game_over.png",
                250,
                200,
                false,
                false));
        gameover.setLayoutX(GameField.x/2-image_width/2+20);
        gameover.setLayoutY(GameField.y/2-image_height);
        Button restart_button = new Button(Main.languageProperties.getProperty("restart"));
        restart_button.setPrefSize(100,35);
        restart_button.setLayoutX(GameField.x/2-restart_button.getPrefWidth()/2+20);
        restart_button.setLayoutY(GameField.y/2+10);
        restart_button.setStyle("-fx-background-color: #917337");
        restart_button.setScaleX(1.5);
        restart_button.setScaleY(1.5);
        restart_button.setOnMousePressed(event -> startThis());

        Button menu_button = new Button(Main.languageProperties.getProperty("menu"));
        menu_button.setPrefSize(100,35);
        menu_button.setLayoutX(GameField.x/2-menu_button.getPrefWidth()/2+20);
        menu_button.setLayoutY(GameField.y/2+40+menu_button.getPrefHeight());
        menu_button.setStyle("-fx-background-color: #917337");
        menu_button.setScaleX(1.5);
        menu_button.setScaleY(1.5);
        menu_button.setOnMousePressed(event -> exitToMenu());

        gameView.getChildren().addAll(new SnakeBackground(),
                new GameField(),
                gameover,
                restart_button,
                menu_button);
    }

    private void exitToMenu() {
        Main.window.setScene(Main.menu);
        Main.window.setX(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2
                -Main.window.getScene().getWidth()/2);
        Main.window.setY(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2
                -Main.window.getScene().getHeight()/2);
        gameView.getChildren().removeAll();
        stop();

    }

    public void startThis() {

        score = new Label(Main.languageProperties.getProperty("score") + " " +0);
        score.setLayoutX(700);
        score.setLayoutY(30);
        gameView.getChildren().addAll(new SnakeBackground(), new GameField(), score);
        snake = new Snake(gameView, "#427412");
        snake.setSpawn(3);
        snake.setDirection(3);
        snake.spawn();
        snake.show();
        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                exitToMenu();
                event.consume();
            }
        });
        setControllerKeys();
        start();
    }

    private void setControllerKeys(){
        if (Main.controller.equals("4arrows")){
            gameScene.setOnKeyPressed(event -> {
                if(event.getCode() == KeyCode.UP){
                    snake.setDirection(3);
                    event.consume();
                }
                else if(event.getCode() == KeyCode.LEFT){
                    snake.setDirection(2);
                    event.consume();
                }
                else if(event.getCode() == KeyCode.DOWN){
                    snake.setDirection(1);
                    event.consume();
                }
                else if(event.getCode() == KeyCode.RIGHT){
                    snake.setDirection(0);
                    event.consume();
                }
            });
        }
        else if (Main.controller.equals("WASD")){
            gameScene.setOnKeyPressed(event -> {
                if(event.getCode() == KeyCode.W){
                    snake.setDirection(3);
                    event.consume();
                }
                else if(event.getCode() == KeyCode.A){
                    snake.setDirection(2);
                    event.consume();
                }
                else if(event.getCode() == KeyCode.S){
                    snake.setDirection(1);
                    event.consume();
                }
                else if(event.getCode() == KeyCode.D){
                    snake.setDirection(0);
                    event.consume();
                }
            });
        }
        else if (Main.controller.equals("2arrows")){
            gameScene.setOnKeyPressed(event -> {
                if(event.getCode() == KeyCode.LEFT){
                    int dir = snake.getDirection();
                    if (dir == 0) snake.setDirection(3);
                    else snake.setDirection(dir-1);
                    event.consume();
                }
                else if(event.getCode() == KeyCode.RIGHT){
                    int dir = snake.getDirection();
                    if (dir == 3) snake.setDirection(0);
                    else snake.setDirection(dir+1);
                    event.consume();
                }
            });
        }
        else if (Main.controller.equals("AD")){
            gameScene.setOnKeyPressed(event -> {
                if(event.getCode() == KeyCode.A){
                    int dir = snake.getDirection();
                    if (dir == 0) snake.setDirection(3);
                    else snake.setDirection(dir-1);
                    event.consume();
                }
                else if(event.getCode() == KeyCode.D){
                    int dir = snake.getDirection();
                    if (dir == 3) snake.setDirection(0);
                    else snake.setDirection(dir+1);
                    event.consume();
                }
            });
        }
    }

    private void spawningFood(String s){
        String temp[] = s.split("_");
        String tempKind = temp[0];
        double tempX = Double.parseDouble(temp[1]);
        double tempY = Double.parseDouble(temp[2]);
        Food tempFood;
        if(tempKind.equals("Apple")){
            tempFood = new Apple();
        }else{
            tempFood = new Apple();
        }
        tempFood.setCenterX(tempX);
        tempFood.setCenterY(tempY);
        food.add(tempFood);
        gameView.getChildren().add(tempFood);

    }



}
