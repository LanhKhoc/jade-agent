/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

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
}
