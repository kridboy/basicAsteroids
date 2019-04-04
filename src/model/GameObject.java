package model;
import javafx.scene.Node;

import javafx.geometry.Point2D;

public class GameObject {
    private Node view;
    private Point2D velocity = new Point2D(0,0);
    private Point2D angle = new Point2D(0,0);

    private long timeDecay = System.currentTimeMillis() + 100;
    private boolean alive = true;
    private double i = 0;

    public GameObject(Node view){
        this.view = view;
    }

    public void update(){
        view.setTranslateX(view.getTranslateX() + velocity.getX());
        view.setTranslateY(view.getTranslateY() + velocity.getY());
    }

    public void setVelocity(Point2D velocity){
        this.velocity = velocity;
    }
    public Point2D getVelocity() {
        return velocity;
    }
    public Point2D getAngle() {
        return angle;
    }
    public Node getView(){
        return view;
    }


    public boolean isDead(){
        return !alive;
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }
    public double getRotate(){
        return view.getRotate();
    }

    public void rotateRight(){
        view.setRotate(view.getRotate() + 3);
        angle = new Point2D(Math.cos(Math.toRadians(getRotate())),Math.sin(Math.toRadians(getRotate())));
    }

    public void rotateLeft(){
        view.setRotate(view.getRotate() - 3);
        angle = new Point2D(Math.cos(Math.toRadians(getRotate())),Math.sin(Math.toRadians(getRotate())));
    }

    public void moveForward(){
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())),Math.sin(Math.toRadians(getRotate()))));
        i=1;
    }
    public void floatSpeed(){
        double x = Math.random();

            setVelocity(new Point2D((Math.random()*20-10)/10, (Math.random()*20-10)/10));
//Math.random()*20-10)/10)

    }

    public void decaySpeed(){
        if(System.currentTimeMillis() > timeDecay && i>0) {
            i = i - 0.02;
            setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())) * i, Math.sin(Math.toRadians(getRotate())) * i));
           timeDecay = System.currentTimeMillis() + 100;
        }
       // setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())),Math.sin(Math.toRadians(getRotate()))));
    }

    public boolean isColliding(GameObject other){
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }
}
