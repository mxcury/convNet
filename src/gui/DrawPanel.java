/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import static io.ImageLoader.displayImage;

public class DrawPanel extends javax.swing.JPanel implements MouseMotionListener, MouseListener {

    private BufferedImage bufferedImage;

   //creates a draw panel
    public DrawPanel() {
        initComponents();

        bufferedImage = new BufferedImage(468,468,BufferedImage.TYPE_INT_ARGB);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }



    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Graphics g = bufferedImage.getGraphics();
        g.setColor(Color.white);
        g.fillOval(e.getX(), e.getY(), 20, 20);
        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0,0,1000,1000);
        g.drawImage(bufferedImage,0, 0, this);
    }

}
