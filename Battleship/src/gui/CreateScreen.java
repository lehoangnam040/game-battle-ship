/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.ClientHandler;
import entity.Ship;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Nam
 */
public class CreateScreen extends javax.swing.JFrame {

    /**
     * Creates new form CreateScreen
     */
    public int[][] coordinates = new int[8][8];
    public Ship[] ships = new Ship[4];
    public ClientHandler handler;
    CreateScreen itself = this;

    public CreateScreen() {
        initComponents();
        this.setResizable(false);
        for (int i = 0; i < ships.length; i++) {
            ships[i] = new Ship();
        }
        setLabel(ships[0], 140, 0, 280, 70, "image\\AirCraftCarrier_Horizontal.png");
        setLabel(ships[1], 70, 140, 210, 70, "image\\BattleShip_Horizontal.png");
        setLabel(ships[2], 280, 280, 210, 70, "image\\Cruiser_Horizontal.png");
        setLabel(ships[3], 210, 490, 140, 70, "image\\PatrolBoat_Horizontal.png");

        setEvent(ships[0], "image\\AirCraftCarrier_Horizontal.png", "image\\AirCraftCarrier_Vertical.png");
        setEvent(ships[1], "image\\BattleShip_Horizontal.png", "image\\BattleShip_Vertical.png");
        setEvent(ships[2], "image\\Cruiser_Horizontal.png", "image\\Cruiser_Vertical.png");
        setEvent(ships[3], "image\\PatrolBoat_Horizontal.png", "image\\PatrolBoat_Vertical.png");
        CreateBoard board = new CreateBoard();
        this.add(board);
        board.setSize(new Dimension(560, 560));
    }

    public void setLabel(Ship ship, int x, int y, int w, int h, String img) {
        ship.label = new JLabel();
        ship.label.setSize(w, h);
        ship.label.setLocation(x, y);
        ship.label.setIcon(setImg(img, ship.label));
        Border border = new LineBorder(Color.BLACK, 1);
        ship.label.setBorder(border);
        ship.label.setOpaque(true);
        this.add(ship.label);
        ship.setLabel(ship.label);
        setCoordinates(ship, 1);
    }

    public void setEvent(Ship ship, String horizontal, String vertical) {
        ship.label.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                ship.label.setLocation((x / 70) * 70, (y / 70) * 70);
                if (canMove(ship)) {
                    ship.x = ship.label.getX();
                    ship.y = ship.label.getY();
                } else {
                    ship.label.setLocation(ship.x, ship.y);
                }
                setCoordinates(ship, 1);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setCoordinates(ship, 0);
                if (SwingUtilities.isLeftMouseButton(e)) {
                    x_press = e.getX() + itself.getX();
                    y_press = e.getY() + itself.getY();
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    ship.label.setSize(ship.label.getHeight(), ship.label.getWidth());  //ROTATE
                    if (ship.label.getX() + ship.label.getWidth() > 560
                            || ship.label.getY() + ship.label.getHeight() > 560
                            || !canMove(ship)) {
                        ship.label.setSize(ship.label.getHeight(), ship.label.getWidth());
                    } else if (ship.label.getWidth() == 70) {
                        ship.label.setIcon(setImg(vertical, ship.label));
                    } else {
                        ship.label.setIcon(setImg(horizontal, ship.label));
                    }
                }
            }
        });

        ship.label.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x = e.getXOnScreen() - x_press;
                y = e.getYOnScreen() - y_press;
                if (x < 0) {
                    x = 0;
                } else if (x > 560 - ship.label.getWidth()) {
                    x = 560 - ship.label.getWidth();
                }
                if (y > 560 - ship.label.getHeight()) {
                    y = 560 - ship.label.getHeight();
                }
                ship.label.setLocation(x, y);
            }
        });
    }

    public ImageIcon setImg(String path, JLabel label) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            return new ImageIcon(img.getScaledInstance(label.getWidth(), label.getHeight(),
                    Image.SCALE_SMOOTH));
        } catch (IOException ex) {
            Logger.getLogger(CreateScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnLogin = new javax.swing.JButton();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("*Arrange your ships!! \n\n*Drag and drop to \n move the ship you want. \n\n*Use right mouse \n to rotate the ship.");
        jTextArea1.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 590, Short.MAX_VALUE)
                .addComponent(jTextArea1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jTextArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jTextArea1.getAccessibleContext().setAccessibleParent(this);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    int x_press;
    int y_press;
    int x;
    int y;

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        handler = new ClientHandler();
        handler.login(this);
    }//GEN-LAST:event_btnLoginActionPerformed

    public boolean canMove(Ship ship) {
        if (ship.label.getWidth() == 70) {
            //vertical
            for (int i = 0; i < ship.length; i++) {
                if (coordinates[y / 70 + i][x / 70] == 1) {
                    return false;
                }
            }
        } else {
            //horizontal
            for (int i = 0; i < ship.length; i++) {
                if (coordinates[y / 70][x / 70 + i] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setCoordinates(Ship ship, int value) {
        if (ship.label.getWidth() == 70) {
            //vertical
            for (int i = 0; i < ship.length; i++) {
                coordinates[ship.y / 70 + i][ship.x / 70] = value;
            }
        } else {
            //horizontal
            for (int i = 0; i < ship.length; i++) {
                coordinates[ship.y / 70][ship.x / 70 + i] = value;
            }
        }
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
