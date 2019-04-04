package model;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import controller.AsteroidsApp;

import java.util.ArrayList;
import java.util.List;

public class LogicService extends Application {

    private Stage primaryStage;
    private Pane root;
    private GameObject player;

    private BooleanProperty leftPressed = new SimpleBooleanProperty();
    private BooleanProperty rightPressed = new SimpleBooleanProperty();
    private BooleanProperty spacePressed = new SimpleBooleanProperty();
    private BooleanProperty upPressed = new SimpleBooleanProperty();

    private long earliestNextShot = System.currentTimeMillis() + 200;

    private List<GameObject> bullets = new ArrayList<>();
    private List<GameObject> asteroids = new ArrayList<>();

    public LogicService() throws Exception{
        start(new Stage());
    }

    private void addBullet(GameObject Bullet, double x, double y){
        bullets.add(Bullet);
        addGameObject(Bullet,x,y);
    }

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(600,600);


        player = new Player();
        addGameObject(player, 300, 300);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
        return root;
    }

    private void addAsteroid(GameObject Asteroid, double x, double y){
        asteroids.add(Asteroid);
        addGameObject(Asteroid,x,y);
    }

    private void addGameObject(GameObject object, double x, double y){
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    private void onUpdate(){
        for (GameObject Bullet : bullets){
            for (GameObject Asteroid : asteroids){
                if (Bullet.isColliding(Asteroid)){
                    Bullet.setAlive(false);
                    Asteroid.setAlive(false);
                    //bij verdere uitwerking scattermethod maken die asteroid in stukken breekt

                    root.getChildren().removeAll(Bullet.getView(), Asteroid.getView());
                    //objecten uit view verwijderen
                }
            }
        }

        for (GameObject Asteroid : asteroids) {
            if (player.isColliding(Asteroid)) {
                player.setAlive(false);
                Asteroid.setAlive(false);
                //game over call
            }
        }
        bullets.removeIf(GameObject::isDead);
        asteroids.removeIf(GameObject::isDead);

        bullets.forEach(GameObject::update);
        asteroids.forEach(GameObject::update);
        //objecten uit model verwijderen

        //asteroids.forEach(GameObject::floatSpeed);
        player.update();
        if (Math.random() < 0.03){
            addAsteroid(new AsteroidsApp.Asteroid(), Math.random() * root.getPrefWidth(),
                    Math.random() * root.getPrefHeight());
            asteroids.get(asteroids.size()-1).floatSpeed();
        }
    }

    public void gameStart() throws Exception{
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT){
                leftPressed.set(true);
            }
            else if (e.getCode() == KeyCode.RIGHT){
                rightPressed.set(true);
            }
            else if (e.getCode() == KeyCode.SPACE){
                spacePressed.set(true);
            }
            else if (e.getCode() == KeyCode.UP){
                upPressed.set(true);
            }
        });

        primaryStage.getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT){
                leftPressed.set(false);
            }
            else if (e.getCode() == KeyCode.RIGHT){
                rightPressed.set(false);
            }
            else if (e.getCode() == KeyCode.SPACE){
                spacePressed.set(false);
            }
            else if (e.getCode() == KeyCode.UP){
                upPressed.set(false);
            }
        });
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (leftPressed.get()){player.rotateLeft();}
                if (rightPressed.get()){player.rotateRight();}
                if (upPressed.get()){player.moveForward();}
                if (!upPressed.get()){player.decaySpeed();}
            }
        };

        timer.start();

        AnimationTimer bulletTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (spacePressed.get()){
                    if(System.currentTimeMillis() > earliestNextShot) {
                        Bullet bullet = new Bullet();
                        bullet.setVelocity(player.getAngle().normalize().multiply(3.5));
                        addBullet(bullet, player.getView().getTranslateX() + 15, player.getView().getTranslateY());

                        earliestNextShot = System.currentTimeMillis() + 200;
                    }
                }
            }
        };
        bulletTimer.start();
    }

    @Override
    public void start(Stage stage) throws Exception {

        this.primaryStage = stage;
        gameStart();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
