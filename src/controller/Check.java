package controller;

import com.sun.scenario.effect.Merge;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import model.*;

import java.util.*;

import static controller.RootLayoutController.inConnector;
import static controller.RootLayoutController.outConnector;
import static model.Constant.viewW;
import static model.Constant.viewH;
public class Check {
    static TextArea outText;


    /**
     * 初始化输出终端
     *
     * @param text 输出文本框
     */
    void setup(TextArea text) {
        outText = text;
    }

    /**
     * 在右下角的控制台打印一行报错信息
     * @param y 纵坐标
     * @param x 横坐标
     * @param string    报错信息
     */
    static private void appendErrorMessage(int y, int x, String string) {
        outText.appendText("("+y+","+x+") "+string+"\n");
    }

    /**
     * check whether the startNode is unique in the list
     *
     * @param nodeMap the map of all nodes
     * @return null is the startNode is unique,
     * otherwise return a list to indicate the place of startNodes
     */
    public static List<List<Integer>> checkStartNodeNumber(Map<Integer, MyNode> nodeMap) {
        List<List<Integer>> result = null;
        int startNodeNumber = 0;
        for (MyNode node : nodeMap.values()) {
            if (node instanceof StartNode) {
                startNodeNumber++;
            }
        }
        if (startNodeNumber > 1) {
            result = new ArrayList<>();
            for (MyNode node : nodeMap.values()) {
                if (node instanceof StartNode) {
                    List<Integer> list = new ArrayList<>();
                    list.add((int) (node.getImageView().getX() / viewW));
                    list.add((int) (node.getImageView().getY() / viewH));
                    result.add(list);
                }
            }
        }
        return result;
    }

    /**
     * check whether the StartNode exist in the map
     *
     * @param nodeMap the map of all nodes
     * @return true if the StartNode exist, otherwise false
     */
    public static boolean checkStartNode(Map<Integer, MyNode> nodeMap) {
        boolean result = false;
        for (MyNode node : nodeMap.values()) {
            if (node instanceof StartNode) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * check whether the connections between nodes are already prepared
     *
     * @param nodeMap the map of all nodes
     * @return null if the connections are all prepared,
     * otherwise return a list to indicate the place of nodes which connection are not prepared yet
     */
    public static List<List<Integer>> checkConnectionError(Map<Integer, MyNode> nodeMap) {
        List<List<Integer>> result = new ArrayList<>();
        for (MyNode node : nodeMap.values()) {
            if (node instanceof StartNode) {
                modifyList(node, result, "start", inConnector);
                modifyList(node, result, "start", outConnector);
            } else if (node instanceof EndNode) {
                modifyList(node, result, "end", inConnector);
                modifyList(node, result, "end", outConnector);
            } else if(node instanceof BranchNode){
                modifyList(node, result, "branch", inConnector);
                modifyList(node, result, "branch", outConnector);
            }else if(node instanceof MergeNode){
                modifyList(node, result, "merge", inConnector);
                modifyList(node, result, "merge", outConnector);
            }else if(node instanceof LoopStNode){
                modifyList(node, result, "loop_st", inConnector);
                modifyList(node, result, "loop_st", outConnector);
            }else if(node instanceof LoopEndNode){
                modifyList(node, result, "loop_end", inConnector);
                modifyList(node, result, "loop_end", outConnector);
            }else if(node instanceof StatementNode) {
                modifyList(node, result, "statement", inConnector);
                modifyList(node, result, "statement", outConnector);
            }else if(node instanceof PrintNode){
                modifyList(node, result, "print", inConnector);
                modifyList(node, result, "print", outConnector);
            }
        }
        if(result.size() == 0){
            result = null;
        }
        return result;
    }

//    private boolean instanceOfList(Object object, Class<?>... types) {
//        boolean result = true;
//        for (Class<?> type : types) {
//            if (object instanceof type.getClass())
//        }
//    }

    /**
     * 分析所有node中是否存在语法错误，顺便在右下角打出错误信息
     * @param nodeTable 你懂得
     * @return  出错node的坐标List，按[[y0,x0],[y1,x1]...]这样
     */
    public static List<List<Integer>> checkSyntaxError(MyNode[][] nodeTable) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i=0; i<nodeTable.length; i++) {
            for (int j=0; j<nodeTable[0].length; j++) {
                MyNode node = nodeTable[i][j];
                try {
                    if (node instanceof BranchNode) {
                        BranchNode b = (BranchNode) node;
                        String type = Run.getType(b.getBranchText());
                        if (!type.equals("bool")) {
                            appendErrorMessage(i,j,"分支语句需要bool类型结果，目前结果为"+type);
                            result.add(Arrays.asList(i,j));
                        }

                    }
                    else if (node instanceof StatementNode) {
                        StatementNode s = (StatementNode) node;
                        Run.getType(s.getStatementText());
//                        if (!type.equals("bool"))
//                            appendErrorMessage(i,j,"分支语句需要bool类型结果，目前结果为"+type);
                    }
                    else if (node instanceof PrintNode) {
                        PrintNode p = (PrintNode) node;
                        Run.getType(p.getPrintText());
                    }
                    else if (node instanceof LoopEndNode) {
                        LoopEndNode l = (LoopEndNode) node;
                        String type = Run.getType(l.getLoop_endText());
                        if (!type.equals("bool")) {
                            appendErrorMessage(i,j,"循环语句需要bool类型结果，目前结果为"+type);
                            result.add(Arrays.asList(i,j));
                        }
                    }
                } catch (Exception e) {
                    appendErrorMessage(i,j,"解析错误："+e.getMessage());
                    result.add(Arrays.asList(i,j));
                }
            }
        }
        return result;
    }

    private static void modifyList(MyNode node, List<List<Integer>> result, String type, Map<String, List<Integer>> connector) {
        for (Integer temp : connector.get("start")) {
            if (node.getConnectTo()[temp] == -1) {
                List<Integer> list = new ArrayList<>();
                list.add((int) (node.getImageView().getX() / viewW));
                list.add((int) (node.getImageView().getY() / viewW));
                result.add(list);
            }
        }
    }

    /**
     *
     * @param map
     * @return
     */
    public static List<List<Integer>> checkNodeMapError(HashMap<Integer, MyNode> map){
        //TODO
        return null;
    }
}
