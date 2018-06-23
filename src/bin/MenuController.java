package bin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController{

    @FXML
    private TextField nameField;

    @FXML
    private Button ExitButton;

    @FXML
    private Scene OptionsScene;

    @FXML
    public void initialize() throws Exception{
        OptionsScene = new Scene(FXMLLoader.load(getClass().getResource("Options.fxml")), 440, 440);
        OptionsScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Main.window.setScene(Main.menu);
            }
            event.consume();
        });
    }

    public void  ExitButtonPress(){
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }

    public void StartBatonPress(){
        new Game();
    }

    public void OptionButtonPress() throws Exception{
        Main.window.setScene(OptionsScene);
    }

}
