package model;

import javafx.scene.text.Font;

import java.awt.*;

public class Constant {
    public static final int viewW = 161;
    public static final int viewH = 100;
    public static final int tableH = 30;
    public static final int tableW = 15;
    public static final int branchTextRelativeX = 30;
    public static final int branchTextRelativeY = 55;
    public static final int LoopTextRelativeX = 25;
    public static final int LoopTextRelativeY = 30;
    public static final int printTextRelativeX = 30;
    public static final int printTextRelativeY = 58;
    public static final int statementTextRelativeX = 20;
    public static final int statementTextRelativeY = 55;
    public static final int connectorSize = 5;
    public static final Font font = new Font("Arial", 18);
    public static final double dragThreshold = 20; // 触发dragging的鼠标位移下限
    public enum Status {
        normal,
        prepareToDrag, // 小位移拖动会进入此状态，此状态的release应调用click这个函数
        dragging,  // 位移到达一定程度后再进入正常拖动状态
        connecting,
        selected,
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
