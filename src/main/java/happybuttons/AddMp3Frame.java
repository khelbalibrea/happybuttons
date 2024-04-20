/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package happybuttons;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Michael Balibrea
 */
public class AddMp3Frame extends javax.swing.JDialog {
    DefaultTableModel model;
    /**
     * Creates new form AddMp3Frame
     */
    public AddMp3Frame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        super.setTitle("Add Video");
        initComponents();
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/3-this.getSize().height/2);
        
        // set frame icon
        ImageIcon imgIcon = new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png"));
        setIconImage(imgIcon.getImage());
        
        tblMp3List.setRowSelectionAllowed(true);
        tblMp3List.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        populateTable();
    }
    
    public void populateTable() {
        File mp3folder = new File(HappyButtons.documentsPath + "/HappyButtons/mp3s/");
        File[] mp3FileList = mp3folder.listFiles();
        model = (DefaultTableModel) tblMp3List.getModel();
        
        for(File f : mp3FileList) {
            String mp3List = "";
            mp3List = (HappyButtons.dbo).checkMp3InProfiles(HappyButtons.profileDB, Utility.renameListName(f.getName(), "wav"));
            
            model.insertRow(model.getRowCount(), new Object[]{
                Utility.renameListName(f.getName(), "wav"), mp3List
            });
//            System.out.println(ctr + " -> " + Utility.renameListName(f.getName())); ctr++;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblMp3List = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblMp3List.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mp3 Item", "Used in (Profile)"
            }
        ));
        jScrollPane1.setViewportView(tblMp3List);

        btnAdd.setText("Add to my Mp3 list");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        int selectedRow = tblMp3List.getSelectedRow();
        model = (DefaultTableModel) tblMp3List.getModel();
        
        if(selectedRow != -1) {
            int confirmation = JOptionPane.showConfirmDialog(MainFrame.mp3, 
                    "Add selected item(s)?", 
                    "Add items", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);
            
            if(confirmation == 0) {
                int[] selectedRows = tblMp3List.getSelectedRows();

                for(int i = 0; i < selectedRows.length; i++) {
                    if(MainFrame.strMp3List.equals("")) {
                        MainFrame.mlist.removeAllElements();
                        Mp3Frame.listMp3.removeAll();
                        MainFrame.strMp3List = tblMp3List.getValueAt(selectedRows[i], 0).toString();
                    }
                    else {
                        MainFrame.strMp3List = MainFrame.strMp3List + ":" + tblMp3List.getValueAt(selectedRows[i], 0).toString();
                    }
                    
                    if(!(MainFrame.mlist).contains(tblMp3List.getValueAt(selectedRows[i], 0).toString())) {
                        (MainFrame.mlist).addElement(tblMp3List.getValueAt(selectedRows[i], 0).toString());
                        (MainFrame.tfLastOperation).setText("[ADDED MP3]:: " + tblMp3List.getValueAt(selectedRows[i], 0).toString());
                    }
                    
                    if(Utility.doesStrArrHasElement(MainFrame.mp3MainQueue, tblMp3List.getValueAt(selectedRows[i], 0).toString())) {
                        MainFrame.mp3MainQueue = Utility.addElementInStrArr(MainFrame.mp3MainQueue, tblMp3List.getValueAt(selectedRows[i], 0).toString());
                    }
                    
                    if(Utility.doesStrArrHasElement(MainFrame.mp3Queue, tblMp3List.getValueAt(selectedRows[i], 0).toString())) {
                        MainFrame.mp3Queue = Utility.addElementInStrArr(MainFrame.mp3Queue, tblMp3List.getValueAt(selectedRows[i], 0).toString());
                    }
                    
                    (Mp3Frame.listMp3).setModel(MainFrame.mlist);
                    
//                    if((MainFrame.cboModel).getIndexOf(tblMp3List.getValueAt(selectedRows[i], 0).toString()) < 0) {
//                        (MainFrame.cboModel).addElement(tblMp3List.getValueAt(selectedRows[i], 0).toString());
//                        (MainFrame.tfLastOperation).setText("[ADDED VIDEO]:: " + tblMp3List.getValueAt(selectedRows[i], 0).toString());
//                    }
//                     System.out.println(tblBGMList.getValueAt(selectedRows[i], 0).toString());
                }
                autosave();
//                Mp3Frame.sortJList(MainFrame.mlist);
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddMp3Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddMp3Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddMp3Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddMp3Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddMp3Frame dialog = new AddMp3Frame(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMp3List;
    // End of variables declaration//GEN-END:variables
    
    public void autosave() {
        if(MainFrame.enableAutosave.equals("on")) {
            if(HappyButtons.canAutosave == 1) {
                prepareSave();
                Profile profile = new Profile();
                DBOperations.indexDB = HappyButtons.loadedDB;

                HappyButtons.profileDB[HappyButtons.loadedDB] = new ProfileDatabase();
                (HappyButtons.dbo).saveEnvironment(HappyButtons.profileDB, profile);
            }
        }
    }
    
    public void prepareSave() {
        // Mp3s
        int listMp3Size = Mp3Frame.listMp3.getModel().getSize();
        MainFrame.strMp3List = "";
        
        for(int ctr = 0; ctr < listMp3Size; ctr++){
            if(ctr == 0) {
                MainFrame.strMp3List = Mp3Frame.listMp3.getModel().getElementAt(ctr);
            }
            else if(ctr > 0 && ctr <= (listMp3Size - 1)) {
                MainFrame.strMp3List = MainFrame.strMp3List + ":" + Mp3Frame.listMp3.getModel().getElementAt(ctr);
            }
        }
    }
}
