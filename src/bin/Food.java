package bin;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Food extends Rectangle{
    Food(){
        Random rand = new Random();
        setX(rand.nextInt(40)*11);
        setY(rand.nextInt(40)*11);
        setFill(Color.web("#ee04f0"));
        setWidth(11);
        setHeight(11);
    }
}
