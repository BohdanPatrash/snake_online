package bin;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.*;


public class Snake{
    private Pane pane;
    private int size = 4;
    private List<SquareDOT> body = new LinkedList<>();
    private SquareDOT head = new SquareDOT();
    private int step = 11;
    private int horizontal = 0;
    private int vertical = -step;

    public int getSize() {
        return size;
    }

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
        for (SquareDOT dot :
             body) {
            dot.setX(220);
            dot.setY(220+i*dot.getHeight());
            pane.getChildren().addAll(dot);
            i++;
        }
    }

    public void move(){
        SquareDOT temp = body.get(body.size()-1);
        body.get(body.size()-1).setX(head.getX());
        body.get(body.size()-1).setY(head.getY());
        body.remove(body.size()-1);
        body.add(1,temp);
        head.setX(head.getX()+horizontal);
        head.setY(head.getY()+vertical);
    }

    public void up(){
        if(vertical==0){
            vertical=-step;
            horizontal=0;
        }
    }

    public void left(){
        if(horizontal==0){
            vertical=0;
            horizontal=-step;
        }
    }

    public void down(){
        if(vertical==0){
            vertical=step;
            horizontal=0;
        }
    }

    public void right(){
        if(horizontal==0){
            vertical=0;
            horizontal=step;
        }
    }
}
