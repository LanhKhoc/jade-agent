/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import utils.Common;

/**
 *
 * @author Admin
 */
public class ChatAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new RequestChat());
        Common.debug("ChatAgent", "chat-server is running...");
    }
    
    private class RequestChat extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            Common.debug("ChatAgent", "chat-server received message");
            if (msg != null) {
                if (msg.getPerformative() == ACLMessage.INFORM) {
                    ACLMessage msg1 = new ACLMessage(ACLMessage.REQUEST);
                    msg1.addReceiver(new AID("chat-client", AID.ISLOCALNAME));
                } else {
                }
            } else {
                block();
            }
        }
    }
}
