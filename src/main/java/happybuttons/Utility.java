/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package happybuttons;

/**
 *
 * @author Michael Balibrea
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

        if (i > p) {
            extension = filename.substring(i+1);
        }
        
        return extension;
    }
    
    public static String renameListName(String filename){
        int index = filename.indexOf(".wav");
        
        String music = filename.substring(0, index);
        
        return music;
    }
    
    public static String cleanSFXNaming(String name) {
        String targetFront = name.copyValueOf("<html><center>".toCharArray());
        String cleanFront = name.replace(targetFront, "");
        String targetBack = cleanFront.copyValueOf("</center></html>".toCharArray());
        String cleanBack = cleanFront.replace(targetBack, "");
        
        return cleanBack;
    }
}
