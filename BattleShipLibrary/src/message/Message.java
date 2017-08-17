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
public abstract class Message implements Serializable {

    public int[][] matrix;
    public int row;
    public int col;
    public int command;

    public static final int SHOOT = 3;
}
