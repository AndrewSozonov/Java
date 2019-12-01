package task2_4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class Controller {
    @FXML public Button btnSend;
    @FXML public TextField textField;
    @FXML public TextArea textArea;


    public void onActionClose(ActionEvent actionEvent) {
        System.exit(0);
    }


    public void clearChat(ActionEvent actionEvent) {
        textArea.clear();
    }


    public void handleEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            btnSend.fire();
        }
    }

    public void sendText (ActionEvent actionEvent){
        if (!textField.getText().equals("")) {
            textArea.appendText("Вы: " + textField.getText() + "\n");
            textField.clear();
            textField.requestFocus();
        }
    }
}



