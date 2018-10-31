/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.agent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;
import utils.CaptureScreen;
import utils.Common;

/**
 *
 * @author Admin
 */
public class CaptureScreenAgent extends CoreAgent {
    @Override
    protected void setup() {
        addBehaviour(new ResponseCaptureScreen());
        Common.debug("CaptureScreenAgent", getAID().getName() + " is running...");
    }
    
    private String imgToBase64(File img) {
        String res = "";
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(img);
            byte[] bytes = new byte[(int)img.length()];
            fileInputStreamReader.read(bytes);
            res = new String(Base64.encodeBase64(bytes), "UTF-8");
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        return res;
    }
    
    private class ResponseCaptureScreen extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            Common.debug("CaptureScreenAgent", agentDebug("recieved message"));
            if (msg != null) {
                if (isSpecialMessage(msg)) {
                    handleSpecialMessage(msg);
                } else {
                    ACLMessage msg1 = msg.createReply();
                    File img = CaptureScreen.capture("png");
                    String base64 = imgToBase64(img);
                    msg1.setContent(base64);
                    send(msg1);
                    Common.debug("CaptureScreenAgent", getAID().getName() + " sent response: " + base64);
                    img.deleteOnExit();
                }
            } else {
                block();
            }
        }
    }
}
