package bin.gamelogic;

import bin.food.Apple;
import bin.Main;
import bin.food.Food;
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
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class GameAnimation extends AnimationTimer {
    private String[] output = new String[5];
    private String[][] input;
    private Snake snake;
    private Pane gameView;
    private Scene gameScene;
    private Label[] score;
    private long lastUpdate=0;
    private Socket socket;
    private DataInputStream inStream;
    private DataOutputStream outStream;
    private Snake[] snakes;
    private int playerNumber;
    private int playerCount;
    private boolean activatedSnakes = false;
    private String foodCoordinates[];
    private List<Food> food = new ArrayList<>();


    static BufferedReader in;
    static PrintWriter out;


    GameAnimation(Pane gameView, Scene gameScene){
        this.gameView = gameView;
        this.gameScene = gameScene;
        serverConnecting();

    }

    @Override
    public void handle(long now){
        if(now - lastUpdate >= 200_000_000) {
            snake.move();
            output[0] = Integer.toString(snake.getDirection());
            output[1] = Main.name;
            output[2] = Integer.toString(snake.getSpawn());
            output[3] = "none";
            output[4] = "none";
            sendData();
            getData();
            for (int i = 5; i < input[playerNumber].length; i++) {
                if(!input[playerNumber][i].equals("?")){
                    spawningFood(input[playerNumber][i]);
                }
            }
            for (int i = 0; i <snakes.length; i++) {
                score[i].setText(input[i][1] + ": " +(snakes[i].getSize() - 5));
                if(i != playerNumber){
                    renderAll(i);
                    snakes[i].setDirection(Integer.parseInt(input[i][0]));
                    snakes[i].move();
                    if (snakes[i].eats(food)){
                        score[i].setText(input[i][1] + ": " +(snakes[i].getSize() - 5));
                    }
                }
            }

            if (snake.eats(food)) {
                score[playerNumber].setText(input[playerNumber][1] + ": " +(snake.getSize() - 5));
            }

            if (snake.hits_border() || snake.hit_self()) {
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
        try {
            playerNumber = inStream.readInt();
            playerCount = inStream.readInt();
        }catch (IOException e ){
            e.printStackTrace();
        }
        input = new String[playerCount][5];
        gameView.getChildren().addAll(new SnakeBackground(), new GameField());
        score = new Label[playerCount];
        for (int i = 0; i <playerCount ; i++) {
            score[i] = new Label(Main.languageProperties.getProperty("score") + " " +0);
            score[i].setLayoutX(700);
            score[i].setLayoutY(30+i*40);
            gameView.getChildren().add(score[i]);
        }

        snakes = new Snake[playerCount];
        for (int i = 0; i <snakes.length ; i++) {
            snakes[i] = new Snake(gameView);
        }
        snake = snakes[playerNumber];
        snake.setSpawn(playerNumber);
        snake.setDirection(playerNumber);
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

    private void sendData(){
        try {
            outStream.writeUTF(
                  output[0]+" "
                    +output[1]+" "
                    +output[2]+" "
                    +output[3]+" "
                    +output[4]);
            outStream.flush();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getData(){
        try{
            for (int i = 0; i < playerCount; i++) {
                    input[i] = inStream.readUTF().split(" ");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void renderAll(int i){
        if (!activatedSnakes){
            snakes[i].setSpawn(Integer.parseInt(input[i][2]));
            snakes[i].spawn();
            snakes[i].show();
            activatedSnakes = true;
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
