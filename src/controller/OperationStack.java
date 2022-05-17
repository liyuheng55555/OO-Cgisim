package controller;

import java.util.Stack;

public class OperationStack {
    private Stack<String> stack = new Stack<String>();

    public OperationStack() {
        stack.add("");
    }

    public void addOperation(String code) {
        stack.add(code);
    }

    public String restoreOperation() {
        String ansString = "";
        return stack.size() != 1 ? stack.pop() : ansString;
    }

    public String getTop() {
        return stack.peek();
    }
}
