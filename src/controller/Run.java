package controller;

import Parse.Main;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

public class Run extends Thread{
//    static volatile boolean pauseSig = false;  // 主线程将此设置为false以暂停运行
//    static volatile Exception exception = null;  // 连续运行过程中出现异常，异常信息存到这里
//    static SimpleIntegerProperty inform = null;  // 连续运行结束或出现异常，修改这玩意儿以通知主线程
//    static Object lock = null;  // 如果连续运行暂停，使用lock.wait()，等待被主线程唤醒
    Run() {}
    static int startID = -1;
    static int nowID = -1;
    static Map<Integer, MyNode> nodeMap = null;
    static Map<String, Object> varMap = null;
    static TextArea outText = null;
    static Stack<LoopStNode> loopStack = null;

    /**
     * 使用此函数设定运行所需的环境
     * @param sID   起始节点编号
     * @param nMap  nodeMap
     * @param data  变量表格
     * @throws Exception    变量表格中的不合法情况
     */
    static public void setup(int sID,
                             Map<Integer, MyNode> nMap,
                             ObservableList<TableVar> data,
                             TextArea outText
//                             SimpleIntegerProperty info,
//                             Object lck
    ) throws Exception {
        loopStack = new Stack<>();
        setStartID(sID);
        setNodeMap(nMap);
        setVarMap(data);
        setTextArea(outText);
//        inform = info;
//        lock = lck;
    }

    static public void setStartID(int sID) {
        startID = sID;
    }
    static public void setNodeMap(Map<Integer, MyNode> nMap) {
        nodeMap = nMap;
    }
    static private final Pattern namePat = Pattern.compile("^[A-Za-z_][A-Za-z0-9_]*$");
    static private final Pattern boolPat = Pattern.compile("^(true|false|True|False)$");

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

    static public void setTextArea(TextArea oText) {
        outText = oText;
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
                                "，值为"+ result
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
        else if (now instanceof MergeNode) {
            MergeNode mergeNode = (MergeNode) now;
            nowID = mergeNode.getMergeNxtID();
        }
        else if (now instanceof PrintNode) {
            PrintNode printNode = (PrintNode) now;
            String expression = printNode.getText().getText();
            if (expression.charAt(expression.length()-1)!='\n')
                expression += "\n";
            Object result = Main.run(expression, varMap);
            outText.appendText(result.toString()+"\n");
            nowID = printNode.getNxtID();
        }
        else if (now instanceof LoopStNode) {
            LoopStNode loopStNode = (LoopStNode) now;
            loopStack.push(loopStNode);
            nowID = loopStNode.getLoop_stNxtID();
        }
        else if (now instanceof LoopEndNode) {
            LoopEndNode loopEndNode = (LoopEndNode) now;
            String expression = loopEndNode.getText().getText();
            if (!expression.endsWith("\n"))
                expression += "\n";
            Object result = Main.run(expression, varMap);
            if (!(result instanceof Boolean))
                throw new Exception(
                        nowID+"号节点是LoopEndNode，"+
                                "但表达式运算结果不是Boolean，"+
                                "而是"+result.getClass().toString()+
                                "，值为"+ result
                );
            Boolean b = (Boolean) result;
            if (b) {
                MyNode next;
                try {
                    next = loopStack.pop();
                } catch (Exception e) {
                    throw new Exception(nowID+"号节点是LoopEndNode，表达式结果为True，但找不到可供跳转的LoopStNode");
                }
                nowID = next.getFactoryID();
            }
            else
                nowID = loopEndNode.getLoop_endNxtID();
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
//        varMap = null;
    }

    static public boolean isRunning() {
        return nowID!=-1;
    }
    static public int continuousRun() throws Exception {
        int id;
        do {
            id = stepRun();
        } while(!(nodeMap.get(id) instanceof EndNode));
        return id;
    }
    static public int getNow() {
        return nowID;
    }


//    @Override
//    public void run() {
//        System.out.println("开始多线程运行");
//        int id;
//        do {
//            if (pauseSig) {
//                synchronized (lock) {
//                    try {
//                        System.out.println("暂停多线程运行");
//                        lock.wait();
//                        System.out.println("恢复多线程运行");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            try {
//                id = stepRun();
//            } catch (Exception e) {
//                exception = e;
//                inform.set(inform.get()+1);
//                return;
//            }
//        } while(!(nodeMap.get(id) instanceof EndNode));
//        inform.set(inform.get()+1);
////        return id;
//    }

    static public String getType(String expression) throws Exception {
        if (!expression.endsWith("\n"))
            expression += "\n";
        return Main.getType(expression, varMap);
    }
}

