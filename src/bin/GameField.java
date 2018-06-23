package bin;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class GameField extends Rectangle {
    GameField(){

        setFill(Color.web("#a1854d"));
        setWidth(880);
        setHeight(880);
        setStroke(Color.BLACK);

    }
}
