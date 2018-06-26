package bin.fxController;

import bin.Main;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileOutputStream;
import java.io.IOException;

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
    public Slider masterVolume;
    @FXML
    public Slider musicVolume;

    @FXML
    public Label messageLable;
    @FXML
    public Label skinsLable;
    @FXML
    public Label soundLable;
    @FXML
    public Label controlLable;
    @FXML
    public Label volumeLable;
    @FXML
    public Label musicVolumeLable;
    @FXML
    public Label languageLable;
    @FXML
    public Label ex0Label;

    @FXML
    public Button saveButton;
    @FXML
    public Button backButton;
    @FXML
    public Button resetButton;

    @FXML
    public MenuButton languageMB;

    @FXML
    public void initialize()throws IOException{

        MenuItem ENitem = new MenuItem("English");
        MenuItem PLitem = new MenuItem("Polski");
        MenuItem UAitem = new MenuItem("Українська");
        MenuItem RUitem = new MenuItem("Русский");

        ImageView UAflag = new ImageView(new Image("images/UA.png"));
        UAitem.setGraphic(UAflag);
        languageMB.getItems().add(UAitem);

        ImageView ENflag = new ImageView(new Image("images/EN.png", 20, 14, false,false));
        ENitem.setGraphic(ENflag);
        languageMB.getItems().add(ENitem);

        ImageView PLflag = new ImageView(new Image("images/PL.png"));
        PLitem.setGraphic(PLflag);
        languageMB.getItems().add(PLitem);

        ImageView RUflag = new ImageView(new Image("images/RU.png"));
        RUitem.setGraphic(RUflag);
        languageMB.getItems().add(RUitem);

        skinsLable.setText(Main.languageProperties.getProperty("skin"));
        soundLable.setText(Main.languageProperties.getProperty("sound"));
        messageLable.setText(Main.languageProperties.getProperty("saved"));
        controlLable.setText(Main.languageProperties.getProperty("control"));
        volumeLable.setText(Main.languageProperties.getProperty("volumeEx"));
        languageLable.setText(Main.languageProperties.getProperty("languageEx"));
        saveButton.setText(Main.languageProperties.getProperty("save"));
        backButton.setText(Main.languageProperties.getProperty("back"));
        resetButton.setText(Main.languageProperties.getProperty("reset"));
        musicVolumeLable.setText(Main.languageProperties.getProperty("musicVolumeEx"));
        Ar2_RB.setText(2 + " " + Main.languageProperties.getProperty("arrows"));
        Ar4_RB.setText(4 + " " + Main.languageProperties.getProperty("arrows"));

        messageLable.setVisible(false);
        ex0Label.setVisible(false);
        takeValues();

        ImageView UAflag1 = new ImageView(new Image("images/UA.png"));
        ImageView ENflag1 = new ImageView(new Image("images/EN.png", 20,14,false,false));
        ImageView PLflag1 = new ImageView(new Image("images/PL.png"));
        ImageView RUflag1 = new ImageView(new Image("images/RU.png"));
        UAitem.setOnAction(event -> {
            languageMB.setGraphic(UAflag1);
            languageMB.setText("Українська");
            changeLanguageConfiguration("UA");
            if (!Main.language.equals("UA"))
                visibleLable(ex0Label, "Будь ласка, \nперезапустіть гру", 2);
        });
        PLitem.setOnAction(event -> {
            languageMB.setGraphic(PLflag1);
            languageMB.setText("Polski");
            changeLanguageConfiguration("PL");
            if (!Main.language.equals("PL"))
                visibleLable(ex0Label, "Proszę ponownie\n uruchomić grę", 2);
        });
        ENitem.setOnAction(event -> {
            languageMB.setGraphic(ENflag1);
            languageMB.setText("English");
            changeLanguageConfiguration("EN");
            if (!Main.language.equals("EN"))
                visibleLable(ex0Label, "Please, \nrestart the game", 2);
        });
        RUitem.setOnAction(event -> {
            languageMB.setGraphic(RUflag1);
            languageMB.setText("Русский");
            changeLanguageConfiguration("RU");
            if (!Main.language.equals("RU"))
                visibleLable(ex0Label, "Пожалуйста, \nперезагрузите игру", 2);
        });

        if (Main.language.equals("RU")){
            languageMB.setGraphic(RUflag1);
            languageMB.setText("Русский");
        }
        if (Main.language.equals("UA")){
            languageMB.setGraphic(UAflag1);
            languageMB.setText("Українська");
        }
        if (Main.language.equals("PL")){
            languageMB.setGraphic(PLflag1);
            languageMB.setText("Polski");
        }
        if (Main.language.equals("EN")){
            languageMB.setGraphic(ENflag1);
            languageMB.setText("English");
        }
        languageMB.setMinWidth(135);
    }

    private void visibleLable(Label label, String text, int time){
        label.setVisible(true);
        label.setText(text);
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(time)
        );
        visiblePause.setOnFinished(
                event -> label.setVisible(false)
        );
        visiblePause.play();
    }

    private void changeLanguageConfiguration(String language){
        try {
            FileOutputStream fos = new FileOutputStream("src/bin/properties/config.properties");
            Main.configProperties.setProperty("language", language);
            Main.configProperties.store(fos, null);
            fos.close();
        } catch (IOException e) {
            System.err.println("Error, file does not exist");
        }
    }

    private void takeValues(){
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
        masterVolume.setValue(Main.volume);
        musicVolume.setValue(Main.musicVolume);
    }

    public void backButtonPress(){
        takeValues();
        Main.window.setScene(Main.menu);
    }

    public void saveButtonPress()throws IOException{
        String control;
        if (WASD_RB.isSelected()) control = "WASD";
        else if (AD_RB.isSelected()) control = "AD";
        else if (Ar2_RB.isSelected()) control = "2arrows";
        else control = "4arrows";

        Main.controller = control;
        Main.volume = masterVolume.getValue();
        Main.musicVolume = musicVolume.getValue();

        try {
            FileOutputStream fos = new FileOutputStream("src/bin/properties/config.properties");
            Main.configProperties.setProperty("controller", control);
            Main.configProperties.setProperty("volume", String.valueOf(masterVolume.getValue()));
            Main.configProperties.setProperty("musicVolume", String.valueOf(musicVolume.getValue()));

            Main.configProperties.store(fos, null);
            fos.close();
        } catch (IOException e) {
            System.err.println("Error, file does not exist");
        }

        visibleLable(messageLable, messageLable.getText(), 1);
    }

    public void resetButtonPress(){
        Ar4_RB.setSelected(true);
        masterVolume.setValue(100);
        musicVolume.setValue(100);
    }

}
