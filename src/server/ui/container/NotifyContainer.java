/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.ui.container;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import server.agent.NotifyAgent;

/**
 *
 * @author Admin
 */
public class NotifyContainer {
    private static NotifyAgent notifyAgent;
    
    private static JFrame component;
    private static JEditorPane notifyContentPane;
    private static JTextField notifyTxt;
    
    public static void initAgent(NotifyAgent _notifyAgent) {
        notifyAgent = _notifyAgent;
    }
    
    public static void initComponent(
        JFrame _component,
        JEditorPane _chatContentPane,
        JTextField _chatTxt
            
    ) {
        component = _component;
        notifyContentPane = _chatContentPane;
        notifyTxt = _chatTxt;
    }
    
    public static void handleSendChat() {
        String message = notifyTxt.getText();
        if (!message.equals("")) {
            notifyAgent.sendInternalRequest("notify-server", message);
        }
    }
    
    public static void printMessage(String who, String message) {
        try {
            String chat = who + ": " + message + "\n";
            Document doc = notifyContentPane.getDocument();
            doc.insertString(doc.getLength(), chat, null);
            if (who.equals("You")) {
                notifyTxt.setText("");
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
