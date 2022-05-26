import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 */
public class Var {
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
        Object value;
        switch (type) {
            case "int":
                value = new Integer(val);
                break;
            case "float":
                value = new Float(val);
                break;
            case "bool":
                if (!boolPat.matcher(val).find())
                    throw new Exception("bool类型变量值"+val+"无效");
                value = Boolean.valueOf(val);
                break;
            default:
                throw new Exception("类型无效");
        }
        initMap.put(name, value);
    }
    static public void edit(String oldName, String newName, String newType, String newVal) throws Exception{
        // 变量名检查
        if (!initMap.containsKey(oldName))
            throw new Exception("变量名 "+oldName+" 不存在");
        if (!namePat.matcher(newName).find())
            throw new Exception("变量名 "+newName+" 无效");
        // 类型检查，变量值检查
        Object value;
        switch (newType) {
            case "int":
                value = new Integer(newVal);
                break;
            case "float":
                value = new Float(newVal);
                break;
            case "bool":
                if (!boolPat.matcher(newVal).find())
                    throw new Exception("bool类型变量值 "+newVal+" 无效");
                value = Boolean.valueOf(newVal);
                break;
            default:
                throw new Exception("类型无效");
        }
        // 通过检查，执行更改
        if (oldName.equals(newName))
            initMap.put(oldName, value);
        else {
            initMap.remove(oldName);
            initMap.put(newName, value);
        }
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
    static public HashMap<String,String> getAll() {
//        ArrayList<Var> arr;
        HashMap<String,Object> map;
        if (Run.isRunning())
            map = runMap;
        else
            map = initMap;
//        ArrayList<String[]> as = new ArrayList<>();
        HashMap<String, String> map1 = new HashMap<>();
        for (Map.Entry<String,Object> entry: map.entrySet()) {
            map1.put(entry.getKey(),entry.getValue().toString());
        }
        return map1;
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

