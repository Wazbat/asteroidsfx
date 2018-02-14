/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asterioidsfx;


import java.awt.Button;
import javafx.scene.image.Image;
import java.util.ArrayList;
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


public class Main extends Application {
    int estilo = 0;
    double velPlayerX = 0;
    double velPlayerY =0;
    double anguloPlayer = 0;
    boolean accelerando = false;
    boolean rotIzq = false;
    boolean rotDir = false;
    boolean disparo = false;
    final int VEL_ROTACION = 5;
    final double POTENCIA_NAVE = 0.1;
    final double MAX_VELOCIDAD_NAVE = 8;
    boolean velMaxima = false;
    int flash = 0;
    int delay= 0;
    double velPlayer = 2;
    double variaX=0;
    double variaY=0;
    int escudos=3;
    Circle[] iconosEscudo;
    Polygon[] asteroide;
    int numeroAsteroides = 3;
    double[] velXAsteroide;
    double[] velYAsteroide;
    double[] velRotacionAsteroide;
    double radiusescudo=0;
    boolean escudoActivo=false;
    boolean ventanaCompleta=false;

    
    ArrayList<Bala> balas = new ArrayList();


    
    
    private boolean getCollisionAsteroideEscudo(Asteroide asteroide, Circle circle){
        if (Shape.intersect(asteroide.getPolygon(), circle).getBoundsInLocal().isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    private boolean getCollisionAsteroidePoly(Asteroide asteroide, Polygon poly){
        if (Shape.intersect(asteroide.getPolygon(), poly).getBoundsInLocal().isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    private boolean getCollisionAsteroideBala(Asteroide asteroide, Bala bala){
        if (Shape.intersect(asteroide.getPolygon(), bala.getPolygon()).getBoundsInLocal().isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    
    @Override
    
    public void start(Stage primaryStage) {
        
        Random random=new Random();
        
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
        //Estilo
        nave.setId("nave");
        fuego.setId("fuego1");
        fuego2.setId("fuego2");
        escudo.setId("escudo");
        escudoin.setId("escudoDentro");
        
//        Image cambiaestilo = new Image("img/ok.png", 100, 100, false, false);
//        cambiaestilo
//        cambiaestilo.(new EventHandler() {            
//            
//            public void handle(ActionEvent event) {
//                scene.getStylesheets().clear();
//                switch(estilo){
//                case 0:
//                    estilo=1;
//                    break;
//                case 1:
//                    scene.getStylesheets().add("css/estilo1.css");
//                    break;
//                case 2:
//                    scene.getStylesheets().add("css/estilo2.css");
//                    estilo=3;
//                    break;
//                case 3:
//                    scene.getStylesheets().add("css/estilo3.css");
//                    estilo=0;
//                    break;
//            } 
//            }
//        });
                                 
        

        ArrayList<Asteroide> listaasteroides = new ArrayList();
        
        for (int i = 0; i < numeroAsteroides; i++) {
          Asteroide asteroide = new Asteroide(0,random.nextDouble() * 2 - 1 );
          listaasteroides.add(asteroide);
          asteroide.setVelX(random.nextDouble() * 2 - 1 );
          asteroide.setVelY(random.nextDouble() * 2 - 1 );
          root.getChildren().add(asteroide.getPolygon());
        };
        
        
        
        
        
       
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

            for(int i=0; i<listaasteroides.size(); i++) {
                listaasteroides.get(i).actualizar(root);
            }
            
            
            
            
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
            if (rotIzq){
                anguloPlayer -=VEL_ROTACION;   
            } else if(rotDir){
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
               velMaxima = true;
            } else{
                velMaxima = false;

            }
            
            
           
           
            //
            //Disparo de bala
            //
            
            if (disparo) {
                if (delay>10){
                    delay =0;
                }
                if (delay<1) {
                    Bala bala = new Bala(anguloPlayer, player.getLayoutX(), player.getLayoutY(), velPlayerX, velPlayerY);
                    balas.add(bala);
                    root.getChildren().add(bala.getPolygon());
                }
            
            delay++;
                
            } else {
                delay = 0;
                disparo = false;
            }
            for(int i=0; i<balas.size(); i++) {
                balas.get(i).actualizar(root); 
                    if (balas.get(i).vida < 0) {
                        balas.get(i).object.setVisible(false);
                        root.getChildren().remove(balas.get(i));
                        balas.remove(i);
                    }
            }
               
            if (accelerando){
                //aplicacion del porcentaje calculado antes a la nave
                if (velMaxima == false) {
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
            for (int i=0; i<listaasteroides.size(); i++) {
                if (getCollisionAsteroideEscudo(listaasteroides.get(i), escudo)) {
                    listaasteroides.get(i).object.setVisible(false);
                    root.getChildren().remove(listaasteroides.get(i));
                    listaasteroides.remove(i);
                    System.out.println("Collison de Escudo");
                }
                for (int j = 0; j <balas.size(); j++) {
                    if (getCollisionAsteroideBala(listaasteroides.get(i), balas.get(j))) {
                        listaasteroides.get(i).object.setVisible(false);
                        root.getChildren().remove(listaasteroides.get(i));
                        listaasteroides.remove(i);
                        System.out.println("Collison de Bala");
                    }   
                }

                if (getCollisionAsteroidePoly(listaasteroides.get(i), nave)) {
                    player.setVisible(false);
                    root.getChildren().remove(nave);
                    System.out.println("Collison de Nave");
                }
            }
            
            
            
            
            //
            //Escudo
            if (escudoActivo) {
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
                    escudoActivo=false;
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
//            for(int i=0; i<numeroAsteroides; i+=1) {
//                    if (asteroide[i].getLayoutX() > root.getWidth()){
//                    asteroide[i].setLayoutX(0);
//                }
//                if (asteroide[i].getLayoutX() < 0){
//                    asteroide[i].setLayoutX(root.getWidth());
//               }
//                if (asteroide[i].getLayoutY() > root.getHeight()){
//                    asteroide[i].setLayoutY(0);
//                }
//                if (asteroide[i].getLayoutY() < 0){
//                    asteroide[i].setLayoutY(root.getHeight());
//                }
//            }
            
            }
            
        };
        
        
        
        
    //Entradas de teclado
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()){
                case UP:
                    accelerando=true;
                    break;
                case DOWN:
                    escudoActivo=true;
                    break;
                case LEFT:
                    rotIzq=true;
                    break;
                case RIGHT:
                    rotDir=true;
                    break;
                case SPACE:
                    disparo=true;
                    break;
                case DIGIT1:
                    scene.getStylesheets().clear();
                    break;
                case DIGIT2:
                    scene.getStylesheets().clear();
                    scene.getStylesheets().add("css/estilo1.css");
                    break;
                case DIGIT3:
                    scene.getStylesheets().clear();
                    scene.getStylesheets().add("css/estilo2.css");
                    break;
                case DIGIT4:
                    scene.getStylesheets().clear();
                    scene.getStylesheets().add("css/estilo2.css");
                    break;

                case F11:
                    if (ventanaCompleta==false) {
                        ventanaCompleta=true;
                        System.out.println("Ventana Completa");
                    } else if(ventanaCompleta){
                        ventanaCompleta=false;
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
                    rotIzq=false;
                    break;
                case RIGHT:
                    rotDir=false;
                    break;
                case SPACE:
                    disparo=false;
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