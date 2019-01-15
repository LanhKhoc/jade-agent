/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.agent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import server.ui.container.ChatContainer;
import utils.Common;

/**
 *
 * @author Admin
 */
public class ChatAgent extends CoreAgent {
    @Override
    protected void setup() {
        addBehaviour(new RequestChat());
        ChatContainer.initAgent(this);
        Common.debug("ChatAgent", "chat-server is running...");
    }
    
    private class RequestChat extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            if (msg != null) {
                String message = msg.getContent();
                Common.debug("ChatAgent", "chat-server received message: " + message);
                
                if (msg.getPerformative() == ACLMessage.INFORM) {
                    sendExternalRequestByRequest("chat-client", message);
                    ChatContainer.printMessage("You", message);
                } else {
                    ChatContainer.printMessage("Client", message);
                }
            } else {
                block();
            }
        }
    }
}
