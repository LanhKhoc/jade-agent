/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.ui.container;

import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import server.StoreServer;
import server.agent.ServerAgent;
import server.ui.component.ControlAgent;
import utils.Common;

/**
 *
 * @author Admin
 */
public class ControlAgentContainer {

    private static ControlAgent component;
    private static JLabel agentNameLabel;
    private static ServerAgent serverAgent;
    private static String agentName;

    public static void init(
            ControlAgent _component,
            JLabel _agentNameLabel
    ) {
        component = _component;
        agentNameLabel = _agentNameLabel;
    }

    public static void setAgentName(String _agentName) {
        agentName = _agentName;
        agentNameLabel.setText("Agent: " + agentName);
    }

    public static void setServerAgent(ServerAgent _serverAgent) {
        serverAgent = _serverAgent;
    }

    public static void handleSuspendAgent() {
        ServerMonitorContainer.handleSuspendAgent();
    }
    
    public static void handleActiveAgent() {
        ServerMonitorContainer.handleActiveAgent();
    }
}
