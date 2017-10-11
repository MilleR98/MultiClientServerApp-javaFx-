package application.client;

import application.ChatApplication;
import java.io.*;
import java.net.Socket;

public class ChatClient {
    private Socket clientSocket;
    private ChatApplication mainApp;
    private ChatClientThread clientThread;

    public ChatClient(ChatApplication mainApp) {
        this.mainApp = mainApp;
    }

    public void start() throws IOException {
        String host = mainApp.getUserCfg().getHost();
        int port = Integer.valueOf(mainApp.getUserCfg().getPort());

        clientSocket = new Socket(host, port);

        BufferedWriter outStream = new BufferedWriter(new OutputStreamWriter(
                clientSocket.getOutputStream(), "UTF-8"));
        outStream.flush();
        BufferedReader inStream = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream(), "UTF-8"));

        clientThread = new ChatClientThread(this, outStream, inStream);
        Thread thread = new Thread(clientThread);
        thread.start();
    }

    public void sendMessage(String message) {
        try {
            clientThread.getWriter().write(message);
            clientThread.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected ChatApplication getMainApp() {
        return mainApp;
    }

    public String getClientId(){
        String id = clientSocket.getLocalSocketAddress().toString();
        return id.substring(id.indexOf(':')+1);
    }
}
