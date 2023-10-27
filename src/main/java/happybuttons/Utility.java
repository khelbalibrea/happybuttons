/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package happybuttons;

import java.util.regex.Pattern;

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

        if (i > p) {
            extension = filename.substring(i+1);
        }
        
        return extension;
    }
    
    public static String renameListName(String filename){
        int index = filename.indexOf(".wav");
        
//        String music = filename.substring(0, index);
        
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
    
    public static String reduceLabelNaming(String name) {
        return "";
    }
    
    public static String[] splitMusicParts(String str) {
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
}
