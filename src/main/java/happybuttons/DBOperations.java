/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package happybuttons;

import java.io.File;
import java.util.Arrays;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Michael Balibrea
 */
public class DBOperations {
    public static int count = 0;
    public static int indexDB = -1;
    String textError = "";
    
    public boolean saveEnvironment(ProfileDatabase profileDB[], Profile profile){
        String profileNameSet = "";
        
        if(HappyButtons.noDB == 0) {
            profile.setProfileName(MainFrame.savingProfile);
            profileNameSet = (MainFrame.savingProfile);
        }
        else {
            profile.setProfileName("");
        }
        
        // =================================================================================================== Set BGM/SFX list, Vid loop, and Mp3 List
        if(HappyButtons.noDB == 0) {
            profile.setStrBGM(MainFrame.strBGM);
            profile.setStrSFX(MainFrame.strSFX);
            profile.setStrVidLoop(MainFrame.strVidLoop);
            profile.setStrVidList(MainFrame.strVidList);
            profile.setStrMp3List(MainFrame.strMp3List);
        }
        else {
            profile.setStrBGM("");
            profile.setStrSFX("");
            profile.setStrVidLoop("");
            profile.setStrVidList("");
            profile.setStrMp3List("");
        }
        
        // =================================================================================================== Set SFXs
        if(HappyButtons.noDB == 0) {
            profile.setR1Sfx01(Utility.cleanSFXNaming(MainFrame.lblR1SFX01.getText()));
            profile.setR1Sfx02(Utility.cleanSFXNaming(MainFrame.lblR1SFX02.getText()));
            profile.setR1Sfx03(Utility.cleanSFXNaming(MainFrame.lblR1SFX03.getText()));
            profile.setR1Sfx04(Utility.cleanSFXNaming(MainFrame.lblR1SFX04.getText()));
            profile.setR1Sfx05(Utility.cleanSFXNaming(MainFrame.lblR1SFX05.getText()));
            profile.setR1Sfx06(Utility.cleanSFXNaming(MainFrame.lblR1SFX06.getText()));
            profile.setR1Sfx07(Utility.cleanSFXNaming(MainFrame.lblR1SFX07.getText()));
            profile.setR1Sfx08(Utility.cleanSFXNaming(MainFrame.lblR1SFX08.getText()));
            profile.setR1Sfx09(Utility.cleanSFXNaming(MainFrame.lblR1SFX09.getText()));
            profile.setR1Sfx10(Utility.cleanSFXNaming(MainFrame.lblR1SFX10.getText()));
            profile.setR1Sfx11(Utility.cleanSFXNaming(MainFrame.lblR1SFX11.getText()));
            profile.setR1Sfx12(Utility.cleanSFXNaming(MainFrame.lblR1SFX12.getText()));
            profile.setR1Sfx13(Utility.cleanSFXNaming(MainFrame.lblR1SFX13.getText()));
            profile.setR1Sfx14(Utility.cleanSFXNaming(MainFrame.lblR1SFX14.getText()));
            
            profile.setR2Sfx01(Utility.cleanSFXNaming(MainFrame.lblR2SFX01.getText()));
            profile.setR2Sfx02(Utility.cleanSFXNaming(MainFrame.lblR2SFX02.getText()));
            profile.setR2Sfx03(Utility.cleanSFXNaming(MainFrame.lblR2SFX03.getText()));
            profile.setR2Sfx04(Utility.cleanSFXNaming(MainFrame.lblR2SFX04.getText()));
            profile.setR2Sfx05(Utility.cleanSFXNaming(MainFrame.lblR2SFX05.getText()));
            profile.setR2Sfx06(Utility.cleanSFXNaming(MainFrame.lblR2SFX06.getText()));
            profile.setR2Sfx07(Utility.cleanSFXNaming(MainFrame.lblR2SFX07.getText()));
            profile.setR2Sfx08(Utility.cleanSFXNaming(MainFrame.lblR2SFX08.getText()));
            profile.setR2Sfx09(Utility.cleanSFXNaming(MainFrame.lblR2SFX09.getText()));
            profile.setR2Sfx10(Utility.cleanSFXNaming(MainFrame.lblR2SFX10.getText()));
            profile.setR2Sfx11(Utility.cleanSFXNaming(MainFrame.lblR2SFX11.getText()));
            profile.setR2Sfx12(Utility.cleanSFXNaming(MainFrame.lblR2SFX12.getText()));
            profile.setR2Sfx13(Utility.cleanSFXNaming(MainFrame.lblR2SFX13.getText()));
            profile.setR2Sfx14(Utility.cleanSFXNaming(MainFrame.lblR2SFX14.getText()));
//            
            profile.setR3Sfx01(Utility.cleanSFXNaming(MainFrame.lblR3SFX01.getText()));
            profile.setR3Sfx02(Utility.cleanSFXNaming(MainFrame.lblR3SFX02.getText()));
            profile.setR3Sfx03(Utility.cleanSFXNaming(MainFrame.lblR3SFX03.getText()));
            profile.setR3Sfx04(Utility.cleanSFXNaming(MainFrame.lblR3SFX04.getText()));
            profile.setR3Sfx05(Utility.cleanSFXNaming(MainFrame.lblR3SFX05.getText()));
            profile.setR3Sfx06(Utility.cleanSFXNaming(MainFrame.lblR3SFX06.getText()));
            profile.setR3Sfx07(Utility.cleanSFXNaming(MainFrame.lblR3SFX07.getText()));
            profile.setR3Sfx08(Utility.cleanSFXNaming(MainFrame.lblR3SFX08.getText()));
            profile.setR3Sfx09(Utility.cleanSFXNaming(MainFrame.lblR3SFX09.getText()));
            profile.setR3Sfx10(Utility.cleanSFXNaming(MainFrame.lblR3SFX10.getText()));
            profile.setR3Sfx11(Utility.cleanSFXNaming(MainFrame.lblR3SFX11.getText()));
            profile.setR3Sfx12(Utility.cleanSFXNaming(MainFrame.lblR3SFX12.getText()));
            profile.setR3Sfx13(Utility.cleanSFXNaming(MainFrame.lblR3SFX13.getText()));
            profile.setR3Sfx14(Utility.cleanSFXNaming(MainFrame.lblR3SFX14.getText()));
        }
        else {
            profile.setR1Sfx01("blank");
            profile.setR1Sfx02("blank");
            profile.setR1Sfx03("blank");
            profile.setR1Sfx04("blank");
            profile.setR1Sfx05("blank");
            profile.setR1Sfx06("blank");
            profile.setR1Sfx07("blank");
            profile.setR1Sfx08("blank");
            profile.setR1Sfx09("blank");
            profile.setR1Sfx10("blank");
            profile.setR1Sfx11("blank");
            profile.setR1Sfx12("blank");
            profile.setR1Sfx13("blank");
            profile.setR1Sfx14("blank");
            
            profile.setR2Sfx01("blank");
            profile.setR2Sfx02("blank");
            profile.setR2Sfx03("blank");
            profile.setR2Sfx04("blank");
            profile.setR2Sfx05("blank");
            profile.setR2Sfx06("blank");
            profile.setR2Sfx07("blank");
            profile.setR2Sfx08("blank");
            profile.setR2Sfx09("blank");
            profile.setR2Sfx10("blank");
            profile.setR2Sfx11("blank");
            profile.setR2Sfx12("blank");
            profile.setR2Sfx13("blank");
            profile.setR2Sfx14("blank");
            
            profile.setR3Sfx01("blank");
            profile.setR3Sfx02("blank");
            profile.setR3Sfx03("blank");
            profile.setR3Sfx04("blank");
            profile.setR3Sfx05("blank");
            profile.setR3Sfx06("blank");
            profile.setR3Sfx07("blank");
            profile.setR3Sfx08("blank");
            profile.setR3Sfx09("blank");
            profile.setR3Sfx10("blank");
            profile.setR3Sfx11("blank");
            profile.setR3Sfx12("blank");
            profile.setR3Sfx13("blank");
            profile.setR3Sfx14("blank");
        }
        // =================================================================================================== Get list BGM, SFX, VL, and MP3 list
        profileDB[indexDB].setStrBGM(profile.getStrBGM());
        profileDB[indexDB].setStrSFX(profile.getStrSFX());
        profileDB[indexDB].setStrVidLoop(profile.getStrVidLoop());
        profileDB[indexDB].setStrVidList(profile.getStrVidList());
        profileDB[indexDB].setStrMp3List(profile.getStrMp3List());
        
        // =================================================================================================== Get profile name
        profileDB[indexDB].setProfileName(profile.getProfileName());
        
        // =================================================================================================== Get SFXs
        profileDB[indexDB].setR1Sfx01(profile.getR1Sfx01());
        profileDB[indexDB].setR1Sfx02(profile.getR1Sfx02());
        profileDB[indexDB].setR1Sfx03(profile.getR1Sfx03());
        profileDB[indexDB].setR1Sfx04(profile.getR1Sfx04());
        profileDB[indexDB].setR1Sfx05(profile.getR1Sfx05());
        profileDB[indexDB].setR1Sfx06(profile.getR1Sfx06());
        profileDB[indexDB].setR1Sfx07(profile.getR1Sfx07());
        profileDB[indexDB].setR1Sfx08(profile.getR1Sfx08());
        profileDB[indexDB].setR1Sfx09(profile.getR1Sfx09());
        profileDB[indexDB].setR1Sfx10(profile.getR1Sfx10());
        profileDB[indexDB].setR1Sfx11(profile.getR1Sfx11());
        profileDB[indexDB].setR1Sfx12(profile.getR1Sfx12());
        profileDB[indexDB].setR1Sfx13(profile.getR1Sfx13());
        profileDB[indexDB].setR1Sfx14(profile.getR1Sfx14());
        
        profileDB[indexDB].setR2Sfx01(profile.getR2Sfx01());
        profileDB[indexDB].setR2Sfx02(profile.getR2Sfx02());
        profileDB[indexDB].setR2Sfx03(profile.getR2Sfx03());
        profileDB[indexDB].setR2Sfx04(profile.getR2Sfx04());
        profileDB[indexDB].setR2Sfx05(profile.getR2Sfx05());
        profileDB[indexDB].setR2Sfx06(profile.getR2Sfx06());
        profileDB[indexDB].setR2Sfx07(profile.getR2Sfx07());
        profileDB[indexDB].setR2Sfx08(profile.getR2Sfx08());
        profileDB[indexDB].setR2Sfx09(profile.getR2Sfx09());
        profileDB[indexDB].setR2Sfx10(profile.getR2Sfx10());
        profileDB[indexDB].setR2Sfx11(profile.getR2Sfx11());
        profileDB[indexDB].setR2Sfx12(profile.getR2Sfx12());
        profileDB[indexDB].setR2Sfx13(profile.getR2Sfx13());
        profileDB[indexDB].setR2Sfx14(profile.getR2Sfx14());
        
        profileDB[indexDB].setR3Sfx01(profile.getR3Sfx01());
        profileDB[indexDB].setR3Sfx02(profile.getR3Sfx02());
        profileDB[indexDB].setR3Sfx03(profile.getR3Sfx03());
        profileDB[indexDB].setR3Sfx04(profile.getR3Sfx04());
        profileDB[indexDB].setR3Sfx05(profile.getR3Sfx05());
        profileDB[indexDB].setR3Sfx06(profile.getR3Sfx06());
        profileDB[indexDB].setR3Sfx07(profile.getR3Sfx07());
        profileDB[indexDB].setR3Sfx08(profile.getR3Sfx08());
        profileDB[indexDB].setR3Sfx09(profile.getR3Sfx09());
        profileDB[indexDB].setR3Sfx10(profile.getR3Sfx10());
        profileDB[indexDB].setR3Sfx11(profile.getR3Sfx11());
        profileDB[indexDB].setR3Sfx12(profile.getR3Sfx12());
        profileDB[indexDB].setR3Sfx13(profile.getR3Sfx13());
        profileDB[indexDB].setR3Sfx14(profile.getR3Sfx14());
        
        boolean saved = new BeanHelper().writeToXml(profileDB);
        if(saved) {
            MainFrame.savedProfile = profileNameSet;
            HappyButtons.canAutosave = 1;
        }
        else {
            MainFrame.savedProfile = "error";
        }
        
        count = 0;
        HappyButtons.loadedDB = indexDB;
        indexDB = -1;
        
        return saved;
    }
    
    public boolean autoSaveUISettings(UIPreference uiPref[], UIProfile uiProfile){
        uiProfile.setPrevTheme(HappyButtons.uiTheme);
        uiPref[0].setPrevTheme(uiProfile.getPrevTheme());
        
        uiProfile.setVlcjPath(HappyButtons.vlcjPath);
        uiPref[0].setVlcjPath(uiProfile.getVlcjPath());
        
        uiProfile.setEnableAutosave(MainFrame.enableAutosave);
        uiPref[0].setEnableAutosave(uiProfile.getEnableAutosave());
        
        uiProfile.setStartup(MainFrame.startup);
        uiPref[0].setStartup(uiProfile.getStartup());
        
        uiProfile.setFullScreenVL(MainFrame.fullScreenVL);
        uiPref[0].setFullScreenVL(uiProfile.getFullScreenVL());
        
        uiProfile.setPrevProfile(String.valueOf(MainFrame.loadedIndexProfile));
        uiPref[0].setPrevProfile(uiProfile.getPrevProfile());
        
        uiProfile.setLocationPopup(MainFrame.locPopup);
        uiPref[0].setLocationPopup(uiProfile.getLocationPopup());
        
        uiProfile.setVLShuffle(String.valueOf(MainFrame.chkVLShuffle));
        uiPref[0].setVLShuffle(uiProfile.getVLShuffle());
        
        uiProfile.setVideoMainVolume(String.valueOf(MainFrame.videoMainVolume));
        uiPref[0].setVideoMainVolume(uiProfile.getVideoMainVolume());
        
        new BeanHelper().writeToXmlUI(uiPref);
        
        return true;
    }
    
    public void loadSystemSettings(UIPreference uiPref[], int index) {
        ViewProfiles uiDetails = new ViewProfiles();
        
        uiDetails.setUISettings(uiPref, index);
        HappyButtons.uiTheme = uiPref[index].getPrevTheme();
        HappyButtons.vlcjPath = uiPref[index].getVlcjPath();
        MainFrame.enableAutosave = uiPref[index].getEnableAutosave();
        MainFrame.startup = uiPref[index].getStartup();
        MainFrame.fullScreenVL = uiPref[index].getFullScreenVL();
        MainFrame.chkVLShuffle = Integer.parseInt(uiPref[index].getVLShuffle());
        MainFrame.videoMainVolume = Integer.parseInt(uiPref[index].getVideoMainVolume());
        
        String prevProfile = uiPref[index].getPrevProfile();
        int equal = -1;
        if(prevProfile.equals("")) {
            if(MainFrame.startup.equals("new")) {
                equal = -1;
            }
        }
        else {
            if(MainFrame.startup.equals("new")) {
                equal = -1;
            }
            else {
                equal = Integer.parseInt(uiPref[index].getPrevProfile());
            }
        }
        MainFrame.loadedIndexProfile = equal;
        
        MainFrame.locPopup = uiPref[index].getLocationPopup();
        if(MainFrame.locPopup.equals("topcenter")) {
            MainFrame.location = Notification.Location.TOP_CENTER;
        }
        else if(MainFrame.locPopup.equals("topleft")) {
            MainFrame.location = Notification.Location.TOP_LEFT;
        }
        else if(MainFrame.locPopup.equals("topright")) {
            MainFrame.location = Notification.Location.TOP_RIGHT;
        }
        else if(MainFrame.locPopup.equals("bottomcenter")) {
            MainFrame.location = Notification.Location.BOTTOM_CENTER;
        }
        else if(MainFrame.locPopup.equals("bottomleft")) {
            MainFrame.location = Notification.Location.BOTTOM_LEFT;
        }
        else if(MainFrame.locPopup.equals("bottomright")) {
            MainFrame.location = Notification.Location.BOTTOM_RIGHT;
        }
        else {
            MainFrame.location = Notification.Location.TOP_CENTER;
            MainFrame.locPopup = "topcenter";
        }
    }
    
    public void loadPreviousProfile(UIPreference uiPref[], int index) {
        ViewProfiles uiDetails = new ViewProfiles();
    }
    
    public String loadEnvironment(ProfileDatabase profileDB[], int index){
        ViewProfiles profileDetails = new ViewProfiles();
        
        if(profileDB[index].getProfileName() != "") {
            profileDetails.setProfileDetails(profileDB, index);
            
            (MainFrame.lblR1SFX01).setText(Utility.prepareLabelNaming(profileDB[index].getR1Sfx01()));
            (MainFrame.lblR1SFX02).setText(Utility.prepareLabelNaming(profileDB[index].getR1Sfx02()));
            (MainFrame.lblR1SFX03).setText(Utility.prepareLabelNaming(profileDB[index].getR1Sfx03()));
            (MainFrame.lblR1SFX04).setText(Utility.prepareLabelNaming(profileDB[index].getR1Sfx04()));
            (MainFrame.lblR1SFX05).setText(Utility.prepareLabelNaming(profileDB[index].getR1Sfx05()));
            (MainFrame.lblR1SFX06).setText(Utility.prepareLabelNaming(profileDB[index].getR1Sfx06()));
            (MainFrame.lblR1SFX07).setText(Utility.prepareLabelNaming(profileDB[index].getR1Sfx07()));
            (MainFrame.lblR1SFX08).setText(Utility.prepareLabelNaming(profileDB[index].getR1Sfx08()));
            (MainFrame.lblR1SFX09).setText(Utility.prepareLabelNaming(profileDB[index].getR1Sfx09()));
            (MainFrame.lblR1SFX10).setText(Utility.prepareLabelNaming(profileDB[index].getR1Sfx10()));
            (MainFrame.lblR1SFX11).setText(Utility.prepareLabelNaming(profileDB[index].getR1Sfx11()));
            (MainFrame.lblR1SFX12).setText(Utility.prepareLabelNaming(profileDB[index].getR1Sfx12()));
            (MainFrame.lblR1SFX13).setText(Utility.prepareLabelNaming(profileDB[index].getR1Sfx13()));
            (MainFrame.lblR1SFX14).setText(Utility.prepareLabelNaming(profileDB[index].getR1Sfx14()));
            
            (MainFrame.lblR2SFX01).setText(Utility.prepareLabelNaming(profileDB[index].getR2Sfx01()));
            (MainFrame.lblR2SFX02).setText(Utility.prepareLabelNaming(profileDB[index].getR2Sfx02()));
            (MainFrame.lblR2SFX03).setText(Utility.prepareLabelNaming(profileDB[index].getR2Sfx03()));
            (MainFrame.lblR2SFX04).setText(Utility.prepareLabelNaming(profileDB[index].getR2Sfx04()));
            (MainFrame.lblR2SFX05).setText(Utility.prepareLabelNaming(profileDB[index].getR2Sfx05()));
            (MainFrame.lblR2SFX06).setText(Utility.prepareLabelNaming(profileDB[index].getR2Sfx06()));
            (MainFrame.lblR2SFX07).setText(Utility.prepareLabelNaming(profileDB[index].getR2Sfx07()));
            (MainFrame.lblR2SFX08).setText(Utility.prepareLabelNaming(profileDB[index].getR2Sfx08()));
            (MainFrame.lblR2SFX09).setText(Utility.prepareLabelNaming(profileDB[index].getR2Sfx09()));
            (MainFrame.lblR2SFX10).setText(Utility.prepareLabelNaming(profileDB[index].getR2Sfx10()));
            (MainFrame.lblR2SFX11).setText(Utility.prepareLabelNaming(profileDB[index].getR2Sfx11()));
            (MainFrame.lblR2SFX12).setText(Utility.prepareLabelNaming(profileDB[index].getR2Sfx12()));
            (MainFrame.lblR2SFX13).setText(Utility.prepareLabelNaming(profileDB[index].getR2Sfx13()));
            (MainFrame.lblR2SFX14).setText(Utility.prepareLabelNaming(profileDB[index].getR2Sfx14()));
//            
            (MainFrame.lblR3SFX01).setText(Utility.prepareLabelNaming(profileDB[index].getR3Sfx01()));
            (MainFrame.lblR3SFX02).setText(Utility.prepareLabelNaming(profileDB[index].getR3Sfx02()));
            (MainFrame.lblR3SFX03).setText(Utility.prepareLabelNaming(profileDB[index].getR3Sfx03()));
            (MainFrame.lblR3SFX04).setText(Utility.prepareLabelNaming(profileDB[index].getR3Sfx04()));
            (MainFrame.lblR3SFX05).setText(Utility.prepareLabelNaming(profileDB[index].getR3Sfx05()));
            (MainFrame.lblR3SFX06).setText(Utility.prepareLabelNaming(profileDB[index].getR3Sfx06()));
            (MainFrame.lblR3SFX07).setText(Utility.prepareLabelNaming(profileDB[index].getR3Sfx07()));
            (MainFrame.lblR3SFX08).setText(Utility.prepareLabelNaming(profileDB[index].getR3Sfx08()));
            (MainFrame.lblR3SFX09).setText(Utility.prepareLabelNaming(profileDB[index].getR3Sfx09()));
            (MainFrame.lblR3SFX10).setText(Utility.prepareLabelNaming(profileDB[index].getR3Sfx10()));
            (MainFrame.lblR3SFX11).setText(Utility.prepareLabelNaming(profileDB[index].getR3Sfx11()));
            (MainFrame.lblR3SFX12).setText(Utility.prepareLabelNaming(profileDB[index].getR3Sfx12()));
            (MainFrame.lblR3SFX13).setText(Utility.prepareLabelNaming(profileDB[index].getR3Sfx13()));
            (MainFrame.lblR3SFX14).setText(Utility.prepareLabelNaming(profileDB[index].getR3Sfx14()));
            
            MainFrame.strBGM = profileDB[index].getStrBGM();
            MainFrame.strSFX = profileDB[index].getStrSFX();
            MainFrame.strVidLoop = profileDB[index].getStrVidLoop();
            MainFrame.strVidList = profileDB[index].getStrVidList();
            MainFrame.strMp3List = profileDB[index].getStrMp3List();
            
            loadJElements(profileDB, index);
            
            // loading mp3 queue
            
            MainFrame.tfLastOperation.setText("PROFILE \"" + profileDB[index].getProfileName() + "\" LOADED");
            
            if(MainFrame.dbLoadedManual == 1) { // profile is manually loaded
                Notification panel = new Notification(HappyButtons.mf, 
                    Notification.Type.SUCCESS, 
                    MainFrame.location, 
                    "Success",
                    "\"" + profileDB[index].getProfileName() + "\"   profile loaded"
                );
                panel.showNotification();
            }
            else { // previous profile is loaded
                Notification panel = new Notification(HappyButtons.mf, 
                    Notification.Type.INFO, 
                    MainFrame.location, 
                    "Previous profile loaded",
                    "\"" + profileDB[index].getProfileName() + "\"   profile loaded"
                );
                panel.showNotification();
            }
            
            MainFrame.dbLoadedManual = 0;
            
            String profileName = profileDB[index].getProfileName();
            HappyButtons.loadedDB = index;
            MainFrame.loadedIndexProfile = index;
            
            UIProfile ui = new UIProfile();
            HappyButtons.dbo.autoSaveUISettings(HappyButtons.uiDB, ui);
            
            HappyButtons.canAutosave = 1;
            MainFrame.savingProfile = profileName;
            
            return profileName;
        }
        else {
            Notification panel = new Notification(HappyButtons.mf, 
                Notification.Type.ERROR, 
                MainFrame.location, 
                "Load failed",
                "\"" + profileDB[index].getProfileName() + "\"   failed to load"
            );
            panel.showNotification();
                
            return "error";
        }
    }
    
    public void sortJComboBox(String[] arrLoop, String[] arrPlaylist) {
        String[] dataLoop = new String[arrLoop.length];
        String[] dataList = new String[arrPlaylist.length];
        
        for(int i = 0; i < arrLoop.length; i++) {
            dataLoop[i] = arrLoop[i];
        }
        for(int i = 0; i < arrPlaylist.length; i++) {
            dataList[i] = arrPlaylist[i];
        }
        
        Arrays.sort(dataLoop);
        Arrays.sort(dataList);
        
        (MainFrame.cboVidLoop).removeAllItems();
        (MainFrame.cboModelForLoop).removeAllElements();
        (MainFrame.cboModelPlaylist).removeAllElements();
        
        for(String vid : dataLoop) {
            (MainFrame.cboModelForLoop).addElement(vid);
        }
        for(String vid : dataList) {
            (MainFrame.cboModelPlaylist).addElement(vid);
        }
        
        (MainFrame.cboVidLoop).setModel(MainFrame.cboModelForLoop);
    }
    
    public void loadJElements(ProfileDatabase profileDB[], int index) {
        // ------------------------------------------------------------------------------------------------------ Loading BGM list
        String[] arrBGM = Utility.splitParts(profileDB[index].getStrBGM());
        String goneBGMs = "";
        int bgmLost = 0;
        int numbering = 1;
        
        for(String music : arrBGM) {
            if(!music.equals("")) {
                File destCheck = new File(HappyButtons.documentsPath + "\\HappyButtons\\bg\\" + music + ".wav");
                if(!destCheck.exists()) {
                    int removedIndex = Utility.findIndexInStrArr(arrBGM, music);
                    arrBGM = Utility.removeIndexInStrArr(arrBGM, removedIndex);
                    bgmLost++;
                    if(!goneBGMs.equals("")) {
                        goneBGMs = goneBGMs + "(" + numbering + ") " + music + ".wav\n";
                        numbering++;
                    }
                    else {
                        goneBGMs = "(" + numbering + ") " + music + ".wav\n";
                        numbering++;
                    }
                }
            }
        }
        
        (MainFrame.blist).removeAllElements();
        for(String music : arrBGM) {
            (MainFrame.blist).addElement(music);
        }
        
        // Sort JList
        sortJList(MainFrame.blist, 0); // 0 - bgm, 1 - sfx
        
        // ------------------------------------------------------------------------------------------------------ Loading SFX list
        String[] arrSFX = Utility.splitParts(profileDB[index].getStrSFX());
        String goneSFXs = "";
        String sfxGone = "";
        int sfxLost = 0;
        numbering = 1;
        
        for(String music : arrSFX) {
            if(!music.equals("")) {
                File destCheck = new File(HappyButtons.documentsPath + "\\HappyButtons\\sfx\\" + music + ".wav");
                if(!destCheck.exists()) {
                    int removedIndex = Utility.findIndexInStrArr(arrSFX, music);
                    arrSFX = Utility.removeIndexInStrArr(arrSFX, removedIndex);
                    sfxLost++;
                    if(goneSFXs.equals("")) {
                        goneSFXs = "(" + numbering + ") " + music + ".wav\n";
                        numbering++;
                        sfxGone = music;
                    }
                    else {
                        goneSFXs = goneSFXs + "(" + numbering + ") " + music + ".wav\n";
                        numbering++;
                        sfxGone = sfxGone + ":" + music;
                    }
                }
            }
        }
        
        (MainFrame.slist).removeAllElements();
        for(String music : arrSFX) {
            (MainFrame.slist).addElement(music);
        }
        
        // Sort JList
        sortJList(MainFrame.slist, 1); // 0 - bgm, 1 - sfx
        
        // ------------------------------------------------------------------------------------------------------ Loading Combo box VL list
        String[] arrVidForLoop = Utility.splitParts(profileDB[index].getStrVidLoop());
        String[] arrVidPlaylist = Utility.splitParts(profileDB[index].getStrVidList());
        String goneVids = "";
        int vidLost = 0;
        numbering = 1;
        
        for(String vid : arrVidForLoop) {
            if(!vid.equals("")) {
                File destCheck = new File(HappyButtons.documentsPath + "\\HappyButtons\\hlvids\\" + vid + ".mp4");
                if(!destCheck.exists()) {
                    int removedIndex = Utility.findIndexInStrArr(arrVidForLoop, vid);
                    arrVidForLoop = Utility.removeIndexInStrArr(arrVidForLoop, removedIndex);
                    vidLost++;
                    if(!goneVids.equals("")) {
                        goneVids = goneVids + "(" + numbering + ") " + vid + ".mp4\n";
                        numbering++;
                    }
                    else {
                        goneVids = "(" + numbering + ") " + vid + ".mp4\n";
                        numbering++;
                    }
                }
            }
        }
        
        for(String vid : arrVidPlaylist) {
            if(!vid.equals("")) {
                File destCheck = new File(HappyButtons.documentsPath + "\\HappyButtons\\hlvids\\" + vid + ".mp4");
                if(!destCheck.exists()) {
                    int removedIndex = Utility.findIndexInStrArr(arrVidPlaylist, vid);
                    arrVidPlaylist = Utility.removeIndexInStrArr(arrVidPlaylist, removedIndex);
                    vidLost++;
                    if(!goneVids.equals("")) {
                        goneVids = goneVids + "(" + numbering + ") " + vid + ".mp4\n";
                        numbering++;
                    }
                    else {
                        goneVids = "(" + numbering + ") " + vid + ".mp4\n";
                        numbering++;
                    }
                }
            }
        }
        
        sortJComboBox(arrVidForLoop, arrVidPlaylist);
        
        // --------------------------------------------------------------------------------------------- check for missing files
        boolean hasError = false;
        if(bgmLost > 0 || sfxLost > 0 || vidLost > 0) {
            hasError = true;
            
            if(bgmLost > 0) {
                textError = bgmLost + " item(s) not found in BGM resource list: \n" + goneBGMs + "\n";
            }
            
            if(sfxLost > 0) {
                if(bgmLost > 0) {
                    textError = textError + sfxLost + " item(s) not found in SFX resource list: \n" + goneSFXs + "\n";
                    goneBGMs = "";
                }
                else {
                    textError = textError + " item(s) not found in SFX resource list: \n" + goneSFXs + "\n";
                    goneBGMs = "";
                }
            }
            
            if(vidLost > 0) {
                textError = textError + vidLost + " item(s) not found in Video Loop resource list: \n" + goneVids + "\n";
                goneVids = "";
            }
        }
        
        if(hasError) {
            int total = bgmLost + sfxLost + vidLost;
            String header = "<html><span style='color:red;'>" + total + " ITEM(s) MISSING:</span></html>" + "\n\n";
            textError = header + textError + "\n\n<html><strong>NOTE:</strong></html>\n• Missing bgm items are removed from BGM list\n• Missing sfx items are removed from SFX list and SFX buttons"
                    + "\n• Missing video items are removed in Happy Loop Combo box\n\n\n";
            
            JOptionPane.showMessageDialog(HappyButtons.mf,
                    textError,
                    "File(s) missing", 
                    JOptionPane.ERROR_MESSAGE);
            
            // clear sfx buttons that are not found
            if(sfxLost > 0) {
                String[] sfx = Utility.splitParts(sfxGone);

                for(int i = 0; i < sfx.length; i++) {
                    Utility.blankSFXLabel(sfx[i]);
                }
            }
            numbering = 1;
        }
        bgmLost = 0;
        sfxLost = 0;
        vidLost = 0;
        textError = "";
    }
    
    public void sortJList(DefaultListModel list, int listType) {
        int n = list.getSize();
        String[] data = new String[n];
        
        for(int i = 0; i < n; i++) {
            data[i] = (String) list.getElementAt(i);
        }
        
        Arrays.sort(data);
        
        if(listType == 0) {
            (MainFrame.blist).removeAllElements();
            
            for(String music : data) {
                (MainFrame.blist).addElement(music);
            }
            (MainFrame.listBGM).setModel(MainFrame.blist);
        }
        else {
            (MainFrame.slist).removeAllElements();
            
            for(String music : data) {
                (MainFrame.slist).addElement(music);
            }
            (MainFrame.listSFX).setModel(MainFrame.slist);
        }
    }
    
    public static String checkBgmInProfiles(ProfileDatabase[] profileDB, String search) {
        String strBGMList = "";
        
        for(int ctr = 0; ctr < 5; ctr++) {
            String str = profileDB[ctr].getStrBGM(); // get the strBGM from DB
            String[] arr = Utility.strToArr(str); // convert string to array
            
            int found = Utility.findIndexInStrArr(arr, search); // search the search item in array
            if(found >= 0) {
                if(strBGMList.equals("")) {
                    strBGMList = profileDB[ctr].getProfileName();
                }
                else {
                    strBGMList = strBGMList + ", " + profileDB[ctr].getProfileName();
                }
            }
        }
        
        return strBGMList;
    }
    
    public static String checkSfxInProfiles(ProfileDatabase[] profileDB, String search) {
        String strSFXList = "";
        
        for(int ctr = 0; ctr < 5; ctr++) {
            String str = profileDB[ctr].getStrSFX(); // get the strSFX from DB
            String[] arr = Utility.strToArr(str); // convert string to array
            
            int found = Utility.findIndexInStrArr(arr, search); // search the search item in array
            if(found >= 0) {
                if(strSFXList.equals("")) {
                    strSFXList = profileDB[ctr].getProfileName();
                }
                else {
                    strSFXList = strSFXList + ", " + profileDB[ctr].getProfileName();
                }
            }
        }
        
        return strSFXList;
    }
    
    public static String checkVideoLoopInProfiles(ProfileDatabase[] profileDB, String search) {
        String strVideoLoopList = "";
        String[] arr = new String[0];
        String[] arrList = new String[0];
        String[] has = new String[0];
        
        for(int ctr = 0; ctr < 5; ctr++) {
            String str = profileDB[ctr].getStrVidLoop(); // get the strVidLoop from DB
            arr = Utility.strToArr(str); // convert string to array
            
            int found = Utility.findIndexInStrArr(arr, search); // search the search item in array
            if(found >= 0) {
                if(Utility.findIndexInStrArr(has, profileDB[ctr].getProfileName()) < 0) {
                    has = Utility.addElementInStrArr(has, profileDB[ctr].getProfileName());
                }
            }
        }
        
        for(int ctr = 0; ctr < 5; ctr++) {
            String strList = profileDB[ctr].getStrVidList(); // get the strVidList from DB
            arrList = Utility.strToArr(strList); // convert string to array
            
            int found = Utility.findIndexInStrArr(arrList, search); // search the search item in array
            if(found >= 0) {
                if(Utility.findIndexInStrArr(has, profileDB[ctr].getProfileName()) < 0) {
                    has = Utility.addElementInStrArr(has, profileDB[ctr].getProfileName());
                }
            }
        }
        
        strVideoLoopList = Utility.arrToStr(has, ", ");
        
        return strVideoLoopList;
    }
    
    public static String checkMp3InProfiles(ProfileDatabase[] profileDB, String search) {
        String strMp3List = "";
        
        for(int ctr = 0; ctr < 5; ctr++) {
            String str = profileDB[ctr].getStrMp3List(); // get the strMp3List from DB
            String[] arr = Utility.strToArr(str); // convert string to array
            
            int found = Utility.findIndexInStrArr(arr, search); // search the search item in array
            if(found >= 0) {
                if(strMp3List.equals("")) {
                    strMp3List = profileDB[ctr].getProfileName();
                }
                else {
                    strMp3List = strMp3List + ", " + profileDB[ctr].getProfileName();
                }
            }
        }
        
        return strMp3List;
    }
}
