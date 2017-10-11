package application;

import application.client.ChatClient;
import application.controller.ChatController;
import application.controller.LoginController;
import application.model.UserConfig;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatApplication extends Application {

    private UserConfig userCfg;
    private ChatClient chatClient;
    private ChatController chatController;
    private LoginController loginController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        primaryStage.setTitle("Connect to chat");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e-> Platform.exit());
        chatClient = new ChatClient(this);
        LoginController.setMainApp(this);
        ChatController.setMainApp(this);

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    public void startChatClient() throws IOException {
        getChatClient().start();
    }


    public UserConfig getUserCfg() {
        return userCfg;
    }

    public void setUserCfg(UserConfig userCfg) {
        this.userCfg = userCfg;
    }

    public ChatClient getChatClient() {
        return chatClient;
    }


    public void appendToChat(String message) {
        chatController.appendTextToConversation(message);
    }

    public ChatController getChatController() {
        return chatController;
    }

    public void setChatController(ChatController chatController) {
        this.chatController = chatController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
