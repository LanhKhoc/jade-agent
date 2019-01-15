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
public class RestartAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new RequestRestart());
        Common.debug("RestartServer", "RestartAgent is running...");
    }

    private class RequestRestart extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            Common.debug("RestartServer", "RestartAgent received message: " + msg);
            if (msg != null) {
                if (msg.getPerformative() == ACLMessage.INFORM) {
                    ACLMessage msg1 = new ACLMessage(ACLMessage.REQUEST);
                    msg1.addReceiver(new AID("restart-client", AID.ISLOCALNAME));
                    msg1.setLanguage("Vietnamese");
                    msg1.setOntology("LanhKhoc");
                    msg1.setContent("LogoutServer");
                    send(msg1);
                    Common.debug("RestartServer", "RestartAgent sent request");
                } else {
                    Common.debug("RestartServerResponse", msg.getContent());
                }
            } else {
                block();
            }
        }
    }
}
