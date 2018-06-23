package bin;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
    public void initialize()throws IOException{
        TakeValues();
    }


    public void TakeValues(){
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
        TakeValues();
        Main.window.setScene(Main.menu);
    }

    public void SaveButtonPress()throws IOException{
        fileText ft = new fileText("src/config.txt");

        ArrayList<String> Config = ft.getText();

        String control;
        if (WASD_RB.isSelected()) control = "WASD";
        else if (AD_RB.isSelected()) control = "AD";
        else if (Ar2_RB.isSelected()) control = "2arrows";
        else control = "4arrows";

        Config.set(2, Config.get(2).substring(0,Config.get(2).indexOf("=")+1) + control);
        Config.set(3, Config.get(3).substring(0,Config.get(3).indexOf("=")+1) + Music.isSelected());
        Config.set(4, Config.get(4).substring(0,Config.get(4).indexOf("=")+1) + Sounds.isSelected());

        Main.controller = control;
        Main.sounds = Sounds.isSelected();
        Main.music = Music.isSelected();

        PrintWriter writer = new PrintWriter("src/config.txt", "UTF-8");
        while (!Config.isEmpty()){
            writer.println(Config.get(0));
            Config.remove(0);
        }
        writer.close();
    }

    public void ResetButtonPress(){
        Ar4_RB.setSelected(true);
        Music.setSelected(true);
        Sounds.setSelected(true);
    }

}
