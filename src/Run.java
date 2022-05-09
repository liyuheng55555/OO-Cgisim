import java.util.HashMap;

public class Run {
    private Run() {}
    static boolean isRunning = false;
    public void stepRun() throws Exception {
        // 如果当前不在运行
        if (!isRunning) {
            if (Node.start==null)
                throw new Exception("没有起始节点，无法开始运行");
            isRunning = true;
            Var.runMap = new HashMap<>(Var.initMap);
            Node.now = Node.start;
            return;
        }
        // 如果当前在运行
        Node node = Node.now;
        if (node.next==null)
            throw new Exception("没有后继节点，无法继续运行");
        Parse.Main.run(node.expression,Var.runMap);
        Node.now = node.next;
        return;
    }
    public void reset() {
        isRunning = false;
        Node.now = null;
        Var.runMap.clear();
    }
    public void continuousRun() {

    }
}
