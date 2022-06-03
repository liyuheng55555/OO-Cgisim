package model;

import controller.DrawController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class LoopNode extends MyNode{

    ImageView loop_st;
    ImageView loop_end;

    public LoopNode(int factoryID, String defaultText, AnchorPane drawingArea, DrawController drawController) {
        super(factoryID, defaultText,drawingArea, drawController);
        loop_st = new ImageView("file:src/images/loop_st.png");
        loop_end = new ImageView("file:src/images/loop_end.png");
        loop_st.setFitHeight(20);
        loop_st.setFitWidth(20);
        loop_end.setFitHeight(20);
        loop_end.setFitWidth(20);
    }
}
