import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

class MouseEvent1 implements EventHandler<MouseEvent>{
    TextField textField;
    public  MouseEvent1(TextField textField){
        this.textField = textField;
    }
    @Override
    public void handle(MouseEvent event) {
        Alert alert =new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("�ύ�ɹ�:"+textField.getText());
        alert.show();
        // System.out.println("�ύ�ɹ�"+textField.getText());
    }
}


