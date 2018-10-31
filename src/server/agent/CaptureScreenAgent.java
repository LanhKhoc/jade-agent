/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.agent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.bind.DatatypeConverter;
import server.StoreServer;
import server.ui.container.CaptureScreenContainer;
import utils.Common;

/**
 *
 * @author Admin
 */
public class CaptureScreenAgent extends CoreAgent {
    @Override
    protected void setup() {
        addBehaviour(new RequestCaptureScreen());
//        new DiskInformation().setVisible(false);
//        DiskInformationContainer.initAgent(this);
        Common.debug("CaptureScreen", "capture-screen-server is running...");
    }
    
    private String createImageFromBase64(String base64) {
        byte[] decoded = DatatypeConverter.parseBase64Binary(base64);		
        String filePath = StoreServer.pathImages + "/screenshot" + System.currentTimeMillis() + ".png";
        try {
            FileOutputStream fileOuputStream = new FileOutputStream(filePath);
            fileOuputStream.write(decoded);
            fileOuputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return filePath;
    }

    private class RequestCaptureScreen extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            Common.debug("CaptureScreenAgent", "capture-screen-server received message: " + msg);
            if (msg != null) {
                if (msg.getPerformative() == ACLMessage.INFORM) {
                    sendExternalRequestByRequest("capturescreen-client", "");
                    Common.debug("CaptureScreenAgent", "capture-screen-server sent request");
                } else {
                    String base64 = msg.getContent();
                    String filepath = createImageFromBase64(base64);
                    CaptureScreenContainer.displayImageFromFile(filepath);
                }
            } else {
                block();
            }
        } 

    }
}
