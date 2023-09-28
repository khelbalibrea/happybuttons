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
    public static String desktopPathDoubleQuote = "";
    public static MainFrame mf;
    
    public static ProfileDatabase[] profileDB = new ProfileDatabase[5];
    public static int noDB = 0;
    
    // Globals
        
    public static void main(String[] args) {
        initializeDatabase();
        getDesktopPath();
        checkMainFolder();
        checkSubFolders();
        
        desktopPathDoubleQuote = Utility.strDoubleQuote(desktopPath);
        // System.out.print("Hi " + desktopPathDoubleQuote);
        
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

        for(int ctr = 0; ctr < profileDB.length; ctr++) {
            if(dbPath.exists()){
                ProfileDatabase[] profile = new BeanHelper().readFromXml();
                profileDB = profile;
            } else {
                noDB = 1;
                profileDB[ctr] = new ProfileDatabase();
            }
        }
    }
}
