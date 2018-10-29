/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.agent;

import jade.core.Agent;
import jade.core.ContainerID;
import jade.lang.acl.ACLMessage;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Admin
 */
public abstract class CoreAgent extends Agent {
    private final List<String> SPECIAL_MESSAGE = Arrays.asList("move", "delete", "suspend", "active");
    
    protected boolean isSpecialMessage(ACLMessage msg) {
        return SPECIAL_MESSAGE.contains(msg.getOntology());
    }
    
    // OPTIMIZE: Should use functional
    protected void handleSpecialMessage(ACLMessage msg) {
        switch (msg.getOntology().toLowerCase()) {
            case "move": {
                doMove(new ContainerID(msg.getContent(), null));
                break;
            }
            case "delete": {
                this.doDelete();
                break;
            }
            case "suspend": {
                this.doSuspend();
                break;
            }
            case "active": {
                this.doActivate();
                break;
            }
        }
    }
}
