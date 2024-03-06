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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Michael Balibrea
 */
public class ResourceManagerFrame extends javax.swing.JDialog {
    DefaultTableModel model;
    String theme = HappyButtons.uiTheme;
    /**
     * Creates new form ResourceManagerFrame
     */
    public ResourceManagerFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        super.setTitle("Resources");
        initComponents();
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/3-this.getSize().height/2);
        
        // set frame icon
        ImageIcon imgIcon = new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png"));
        setIconImage(imgIcon.getImage());
        
        tblResources.setAutoCreateRowSorter(true);
        
        setupTheme();
        populateTable();
    }
    
    public void populateTable() {
        File bfolder = new File(HappyButtons.documentsPath + "/HappyButtons/bg/");
        File sfolder = new File(HappyButtons.documentsPath + "/HappyButtons/sfx/");
        
        File[] bFileList = bfolder.listFiles();
        File[] sFileList = sfolder.listFiles();
        
        model = (DefaultTableModel) tblResources.getModel();
        
        String[] arrBGM = new String[bFileList.length];
        for(File f : bFileList) {
            String bgmList = "";
            bgmList = (HappyButtons.dbo).checkBgmInProfiles(HappyButtons.profileDB, Utility.renameListName(f.getName()));
            
            model.insertRow(model.getRowCount(), new Object[]{
                Utility.renameListName(f.getName()), "BGM", bgmList
            });
        }
        
        String[] arrSFX = new String[sFileList.length];
        for(File f : sFileList) {
            String sfxList = "";
            sfxList = (HappyButtons.dbo).checkSfxInProfiles(HappyButtons.profileDB, Utility.renameListName(f.getName()));
            
            model.insertRow(model.getRowCount(), new Object[]{
                Utility.renameListName(f.getName()), "SFX", sfxList
            });
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

        tabPanel = new javax.swing.JTabbedPane();
        panelBgmSfx = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResources = new javax.swing.JTable();
        btnDeleteBS = new javax.swing.JButton();
        panelHappyLoop = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHappyLoop = new javax.swing.JTable();
        btnAddHL = new javax.swing.JButton();
        btnDeleteHL = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(700, 290));
        setMinimumSize(new java.awt.Dimension(700, 290));
        setResizable(false);

        panelBgmSfx.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblResources.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sound item", "Type", "Used in (Profile)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblResources);

        panelBgmSfx.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 594, 291));

        btnDeleteBS.setText("Delete");
        btnDeleteBS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteBSActionPerformed(evt);
            }
        });
        panelBgmSfx.add(btnDeleteBS, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 90, -1));

        tabPanel.addTab("BGM / SFX", panelBgmSfx);

        panelHappyLoop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblHappyLoop.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Video item", "Used in (Profile)"
            }
        ));
        jScrollPane2.setViewportView(tblHappyLoop);
        if (tblHappyLoop.getColumnModel().getColumnCount() > 0) {
            tblHappyLoop.getColumnModel().getColumn(0).setResizable(false);
            tblHappyLoop.getColumnModel().getColumn(1).setResizable(false);
        }

        panelHappyLoop.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 300));

        btnAddHL.setText("Add");
        panelHappyLoop.add(btnAddHL, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 90, -1));

        btnDeleteHL.setText("Delete");
        panelHappyLoop.add(btnDeleteHL, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 40, 90, -1));

        tabPanel.addTab("Happy Loop", panelHappyLoop);

        getContentPane().add(tabPanel, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setupTheme() {
        if(theme.equals("light")) {
            this.getContentPane().setBackground(new JDialog().getBackground());
            
            btnDeleteBS.setBackground(new JButton().getBackground());
            btnDeleteBS.setForeground(new JButton().getForeground());
        }
        else if(theme.equals("dark")) {
            this.getContentPane().setBackground(Color.DARK_GRAY);
            
            btnDeleteBS.setBackground(Color.GRAY);
            btnDeleteBS.setForeground(Color.WHITE);
        }
    }
    
    private void btnDeleteBSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteBSActionPerformed
        int selectedRow = tblResources.getSelectedRow();
        String selectedItem = "";
        String selectedType = "";
        File filePath = null;
        
        if(selectedRow != -1) {
            int confirmation = JOptionPane.showConfirmDialog(null, 
                    "Are you sure you want to permanently delete selected item(s)?", 
                    "Delete items", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);
            
            if(confirmation == 0) {
                selectedItem = model.getValueAt(selectedRow, 0).toString();
                selectedType = model.getValueAt(selectedRow, 1).toString();

                String str = "";
                if(selectedType.equals("BGM")) {
                    filePath = new File(HappyButtons.documentsPathDoubleSlash + "\\HappyButtons\\bg\\" + selectedItem + ".wav");
                }
                else if(selectedType.equals("SFX")) {
                    filePath = new File(HappyButtons.documentsPathDoubleSlash + "\\HappyButtons\\sfx\\" + selectedItem + ".wav");
                }

                if(filePath.delete()) {
                    model.removeRow(selectedRow);

                    if(selectedType.equals("BGM")) {
                        (MainFrame.blist).removeElement(selectedItem);

                        // gets the new list in bgm jlist
    //                    int listBGMSize = (MainFrame.listBGM).getModel().getSize();
    //                    MainFrame.strBGM = "";
    //
    //                    for(int ctr = 0; ctr < listBGMSize; ctr++){
    //                        if(ctr == 0) {
    //                            MainFrame.strBGM = (MainFrame.listBGM).getModel().getElementAt(ctr);
    //                        }
    //                        else if(ctr > 0 && ctr <= (listBGMSize - 1)) {
    //                            MainFrame.strBGM = MainFrame.strBGM + ":" + (MainFrame.listBGM).getModel().getElementAt(ctr);
    //                        }
    //                    }
    //                    
    //                    // save automatically after deletion
    //                    Profile profile = new Profile();
    //                    HappyButtons.profileDB[HappyButtons.loadedDB] = new ProfileDatabase();
    //                    (HappyButtons.dbo).saveEnvironment(HappyButtons.profileDB, profile);
                    }
                    else if(selectedType.equals("SFX")) {
                        (MainFrame.slist).removeElement(selectedItem);

                        // gets the new list in sfx jlist
    //                    int listSFXSize = (MainFrame.listSFX).getModel().getSize();
    //                    MainFrame.strSFX = "";
    //
    //                    for(int ctr = 0; ctr < listSFXSize; ctr++){
    //                        if(ctr == 0) {
    //                            MainFrame.strSFX = (MainFrame.listSFX).getModel().getElementAt(ctr);
    //                        }
    //                        else if(ctr > 0 && ctr <= (listSFXSize - 1)) {
    //                            MainFrame.strSFX = MainFrame.strSFX + ":" + (MainFrame.listSFX).getModel().getElementAt(ctr);
    //                        }
    //                    }
    //                    
    //                    // save automatically after deletion
    //                    Profile profile = new Profile();
    //                    HappyButtons.profileDB[HappyButtons.loadedDB] = new ProfileDatabase();
    //                    (HappyButtons.dbo).saveEnvironment(HappyButtons.profileDB, profile);
                    }

                    selectedItem = "";
                }
                else {
                    JOptionPane.showMessageDialog(HappyButtons.mf, 
                        "An error occurred when deleting " + selectedItem + ".wav", 
                        "File deletion error", 
                        JOptionPane.ERROR_MESSAGE);
                    selectedItem = "";
                }
            }
        }
    }//GEN-LAST:event_btnDeleteBSActionPerformed

    
    
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
            java.util.logging.Logger.getLogger(ResourceManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ResourceManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ResourceManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ResourceManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ResourceManagerFrame dialog = new ResourceManagerFrame(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAddHL;
    private javax.swing.JButton btnDeleteBS;
    private javax.swing.JButton btnDeleteHL;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelBgmSfx;
    private javax.swing.JPanel panelHappyLoop;
    private javax.swing.JTabbedPane tabPanel;
    private javax.swing.JTable tblHappyLoop;
    private javax.swing.JTable tblResources;
    // End of variables declaration//GEN-END:variables
}
