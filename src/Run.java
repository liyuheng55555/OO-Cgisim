public class Run {
    private Run() {}
    public void stepRun() throws Exception {
        // 如果当前不在运行
        if (Node.running==false) {
            if (Node.start==null)
                throw new Exception("没有起始节点，无法开始运行");
            Node.running = true;
            Node.now = Node.start;
            return;
        }
        // 如果当前在运行
        Node node = Node.now;
        if (node.next==null)
            throw new Exception("没有后继节点，无法继续运行");
        Node.now = node.next;
        return;
    }
    public void reset() {
        Node.running = false;
        Node.now = null;
    }
    public void continuousRun() {

    }
}
