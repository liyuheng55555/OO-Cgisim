import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Map;

public class TableVar {
    static ArrayList<TableVar> varList = new ArrayList<>();

    private final StringProperty varName;
    private final StringProperty varType;
    private final StringProperty varValue;

    public TableVar(String varName, String varType, String varValue) throws Exception {
        Var.add(varName, varType, varValue);
        this.varName = new SimpleStringProperty(varName);
        this.varType = new SimpleStringProperty(varType);
        this.varValue = new SimpleStringProperty(varValue);
        varList.add(this);
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

    public void tryEdit(String varName, String varType, String varValue) throws Exception {
        Var.edit(this.varName.getValue(), varName, varType, varValue);
        this.varName.set(varName);
        this.varType.set(varType);
        this.varValue.set(varValue);
    }

    public void tryRemove() {
        Var.remove(varName.getName());
//        varList.remove(this);
    }

    static void pull() {
        Map<String,String> varMap = Var.getAll();
        for (TableVar var: varList) {
            if (varMap.containsKey(var.varName.get()))
                var.varValue.set(varMap.get(var.varName.getValue()));
        }
    }
}