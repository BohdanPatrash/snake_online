package bin;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;



public class Snake{
    private Pane pane;
    private int size = 4;
    private SnakeList body;
    private SquareDOT head;
    private int step = 11;
    private int horizontal = 0;
    private int vertical = -step;

    public int getSize() {
        return size;
    }

    Snake(Pane pane){
        body = new SnakeList(size);
        head = body.getHead();
        head.setFill(Color.web("#d44a0e"));
        for (int i = 2; i <size ; i++) {
            body.add(new SquareDOT());
        }
        this.pane = pane;
    }

    public void show(){
        SquareDOT dot = body.getHead();
        for(int i = 0; i < body.size(); i++){
            dot.setX(220);
            dot.setY(220+i*dot.getHeight());
            pane.getChildren().addAll(dot);
            if(dot.hasNext()) dot = dot.getNext();
        }
    }

    public void move(){
        body.getLast().setX(head.getX());
        body.getLast().setY(head.getY());
        body.add_second(body.getLast());
        body.delete_last();
        head.setX(head.getX()+horizontal);
        head.setY(head.getY()+vertical);
        head.toFront();
    }

    public void grow(){
        SquareDOT temp = new SquareDOT();
        temp.setX(body.getLast().getX());
        temp.setY(body.getLast().getY());
        body.add(temp);
        pane.getChildren().add(body.getLast());
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

    public SquareDOT getHead(){
        return head;
    }
}
