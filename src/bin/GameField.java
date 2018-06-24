package bin;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameField extends Rectangle {

    GameField(){
        setFill(Color.web("#a1854d"));
        setWidth(Game.x);
        setHeight(Game.y);
        setStroke(Color.BLACK);
    }

}
