package bin.fxController;

import bin.Main;
import bin.gamelogic.Game;
import bin.gamelogic.server.ServerStart;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CreateServerController {

    @FXML
    private Label localIPLabel;
    @FXML
    private Label portLabel;
    @FXML
    private Label playersAmountLabel;

    @FXML
    private TextField localIPTextField;
    @FXML
    private TextField portTextField;

    @FXML
    private Button createAndPlayButton;
    @FXML
    private Button backButton;

    @FXML
    private MenuButton playersAmountMenuButton;

    @FXML
    public void initialize() throws Exception{
        setTexts();
        setPlayersAmountMenuButton();
    }

    private void setTexts(){
        createAndPlayButton.setText(Main.languageProperties.getProperty("createAndPlay"));
        backButton.setText(Main.languageProperties.getProperty("back"));
        localIPLabel.setText(Main.languageProperties.getProperty("localIP"));
        playersAmountLabel.setText(Main.languageProperties.getProperty("playersAmount"));
        portLabel.setText(Main.languageProperties.getProperty("port"));
    }

    private void setPlayersAmountMenuButton(){
        MenuItem two = new MenuItem("2");
        MenuItem three = new MenuItem("3");
        MenuItem four = new MenuItem("4");
        playersAmountMenuButton.getItems().add(two);
        playersAmountMenuButton.getItems().add(three);
        playersAmountMenuButton.getItems().add(four);
        playersAmountMenuButton.setText("4");
        two.setOnAction(event -> playersAmountMenuButton.setText("2"));
        three.setOnAction(event -> playersAmountMenuButton.setText("3"));
        four.setOnAction(event -> playersAmountMenuButton.setText("4"));
    }

    public void createAndPlayButtonPress(){
        int playerCount = Integer.parseInt(playersAmountMenuButton.getText());
        int port = Integer.parseInt(portTextField.getText());
        String ip = localIPTextField.getText();
        new Thread(new ServerStart(playerCount, port)).start();
        new Game(ip ,port);
    }

    public void backButtonPress(){
        Main.window.setScene(MenuController.modeChoiceScene);
    }

    public void createAndPlayButtonSetOnMouseEntered(){
        createAndPlayButton.setUnderline(true);
    }

    public void createAndPlayButtonSetOnMouseExited(){
        createAndPlayButton.setUnderline(false);
    }

}
