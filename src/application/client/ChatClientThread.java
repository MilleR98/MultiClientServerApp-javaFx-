package application.client;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.SocketException;

public class ChatClientThread implements Runnable {
    private ChatClient chatClient;
    private BufferedWriter outStream;
    private BufferedReader inStream;

    public ChatClientThread(ChatClient chatClient, BufferedWriter outStream,
                        BufferedReader inStream) {
        this.chatClient = chatClient;
        this.outStream = outStream;
        this.inStream = inStream;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = inStream.readLine();
                chatClient.getMainApp().appendToChat(message + "\n");
            }
        } catch (SocketException e) {

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection Lost");
                alert.setHeaderText("Lost the connection to the server.");
                alert.setContentText("The connection to the server was lost.");
                alert.showAndWait();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public BufferedWriter getWriter() {
        return this.outStream;
    }
}
