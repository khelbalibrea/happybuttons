/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package happybuttons;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

import org.bytedeco.javacv.FFmpegFrameGrabber;

public class VidThumbnailGenerator {
    File path;
    int frameNumber = 100;
    
    public void createThumbnail(String filename) throws IOException, JCodecException {
        frameNumber = (int)((double)getFrameTotal(filename) * 0.1);
        System.out.println("num frames: " + frameNumber);
        
        try {
            path = new File(HappyButtons.documentsPath + "\\HappyButtons\\hlvids\\" + filename);
            Picture picture = FrameGrab.getFrameFromFile(new File(path.toString()), frameNumber);
            BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
            ImageIO.write(bufferedImage, "png", new File(HappyButtons.documentsPath + "\\HappyButtons\\dtbs\\thumbnails\\" + Utility.renameVideoName(filename) + ".png"));
        }
        catch(IOException | JCodecException e1) {
            
        }
        
        resizeImage(HappyButtons.documentsPath + "\\HappyButtons\\dtbs\\thumbnails\\" + Utility.renameVideoName(filename) + ".png");
    }
    
    public int getFrameTotal(String filename) {
        int frameCount = -1;
        String filePath = HappyButtons.documentsPath + "\\HappyButtons\\hlvids\\" + filename;
        
        try {
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(filePath);
            grabber.start();
            frameCount = grabber.getLengthInFrames();
            grabber.stop();
            System.out.println("Total frames: " + frameCount);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return frameCount;
    }
    
    public static BufferedImage resizeProcess(BufferedImage originalImage, int newWidth, int newHeight) {
        // Create a new BufferedImage with the specified width and height
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());

        // Create a Graphics2D object to draw the resized image
        Graphics2D g = resizedImage.createGraphics();

        // Draw the original image scaled to the new size
        g.drawImage(originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
        g.dispose(); // Dispose of the graphics context

        return resizedImage;
    }
    
    public static void resizeImage(String filename) {
        try {
            // Load the original image
            File inputFile = new File(filename); // Change this to your image file path
            BufferedImage originalImage = ImageIO.read(inputFile);
            
            // getting original size
            int origWidth = originalImage.getWidth();
            int origHeight = originalImage.getHeight();

            // Specify the new width and height
            int newWidth = 96; // Set your desired width
            int newHeight = 72; // Set your desired height
            
            // retain image orientation
            if(origHeight > origWidth) {
                int tempHeight = newHeight;
                newHeight = newWidth;
                newWidth = tempHeight;
            }

            // Resize the image
            BufferedImage resizedImage = resizeProcess(originalImage, newWidth, newHeight);

            // Save the resized image to a new file
            File outputFile = new File(filename); // Change this to your output file path
            ImageIO.write(resizedImage, "jpg", outputFile); // Specify the format (e.g., "jpg", "png")

            System.out.println("Image resized successfully!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
