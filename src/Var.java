import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Var {
    private enum Type {
        INT, FLOAT, BOOLEAN
    }
    static private Pattern namePat = Pattern.compile("^[A-Za-z_][A-Za-z0-9_]*$");
//    static private Pattern intPat = Pattern.compile("^-?[0-9]+$");
//    static private Pattern floatPat = Pattern.compile("^-?[0-9]+(.[0-9]*)$");
    static private Pattern boolPat = Pattern.compile("^(true|false|True|False)$");

    static private HashMap<String, Var> initMap = new HashMap<>();  // 此表由Menu类维护，Run类读取
    static private HashMap<String, Var> runMap = null;   // 此表由Run类维护，Menu类读取

    // Var.add()用于添加一个变量，传过来3个参数，变量名、类型、值
    // 进行了格式判断，格式错误会抛出异常
    static public void add(String name, String type, String val) throws Exception {
        if (initMap.containsKey(name))
            throw new Exception("变量名"+name+"已经被定义过");
        if (!namePat.matcher(name).find())
            throw new Exception("变量名无效");
        Var var = new Var(name);
        switch (type) {
            case "int":
                var.type = Type.INT;
                try {
                    var.val = Integer.parseInt(val);
                } catch (Exception e) {
                    throw e;
                }
                break;
            case "float":
                var.type = Type.FLOAT;
                try {
                    var.val = Float.parseFloat(val);
                } catch (Exception e) {
                    throw e;
                }
                break;
            case "bool":
                var.type = Type.BOOLEAN;
                if (!boolPat.matcher(val).find())
                    throw new Exception("bool类型变量值"+val+"无效");
                try {
                    var.val = Boolean.parseBoolean(val);
                } catch (Exception e) {
                    throw e;
                }
                break;
            default:
                throw new Exception("类型无效");
        }
        initMap.put(name, var);
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
    static public ArrayList<String[]> getVarList() {
        ArrayList<Var> arr;
        if (Run.isRunning)
            arr = new ArrayList<Var>(runMap.values());
        else
            arr = new ArrayList<Var>(initMap.values());
        ArrayList<String[]> as = new ArrayList<>();
        for (Var var: arr)
            as.add(var.toStringList());
        return as;
    }

    Type type;
    String name;
    Object val;
    private Var(String name) {
        this.name = name;
    }
    private String[] toStringList() {
        String[] ss = new String[3];
        ss[0] = this.name;
        ss[1] = this.type.name();
        ss[2] = this.val.toString();
        return ss;
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

