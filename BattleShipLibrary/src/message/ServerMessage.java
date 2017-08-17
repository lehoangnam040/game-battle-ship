/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import java.io.Serializable;

/**
 *
 * @author Nam
 */
public class ServerMessage extends Message implements Serializable {

    public static final int LOGIN_SUCCESS = -1;
    public static final int OUT = -2;
    public boolean yourTurn;
}
