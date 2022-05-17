package model;

import javafx.scene.shape.Circle;

public class Point extends Circle {
    private  int posid;
    private  int directionX;
    private  int directionY;
    private  int leftX;
    private  int leftY;

    /**
     * Constructor for Point
     * @param x x-coordinate
     * @param y y-coordinate
     * @param radius  radius of the point
     */
    public Point(double x,double y,double radius){
        super(x,y,radius);
    }

    /**
     * @return the leftX
     */
    public int getLeftX() {
        return leftX;
    }

    /**
     * @param leftX the leftX to set
     */
    public void setLeftX(int leftX) {
        this.leftX = leftX;
    }

    /**
     * @return the leftY
     */
    public int getLeftY() {
        return leftY;
    }

    /**
     * @param leftY the leftY to set
     */
    public void setLeftY(int leftY) {
        this.leftY = leftY;
    }

    /**
     * @return the posid
     */
    public int getPosid() {
        return posid;
    }

    /**
     * @param posid the posid to set
     */
    public void setPosid(int posid) {
        this.posid = posid;
    }

    /**
     * @return  the directionX
     */
    public int getDirectionX() {
        return directionX;
    }

    /**
     * @param directionX the directionX to set
     */
    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    /**
     * @return the directionY
     */
    public int getDirectionY() {
        return directionY;
    }

    /**
     * @param directionY the directionY to set
     */
    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }
}
