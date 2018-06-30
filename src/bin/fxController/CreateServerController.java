package bin.fxController;

import bin.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CreateServerController {

    @FXML
    Label localIPLabel;
    @FXML
    Label portLabel;
    @FXML
    Label playersAmountLabel;

    @FXML
    TextField localIPTextField;
    @FXML
    TextField portTextField;

    @FXML
    Button createAndPlayButton;
    @FXML
    Button backButton;

    @FXML
    MenuButton playersAmountMenuButton;

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
        //                                                          !!!
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
