package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;


public class TableVar {
    public static ArrayList<TableVar> varList = new ArrayList<>();

    private final StringProperty varName;
    private final StringProperty varType;
    private final StringProperty varValue;

    public TableVar(String varName, String varType, String varValue) {
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

}
