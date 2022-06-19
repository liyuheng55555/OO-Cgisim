package controller;

import model.MyNode;

import java.util.Arrays;
import java.util.HashMap;

public class Test {
    public static void printMap(HashMap<Integer, MyNode> map){
        System.out.println("[debug] printMap connectionInfo");
        for(MyNode node : map.values()) {
            System.out.println("name: " + node.getClass().getName() +" ID: "+ node.getFactoryID());
            System.out.println("node.connectTo");
            System.out.println(Arrays.toString(node.getConnectTo()));
            System.out.println("node.connectPlace");
            System.out.println(Arrays.toString(node.getConnectPlace()));
        }
    }
}
