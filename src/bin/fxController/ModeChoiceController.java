package bin.fxController;

import bin.Main;
import bin.gamelogic.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

import java.io.IOException;

public class ModeChoiceController {

    static private Scene joinServerScene;
    static private Scene createServerScene;

    @FXML
    Button joinServerButton;
    @FXML
    Button backButton;
    @FXML
    Button createServerButton;
    @FXML
    Button soloButton;

    @FXML
    public void initialize() throws IOException{
        setTexts();
        createJoinServerScene();
        createCreateServerScene();
    }

    private void setTexts(){
        joinServerButton.setText(Main.languageProperties.getProperty("joinServer"));
        backButton.setText(Main.languageProperties.getProperty("back"));
        createServerButton.setText(Main.languageProperties.getProperty("createServer"));
        soloButton.setText(Main.languageProperties.getProperty("solo"));
    }

    private void createJoinServerScene() throws IOException{
        joinServerScene = new Scene(FXMLLoader.load(getClass().getResource("../fx/JoinServer.fxml")), 440, 440);
        joinServerScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Main.window.setScene(MenuController.modeChoiceScene);
            }
            event.consume();
        });
    }

    private void createCreateServerScene() throws IOException{
        createServerScene = new Scene(FXMLLoader.load(getClass().getResource("../fx/CreateServer.fxml")), 440, 440);
        createServerScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Main.window.setScene(MenuController.modeChoiceScene);
            }
            event.consume();
        });
    }

    public void soloButtonPress(){
        new Game();
    }

    public void backButtonPress(){
        Main.window.setScene(Main.menu);
    }

    public void createServerButtonPress(){
        Main.window.setScene(createServerScene);
    }

    public void joinServerButtonPress(){
        Main.window.setScene(joinServerScene);
    }

    public void joinServerButtonSetOnMouseEntered(){
        joinServerButton.setUnderline(true);
    }

    public void joinServerButtonSetOnMouseExited(){
        joinServerButton.setUnderline(false);
    }

    public void createServerButtonSetOnMouseEntered(){
        createServerButton.setUnderline(true);
    }

    public void createServerButtonSetOnMouseExited(){
        createServerButton.setUnderline(false);
    }

    public void soloButtonSetOnMouseEntered(){
        soloButton.setUnderline(true);
    }

    public void soloButtonSetOnMouseExited(){
        soloButton.setUnderline(false);
    }

}
