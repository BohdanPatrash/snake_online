package bin;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class GameField extends Rectangle {
    GameField(){
        Random rand = new Random();
        setFill(Color.web("#a1854d"));
        setWidth(1760);
        setHeight(1760);
        setLayoutX(-rand.nextInt(1320));
        setLayoutY(-rand.nextInt(1320));
    }
}
