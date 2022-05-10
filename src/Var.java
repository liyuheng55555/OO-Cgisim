import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Var {
    public Var(String name, String type, String val) throws Exception {
        if (initMap.containsKey(name))
            throw new Exception("变量名 "+name+" 已经被定义过");
        if (!namePat.matcher(name).find())
            throw new Exception("变量名 "+name+" 有格式问题");
//        Var var = new Var(name);
        Object value;
        switch (type) {
            case "int":
                value = new Integer(val);
                break;
            case "float":
                value = new Float(val);
                break;
            case "bool":
//                var.type = Type.BOOLEAN;
                if (!boolPat.matcher(val).find())
                    throw new Exception("bool类型变量值"+val+"无效");
                value = Boolean.valueOf(val);
                break;
            default:
                throw new Exception("类型无效");
        }
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.val = value;
        this.valStr = new SimpleStringProperty(val);
        initMap.put(name, value);
    }
    static private Pattern namePat = Pattern.compile("^[A-Za-z_][A-Za-z0-9_]*$");
//    static private Pattern intPat = Pattern.compile("^-?[0-9]+$");
//    static private Pattern floatPat = Pattern.compile("^-?[0-9]+(.[0-9]*)$");
    static private Pattern boolPat = Pattern.compile("^(true|false|True|False)$");

    static public HashMap<String, Object> initMap = new HashMap<>();
    static public HashMap<String, Object> runMap = null;
    static private final HashMap<String, String> javaTypeToCType = new HashMap<>();
    static {
        javaTypeToCType.put("Integer", "int");
        javaTypeToCType.put("Boolean", "bool");
        javaTypeToCType.put("Float", "float");
    }
    // 变量名转换成变量值
    static public Object name2val(String name) {
        return runMap.get(name);
    }
    // Var.add()用于添加一个变量，传过来3个参数，变量名、类型、值
    // 进行了格式判断，格式错误会抛出异常
    static public void add(String name, String type, String val) throws Exception {
        if (initMap.containsKey(name))
            throw new Exception("变量名"+name+"已经被定义过");
        if (!namePat.matcher(name).find())
            throw new Exception("变量名无效");
//        Var var = new Var(name);
        Object value;
        switch (type) {
            case "int":
                value = new Integer(val);
                break;
            case "float":
                value = new Float(val);
                break;
            case "bool":
//                var.type = Type.BOOLEAN;
                if (!boolPat.matcher(val).find())
                    throw new Exception("bool类型变量值"+val+"无效");
                value = Boolean.valueOf(val);
                break;
            default:
                throw new Exception("类型无效");
        }
        initMap.put(name, value);
    }
    // 删除一个变量
    static public void remove(String name) {
        initMap.remove(name);
    }
    // 清空
    static public void clear() {
        initMap.clear();
        if (runMap!=null)
            runMap.clear();
    }
    // 获取变量列表
    // 运行状态下提供runMap，非运行状态下提供initMap
//    static public ArrayList<String[]> getVarList() {
////        ArrayList<Var> arr;
//        HashMap<String,Object> map;
//        if (Run.isRunning)
//            map = runMap;
//        else
//            map = initMap;
//        ArrayList<String[]> as = new ArrayList<>();
//        for (Map.Entry<String,Object> entry: map.entrySet()) {
//            String[] ss = new String[3];
//            ss[0] = entry.getKey();
//            ss[1] = entry.getValue().getClass().getName();
//            ss[2] = javaTypeToCType.get(entry.getValue().toString());
//            as.add(ss);
//        }
//        return as;
//    }

    final StringProperty type;
    final StringProperty name;
    Object val;
    final StringProperty valStr;

    public String getName() {
        return name.get();
    }

    public String getType() {
        return type.get();
    }

    public String getValStr() {
        return valStr.get();
    }



    private static class Test {
        static private void test(String name, String type, String val) {
            try {
                Var.add(name,type,val);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
            System.out.println("add "+name+" success");
        }

        static private void 重复变量名测试() {
            Var.clear();
            System.out.println("重复变量名测试");
            test("a","int","1");
            test("a","int","1");
            test("a","int","1");
        }

        static private void 变量名格式测试() {
            Var.clear();
            System.out.println("变量名格式测试");
            test("__Aiuoigfh___kj09487509____","int","1");
            test("990909090adsa","int","1");
            test("__0__1__2___","int","1");
        }

        static private void 类型与数值测试() {
            Var.clear();
            System.out.println("类型与数值测试");
            test("a1","int","-10086");
            test("a2","int","143988");
            test("a3","int","1.0");
            test("a4","int","");
            test("a5","float","1.");
            test("a7","bool","true");
            test("a8","bool","false");
            test("a9","float","1.0");
            test("a10","float",".0");
            test("a11","gg","jkjkjkj");
            test("a11","bool","TRUE");
            test("a12","bool","TRU");
        }
        public static void main(String[] args) {
            重复变量名测试();
            变量名格式测试();
            类型与数值测试();
        }
    }
}

