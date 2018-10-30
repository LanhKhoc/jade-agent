/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Admin
 */
public abstract class CoreAgent extends Agent {
    public void sendInternalRequest(String who, String content) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID(who, AID.ISLOCALNAME));
        msg.setLanguage("Vietnamese");
        msg.setOntology("LanhKhoc");
        msg.setContent(content);
        send(msg);
    }
    
    public void sendExternalRequestByRequest(String who, String content) {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID(who, AID.ISLOCALNAME));
        msg.setLanguage("Vietnamese");
        msg.setOntology("LanhKhoc");
        msg.setContent(content);
        send(msg);
    }
}
