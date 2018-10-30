/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.ui.container;

import client.agent.ChatAgent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author Admin
 */
public class ChatContainer {
    private static ChatAgent chatAgent;
    
    private static JFrame component;
    private static JEditorPane chatContentPane;
    private static JTextField chatTxt;
    
    public static void initAgent(ChatAgent _chatAgent) {
        chatAgent = _chatAgent;
    }
    
    public static void initComponent(
        JFrame _component,
        JEditorPane _chatContentPane,
        JTextField _chatTxt
            
    ) {
        component = _component;
        chatContentPane = _chatContentPane;
        chatTxt = _chatTxt;
    }
    
    public static void handleSendChat() {
        String message = chatTxt.getText();
        chatAgent.sendInternalRequest("chat-client", message);
    }
    
    public static void showChat() {
        component.setVisible(true);
    }
    
    public static void printMessage(String who, String message) {
        try {
            String chat = who + ": " + message + "\n";
            Document doc = chatContentPane.getDocument();
            doc.insertString(doc.getLength(), chat, null);
            chatTxt.setText("");
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
