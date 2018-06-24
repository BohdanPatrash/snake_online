package bin.food;

import bin.snake.Snake;
import javafx.scene.paint.Color;

public class Apple extends Food{
    @Override
    public void apearence() {
        setFill(Color.web("#ff0000"));
    }

    @Override
    public void causes(Snake snake) {
        snake.grow();
    }
}
