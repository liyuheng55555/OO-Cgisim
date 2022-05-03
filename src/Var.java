import java.util.HashMap;

public class Var {
    enum Type {
        INT, FLOAT, BOOLEAN
    }
    Type type;
    String name;
    Object val;

    static private HashMap<String, Var> initMap = new HashMap<>();  // 此表由Menu类维护，Run类读取
    static private HashMap<String, Var> runtimeMap = null;   // 此表由Run类维护，Menu类读取
}
