package controller;

import model.*;

public class NodeFactory {
    private int factoryID;
    public NodeFactory() {
        this.factoryID = 0;
    }

    public MyNode produceNode(String selectNode, int x, int y) {
        MyNode node = null;
        switch (selectNode.split("_")[2]) {
            case "start":
                node = new StartNode(factoryID++, x, y);
                break;
            case "end":
                node = new EndNode(factoryID++, x, y);
                break;
            case "statement":
                node = new StatementNode(factoryID++, x, y);
                break;
            case "if":
                node = new IfNode(factoryID++, x, y);
                break;
            case "loop":
                node = new LoopNode(factoryID++, x, y);
                break;
            case "print":
                node = new PrintNode(factoryID++, x, y);
                break;
            default:
                break;
        }
        return node;
    }
}
