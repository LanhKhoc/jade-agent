/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.ui.container;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class CaptureScreenContainer {
    public static void displayImageFromFile(String imagePath) {
        try {
            BufferedImage buffer = ImageIO.read(new File(imagePath));
            ImageIcon icon = new ImageIcon(buffer);
            JLabel label = new JLabel(icon);
            JOptionPane.showMessageDialog(null, label, "Preview", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            String message = String.format("An error occurred when open image at path:\n%s.", imagePath);
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
