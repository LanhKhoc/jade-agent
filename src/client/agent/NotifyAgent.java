/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.agent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import javax.swing.JOptionPane;
import utils.Common;

/**
 *
 * @author Admin
 */
public class NotifyAgent extends CoreAgent {
    @Override
    protected void setup() {
        addBehaviour(new ResponseNotify());
        Common.debug("NotifyClient", "notify-agent is running...");
    }

    class ResponseNotify extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            if (msg != null) {
                if (isSpecialMessage(msg)) {
                    handleSpecialMessage(msg);
                } else {
                    String content = msg.getContent();
                    Common.toast(null, msg.getContent());
                    Common.debug("NotifyClient", "notify-agent recieved message: " + content);
                }
            } else {
                block();
            }
        }

    }
}
