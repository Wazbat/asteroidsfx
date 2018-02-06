/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asterioidsfx;


import java.util.Random;
import javafx.scene.shape.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Wazbat
 */


public class AsterioidsFX extends Application {
    double velPlayerX = 0;
    double velPlayerY =0;
    double anguloPlayer = 0;
    boolean accelerando = false;
    boolean ROTIZQ = false;
    boolean ROTDIR = false;
    boolean DISPARO = false;
    final int VEL_ROTACION = 5;
    final double POTENCIA_NAVE = 0.1;
    final double MAX_VELOCIDAD_NAVE = 8;
    boolean VEL_MAXIMA = false;
    int flash = 0;
    int delay= 0;
    double velPlayer = 2;
    double variaX=0;
    double variaY=0;
    int escudos=3;
    Circle[] iconosEscudo;
    Polygon[] asteroide; 
    int numeroAsteroides = 2;
    
    double radiusescudo=0;
    boolean ESCUDO=false;
    boolean VENTANACOMPLETA=false;
    double velAsterioideX = 0;
    double velAsterioideY = 0;
    double velBalaX = 0;
    double velBalaY = 0;
    double velBala = 10;
    
    
    private boolean getCollisionCircle(Circle obj1, Circle obj2){
        if (Shape.intersect(obj1, obj2).getBoundsInLocal().isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    private boolean getCollisionPoly(Circle obj1, Polygon obj2){
        if (Shape.intersect(obj1, obj2).getBoundsInLocal().isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    
    @Override
    
    public void start(Stage primaryStage) {
        
        
        
        Pane root = new Pane();        
        Scene scene = new Scene(root, 1280, 720);
        scene.setFill(Color.BLACK);
        
        primaryStage.setTitle("AsteroidsFX");
        primaryStage.setScene(scene);
        primaryStage.show();
        Pane player = new Pane();
        player.setMinHeight(5);
        player.setMaxHeight(5);
        player.setMinWidth(10);
        Polygon nave = new Polygon();        
        nave.getPoints().addAll(new Double[]{
        7.5, -10.0,
        15.0, 10.0,
        7.5, 5.0,
        0.0, 10.0 });   
        Polygon fuego = new Polygon(); 
        fuego.getPoints().addAll(new Double[]{
        2.0, 5.0,
        3.0, 15.0,
        6.5, 12.0,
        8.0, 18.0,
        10.0, 12.0,
        12.0, 15.0,
        13.0, 5.0 });
        Polygon fuego2 = new Polygon(); 
        fuego2.getPoints().addAll(new Double[]{
        2.0, 5.0,
        3.0, 10.0,
        6.5, 17.0,
        8.0, 13.0,
        10.0, 17.0,
        12.0, 10.0,
        13.0, 5.0 });
        
        //Circulo escudo
        Circle escudo = new Circle();        
        Circle escudoin = new Circle();
        escudo.setRadius(10);
        escudo.setCenterX(-200);
        escudoin.setCenterX(-200);
        escudoin.setRadius(0);
        escudo.setFill(Color.WHITE);
        escudoin.setFill(Color.BLACK);
        escudo.setVisible(false);
        escudoin.setVisible(false);
        
        
        //Agrega dentro del contenedor "player"
        player.getChildren().addAll(nave, fuego, fuego2);
        fuego.toBack();
        fuego2.toBack();
        
        nave.setFill(Color.WHITE);
        fuego.setFill(Color.RED);
        fuego2.setFill(Color.ORANGE);
        fuego.setVisible(false);
        fuego2.setVisible(false);
        root.getChildren().addAll(player, escudo, escudoin);
        escudoin.toBack();
        escudo.toBack();
        player.setLayoutX(scene.getWidth()/2);
        player.setLayoutY(scene.getHeight()/2);
        
        
        Polygon bala = new Polygon(); 
        bala.getPoints().addAll(new Double[]{
        0.0, 0.0,
        1.0, 0.0,
        1.0, 15.0,
        0.0, 15.0});
        bala.setFill(Color.WHITE);
        bala.setLayoutX(50);
        bala.setLayoutY(50);
        root.getChildren().addAll(bala);
        
        //Asteroid Test
        asteroide = new Polygon[numeroAsteroides];
        for(int i=1; i<=numeroAsteroides; i+=1) {
            asteroide[i] = new Polygon();        
            asteroide[i].getPoints().addAll(new Double[]{
            0.0, -0.0,
            -1.0, -7.0,
            -10.0, -10.0,
            -8.0, -27.0,
            -3.0, -31.0,
            8.0, 
            0.0, 10.0 });
            root.getChildren().add(asteroide[i]);
            }
        Circle asteroide = new Circle();
        asteroide.setRadius(50);
        asteroide.setFill(Color.WHITE);
        root.getChildren().addAll(asteroide);
        Random random=new Random();
        
        velAsterioideX = random.nextDouble() * 2 - 1 ;
        velAsterioideY =  random.nextDouble() * 2 - 1 ;
        System.out.println(velAsterioideX);
        System.out.println(velAsterioideY);
        //Iconos para mostrar escudos
        iconosEscudo = new Circle[escudos];
        for(int i=1; i<=escudos; i+=1) {
            iconosEscudo[i-1] = new Circle(i*40,20,10,Color.WHITE);
            root.getChildren().add(iconosEscudo[i-1]);
        } 
        //
        //AnimationTImer, un bucle que se repite a 60 Hz
        //
        AnimationTimer animacion = new AnimationTimer() {
            @Override
            public void handle(long now){

            player.relocate(player.getLayoutX()+velPlayerX, player.getLayoutY()+velPlayerY);
            
            asteroide.setLayoutX(asteroide.getLayoutX()+velAsterioideX);
            asteroide.setLayoutY(asteroide.getLayoutY()+velAsterioideY);
            //
            //Decay Velocidad
            //
            if (velPlayerX > 0) {
                velPlayerX=velPlayerX-0.005;
            }
            if (velPlayerX < 0) {
                velPlayerX=velPlayerX+0.005;
            }
            if (velPlayerY > 0) {
                velPlayerY=velPlayerY-0.005;
            }
            if (velPlayerY < 0) {
                   velPlayerY=velPlayerY+0.005; 
            }
            
            
           
            //
            //Usado para la rotacion de la nave
            //
            if (ROTIZQ){
                anguloPlayer -=VEL_ROTACION;   
            } else if(ROTDIR){
                anguloPlayer +=VEL_ROTACION;    
            }
            if (anguloPlayer>360){
                anguloPlayer=0;
            } else if (anguloPlayer<0){
                anguloPlayer=360;
            }
            player.setRotate(anguloPlayer);
            
            //Angulo rotacion
            variaX=Math.sin(Math.toRadians(anguloPlayer));
            variaY=Math.cos(Math.toRadians(anguloPlayer))*-1;
            //
            //Velocidad Maxima
            //
            if (Math.hypot(velPlayerX, velPlayerY) > MAX_VELOCIDAD_NAVE){
               VEL_MAXIMA = true;
            } else{
                VEL_MAXIMA = false;

            }
            
            
           
            //
            //Disparo de bala
            //
            if (DISPARO) {
                if (delay>10){
                    delay =0;
                }
                if (delay<5) {
                bala.setVisible(true);
                bala.setLayoutX(bala.getLayoutX()+velBalaX);
                bala.setLayoutY(bala.getLayoutY()+velBalaY);
                } else{
                
                }
            } else {
                delay = 0;
                bala.setVisible(false);
                bala.setLayoutX(player.getLayoutX());
                bala.setLayoutY(player.getLayoutY());
                bala.setRotate(anguloPlayer);
                velBalaX = velPlayerX+velBala*variaX;
                velBalaY = velPlayerY+velBala*variaY;
                DISPARO = false;
            }
            
                
            if (accelerando){
                //aplicacion del porcentaje calculado antes a la nave
                if (VEL_MAXIMA == false) {
                    velPlayerX=velPlayerX+POTENCIA_NAVE*variaX;
                    velPlayerY=velPlayerY+POTENCIA_NAVE*variaY;
                }
                
                
                
                
                //Usado para la Animacion del Fuego
                if (flash > 10){
                    flash = 0;
                }
                if(flash< 5){
                    fuego.setVisible(true);
                    fuego2.setVisible(false);
                } else{
                    fuego.setVisible(false);
                    fuego2.setVisible(true);
                }
                flash++;
            } else{
                fuego.setVisible(false);
                fuego2.setVisible(false);
                flash = 0;
            }
            //  
            //Collision
            //
            if (getCollisionCircle(asteroide, escudo)) {
                asteroide.setVisible(false);
                System.out.println("Collison de Escudo");
            }
            if (getCollisionPoly(asteroide, bala)) {
                asteroide.setVisible(false);
                System.out.println("Collison de Bala");
            }
            
            
            //
            //Escudo
            if (ESCUDO) {
                if (escudos>0) {
                   escudo.setVisible(true);
                   escudoin.setVisible(true);
                   escudo.setRadius(escudo.getRadius()+5);
                   escudoin.setRadius(escudoin.getRadius()+5.3);
                   escudo.setCenterX(player.getLayoutX());
                   escudo.setCenterY(player.getLayoutY());
                   escudoin.setCenterX(player.getLayoutX());
                   escudoin.setCenterY(player.getLayoutY());
                   
                   
                   
                }
                if (escudo.getRadius()<escudoin.getRadius()) {
                    ESCUDO=false;
                    iconosEscudo[escudos-1].setVisible(false);
                    escudo.setVisible(false);
                    escudoin.setVisible(false);
                    escudo.setRadius(10);
                    escudoin.setRadius(0);
                    escudos-=1;
                    
                    System.out.println(escudos);
                    
                }
                
            } else{
                escudo.setCenterX(-200);
                escudoin.setCenterY(-200);
            }
            
            
            
            //
            //Salida de la escena
            //
            if (player.getLayoutX() > root.getWidth()){
                player.setLayoutX(0);
            }
            if (player.getLayoutX() < 0){
                player.setLayoutX(root.getWidth());
            }
            if (player.getLayoutY() > root.getHeight()){
                player.setLayoutY(0);
            }
            if (player.getLayoutY() < 0){
                player.setLayoutY(root.getHeight());
            }
            //Asteroide
            if (asteroide.getLayoutX() > root.getWidth()){
                asteroide.setLayoutX(0);
            }
            if (asteroide.getLayoutX() < 0){
                asteroide.setLayoutX(root.getWidth());
            }
            if (asteroide.getLayoutY() > root.getHeight()){
                asteroide.setLayoutY(0);
            }
            if (asteroide.getLayoutY() < 0){
                asteroide.setLayoutY(root.getHeight());
            }
            }
            
        };
        
        
        
        
    //Entradas de teclado
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()){
                case UP:
                    accelerando=true;
                    break;
                case DOWN:
                    ESCUDO=true;
                    break;
                case LEFT:
                    ROTIZQ=true;
                    break;
                case RIGHT:
                    ROTDIR=true;
                    break;
                case SPACE:
                    DISPARO=true;
                    break;
                case F11:
                    if (VENTANACOMPLETA==false) {
                        VENTANACOMPLETA=true;
                        System.out.println("Ventana Completa");
                    } else if(VENTANACOMPLETA){
                        VENTANACOMPLETA=false;
                        System.out.println("Ventana Normal");
                    }
                    break;
                
            }
        });
        scene.setOnKeyReleased((KeyEvent event) -> {
            
            switch(event.getCode()){
                case UP:
                    accelerando=false;
                    break;
                case DOWN:
                    break;
                case LEFT:
                    ROTIZQ=false;
                    break;
                case RIGHT:
                    ROTDIR=false;
                    break;
                case SPACE:
                    DISPARO=false;
                    break;
            }
        });
    animacion.start();           
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
}