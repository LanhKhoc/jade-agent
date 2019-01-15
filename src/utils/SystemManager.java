/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;

/**
 *
 * @author Admin
 */
public class SystemManager {

    public static void shutdown() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Runtime runtime = Runtime.getRuntime();
                Process process = null;
                try {
                    process = runtime.exec("shutdown -s -t 0");
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    process.destroy();
                }
            }
        }).start();
    }

    public static void logout() {
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr1 = rt.exec("shutdown -l"); // for log off
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void restart() {
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr2 = rt.exec("shutdown -r -t 0"); // for log off
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
