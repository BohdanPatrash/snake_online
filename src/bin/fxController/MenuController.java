package bin.fxController;

import bin.Main;
import bin.Music;
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

    static private Scene optionsScene;
    static Scene modeChoiceScene;

    @FXML
    TextField nameField;
    @FXML
    Button startButton;
    @FXML
    Button exitButton;
    @FXML
    Button optionButton;
    @FXML
    Label yourNameLabel;

    @FXML
    public void initialize() throws Exception{
        setTexts();
        createOptionsScene();
        createModeChoiceScene();
    }

    private void setTexts(){
        startButton.setText(Main.languageProperties.getProperty("start"));
        optionButton.setText(Main.languageProperties.getProperty("options"));
        exitButton.setText(Main.languageProperties.getProperty("exit"));
        yourNameLabel.setText(Main.languageProperties.getProperty("yourName"));
        nameField.setText(Main.name);
    }

    private void createOptionsScene() throws IOException{
        optionsScene = new Scene(FXMLLoader.load(getClass().getResource("../fx/Options.fxml")), 440, 440);
        optionsScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Main.window.setScene(Main.menu);
            }
            event.consume();
        });
    }

    private void createModeChoiceScene() throws IOException{
        modeChoiceScene = new Scene(FXMLLoader.load(getClass().getResource("../fx/ModeChoice.fxml")), 440, 440);
        modeChoiceScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Main.window.setScene(Main.menu);
            }
            event.consume();
        });
    }

    public void exitButtonPress(){
        Main.window.close();
    }

    public void startButtonPress() throws IOException{
        //Music.playMusic("src/bin/media/SweetDreams.wav");
        if (!nameField.getText().equals(Main.name))
            saveName();
        Main.window.setScene(modeChoiceScene);
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

    public void startButtonSetOnMouseEntered(){
        startButton.setUnderline(true);
    }

    public void startButtonSetOnMouseExited(){
        startButton.setUnderline(false);
    }

    public void optionButtonSetOnMouseEntered(){
        optionButton.setUnderline(true);
    }

    public void optionButtonSetOnMouseExited(){
        optionButton.setUnderline(false);
    }

    public void exitButtonSetOnMouseEntered(){
        exitButton.setText(Main.languageProperties.getProperty("nooo"));
        exitButton.setTextFill(Color.RED);
    }

    public void exitButtonSetOnMouseExited(){
        exitButton.setText(Main.languageProperties.getProperty("exit"));
        exitButton.setTextFill(Color.BLACK);
    }
}
