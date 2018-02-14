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
    public double velAsteroideX = 0;
    public double velAsteroideY = 0;  
    Polygon object = new Polygon();
    
    public Polygon getPolygon(){
        return this.object;
    }
    public Asteroide(int fase, double rot){
        if (fase == 0) {
        object.getPoints().addAll(new Double[]{
            0.0, 0.0,
            -10.0, 70.0,
            -100.0, 100.0,
            -70.0, 160.0,
            -80.0, 270.0,
            -30.0, 310.0,
            80.0, 250.0,
            100.0, 200.0,
            70.0, 170.0,
            70.0, 60.0 });
        } else if (fase == 1) {
        object.getPoints().addAll(new Double[]{
            0.0, 0.0,
            -10.0/2, 70.0/2,
            -100.0/2, 100.0/2,
            -70.0/2, 160.0/2,
            -80.0/2, 270.0/2,
            -30.0/2, 310.0/2,
            80.0/2, 250.0/2,
            100.0/2, 200.0/2,
            70.0/2, 170.0/2,
            70.0/2, 60.0/2 });
        } else if (fase == 2) {
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
    public void setVelX(double vel) {
        velAsteroideX=vel;
    }
    public void setVelY(double vel) {
        velAsteroideY=vel;
    }
}
