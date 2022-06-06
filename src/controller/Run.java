package controller;

import Parse.Main;
import model.*;

import java.util.HashMap;
import java.util.Map;

public class Run {
    Run() {}
    static int nowID = -1;
    static int startID = -1;
    static Map<Integer, MyNode> nodeMap = null;


    /**
     * 运行此函数以设置Run类
     * @param sID 起始节点编号
     * @param nMap 包含全部节点的Map
     */
    static public void setup(int sID, Map<Integer, MyNode> nMap) {
        startID = sID;
        nodeMap = nMap;
    }

    /**
     * 如果当前处于非运行状态，就将状态改变为运行，运行位置设在startNode；
     * 如果当前已经处于运行状态，就运行一步
     * @throws Exception 运行过程中发生错误
     */
    static public void stepRun() throws Exception {
        if (nowID == -1) {
            nowID = startID;
            System.out.printf("nowID = %d\n", nowID);
            Var.runMap = new HashMap<>(Var.initMap);
            return;
        }
//        Map<Integer, MyNode> shapeMap = rootLayoutController.getNodeMap();
        MyNode now = nodeMap.get(nowID);
        if (now==null) {
            throw new Exception(nowID+"号节点没有对应的MyShape对象");
        }
        if (now instanceof StartNode) {
            nowID = ((StartNode) now).getNxtID();
        }
        else if (now instanceof EndNode) {
            throw new Exception("程序已经到达终点");
        }
        else if (now instanceof BranchNode) {
            BranchNode ifNode = (BranchNode) now;
            String expression = ifNode.getText().getText();
            Object result = Main.run(expression, Var.runMap);
            if (!(result instanceof Boolean))
                throw new Exception(
                        nowID+"号节点是IfNode，"+
                                "但表达式运算结果不是Boolean，"+
                                "而是"+result.getClass().toString()+
                                "，值为"+result.toString()
                );
            if ((Boolean)result)
                nowID = ifNode.getBranchTrueID();
            else
                nowID = ifNode.getBranchFalseID();
        }
        else if (now instanceof StatementNode) {
            StatementNode stateNode = (StatementNode) now;
            String expression = stateNode.getText().getText();
            Main.run(expression, Var.runMap);
            nowID = stateNode.getNxtID();
        }
        else {
            throw new Exception(nowID+"号节点类型未知，也许是"+now.getClass().toString());
        }
        System.out.printf("nowID = %d\n", nowID);
    }

    /**
     * 重置到非运行状态
     */
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
}