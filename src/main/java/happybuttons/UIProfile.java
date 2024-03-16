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
    String prevTheme, vlcjPath, enableAutosave, startup;
    
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
    
    public UIProfile(){
        prevTheme = null;
        vlcjPath = null;
        enableAutosave = null;
        startup = null;
    }
}