/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.ui.container;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javax.swing.JTextField;
import server.CONFIG;
import server.StoreServer;
import utils.Common;

/**
 *
 * @author Admin
 */
public class StartContainer {
    private static JTextField pathImagesTxt;
    private static JTextField serverPortTxt;
    
    public static void init(   
        JTextField _pathImagesTxt,
        JTextField _serverPortTxt
    ) {
        pathImagesTxt = _pathImagesTxt;
        serverPortTxt = _serverPortTxt;
    }
    
    public static void handleOkAction() {
        String pathImages = pathImagesTxt.getText();
        String serverPort = serverPortTxt.getText();
        
        // TODO: Validate pathImages and port
        StoreServer.pathImages = pathImages;
        StoreServer.port = Integer.parseInt(serverPort);
        
        initServerAgents();
    }
    
    public static void handleClickDefaultBtn() {
        pathImagesTxt.setText(CONFIG.PATH_IMAGES);
        serverPortTxt.setText(CONFIG.PORT + "");
        StoreServer.pathImages = CONFIG.PATH_IMAGES;
        StoreServer.port = CONFIG.PORT;
    }
    
    private static void initServerAgents() {
        Runtime rt = Runtime.instance();
        
        // NOTE: Exit the JVM when there are no more containers around
        rt.setCloseVM(true);
        Common.debug("StartContainer", "Runtime created");
        
        // NOTE: Create a default profile
        Profile profile = new ProfileImpl("localhost", StoreServer.port, null);
        Common.debug("StartContainer", "Profile created");
        StoreServer.mainContainer = rt.createMainContainer(profile);
        
        // NOTE: Start server agents
        try {
            AgentController rma = StoreServer.mainContainer.createNewAgent("rma", "jade.tools.rma.rma", new Object[0]);
            rma.start();
            
            createAgent("ServerAgent", "server.agent.ServerAgent", new Object[0]);
            createAgent("disk-server", "server.agent.DiskAgent", new Object[0]);
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
    
    private static void createAgent(String agentNickName, String agentClassName, Object[] params) throws StaleProxyException {
        StoreServer.mainContainer.createNewAgent(
            agentNickName,
            agentClassName,
            params
        ).start();
    }
}
