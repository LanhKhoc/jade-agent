/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.ui.container;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import server.agent.DiskAgent;

/**
 *
 * @author Admin
 */
public class DiskInformationContainer {
    private static JFrame component;
    private static JTextPane diskInforPane;
    private static DiskAgent diskAgent;
    
    public static void init(
        JFrame _component,
        JTextPane _diskInforPane
    ) {
        component = _component;
        diskInforPane = _diskInforPane;
    }
    
    public static void initAgent(DiskAgent _diskAgent) {
        diskAgent = _diskAgent;
    }
    
    public static void showDiskInfor(String diskInfo) {
        diskInforPane.setText(diskInfo);
        component.setVisible(true);
    }
}
