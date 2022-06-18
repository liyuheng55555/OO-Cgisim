package model;

//public class TableVar {
//    public String varName;
//    public String varValue;
//    public String varType;
//    public TableVar(String name, String type, String value){
//        this.varName = name;
//        this.varType = type;
//        this.varValue = value;
//    }
//
//    public void setVarName(String varName) {
//        this.varName = varName;
//    }
//
//    public void setVarType(String varType) {
//        this.varType = varType;
//    }
//
//    public void setVarValue(String varValue) {
//        this.varValue = varValue;
//    }
//
//    public String getVarName() {
//        return varName;
//    }
//
//    public String getVarType() {
//        return varType;
//    }
//
//    public String getVarValue() {
//        return varValue;
//    }
//}
//
///**
// *
// */


// 原TableVar

//import model.Var;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TableVar {
    public static ArrayList<TableVar> varList = new ArrayList<>();

    private final StringProperty varName;
    private final StringProperty varType;
    private final StringProperty varValue;

    public TableVar(String varName, String varType, String varValue) throws Exception {
//        Var.add(varName, varType, varValue);
        this.varName = new SimpleStringProperty(varName);
        this.varType = new SimpleStringProperty(varType);
        this.varValue = new SimpleStringProperty(varValue);
        varList.add(this);
    }

    public TableVar(TableVar var) {
        this.varName = new SimpleStringProperty(var.varName.get());
        this.varType = new SimpleStringProperty(var.varType.get());
        this.varValue = new SimpleStringProperty(var.varValue.get());
    }


    public String getVarName() {
        return varName.get();
    }

    public String getVarType() {
        return varType.get();
    }

    public String getVarValue() {
        return varValue.get();
    }

    public void setVarName(String name) {
        this.varName.set(name);
    }

    public void setVarType(String varType) {
        this.varType.set(varType);
    }

    public void setVarValue(String value) {
        varValue.set(value);
    }

    /**
     * 解析整个表格，获取
     * @return
     */
//    static HashMap<String, Object> help() {
//        HashMap<String, Object> result = new HashMap<>();
//
//        return result;
//    }
//
//
//
//    // ???????????б?????????λ???????TableVar??????????????
//    // controller.Var.edit()?????????????У???????????????
//    public void tryEdit(String varName, String varType, String varValue) throws Exception {
//        Var.edit(this.varName.getValue(), varName, varType, varValue);
//        this.varName.set(varName);
//        this.varType.set(varType);
//        this.varValue.set(varValue);
//    }
//
//    // ?????????е???????λ???????TableVar??????????????
//    public void tryRemove() {
//        Var.remove(varName.getName());
//        varList.remove(this);
//    }
//
//    // stepRun???????????????????±???
//    static void pull() {
//        Map<String,String> varMap = Var.getAll();
//        for (String name : varMap.keySet()) {
//            System.out.println(name+" "+varMap.get(name));
//        }
//        for (TableVar var: varList) {
//            if (varMap.containsKey(var.varName.get())) {
//                var.varValue.set(varMap.get(var.varName.get()));
//                System.out.println("change "+var.varName.get()+" to "+var.varValue.get());
//            }
//
//        }
//    }
}
