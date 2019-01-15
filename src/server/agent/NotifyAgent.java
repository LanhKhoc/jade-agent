/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.agent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import server.ui.container.NotifyContainer;
import utils.Common;

/**
 *
 * @author Admin
 */
public class NotifyAgent extends CoreAgent {
    @Override
    protected void setup() {
        addBehaviour(new RequestNotify());
        NotifyContainer.initAgent(this);
        Common.debug("NotifyServer", "notify-agent is running...");
    }
    
    private class RequestNotify extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            if (msg != null) {
                String message = msg.getContent();
                Common.debug("NotifyServer", "notify-agent received message: " + message);
                
                if (msg.getPerformative() == ACLMessage.INFORM) {
                    sendExternalRequestByRequest("notify-client", message);
                    NotifyContainer.printMessage("You", message);
                }
            } else {
                block();
            }
        }
    }
}
