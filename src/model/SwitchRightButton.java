package model;

import controller.RootLayoutController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;

public class SwitchRightButton extends Label {
    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(true);

    public SwitchRightButton(RootLayoutController rootLayoutController) {
        Button switchBtn = new Button();
        switchBtn.setPrefWidth(40);
        switchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                switchedOn.set(!switchedOn.get());
            }
        });

        setGraphic(switchBtn);
        switchedOn.set(true);
        setAttribute();
        setVarTable();
        switchedOn.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    setVarTable();
                    rootLayoutController.changeRightModel();
                } else {
                    setAttribute();
                    rootLayoutController.changeRightModel();
                }
            }
        });
    }

    public void setVarTable() {
        setText("VarTable");
        setStyle("-fx-background-color: green;-fx-text-fill:white;");
        setContentDisplay(ContentDisplay.RIGHT);
    }

    public void setAttribute() {
        setText("Attribute");
        setStyle("-fx-background-color: grey;-fx-text-fill:black;");
        setContentDisplay(ContentDisplay.LEFT);
    }

    public SimpleBooleanProperty switchOnProperty() {
        return switchedOn;
    }
}