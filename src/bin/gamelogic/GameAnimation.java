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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GameAnimation extends AnimationTimer {
    private String[] output = new String[5];
    private String[][] input = new String[4][5];
    private Snake snake;
    private Apple food;
    private Pane gameView;
    private Scene gameScene;
    private Label score;
    private long lastUpdate=0;
    private Socket socket;
    private DataInputStream inStream;
    private DataOutputStream outStream;
    private Snake[] snakes;
    private int playerNumber;
    private boolean allConnected;

    GameAnimation(Pane gameView, Scene gameScene){
        this.gameView = gameView;
        this.gameScene = gameScene;
        serverConnecting();

    }

    @Override
    public void handle(long now) {
        if(now - lastUpdate >= 200_000_000) {
            snake.move();
            System.out.println("lala");
            output[0] = Integer.toString(snake.getDirection());
            output[1] = Main.name;
            output[2] = Integer.toString(snake.getSpawn());
            output[3] = "none";
            output[4] = "none";
            dataOutput();
            dataInput();
            for (int i = 0; i <snakes.length; i++) {
                if (playerNumber != i){
                    if (!(snakes[i].getSpawn() == Integer.parseInt(input[i][2]))){
                        snakes[i].setSpawn(Integer.parseInt(input[i][2]));
                        snakes[i].spawn();
                        snakes[i].show();
                    }
                    snakes[i].setDirection(Integer.parseInt(input[i][0]));
                    snakes[i].move();
                }
            }
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
        snake.loose();
        gameView.getChildren().clear();
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
        Button restart_button = new Button("RESTART");
        restart_button.setPrefSize(90,35);
        restart_button.setLayoutX(GameField.x/2-restart_button.getPrefWidth()/2+20);
        restart_button.setLayoutY(GameField.y/2+10);
        restart_button.setStyle("-fx-background-color: #917337");
        restart_button.setScaleX(1.5);
        restart_button.setScaleY(1.5);
        restart_button.setOnMousePressed(event -> startThis());

        Button menu_button = new Button("MENU");
        menu_button.setPrefSize(90,35);
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
        try {
            playerNumber = inStream.readInt();
        }catch (IOException e ){
            e.printStackTrace();
        }

        score = new Label("SCORE: "+0);
        score.setLayoutX(700);
        score.setLayoutY(30);
        snake = new Snake(gameView);
        snake.setSpawn(playerNumber);
        snake.setDirection(playerNumber);
        snakes = new Snake[2];
        for (int i = 0; i <snakes.length ; i++) {
            snakes[i] = new Snake(gameView);
        }
        food = new Apple();
        gameView.getChildren().addAll(new SnakeBackground(), new GameField(), food, score);
        snake.spawn();
        snake.show();
        gameScene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE) {
                exitToMenu();
                event.consume();
            }
            else if(event.getCode() == KeyCode.UP){
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
        start();
    }

    private void serverConnecting(){
        try {
            socket = new Socket("localhost", 3355);
            System.out.println("Client connected to socket");
        } catch(Exception e){
            e.printStackTrace();
        }
        try{
            inStream = new DataInputStream(socket.getInputStream());
            outStream = new DataOutputStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void dataOutput(){
        try {
            for (int i = 0; i <5 ; i++) {
                outStream.writeUTF(output[i]);
                outStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dataInput(){
        try{
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j <5 ; j++) {
                    input[i][j] = inStream.readUTF();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }



}
