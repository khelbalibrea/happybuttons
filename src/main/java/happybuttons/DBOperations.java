/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package happybuttons;

import javax.swing.JOptionPane;

/**
 *
 * @author Michael Balibrea
 */
public class DBOperations {
    public static int count = 0;
    public static int indexDB = -1;
    
    public boolean saveEnvironment(ProfileDatabase profileDB[], Profile profile){
        String profileNameSet = "";
        if(indexDB == 0){
            if(HappyButtons.noDB == 0) {
                profile.setProfileName(SaveFrame.profileName1);
                profileNameSet = SaveFrame.profileName1;
            }
            else {
                profile.setProfileName("");
            }
        }
        else if(indexDB == 1){
            if(HappyButtons.noDB == 0) {
                profile.setProfileName(SaveFrame.profileName2);
                profileNameSet = SaveFrame.profileName2;
            }
            else {
                profile.setProfileName("");
            }
        }
        else if(indexDB == 2){
            if(HappyButtons.noDB == 0) {
                profile.setProfileName(SaveFrame.profileName3);
                profileNameSet = SaveFrame.profileName3;
            }
            else {
                profile.setProfileName("");
            }
        }
        else if(indexDB == 3){
            if(HappyButtons.noDB == 0) {
                profile.setProfileName(SaveFrame.profileName4);
                profileNameSet = SaveFrame.profileName4;
            }
            else {
                profile.setProfileName("");
            }
        }
        else if(indexDB == 4){
            if(HappyButtons.noDB == 0) {
                profile.setProfileName(SaveFrame.profileName5);
                profileNameSet = SaveFrame.profileName5;
            }
            else {
                profile.setProfileName("");
            }
        }
        
        // =================================================================================================== Set SFX group name
        if(HappyButtons.noDB == 0) {
            profile.setSfxName1(MainFrame.sfxGroupName1);
            profile.setSfxName2(MainFrame.sfxGroupName2);
            profile.setSfxName3(MainFrame.sfxGroupName3);
        }
        else {
            profile.setSfxName1("");
            profile.setSfxName2("");
            profile.setSfxName3("");
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
        }
        
        // =================================================================================================== Get profile name and grp sfx names
        profileDB[indexDB].setProfileName(profile.getProfileName());
            
        profileDB[indexDB].setSfxName1(profile.getSfxName1());
        profileDB[indexDB].setSfxName2(profile.getSfxName2());
        profileDB[indexDB].setSfxName3(profile.getSfxName3());
        
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
        
        boolean saved = new BeanHelper().writeToXml(profileDB);
        if(saved) {
            MainFrame.savedProfile = profileNameSet;
        }
        else {
            MainFrame.savedProfile = "error";
        }
        
        count = 0;
        indexDB = -1;
        
        return saved;
    }
    
    public String loadEnvironment(ProfileDatabase profileDB[], int index){
        ViewProfiles profileDetails = new ViewProfiles();
        
        if(profileDB[index].getProfileName() != ""){
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
            
            (MainFrame.tfSFXGroup1).setText(profileDB[index].getSfxName1());
            (MainFrame.tfSFXGroup2).setText(profileDB[index].getSfxName2());
            (MainFrame.tfSFXGroup3).setText(profileDB[index].getSfxName3());
            
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                profileDB[index].getProfileName() + " profile loaded", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            String profileName = profileDB[index].getProfileName();
            return profileName;
        }
        else {
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                "SLOT " + (index + 1) + " failed to load", 
                "Load failed", 
                JOptionPane.ERROR_MESSAGE);
            return "error";
        }
    }
}
