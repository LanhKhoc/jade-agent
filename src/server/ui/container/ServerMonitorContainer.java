/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.ui.container;

import jade.core.Location;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import server.StoreServer;
import server.agent.ServerAgent;

/**
 *
 * @author Admin
 */
public class ServerMonitorContainer {
    private static ServerAgent serverAgent;
    
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
    
    private static String getIPAddressFromName(String name) {
        // NOTE: AgentName@IP:Port
        int index1 = name.indexOf('@');
        int index2 = name.indexOf(':');
        return name.substring(index1 + 1, index2);
    }
    
    private static Map<String, String> getDetailOfAgent(AMSAgentDescription desc) {
        Map<String, String> map = new HashMap<>();
        String[] addresses = desc.getName().getAddressesArray();
        
        map.put("id", desc.getName().getName());
        map.put("name", desc.getName().getLocalName());
        map.put("status", desc.getState());
        map.put("position", (addresses != null && addresses.length > 0) ? addresses[0] : "");
        map.put("ip", getIPAddressFromName(map.get("id")));
        return map;
    }
    
    public static void showListAgents() {
        AMSAgentDescription[] descs = StoreServer.listAgents;
        Object[][] data = new Object[descs.length][4];
        for (int i = 0; i < descs.length; i++) {
            Map<String, String> map = getDetailOfAgent(descs[i]);
            data[i][0] = map.get("name");
            data[i][1] = map.get("status");
            data[i][2] = map.get("position");
            data[i][3] = map.get("ip");
        }
        listAgentsTable.setModel(new DefaultTableModel(data, new String[] { "Name", "Status", "Position", "IP" }));
    }
    
    public static void showInforAgent(int index) {
        if (index > -1) {
            Map<String, String> map = getDetailOfAgent(StoreServer.listAgents[index]);
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
    
    public static void reloadListLocations() {
        serverAgent.loadListLocations();
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
}
