/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class Common {
    private static boolean develop = true;
    
    public static void debug(String label, String value) {
        if (label.equals("")) { label = "DEVELOP"; }
        if (develop) {
            System.out.println(">>> " + label.toUpperCase() + ": " + value);
        }
    }
    
    public static void toast(JFrame context, String message) {
        JOptionPane.showMessageDialog(context, message);
    }
}
