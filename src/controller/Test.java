package controller;

import model.MyNode;

import java.util.HashMap;

public class Test {
    public void printMap(HashMap<Integer, MyNode> map){
        for(MyNode node : map.values()) {
            System.out.println(node.getClass().getName());
            System.out.println();
        }
    }
}
