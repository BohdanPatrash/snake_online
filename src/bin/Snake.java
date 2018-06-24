package bin;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;



public class Snake{
    private Pane pane;
    private int size = 20;
    private SnakeList body;
    private SquareDOT head;
    private double x;
    private double y;
    private int step = 11;
    private int horizontal = 0;
    private int vertical = -step;

    public int getSize() {return body.size();}

    Snake(Pane pane){
        body = new SnakeList();
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
            dot.setX(440);
            dot.setY(440+i*dot.getHeight());
            pane.getChildren().addAll(dot);
            if(dot.hasNext()) dot = dot.getNext();
        }
    }

    public void move(){
        body.getLast().setX(head.getX());
        body.getLast().setY(head.getY());
        body.add_second(body.getLast());
        body.delete_last();
        x = head.getX()+horizontal;
        y = head.getY()+vertical;
        head.setX(x);
        head.setY(y);
        head.toFront();
    }

    public void grow(){
        SquareDOT temp = new SquareDOT();
        temp.setX(body.getLast().getX());
        temp.setY(body.getLast().getY());
        body.add(temp);
        pane.getChildren().add(body.getLast());
    }

    public boolean eats(Apple food){
        if(getHead().getX()==food.getX()&&getHead().getY()==food.getY()){
            grow();
            return true;
        }else return false;
    }

    public boolean hits_border(){
        return (x<0||x>880||y<0|| y>880);
    }

    public boolean hit_self(){
        SquareDOT body_dot = body.getLast();
        for (int i = 0; i <body.size() ; i++) {
            if(body_dot.getX()==x&&body_dot.getY()==y){
                return true;
            }
            body_dot = body_dot.getPrevious();
        }
        return false;
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

    public SnakeList getBody(){return body;}

    public SquareDOT getHead(){
        return head;
    }
}
