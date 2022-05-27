package model;

import controller.DrawController;

/**
 * ��¼���ӵ���Ϣ
 *
 */
public class ConnectionInfo {
    /*
     * �ߣ�ͼ�����ӵ�λ�ã�ͼ�ι�����
     */
    private MyLine line;
    private int location;
    private String ConnectionPart;
    private DrawController drawController;

    public DrawController getDrawController() {
        return drawController;
    }

    public void setDrawController(DrawController drawController) {
        this.drawController = drawController;
    }

    public ConnectionInfo(String text, DrawController drawController) {
        String[] temp = text.split("_");
        int id = Integer.parseInt(temp[0]);
        int pos = Integer.parseInt(temp[1]);
        String part = temp[2];
        this.line = drawController.getMyLine(id);
        this.location = pos;
        this.ConnectionPart = part;
    }

    public ConnectionInfo(MyLine line, int location, String part, DrawController drawController) {
        this.drawController = drawController;
        this.line = line;
        this.location = location;
        this.ConnectionPart = part;
    }

    public MyLine getLine() {
        return line;
    }

    public void setLine(MyLine line) {
        this.line = line;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getConnectionPart() {
        return ConnectionPart;
    }

    public void setConnectionPart(String connectionPart) {
        ConnectionPart = connectionPart;
    }

    public String toString() {
        System.out.println(line);
        System.out.println(ConnectionPart);
        return String.valueOf(line.getFactoryID()) + "_" + location + "_" + ConnectionPart;
    }
}
