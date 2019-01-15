/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.ui.container;

import jade.core.Location;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import server.StoreServer;
import server.agent.ServerAgent;
import server.ui.component.Chat;
import server.ui.component.ControlAgent;
import server.ui.component.CreateAgent;
import server.ui.component.Notify;
import server.ui.component.ServerMonitor;
import utils.AgentUtil;
import utils.Common;

/**
 *
 * @author Admin
 */
public class ServerMonitorContainer {
    private static ServerAgent serverAgent;
    
    private static ServerMonitor component;
    
    private static JTable listAgentsTable;
    private static JLabel idAgentLabel;
    private static JLabel nameAgentLabel;
    private static JLabel positonAgentLabel;
    private static JLabel serverMonitorAgentLabel;
    private static JLabel statusAgentLabel;
    private static JLabel dateCreatedAgentLabel;
    private static JLabel serverIPLabel;
    private static JLabel serverPortLabel;
    private static JList listLocation;
    private static JLabel workstationNameLabel;
    private static JLabel workstationIPLabel;
    private static JLabel workstationOSLabel;
    private static JLabel workstationArchitectureLabel;
    private static JLabel workstationVersionLabel;
    
    public static void initServerAgent(ServerAgent _serverAgent) {
        serverAgent = _serverAgent;
    }
    
    public static void init(
        ServerMonitor _component,
        JTable _listAgentsTable,
        JLabel _idAgentLabel,
        JLabel _nameAgentLabel,
        JLabel _positonAgentLabel,
        JLabel _serverMonitorAgentLabel,
        JLabel _statusAgentLabel,
        JLabel _dateCreatedAgentLabel,
        JLabel _serverIPLabel,
        JLabel _serverPortLabel,
        JList _listLocation,
        JLabel _workstationNameLabel,
        JLabel _workstationIPLabel,
        JLabel _workstationOSLabel,
        JLabel _workstationArchitectureLabel,
        JLabel _workstationVersionLabel
    ) {
        component = _component;
        
        listAgentsTable = _listAgentsTable;
        
        idAgentLabel = _idAgentLabel;
        nameAgentLabel = _nameAgentLabel;
        positonAgentLabel = _positonAgentLabel;
        serverMonitorAgentLabel = _serverMonitorAgentLabel;
        statusAgentLabel = _statusAgentLabel;
        dateCreatedAgentLabel = _dateCreatedAgentLabel;
        
        serverIPLabel = _serverIPLabel;
        serverPortLabel = _serverPortLabel;
        
        listLocation = _listLocation;
        
        workstationNameLabel = _workstationNameLabel;
        workstationIPLabel = _workstationIPLabel;
        workstationOSLabel = _workstationOSLabel;
        workstationArchitectureLabel = _workstationArchitectureLabel;
        workstationVersionLabel = _workstationVersionLabel;
    }
    
    public static void showListAgents() {
        AMSAgentDescription[] descs = StoreServer.listAgents;
        Object[][] data = new Object[descs.length][4];
        for (int i = 0; i < descs.length; i++) {
            Map<String, String> map = AgentUtil.getDetailOfAgent(descs[i]);
            data[i][0] = map.get("name");
            data[i][1] = map.get("status");
            data[i][2] = map.get("position");
            data[i][3] = map.get("ip");
        }
        listAgentsTable.setModel(new DefaultTableModel(data, new String[] { "Name", "Status", "Position", "IP" }));
    }
    
    public static void showInforAgent(int index) {
        if (index > -1) {
            Map<String, String> map = AgentUtil.getDetailOfAgent(StoreServer.listAgents[index]);
            idAgentLabel.setText(map.get("id"));
            nameAgentLabel.setText(map.get("name"));
            positonAgentLabel.setText(map.get("position"));
            serverMonitorAgentLabel.setText("Server Monitor");
            statusAgentLabel.setText(map.get("status"));
            dateCreatedAgentLabel.setText("");
        }
    }
    
    public static void showServerInfor() {
        serverIPLabel.setText(StoreServer.serverIP);
        serverPortLabel.setText(StoreServer.port + "");
    }
    
    public static void showListLocations() {
        DefaultListModel<Location> listModel = new DefaultListModel<>();
        StoreServer.listLocations.forEach((location) -> {
            listModel.addElement(location);
        });

        listLocation.setModel(listModel);
    }
    
    public static void showWorkstationInfor() {
        workstationNameLabel.setText(StoreServer.workstationName);
        workstationIPLabel.setText(StoreServer.workstationIP);
        workstationOSLabel.setText(StoreServer.workstationOS);
        workstationArchitectureLabel.setText(StoreServer.workstationArchitecture);
        workstationVersionLabel.setText(StoreServer.workstationVersion);
    }
    
    public static void handleMoveAgentLocation() {
        int indexSelectedLocation = listLocation.getSelectedIndex();
        int indexSelectedAgent = listAgentsTable.getSelectedRow();
        Common.debug("MoveAgent", indexSelectedAgent + "->" + indexSelectedLocation);
        
        if (indexSelectedLocation > -1 && indexSelectedAgent > -1) {
            AMSAgentDescription selectedAgent = StoreServer.listAgents[indexSelectedAgent];
            Location selectedLocation = StoreServer.listLocations.get(indexSelectedLocation);
            String agentName = selectedAgent.getName().getLocalName();
            String locationName = selectedLocation.getName();
            Common.toast(component, "Moved " + agentName + " to " + locationName);

            try {
                AgentController agentCtrl = StoreServer.mainContainer.getAgent(agentName);
                agentCtrl.move(selectedLocation);
            } catch(ControllerException e) {
                Common.debug("MoveAgentCatch", agentName + "->" + locationName);
                serverAgent.sendMessageMoveAgent(agentName, locationName);
            }
        } else {
            Common.toast(component, "Please select agent and location!");
        }
    }
    
    public static void handleDeleteAgent() {
        int indexSelectedAgent = listAgentsTable.getSelectedRow();
        Common.debug("DeleteAgent", indexSelectedAgent + "");
        
        if (indexSelectedAgent > -1) {
            AMSAgentDescription selectedAgent = StoreServer.listAgents[indexSelectedAgent];
            String agentName = selectedAgent.getName().getLocalName();
            serverAgent.sendMessageDeleteAgent(agentName);
            
            Common.toast(component, "Delete " + agentName);
            reloadListAgents();
        } else {
            Common.toast(component, "Please select agent!");
        }
    }
    
    public static void handleSuspendAgent() {
        int indexSelectedAgent = listAgentsTable.getSelectedRow();
        Common.debug("SuspendAgent", "" + indexSelectedAgent);
        
        if (indexSelectedAgent > -1) {
            AMSAgentDescription selectedAgent = StoreServer.listAgents[indexSelectedAgent];
            String agentName = selectedAgent.getName().getLocalName();
            Common.toast(component, agentName + " suspended!");

            try {
                AgentController agentCtrl = StoreServer.mainContainer.getAgent(agentName);
                agentCtrl.suspend();
            } catch(ControllerException e) {
                serverAgent.sendMessageSuspendAgent(agentName);
            } finally {
                serverAgent.loadListAgents();
            }
        } else {
            Common.toast(component, "Please select agent!");
        }
    }
    
    public static void handleActiveAgent() {
        int indexSelectedAgent = listAgentsTable.getSelectedRow();
        Common.debug("ActiveAgent", "" + indexSelectedAgent);
        
        if (indexSelectedAgent > -1) {
            AMSAgentDescription selectedAgent = StoreServer.listAgents[indexSelectedAgent];
            String agentName = selectedAgent.getName().getLocalName();
            Common.toast(component, agentName + " actived!");

            try {
                AgentController agentCtrl = StoreServer.mainContainer.getAgent(agentName);
                agentCtrl.activate();
            } catch(ControllerException e) {
                serverAgent.sendMessageActiveAgent(agentName);
            } finally {
                serverAgent.loadListAgents();
            }
        } else {
            Common.toast(component, "Please select agent!");
        }
    }
    
    public static void reloadListLocations() {
        serverAgent.loadListLocations();
    }
    
    public static void reloadListAgents() {
        serverAgent.loadListAgents();
    }
    
    public static void refreshServer() {
        serverAgent.loadListAgents();
        serverAgent.loadServerInfor();
        serverAgent.loadListLocations();
        serverAgent.loadWorkstationInfor();
    }
    
    public static void showDiskInfor() {
        serverAgent.sendInternalRequest("disk-server");
    }
    
    public static void openModalCreateAgent() {
        new CreateAgent().setVisible(true);
    }
    
    public static void openModalChat() {
        new Chat().setVisible(true);
    }
    
    public static void showCaptureScreen() {
        serverAgent.sendInternalRequest("capture-screen-server");
    }
    
    public static void handleLogout() {
        serverAgent.sendInternalRequest("logout-server");
    }
    
    public static void handleRestart() {
        serverAgent.sendInternalRequest("restart-server");
    }
    
    public static void handleShutdown() {
        serverAgent.sendInternalRequest("shutdown-server");
    }
    
    public static void openModalNotify() {
        new Notify().setVisible(true);
    }
    
    public static void openModalControlAgent() {
        int indexSelectedAgent = listAgentsTable.getSelectedRow();
        Common.debug("ControlAgent", indexSelectedAgent + "");
        
        if (indexSelectedAgent > -1) {
            AMSAgentDescription selectedAgent = StoreServer.listAgents[indexSelectedAgent];
            String agentName = selectedAgent.getName().getLocalName();
            new ControlAgent().setVisible(true);
            ControlAgentContainer.setAgentName(agentName);
            ControlAgentContainer.setServerAgent(serverAgent);
        } else {
            Common.toast(component, "Please select agent!");
        }
    }
}
