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
    public static String strDoubleQuote(String str) {
        String returnStr = str.replaceAll("\\\\", "\\\\\\\\");
        
        return returnStr;
    }
    
    public static String renameListName(String string){
        int index = string.indexOf(".mp3");
        
        String music = string.substring(0, index);
        
        return music;
    }
}
