package bin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage window) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        window.setTitle("Super snake");
        window.setScene(new Scene(root, 440, 440));
        window.getIcons().add(new Image("images/serpent-frontal-head.png"));
        window.setResizable(false);
        window.show();
    }


    public static void main(String[] args){
        launch(args);
    }
}