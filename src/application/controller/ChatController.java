package application.controller;

import application.ChatApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController {

    private static ChatApplication mainApp;
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField inputMessage;

    public ChatController(){
        mainApp.setChatController(this);
    }
    public void Send(ActionEvent actionEvent) {
        String sendingUser = "<" + mainApp.getUserCfg().getUsername() + ">";
        String message = inputMessage.getText() + "\n";

        mainApp.getChatClient().sendMessage(sendingUser + " " + message);
        chatArea.appendText("<You> " + message);

        inputMessage.clear();
    }

    public void appendTextToConversation(String message) {

        chatArea.appendText(message);
    }


    public static void setMainApp(ChatApplication app) {
        mainApp = app;
    }
}
