/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Admin
 */
public class CaptureScreen {
    public static File capture(String fileType) {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        File file = null;
        try {
            BufferedImage capture = new Robot().createScreenCapture(screenRect);
            file = File.createTempFile("screenshot", "." + fileType);
            ImageIO.write(capture, fileType, file);
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
        
        return file;
    }
}
