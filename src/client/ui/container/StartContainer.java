/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.ui.container;

import javax.swing.JTextField;
import client.CONFIG;
import client.StoreClient;

/**
 *
 * @author Admin
 */
public class StartContainer {
    private static JTextField portServerTxt;
    private static JTextField ipServerTxt;
    
    public static void init(
        JTextField _portServerTxt,
        JTextField _ipServerTxt
    ) {
        ipServerTxt = _ipServerTxt;
        portServerTxt = _portServerTxt;
        
        fillDefaultToInput();
    }
    
    private static void fillDefaultToInput() {
        portServerTxt.setText(CONFIG.PORT + "");
    }
    
    public static void handleConnectToServer() {
        StoreClient.ipServer = ipServerTxt.getText();
        StoreClient.portServer = portServerTxt.getText();
    }
}
