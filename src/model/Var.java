package model;

import controller.Run;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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
    // ������ת���ɱ���ֵ
    static public Object name2val(String name) {
        return runMap.get(name);
    }
    // controller.Var.add()�������һ��������������3�������������������͡�ֵ
    // �����˸�ʽ�жϣ���ʽ������׳��쳣
    static public void add(String name, String type, String val) throws Exception {
        if (initMap.containsKey(name))
            throw new Exception("������"+name+"�Ѿ��������");
        if (!namePat.matcher(name).find())
            throw new Exception("��������Ч");
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
                    throw new Exception("bool���ͱ���ֵ"+val+"��Ч");
                value = Boolean.valueOf(val);
                break;
            default:
                throw new Exception("������Ч");
        }
        initMap.put(name, value);
    }
    static public void edit(String oldName, String newName, String newType, String newVal) throws Exception{
        // ���������
        if (!initMap.containsKey(oldName))
            throw new Exception("������ "+oldName+" ������");
        if (!namePat.matcher(newName).find())
            throw new Exception("������ "+newName+" ��Ч");
        // ���ͼ�飬����ֵ���
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
                    throw new Exception("bool���ͱ���ֵ "+newVal+" ��Ч");
                value = Boolean.valueOf(newVal);
                break;
            default:
                throw new Exception("������Ч");
        }
        // ͨ����飬ִ�и���
        if (oldName.equals(newName))
            initMap.put(oldName, value);
        else {
            initMap.remove(oldName);
            initMap.put(newName, value);
        }
    }
    // ɾ��һ������
    static public void remove(String name) {
        initMap.remove(name);
    }
    // ���
    static public void clear() {
        initMap.clear();
        if (runMap!=null)
            runMap.clear();
    }
    // ��ȡ�����б�
    // ����״̬���ṩrunMap��������״̬���ṩinitMap
    static public HashMap<String,String> getAll() {
//        ArrayList<controller.Var> arr;
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

        static private void dupNameTest() {
            Var.clear();
            System.out.println("�ظ�����������");
            test("a","int","1");
            test("a","int","1");
            test("a","int","1");
        }

        static private void nameSyntaxCheck() {
            Var.clear();
            System.out.println("��������ʽ����");
            test("__Aiuoigfh___kj09487509____","int","1");
            test("990909090adsa","int","1");
            test("__0__1__2___","int","1");
        }

        static private void typeAndValTest() {
            Var.clear();
            System.out.println("��������ֵ����");
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
//        public static void main(String[] args) {
//            �ظ�����������();
//            ��������ʽ����();
//            ��������ֵ����();
//        }
    }
}

