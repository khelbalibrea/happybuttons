/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package happybuttons;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

// @author Michael Balibrea (khel) &

public final class MainFrame extends javax.swing.JFrame {
    public Image icon;
    
    // Globals
    public static int bgmVolumeLink = 0;
    public static DefaultListModel blist = new DefaultListModel();
    public static DefaultListModel slist = new DefaultListModel();
    public static int draggedList = -1; // -1 not selected, 0 bgm, 1 sfx
    public static int errorOccurred = 0;
    
    // globals for media playing
    public static int playing1 = 0, playing2 = 0, pause1 = 0, pause2 = 0;
    public AudioInputStream media1;
    public static Clip clipBGM1 = null;
    public static Clip clipBGM2 = null;
    public static Clip clipR1SFX01 = null, clipR1SFX02 = null;
    public static int lastFrame1 = 0;
    public static int lastFrame2 = 0;
    
    FloatControl fcBGM1;
    FloatControl fcBGM2;
    FloatControl fcSFX;
    float bgmVol1 = 100f;
    float bgmVol2 = 100f;
    float sfxVol = 100f;
    
    // Jlist
    File bfolder = new File(HappyButtons.documentsPath + "/HappyButtons/bg/");
    File sfolder = new File(HappyButtons.documentsPath + "/HappyButtons/sfx/");
    String selectedBGMItem = "";
    String selectedSFXItem = "";
    
    // Profiles
    Profile profile = new Profile();
    public static String profileName1 = "Sample", profileName2 = "", profileName3 = "", profileName4 = "", profileName5 = "";
    public static String sfxGroupName1 = "";
    
    public MainFrame() {
        initComponents();
        
        super.setTitle("Happy Buttons");
        setSize(1366, 768);
        
        if(!HappyButtons.firstCheck.equals("")) {
            tfLastOperation.setText(HappyButtons.firstCheck);
        }
        
        if(HappyButtons.mainFolderChk == 1) {
            tfLastOperation.setText("[SYSTEM] Some system file resources are missing...");
        }
        
        if(HappyButtons.bgFolderChk == 1) {
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                "\"" + HappyButtons.documentsPath + "\\HappyButtons\\bg\" folder not found\n\n\"bg\" folder is created.\nNote that bg sounds involve in some profile saves may gone missing", 
                "CRITICAL FOLDER MISSING", 
                JOptionPane.WARNING_MESSAGE);
            
            HappyButtons.bgFolderChk = 0;
        }
        
        if(HappyButtons.sfxFolderChk == 1) {
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                "\"" + HappyButtons.documentsPath + "\\HappyButtons\\sfx\" folder not found\n\n\"sfx\" folder is created.\nNote that sfx sounds involve in some profile saves may gone missing", 
                "CRITICAL FOLDER MISSING", 
                JOptionPane.WARNING_MESSAGE);
            
            HappyButtons.sfxFolderChk = 0;
        }
        
        // set element icons
        String btnBGMCancelIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\cancel_12px.png");
        btnClearBGM1.setIcon(new javax.swing.ImageIcon(btnBGMCancelIcon));
        btnClearBGM2.setIcon(new javax.swing.ImageIcon(btnBGMCancelIcon));
        
        String btnBGMStopIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\stop_12px.png");
        btnStopBGM1.setIcon(new javax.swing.ImageIcon(btnBGMStopIcon));
        btnStopBGM2.setIcon(new javax.swing.ImageIcon(btnBGMStopIcon));
        
        String btnBGMPlayPauseIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
        btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnBGMPlayPauseIcon));
        btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnBGMPlayPauseIcon));
        
        String btnAddBGMIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\add_bgm_12px.png");
        btnAddBGM.setIcon(new javax.swing.ImageIcon(btnAddBGMIcon));
        
        String btnAddSFXIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\add_sfx_12px.png");
        btnAddSFX.setIcon(new javax.swing.ImageIcon(btnAddSFXIcon));
        
        String btnDeleteBGMIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\delete_bgm_14px.png");
        btnDeleteBGM.setIcon(new javax.swing.ImageIcon(btnDeleteBGMIcon));
        
        String btnDeleteSFXIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\delete_sfx_14px.png");
        btnDeleteSFX.setIcon(new javax.swing.ImageIcon(btnDeleteSFXIcon));
        
        String btnEditSFXIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\edit_sfx_14px.png");
        btnEditSFX.setIcon(new javax.swing.ImageIcon(btnEditSFXIcon));
        
        String itmNewIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\new_workspace_12px.png");
        itmNew.setIcon(new javax.swing.ImageIcon(itmNewIcon));
        
        String itmSaveIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\save_workspace_12px.png");
        itmSave.setIcon(new javax.swing.ImageIcon(itmSaveIcon));
        
        String btnSFXIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave_black_14px.png");
        btnR1SFX01.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR1SFX02.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR1SFX03.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR1SFX04.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR1SFX05.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR1SFX06.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR1SFX07.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR1SFX08.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR1SFX09.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR1SFX10.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR1SFX11.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        
        // set frame icon
        ImageIcon imgIcon = new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png"));
        setIconImage(imgIcon.getImage());
        // ------------------------------ >>
        
        listBGM.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        listSFX.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        listBGM.setBorder(BorderFactory.createTitledBorder("BGM"));
        listSFX.setBorder(BorderFactory.createTitledBorder("SFX"));
        
        // Transfer Handlers initializations
        listBGM.setTransferHandler(new DnDListBGM());
        listSFX.setTransferHandler(new DnDListSFX());
        tfBGM1.setTransferHandler(new DnDBGM1Textfield());
        tfBGM2.setTransferHandler(new DnDBGM2Textfield());
        lblR1SFX01.setTransferHandler(new DnDSFXLabels());
        lblR1SFX02.setTransferHandler(new DnDSFXLabels());
        lblR1SFX03.setTransferHandler(new DnDSFXLabels());
        lblR1SFX04.setTransferHandler(new DnDSFXLabels());
        lblR1SFX05.setTransferHandler(new DnDSFXLabels());
        lblR1SFX06.setTransferHandler(new DnDSFXLabels());
        lblR1SFX07.setTransferHandler(new DnDSFXLabels());
        lblR1SFX08.setTransferHandler(new DnDSFXLabels());
        lblR1SFX09.setTransferHandler(new DnDSFXLabels());
        lblR1SFX10.setTransferHandler(new DnDSFXLabels());
        lblR1SFX11.setTransferHandler(new DnDSFXLabels());
        
        blistFilesForFolder(bfolder);
        slistFilesForFolder(sfolder);
        
        tfLastOperation.setBackground(Color.WHITE);
        
        listBGM.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if(!arg0.getValueIsAdjusting()) {
                  selectedBGMItem = listBGM.getSelectedValue();
                }
            }
        });
        
        listSFX.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if(!arg0.getValueIsAdjusting()) {
                  selectedSFXItem = listSFX.getSelectedValue();
                }
            }
        });
        
        tfBGM1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Stop the event from propagating.
                }
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                e.consume();
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                e.consume();
            }
        });
        
        tfBGM2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Stop the event from propagating.
                }
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                e.consume();
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                e.consume();
            }
        });
        
        //---------------------------------------------------------------------------------------------------------------- PLAY BGM1 -->
        btnPlayPauseBGM1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clipBGM1 == null) {
                    if(tfBGM1.getText().isEmpty()){
                        JOptionPane.showMessageDialog(HappyButtons.mf, 
                                    "Nothing to play", 
                                    "Empty", 
                                    JOptionPane.WARNING_MESSAGE);
                        
                        errorOccurred = 1;
                    }
                    else {
                        try {
                            String musicPath = bfolder + "\\" + tfBGM1.getText() + ".wav";
                            loadClipBGM1(new File(musicPath));
                            fcBGM1 = (FloatControl)clipBGM1.getControl(FloatControl.Type.MASTER_GAIN);
                            if(volBGM1.getValue() == 100) {
                                fcBGM1.setValue(6f);
                            }
                            else {
                                fcBGM1.setValue(bgmVol1); // float value
                            }
                            
                            clipBGM1.start();
                        }
                        catch(IOException ioe){
                            JOptionPane.showMessageDialog(HappyButtons.mf, 
                                    "Specified BGM may be gone missing or suddenly deleted.\nIf not, inform the developer for this bug", 
                                    "File IO exception occurred", 
                                    JOptionPane.ERROR_MESSAGE);

                            playing1 = 0;
                            pause1 = 0;
                            errorOccurred = 1;

                            String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                            btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
                        }
                        catch(LineUnavailableException lue){
                            JOptionPane.showMessageDialog(HappyButtons.mf, 
                                    "LineUnavailableException error has occured. Please inform the developer", 
                                    "Line Unavailable Exception", 
                                    JOptionPane.ERROR_MESSAGE);

                            playing1 = 0;
                            pause1 = 0;
                            errorOccurred = 1;

                            String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                            btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
                        }
                        catch(UnsupportedAudioFileException uafe){
                            JOptionPane.showMessageDialog(HappyButtons.mf, 
                                    "BGM file may be broken/corrupted. Or make sure the audio file has genuine wav format.\nChanging the file extension by renaming it will NOT do the trick", 
                                    "Unsupported file", 
                                    JOptionPane.ERROR_MESSAGE);

                            playing1 = 0;
                            pause1 = 0;
                            errorOccurred = 1;

                            String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                            btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
                        }
                    }
                }
                else {
                    if(clipBGM1.isRunning()) {
                        lastFrame1 = clipBGM1.getFramePosition();
                        clipBGM1.stop();
                    }
                    else {
                        if(lastFrame1 < clipBGM1.getFrameLength()) {
                            clipBGM1.setFramePosition(lastFrame1);
                        }
                        else {
                            clipBGM1.setFramePosition(0);
                        }
                        clipBGM1.start();
                    }

                }
                
                if(errorOccurred == 0){
                    if(playing1 == 0 && pause1 == 0){
                        playing1 = 1;

                        String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                        btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
            //            System.out.println("Play: " + playing1 + ", Pause: " + pause1);
                    }
                    else if(playing1 == 1 && pause1 == 0){
                        pause1 = 1;

                        String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                        btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
            //            System.out.println("Play: " + playing1 + ", Pause: " + pause1);
                    }
                    else if(playing1 == 1 && pause1 == 1){
                        pause1 = 0;

                        String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                        btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
            //            System.out.println("Play: " + playing1 + ", Pause: " + pause1);
                    }
                    
                }
                else {
                    errorOccurred = 0;
                }
            }
        });
        
        //---------------------------------------------------------------------------------------------------------------- PLAY BGM2 -->
        btnPlayPauseBGM2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clipBGM2 == null) {
                    if(tfBGM2.getText().isEmpty()){
                        JOptionPane.showMessageDialog(HappyButtons.mf, 
                                    "Nothing to play", 
                                    "Empty", 
                                    JOptionPane.WARNING_MESSAGE);
                        
                        errorOccurred = 1;
                    }
                    else {
                        try {
                            String musicPath = bfolder + "\\" + tfBGM2.getText() + ".wav";
                            loadClipBGM2(new File(musicPath));
                            fcBGM2 = (FloatControl)clipBGM2.getControl(FloatControl.Type.MASTER_GAIN);
                            if(volBGM2.getValue() == 100) {
                                fcBGM2.setValue(6f);
                            }
                            else {
                                fcBGM2.setValue(bgmVol2); // float value
                            }
                            
                            clipBGM2.start();
                        }
                        catch(IOException ioe){
                            JOptionPane.showMessageDialog(HappyButtons.mf, 
                                    "Specified BGM may be gone missing or suddenly deleted.\nIf not, inform the developer for this bug", 
                                    "File IO exception occured", 
                                    JOptionPane.ERROR_MESSAGE);

                            playing2 = 0;
                            pause2 = 0;
                            errorOccurred = 1;

                            String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                            btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
                        }
                        catch(LineUnavailableException lue){
                            JOptionPane.showMessageDialog(HappyButtons.mf, 
                                    "LineUnavailableException error has occured. Please inform the developer", 
                                    "Line Unavailable Exception", 
                                    JOptionPane.ERROR_MESSAGE);

                            playing2 = 0;
                            pause2 = 0;
                            errorOccurred = 1;

                            String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                            btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
                        }
                        catch(UnsupportedAudioFileException uafe){
                            JOptionPane.showMessageDialog(HappyButtons.mf, 
                                    "BGM file may be broken/corrupted. Or make sure the audio file has genuine wav format.\nChanging the file extension by renaming it will NOT do the trick", 
                                    "Unsupported file", 
                                    JOptionPane.ERROR_MESSAGE);

                            playing2 = 0;
                            pause2 = 0;
                            errorOccurred = 1;

                            String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                            btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
                        }
                    }
                }
                else {
                    if(clipBGM2.isRunning()) {
                        lastFrame2 = clipBGM2.getFramePosition();
                        clipBGM2.stop();
                    }
                    else {
                        if(lastFrame2 < clipBGM2.getFrameLength()) {
                            clipBGM2.setFramePosition(lastFrame2);
                        }
                        else {
                            clipBGM2.setFramePosition(0);
                        }
                        clipBGM2.start();
                    }

                }
                
                if(errorOccurred == 0){
                    if(playing2 == 0 && pause2 == 0){
                        playing2 = 1;

                        String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                        btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
            //            System.out.println("Play: " + playing1 + ", Pause: " + pause1);
                    }
                    else if(playing2 == 1 && pause2 == 0){
                        pause2 = 1;

                        String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                        btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
            //            System.out.println("Play: " + playing1 + ", Pause: " + pause1);
                    }
                    else if(playing2 == 1 && pause2 == 1){
                        pause2 = 0;

                        String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                        btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
            //            System.out.println("Play: " + playing1 + ", Pause: " + pause1);
                    }
                    
                }
                else {
                    errorOccurred = 0;
                }
            }
        });
        
        //---------------------------------------------------------------------------------------------------------------- STOP BGM1 -->
        btnStopBGM1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clipBGM1 != null) {
                    lastFrame1 = 0;
                    clipBGM1.stop();
                    
                    String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                    btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
                    
                    playing1 = 0;
                    pause1 = 0;
                }
            }
        });
        
        //---------------------------------------------------------------------------------------------------------------- STOP BGM2 -->
        btnStopBGM2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clipBGM2 != null) {
                    lastFrame2 = 0;
                    clipBGM2.stop();
                    
                    String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                    btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
                    
                    playing2 = 0;
                    pause2 = 0;
                }
            }
        });
        
        //---------------------------------------------------------------------------------------------------------------- CLEAR BGM1 -->
        btnClearBGM1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!tfBGM1.getText().isEmpty()){
                    if(clipBGM1 != null) {
                        lastFrame1 = 0;
                        clipBGM1.stop();
                    }
                    clipBGM1 = null;

                    String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                    btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));

                    playing1 = 0;
                    pause1 = 0;

                    tfLastOperation.setText("[Clear] BGM1: " + tfBGM1.getText());
                    tfBGM1.setText("");
                }
            }
        });
        
        //---------------------------------------------------------------------------------------------------------------- CLEAR BGM2 -->
        btnClearBGM2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!tfBGM2.getText().isEmpty()){
                    if(clipBGM2 != null) {
                        lastFrame2 = 0;
                        clipBGM2.stop();
                    }
                    clipBGM2 = null;

                    String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                    btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));

                    playing2 = 0;
                    pause2 = 0;

                    tfLastOperation.setText("[Clear] BGM2: " + tfBGM2.getText());
                    tfBGM2.setText("");
                }
            }
        });
        
        //---------------------------------------------------------------------------------------------------------------- BGM VOL1 -->
        volBGM1.addChangeListener(new ChangeListener() {
            @Override
            
            public void stateChanged(ChangeEvent e) {
                float f = (float)volBGM1.getValue();
                
                try {
                    if(f >= 50) {
                        bgmVol1 = 6 - ((100f - f)*(0.22f));
                    }

                    if(f < 50 && f >= 25) {
                        bgmVol1 = -5 + (50f - f)*(-0.4f);
                    }

                    if(f < 25 && f >= 10) {
                        bgmVol1 = -15 + (25f - f)*(-0.66f);
                    }

                    if(f < 10) {
                        bgmVol1 = -25 + (10f - f)*(-5.5f);
                    }

                    if(playing1 == 1) {
                        fcBGM1.setValue(bgmVol1); // float value
                    }
                    else {
                        fcBGM1.setValue(bgmVol1); // float value
                    }
                }
                catch(Exception ex) {
                    
                }
                
                if(bgmVolumeLink == 1) {
                    int value = (int)volBGM1.getValue();
                    volBGM2.setValue(100 - value);
                }
                
                lblVolBGM1.setText("Vol1: " + Integer.toString((int)volBGM1.getValue()));
            }
        });
        
        //---------------------------------------------------------------------------------------------------------------- BGM VOL2 -->
        volBGM2.addChangeListener(new ChangeListener() {
            @Override
            
            public void stateChanged(ChangeEvent e) {
                float f = (float)volBGM2.getValue();
                
                try {
                    if(f >= 50) {
                        bgmVol2 = 6 - ((100f - f)*(0.22f));
                    }

                    if(f < 50 && f >= 25) {
                        bgmVol2 = -5 + (50f - f)*(-0.4f);
                    }

                    if(f < 25 && f >= 10) {
                        bgmVol2 = -15 + (25f - f)*(-0.66f);
                    }

                    if(f < 10) {
                        bgmVol2 = -25 + (10f - f)*(-5.5f);
                    }

                    if(playing2 == 1) {
                        fcBGM2.setValue(bgmVol2); // float value
                    }
                    else {
                        fcBGM2.setValue(bgmVol2); // float value
                    }
                }
                catch(Exception ex) {
                    
                }
                
                if(bgmVolumeLink == 1) {
                    int value = (int)volBGM2.getValue();
                    volBGM1.setValue(100 - value);
                }
                
                lblVolBGM2.setText("Vol2: " + Integer.toString((int)volBGM2.getValue()));
            }
        });
        
        //---------------------------------------------------------------------------------------------------------------- SFX VOL -->
        volSFX.addChangeListener(new ChangeListener() {
            @Override
            
            public void stateChanged(ChangeEvent e) {
                float f = (float)volSFX.getValue();
                
                try {
                    if(f >= 50) {
                        sfxVol = 6 - ((100f - f)*(0.22f));
                    }

                    if(f < 50 && f >= 25) {
                        sfxVol = -5 + (50f - f)*(-0.4f);
                    }

                    if(f < 25 && f >= 10) {
                        sfxVol = -15 + (25f - f)*(-0.66f);
                    }

                    if(f < 10) {
                        sfxVol = -25 + (10f - f)*(-5.5f);
                    }

                    fcSFX.setValue(sfxVol); // float value
//                    if(playing2 == 1) {
//                        fcBGM2.setValue(bgmVol2); // float value
//                    }
//                    else {
//                        fcBGM2.setValue(bgmVol2); // float value
//                    }
                }
                catch(Exception ex) {
                    
                }
                
//                if(bgmVolumeLink == 1) {
//                    int value = (int)volBGM2.getValue();
//                    volBGM1.setValue(100 - value);
//                }
                
                lblVolSFX.setText("SFX Vol: " + Integer.toString((int)volSFX.getValue()));
            }
        });
        
        //---------------------------------------------------------------------------------------------------------------- SFX Grp 1 -->
        tfSFXGroup1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfSFXGroup1.setEditable(false);
                sfxGroupName1 = tfSFXGroup1.getText();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelJList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listBGM = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        listSFX = new javax.swing.JList<>();
        btnAddBGM = new javax.swing.JButton();
        btnAddSFX = new javax.swing.JButton();
        lblDeleteSFX = new javax.swing.JLabel();
        btnDeleteBGM = new javax.swing.JButton();
        btnDeleteSFX = new javax.swing.JButton();
        panelRow1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfBGM1 = new javax.swing.JTextField();
        btnClearBGM1 = new javax.swing.JButton();
        btnStopBGM1 = new javax.swing.JButton();
        btnPlayPauseBGM1 = new javax.swing.JButton();
        volBGM1 = new javax.swing.JSlider();
        lblVolBGM1 = new javax.swing.JLabel();
        panelRow2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tfBGM2 = new javax.swing.JTextField();
        btnClearBGM2 = new javax.swing.JButton();
        btnStopBGM2 = new javax.swing.JButton();
        btnPlayPauseBGM2 = new javax.swing.JButton();
        volBGM2 = new javax.swing.JSlider();
        lblVolBGM2 = new javax.swing.JLabel();
        panelRow3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tfLastOperation = new javax.swing.JTextField();
        togLinkBGMVol = new javax.swing.JToggleButton();
        lblLinkBGMVolumes = new javax.swing.JLabel();
        panelRow6 = new javax.swing.JPanel();
        btnEditSFX = new javax.swing.JButton();
        lblVolSFX = new javax.swing.JLabel();
        volSFX = new javax.swing.JSlider();
        jSeparator1 = new javax.swing.JSeparator();
        panelRight = new javax.swing.JPanel();
        panelSFX1 = new javax.swing.JPanel();
        tfSFXGroup1 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        panelSFXGroup1 = new javax.swing.JPanel();
        panelR1S01 = new javax.swing.JPanel();
        btnR1SFX01 = new javax.swing.JButton();
        lblR1SFX01 = new javax.swing.JLabel();
        panelR1S02 = new javax.swing.JPanel();
        btnR1SFX02 = new javax.swing.JButton();
        lblR1SFX02 = new javax.swing.JLabel();
        panelR1S04 = new javax.swing.JPanel();
        btnR1SFX04 = new javax.swing.JButton();
        lblR1SFX04 = new javax.swing.JLabel();
        panelR1S03 = new javax.swing.JPanel();
        btnR1SFX03 = new javax.swing.JButton();
        lblR1SFX03 = new javax.swing.JLabel();
        panelR1S08 = new javax.swing.JPanel();
        btnR1SFX08 = new javax.swing.JButton();
        lblR1SFX08 = new javax.swing.JLabel();
        panelR1S05 = new javax.swing.JPanel();
        btnR1SFX05 = new javax.swing.JButton();
        lblR1SFX05 = new javax.swing.JLabel();
        panelR1S07 = new javax.swing.JPanel();
        btnR1SFX07 = new javax.swing.JButton();
        lblR1SFX07 = new javax.swing.JLabel();
        panelR1S06 = new javax.swing.JPanel();
        btnR1SFX06 = new javax.swing.JButton();
        lblR1SFX06 = new javax.swing.JLabel();
        panelR1S09 = new javax.swing.JPanel();
        btnR1SFX09 = new javax.swing.JButton();
        lblR1SFX09 = new javax.swing.JLabel();
        panelR1S10 = new javax.swing.JPanel();
        btnR1SFX10 = new javax.swing.JButton();
        lblR1SFX10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnR1SFX11 = new javax.swing.JButton();
        lblR1SFX11 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        itmNew = new javax.swing.JMenuItem();
        itmSave = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1366, 700));
        setMinimumSize(new java.awt.Dimension(1366, 700));
        setResizable(false);
        setSize(new java.awt.Dimension(1366, 733));

        panelJList.setPreferredSize(new java.awt.Dimension(1354, 180));

        listBGM.setAutoscrolls(false);
        listBGM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        listBGM.setDragEnabled(true);
        listBGM.setMaximumSize(new java.awt.Dimension(170, 673));
        listBGM.setMinimumSize(new java.awt.Dimension(170, 673));
        listBGM.setName(""); // NOI18N
        listBGM.setPreferredSize(new java.awt.Dimension(170, 673));
        listBGM.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                listBGMFocusLost(evt);
            }
        });
        listBGM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                listBGMMouseExited(evt);
            }
        });
        jScrollPane1.setViewportView(listBGM);

        listSFX.setAutoscrolls(false);
        listSFX.setDragEnabled(true);
        listSFX.setMaximumSize(new java.awt.Dimension(180, 673));
        listSFX.setMinimumSize(new java.awt.Dimension(180, 673));
        listSFX.setPreferredSize(new java.awt.Dimension(180, 673));
        jScrollPane2.setViewportView(listSFX);

        btnAddBGM.setToolTipText("Add BGM from your file");
        btnAddBGM.setMaximumSize(new java.awt.Dimension(22, 22));
        btnAddBGM.setMinimumSize(new java.awt.Dimension(22, 22));
        btnAddBGM.setPreferredSize(new java.awt.Dimension(22, 22));
        btnAddBGM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBGMActionPerformed(evt);
            }
        });

        btnAddSFX.setToolTipText("Add SFX from your file");
        btnAddSFX.setMaximumSize(new java.awt.Dimension(22, 22));
        btnAddSFX.setMinimumSize(new java.awt.Dimension(22, 22));
        btnAddSFX.setPreferredSize(new java.awt.Dimension(22, 22));
        btnAddSFX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSFXActionPerformed(evt);
            }
        });

        lblDeleteSFX.setToolTipText("Drag here from SFX list to delete");
        lblDeleteSFX.setMaximumSize(new java.awt.Dimension(22, 22));
        lblDeleteSFX.setMinimumSize(new java.awt.Dimension(22, 22));
        lblDeleteSFX.setPreferredSize(new java.awt.Dimension(22, 22));

        btnDeleteBGM.setToolTipText("Delete selected BGM from list");
        btnDeleteBGM.setMaximumSize(new java.awt.Dimension(22, 22));
        btnDeleteBGM.setMinimumSize(new java.awt.Dimension(22, 22));
        btnDeleteBGM.setPreferredSize(new java.awt.Dimension(22, 22));
        btnDeleteBGM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteBGMActionPerformed(evt);
            }
        });

        btnDeleteSFX.setToolTipText("Delete selected SFX from list");
        btnDeleteSFX.setMaximumSize(new java.awt.Dimension(22, 22));
        btnDeleteSFX.setMinimumSize(new java.awt.Dimension(22, 22));
        btnDeleteSFX.setPreferredSize(new java.awt.Dimension(22, 22));
        btnDeleteSFX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSFXActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelJListLayout = new javax.swing.GroupLayout(panelJList);
        panelJList.setLayout(panelJListLayout);
        panelJListLayout.setHorizontalGroup(
            panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJListLayout.createSequentialGroup()
                .addGroup(panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelJListLayout.createSequentialGroup()
                        .addComponent(btnAddBGM, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteBGM, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelJListLayout.createSequentialGroup()
                        .addComponent(btnAddSFX, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteSFX, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDeleteSFX, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)))
        );
        panelJListLayout.setVerticalGroup(
            panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJListLayout.createSequentialGroup()
                .addGroup(panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnAddBGM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddSFX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDeleteSFX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeleteBGM, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeleteSFX, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("BGM1:");

        tfBGM1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tfBGM1.setFocusable(false);
        tfBGM1.setName("tfBGM1"); // NOI18N

        btnClearBGM1.setToolTipText("Clear BGM1 and stop");
        btnClearBGM1.setMaximumSize(new java.awt.Dimension(22, 22));
        btnClearBGM1.setMinimumSize(new java.awt.Dimension(22, 22));
        btnClearBGM1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearBGM1ActionPerformed(evt);
            }
        });

        btnStopBGM1.setToolTipText("Stop BGM1");
        btnStopBGM1.setMaximumSize(new java.awt.Dimension(22, 22));
        btnStopBGM1.setMinimumSize(new java.awt.Dimension(22, 22));

        btnPlayPauseBGM1.setToolTipText("Play or pause BGM1");
        btnPlayPauseBGM1.setMaximumSize(new java.awt.Dimension(22, 22));
        btnPlayPauseBGM1.setMinimumSize(new java.awt.Dimension(22, 22));
        btnPlayPauseBGM1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayPauseBGM1ActionPerformed(evt);
            }
        });

        volBGM1.setToolTipText("BGM1 volume");
        volBGM1.setValue(100);
        volBGM1.setMaximumSize(new java.awt.Dimension(200, 20));
        volBGM1.setMinimumSize(new java.awt.Dimension(200, 20));

        lblVolBGM1.setText("Vol1: 100");
        lblVolBGM1.setMaximumSize(new java.awt.Dimension(60, 22));
        lblVolBGM1.setMinimumSize(new java.awt.Dimension(60, 22));
        lblVolBGM1.setPreferredSize(new java.awt.Dimension(60, 22));

        javax.swing.GroupLayout panelRow1Layout = new javax.swing.GroupLayout(panelRow1);
        panelRow1.setLayout(panelRow1Layout);
        panelRow1Layout.setHorizontalGroup(
            panelRow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRow1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClearBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblVolBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(volBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPlayPauseBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnStopBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRow1Layout.setVerticalGroup(
            panelRow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRow1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPlayPauseBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStopBGM1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRow1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelRow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(tfBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnClearBGM1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(volBGM1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblVolBGM1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        jLabel4.setText("BGM2:");

        tfBGM2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tfBGM2.setFocusable(false);
        tfBGM2.setName("tfBGM1"); // NOI18N

        btnClearBGM2.setToolTipText("Clear BGM1 and stop");
        btnClearBGM2.setMaximumSize(new java.awt.Dimension(22, 22));
        btnClearBGM2.setMinimumSize(new java.awt.Dimension(22, 22));
        btnClearBGM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearBGM2ActionPerformed(evt);
            }
        });

        btnStopBGM2.setToolTipText("Stop BGM1");
        btnStopBGM2.setMaximumSize(new java.awt.Dimension(22, 22));
        btnStopBGM2.setMinimumSize(new java.awt.Dimension(22, 22));

        btnPlayPauseBGM2.setToolTipText("Play or pause BGM1");
        btnPlayPauseBGM2.setMaximumSize(new java.awt.Dimension(22, 22));
        btnPlayPauseBGM2.setMinimumSize(new java.awt.Dimension(22, 22));
        btnPlayPauseBGM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayPauseBGM2ActionPerformed(evt);
            }
        });

        volBGM2.setToolTipText("BGM1 volume");
        volBGM2.setValue(100);
        volBGM2.setMaximumSize(new java.awt.Dimension(200, 20));
        volBGM2.setMinimumSize(new java.awt.Dimension(200, 20));

        lblVolBGM2.setText("Vol2: 100");
        lblVolBGM2.setMaximumSize(new java.awt.Dimension(60, 22));
        lblVolBGM2.setMinimumSize(new java.awt.Dimension(60, 22));
        lblVolBGM2.setPreferredSize(new java.awt.Dimension(60, 22));

        javax.swing.GroupLayout panelRow2Layout = new javax.swing.GroupLayout(panelRow2);
        panelRow2.setLayout(panelRow2Layout);
        panelRow2Layout.setHorizontalGroup(
            panelRow2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRow2Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfBGM2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClearBGM2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblVolBGM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(volBGM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPlayPauseBGM2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnStopBGM2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRow2Layout.setVerticalGroup(
            panelRow2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRow2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRow2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPlayPauseBGM2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStopBGM2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRow2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelRow2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRow2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(tfBGM2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnClearBGM2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(volBGM2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblVolBGM2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        jLabel3.setText("Last Operation:");

        tfLastOperation.setEditable(false);
        tfLastOperation.setForeground(new java.awt.Color(0, 0, 0));
        tfLastOperation.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tfLastOperation.setMaximumSize(new java.awt.Dimension(22, 600));
        tfLastOperation.setMinimumSize(new java.awt.Dimension(22, 600));

        togLinkBGMVol.setText("OFF");
        togLinkBGMVol.setToolTipText("Toggle this ON when you want to inversely link BGM1 vol and BGM2 vol");
        togLinkBGMVol.setMaximumSize(new java.awt.Dimension(50, 22));
        togLinkBGMVol.setMinimumSize(new java.awt.Dimension(50, 22));
        togLinkBGMVol.setPreferredSize(new java.awt.Dimension(50, 22));
        togLinkBGMVol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                togLinkBGMVolActionPerformed(evt);
            }
        });

        lblLinkBGMVolumes.setText("Link BGM Volumes");
        lblLinkBGMVolumes.setToolTipText("");

        javax.swing.GroupLayout panelRow3Layout = new javax.swing.GroupLayout(panelRow3);
        panelRow3.setLayout(panelRow3Layout);
        panelRow3Layout.setHorizontalGroup(
            panelRow3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRow3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfLastOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLinkBGMVolumes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(togLinkBGMVol, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRow3Layout.setVerticalGroup(
            panelRow3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRow3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRow3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfLastOperation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(togLinkBGMVol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLinkBGMVolumes, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnEditSFX.setToolTipText("Edit SFX buttons");
        btnEditSFX.setMaximumSize(new java.awt.Dimension(22, 22));
        btnEditSFX.setMinimumSize(new java.awt.Dimension(22, 22));
        btnEditSFX.setPreferredSize(new java.awt.Dimension(22, 22));

        lblVolSFX.setText("SFX Vol: 100");
        lblVolSFX.setMaximumSize(new java.awt.Dimension(85, 22));
        lblVolSFX.setMinimumSize(new java.awt.Dimension(85, 22));
        lblVolSFX.setPreferredSize(new java.awt.Dimension(85, 22));

        volSFX.setToolTipText("BGM1 volume");
        volSFX.setValue(100);
        volSFX.setMaximumSize(new java.awt.Dimension(200, 20));
        volSFX.setMinimumSize(new java.awt.Dimension(200, 20));

        javax.swing.GroupLayout panelRow6Layout = new javax.swing.GroupLayout(panelRow6);
        panelRow6.setLayout(panelRow6Layout);
        panelRow6Layout.setHorizontalGroup(
            panelRow6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRow6Layout.createSequentialGroup()
                .addComponent(lblVolSFX, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(volSFX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEditSFX, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelRow6Layout.setVerticalGroup(
            panelRow6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRow6Layout.createSequentialGroup()
                .addGroup(panelRow6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditSFX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelRow6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(volSFX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblVolSFX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelRightLayout = new javax.swing.GroupLayout(panelRight);
        panelRight.setLayout(panelRightLayout);
        panelRightLayout.setHorizontalGroup(
            panelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelRightLayout.setVerticalGroup(
            panelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
        );

        tfSFXGroup1.setEditable(false);
        tfSFXGroup1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfSFXGroup1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tfSFXGroup1.setMaximumSize(new java.awt.Dimension(200, 22));
        tfSFXGroup1.setMinimumSize(new java.awt.Dimension(200, 22));
        tfSFXGroup1.setPreferredSize(new java.awt.Dimension(200, 22));
        tfSFXGroup1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfSFXGroup1MouseClicked(evt);
            }
        });
        tfSFXGroup1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSFXGroup1ActionPerformed(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        panelR1S01.setMaximumSize(new java.awt.Dimension(90, 88));
        panelR1S01.setMinimumSize(new java.awt.Dimension(90, 88));

        btnR1SFX01.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR1SFX01.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR1SFX01.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR1SFX01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR1SFX01ActionPerformed(evt);
            }
        });

        lblR1SFX01.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR1SFX01.setForeground(new java.awt.Color(0, 0, 0));
        lblR1SFX01.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR1SFX01.setText("blank");
        lblR1SFX01.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR1SFX01.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR1SFX01.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR1SFX01.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR1S01Layout = new javax.swing.GroupLayout(panelR1S01);
        panelR1S01.setLayout(panelR1S01Layout);
        panelR1S01Layout.setHorizontalGroup(
            panelR1S01Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S01Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR1SFX01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S01Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR1SFX01, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S01Layout.setVerticalGroup(
            panelR1S01Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S01Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnR1SFX01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblR1SFX01, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnR1SFX02.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR1SFX02.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR1SFX02.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR1SFX02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR1SFX02ActionPerformed(evt);
            }
        });

        lblR1SFX02.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR1SFX02.setForeground(new java.awt.Color(0, 0, 0));
        lblR1SFX02.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR1SFX02.setText("blank");
        lblR1SFX02.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR1SFX02.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR1SFX02.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR1SFX02.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR1S02Layout = new javax.swing.GroupLayout(panelR1S02);
        panelR1S02.setLayout(panelR1S02Layout);
        panelR1S02Layout.setHorizontalGroup(
            panelR1S02Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelR1S02Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(btnR1SFX02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(panelR1S02Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR1SFX02, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S02Layout.setVerticalGroup(
            panelR1S02Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S02Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnR1SFX02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblR1SFX02, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnR1SFX04.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR1SFX04.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR1SFX04.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR1SFX04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR1SFX04ActionPerformed(evt);
            }
        });

        lblR1SFX04.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR1SFX04.setForeground(new java.awt.Color(0, 0, 0));
        lblR1SFX04.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR1SFX04.setText("blank");
        lblR1SFX04.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR1SFX04.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR1SFX04.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR1SFX04.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR1S04Layout = new javax.swing.GroupLayout(panelR1S04);
        panelR1S04.setLayout(panelR1S04Layout);
        panelR1S04Layout.setHorizontalGroup(
            panelR1S04Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S04Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR1SFX04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S04Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR1SFX04, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S04Layout.setVerticalGroup(
            panelR1S04Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S04Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR1SFX04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR1SFX04, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnR1SFX03.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR1SFX03.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR1SFX03.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR1SFX03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR1SFX03ActionPerformed(evt);
            }
        });

        lblR1SFX03.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR1SFX03.setForeground(new java.awt.Color(0, 0, 0));
        lblR1SFX03.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR1SFX03.setText("blank");
        lblR1SFX03.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR1SFX03.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR1SFX03.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR1SFX03.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR1S03Layout = new javax.swing.GroupLayout(panelR1S03);
        panelR1S03.setLayout(panelR1S03Layout);
        panelR1S03Layout.setHorizontalGroup(
            panelR1S03Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S03Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR1SFX03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S03Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR1SFX03, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S03Layout.setVerticalGroup(
            panelR1S03Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S03Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR1SFX03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR1SFX03, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnR1SFX08.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR1SFX08.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR1SFX08.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR1SFX08.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR1SFX08ActionPerformed(evt);
            }
        });

        lblR1SFX08.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR1SFX08.setForeground(new java.awt.Color(0, 0, 0));
        lblR1SFX08.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR1SFX08.setText("blank");
        lblR1SFX08.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR1SFX08.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR1SFX08.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR1SFX08.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR1S08Layout = new javax.swing.GroupLayout(panelR1S08);
        panelR1S08.setLayout(panelR1S08Layout);
        panelR1S08Layout.setHorizontalGroup(
            panelR1S08Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S08Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR1SFX08, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S08Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR1SFX08, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S08Layout.setVerticalGroup(
            panelR1S08Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S08Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR1SFX08, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR1SFX08, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnR1SFX05.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR1SFX05.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR1SFX05.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR1SFX05.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR1SFX05ActionPerformed(evt);
            }
        });

        lblR1SFX05.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR1SFX05.setForeground(new java.awt.Color(0, 0, 0));
        lblR1SFX05.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR1SFX05.setText("blank");
        lblR1SFX05.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR1SFX05.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR1SFX05.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR1SFX05.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR1S05Layout = new javax.swing.GroupLayout(panelR1S05);
        panelR1S05.setLayout(panelR1S05Layout);
        panelR1S05Layout.setHorizontalGroup(
            panelR1S05Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S05Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR1SFX05, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S05Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR1SFX05, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S05Layout.setVerticalGroup(
            panelR1S05Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S05Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR1SFX05, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR1SFX05, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnR1SFX07.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR1SFX07.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR1SFX07.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR1SFX07.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR1SFX07ActionPerformed(evt);
            }
        });

        lblR1SFX07.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR1SFX07.setForeground(new java.awt.Color(0, 0, 0));
        lblR1SFX07.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR1SFX07.setText("blank");
        lblR1SFX07.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR1SFX07.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR1SFX07.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR1SFX07.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR1S07Layout = new javax.swing.GroupLayout(panelR1S07);
        panelR1S07.setLayout(panelR1S07Layout);
        panelR1S07Layout.setHorizontalGroup(
            panelR1S07Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelR1S07Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(btnR1SFX07, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(panelR1S07Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR1SFX07, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S07Layout.setVerticalGroup(
            panelR1S07Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S07Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR1SFX07, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR1SFX07, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnR1SFX06.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR1SFX06.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR1SFX06.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR1SFX06.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR1SFX06ActionPerformed(evt);
            }
        });

        lblR1SFX06.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR1SFX06.setForeground(new java.awt.Color(0, 0, 0));
        lblR1SFX06.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR1SFX06.setText("blank");
        lblR1SFX06.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR1SFX06.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR1SFX06.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR1SFX06.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR1S06Layout = new javax.swing.GroupLayout(panelR1S06);
        panelR1S06.setLayout(panelR1S06Layout);
        panelR1S06Layout.setHorizontalGroup(
            panelR1S06Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S06Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR1SFX06, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S06Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR1SFX06, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S06Layout.setVerticalGroup(
            panelR1S06Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S06Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR1SFX06, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR1SFX06, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnR1SFX09.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR1SFX09.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR1SFX09.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR1SFX09.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR1SFX09ActionPerformed(evt);
            }
        });

        lblR1SFX09.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR1SFX09.setForeground(new java.awt.Color(0, 0, 0));
        lblR1SFX09.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR1SFX09.setText("blank");
        lblR1SFX09.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR1SFX09.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR1SFX09.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR1SFX09.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR1S09Layout = new javax.swing.GroupLayout(panelR1S09);
        panelR1S09.setLayout(panelR1S09Layout);
        panelR1S09Layout.setHorizontalGroup(
            panelR1S09Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S09Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR1SFX09, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S09Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR1SFX09, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S09Layout.setVerticalGroup(
            panelR1S09Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S09Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR1SFX09, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR1SFX09, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnR1SFX10.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR1SFX10.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR1SFX10.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR1SFX10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR1SFX10ActionPerformed(evt);
            }
        });

        lblR1SFX10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR1SFX10.setForeground(new java.awt.Color(0, 0, 0));
        lblR1SFX10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR1SFX10.setText("blank");
        lblR1SFX10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR1SFX10.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR1SFX10.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR1SFX10.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR1S10Layout = new javax.swing.GroupLayout(panelR1S10);
        panelR1S10.setLayout(panelR1S10Layout);
        panelR1S10Layout.setHorizontalGroup(
            panelR1S10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S10Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(btnR1SFX10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(panelR1S10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR1SFX10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S10Layout.setVerticalGroup(
            panelR1S10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR1SFX10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR1SFX10, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnR1SFX11.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR1SFX11.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR1SFX11.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR1SFX11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR1SFX11ActionPerformed(evt);
            }
        });

        lblR1SFX11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR1SFX11.setForeground(new java.awt.Color(0, 0, 0));
        lblR1SFX11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR1SFX11.setText("blank");
        lblR1SFX11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR1SFX11.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR1SFX11.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR1SFX11.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR1SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR1SFX11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR1SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR1SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelSFXGroup1Layout = new javax.swing.GroupLayout(panelSFXGroup1);
        panelSFXGroup1.setLayout(panelSFXGroup1Layout);
        panelSFXGroup1Layout.setHorizontalGroup(
            panelSFXGroup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSFXGroup1Layout.createSequentialGroup()
                .addComponent(panelR1S01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S05, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S06, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S07, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S08, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S09, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelSFXGroup1Layout.setVerticalGroup(
            panelSFXGroup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelR1S01, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelR1S02, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelR1S03, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelR1S04, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelR1S05, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelR1S06, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelR1S07, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelR1S08, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelR1S09, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelR1S10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelSFX1Layout = new javax.swing.GroupLayout(panelSFX1);
        panelSFX1.setLayout(panelSFX1Layout);
        panelSFX1Layout.setHorizontalGroup(
            panelSFX1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSFX1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfSFXGroup1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSFXGroup1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSFX1Layout.setVerticalGroup(
            panelSFX1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(panelSFX1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(tfSFXGroup1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panelSFXGroup1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenuBar1.setName("mbrMain"); // NOI18N
        jMenuBar1.setOpaque(true);

        jMenu1.setText("File");
        jMenu1.setName("menuFile"); // NOI18N

        itmNew.setMnemonic('N');
        itmNew.setText("New workspace");
        itmNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmNewActionPerformed(evt);
            }
        });
        jMenu1.add(itmNew);

        itmSave.setMnemonic('S');
        itmSave.setText("Save workspace");
        itmSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmSaveActionPerformed(evt);
            }
        });
        jMenu1.add(itmSave);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenu2.setName("menuEdit"); // NOI18N
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelRow6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelRow1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelRow2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelRow3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(panelJList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jSeparator1)))
                    .addComponent(panelSFX1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelRow1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRow2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRow3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelJList, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(panelRow6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(55, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelSFX1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearBGM1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearBGM1ActionPerformed
        
    }//GEN-LAST:event_btnClearBGM1ActionPerformed

    private void togLinkBGMVolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togLinkBGMVolActionPerformed
        if(bgmVolumeLink == 0){
            togLinkBGMVol.setText("ON");
            
            int valueVol1 = (int)volBGM1.getValue();
            volBGM2.setValue(100 - valueVol1);
            
            bgmVolumeLink = 1;
        }
        else {
            togLinkBGMVol.setText("OFF");
            bgmVolumeLink = 0;
        }
    }//GEN-LAST:event_togLinkBGMVolActionPerformed

    private void btnPlayPauseBGM1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayPauseBGM1ActionPerformed
        
    }//GEN-LAST:event_btnPlayPauseBGM1ActionPerformed

    private void btnAddBGMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBGMActionPerformed
        JFileChooser fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("WAV File", "wav");
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(true);
        fc.showOpenDialog(HappyButtons.mf);
        
        File[] selectedFiles = fc.getSelectedFiles();
        
        for(File file : selectedFiles) {
            try {
                FileChannel src = new FileInputStream(file.getAbsolutePath()).getChannel();
                File destCheck = new File(HappyButtons.documentsPath + "\\HappyButtons\\bg\\" + file.getName());
                
                if(!destCheck.exists()) {
                    FileChannel dest = new FileOutputStream(HappyButtons.documentsPath + "\\HappyButtons\\bg\\" + file.getName()).getChannel();
                
                    src.transferTo(0,src.size(),dest);

                    src.close();
                    dest.close();
                    
                    blist.addElement(Utility.renameListName(file.getName()));
                    tfLastOperation.setText("[ADDED BGM]:: " + file.getName());
                }
            }
            catch(IOException ex) {
                System.out.println(file.getAbsolutePath());
                JOptionPane.showMessageDialog(HappyButtons.mf,
                    "Error reading/writing file",
                    "IO Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAddBGMActionPerformed

    private void btnAddSFXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSFXActionPerformed
        JFileChooser fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("WAV File","wav");
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(true);
        fc.showOpenDialog(HappyButtons.mf);
        
        File[] selectedFiles = fc.getSelectedFiles();
        
        for(File file : selectedFiles) {
            try {
                FileChannel src = new FileInputStream(file.getAbsolutePath()).getChannel();
                File destCheck = new File(HappyButtons.documentsPath + "\\HappyButtons\\sfx\\" + file.getName());
                
                if(!destCheck.exists()) {
                    FileChannel dest = new FileOutputStream(HappyButtons.documentsPath + "\\HappyButtons\\sfx\\" + file.getName()).getChannel();
                
                    src.transferTo(0,src.size(),dest);

                    src.close();
                    dest.close();
                    
                    slist.addElement(Utility.renameListName(file.getName()));
                    tfLastOperation.setText("[ADDED SFX]:: " + file.getName());
                }
            }
            catch(IOException ex) {
                System.out.println(file.getAbsolutePath());
                JOptionPane.showMessageDialog(HappyButtons.mf,
                    "Error reading/writing file",
                    "IO Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAddSFXActionPerformed

    private void btnClearBGM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearBGM2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClearBGM2ActionPerformed

    private void btnPlayPauseBGM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayPauseBGM2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPlayPauseBGM2ActionPerformed

    private void listBGMFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_listBGMFocusLost

    }//GEN-LAST:event_listBGMFocusLost

    private void listBGMMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listBGMMouseExited
        
    }//GEN-LAST:event_listBGMMouseExited

    private void btnDeleteBGMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteBGMActionPerformed
        if(selectedBGMItem != "") {
           File deleteItem = new File(HappyButtons.documentsPathDoubleSlash + "\\HappyButtons\\bg\\" + selectedBGMItem + ".wav");
            if(deleteItem.delete()) {
                tfLastOperation.setText("[DELETE BGM]:: " + selectedBGMItem);
                blist.removeElement(selectedBGMItem);
                selectedBGMItem = "";
            }
            else {
                JOptionPane.showMessageDialog(HappyButtons.mf, 
                    "An error occurred when deleting " + selectedBGMItem + ".wav", 
                    "File deletion error", 
                    JOptionPane.ERROR_MESSAGE);
                selectedBGMItem = "";
            } 
        }
        else {
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                        "Please select an item first from BGM list", 
                        "Nothing selected", 
                        JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteBGMActionPerformed

    private void btnDeleteSFXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSFXActionPerformed
        if(selectedSFXItem != "") {
           File deleteItem = new File(HappyButtons.documentsPathDoubleSlash + "\\HappyButtons\\sfx\\" + selectedSFXItem + ".wav");
            if(deleteItem.delete()) {
                tfLastOperation.setText("[DELETE SFX]:: " + selectedSFXItem);
                slist.removeElement(selectedSFXItem);
                selectedSFXItem = "";
            }
            else {
                JOptionPane.showMessageDialog(HappyButtons.mf, 
                    "An error occurred when deleting " + selectedSFXItem + ".wav", 
                    "File deletion error", 
                    JOptionPane.ERROR_MESSAGE);
                selectedSFXItem = "";
            } 
        }
        else {
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                        "Please select an item first from SFX list", 
                        "Nothing selected", 
                        JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteSFXActionPerformed

    private void btnR1SFX01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX01ActionPerformed
        if(!lblR1SFX01.getText().equals("blank")){
            try {
                String musicPath = sfolder + "\\" + Utility.cleanSFXNaming(lblR1SFX01.getText()) + ".wav";
                loadClipSFX(new File(musicPath));
                fcSFX = (FloatControl)clipR1SFX01.getControl(FloatControl.Type.MASTER_GAIN);
                if(volSFX.getValue() == 100) {
                    fcSFX.setValue(6f);
                }
                else {
                    fcSFX.setValue(sfxVol); // float value
                }

                clipR1SFX01.start();
            }
            catch(IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                System.out.println(e.toString());
            }
        }
    }//GEN-LAST:event_btnR1SFX01ActionPerformed

    private void tfSFXGroup1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfSFXGroup1MouseClicked
        if(evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            tfSFXGroup1.setEditable(true);
        }
    }//GEN-LAST:event_tfSFXGroup1MouseClicked

    private void btnR1SFX02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX02ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnR1SFX02ActionPerformed

    private void btnR1SFX03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX03ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnR1SFX03ActionPerformed

    private void btnR1SFX04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX04ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnR1SFX04ActionPerformed

    private void btnR1SFX05ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX05ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnR1SFX05ActionPerformed

    private void btnR1SFX06ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX06ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnR1SFX06ActionPerformed

    private void btnR1SFX07ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX07ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnR1SFX07ActionPerformed

    private void btnR1SFX08ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX08ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnR1SFX08ActionPerformed

    private void btnR1SFX09ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX09ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnR1SFX09ActionPerformed

    private void btnR1SFX10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnR1SFX10ActionPerformed

    private void btnR1SFX11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnR1SFX11ActionPerformed

    private void itmSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmSaveActionPerformed
        SaveFrame saveFrame = new SaveFrame(HappyButtons.mf, true);
        saveFrame.setVisible(true);
    }//GEN-LAST:event_itmSaveActionPerformed

    private void itmNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmNewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itmNewActionPerformed

    private void tfSFXGroup1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSFXGroup1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfSFXGroup1ActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddBGM;
    private javax.swing.JButton btnAddSFX;
    private javax.swing.JButton btnClearBGM1;
    private javax.swing.JButton btnClearBGM2;
    private javax.swing.JButton btnDeleteBGM;
    private javax.swing.JButton btnDeleteSFX;
    private javax.swing.JButton btnEditSFX;
    public static javax.swing.JButton btnPlayPauseBGM1;
    public static javax.swing.JButton btnPlayPauseBGM2;
    private javax.swing.JButton btnR1SFX01;
    private javax.swing.JButton btnR1SFX02;
    private javax.swing.JButton btnR1SFX03;
    private javax.swing.JButton btnR1SFX04;
    private javax.swing.JButton btnR1SFX05;
    private javax.swing.JButton btnR1SFX06;
    private javax.swing.JButton btnR1SFX07;
    private javax.swing.JButton btnR1SFX08;
    private javax.swing.JButton btnR1SFX09;
    private javax.swing.JButton btnR1SFX10;
    private javax.swing.JButton btnR1SFX11;
    private javax.swing.JButton btnStopBGM1;
    private javax.swing.JButton btnStopBGM2;
    private javax.swing.JMenuItem itmNew;
    private javax.swing.JMenuItem itmSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblDeleteSFX;
    private javax.swing.JLabel lblLinkBGMVolumes;
    public static javax.swing.JLabel lblR1SFX01;
    public static javax.swing.JLabel lblR1SFX02;
    public static javax.swing.JLabel lblR1SFX03;
    public static javax.swing.JLabel lblR1SFX04;
    public static javax.swing.JLabel lblR1SFX05;
    public static javax.swing.JLabel lblR1SFX06;
    public static javax.swing.JLabel lblR1SFX07;
    public static javax.swing.JLabel lblR1SFX08;
    public static javax.swing.JLabel lblR1SFX09;
    public static javax.swing.JLabel lblR1SFX10;
    public static javax.swing.JLabel lblR1SFX11;
    private javax.swing.JLabel lblVolBGM1;
    private javax.swing.JLabel lblVolBGM2;
    private javax.swing.JLabel lblVolSFX;
    private javax.swing.JList<String> listBGM;
    private javax.swing.JList<String> listSFX;
    private javax.swing.JPanel panelJList;
    private javax.swing.JPanel panelR1S01;
    private javax.swing.JPanel panelR1S02;
    private javax.swing.JPanel panelR1S03;
    private javax.swing.JPanel panelR1S04;
    private javax.swing.JPanel panelR1S05;
    private javax.swing.JPanel panelR1S06;
    private javax.swing.JPanel panelR1S07;
    private javax.swing.JPanel panelR1S08;
    private javax.swing.JPanel panelR1S09;
    private javax.swing.JPanel panelR1S10;
    private javax.swing.JPanel panelRight;
    private javax.swing.JPanel panelRow1;
    private javax.swing.JPanel panelRow2;
    private javax.swing.JPanel panelRow3;
    private javax.swing.JPanel panelRow6;
    private javax.swing.JPanel panelSFX1;
    private javax.swing.JPanel panelSFXGroup1;
    private javax.swing.JTextField tfBGM1;
    private javax.swing.JTextField tfBGM2;
    private javax.swing.JTextField tfLastOperation;
    private javax.swing.JTextField tfSFXGroup1;
    private javax.swing.JToggleButton togLinkBGMVol;
    private javax.swing.JSlider volBGM1;
    private javax.swing.JSlider volBGM2;
    private javax.swing.JSlider volSFX;
    // End of variables declaration//GEN-END:variables
    
    public void blistFilesForFolder(final File folder) {
        try {
            File[] bFileList = folder.listFiles();
        
            for(File f : bFileList) {
                if(Utility.getFileExtension(f.getName()).equals("wav")){
                    blist.addElement(Utility.renameListName(f.getName()));
                }
            }
        
            listBGM.setModel(blist);
        } catch(Exception e){
            blist.removeAllElements();
            blist.addElement("Error loading..");
            listBGM.setModel(blist);
            listBGM.setEnabled(false);
            
            tfLastOperation.setText("[ERROR]:: Error on loading BGM folder");
            
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                    "BGM folder might be consisting of different file format. Make sure to only add file(s) with mp3 format\n\nHowever system will proceed starting", 
                    "BGM Folder Error", 
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void slistFilesForFolder(final File folder) {
        try {
            File[] sFileList = folder.listFiles();
        
            for(File f : sFileList) {
                if(Utility.getFileExtension(f.getName()).equals("wav")){
                    slist.addElement(Utility.renameListName(f.getName()));
                }
            }
            listSFX.setModel(slist);
        } catch(Exception e){
            slist.removeAllElements();
            slist.addElement("Error loading..");
            listSFX.setModel(slist);
            listSFX.setEnabled(false);
            
            tfLastOperation.setText("[ERROR]:: Error on loading SFX folder");
            
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                    "SFX folder might be consisting of different file format. Make sure to only add file(s) with mp3 format\n\nHowever system will proceed starting", 
                    "SFX Folder Error", 
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    protected void loadClipBGM1(File audioFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        this.clipBGM1 = (Clip) AudioSystem.getLine(info);
        this.clipBGM1.open(audioStream);
    }
    
    protected void loadClipBGM2(File audioFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        this.clipBGM2 = (Clip) AudioSystem.getLine(info);
        this.clipBGM2.open(audioStream);
    }
    
    protected void loadClipSFX(File audioFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        this.clipR1SFX01 = (Clip) AudioSystem.getLine(info);
        this.clipR1SFX01.open(audioStream);
    }
}
