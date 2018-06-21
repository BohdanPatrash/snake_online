package bin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;


public class Game {

    public static void start(){
        Pane gameView = new Pane();
        Scene gameScene = new Scene(gameView);
        gameView.setPrefSize(440,440);
        GameField field = new GameField();

        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Main.window.setScene(Main.menu);
            }
            event.consume();
        });


        gameView.getChildren().addAll(field);
        Main.window.setScene(gameScene);
    }
}
