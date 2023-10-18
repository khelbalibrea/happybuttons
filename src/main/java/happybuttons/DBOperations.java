/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package happybuttons;

/**
 *
 * @author Michael Balibrea
 */
public class DBOperations {
    public static int count = 0;
    public static int indexDB = -1;
    
    public void saveEnvironment(ProfileDatabase profileDB[], Profile profile){
//        ViewProfiles profileDetails = new ViewProfiles();
        
        if(indexDB == 0){
            if(HappyButtons.noDB == 0) {
                profile.setProfileName("Sample");
            }
            else {
                profile.setProfileName("");
            }
        }
        else if(indexDB == 1){
            if(HappyButtons.noDB == 0) {
                profile.setProfileName("Sample");
            }
            else {
                profile.setProfileName("");
            }
        }
        else if(indexDB == 2){
            if(HappyButtons.noDB == 0) {
                profile.setProfileName("Sample");
            }
            else {
                profile.setProfileName("");
            }
        }
        else if(indexDB == 3){
            if(HappyButtons.noDB == 0) {
                profile.setProfileName("Sample");
            }
            else {
                profile.setProfileName("");
            }
        }
        else if(indexDB == 4){
            if(HappyButtons.noDB == 0) {
                profile.setProfileName("Sample");
            }
            else {
                profile.setProfileName("");
            }
        }
        
        // =================================================================================================== Set SFX group name
        if(HappyButtons.noDB == 0) {
            profile.setSfxName1(MainFrame.sfxGroupName1);
//            profile.setSfxName2(Controller.sfxGrp2);
//            profile.setSfxName3(Controller.sfxGrp3);
//            profile.setSfxName4(Controller.sfxGrp4);
//            profile.setSfxName5(Controller.sfxGrp5);
        }
        else {
            profile.setSfxName1("");
            profile.setSfxName2("");
            profile.setSfxName3("");
            profile.setSfxName4("");
            profile.setSfxName5("");
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
        }
        
        // =================================================================================================== Get profile name and grp sfx names
        profileDB[indexDB].setProfileName(profile.getProfileName());
            
        profileDB[indexDB].setSfxName1(profile.getSfxName1());
        profileDB[indexDB].setSfxName2(profile.getSfxName2());
        profileDB[indexDB].setSfxName3(profile.getSfxName3());
        profileDB[indexDB].setSfxName4(profile.getSfxName4());
        profileDB[indexDB].setSfxName5(profile.getSfxName5());
        
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
        
        new BeanHelper().writeToXml(profileDB);
        
        count = 0;
        indexDB = -1;
    }
}
