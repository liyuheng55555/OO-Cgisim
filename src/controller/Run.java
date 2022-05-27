package controller;

import jdk.nashorn.internal.runtime.regexp.joni.ast.StateNode;
import model.*;

import java.util.HashMap;
import java.util.Map;

public class Run {
    Run() {}
//    static boolean isRunning = false;
    static int nowID = -1;
    static DrawController drawController;
    static public void stepRun() throws Exception {
        if (nowID == -1) {
            nowID = drawController.getStartID();
            System.out.printf("nowID = %d\n", nowID);
            Var.runMap = new HashMap<>(Var.initMap);
            return;
        }
        Map<Integer, MyShape> shapeMap = drawController.getShapeMap();
        MyShape now = shapeMap.get(nowID);
        if (now==null) {
            throw new Exception(nowID+"号节点没有对应的MyShape对象");
        }
        if (now instanceof StartNode) {
            nowID = ((StartNode) now).getNxtID();
        }
        else if (now instanceof EndNode) {
            throw new Exception("程序已经到达终点");
        }
        else if (now instanceof IfNode) {
            IfNode ifNode = (IfNode) now;
            String expression = ifNode.getText().getText();
            Object result = Parse.Main.run(expression, Var.runMap);
            if (!(result instanceof Boolean))
                throw new Exception(
                        nowID+"号节点是IfNode，"+
                        "但表达式运算结果不是Boolean，"+
                        "而是"+result.getClass().toString()+
                        "，值为"+result.toString()
                );
            if ((Boolean)result)
                nowID = ifNode.getTrueID();
            else
                nowID = ifNode.getFalseID();
        }
        else if (now instanceof StatementNode) {
            StatementNode stateNode = (StatementNode) now;
            String expression = stateNode.getText().getText();
            Parse.Main.run(expression, Var.runMap);
            nowID = stateNode.getNxtID();
        }
        else {
            throw new Exception(nowID+"号节点类型未知，也许是"+now.getClass().toString());
        }
        System.out.printf("nowID = %d\n", nowID);
    }
    static public void reset() {
        nowID = -1;
        Var.runMap = null;
    }
    static public int getNowID() {
        return nowID;
    }
    static public boolean isRunning() {
        return nowID!=-1;
    }
    static public void continuousRun() {

    }
    static public void setDrawController(DrawController dc){
        drawController = dc;
    }
}
