/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import entity.Client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.ClientMessage;
import message.ServerMessage;

/**
 *
 * @author Nam
 */
public class MyServer {

    ServerSocket server;
    ArrayList<Client> clients = new ArrayList<>();

    public MyServer() {
        try {
            server = new ServerSocket(9999);
            ClientConnecter connecter = new ClientConnecter();
            connecter.start();
        } catch (IOException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class ClientConnecter extends Thread {

        @Override
        public void run() {
            System.out.println("Server starting........!");
            while (true) {
                try {
                    Socket socket = server.accept();
                    Client served = new Client(socket);
                    ClientHandler handler = new ClientHandler(served);
                    handler.start();
                } catch (IOException ex) {
                    Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    class ClientHandler extends Thread {

        Client served;

        public ClientHandler(Client served) {
            this.served = served;
        }

        @Override
        public void run() {
            try {
                OUTER:
                while (true) {
                    ObjectInputStream ois = new ObjectInputStream(served.socket.getInputStream());
                    ClientMessage clientMess = (ClientMessage) ois.readObject();
                    ServerMessage serverMess = new ServerMessage();
                    switch (clientMess.command) {
                        case ClientMessage.LOGIN:
                            served.matrix = clientMess.matrix;
                            clients.add(served);
                            if (clients.size() % 2 == 0) { //2 player to play
                                Client player2 = clients.get(clients.size() - 2);
                                serverMess.command = ServerMessage.LOGIN_SUCCESS;
                                serverMess.matrix = served.matrix;
                                serverMess.yourTurn = true;
                                sendMessage(serverMess, player2.socket);
                                serverMess.matrix = player2.matrix;
                                serverMess.yourTurn = false;
                                sendMessage(serverMess, served.socket);
                                served.opposite = player2;
                                player2.opposite = served;
                            }
                            
                            break;
                        case ClientMessage.LOGOUT:
                            if (clients.contains(served.opposite)) {
                                serverMess.command = ServerMessage.OUT;
                                sendMessage(serverMess, served.opposite.socket);
                            }
                            clients.remove(served);
                            break OUTER;
                        case ClientMessage.SHOOT:
                            serverMess.row = clientMess.row;
                            serverMess.col = clientMess.col;
                            serverMess.yourTurn = true;
                            serverMess.command = ServerMessage.SHOOT;
                            sendMessage(serverMess, served.opposite.socket);
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void sendMessage(ServerMessage serverMess, Socket socket) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(serverMess);
        } catch (IOException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
