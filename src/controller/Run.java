package controller;

import Parse.Main;
import javafx.collections.ObservableList;
import model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Run {
    Run() {}
    static int startID = -1;
    static int nowID = -1;
    static Map<Integer, MyNode> nodeMap = null;
    static Map<String, Object> varMap = null;

    /**
     * 使用此函数设定运行所需的环境
     * @param sID   起始节点编号
     * @param nMap  nodeMap
     * @param data  变量表格
     * @throws Exception    变量表格中的不合法情况
     */
    static public void setup(int sID, Map<Integer, MyNode> nMap, ObservableList<TableVar> data) throws Exception {
        setStartID(sID);
        setNodeMap(nMap);
        setVarMap(data);
    }

    static public void setStartID(int sID) {
        startID = sID;
    }
    static public void setNodeMap(Map<Integer, MyNode> nMap) {
        nodeMap = nMap;
    }
    static private Pattern namePat = Pattern.compile("^[A-Za-z_][A-Za-z0-9_]*$");
    static private Pattern boolPat = Pattern.compile("^(true|false|True|False)$");

    /**
     * 检查并设定dataMap，检查内容包括：<br/>
     * (1)变量名不能重复 <br/>
     * (2)变量名要符合namePat <br/>
     * (3)类型是int、bool、float <br/>
     * (4)值和类型要匹配 <br/>
     * @param data RootLayoutController中的data表
     * @throws Exception 任何检查不通过则抛出Exception，异常信息会写清楚
     */
    static public void setVarMap(ObservableList<TableVar> data) throws Exception {
        varMap = new HashMap<>();
        int cnt = 0;
        for (TableVar var : data) {
            String name = var.getVarName();
            String type = var.getVarType();
            String strValue = var.getVarValue();
            if (varMap.containsKey(name))
                throw new Exception("变量名"+name+"重复");
            if (!namePat.matcher(name).find())
                throw new Exception("变量名"+name+"无效");
            Object value;
            switch (type) {
                case "int":
                    value = new Integer(strValue);
                    break;
                case "float":
                    value = new Float(strValue);
                    break;
                case "bool":
                    if (!boolPat.matcher(strValue).find())
                        throw new Exception("bool类型变量值"+strValue+"无效");
                    value = Boolean.valueOf(strValue);
                    break;
                default:
                    throw new Exception("类型"+type+"无效");
            }
            varMap.put(name, value);
        }
    }

    /**
     * 如果当前处于非运行状态，就将状态改变为运行，运行位置设在startNode；
     * 如果当前已经处于运行状态，就运行一步
     * @throws Exception 运行过程中发生错误
     */
    static public int stepRun() throws Exception {
        if (nowID == -1) {
            nowID = startID;
            System.out.printf("nowID = %d\n", nowID);
//            Var.runMap = new HashMap<>(Var.initMap);
            return nowID;
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
            if (expression.charAt(expression.length()-1)!='\n')
                expression += "\n";
            Object result = Main.run(expression, varMap);
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
            if (expression.charAt(expression.length()-1)!='\n')
                expression += "\n";
            Main.run(expression, varMap);
            nowID = stateNode.getNxtID();
        }
        else {
            throw new Exception(nowID+"号节点类型未知，也许是"+now.getClass().toString());
        }
        System.out.printf("nowID = %d\n", nowID);
        return nowID;
    }

    /**
     * 重置到非运行状态
     */
    static public void reset() {
        nowID = -1;
        varMap = null;
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