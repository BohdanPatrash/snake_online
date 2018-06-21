package bin;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class Game {

    public static void start(){
        Pane gameView = new Pane();
        gameView.setPrefSize(400,300);

        Main.window.setScene(new Scene(gameView) );
    }
}
