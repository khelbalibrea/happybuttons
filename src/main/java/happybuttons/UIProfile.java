/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package happybuttons;

/**
 *
 * @author Michael Balibrea
 */
public class UIProfile {
    String prevTheme, vlcjPath;
    
    public String getPrevTheme(){
        return prevTheme;
    }
    
    public void setPrevTheme(String prevTheme){
        this.prevTheme = prevTheme;
    }
    
    public String getVlcjPath(){
        return vlcjPath;
    }
    
    public void setVlcjPath(String vlcjPath){
        this.vlcjPath = vlcjPath;
    }
    
    public UIProfile(){
        prevTheme = null;
        vlcjPath = null;
    }
}