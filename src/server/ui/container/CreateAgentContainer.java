/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.ui.container;

import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.wrapper.StaleProxyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import server.StoreServer;
import server.ui.component.ServerMonitor;
import utils.AgentUtil;
import utils.Common;

/**
 *
 * @author Admin
 */
public class CreateAgentContainer {
    private static JFrame component;
    private static JComboBox listAgentClientComboBox;
    
    public static void init(
        JFrame _component,
        JComboBox _listAgentClientComboBox
    ) {
        component = _component;
        listAgentClientComboBox = _listAgentClientComboBox;
        
        fillOptionsValue();
    }
    
    private static void fillOptionsValue() {
        AMSAgentDescription[] descs = StoreServer.listAgents;
        for (AMSAgentDescription desc : descs) {
            if (AgentUtil.isAgentOfServer(desc)) {
                String name = AgentUtil.getAgentNameWithoutPostfix(desc);
                listAgentClientComboBox.addItem(name);
            }
        }
    }
    
    public static void handleCreateAgent() {
        try {
            String agentNameSelected = (String) listAgentClientComboBox.getSelectedItem();
            AgentUtil.createAgent(
                StoreServer.mainContainer,
                agentNameSelected.toLowerCase() + "-client",
                "client.agent." + agentNameSelected + "Agent",
                new Object[0]
            );
            
            Common.toast(null, "Created " + agentNameSelected + "Agent");
            ServerMonitorContainer.reloadListAgents();
        } catch (StaleProxyException ex) {
            Logger.getLogger(CreateAgentContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        closeComponent();
    }
      
    private static void closeComponent() {
        component.setVisible(false);
        component.dispose();
    }
}
