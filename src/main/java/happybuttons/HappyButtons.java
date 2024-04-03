/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package happybuttons;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
/**
 *
 * @author Michael Balibrea (khel) & •
 */
public class HappyButtons {
    public static String documentsPath = "";
    public static String documentsPathDoubleSlash = "";
    public static String firstCheck = "";
    public static int mainFolderChk = 0, bgFolderChk = 0, sfxFolderChk = 0, happyloopFolderChk = 0, vlcjFolderChk = 0; // if this sets to 1 means required folders aren't found, so it is created
    public static MainFrame mf;
    public static boolean go = false;
    
    public static DBOperations dbo = new DBOperations();
    public static ProfileDatabase[] profileDB = new ProfileDatabase[5];
    public static UIPreference[] uiDB = new UIPreference[1];
    public static int noDB = 0, loadedDB = -1, canAutosave = 0;
    public static String uiTheme = "", vlcjPath = "";
    
    // Globals
        
    public static void main(String[] args) {
        getHomePath();
        start();
    }
    
    public static void start() {
        while(!go) {
            checkMainFolder();
        }
        
        checkSubFolders();
        initializeDatabase();
        setupUIPreferences();
        
        documentsPathDoubleSlash = Utility.strDoubleSlash(documentsPath); // C:\\Users\\<PC NAME>\\Documents
        
        mf = new MainFrame();
        mf.setVisible(true);
        
        loadUISettings();
    }
    
    public static void loadUISettings() {
        dbo.loadSystemSettings(uiDB, 0);
        
        if(MainFrame.startup.equals("load")) {
            dbo.loadEnvironment(profileDB, MainFrame.loadedIndexProfile);
        }
        
        SystemClass.UITheme(uiTheme);
        SystemClass.setupElementsStatus();
    }
    
    public static void getHomePath() {
        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        
        documentsPath = fw.getDefaultDirectory().toString();
//        System.out.println(documentsPath);
        
//        THIS IS FOR GETTING DESKTOP PATH USING POWERSHELL IN JAVA
//        String command = "powershell -command \"[Environment]::GetFolderPath('\"Desktop\"')\"";
//        try {
//            Process process = Runtime.getRuntime().exec(command);
//
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(process.getInputStream()));
//            
//            String line;
//            while ((line = reader.readLine()) != null) {
//                desktopPath = line;
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    
    public static void checkMainFolder(){
        if(documentsPath != "") {
            try{
                File mainPath = new File(documentsPath + "/HappyButtons");

                if(!mainPath.exists()){
                    mainPath.mkdir();
                    mainFolderChk = 1;
                }
                else {
                    go = true;
                }
            } catch(Exception e){
    //            Controller.chkMain = 1;
            }
        }
    }
    
    public static void checkSubFolders(){
        // checking BG folder
        try {
            File subPath1 = new File(documentsPath + "\\HappyButtons\\bg");
           
            if(!subPath1.exists()){
                subPath1.mkdir();
                bgFolderChk = 1;
            }
        }
        catch(Exception e){
            
        }
        
        // checking SFX folder
        try {
            File subPath1 = new File(documentsPath + "\\HappyButtons\\sfx");
           
            if(!subPath1.exists()){
                subPath1.mkdir();
                sfxFolderChk = 1;
            }
        }
        catch(Exception e){
            
        }
        
        // checking Happy Loop vids folder
        try {
            File subPath1 = new File(documentsPath + "\\HappyButtons\\hlvids");
           
            if(!subPath1.exists()){
                subPath1.mkdir();
                happyloopFolderChk = 1;
            }
        }
        catch(Exception e){
            
        }
        
        // checking VLCj plugins folder
        try {
            File subPath1 = new File(documentsPath + "\\HappyButtons\\plugins");
           
            if(!subPath1.exists()){
                subPath1.mkdir();
                new File(documentsPath + "\\HappyButtons\\plugins\\vlcj").mkdir();
                vlcjFolderChk = 1;
            }
        }
        catch(Exception e){
            
        }
    }
    
    // initialize XML as database
    public static void initializeDatabase(){
        File dbPath = new File(documentsPath + "\\HappyButtons\\happyDB.xml");
        
        if(dbPath.exists()){
            for(int ctr = 0; ctr < profileDB.length; ctr++) {
                ProfileDatabase[] profile = new BeanHelper().readFromXml();
                profileDB = profile;
            }
        }
        else {
            File file = new File(documentsPath + "\\HappyButtons\\happyDB.xml");
            
            try {
                file.createNewFile();
                firstCheck = "[SYSTEM] No database found";
                noDB = 1;
                
                Profile profile = new Profile();
                for(int ctr = 0; ctr < profileDB.length; ctr++) {
                    profileDB[ctr] = new ProfileDatabase();
                    DBOperations.indexDB = ctr;
                    dbo.saveEnvironment(profileDB, profile);
                }
                
                noDB = 0;
            }
            catch(Exception e){
//                firstCheck = "[ERROR]::" + e.toString();
                System.out.println(e.toString());
                mf = new MainFrame();
                mf.setVisible(true);
            }
        }
    }
    
    public static void setupUIPreferences(){
        File dbPath = new File(documentsPath + "\\HappyButtons\\uidb.xml");
        
        if(dbPath.exists()){
            for(int ctr = 0; ctr < uiDB.length; ctr++) {
                UIPreference[] uiProf = new BeanHelper().readFromXmlUI();
                uiDB = uiProf;
            }
        }
        else {
            File file = new File(documentsPath + "\\HappyButtons\\uidb.xml");
            
            try {
                file.createNewFile();
//                firstCheck = "[SYSTEM] No database found";
//                noDB = 1;
                
                UIProfile uiProfile = new UIProfile();
                for(int ctr = 0; ctr < uiDB.length; ctr++) {
                    uiDB[ctr] = new UIPreference();
//                    DBOperations.indexDB = ctr;
                    dbo.autoSaveUISettings(uiDB, uiProfile);
                }
                
//                noDB = 0;
            }
            catch(Exception e){
//                firstCheck = "[ERROR]::" + e.toString();
//                System.out.println(e.toString());
//                mf = new MainFrame();
//                mf.setVisible(true);
            }
        }
    }
}
