package bin;

import bin.gamelogic.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MenuController{

    static public Scene OptionsScene;

    @FXML
    private TextField nameField;

    @FXML
    private Button StartButton;

    @FXML
    private Button ExitButton;

    @FXML
    private Button OptionButton;

    @FXML
    public void initialize() throws Exception{
        nameField.setText(Main.name);
        OptionsScene = new Scene(FXMLLoader.load(getClass().getResource("Options.fxml")), 440, 440);
        OptionsScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Main.window.setScene(Main.menu);
            }
            event.consume();
        });
        StartButton.setOnMouseEntered(event -> StartButton.setUnderline(true));
        StartButton.setOnMouseExited(event -> StartButton.setUnderline(false));

        OptionButton.setOnMouseEntered(event -> OptionButton.setUnderline(true));
        OptionButton.setOnMouseExited(event -> OptionButton.setUnderline(false));

        ExitButton.setOnMouseEntered(event -> {
            ExitButton.setText("NOOOO");
            ExitButton.setTextFill(Color.RED);
        });
        ExitButton.setOnMouseExited(event ->{
            ExitButton.setText("Exit");
            ExitButton.setTextFill(Color.BLACK);
        });
    }

    public void  ExitButtonPress(){
        Main.window.close();
    }


    public void StartBatonPress()throws  IOException{
        //Music.PlayMusic("src/media/SweetDreams.wav");
        saveName();
        new Game();
    }

    private void saveName()throws IOException{
        if (!Main.name.equals(nameField.getText())){
            fileText ft = new fileText("src/config.txt");
            ArrayList<String> Config = ft.getText();

            Main.name = nameField.getText();
            Config.set(0, Config.get(0).substring(0,Config.get(0).indexOf("=")+1) + nameField.getText());
            PrintWriter writer = new PrintWriter("src/config.txt", "UTF-8");
            while (!Config.isEmpty()){
                writer.println(Config.get(0));
                Config.remove(0);
            }
            writer.close();
        }
    }

    public void OptionButtonPress() throws Exception{
        Main.window.setScene(OptionsScene);
    }

}
