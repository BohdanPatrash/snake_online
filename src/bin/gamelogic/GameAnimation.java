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
import javafx.scene.paint.*;
import javafx.scene.paint.Color;

import java.awt.*;
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
    private List<Food> food = new ArrayList<>();
    private String ip;
    private int port;
    private boolean alive = true;
    private boolean snakeRemoved[] = new boolean[4];;


    GameAnimation(Pane gameView, Scene gameScene, String ip, int port){
        this.gameView = gameView;
        this.gameScene = gameScene;
        this.ip = ip;
        this.port = port;
        serverConnecting();


    }

    @Override
    public void handle(long now){
        if(now - lastUpdate >= 200_000_000) {
            if (!allSnakesDead()) {
                snake.move();
                output[0] = Integer.toString(snake.getDirection());
                output[1] = Main.name;
                output[2] = Integer.toString(snake.getSpawn());
                output[3] = Boolean.toString(alive);
                output[4] = "none";
                sendData();
                getData();
                for (int i = 5; i < input[playerNumber].length; i++) {
                    if (!input[playerNumber][i].equals("?")) {
                        spawningFood(input[playerNumber][i]);
                    }
                }
                for (int i = 0; i < snakes.length; i++) {

                    score[i].setText(input[i][1] + ": " + (snakes[i].getSize() - 5));
                    if (input[i][3].equals("false") && !snakeRemoved[i]) {
                        score[i].setTextFill(Color.RED);
                        snakes[i].removeSnake();
                        snakeRemoved[i] = true;
                    }
                    if (!snakeRemoved[i]) {
                        if (i != playerNumber) {
                            renderAll(i);
                            snakes[i].setDirection(Integer.parseInt(input[i][0]));
                            snakes[i].move();
                            if (snakes[i].eats(food)) {
                                score[i].setText(input[i][1] + ": " + (snakes[i].getSize() - 5));
                            }
                        }
                    }

                }
                activatedSnakes = true;
                if (snake.eats(food)) {
                    score[playerNumber].setText(input[playerNumber][1] + ": " + (snake.getSize() - 5));
                }

                if (snake.hitsBorder() || snake.hitSelf()) {
                    alive = false;
                }
                for (int i = 0; i <playerCount ; i++) {
                    if (i!=playerNumber && snake.hitAnotherSnake(snakes[i]) && !snakeRemoved[i]){
                        alive = false;
                    }
                }
            }else {
                end();
            }

            lastUpdate = now;
        }
    }

    private void end() {
        gameView.getChildren().clear();
        stop();
        int image_width = 250;
        int image_height = 200;
        ImageView gameover = new ImageView(new Image("bin/images/game_over.png",
                image_width,
                image_height,
                false,
                false));
        gameover.setLayoutX(Game.x/2-image_width/2);
        gameover.setLayoutY(Game.y/2-image_height-100);
        Button menu_button = new Button(Main.languageProperties.getProperty("menu"));
        menu_button.setPrefSize(100,35);
        menu_button.setLayoutX(Game.x/2-menu_button.getPrefWidth()/2);
        menu_button.setLayoutY(Game.y/2+50+menu_button.getPrefHeight());
        menu_button.setStyle("-fx-background-color: #917337");
        menu_button.setScaleX(1.5);
        menu_button.setScaleY(1.5);
        menu_button.setOnMousePressed(event -> exitToMenu());

        gameView.getChildren().addAll(new SnakeBackground(),
                                            gameover,
                                            menu_button);
        for (int i = 0; i <playerCount ; i++) {
            score[i] = new Label(input[i][1] + ": " + (snakes[i].getSize() - 5));
            score[i].setLayoutX(Game.x/2-image_width/2+50);
            score[i].setLayoutY(Game.y/2-90+40*i);
            score[i].setScaleX(2);
            score[i].setScaleY(2);
            gameView.getChildren().add(score[i]);
        }
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
            if(i==0)snakes[i] = new Snake(gameView,"#427412");
            else if (i==1)snakes[i] = new Snake(gameView,"#7727ba");
            else if (i==2)snakes[i] = new Snake(gameView,"#b920bf");
            else if (i==3)snakes[i] = new Snake(gameView,"#ef7f07");
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
            socket = new Socket(ip, port);
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

    private boolean allSnakesDead(){
        for (int i = 0; i < playerCount ; i++) {
            if(!snakeRemoved[i]) return false;
        }
        return true;
    }


}
