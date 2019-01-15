/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.agent;

import client.ui.component.Chat;
import client.ui.container.ChatContainer;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import javax.swing.JFrame;
import utils.Common;

/**
 *
 * @author Admin
 */
public class ChatAgent extends CoreAgent {
    private ACLMessage replyTo;
    private JFrame chatComponent = null;
    
    @Override
    protected void setup() {
        addBehaviour(new ResponseChat());
        Common.debug("ChatAgent", "chat-client is running...");
    }
    
    private JFrame getComponent() {
        if (chatComponent == null) {
            chatComponent = new Chat();
        }
        return chatComponent;
    }
    
    private void handleReceiveMessageFromServer(ACLMessage msg) {
        replyTo = msg.createReply();
    }
    
    private void handleSendMessageToServer(String message) {
        replyTo.setContent(message);
        send(replyTo);
    }
    
    private class ResponseChat extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            if (msg != null) {
                Common.debug("ChatAgent", "chat-client recieved message: " + msg.getContent());
                if (isSpecialMessage(msg)) {
                    handleSpecialMessage(msg);
                } else {
                    String content = msg.getContent();
                    switch (msg.getPerformative()) {
                        case ACLMessage.REQUEST: {
                            handleReceiveMessageFromServer(msg);
                            
                            // NOTE: If we init ChatComponent in constuctor it will not work
                            // Because that initial will be called in Server
                            // Move agent to Client make that code not work anymore
                            // So in here, we have to re-init both Component and Agent for ChatContainer
                            getComponent().setVisible(true);
                            ChatContainer.initAgent(ChatAgent.this);
                            ChatContainer.printMessage("Server", content);
                            break;
                        }
                        case ACLMessage.INFORM: {
                            handleSendMessageToServer(msg.getContent());
                            ChatContainer.printMessage("You", content);
                        }
                    }
                }
            } else {
                block();
            }
        }
    }
}
