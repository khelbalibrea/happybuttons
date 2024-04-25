/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package happybuttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Michael Balibrea
 */
public class ResourceManagerFrame extends javax.swing.JDialog {
    DefaultTableModel tblModelBS, tblModelVL, tblModelMusic;
    DefaultListModel<String> listModelVL = new DefaultListModel<>();
    String theme = HappyButtons.uiTheme;
    String[] list = {};
    
    /**
     * Creates new form ResourceManagerFrame
     */
    public ResourceManagerFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        super.setTitle("Resources");
        initComponents();
        
        lblNS1.setVisible(false);
        lblNS2.setVisible(false);
        lblNS3.setVisible(false);
        lblNotif.setVisible(false);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/3-this.getSize().height/2);
        
        // set frame icon
        ImageIcon imgIcon = new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png"));
        setIconImage(imgIcon.getImage());
        
        tblResources.setAutoCreateRowSorter(true);
        
        setupTheme();
        populateBSTable();
        populateVLTable();
        loadJListVL();
        populateMp3Table();
        
//        String[] dataListVL = {};
//        listVL.addListSelectionListener(e -> {
//            var selectedIndices = listVL.getSelectedValuesList();
//            String[] selectedValues = Arrays.stream(selectedIndices)
//                    .mapToObj(i -> dataListVL[i])
//                    .toArray(String[]::new);
//
//            System.out.println("Selected Values: " + Arrays.toString(selectedValues));
//        });
    }
    
    public void populateBSTable() {
        File bfolder = new File(HappyButtons.documentsPath + "/HappyButtons/bg/");
        File sfolder = new File(HappyButtons.documentsPath + "/HappyButtons/sfx/");
        
        File[] bFileList = bfolder.listFiles();
        File[] sFileList = sfolder.listFiles();
        
        tblModelBS = (DefaultTableModel) tblResources.getModel();
        
        for(File f : bFileList) {
            String bgmList = "";
            bgmList = (HappyButtons.dbo).checkBgmInProfiles(HappyButtons.profileDB, Utility.renameListName(f.getName(), "wav"));
            
            tblModelBS.insertRow(tblModelBS.getRowCount(), new Object[]{
                Utility.renameListName(f.getName(), "wav"), "BGM", bgmList
            });
        }
        
        for(File f : sFileList) {
            String sfxList = "";
            sfxList = (HappyButtons.dbo).checkSfxInProfiles(HappyButtons.profileDB, Utility.renameListName(f.getName(), "wav"));
            
            tblModelBS.insertRow(tblModelBS.getRowCount(), new Object[]{
                Utility.renameListName(f.getName(), "wav"), "SFX", sfxList
            });
        }
    }
    
    public void populateVLTable() {
        File vlFolder = new File(HappyButtons.documentsPath + "/HappyButtons/hlvids/");
        File[] vlFileList = vlFolder.listFiles();
        
        tblModelVL = (DefaultTableModel) tblVideoLoop.getModel();
        
        for(File f : vlFileList) {
            String videoLoopList = "";
            videoLoopList = (HappyButtons.dbo).checkVideoLoopInProfiles(HappyButtons.profileDB, Utility.renameVideoName(f.getName()));
            
            tblModelVL.insertRow(tblModelVL.getRowCount(), new Object[]{
                Utility.renameVideoName(f.getName()), videoLoopList
            });
        }
    }
    
    public void populateMp3Table() {
        File mp3Folder = new File(HappyButtons.documentsPath + "/HappyButtons/mp3s/");
        File[] mp3FileList = mp3Folder.listFiles();
        
        tblModelMusic = (DefaultTableModel) tblMusic.getModel();
        
        for(File f : mp3FileList) {
            String mp3List = "";
            mp3List = (HappyButtons.dbo).checkMp3InProfiles(HappyButtons.profileDB, Utility.renameListName(f.getName(), "wav"));
            
            tblModelMusic.insertRow(tblModelMusic.getRowCount(), new Object[]{
                Utility.renameListName(f.getName(), "wav"), mp3List
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
        lblNotif = new javax.swing.JLabel();
        panelHappyLoop = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVideoLoop = new javax.swing.JTable();
        btnDeleteVL = new javax.swing.JButton();
        btnAddVL = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        listVL = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        btnAddToList = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        lblNS2 = new javax.swing.JLabel();
        lblNS1 = new javax.swing.JLabel();
        lblNS3 = new javax.swing.JLabel();
        panelMp3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblMusic = new javax.swing.JTable();
        btnDeleteMusic = new javax.swing.JButton();
        lblMusicNotif = new javax.swing.JLabel();

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

        panelBgmSfx.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 650, 340));

        btnDeleteBS.setText("Delete");
        btnDeleteBS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteBSActionPerformed(evt);
            }
        });
        panelBgmSfx.add(btnDeleteBS, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, 140, -1));

        lblNotif.setText("jLabel2");
        panelBgmSfx.add(lblNotif, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 40, 140, -1));

        tabPanel.addTab("BGM / SFX", panelBgmSfx);

        panelHappyLoop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblVideoLoop.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Video item", "Used in (Profile)"
            }
        ));
        tblVideoLoop.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane2.setViewportView(tblVideoLoop);
        if (tblVideoLoop.getColumnModel().getColumnCount() > 0) {
            tblVideoLoop.getColumnModel().getColumn(0).setResizable(false);
            tblVideoLoop.getColumnModel().getColumn(1).setResizable(false);
        }

        panelHappyLoop.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 540, 280));

        btnDeleteVL.setText("Delete");
        btnDeleteVL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteVLActionPerformed(evt);
            }
        });
        panelHappyLoop.add(btnDeleteVL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 130, -1));

        btnAddVL.setText("Add");
        btnAddVL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddVLActionPerformed(evt);
            }
        });
        panelHappyLoop.add(btnAddVL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 130, -1));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        panelHappyLoop.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 10, 350));

        jScrollPane3.setViewportView(listVL);

        panelHappyLoop.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(567, 30, 230, 290));

        jLabel1.setText("My video list");
        panelHappyLoop.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 140, -1));

        btnAddToList.setText("Add to my list >>");
        btnAddToList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToListActionPerformed(evt);
            }
        });
        panelHappyLoop.add(btnAddToList, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, 140, -1));

        btnRemove.setText("Remove from my list");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        panelHappyLoop.add(btnRemove, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 330, 150, -1));

        lblNS2.setForeground(new java.awt.Color(255, 51, 51));
        lblNS2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNS2.setText("Nothing selected");
        panelHappyLoop.add(lblNS2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 330, 110, 20));

        lblNS1.setForeground(new java.awt.Color(255, 51, 51));
        lblNS1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNS1.setText("Nothing selected");
        panelHappyLoop.add(lblNS1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 330, 110, 20));

        lblNS3.setForeground(new java.awt.Color(255, 51, 51));
        lblNS3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNS3.setText("Nothing selected");
        panelHappyLoop.add(lblNS3, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, 100, -1));

        tabPanel.addTab("Video Loop", panelHappyLoop);

        panelMp3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblMusic.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Music item", "Used in (Profile)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblMusic);

        panelMp3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 640, 350));

        btnDeleteMusic.setText("Delete");
        btnDeleteMusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteMusicActionPerformed(evt);
            }
        });
        panelMp3.add(btnDeleteMusic, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 13, 140, -1));

        lblMusicNotif.setText("jLabel2");
        panelMp3.add(lblMusicNotif, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 50, 140, -1));

        tabPanel.addTab("Music", panelMp3);

        getContentPane().add(tabPanel, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setupTheme() {
        if(theme.equals("light")) {
            this.getContentPane().setBackground(new JDialog().getBackground());
            
            btnDeleteBS.setBackground(new JButton().getBackground());
            btnDeleteBS.setForeground(new JButton().getForeground());
            
            btnDeleteMusic.setBackground(new JButton().getBackground());
            btnDeleteMusic.setForeground(new JButton().getForeground());
        }
        else if(theme.equals("dark")) {
            this.getContentPane().setBackground(Color.DARK_GRAY);
            
            btnDeleteBS.setBackground(Color.GRAY);
            btnDeleteBS.setForeground(Color.WHITE);
            
            btnDeleteMusic.setBackground(Color.GRAY);
            btnDeleteMusic.setForeground(Color.WHITE);
        }
    }
    
    public void loadJListVL() {
        if(MainFrame.loadedIndexProfile >= 0) {
            System.out.println("Loaded profile: " + MainFrame.loadedIndexProfile);
            System.out.println("Load JList: " + MainFrame.strVidLoop);
            String[] arrVL = Utility.strToArr(MainFrame.strVidLoop);
//            String goneVLs = "";
//            int vlLost = 0;
//            int numbering = 1;

            for(String vid : arrVL) {
                File destCheck = new File(HappyButtons.documentsPath + "\\HappyButtons\\hlvids\\" + vid + ".mp4");
                if(!destCheck.exists()) {
                    int removedIndex = Utility.findIndexInStrArr(arrVL, vid);
                    arrVL = Utility.removeIndexInStrArr(arrVL, removedIndex);
//                    vlLost++;
//                    if(!goneVLs.equals("")) {
//                        goneVLs = goneVLs + "(" + numbering + ") " + vid + ".mp4\n";
//                        numbering++;
//                    }
//                    else {
//                        goneVLs = "(" + numbering + ") " + vid + ".mp4\n";
//                        numbering++;
//                    }
                }
            }

            if(listModelVL != null) {
                listModelVL.removeAllElements();
            }

            for(String vid : arrVL) {
                listModelVL.addElement(vid);
            }
            
            for(int i = 0; i < listModelVL.size(); i++) {
                list = Utility.addElementInStrArr(list, listModelVL.getElementAt(i));
            }

            listVL.setModel(listModelVL);
        }
    }
    
    private void btnDeleteBSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteBSActionPerformed
        int selectedRow = tblResources.getSelectedRow();// for checking only if atleast one item is selected
        int[] selectedRows = tblResources.getSelectedRows();
        File filePath = null;
        String[] fileErrorBGM = {};
        String[] fileErrorSFX = {};
        int[] deleteRow = new int[]{};
        
        if(selectedRow != -1) {
            int confirmation = JOptionPane.showConfirmDialog(null, 
                    "Some item(s) may be used in other profiles.\nProceed to permanently delete selected item(s)?", 
                    "Delete items", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);
            
            if(confirmation == 0) {
                String selectedItem = "", selectedType = "";
                
                for(int i = 0; i < (selectedRows.length); i++) {
                    selectedItem = tblModelBS.getValueAt(selectedRows[i], 0).toString();
                    selectedType = tblModelBS.getValueAt(selectedRows[i], 1).toString();
                    
                    if(selectedType.equals("BGM")) {
                        filePath = new File(HappyButtons.documentsPathDoubleSlash + "\\HappyButtons\\bg\\" + selectedItem + ".wav");
                    }
                    else if(selectedType.equals("SFX")) {
                        filePath = new File(HappyButtons.documentsPathDoubleSlash + "\\HappyButtons\\sfx\\" + selectedItem + ".wav");
                    }

                    if(filePath.delete()) {
                        // collect first the rows to be removed in table
                        deleteRow = Utility.addElementInIntArr(deleteRow, selectedRows[i]);

                        if(selectedType.equals("BGM")) {
                            (MainFrame.blist).removeElement(selectedItem);
                        }
                        else if(selectedType.equals("SFX")) {
                            (MainFrame.slist).removeElement(selectedItem);
                            
                            Utility.blankSFXLabel(selectedItem);
                        }
                        
                        lblNotif.setVisible(true);
                        lblNotif.setForeground(Color.GREEN);
                        String str = Utility.strDoubleSlash("<html>Sucessfully removed\nChanges saved<\\html>");
                        lblNotif.setText(str);
                        labelNotif.setRepeats(false);
                        labelNotif.start();
                        
                        selectedItem = "";
                        autosave();
                    }
                    else {
                        if(selectedType == "BGM") {
                            fileErrorBGM = Utility.addElementInStrArr(fileErrorBGM, selectedItem);
                        }
                        else {
                            fileErrorSFX = Utility.addElementInStrArr(fileErrorSFX, selectedItem);
                        }
                        
                        selectedItem = ""; selectedType = "";
                    }
                }
                
                // removing the collected rows to be deleted in table 
                if(deleteRow.length > 0) {
                    for(int i = (deleteRow.length-1); i >= 0; i--) {
                        tblModelBS.removeRow(deleteRow[i]);
                    }
                    
                    deleteRow = null;
                }
                
                if(fileErrorBGM.length > 0 || fileErrorSFX.length > 0) {
                    String err = "", strBGMListDown = "", strSFXListDown = "";
                    err = (fileErrorBGM.length + fileErrorSFX.length) + " item(s)\nNote: The file may be deleted already or renamed\n";
                    int errNumbering = 1;

                    if(fileErrorBGM.length > 0) {
                        for(int ii = 0; ii < fileErrorBGM.length; ii++) {
                            strBGMListDown = strBGMListDown + "\n(" + errNumbering + ") " + fileErrorBGM[ii] + ".mp4 [BGM]";
                            errNumbering++;
                        }
                    }

                    if(fileErrorSFX.length > 0) {
                        for(int ii = 0; ii < fileErrorSFX.length; ii++) {
                            strSFXListDown = strSFXListDown + "\n(" + errNumbering + ") " + fileErrorSFX[ii] + ".mp4 [SFX]";
                            errNumbering++;
                        }
                    }
                    
                    errNumbering = 1;

                    JOptionPane.showMessageDialog(HappyButtons.mf, 
                        "Error in deleting " + err + strBGMListDown + strSFXListDown, 
                        "File deletion error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else {
            lblNotif.setVisible(true);
            lblNotif.setForeground(Color.RED);
            lblNotif.setText("Nothing selected");
            labelNotif.setRepeats(false);
            labelNotif.start();
        }
    }//GEN-LAST:event_btnDeleteBSActionPerformed

    private void btnAddVLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddVLActionPerformed
//        Object[] options = {"Add from App Resource", "Add from My PC files"};
//        
//        int choice = JOptionPane.showOptionDialog(HappyButtons.mf, "Select path where to get Video files",
//                "Get Happy Loop video source",
//                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
//        
//        if(choice == 0) {
//            AddVideoLoopFrame addVLFrame = new AddVideoLoopFrame(HappyButtons.mf, true);
//            addVLFrame.setVisible(true);
//        }
//        else {
            JFileChooser fc = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("MP4 File", "mp4");
            fc.setFileFilter(filter);
            fc.setMultiSelectionEnabled(true);
            fc.setPreferredSize(new Dimension(1000, 600));
            fc.showOpenDialog(HappyButtons.mf);

            File[] selectedFiles = fc.getSelectedFiles();

            for(File file : selectedFiles) {
                try {
                    FileChannel src = new FileInputStream(file.getAbsolutePath()).getChannel();
                    File destCheck = new File(HappyButtons.documentsPath + "\\HappyButtons\\hlvids\\" + file.getName());

                    if(!destCheck.exists()) {
                        FileChannel dest = new FileOutputStream(HappyButtons.documentsPath + "\\HappyButtons\\hlvids\\" + file.getName()).getChannel();

                        src.transferTo(0, src.size(), dest);

                        src.close();
                        dest.close();
                    }

//                    if((MainFrame.cboModel).getIndexOf(Utility.renameVideoName(file.getName())) < 0) {
//                        (MainFrame.cboModel).addElement(Utility.renameVideoName(file.getName()));
//                        (MainFrame.tfLastOperation).setText("[ADDED VIDEO]:: " + file.getName());
//                    }

                    if(Utility.searchInTableCol(tblModelVL, Utility.renameVideoName(file.getName()), 0) == false) {
                        tblModelVL.insertRow(tblModelVL.getRowCount(), new Object[]{
                            Utility.renameVideoName(file.getName()), ""
                        });
                    }
                    
                    autosave();

//                    (MainFrame.cboVidLoop).setModel(MainFrame.cboModel);
                }
                catch(IOException ex) {
                    System.out.println(file.getAbsolutePath());
                    JOptionPane.showMessageDialog(HappyButtons.mf,
                        "Error reading/writing file",
                        "IO Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
//        }
    }//GEN-LAST:event_btnAddVLActionPerformed

    private void btnDeleteVLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteVLActionPerformed
        int selectedRow = tblVideoLoop.getSelectedRow(); // for checking only if atleast one item is selected
        int[] selectedRows = tblVideoLoop.getSelectedRows();
        File filePath = null;
        String[] fileError = {};
        int[] deleteRow = new int[]{};
        
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblNS1.setVisible(false);
            }
        });
        
        if(selectedRow != -1) {
            int confirmation = JOptionPane.showConfirmDialog(null, 
                    "Some item(s) may be used in other profiles.\nProceed to permanently delete selected item(s)?", 
                    "Delete items", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);
            
            if(confirmation == 0) {
                String selectedItem = "";
                
                for(int i = 0; i < (selectedRows.length); i++) {
                    selectedItem = tblModelVL.getValueAt(selectedRows[i], 0).toString();
                    filePath = new File(HappyButtons.documentsPathDoubleSlash + "\\HappyButtons\\hlvids\\" + selectedItem + ".mp4");
                    
                    if(filePath.delete()) {
                        // collect first the rows to be removed in table
                        deleteRow = Utility.addElementInIntArr(deleteRow, selectedRows[i]);
                        
                        (MainFrame.cboModel).removeElement(selectedItem);
                        (MainFrame.cboVidLoop).removeItem(selectedItem);
                        
                        selectedItem = "";
                        autosave();
                    }
                    else {
                        fileError = Utility.addElementInStrArr(fileError, selectedItem);
                        selectedItem = "";
                    }
                }
                
                // removing the collected rows to be deleted in table 
                if(deleteRow.length > 0) {
                    for(int i = (deleteRow.length-1); i >= 0; i--) {
                        tblModelVL.removeRow(deleteRow[i]);
                    }
                    
                    deleteRow = null;
                }
                
                if(fileError.length > 0) {
                    String err = "", strListDown = "";
                    
                    err = fileError.length + " item(s)\nNote: The file may be deleted already or renamed\n";
                    strListDown = "";
                    
                    for(int i = 0; i < fileError.length; i++) {
                        strListDown = strListDown + "\n(" + (i+1) + ") " + fileError[i] + ".mp4";
                    }
                    
                    JOptionPane.showMessageDialog(HappyButtons.mf, 
                        "Error in deleting " + err + strListDown, 
                        "File deletion error", 
                        JOptionPane.ERROR_MESSAGE);
                }
                
                tblVideoLoop.setModel(tblModelVL);
            }
        }
        else {
            lblNS1.setVisible(true);
            
            timer.setRepeats(false);
            timer.start();
        }
    }//GEN-LAST:event_btnDeleteVLActionPerformed

    private void btnAddToListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToListActionPerformed
        int selectedRow = tblVideoLoop.getSelectedRow(); // for checking only if atleast one item is selected
        int[] selectedRows = tblVideoLoop.getSelectedRows();
        
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblNS2.setVisible(false);
            }
        });
        
        if(selectedRow != -1) {
            String selectedItem = "";
            
            for(int i = 0; i < (selectedRows.length); i++) {
                selectedItem = tblModelVL.getValueAt(selectedRows[i], 0).toString();
                
                if(!listModelVL.contains(selectedItem)) {
                    list = Utility.addElementInStrArr(list, selectedItem);
                    listModelVL.addElement(selectedItem);
                }
                
                if((MainFrame.cboModel).getIndexOf(selectedItem) < 0) {
                    (MainFrame.cboModel).addElement(selectedItem);
                    (MainFrame.tfLastOperation).setText("[ADDED VIDEO]:: " + selectedItem);
                }
                
                if(MainFrame.strVidLoop.equals("")) {
                    MainFrame.cboModel.removeAllElements();
                    (MainFrame.cboModel).addElement(selectedItem);
                    MainFrame.strVidLoop = selectedItem;
                }
                else {
                    MainFrame.strVidLoop = MainFrame.strVidLoop + ":" + selectedItem;
                }

                listVL.setModel(listModelVL);
            }
            autosave();
        }
        else {
            lblNS2.setVisible(true);
            
            timer.setRepeats(false);
            timer.start();
        }
        
//        MainFrame.strVidLoop = Utility.arrToStr(list);
//        System.out.println("Added to list: " + MainFrame.strVidLoop);
    }//GEN-LAST:event_btnAddToListActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        List<String> stringList = listVL.getSelectedValuesList();
        
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblNS3.setVisible(false);
            }
        });
        
        if(!stringList.isEmpty()) { // not empty
            for(int i = 0; i < stringList.size(); i++) {
//                list = Utility.addElementInStrArr(list.length, list, stringList.get(i));
//                list = Utility.removeIndexInStrArr(list, i);
                listModelVL.removeElement(stringList.get(i));
                
                (MainFrame.cboModel).removeElement(stringList.get(i));
            }
            autosave();
        }
        else {
            lblNS3.setVisible(true);
            
            timer.setRepeats(false);
            timer.start();
        }
        
        MainFrame.strVidLoop = Utility.arrToStr(list);
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnDeleteMusicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteMusicActionPerformed
        int selectedRow = tblMusic.getSelectedRow();// for checking only if atleast one item is selected
        int[] selectedRows = tblMusic.getSelectedRows();
        File filePath = null;
        String[] fileErrorMp3 = {};
        int[] deleteRow = new int[]{};
        String[] delRow = new String[]{};
        
        if(selectedRow != -1) {
            int confirmation = JOptionPane.showConfirmDialog(null, 
                    "Some item(s) may be used in other profiles.\nProceed to permanently delete selected item(s)?", 
                    "Delete items", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);
            
            if(confirmation == 0) {
                String selectedItem = "";
                
                for(int i = 0; i < (selectedRows.length); i++) {
                    selectedItem = tblModelMusic.getValueAt(selectedRows[i], 0).toString();
                    
                    filePath = new File(HappyButtons.documentsPathDoubleSlash + "\\HappyButtons\\mp3s\\" + selectedItem + ".wav");

                    if(filePath.delete()) {
                        // collect first the rows to be removed in table
                        deleteRow = Utility.addElementInIntArr(deleteRow, selectedRows[i]);
                        delRow = Utility.addElementInStrArr(delRow, selectedItem);

                        (MainFrame.mlist).removeElement(selectedItem);
                        
                        lblMusicNotif.setVisible(true);
                        lblMusicNotif.setForeground(Color.GREEN);
                        String str = Utility.strDoubleSlash("<html>Sucessfully removed\nChanges saved<\\html>");
                        lblMusicNotif.setText(str);
                        labelNotifMusic.setRepeats(false);
                        labelNotifMusic.start();
                        
                        selectedItem = "";
                        autosave();
                    }
                    else {
                        fileErrorMp3 = Utility.addElementInStrArr(fileErrorMp3, selectedItem);
                        
                        selectedItem = "";
                    }
                }
                
                // removing the collected rows to be deleted in table 
                if(deleteRow.length > 0) {
                    for(int i = (deleteRow.length-1); i >= 0; i--) {
                        if(delRow[i].equals(MainFrame.selectedMp3Item)) {
                            Mp3Frame.lblSongMp3.setText("");
                            MainFrame.tfMp3.setText("");
                            MainFrame.selectedMp3Item = "";
                            MainFrame.clipMp3.removeLineListener(MainFrame.listenMp3);
                            MainFrame.clipMp3.stop();
                            MainFrame.mp3MainQueue = Utility.removeElementInStrArr(MainFrame.mp3MainQueue, delRow[i]);
                            MainFrame.clipMp3 = null;
                            MainFrame.iconPlayMp3 = 1;
                            
                            if(HappyButtons.uiTheme.equals("light")) {
                                String btnIcon3 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_12px.png");
                                MainFrame.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon3));
                            }
                            else if(HappyButtons.uiTheme.equals("dark")) {
                                String btnIcon3 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_mp3_12px.png");
                                MainFrame.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon3));
                            }
                            
                            if(MainFrame.mp3.isShowing()) {
                                if(theme.equals("light")) {
                                    MainFrame.mp3.btnPlayPauseMp3.setBackground(new JButton().getBackground());
                                    String strIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_ui_18px.png");
                                    MainFrame.mp3.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(strIcon));
                                }
                                else if(theme.equals("dark")) {
                                    MainFrame.mp3.btnPlayPauseMp3.setBackground(Color.GRAY);
                                    String strIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_mp3_ui_18px.png");
                                    MainFrame.mp3.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(strIcon));
                                }
                            }
                        }
                        tblModelMusic.removeRow(deleteRow[i]);
                    }
                    
                    deleteRow = null;
                }
                
                if(fileErrorMp3.length > 0) {
                    String err = "", strMp3ListDown = "";
                    err = fileErrorMp3.length + " item(s)\nNote: The file may be deleted already or renamed\n";
                    int errNumbering = 1;

                    if(fileErrorMp3.length > 0) {
                        for(int ii = 0; ii < fileErrorMp3.length; ii++) {
                            strMp3ListDown = strMp3ListDown + "\n(" + errNumbering + ") " + fileErrorMp3[ii] + ".wav";
                            errNumbering++;
                        }
                    }
                    
                    errNumbering = 1;

                    JOptionPane.showMessageDialog(HappyButtons.mf, 
                        "Error in deleting " + err + strMp3ListDown, 
                        "File deletion error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else {
            lblNotif.setVisible(true);
            lblNotif.setForeground(Color.RED);
            lblNotif.setText("Nothing selected");
            labelNotif.setRepeats(false);
            labelNotif.start();
        }
    }//GEN-LAST:event_btnDeleteMusicActionPerformed
    
    public void autosave() {
        if(MainFrame.enableAutosave.equals("on")) {
            if(HappyButtons.canAutosave == 1) {
                prepareSave();
                Profile profile = new Profile();
                DBOperations.indexDB = HappyButtons.loadedDB;

                HappyButtons.profileDB[HappyButtons.loadedDB] = new ProfileDatabase();
                (HappyButtons.dbo).saveEnvironment(HappyButtons.profileDB, profile);

                visualizeSaving();
            }
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
        
        // Mp3
        int listMp3Size = MainFrame.mlist.getSize(); System.out.println(listMp3Size);
        MainFrame.strMp3List = "";
        
        for(int ctr = 0; ctr < listMp3Size; ctr++){
            if(ctr == 0) {
                MainFrame.strMp3List = MainFrame.mlist.getElementAt(ctr).toString();
            }
            else if(ctr > 0 && ctr <= (listSFXSize - 1)) {
                MainFrame.strMp3List = MainFrame.strMp3List + ":" + MainFrame.mlist.getElementAt(ctr).toString();
            }
        }
    }
    
    public void visualizeSaving() {
        Timer timer1 = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFrameTitle(2);
            }
        });
        
        Timer timer = new Timer(4000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFrameTitle(1);
            }
        });
        
        timer1.setRepeats(false);
        timer1.start();
        
        timer.setRepeats(false);
        timer.start();
    }
    
    public void setFrameTitle(int type) {
        if(type == 1) {
            super.setTitle("Resources - (" + MainFrame.savedProfile + " • Changes saved)");
        }
        else if(type == 2) {
            super.setTitle("Resources - (Saving changes..)");
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
    
    Timer labelNotif = new Timer(3000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            lblNotif.setVisible(false);
        }
    });
    
    Timer labelNotifMusic = new Timer(3000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            lblMusicNotif.setVisible(false);
        }
    });

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddToList;
    private javax.swing.JButton btnAddVL;
    private javax.swing.JButton btnDeleteBS;
    private javax.swing.JButton btnDeleteMusic;
    private javax.swing.JButton btnDeleteVL;
    private javax.swing.JButton btnRemove;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblMusicNotif;
    private javax.swing.JLabel lblNS1;
    private javax.swing.JLabel lblNS2;
    private javax.swing.JLabel lblNS3;
    private javax.swing.JLabel lblNotif;
    private javax.swing.JList<String> listVL;
    private javax.swing.JPanel panelBgmSfx;
    private javax.swing.JPanel panelHappyLoop;
    private javax.swing.JPanel panelMp3;
    private javax.swing.JTabbedPane tabPanel;
    private javax.swing.JTable tblMusic;
    private javax.swing.JTable tblResources;
    private javax.swing.JTable tblVideoLoop;
    // End of variables declaration//GEN-END:variables
}
