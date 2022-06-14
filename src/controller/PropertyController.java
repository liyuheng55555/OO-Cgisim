package controller;

import javafx.scene.control.TextField;
import model.*;

public class PropertyController {
    public TextField messageBox;
    MyNode curNode;

    public PropertyController(TextField messageBox) {
        this.messageBox = messageBox;
    }

    /**
     * This method is called when the user clicks the "Set" button.
     *
     */
    public void sendMessage() {
        if(curNode instanceof BranchNode) {
            ((BranchNode) curNode).getText().setText(messageBox.getText());
            ((BranchNode) curNode).setBranchText(messageBox.getText());
        } else if(curNode instanceof LoopStNode) {
            ((LoopStNode) curNode).getText().setText(messageBox.getText());
            ((LoopStNode) curNode).setLoop_stText(messageBox.getText());
        } else if(curNode instanceof StatementNode) {
            ((StatementNode) curNode).getText().setText(messageBox.getText());
            ((StatementNode) curNode).setStatementText(messageBox.getText());
        } else if(curNode instanceof PrintNode) {
            ((PrintNode) curNode).getText().setText(messageBox.getText());
            ((PrintNode) curNode).setPrintText(messageBox.getText());
        }
    }

    public void update(MyNode curNode) {
        this.curNode = curNode;
        if(curNode instanceof BranchNode) {
            messageBox.setText(((BranchNode) curNode).getText().getText());
        } else if(curNode instanceof LoopStNode) {
            messageBox.setText(((LoopStNode) curNode).getText().getText());
        } else if(curNode instanceof StatementNode) {
            System.out.println("StatementNode debug");
            messageBox.setText(((StatementNode) curNode).getText().getText());
        } else if(curNode instanceof PrintNode) {
            messageBox.setText(((PrintNode) curNode).getText().getText());
        }
    }
}
