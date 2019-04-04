package model;

import javafx.scene.shape.Polygon;
import model.GameObject;

public class Player extends GameObject {

    public Player(){
        super(new Polygon(0,-10, 30,0, 0,10));
    }

}
