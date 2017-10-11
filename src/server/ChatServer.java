package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ChatServer{
    private final int PORT = 4242;
    private boolean isFirstMessage;
    private final List<ChatServerThread> clientList = new ArrayList<>();
    private final List<String> clientsNamesList = new ArrayList<>();
    private final List<String> clientsIdList= new ArrayList<>();
    final private Logger LOG = Logger.getLogger(ChatServer.class.getName());

    public void start() {
        isFirstMessage = true;
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            LOG.info("Server has started and is listening for clients.");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                String id = clientSocket.getRemoteSocketAddress().toString();
                clientsIdList.add(id.substring(id.indexOf(':')+1));
                writeClientsToFile();
                LOG.info("A client has connected.");
                BufferedWriter outStream = new BufferedWriter(new OutputStreamWriter(
                        clientSocket.getOutputStream(), "UTF-8"));
                outStream.flush();
                BufferedReader inStream = new BufferedReader(new InputStreamReader(
                        clientSocket.getInputStream(), "UTF-8"));
                LOG.info("Streams to client established.");
                ChatServerThread chatThread = new ChatServerThread(this, outStream, inStream);
                clientList.add(chatThread);
                Thread thread = new Thread(chatThread);
                thread.start();
                LOG.info("A new client thread started.");
                isFirstMessage =true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeClient(ChatServerThread client) {
        int indexToRemove = clientList.indexOf(client);
        if(clientsNamesList.size()>indexToRemove)
            clientsNamesList.remove(indexToRemove);
        if(clientsIdList.size()>indexToRemove)
            clientsIdList.remove(indexToRemove);
        clientList.remove(indexToRemove);
        writeClientsToFile();
}

    public void sendToClients(String message, ChatServerThread sender) {

        LOG.info("Sending message: " + message);
        for (ChatServerThread clientThread : clientList) {
            if(clientsNamesList.indexOf(message.substring(1,message.indexOf('>')))<0)
                clientsNamesList.add(message.substring(1,message.indexOf('>')));
            if (clientThread == sender)
                continue;
            try {
                clientThread.getWriter().write(message + "\n");
                clientThread.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writeClientsToFile();
    }

    public void writeClientsToFile(){
        try(FileWriter writer = new FileWriter("clients.txt")) {
            for (int i = 0; i < clientList.size(); i++) {
                if(clientsNamesList.size()>i)
                    writer.write(clientsIdList.get(i)+" "+clientsNamesList.get(i)+"\r\n");
                else
                    writer.write(clientsIdList.get(i)+"\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
