package bin;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

public abstract class Food extends Circle{
    Food(){
        Random rand = new Random();
        setCenterX(rand.nextInt(Game.x/11)*11+5.5);
        setCenterY(rand.nextInt(Game.y/11)*11+5.5);
        setRadius(5.5);
        setStroke(Color.BLACK);
        apearence();
    }

    /**
     * in apearence you should describe the look of the food
     */
    public abstract void apearence();

    /**
     * in method causes() you have to describe what will happen to the snake when it its food
     */
    public abstract void causes(Snake snake);
}
