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
    String prevTheme, vlcjPath, enableAutosave, startup, fullScreenVL, prevProfile;
    
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
    
    public String getEnableAutosave(){
        return enableAutosave;
    }
    
    public void setEnableAutosave(String enableAutosave){
        this.enableAutosave = enableAutosave;
    }
    
    public String getStartup(){
        return startup;
    }
    
    public void setStartup(String startup){
        this.startup = startup;
    }
    
    public String getFullScreenVL(){
        return fullScreenVL;
    }
    
    public void setFullScreenVL(String fullScreenVL){
        this.fullScreenVL = fullScreenVL;
    }
    
    public String getPrevProfile(){
        return prevProfile;
    }
    
    public void setPrevProfile(String prevProfile){
        this.prevProfile = prevProfile;
    }
    
    public UIProfile(){
        prevTheme = "light";
        vlcjPath = "";
        enableAutosave = "on";
        startup = "new";
        fullScreenVL = "window";
        prevProfile = "";
    }
}