/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.agent;

import helper.GetAvailableLocationsBehaviour;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.StoreServer;
import server.ui.component.ServerMonitor;
import server.ui.container.ServerMonitorContainer;

/**
 *
 * @author Admin
 */
public class ServerAgent extends Agent {
    @Override
    protected void setup() {
        try {
            ServerMonitorContainer.initServerAgent(this);
            new ServerMonitor().setVisible(true);
            TimeUnit.SECONDS.sleep(3);
            
            this.loadListAgents();
            this.loadServerInfor();
            this.loadListLocations();
            this.loadWorkstationInfor();
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadListAgents() {
        StoreServer.listAgents = this.getAllAgents();
        ServerMonitorContainer.showListAgents();
    }
    
    public void loadServerInfor() {
        try {
            StoreServer.serverIP = InetAddress.getLocalHost().getHostAddress();
            ServerMonitorContainer.showServerInfor();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadWorkstationInfor() {
        try {
            StoreServer.workstationName = InetAddress.getLocalHost().getHostName();
            StoreServer.workstationIP = StoreServer.serverIP;
            StoreServer.workstationOS = System.getProperty("os.name");
            StoreServer.workstationArchitecture = System.getProperty("os.arch");
            StoreServer.workstationVersion = System.getProperty("os.version");
            ServerMonitorContainer.showWorkstationInfor();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // NOTE: Load and show in GetAvailableLocationsBehaviour
    public void loadListLocations() {
        getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);
        getContentManager().registerOntology(MobilityOntology.getInstance());
        addBehaviour(new GetAvailableLocationsBehaviour(this));
    }
    
    private AMSAgentDescription[] getAllAgents() {
        AMSAgentDescription[] agents = null;
        try {
            SearchConstraints c = new SearchConstraints();
            c.setMaxResults(new Long(-1));
            agents = AMSService.search(this, new AMSAgentDescription(), c);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        return agents;
    }
    
    public void sendInternalRequest(String who) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID(who, AID.ISLOCALNAME));
        msg.setLanguage("vietnamese");
        msg.setOntology("lanhkhoc");
        msg.setContent(who);
        send(msg);
    }
    
    public void sendMessageMoveAgent(String who, String location) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID(who, AID.ISLOCALNAME));
        msg.setOntology("move");
        msg.setContent(location);
        send(msg);
    }
    
    public void sendMessageDeleteAgent(String who) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID(who, AID.ISLOCALNAME));
        msg.setOntology("delete");
        send(msg);
    }
}
