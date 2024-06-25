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
    String prevTheme, vlcjPath, enableAutosave, startup, fullScreenVL, prevProfile, locationPopup, vlShuffle, videoMainVolume;
    
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
    
    public String getLocationPopup(){
        return locationPopup;
    }
    
    public void setLocationPopup(String locationPopup){
        this.locationPopup = locationPopup;
    }
    
    public String getVLShuffle(){
        return vlShuffle;
    }
    
    public void setVLShuffle(String vlShuffle){
        this.vlShuffle = vlShuffle;
    }
    
    public String getVideoMainVolume(){
        return videoMainVolume;
    }
    
    public void setVideoMainVolume(String videoMainVolume){
        this.videoMainVolume = videoMainVolume;
    }
    
    public UIProfile(){
        prevTheme = "light";
        vlcjPath = "";
        enableAutosave = "on";
        startup = "new";
        fullScreenVL = "window";
        prevProfile = "";
        locationPopup = "";
        vlShuffle = "0";
        videoMainVolume = "90";
    }
}