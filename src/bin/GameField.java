package bin;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameField extends Rectangle {
    public static int layout_x = 22;
    public static int layout_y = 22;
    public static int width = 660;
    public static int height = 660;
    public static int x = width + layout_x;
    public static int y = height + layout_y;

    GameField(){
        setX(layout_x);
        setY(layout_y);
        setFill(Color.web("#a1854d"));
        setWidth(width);
        setHeight(height);
        setStroke(Color.BLACK);
    }

}
