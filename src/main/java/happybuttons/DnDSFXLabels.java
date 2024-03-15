/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package happybuttons;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.TransferHandler;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Michael Balibrea
 */
public class DnDSFXLabels extends TransferHandler {
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    public Transferable createTransferable(JComponent c) {
        return new StringSelection(((JTextComponent) c).getSelectedText());
    }

    public void exportDone(JComponent c, Transferable t, int action) {
        if(action == MOVE)
            ((JTextComponent) c).replaceSelection("");
    }

    public boolean canImport(TransferSupport ts) {
        if(MainFrame.draggedList == 1){ // check if dragged data comes from SFX list
//            return ts.getComponent() instanceof JTextComponent;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean importData(TransferSupport ts) {
        try {
            ((JLabel) ts.getComponent()).setText("<html><center>" 
                    + (String) ts.getTransferable().getTransferData(DataFlavor.stringFlavor)
                    + "</center></html>");
            
            autosave();
            
//            if(MainFrame.clipBGM1 != null) {
//                MainFrame.lastFrame1 = 0;
//                MainFrame.clipBGM1.stop();
//
//                String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
//                MainFrame.btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
//
//                MainFrame.playing1 = 0;
//                MainFrame.pause1 = 0;
//                
//                MainFrame.clipBGM1 = null;
//            }
            return true;
        }
        catch(UnsupportedFlavorException | IOException e) {
            return false;
        }
    }
    
    public void autosave() {
        if(HappyButtons.canAutosave == 1) {
            prepareSave();
            Profile profile = new Profile();
            DBOperations.indexDB = HappyButtons.loadedDB;
            
            HappyButtons.profileDB[HappyButtons.loadedDB] = new ProfileDatabase();
            (HappyButtons.dbo).saveEnvironment(HappyButtons.profileDB, profile);
            
            String lastText = MainFrame.tfLastOperation.getText();
            MainFrame.tfLastOperation.setText("SFX changes");
            MainFrame.tfLastOperation.setText(lastText);
        }
    }
    
    public void prepareSave() {
        // BGMs
        int listBGMSize = MainFrame.listBGM.getModel().getSize();
        MainFrame.strBGM = "";
        
        for(int ctr = 0; ctr < listBGMSize; ctr++){
            if(ctr == 0) {
                MainFrame.strBGM = MainFrame.listBGM.getModel().getElementAt(ctr);
            }
            else if(ctr > 0 && ctr <= (listBGMSize - 1)) {
                MainFrame.strBGM = MainFrame.strBGM + ":" + MainFrame.listBGM.getModel().getElementAt(ctr);
            }
        }
        
        // SFXs
        int listSFXSize = MainFrame.listSFX.getModel().getSize();
        MainFrame.strSFX = "";
        
        for(int ctr = 0; ctr < listSFXSize; ctr++){
            if(ctr == 0) {
                MainFrame.strSFX = MainFrame.listSFX.getModel().getElementAt(ctr);
            }
            else if(ctr > 0 && ctr <= (listSFXSize - 1)) {
                MainFrame.strSFX = MainFrame.strSFX + ":" + MainFrame.listSFX.getModel().getElementAt(ctr);
            }
        }
        
        // Video Loop videos
        int cboHappyLoopSize = MainFrame.cboVidLoop.getModel().getSize();
        MainFrame.strVidLoop = "";
        
        for(int ctr = 0; ctr < cboHappyLoopSize; ctr++) {
            if(ctr == 0) {
                MainFrame.strVidLoop = MainFrame.cboVidLoop.getModel().getElementAt(ctr);
            }
            else if(ctr > 0 && ctr <= (cboHappyLoopSize - 1)) {
                MainFrame.strVidLoop = MainFrame.strVidLoop + ":" + MainFrame.cboVidLoop.getModel().getElementAt(ctr);
            }
        }
    }
}
