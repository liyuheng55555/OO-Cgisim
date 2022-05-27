//import javafx.scene.text.Text;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.google.gson.*;
//
//class DrawController {
//    static Map<Integer, MyShape> shapeMap = new HashMap<>();
////    static {
////        MyShape s;
////        s = new StartNode(1);
////        shapeMap.put(s.myID, s);
////        s = new StateNode(0,2, new Text("a = 1+2+3\n"));
////        shapeMap.put(s.myID, s);
////        s = new EndNode(1);
////        shapeMap.put(s.myID, s);
////    }
////    static {
////        MyShape s;
////        s = new StartNode(1);
////        shapeMap.put(s.myID, s);
////        s = new StateNode(0,2, new Text("aa = 1+2+3\n"));
////        shapeMap.put(s.myID, s);
////        s = new EndNode(1);
////        shapeMap.put(s.myID, s);
////    }
//    static {
//        MyShape s;
//        s = new StartNode(1);
//        shapeMap.put(s.myID, s);
//        s = new StateNode(0,2, new Text(" = 1+2+3\n"));
//        shapeMap.put(s.myID, s);
//        s = new EndNode(1);
//        shapeMap.put(s.myID, s);
//    }
//    int startID;
//    Map<Integer, MyShape> getShapeMap() {
//        return shapeMap;
//    }
//    void save() {
//        Gson gson = new Gson();
//        System.out.println(gson.toJson(shapeMap));
//    }
//    int getStartID() {
//        return 0;
//    }
//}
//
//class MyShape {
//    private static int auto = 0;
//    int myID;
//
//    public MyShape() {
//        this.myID = auto;
//        auto++;
//    }
//}
//
//class IfNode extends MyShape {
//    int preID;
//    int trueID;
//    int falseID;
//    Text text;
//
//    public IfNode(int preID, int trueID, int falseID, Text text) {
//        this.preID = preID;
//        this.trueID = trueID;
//        this.falseID = falseID;
//        this.text = text;
//    }
//}
//
//class StartNode extends MyShape {
//    int nxtID;
//
//    public StartNode(int nxtID) {
//        this.nxtID = nxtID;
//    }
//}
//
//class EndNode extends MyShape {
//    int preID;
//
//    public EndNode(int preID) {
//        this.preID = preID;
//    }
//}
//
//class StateNode extends MyShape {
//    int preID;
//    int nxtID;
//    Text text;
//
//    public StateNode(int preID, int nxtID, Text text) {
//        this.preID = preID;
//        this.nxtID = nxtID;
//        this.text = text;
//    }
//}