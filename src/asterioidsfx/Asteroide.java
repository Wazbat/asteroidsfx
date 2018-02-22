/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asterioidsfx;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Wazbat
 */
public class Asteroide {
    private double velRot =0;
    private double posX = 0;
    private double posY =0;
    private double velAsteroideX = 0;
    private double velAsteroideY = 0;
    private int tamano=0;
    Polygon object = new Polygon();
    
    public Polygon getPolygon(){
        return this.object;
    }
    public Asteroide(int fase,double posx, double posy, double rot){
        this.object.setId("asteroide");
        tamano=fase;
        if (tamano == 0) {
        object.getPoints().addAll(new Double[]{
            -192.0, -43.0,
            -64.0, -175.0,
            76.0, -161.0,
            176.0, -51.0,
            166.0, 45.0,
            32.0, 55.0,
            34.0, 169.0,
            -72.0, 215.0,
            -158.0, 167.0,
            -140.0, 69.0,
            -214.0, 9.0});
        } else if (tamano == 1) {
        object.getPoints().addAll(new Double[]{
            -192.0/2, -43.0/2,
            -64.0/2, -175.0/2,
            76.0/2, -161.0/2,
            176.0/2, -51.0/2,
            166.0/2, 45.0/2,
            32.0/2, 55.0/2,
            34.0/2, 169.0/2,
            -72.0/2, 215.0/2,
            -158.0/2, 167.0/2,
            -140.0/2, 69.0/2,
            -214.0/2, 9.0/2 });
        } else if (tamano == 2) {
        object.getPoints().addAll(new Double[]{
            0.0, 0.0,
            -10.0/4, 70.0/4,
            -100.0/4, 100.0/4,
            -70.0/4, 160.0/4,
            -80.0/4, 270.0/4,
            -30.0/4, 310.0/4,
            80.0/4, 250.0/4,
            100.0/4, 200.0/4,
            70.0/4, 170.0/4,
            70.0/4, 60.0/4 });
        }
        object.setFill(Color.WHITE);
        velRot=rot;
        posX=posx;
        posY=posy;
    }
        
    public void actualizar(Pane root){
        
        object.setRotate(object.getRotate()+velRot);
        posX=posX+velAsteroideX;
        posY=posY+velAsteroideY;
        if (posX > root.getWidth()){
                posX=0;
            }
            if (posX < 0){
                posX=root.getWidth();
            }
            if (posY > root.getHeight()){
                posY=0;
            }
            if (posY < 0){
                posY=root.getHeight();
            }
        object.setLayoutX(posX);
        object.setLayoutY(posY);
    }
    public double getPosX() {
        return this.posX;
    }
    public double getPosY() {
        return this.posY;
    }
    public int getFase()    {
       return this.tamano;
    }
    
    public void setVelX(double vel) {
        velAsteroideX=vel;
    }
    public void setVelY(double vel) {
        velAsteroideY=vel;
    }
}
