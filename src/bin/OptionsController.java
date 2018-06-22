package bin;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;

public class OptionsController{


    public void BackButtonPress(){
        Main.window.setScene(Main.menu);
    }


}
