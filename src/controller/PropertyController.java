package controller;

import javafx.scene.control.TextField;

public class PropertyController {
    public TextField messageBox;

    public PropertyController(TextField messageBox) {
        this.messageBox = messageBox;
    }

    public void sendMessage() {
        messageBox.setText("");
    }
    public void update() {
        messageBox.setText("");
    }
}
