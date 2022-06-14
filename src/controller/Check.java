package controller;

import com.sun.scenario.effect.Merge;
import javafx.collections.ObservableList;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static controller.RootLayoutController.inConnector;
import static controller.RootLayoutController.outConnector;
import static model.Constant.viewW;

public class Check {
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
                    list.add((int) (node.getImageView().getY() / viewW));
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

    private static void modifyList(MyNode node, List<List<Integer>> result, String type, Map<String, List<Integer>> connector) {
        for (Integer temp : connector.get("start")) {
            if (node.connectTo[temp] == -1) {
                List<Integer> list = new ArrayList<>();
                list.add((int) (node.getImageView().getX() / viewW));
                list.add((int) (node.getImageView().getY() / viewW));
                result.add(list);
            }
        }
    }
}
