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
import server.ui.component.DiskInformation;
import server.ui.container.DiskInformationContainer;
import utils.Common;

/**
 *
 * @author Admin
 */
public class DiskAgent extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new RequestDiskInfo());
        new DiskInformation().setVisible(false);
        DiskInformationContainer.initAgent(this);
        Common.debug("DiskServer", "DiskAgent is running...");
    }

    private class RequestDiskInfo extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            Common.debug("DiskAgent", "DiskAgent received message: " + msg);
            if (msg != null) {
                if (msg.getPerformative() == ACLMessage.INFORM) {
                    ACLMessage msg1 = new ACLMessage(ACLMessage.REQUEST);
                    msg1.addReceiver(new AID("disk-client", AID.ISLOCALNAME));
                    msg1.setLanguage("Vietnamese");
                    msg1.setOntology("LanhKhoc");
                    msg1.setContent("DiskServer");
                    send(msg1);
                    Common.debug("DiskAgent", "DiskAgent sent request");
                } else {
                    Common.debug("DiskAgentResponse", msg.getContent());
                    DiskInformationContainer.showDiskInfor(msg.getContent());
                }
            } else {
                block();
            }
        }
    }
}
