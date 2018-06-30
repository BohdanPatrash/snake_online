package bin.snake;

import bin.food.Apple;
import bin.food.Food;
import bin.gamelogic.GameField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.List;


public class Snake{
    private Pane pane;
    private int size = 5;
    private SnakeList body;
    private SquareDOT head;
    private double x;
    private double y;
    private int step = 11;
    private int horizontal = 0;
    private int vertical = -step;
    private int spawn = 0;// 0 -> right ; 1 -> down ; 2 -> left ; 3 -> up
    private boolean lost = false;
    private int direction = 0; // 0 -> right ; 1 -> down ; 2 -> left ; 3 -> up
    private Food tempF;

    public int getSize() {return body.size();}

    public Snake(Pane pane){
        body = new SnakeList();
        head = body.getHead();
        head.setFill(Color.web("#d44a0e"));
        for (int i = 2; i <size ; i++) {
            body.add(new SquareDOT());
        }
        this.pane = pane;
    }

    public void spawn(){
        SquareDOT dot = getHead();
        if (spawn == 0){
            setDirection(0);
            for(int i = 0; i < body.size(); i++){
                dot.setX(GameField.x/2+66-i*dot.getWidth());
                dot.setY(GameField.y/2);
                if(dot.hasNext()) dot = dot.getNext();
            }
        }else if (spawn == 1){
            setDirection(1);
            for(int i = 0; i < body.size(); i++){
                dot.setX(GameField.x/2);
                dot.setY(GameField.y/2+66-i*dot.getHeight());
                if(dot.hasNext()) dot = dot.getNext();
            }
        }else if (spawn == 2){
            setDirection(2);
            for(int i = 0; i < body.size(); i++){
                dot.setX(GameField.x/2-66+i*dot.getWidth());
                dot.setY(GameField.y/2);
                if(dot.hasNext()) dot = dot.getNext();
            }
        }else if (spawn == 3){
            setDirection(3);
            for(int i = 0; i < body.size(); i++){
                dot.setX(GameField.x/2);
                dot.setY(GameField.y/2-66+i*dot.getHeight());
                if(dot.hasNext()) dot = dot.getNext();
            }
        }
    }

    public void show(){
        SquareDOT dot = getHead();
        for (int i = 0; i < body.size() ; i++) {
            pane.getChildren().add(dot);
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

    public boolean eats(List<Food> food){
        for (Food f:
             food) {
            if(x+5.5 == f.getCenterX() && y+5.5 == f.getCenterY()){
                f.causes(this);
                pane.getChildren().remove(f);
                food.remove(f);
                return true;
            }
        }
        return false;
    }

    public boolean hits_border(){
        return (x < GameField.layoutX ||
                x > GameField.x-11 ||
                y < GameField.layoutY ||
                y > GameField.y-11);
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

    public SnakeList getBody(){return body;}

    public SquareDOT getHead(){
        return head;
    }

    public boolean hasLost(){
        return lost;
    }

    public void loose(){
        lost = true;
    }

    public int getDirection(){
        return direction;
    }

    public void setDirection(int direction){
        this.direction = direction;
        if(direction == 3){
            vertical=-step;
            horizontal=0;
        }else if(direction == 2){
            vertical=0;
            horizontal=-step;
        }else if(direction == 1){
            vertical=step;
            horizontal=0;
        }else if( direction == 0){
            vertical=0;
            horizontal=step;
        }
    }

    public int getSpawn(){
        return spawn;
    }

    public void setSpawn( int spawn){
        this.spawn = spawn;
    }

    public Food getTempF(){
        return tempF;
    }
}
