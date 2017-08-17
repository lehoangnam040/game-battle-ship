/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.swing.JLabel;

/**
 *
 * @author Nam
 */
public class Ship {

    public int length;
    public int x;
    public int y;
    public JLabel label;

    public Ship() {
    }

    public void setLabel(JLabel label) {
        x = label.getX();
        y = label.getY();
        if (label.getWidth() == 70) {
            this.length = label.getHeight() / 70;
        } else {
            this.length = label.getWidth() / 70;
        }
    }

}
