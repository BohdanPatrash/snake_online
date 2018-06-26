package bin.gamelogic;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameField extends Rectangle {
    public static int layoutX = 22;
    public static int layoutY = 22;
    public static int width = 660;
    public static int height = 660;
    public static int x = width + layoutX;
    public static int y = height + layoutY;

    GameField(){
        setX(layoutX);
        setY(layoutY);
        setFill(Color.web("#a1854d"));
        setWidth(width);
        setHeight(height);
        setStroke(Color.BLACK);
    }

}
