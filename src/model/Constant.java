package model;

import javafx.scene.text.Font;

public class Constant {
    public static final int viewW = 150;
    public static final int viewH = 100;
    public static final int tableH = 20;
    public static final int tableW = 10;
    public static final int connectorSize = 5;
    public static final Font font = new Font("Arial", 10);

    public enum Status {
        normal, dragging, connecting, selected
    }
    /**
     *  This enumeration is used to mark the current state.
     *  The current state determine whether the starting point or the ending point is selected.
     *  <li>{@link #choosingStart}</li>
     *  <li>{@link #choosingEnd}</li>
     *
     *  @author <a href="hr.mail.qaq@gmail.com">huang rui</a>
     */
    public enum ClickStatus {
        /**
         * choosingStart 此时的鼠标选择的是连线的起点
         */
        choosingStart,
        /**
         * choosingEnd 此时的鼠标选择的是连线的终点
         */
        choosingEnd
    }
}
