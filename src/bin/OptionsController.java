package bin;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;

public class OptionsController{

    @FXML
    public RadioButton WASD_RB;

    @FXML
    public RadioButton AD_RB;

    @FXML
    public RadioButton Ar2_RB;

    @FXML
    public RadioButton Ar4_RB;

    @FXML
    private CheckBox Music;

    @FXML
    private CheckBox Sounds;

    @FXML
    public void initialize(){
        switch (Main.controller){
            case "4arrows":{
                Ar4_RB.setSelected(true);
                break;
            }
            case "2arrows":{
                Ar2_RB.setSelected(true);
                break;
            }
            case "WASD":{
                WASD_RB.setSelected(true);
                break;
            }
            case "AD":{
                AD_RB.setSelected(true);
                break;
            }
            default:{
                Ar4_RB.setSelected(true);
                break;
            }
        }
        Music.setSelected(Main.music);
        Sounds.setSelected(Main.sounds);
    }

    public void BackButtonPress(){
        Main.window.setScene(Main.menu);
    }

    public void SaveButtonPress(){

    }

}
