/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.agent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import utils.Common;
import utils.SystemManager;

/**
 *
 * @author Admin
 */
public class ShutdownAgent extends CoreAgent {
    @Override
    protected void setup() {
        addBehaviour(new ResponseShutdown());
        Common.debug("ShutdownClient", "ShutdownAgent is running...");
    }
    
    private class ResponseShutdown extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            Common.debug("ShutdownClient", "ShutdownAgent recieved message");
            if (msg != null) {
                if (isSpecialMessage(msg)) {
                    handleSpecialMessage(msg);
                } else {
                    ACLMessage msg1 = msg.createReply();
                    msg1.setContent("Client logout!");
                    send(msg1);
                    Common.debug("ShutdownClient", "ShutdownAgent sent response");
                    SystemManager.shutdown();
                }
            } else {
                block();
            }
        }
    }
}
