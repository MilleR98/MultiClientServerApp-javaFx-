package application.controller;

import application.ChatApplication;
import application.model.UserConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private static ChatApplication mainApp;
    @FXML
    private TextField inputUsername;
    @FXML
    private TextField inputServerAddress;
    @FXML
    private TextField inputServerPort;

    public void Connect(ActionEvent actionEvent) {

            UserConfig userCfg = new UserConfig();
            userCfg.setUsername(inputUsername.getText());
            userCfg.setHost(inputServerAddress.getText());
            userCfg.setPort(inputServerPort.getText());

            mainApp.setUserCfg(userCfg);

            try {
                mainApp.startChatClient();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Could not connect");
                alert.setHeaderText("Could not connect to the server.");
                alert.setContentText("Make sure you're using the correct IP and Port, or that\n"
                        + "the server you're trying to connect to is running.");

                alert.showAndWait();
                return;
            }
        Stage stage = (Stage) inputServerPort.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/chat.fxml"));
            stage.setTitle(inputUsername.getText()+"(id:"+mainApp.getChatClient().getClientId()+")");
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(e-> System.exit(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setResizable(false);
        stage.show();

    }

    public static void setMainApp(ChatApplication app) {
        mainApp = app;
    }

}
