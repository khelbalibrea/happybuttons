/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package happybuttons;

import java.awt.datatransfer.*;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

/**
 *
 * @author Michael Balibrea
 */
public class DnDListBGM extends TransferHandler {
    @Override
    public int getSourceActions(JComponent c){
        return TransferHandler.COPY;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        MainFrame.draggedList = 0; // this is bgm list
        
        JList drgSrc = (JList) c;
        List<String> values = drgSrc.getSelectedValuesList();
        
        String data = "";
        for(String str: values){
            data = str;
        }
        
        return (new StringSelection(data));
    }

    @Override
    public boolean canImport(TransferSupport support) {
//        Boolean proceed = false;
//        
//        if(support.isDataFlavorSupported(DataFlavor.stringFlavor)){
//            proceed = true;
//        }
//        
//        return proceed;
//        return support.getComponent() instanceof JTextComponent;
        return false;
    }
    
    @Override
    public boolean importData(TransferSupport support){
//        Transferable exportTfr = support.getTransferable();
//        
//        String exportedData = "";
//        
//        try {
//            exportedData = (String) exportTfr.getTransferData(DataFlavor.stringFlavor);
//            exportTfr.getTransferData(DataFlavor.stringFlavor);
//        } catch (UnsupportedFlavorException ex) {
//            Logger.getLogger(SimpleListDTSManager.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(SimpleListDTSManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
//        JTextField dropTarget = (JTextField)support.getComponent();
//        JTextField.DropLocation dropLocation = (JTextField.DropLocation) support.getDropLocation();
//        
//        try {
//            ((JTextComponent) support.getComponent())
//                .setText((String) support
//                         .getTransferable()
//                         .getTransferData(DataFlavor.stringFlavor));
//            return true;
//        } catch(UnsupportedFlavorException e) {
//            return false;
//        } catch(IOException e) {
//            return false;
//        }
        return true;
    }
}
