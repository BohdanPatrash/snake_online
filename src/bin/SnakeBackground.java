package bin;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SnakeBackground extends Rectangle {
    SnakeBackground(){
        setWidth(Game.x);
        setHeight(Game.y);
        setFill(Color.web("#a38d72"));
    }
}
