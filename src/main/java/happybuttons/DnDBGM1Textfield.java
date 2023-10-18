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
import javax.swing.TransferHandler;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Michael Balibrea
 */
public class DnDBGM1Textfield extends TransferHandler {
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
        if(MainFrame.draggedList == 0){ // check if dragged data comes from BGM list
            return ts.getComponent() instanceof JTextComponent;
        }
        else {
            return false;
        }
    }

    public boolean importData(TransferSupport ts) {
        try {
            ((JTextComponent) ts.getComponent()).setText((String) ts.getTransferable().getTransferData(DataFlavor.stringFlavor));
            
            if(MainFrame.clipBGM1 != null) {
                MainFrame.lastFrame1 = 0;
                MainFrame.clipBGM1.stop();

                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                MainFrame.btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));

                MainFrame.playing1 = 0;
                MainFrame.pause1 = 0;
                
                MainFrame.clipBGM1 = null;
            }
            
            return true;
        }
        catch(UnsupportedFlavorException | IOException e) {
            return false;
        }
    }
}
