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
            profile.setProfileName("Sample");
        }
//        else if(index == 1){
//            profile.setProfileName(SaveProfileController.profileName2);
//        }
//        else if(index == 2){
//            profile.setProfileName(SaveProfileController.profileName3);
//        }
//        else if(index == 3){
//            profile.setProfileName(SaveProfileController.profileName4);
//        }
//        else if(index == 4){
//            profile.setProfileName(SaveProfileController.profileName5);
//        }
        
        profile.setSfxName1(MainFrame.sfxGroupName1);
//        profile.setSfxName2(Controller.sfxGrp2);
//        profile.setSfxName3(Controller.sfxGrp3);
//        profile.setSfxName4(Controller.sfxGrp4);
//        profile.setSfxName5(Controller.sfxGrp5);
        
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
        
//        profile.setSfx11(Controller.sfxn11);
//        profile.setSfx12(Controller.sfxn12);
//        profile.setSfx13(Controller.sfxn13);
//        profile.setSfx14(Controller.sfxn14);
//        profile.setSfx15(Controller.sfxn15);
//        profile.setSfx16(Controller.sfxn16);
//        profile.setSfx17(Controller.sfxn17);
//        profile.setSfx18(Controller.sfxn18);
//        profile.setSfx19(Controller.sfxn19);
//        profile.setSfx20(Controller.sfxn20);
//        
//        profile.setSfx21(Controller.sfxn21);
//        profile.setSfx22(Controller.sfxn22);
//        profile.setSfx23(Controller.sfxn23);
//        profile.setSfx24(Controller.sfxn24);
//        profile.setSfx25(Controller.sfxn25);
//        profile.setSfx26(Controller.sfxn26);
//        profile.setSfx27(Controller.sfxn27);
//        profile.setSfx28(Controller.sfxn28);
//        profile.setSfx29(Controller.sfxn29);
//        profile.setSfx30(Controller.sfxn30);
//        
//        profile.setSfx31(Controller.sfxn31);
//        profile.setSfx32(Controller.sfxn32);
//        profile.setSfx33(Controller.sfxn33);
//        profile.setSfx34(Controller.sfxn34);
//        profile.setSfx35(Controller.sfxn35);
//        profile.setSfx36(Controller.sfxn36);
//        profile.setSfx37(Controller.sfxn37);
//        profile.setSfx38(Controller.sfxn38);
//        profile.setSfx39(Controller.sfxn39);
//        profile.setSfx40(Controller.sfxn40);
//        
//        profile.setSfx41(Controller.sfxn41);
//        profile.setSfx42(Controller.sfxn42);
//        profile.setSfx43(Controller.sfxn43);
//        profile.setSfx44(Controller.sfxn44);
//        profile.setSfx45(Controller.sfxn45);
//        profile.setSfx46(Controller.sfxn46);
//        profile.setSfx47(Controller.sfxn47);
//        profile.setSfx48(Controller.sfxn48);
//        profile.setSfx49(Controller.sfxn49);
//        profile.setSfx50(Controller.sfxn50);
//        
//        profile.setHK1(Controller.hkn1);
//        profile.setHK2(Controller.hkn2);
//        profile.setHK3(Controller.hkn3);
//        profile.setHK4(Controller.hkn4);
//        profile.setHK5(Controller.hkn5);
        
        profileDB[indexDB].setProfileName(profile.getProfileName());
        
        profileDB[indexDB].setSfxName1(profile.getSfxName1());
//        profileDB[index].setSfxName2(profile.getSfxName2());
//        profileDB[index].setSfxName3(profile.getSfxName3());
//        profileDB[index].setSfxName4(profile.getSfxName4());
//        profileDB[index].setSfxName5(profile.getSfxName5());

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

//        profileDB[index].setSfx11(profile.getSfx11());
//        profileDB[index].setSfx12(profile.getSfx12());
//        profileDB[index].setSfx13(profile.getSfx13());
//        profileDB[index].setSfx14(profile.getSfx14());
//        profileDB[index].setSfx15(profile.getSfx15());
//        profileDB[index].setSfx16(profile.getSfx16());
//        profileDB[index].setSfx17(profile.getSfx17());
//        profileDB[index].setSfx18(profile.getSfx18());
//        profileDB[index].setSfx19(profile.getSfx19());
//        profileDB[index].setSfx20(profile.getSfx20());
//
//        profileDB[index].setSfx21(profile.getSfx21());
//        profileDB[index].setSfx22(profile.getSfx22());
//        profileDB[index].setSfx23(profile.getSfx23());
//        profileDB[index].setSfx24(profile.getSfx24());
//        profileDB[index].setSfx25(profile.getSfx25());
//        profileDB[index].setSfx26(profile.getSfx26());
//        profileDB[index].setSfx27(profile.getSfx27());
//        profileDB[index].setSfx28(profile.getSfx28());
//        profileDB[index].setSfx29(profile.getSfx29());
//        profileDB[index].setSfx30(profile.getSfx30());
//
//        profileDB[index].setSfx31(profile.getSfx31());
//        profileDB[index].setSfx32(profile.getSfx32());
//        profileDB[index].setSfx33(profile.getSfx33());
//        profileDB[index].setSfx34(profile.getSfx34());
//        profileDB[index].setSfx35(profile.getSfx35());
//        profileDB[index].setSfx36(profile.getSfx36());
//        profileDB[index].setSfx37(profile.getSfx37());
//        profileDB[index].setSfx38(profile.getSfx38());
//        profileDB[index].setSfx39(profile.getSfx39());
//        profileDB[index].setSfx40(profile.getSfx40());
//
//        profileDB[index].setSfx41(profile.getSfx41());
//        profileDB[index].setSfx42(profile.getSfx42());
//        profileDB[index].setSfx43(profile.getSfx43());
//        profileDB[index].setSfx44(profile.getSfx44());
//        profileDB[index].setSfx45(profile.getSfx45());
//        profileDB[index].setSfx46(profile.getSfx46());
//        profileDB[index].setSfx47(profile.getSfx47());
//        profileDB[index].setSfx48(profile.getSfx48());
//        profileDB[index].setSfx49(profile.getSfx49());
//        profileDB[index].setSfx50(profile.getSfx50());
//        
//        profileDB[index].setHK1(profile.getHK1());
//        profileDB[index].setHK2(profile.getHK2());
//        profileDB[index].setHK3(profile.getHK3());
//        profileDB[index].setHK4(profile.getHK4());
//        profileDB[index].setHK5(profile.getHK5());
        

//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Operation Success");
//        alert.setHeaderText("Profile saved");
//        alert.setContentText(null);
//        alert.showAndWait();
        
        new BeanHelper().writeToXml(profileDB);
        
//        Controller.stagePane.close();
        count = 0;
        indexDB = -1;
        //Controller.setOperationStatus("");
    }
}
