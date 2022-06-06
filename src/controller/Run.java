package controller;

import Parse.Main;
import model.*;

import java.util.HashMap;
import java.util.Map;

public class Run {
    Run() {}
    static int nowID = -1;
    static int startID = -1;
    static Map<Integer, MyNode> nodeMap = null;


    /**
     * ���д˺���������Run��
     * @param sID ��ʼ�ڵ���
     * @param nMap ����ȫ���ڵ��Map
     */
    static public void setup(int sID, Map<Integer, MyNode> nMap) {
        startID = sID;
        nodeMap = nMap;
    }

    /**
     * �����ǰ���ڷ�����״̬���ͽ�״̬�ı�Ϊ���У�����λ������startNode��
     * �����ǰ�Ѿ���������״̬��������һ��
     * @throws Exception ���й����з�������
     */
    static public void stepRun() throws Exception {
        if (nowID == -1) {
            nowID = startID;
            System.out.printf("nowID = %d\n", nowID);
            Var.runMap = new HashMap<>(Var.initMap);
            return;
        }
//        Map<Integer, MyNode> shapeMap = rootLayoutController.getNodeMap();
        MyNode now = nodeMap.get(nowID);
        if (now==null) {
            throw new Exception(nowID+"�Žڵ�û�ж�Ӧ��MyShape����");
        }
        if (now instanceof StartNode) {
            nowID = ((StartNode) now).getNxtID();
        }
        else if (now instanceof EndNode) {
            throw new Exception("�����Ѿ������յ�");
        }
        else if (now instanceof BranchNode) {
            BranchNode ifNode = (BranchNode) now;
            String expression = ifNode.getText().getText();
            Object result = Main.run(expression, Var.runMap);
            if (!(result instanceof Boolean))
                throw new Exception(
                        nowID+"�Žڵ���IfNode��"+
                                "�����ʽ����������Boolean��"+
                                "����"+result.getClass().toString()+
                                "��ֵΪ"+result.toString()
                );
            if ((Boolean)result)
                nowID = ifNode.getBranchTrueID();
            else
                nowID = ifNode.getBranchFalseID();
        }
        else if (now instanceof StatementNode) {
            StatementNode stateNode = (StatementNode) now;
            String expression = stateNode.getText().getText();
            Main.run(expression, Var.runMap);
            nowID = stateNode.getNxtID();
        }
        else {
            throw new Exception(nowID+"�Žڵ�����δ֪��Ҳ����"+now.getClass().toString());
        }
        System.out.printf("nowID = %d\n", nowID);
    }

    /**
     * ���õ�������״̬
     */
    static public void reset() {
        nowID = -1;
        Var.runMap = null;
    }
    static public int getNowID() {
        return nowID;
    }
    static public boolean isRunning() {
        return nowID!=-1;
    }
    static public void continuousRun() {

    }
}