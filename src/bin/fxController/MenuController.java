package bin.fxController;

import bin.Main;
import bin.Music;
import bin.gamelogic.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.FileOutputStream;
import java.io.IOException;

public class MenuController{

    static public Scene optionsScene;

    @FXML
    private TextField nameField;
    @FXML
    private Button startButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button optionButton;
    @FXML
    private Label yourNameLabel;

    @FXML
    public void initialize() throws Exception{
        startButton.setText(Main.languageProperties.getProperty("start"));
        optionButton.setText(Main.languageProperties.getProperty("options"));
        exitButton.setText(Main.languageProperties.getProperty("exit"));
        yourNameLabel.setText(Main.languageProperties.getProperty("yourName"));
        nameField.setText(Main.name);
        optionsScene = new Scene(FXMLLoader.load(getClass().getResource("../fx/Options.fxml")), 440, 440);
        optionsScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Main.window.setScene(Main.menu);
            }
            event.consume();
        });
        startButton.setOnMouseEntered(event -> startButton.setUnderline(true));
        startButton.setOnMouseExited(event -> startButton.setUnderline(false));

        optionButton.setOnMouseEntered(event -> optionButton.setUnderline(true));
        optionButton.setOnMouseExited(event -> optionButton.setUnderline(false));

        exitButton.setOnMouseEntered(event -> {
            exitButton.setText(Main.languageProperties.getProperty("nooo"));
            exitButton.setTextFill(Color.RED);
        });
        exitButton.setOnMouseExited(event ->{
            exitButton.setText(Main.languageProperties.getProperty("exit"));
            exitButton.setTextFill(Color.BLACK);
        });
    }

    public void exitButtonPress(){
        Main.window.close();
    }


    public void startButtonPress()throws  IOException{
        //Music.playMusic("src/bin/media/SweetDreams.wav");
        if (!nameField.getText().equals(Main.name))
            saveName();
        new Game();
    }

    private void saveName(){
        Main.name = nameField.getText();
        try {
            FileOutputStream fos = new FileOutputStream("src/bin/properties/config.properties");
            Main.configProperties.setProperty("name", Main.name);
            Main.configProperties.store(fos, null);
            fos.close();

        } catch (IOException e) {
            System.err.println("Error, file does not exist");
        }
    }

    public void optionButtonPress() throws Exception{
        Main.window.setScene(optionsScene);
    }

}
