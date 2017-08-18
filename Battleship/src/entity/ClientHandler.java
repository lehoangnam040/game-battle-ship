/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import gui.CreateScreen;
import gui.MainScreen;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import message.ClientMessage;
import message.ServerMessage;

/**
 *
 * @author Nam
 */
public class ClientHandler {

    public Socket socket;
    CreateScreen createScreen;
    MainScreen mainScreen;
    int hp;

    public void login(CreateScreen createScreen) {
        while (true) {
            try {
                this.createScreen = createScreen;
                createScreen.setVisible(false);
                mainScreen = new MainScreen(createScreen);
                mainScreen.setVisible(true);
                socket = new Socket("localhost", 9999);
                //replace localhost = ip of server computer. for example: 192.168.43.240
                ServerConnecter connecter = new ServerConnecter();
                connecter.start();
                ClientMessage clientMess = new ClientMessage();
                clientMess.matrix = createScreen.coordinates;
                clientMess.command = ClientMessage.LOGIN;
                sendMessage(clientMess);
                //create main screen
                break;
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void logout() {
        mainScreen.dispose();
        createScreen.setVisible(true);
        ClientMessage clientMess = new ClientMessage();
        clientMess.command = ClientMessage.LOGOUT;
        sendMessage(clientMess);
    }

    public void sendMessage(ClientMessage clientMess) {
        try {
            //send message to server
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(clientMess);
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class ServerConnecter extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    ServerMessage serverMess = (ServerMessage) ois.readObject();
                    switch (serverMess.command) {
                        case ServerMessage.LOGIN_SUCCESS:
                            mainScreen.setEnemy(serverMess.matrix);
                            hp = healthpoint(createScreen.coordinates);
                            mainScreen.myHP.setText(hp + "");
                            mainScreen.enemyHP.setText(mainScreen.eneHP + "");
                            mainScreen.turn = serverMess.yourTurn;
                            mainScreen.lblTurn.setText(serverMess.yourTurn ? "Your turn" : "Enemy turn");
                            mainScreen.lblTurn.setForeground(serverMess.yourTurn ? Color.BLUE : Color.RED);
                            break;
                        case ServerMessage.OUT:
                            logout();
                            break;
                        case ServerMessage.SHOOT:
                            int row = serverMess.row;
                            int col = serverMess.col;
                            mainScreen.turn = serverMess.yourTurn;
                            if (createScreen.coordinates[row][col] == 1) {
                                //hien thi tau bi ban trung
                                mainScreen.myBoard.paintSquare(row, col, "image\\fire.gif");
                                hp--;
                                mainScreen.myHP.setText(hp + "");
                                if (hp == 0) {
                                    logout();
                                    JOptionPane.showMessageDialog(mainScreen, "You lose");
                                }
                            } else {
                                //hien thi tau bi ban truot
                                mainScreen.myBoard.paintSquare(row, col, "image\\wave.png");
                            }
                            mainScreen.lblTurn.setText("Your turn");
                            mainScreen.lblTurn.setForeground(Color.BLUE);
                            break;
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static int healthpoint(int[][] matrix) {
        int hp = 0;
        int row = matrix.length;
        int col = matrix[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] > 0) {
                    hp += matrix[i][j];
                }
            }
        }
        return hp;
    }
}
