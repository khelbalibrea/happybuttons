/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package happybuttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Michael Balibrea
 */
public class AddSFXFrame extends javax.swing.JDialog {
    DefaultTableModel model;
    String theme = HappyButtons.uiTheme;
    
    /**
     * Creates new form AddSFXFrame
     */
    public AddSFXFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        super.setTitle("Add SFX");
        initComponents();
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/3-this.getSize().height/2);
        
        // set frame icon
        ImageIcon imgIcon = new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png"));
        setIconImage(imgIcon.getImage());
        
        tblSFXList.setRowSelectionAllowed(true);
        tblSFXList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        setupTheme();
        populateTable();
    }
    
    public void populateTable() {
        File bfolder = new File(HappyButtons.documentsPath + "/HappyButtons/sfx/");
        
        File[] sFileList = bfolder.listFiles();
        
        model = (DefaultTableModel) tblSFXList.getModel();
        int ctr = 1;
        for(File f : sFileList) {
            String sfxList = "";
            sfxList = (HappyButtons.dbo).checkSfxInProfiles(HappyButtons.profileDB, Utility.renameListName(f.getName(), "wav"));
            
            model.insertRow(model.getRowCount(), new Object[]{
                Utility.renameListName(f.getName(), "wav"), sfxList
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
        tblSFXList = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        tblSFXList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sound item", "Used in (Profile)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblSFXList);

        btnAdd.setText("Add to my SFX list");
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
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
        int selectedRow = tblSFXList.getSelectedRow();
        model = (DefaultTableModel) tblSFXList.getModel();
        
        if(selectedRow != -1) {
            int confirmation = JOptionPane.showConfirmDialog(null, 
                    "Add selected item(s)?", 
                    "Add items", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);
            
            if(confirmation == 0) {
                int[] selectedRows = tblSFXList.getSelectedRows();

                for(int i = 0; i < selectedRows.length; i++) {
                    if(!(MainFrame.slist).contains(tblSFXList.getValueAt(selectedRows[i], 0).toString())) {
                        (MainFrame.slist).addElement(tblSFXList.getValueAt(selectedRows[i], 0).toString());
                    }
//                     System.out.println(tblBGMList.getValueAt(selectedRows[i], 0).toString());
                }
                (MainFrame.listSFX).setModel(MainFrame.slist);
                
                autosave();
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void setupTheme() {
        if(theme.equals("light")) {
            this.getContentPane().setBackground(new JDialog().getBackground());
            
            btnAdd.setBackground(new JButton().getBackground());
            btnAdd.setForeground(new JButton().getForeground());
        }
        else if(theme.equals("dark")) {
            this.getContentPane().setBackground(Color.DARK_GRAY);
            
            btnAdd.setBackground(Color.GRAY);
            btnAdd.setForeground(Color.WHITE);
        }
    }
    
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
            java.util.logging.Logger.getLogger(AddSFXFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddSFXFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddSFXFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddSFXFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddSFXFrame dialog = new AddSFXFrame(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable tblSFXList;
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
    }
}