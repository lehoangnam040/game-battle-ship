/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.net.Socket;
import java.util.Objects;

/**
 *
 * @author Nam
 */
public class Client {
    
    public Socket socket;
    public int[][] matrix;
    public Client opposite;
    
    public Client(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.socket);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        return this.socket.equals(other.socket);
    }
    
}
