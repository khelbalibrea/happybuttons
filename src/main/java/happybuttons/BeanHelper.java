/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package happybuttons;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author Michael Balibrea
 */
public class BeanHelper {
    public boolean writeToXml(ProfileDatabase[] profileDB) {		
        try {
            XMLEncoder encoder = new XMLEncoder(
                new BufferedOutputStream(
                    new FileOutputStream(HappyButtons.documentsPath + "\\HappyButtons\\happyDB.xml")
                )
            );

            encoder.writeObject(profileDB);
            encoder.close();
            
            return true;
        }
        catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(null, "XML write error: " + fnfe.toString());
            return false;
        }
    }
	
    public ProfileDatabase[] readFromXml() {
        ProfileDatabase ProfileDatabase[] = null;

        try {
            XMLDecoder decoder = new XMLDecoder(
                new BufferedInputStream(
                    new FileInputStream(HappyButtons.documentsPath + "\\HappyButtons\\happyDB.xml")
                )
            );

            ProfileDatabase = (ProfileDatabase[]) decoder.readObject();
            decoder.close();
        }
        catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(null, "XML read error: " + fnfe.toString());
        }
        
        return ProfileDatabase;
    }
    
    // -------------------------------------------------------------------------------------------------------- UI Preferences
    public boolean writeToXmlUI(UIPreference[] uiDB) {		
        try {
            XMLEncoder encoder = new XMLEncoder(
            new BufferedOutputStream(
            new FileOutputStream(HappyButtons.documentsPath + "\\HappyButtons\\uidb.xml")));

            encoder.writeObject(uiDB);
            encoder.close();
            
            return true;
        }
        catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(null, "XML write error: " + fnfe.toString());
            return false;
        }
    }
    
    public UIPreference[] readFromXmlUI() {
        UIPreference UIPreference[] = null;

        try {
            XMLDecoder decoder = new XMLDecoder(
            new BufferedInputStream(
            new FileInputStream(HappyButtons.documentsPath + "\\HappyButtons\\uidb.xml")));

            UIPreference = (UIPreference[]) decoder.readObject();
            decoder.close();
        }
        catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(null, "XML read error: " + fnfe.toString());
        }
        
        return UIPreference;
    }
}
