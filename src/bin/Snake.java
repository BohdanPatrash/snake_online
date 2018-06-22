package bin;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;


public class Snake{
    private Pane pane;
    private int size = 4;
    private List<SquareDOT> body = new LinkedList<>();
    public int getSize() {
        return size;
    }
    private SquareDOT head = new SquareDOT();
    Snake(Pane pane){
        head.setFill(Color.web("#d44a0e"));
        body.add(head);
        for (int i = 1; i <size ; i++) {
            body.add(new SquareDOT());
        }
        this.pane = pane;
    }
    public void show(){
        int i=0;
        for (SquareDOT dot : body) {
            dot.setLayoutX(220);
            dot.setLayoutY(220+i*dot.getHeight());
            pane.getChildren().addAll(dot);
            i++;
        }
    }

}
