/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package happybuttons;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Michael Balibrea (khel)
 */
public class Utility {
    public static String strDoubleSlash(String str) {
        String returnStr = str.replaceAll("\\\\", "\\\\\\\\");
        
        return returnStr;
    }
    
    public static String getFileExtension(String filename) {
        String extension = "";

        int i = filename.lastIndexOf('.');
        int p = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));

        if(i > p) {
            extension = filename.substring(i + 1);
        }
        
        return extension;
    }
    
    public static String renameListName(String filename, String type){ // type eg. wav, mp3
        int index = filename.indexOf("." + type);
        return filename.substring(0, index);
    }
    
    public static String renameVideoName(String filename){
        int index = filename.indexOf(".mp4");
        return filename.substring(0, index);
    }
    
    public static String cleanSFXNaming(String name) {
        String targetFront = name.copyValueOf("<html><center>".toCharArray());
        String cleanFront = name.replace(targetFront, "");
        String targetBack = cleanFront.copyValueOf("</center></html>".toCharArray());
        String cleanBack = cleanFront.replace(targetBack, "");
        
        return cleanBack;
    }
    
    public static String prepareLabelNaming(String name) {
        return "<html><center>" + name + "</center></html>";
    }
    
    public static String htmlText(String str, int type) { // 0-bold
        if(type == 0) {
            return "<html><strong>" + str + "</strong></html>";
        }
        else {
            return str;
        }
    }
    
    public static String reduceLabelNaming(String name) { // this function not finalized, not used
        return "";
    }
    
    public static String[] splitParts(String str) {
        String[] parts = str.split(Pattern.quote(":"));
        
        return parts;
    }
    
    public static int countChar(String str, char c) {
        int count = 0;
    
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == c) {
                count++;
            }
        }

        return count;
    }
    
    public static int findIndexInStrArr(String arr[], String search) {
        int index = -1;
        
        for(int ctr = 0; ctr < arr.length; ctr++) {
            if(arr[ctr].equals(search)) {
                index = ctr;
            }
        }
        
        return index;
    }
    
    public static String[] addElementInStrArr(String arr[], String addStr) { 
       String newarr[] = new String[arr.length + 1];
       int i;
       
       for(i = 0; i < arr.length; i++) {
           newarr[i] = arr[i]; 
       }
       
       newarr[arr.length] = addStr; 
   
       return newarr; 
   } 
    
    public static int[] addElementInIntArr(int arr[], int addThis) { 
       int newarr[] = new int[arr.length + 1];
       int i;
       
       for(i = 0; i < arr.length; i++) {
           newarr[i] = arr[i]; 
       }
       
       newarr[arr.length] = addThis; 
   
       return newarr; 
   }
    
    public static String[] removeIndexInStrArr(String arr[], int index) {
        String[] copy = new String[arr.length - 1];

        for(int i = 0, j = 0; i < arr.length; i++) {
            if(i != index) {
                copy[j++] = arr[i];
            }
        }
        
        return copy;
    }
    
    public static String[] removeElementInStrArr(String[] arr, String element) {
        String[] copy = new String[arr.length - 1];
        
        for(int i = 0, j = 0; i < arr.length; i++) {
            if(!arr[i].equals(element)) {
                copy[j++] = arr[i];
            }
//            testPrintStrArray(copy);
//            System.out.println("\n");
        }
        
        return copy;
    }
    
    public static boolean doesStrArrHasElement(String arr[], String search) {
        boolean itHas = false;
        
        for(String data : arr) {
            if (data.equals(search)) {
                itHas = true;
            }
        }
        
        return itHas;
    }
    
    public static boolean doesIntArrHasElement(int arr[], int search) {
        boolean itHas = false;
        
        for(int data : arr) {
            if (data == search) {
                itHas = true;
            }
        }
        
        return itHas;
    }
    
    public static int getIndexOfStrArrElement(String arr[], String element) {
        int index = -1;
        
        for(int i = 0; i < arr.length; i++) {
            if(arr[i].equals(element)) {
                index = i;
            }
        }
        
        return index;
    }
    
    public static String[] strToArr(String str) {
        String[] arr = str.split(Pattern.quote(":"));
        
        return arr;
    }
    
    public static String arrToStr(String[] arr, String delimiter) {
        String arrStr = "";
        for(String data : arr) {
            if (arrStr.equals("")) {
                arrStr = data;
            }
            else {
                arrStr = arrStr + delimiter + data;
            }
        }
        
        return arrStr;
    }
    
    public static String arrToInt(int[] arr) {
        String arrStr = "";
        for(int data : arr) {
            if (arrStr.equals("")) {
                arrStr = String.valueOf(data);
            }
            else {
                arrStr = arrStr + ":" + String.valueOf(data);
            }
        }
        
        return arrStr;
    }
    
    public static boolean searchInTableCol(DefaultTableModel table, String strSearch, int col) {
        boolean ret = false;
        
        for(int i = 0; i < table.getRowCount(); i++){
            ret = table.getValueAt(i, col).equals(strSearch);
        }
        
        return ret;
    }
    
    public static boolean searchInTable(DefaultTableModel table, String itemToCheck) {
        boolean found = false;
        
        int rowCount = table.getRowCount();
        int colCount = table.getColumnCount();
        
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                if (table.getValueAt(row, col).equals(itemToCheck)) {
                    found = true;
                    break;
                }
            }
        }
        
        return found;
    }
    
    public static boolean removeItemInTable(DefaultTableModel table, String strRemove) {
        boolean bool = false;
        
        for(int i = 0; i < table.getRowCount(); i++) {
            if(table.getValueAt(i, 0).equals(strRemove)) { // Check the first column
                table.removeRow(i);
                bool = true;
            }
        }
        
        return bool;
    }
    
    public static String shortenText(String str, int limit) {
        if(str.length() > limit) {
            return str.substring(0, limit) + "...";
        }
        else {
            return str;
        }
    }
    
    public static int searchSFX(String search) {
        int found = 0;
        
        if(search.equals(cleanSFXNaming(MainFrame.lblR1SFX01.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR1SFX02.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR1SFX03.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR1SFX04.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR1SFX05.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR1SFX06.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR1SFX07.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR1SFX08.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR1SFX09.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR1SFX10.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR1SFX11.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR1SFX12.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR1SFX13.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR1SFX14.getText()))) { found++; }
        
        if(search.equals(cleanSFXNaming(MainFrame.lblR2SFX01.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR2SFX02.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR2SFX03.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR2SFX04.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR2SFX05.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR2SFX06.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR2SFX07.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR2SFX08.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR2SFX09.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR2SFX10.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR2SFX11.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR2SFX12.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR2SFX13.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR2SFX14.getText()))) { found++; }
//        
        if(search.equals(cleanSFXNaming(MainFrame.lblR3SFX01.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR3SFX02.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR3SFX03.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR3SFX04.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR3SFX05.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR3SFX06.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR3SFX07.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR3SFX08.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR3SFX09.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR3SFX10.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR3SFX11.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR3SFX12.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR3SFX13.getText()))) { found++; }
        if(search.equals(cleanSFXNaming(MainFrame.lblR3SFX14.getText()))) { found++; }
        
        return found;
    }
    
    public static void blankSFXLabel(String str) {
        if(str.equals(cleanSFXNaming(MainFrame.lblR1SFX01.getText()))) { MainFrame.lblR1SFX01.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR1SFX02.getText()))) { MainFrame.lblR1SFX02.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR1SFX03.getText()))) { MainFrame.lblR1SFX03.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR1SFX04.getText()))) { MainFrame.lblR1SFX04.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR1SFX05.getText()))) { MainFrame.lblR1SFX05.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR1SFX06.getText()))) { MainFrame.lblR1SFX06.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR1SFX07.getText()))) { MainFrame.lblR1SFX07.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR1SFX08.getText()))) { MainFrame.lblR1SFX08.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR1SFX09.getText()))) { MainFrame.lblR1SFX09.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR1SFX10.getText()))) { MainFrame.lblR1SFX10.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR1SFX11.getText()))) { MainFrame.lblR1SFX11.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR1SFX12.getText()))) { MainFrame.lblR1SFX12.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR1SFX13.getText()))) { MainFrame.lblR1SFX13.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR1SFX14.getText()))) { MainFrame.lblR1SFX14.setText("blank"); }
        
        if(str.equals(cleanSFXNaming(MainFrame.lblR2SFX01.getText()))) { MainFrame.lblR2SFX01.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR2SFX02.getText()))) { MainFrame.lblR2SFX02.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR2SFX03.getText()))) { MainFrame.lblR2SFX03.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR2SFX04.getText()))) { MainFrame.lblR2SFX04.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR2SFX05.getText()))) { MainFrame.lblR2SFX05.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR2SFX06.getText()))) { MainFrame.lblR2SFX06.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR2SFX07.getText()))) { MainFrame.lblR2SFX07.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR2SFX08.getText()))) { MainFrame.lblR2SFX08.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR2SFX09.getText()))) { MainFrame.lblR2SFX09.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR2SFX10.getText()))) { MainFrame.lblR2SFX10.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR2SFX11.getText()))) { MainFrame.lblR2SFX11.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR2SFX12.getText()))) { MainFrame.lblR2SFX12.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR2SFX13.getText()))) { MainFrame.lblR2SFX13.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR2SFX14.getText()))) { MainFrame.lblR2SFX14.setText("blank"); }
        
        if(str.equals(cleanSFXNaming(MainFrame.lblR3SFX01.getText()))) { MainFrame.lblR3SFX01.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR3SFX02.getText()))) { MainFrame.lblR3SFX02.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR3SFX03.getText()))) { MainFrame.lblR3SFX03.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR3SFX04.getText()))) { MainFrame.lblR3SFX04.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR3SFX05.getText()))) { MainFrame.lblR3SFX05.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR3SFX06.getText()))) { MainFrame.lblR3SFX06.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR3SFX07.getText()))) { MainFrame.lblR3SFX07.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR3SFX08.getText()))) { MainFrame.lblR3SFX08.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR3SFX09.getText()))) { MainFrame.lblR3SFX09.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR3SFX10.getText()))) { MainFrame.lblR3SFX10.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR3SFX11.getText()))) { MainFrame.lblR3SFX11.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR3SFX12.getText()))) { MainFrame.lblR3SFX12.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR3SFX13.getText()))) { MainFrame.lblR3SFX13.setText("blank"); }
        if(str.equals(cleanSFXNaming(MainFrame.lblR3SFX14.getText()))) { MainFrame.lblR3SFX14.setText("blank"); }
    }
    
    public static String convertSecondsToHMS(int totalSeconds) {
        int hours = totalSeconds / 3600; // 1 hour = 3600 seconds
        int minutes = (totalSeconds % 3600) / 60; // 1 minute = 60 seconds
        int seconds = totalSeconds % 60; // Remaining seconds
        
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    
    public static void testPrintIntArray(int[] arr) {
        for(int ctr = 0; ctr < arr.length; ctr++) {
            System.out.println(arr[ctr]);
        }
    }
    
    public static void testPrintStrArray(String[] arr) {
        for(int ctr = 0; ctr < arr.length; ctr++) {
            System.out.println(arr[ctr]);
        }
    }
    
    public static Dimension getImageDimension(File imgFile) throws IOException {
        BufferedImage image = ImageIO.read(imgFile);
        int width = image.getWidth();
        int height = image.getHeight();
//        System.out.println("Width: " + width + ", Height: " + height);

        return new Dimension(width, height);
//        int pos = imgFile.getName().lastIndexOf(".");
//        
//        if(pos == -1) {
//            throw new IOException("No extension for file: " + imgFile.getAbsolutePath());
//        }
//          
//        String suffix = imgFile.getName().substring(pos + 1);
//        Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
//        
//        while(iter.hasNext()) {
//          ImageReader reader = iter.next();
//          
//          try {
//            ImageInputStream stream = new FileImageInputStream(imgFile);
//            reader.setInput(stream);
//            int width = reader.getWidth(reader.getMinIndex());
//            int height = reader.getHeight(reader.getMinIndex());
//            return new Dimension(width, height);
//          }
//          catch (IOException e) {
//            System.err.println("Error reading: " + imgFile.getAbsolutePath() + "\nErr: " + e);
//          } finally {
//            reader.dispose();
//          }
//        }
//        
//        throw new IOException("Not a known image file: " + imgFile.getAbsolutePath());
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
    
    public static void resizeImage(String filename) { // System.out.println("Resize: " + filename);
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

//            System.out.println("Image resized successfully!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
