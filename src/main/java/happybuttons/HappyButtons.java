/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package happybuttons;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;
/**
 *
 * @author Michael Balibrea
 */
public class HappyButtons {
    public static String desktopPath = "";
    public static String desktopPathDoubleSlash = "";
    public static String firstCheck = "";
    public static MainFrame mf;
    
    public static ProfileDatabase[] profileDB = new ProfileDatabase[5];
    public static int noDB = 0;
    
    // Globals
        
    public static void main(String[] args) {
        getDesktopPath();
        initializeDatabase();
        checkMainFolder();
        checkSubFolders();
        
        desktopPathDoubleSlash = Utility.strDoubleSlash(desktopPath);
        System.out.print("Hi " + desktopPathDoubleSlash);
        
        mf = new MainFrame();
        mf.setVisible(true);
    }
    
    public static void getDesktopPath() {
        String command = "powershell -command \"[Environment]::GetFolderPath('\"Desktop\"')\"";
        
        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            
            String line;
            while ((line = reader.readLine()) != null) {
                desktopPath = line;
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void checkMainFolder(){
        if(desktopPath != "") {
            try{
                File mainPath = new File(desktopPath);

                if(!mainPath.exists()){
                    mainPath.mkdir();
                }
            } catch(Exception e){
    //            Controller.chkMain = 1;
            }
        }
    }
    
    public static void checkSubFolders(){
        // checking BG folder
        try{
            File subPath1 = new File(desktopPath + "\\HappyButtons\\bg");
           
            if(!subPath1.exists()){
                subPath1.mkdir();
                JOptionPane.showMessageDialog(mf, 
                    "\"" + desktopPath + "\\HappyButtons\\bg\" folder not found\n\n\"bg\" folder is created. Note that bg sounds involve in some profile saves may gone missing", 
                    "CRITICAL FOLDER MISSING", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch(Exception e){
//            Controller.chksub1 = 1;
        }
        
        // checking SFX folder
        try{
            File subPath1 = new File(desktopPath + "\\HappyButtons\\sfx");
           
            if(!subPath1.exists()){
                subPath1.mkdir();
                JOptionPane.showMessageDialog(mf, 
                    "\"" + desktopPath + "\\HappyButtons\\sfx\" folder not found\n\n\"sfx\" folder is created. Note that sfx sounds involve in some profile saves may gone missing", 
                    "CRITICAL FOLDER MISSING", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch(Exception e){
//            Controller.chksub1 = 1;
        }
    }
    
    // initialize XML as database
    public static void initializeDatabase(){
        File dbPath = new File(desktopPath + "\\HappyButtons\\happyDB.xml");
        
        if(dbPath.exists()){
//            for(int ctr = 0; ctr < profileDB.length; ctr++) {
//                ProfileDatabase[] profile = new BeanHelper().readFromXml();
//                profileDB = profile;
//            }
        }
        else {
            File file = new File(desktopPath + "\\HappyButtons\\happyDB.xml");
            noDB = 1;
            
            try {
                file.createNewFile();
                firstCheck = "[SYSTEM] No database found";
            } catch(Exception e){
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
