package bin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
static  Stage window;
static  Scene menu;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        window = primaryStage;
        menu = new Scene(root, 440, 440);
        window.setTitle("Super snake");
        window.setScene(menu);
        window.getIcons().add(new Image("images/icon.png"));
        window.setResizable(false);
        window.show();
    }


    public static void main(String[] args){
        launch(args);
    }
}
