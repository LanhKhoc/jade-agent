/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.ui.container;

import jade.core.Runtime;
import javax.swing.JTextField;
import client.CONFIG;
import client.StoreClient;
import jade.core.ProfileImpl;
import utils.Common;

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
        StoreClient.portServer = Integer.parseInt(portServerTxt.getText());
        
        new Thread(() -> {
            initAgentContainer();
        }).start();
    }
    
    private static void initAgentContainer() {
        // Get a hold on JADE runtime
        Runtime rt = Runtime.instance();

        // NOTE: Exit the JVM when there are no more containers around
        rt.setCloseVM(true);
        Common.debug("StartClient", "Runtime created");

        // NOTE: Set the default Profile to start a container
        ProfileImpl pContainer = new ProfileImpl(StoreClient.ipServer, StoreClient.portServer, null);
        StoreClient.agentContainer = rt.createAgentContainer(pContainer);
        
        Common.debug("StartClient", "Launching the agent container " + pContainer);
        Common.toastDev(null, "Connect successfull!");
    }
}
