package com.example.javafx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Var {
    private StringProperty varName;
    private StringProperty varType;
    private StringProperty varValue;

    public Var(String varName, String varType, String varValue) {
        this.varName = new SimpleStringProperty(varName);
        this.varType = new SimpleStringProperty(varType);
        this.varValue = new SimpleStringProperty(varValue);
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
    public void setVarName(String varName){
        this.varName=new SimpleStringProperty(varName);
    }

    public void setVarType(String varType) {
        this.varType.set(varType);
    }

    public void setVarValue(String varValue) {
        this.varValue.set(varValue);
    }
}
