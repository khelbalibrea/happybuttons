/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package happybuttons;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
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
    public static int mainFolderChk = 0, // if this sets to 1 means required folders aren't found, so it is created
            bgFolderChk = 0, 
            sfxFolderChk = 0, 
            happyloopFolderChk = 0, 
            vlcjFolderChk = 0, 
            mp3FolderChk = 0, 
            dtbsFolderChk = 0;
    public static MainFrame mf;
    public static boolean go = false, standardScreen = false;
    
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
        
        getScreenCount();
        
        dbo.loadSystemSettings(uiDB, 0);
        
        mf = new MainFrame();
        mf.setVisible(true);
        
        if(MainFrame.startup.equals("load")) {
            MainFrame.dbLoadedManual = 0;
            dbo.loadEnvironment(profileDB, MainFrame.loadedIndexProfile);
            mf.setTitle("Happy Buttons - (" + mf.savingProfile + ")");
        }
        else {
            mf.setTitle("Happy Buttons");
        }
        
        loadUISettings();
    }
    
    public static void loadUISettings() {
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
        
        // checking Video thumbnails folder
        try {
            File subPath1 = new File(documentsPath + "\\HappyButtons\\dtbs\\thumbnails");
           
            if(!subPath1.exists()){
                subPath1.mkdir();
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
        
        // checking MP3 folder
        try {
            File subPath1 = new File(documentsPath + "\\HappyButtons\\mp3s");
           
            if(!subPath1.exists()){
                subPath1.mkdir();
                new File(documentsPath + "\\HappyButtons\\mp3s").mkdir();
                mp3FolderChk = 1;
            }
        }
        catch(Exception e){
            
        }
        
        // checking DBs folder
        try {
            File subPath1 = new File(documentsPath + "\\HappyButtons\\dtbs");
           
            if(!subPath1.exists()){
                subPath1.mkdir();
                dtbsFolderChk = 1;
            }
        }
        catch(Exception e){
            
        }
        
        // checking data folder
        try {
            File subPath1 = new File(documentsPath + "\\HappyButtons\\data");
           
            if(!subPath1.exists()){
                subPath1.mkdir();
//                dataFolderChk = 1;
            }
        }
        catch(Exception e){
            
        }
    }
    
    // initialize XML as database
    public static void initializeDatabase(){
        File dbPath = new File(documentsPath + "\\HappyButtons\\dtbs\\happyDB.xml");
        
        if(dbPath.exists()){
            for(int ctr = 0; ctr < profileDB.length; ctr++) {
                ProfileDatabase[] profile = new BeanHelper().readFromXml();
                profileDB = profile;
            }
        }
        else {
            File file = new File(documentsPath + "\\HappyButtons\\dtbs\\happyDB.xml");
            
            try {
                file.createNewFile();
                firstCheck = "[SYSTEM]:: No database found";
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
        File dbPath = new File(documentsPath + "\\HappyButtons\\dtbs\\uidb.xml");
        
        if(dbPath.exists()){
            for(int ctr = 0; ctr < uiDB.length; ctr++) {
                UIPreference[] uiProf = new BeanHelper().readFromXmlUI();
                uiDB = uiProf;
            }
        }
        else {
            File file = new File(documentsPath + "\\HappyButtons\\dtbs\\uidb.xml");
            
            try {
                file.createNewFile();
                
                UIProfile uiProfile = new UIProfile();
                for(int ctr = 0; ctr < uiDB.length; ctr++) {
                    uiDB[ctr] = new UIPreference();
                    dbo.autoSaveUISettings(uiDB, uiProfile);
                }
            }
            catch(Exception e){
                
            }
        }
    }
    
    public static void getScreenCount() {
        GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        GraphicsDevice mainScreen = screenDevices[0];
        DisplayMode displayMode = mainScreen.getDisplayMode();
        
        int screenWidth = displayMode.getWidth();
        int screenHeight = displayMode.getHeight();
        System.out.println(screenWidth + "x" + screenHeight);
        
        if(screenWidth <= 1366 && screenHeight <= 768) {
            standardScreen = true;
        }
    }
    
    public static void checkVideoThumbnails() {
        File vlFolder = new File(documentsPath + "/HappyButtons/hlvids/");
        File[] vlFileList = vlFolder.listFiles();

        File thumbFolder = new File(documentsPath + "/HappyButtons/data/thumbnails/");
        File[] thumbFileList = thumbFolder.listFiles();

        String videoList[] = {};
        String thumbList[] = {};

        for(File f : vlFileList) {
            videoList = Utility.addElementInStrArr(videoList, Utility.renameListName(f.getName(), "mp4"));
        }

        for(File f : thumbFileList) {
            thumbList = Utility.addElementInStrArr(thumbList, Utility.renameListName(f.getName(), "png"));
        }
        
//        for(int ctr = 0; ctr < videoList.length; ctr++) {
//            System.out.println(ctr + 1 + ". " + videoList[ctr]);
//        }

        for(int ctr = 0; ctr < videoList.length; ctr++) {
            String file = videoList[ctr]; // sample: video_name.mp4
            boolean found = Utility.doesStrArrHasElement(thumbList, file); //System.out.println(file + " -> " + found);

            if(!found) { 
                // generate video thumbnail
                try{
                    VidThumbnailGenerator gen = new VidThumbnailGenerator();
                    gen.createThumbnail(vlFileList[ctr].getName());
                }
                catch(Exception e) {
                    System.out.println("\n\nError1: \n" + e.toString());
                }
            }
            else {
                continue;
            }
        }
        
        SystemToolsFrame.textResult.setText("");
        SystemToolsFrame.textResult.setText("VIDEO THUMBNAILS:\n\nTask done. Note that this is not guaranteed. If video still doesn't generate thumbnails, delete the video and add it again manually.");
    }
    
    public static void fixResizeImage() throws IOException {
        File imgFolder = new File(documentsPath + "/HappyButtons/data/thumbnails/");
        File[] imgFileList = imgFolder.listFiles();
        
        String toReduceSize[] = {};
        String result = "";

        for(File f : imgFileList) {
            Dimension imgDim = Utility.getImageDimension(f);
            
            if(imgDim.width > imgDim.height) {
                if(imgDim.width > 96 || imgDim.height > 72) {
                    toReduceSize = Utility.addElementInStrArr(toReduceSize, f.getName());
                }
            }
            else {
                if(imgDim.width > 72 || imgDim.height > 96) {
                    toReduceSize = Utility.addElementInStrArr(toReduceSize, f.getName());
                }
            }
        }
        
//        for(int ctr = 0; ctr < toReduceSize.length; ctr++) {
//            System.out.println((ctr + 1) + ". " + toReduceSize[ctr]);
//        }
        
        if(toReduceSize.length > 0) {
            for(int ctr = 0; ctr < toReduceSize.length; ctr++) {
                Dimension imgDim = Utility.getImageDimension(new File(HappyButtons.documentsPath + "/HappyButtons/data/thumbnails/" + toReduceSize[ctr]));
                Utility.resizeImage(HappyButtons.documentsPath + "/HappyButtons/data/thumbnails/" + toReduceSize[ctr]);
                
                result = result + "Resized image: " + toReduceSize[ctr] + " (" + imgDim.width + "x" + imgDim.height + ")\nSuccess!\n\n";
            }
        }
        else {
            result = "All images are good in size";
        }
        
        SystemToolsFrame.textResult.setText("");
        SystemToolsFrame.textResult.setText("RESIZING IMAGE\n\n" + result);
    }
}
