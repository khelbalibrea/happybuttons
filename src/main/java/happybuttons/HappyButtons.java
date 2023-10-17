/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package happybuttons;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
/**
 *
 * @author Michael Balibrea
 */
public class HappyButtons {
    public static String documentsPath = "";
    public static String documentsPathDoubleSlash = "";
    public static String firstCheck = "";
    public static int mainFolderChk = 0, bgFolderChk = 0, sfxFolderChk = 0; // if this sets to 1 means bg and sfx folders aren't found, so it is created
    public static MainFrame mf;
    public static boolean go = false;
    
    public static DBOperations dbo = new DBOperations();
    public static ProfileDatabase[] profileDB = new ProfileDatabase[5];
    public static int noDB = 0;
    
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
        
        documentsPathDoubleSlash = Utility.strDoubleSlash(documentsPath); // C:\\Users\\<PC NAME>\\Documents
        
        mf = new MainFrame();
        mf.setVisible(true);
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
        try{
            File subPath1 = new File(documentsPath + "\\HappyButtons\\bg");
           
            if(!subPath1.exists()){
                subPath1.mkdir();
                bgFolderChk = 1;
            }
        }
        catch(Exception e){
//            Controller.chksub1 = 1;
        }
        
        // checking SFX folder
        try{
            File subPath1 = new File(documentsPath + "\\HappyButtons\\sfx");
           
            if(!subPath1.exists()){
                subPath1.mkdir();
                sfxFolderChk = 1;
            }
        }
        catch(Exception e){
//            Controller.chksub1 = 1;
        }
    }
    
    // initialize XML as database
    public static void initializeDatabase(){
        File dbPath = new File(documentsPath + "\\HappyButtons\\happyDB.xml");
        
        if(dbPath.exists()){
//            for(int ctr = 0; ctr < profileDB.length; ctr++) {
//                ProfileDatabase[] profile = new BeanHelper().readFromXml();
//                profileDB = profile;
//            }
        }
        else {
            File file = new File(documentsPath + "\\HappyButtons\\happyDB.xml");
            noDB = 1;
            
            try {
                file.createNewFile();
                firstCheck = "[SYSTEM] No database found";
            }
            catch(Exception e){
                firstCheck = "[ERROR]::" + e.toString();
                mf = new MainFrame();
                mf.setVisible(true);
            }
            
            for(int ctr = 0; ctr < profileDB.length; ctr++) {
                profileDB[ctr] = new ProfileDatabase();
            }
        }

//        for(int ctr = 0; ctr < profileDB.length; ctr++) {
//            if(dbPath.exists()){
//                System.out.print("File found");
//                ProfileDatabase[] profile = new BeanHelper().readFromXml();
//                profileDB = profile;
//            }
//            else {
//                System.out.print("No file found");
//                File file = new File("happyDB.xml");
//                noDB = 1;
//                profileDB[ctr] = new ProfileDatabase();
//            }
//        }
    }
}
