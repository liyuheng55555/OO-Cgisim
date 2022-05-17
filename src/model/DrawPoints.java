package model;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class DrawPoints {
    private Circle[] points;

    /**
     * Constructor for DrawPoints
     */
    public DrawPoints() {
        points = new Circle[4];
        for (int cnt = 0; cnt < 4; ++cnt) {
            points[cnt] = new Circle();
            points[cnt].setRadius(StandardNum.DRAW_POINTS_RADIUS);
        }
    }

    public void updateLocation(double leftMidX, double leftMidY, double upMidX, double upMidY, double rightMidX,
                               double rightMidY, double downMidX, double downMidY) {
        Double[] list = { leftMidX, leftMidY, upMidX, upMidY, rightMidX, rightMidY, downMidX, downMidY };
        int cnt = 0;
        for (int i = 0; i < 8; i += 2) {
            points[cnt].setCenterX(list[i]);
            points[cnt].setCenterY(list[i + 1]);
            cnt++;
        }
    }

    /**
     * Delete the points on the pane
     * @param pane  remove the points from the pane
     */
    public void delPoint(Pane pane) {
        pane.getChildren().removeAll(points);
    }

    /**
     * get the points
     * @return the points -- Circle[]
     */
    public Circle[] getCircles() {
        return points;
    }

    /**
     * Show the points depending on the state
     * @param state  state decides display the points or not
     */
    public void setAllVisiable(boolean state) {
        for (int i = 0; i < points.length; i++) {
            points[i].setVisible(state);
        }
    }
}
