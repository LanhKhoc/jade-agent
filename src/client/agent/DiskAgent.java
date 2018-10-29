/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.agent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.List;
import utils.Common;
import utils.Disk;

/**
 *
 * @author Admin
 */
public class DiskAgent extends CoreAgent {
    @Override
    protected void setup() {
        addBehaviour(new ResponseDiskInfo());
        Common.debug("DiskAgent", "disk-client is running...");
    }
    
    private class ResponseDiskInfo extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            Common.debug("DiskAgent", "disk-client recieved message");
            if (msg != null) {
                if (isSpecialMessage(msg)) {
                    handleSpecialMessage(msg);
                } else {
                    ACLMessage msg1 = msg.createReply();
                    List<Disk> listInformation = Disk.getAll();
                    StringBuilder builder = new StringBuilder();
                    for (Disk driveInformation : listInformation) {
                        builder.append(driveInformation.toString());
                        builder.append("\n");
                    }
                    msg1.setContent(builder.toString());
                    send(msg1);
                    Common.debug("DiskAgent", "disk-client sent response");
                }
            } else {
                block();
            }
        }
    }
}
