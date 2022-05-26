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

    public void setVarValue(String value) {
        varValue.set(value);
    }

    // �Ա��ĳ�����б༭��ʱ���ȶ�λ�������TableVar����Ȼ����ô˷���
    // Var.edit()����˸����Ƿ���У�����������׳��쳣
    public void tryEdit(String varName, String varType, String varValue) throws Exception {
        Var.edit(this.varName.getValue(), varName, varType, varValue);
        this.varName.set(varName);
        this.varType.set(varType);
        this.varValue.set(varValue);
    }

    // ɾ�����ĳ�е�ʱ���ȶ�λ�������TableVar����Ȼ����ô˷���
    public void tryRemove() {
        Var.remove(varName.getName());
        varList.remove(this);
    }

    // stepRun֮����ô˾�̬�������Ը��±��
    static void pull() {
        Map<String,String> varMap = Var.getAll();
        for (String name : varMap.keySet()) {
            System.out.println(name+" "+varMap.get(name));
        }
        for (TableVar var: varList) {
            if (varMap.containsKey(var.varName.get())) {
                var.varValue.set(varMap.get(var.varName.get()));
                System.out.println("change "+var.varName.get()+" to "+var.varValue.get());
            }

        }
    }
}