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
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

// @author Michael Balibrea (khel)

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
    public static int lastFrame1 = 0;
    public static int lastFrame2 = 0;
    
    // Jlist populate
    File bfolder = new File(HappyButtons.desktopPath + "/HappyButtons/bg/");
    File sfolder = new File(HappyButtons.desktopPath + "/HappyButtons/sfx/");
    
    public MainFrame() {
        initComponents();
        
        super.setTitle("Happy Buttons");
        setSize(1366, 768);
        
        if(!HappyButtons.firstCheck.equals("")) {
            tfLastOperation.setText(HappyButtons.firstCheck);
        }
        
        // set element icons
        String btnBGMCancelIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\cancel_12px.png");
        btnClearBGM1.setIcon(new javax.swing.ImageIcon(btnBGMCancelIcon));
        btnClearBGM2.setIcon(new javax.swing.ImageIcon(btnBGMCancelIcon));
        
        String btnBGMStopIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\stop_12px.png");
        btnStopBGM1.setIcon(new javax.swing.ImageIcon(btnBGMStopIcon));
        btnStopBGM2.setIcon(new javax.swing.ImageIcon(btnBGMStopIcon));
        
        String btnBGMPlayPauseIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
        btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnBGMPlayPauseIcon));
        btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnBGMPlayPauseIcon));
        
        String btnAddBGMIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\add_12px.png");
        btnAddBGM.setIcon(new javax.swing.ImageIcon(btnAddBGMIcon));
        
        String btnAddSFXIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\add_12px.png");
        btnAddSFX.setIcon(new javax.swing.ImageIcon(btnAddSFXIcon));
        
        // set frame icon
        ImageIcon imgIcon = new ImageIcon(HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png"));
        setIconImage(imgIcon.getImage());
        // ------------------------------ >>
        
        listBGM.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        listSFX.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        listBGM.setBorder(BorderFactory.createTitledBorder("BGM"));
        listSFX.setBorder(BorderFactory.createTitledBorder("SFX"));
        listBGM.setTransferHandler(new DnDListBGM());
        listSFX.setTransferHandler(new DnDListSFX());
        tfBGM1.setTransferHandler(new DnDBGM1Textfield());
        tfBGM2.setTransferHandler(new DnDBGM2Textfield());
        
        blistFilesForFolder(bfolder);
        slistFilesForFolder(sfolder);
        
        tfLastOperation.setBackground(Color.WHITE);
        
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
                            clipBGM1.start();
                        }
                        catch(IOException ioe){
                            JOptionPane.showMessageDialog(HappyButtons.mf, 
                                    "Specified BGM may be gone missing or suddenly deleted. If not, inform the developer for this bug", 
                                    "File IO exception occurred", 
                                    JOptionPane.ERROR_MESSAGE);

                            playing1 = 0;
                            pause1 = 0;
                            errorOccurred = 1;

                            String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
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

                            String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                            btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
                        }
                        catch(UnsupportedAudioFileException uafe){
                            JOptionPane.showMessageDialog(HappyButtons.mf, 
                                    "BGM file may be broken/corrupted. Or make sure the audio file has genuine wav format. Changing the file extension by renaming it will NOT do the trick", 
                                    "Unsupported file", 
                                    JOptionPane.ERROR_MESSAGE);

                            playing1 = 0;
                            pause1 = 0;
                            errorOccurred = 1;

                            String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
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

                        String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                        btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
            //            System.out.println("Play: " + playing1 + ", Pause: " + pause1);
                    }
                    else if(playing1 == 1 && pause1 == 0){
                        pause1 = 1;

                        String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                        btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
            //            System.out.println("Play: " + playing1 + ", Pause: " + pause1);
                    }
                    else if(playing1 == 1 && pause1 == 1){
                        pause1 = 0;

                        String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
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
                            clipBGM2.start();
                        }
                        catch(IOException ioe){
                            JOptionPane.showMessageDialog(HappyButtons.mf, 
                                    "Specified BGM may be gone missing or suddenly deleted. If not, inform the developer for this bug", 
                                    "File IO exception occured", 
                                    JOptionPane.ERROR_MESSAGE);

                            playing2 = 0;
                            pause2 = 0;
                            errorOccurred = 1;

                            String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
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

                            String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                            btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
                        }
                        catch(UnsupportedAudioFileException uafe){
                            JOptionPane.showMessageDialog(HappyButtons.mf, 
                                    "BGM file may be broken/corrupted. Or make sure the audio file has genuine wav format. Changing the file extension by renaming it will NOT do the trick", 
                                    "Unsupported file", 
                                    JOptionPane.ERROR_MESSAGE);

                            playing2 = 0;
                            pause2 = 0;
                            errorOccurred = 1;

                            String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
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

                        String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                        btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
            //            System.out.println("Play: " + playing1 + ", Pause: " + pause1);
                    }
                    else if(playing2 == 1 && pause2 == 0){
                        pause2 = 1;

                        String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                        btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
            //            System.out.println("Play: " + playing1 + ", Pause: " + pause1);
                    }
                    else if(playing2 == 1 && pause2 == 1){
                        pause2 = 0;

                        String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
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
                    
                    String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
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
                    
                    String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
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

                    String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
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

                    String btnIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                    btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));

                    playing2 = 0;
                    pause2 = 0;

                    tfLastOperation.setText("[Clear] BGM2: " + tfBGM2.getText());
                    tfBGM2.setText("");
                }
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

        jLabel1 = new javax.swing.JLabel();
        tfBGM1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfBGM2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfLastOperation = new javax.swing.JTextField();
        btnStopBGM1 = new javax.swing.JButton();
        btnStopBGM2 = new javax.swing.JButton();
        btnClearBGM1 = new javax.swing.JButton();
        btnClearBGM2 = new javax.swing.JButton();
        btnPlayPauseBGM1 = new javax.swing.JButton();
        btnPlayPauseBGM2 = new javax.swing.JButton();
        togLinkBGMVol = new javax.swing.JToggleButton();
        lblLinkBGMVolumes = new javax.swing.JLabel();
        volBGM1 = new javax.swing.JSlider();
        volBGM2 = new javax.swing.JSlider();
        panelJList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listBGM = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        listSFX = new javax.swing.JList<>();
        btnAddBGM = new javax.swing.JButton();
        btnAddSFX = new javax.swing.JButton();
        txtBGMPlay1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1366, 700));
        setMinimumSize(new java.awt.Dimension(1366, 700));
        setResizable(false);
        setSize(new java.awt.Dimension(1366, 733));

        jLabel1.setText("BGM1:");

        tfBGM1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tfBGM1.setFocusable(false);
        tfBGM1.setName("tfBGM1"); // NOI18N

        jLabel2.setText("BGM2:");

        tfBGM2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tfBGM2.setFocusable(false);
        tfBGM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfBGM2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Last Operation:");

        tfLastOperation.setEditable(false);
        tfLastOperation.setForeground(new java.awt.Color(0, 0, 0));
        tfLastOperation.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tfLastOperation.setMaximumSize(new java.awt.Dimension(22, 600));
        tfLastOperation.setMinimumSize(new java.awt.Dimension(22, 600));

        btnStopBGM1.setToolTipText("Stop BGM1");
        btnStopBGM1.setMaximumSize(new java.awt.Dimension(22, 22));
        btnStopBGM1.setMinimumSize(new java.awt.Dimension(22, 22));

        btnStopBGM2.setToolTipText("Stop BGM2");
        btnStopBGM2.setMaximumSize(new java.awt.Dimension(22, 22));
        btnStopBGM2.setMinimumSize(new java.awt.Dimension(22, 22));

        btnClearBGM1.setToolTipText("Clear BGM1 and stop");
        btnClearBGM1.setMaximumSize(new java.awt.Dimension(22, 22));
        btnClearBGM1.setMinimumSize(new java.awt.Dimension(22, 22));
        btnClearBGM1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearBGM1ActionPerformed(evt);
            }
        });

        btnClearBGM2.setToolTipText("Clear BGM2 and stop");
        btnClearBGM2.setMaximumSize(new java.awt.Dimension(22, 22));
        btnClearBGM2.setMinimumSize(new java.awt.Dimension(22, 22));
        btnClearBGM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearBGM2ActionPerformed(evt);
            }
        });

        btnPlayPauseBGM1.setToolTipText("Play or pause BGM1");
        btnPlayPauseBGM1.setMaximumSize(new java.awt.Dimension(22, 22));
        btnPlayPauseBGM1.setMinimumSize(new java.awt.Dimension(22, 22));
        btnPlayPauseBGM1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayPauseBGM1ActionPerformed(evt);
            }
        });

        btnPlayPauseBGM2.setToolTipText("Play or pause BGM2");
        btnPlayPauseBGM2.setMaximumSize(new java.awt.Dimension(22, 22));
        btnPlayPauseBGM2.setMinimumSize(new java.awt.Dimension(22, 22));

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

        volBGM1.setToolTipText("BGM1 volume");
        volBGM1.setMaximumSize(new java.awt.Dimension(200, 20));
        volBGM1.setMinimumSize(new java.awt.Dimension(200, 20));

        volBGM2.setToolTipText("BGM1 volume");
        volBGM2.setMaximumSize(new java.awt.Dimension(200, 20));
        volBGM2.setMinimumSize(new java.awt.Dimension(200, 20));

        panelJList.setPreferredSize(new java.awt.Dimension(1354, 180));

        listBGM.setAutoscrolls(false);
        listBGM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        listBGM.setDragEnabled(true);
        listBGM.setMaximumSize(new java.awt.Dimension(170, 673));
        listBGM.setMinimumSize(new java.awt.Dimension(170, 673));
        listBGM.setName(""); // NOI18N
        listBGM.setPreferredSize(new java.awt.Dimension(170, 673));
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

        javax.swing.GroupLayout panelJListLayout = new javax.swing.GroupLayout(panelJList);
        panelJList.setLayout(panelJListLayout);
        panelJListLayout.setHorizontalGroup(
            panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJListLayout.createSequentialGroup()
                .addGroup(panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddBGM, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelJListLayout.createSequentialGroup()
                        .addComponent(btnAddSFX, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGroup(panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAddBGM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddSFX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtBGMPlay1.setText("0:00 / 0:00");
        txtBGMPlay1.setMaximumSize(new java.awt.Dimension(60, 22));
        txtBGMPlay1.setMinimumSize(new java.awt.Dimension(60, 22));
        txtBGMPlay1.setPreferredSize(new java.awt.Dimension(60, 22));
        txtBGMPlay1.setVerifyInputWhenFocusTarget(false);

        jMenuBar1.setName("mbrMain"); // NOI18N
        jMenuBar1.setOpaque(true);

        jMenu1.setText("File");
        jMenu1.setName("menuFile"); // NOI18N
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenu2.setName("menuEdit"); // NOI18N
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tfBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tfBGM2)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnClearBGM2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(volBGM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnClearBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtBGMPlay1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(volBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfLastOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblLinkBGMVolumes)))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(togLinkBGMVol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(btnPlayPauseBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnStopBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(btnPlayPauseBGM2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnStopBGM2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelJList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnStopBGM1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tfBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnClearBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnPlayPauseBGM1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(volBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtBGMPlay1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnStopBGM2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfBGM2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPlayPauseBGM2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addComponent(btnClearBGM2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(volBGM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(togLinkBGMVol, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(tfLastOperation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblLinkBGMVolumes, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelJList, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(366, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfBGM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfBGM2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfBGM2ActionPerformed

    private void btnClearBGM1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearBGM1ActionPerformed
        
    }//GEN-LAST:event_btnClearBGM1ActionPerformed

    private void btnClearBGM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearBGM2ActionPerformed
        if(!tfBGM2.getText().isEmpty()){
            String bgm2Text = tfBGM2.getText();
            tfBGM2.setText("");

            tfLastOperation.setText("[Clear] BGM2: " + bgm2Text);
        }
    }//GEN-LAST:event_btnClearBGM2ActionPerformed

    private void togLinkBGMVolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togLinkBGMVolActionPerformed
        if(bgmVolumeLink == 0){
            togLinkBGMVol.setText("ON");
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
        FileFilter filter = new FileNameExtensionFilter("WAV File","wav");
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(true);
        fc.showOpenDialog(HappyButtons.mf);
        
        File[] selectedFiles = fc.getSelectedFiles();
        
        for(File file : selectedFiles) {
            try {
                FileChannel src = new FileInputStream(file.getAbsolutePath()).getChannel();
                File destCheck = new File(HappyButtons.desktopPath + "\\HappyButtons\\bg\\" + file.getName());
                
                if(!destCheck.exists()) {
                    FileChannel dest = new FileOutputStream(HappyButtons.desktopPath + "\\HappyButtons\\bg\\" + file.getName()).getChannel();
                
                    src.transferTo(0,src.size(),dest);

                    src.close();
                    dest.close();
                    
                    blist.addElement(Utility.renameListName(file.getName()));
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
                File destCheck = new File(HappyButtons.desktopPath + "\\HappyButtons\\sfx\\" + file.getName());
                
                if(!destCheck.exists()) {
                    FileChannel dest = new FileOutputStream(HappyButtons.desktopPath + "\\HappyButtons\\sfx\\" + file.getName()).getChannel();
                
                    src.transferTo(0,src.size(),dest);

                    src.close();
                    dest.close();
                    
                    slist.addElement(Utility.renameListName(file.getName()));
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
    public static javax.swing.JButton btnPlayPauseBGM1;
    public static javax.swing.JButton btnPlayPauseBGM2;
    private javax.swing.JButton btnStopBGM1;
    private javax.swing.JButton btnStopBGM2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblLinkBGMVolumes;
    private javax.swing.JList<String> listBGM;
    private javax.swing.JList<String> listSFX;
    private javax.swing.JPanel panelJList;
    private javax.swing.JTextField tfBGM1;
    private javax.swing.JTextField tfBGM2;
    private javax.swing.JTextField tfLastOperation;
    private javax.swing.JToggleButton togLinkBGMVol;
    private javax.swing.JLabel txtBGMPlay1;
    private javax.swing.JSlider volBGM1;
    private javax.swing.JSlider volBGM2;
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
    
    public void operateMedia1(){
        try {
            File musicPath = new File(bfolder + "\\" + tfBGM1.getText() + ".wav");
            
            media1 = AudioSystem.getAudioInputStream(musicPath);
            
            Clip clip = AudioSystem.getClip();
            clip.open(media1);
            clip.start();
            
//            mediaPlayer1 = new MediaPlayer(media1);
//            mediaPlayer1.setVolume(vol1.getValue()/100/2);

//            vol1.valueProperty().addListener((Observable observable) -> {
//                volFocus = 1;
//                
//                if(bgmLink == 1){
//                    vol2.setValue(100 - (vol1.getValue()));
//                }
//                
//                mediaPlayer1.setVolume((vol1.getValue()/100)/2);
//            });
        } catch(IOException ioe){
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                    "File IO error exceotion has occured. Please inform the developer", 
                    "IO Exception", 
                    JOptionPane.ERROR_MESSAGE);
            
            playing1 = 0;
            pause1 = 0;
            
            String btnBGMCancelIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
            btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnBGMCancelIcon));
        } catch(LineUnavailableException lue){
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                    "LineUnavailableException error has occured. Please inform the developer", 
                    "Line Unavailable Exception", 
                    JOptionPane.ERROR_MESSAGE);
            
            playing1 = 0;
            pause1 = 0;
            
            String btnBGMCancelIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
            btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnBGMCancelIcon));
        } catch(UnsupportedAudioFileException uafe){
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                    "Make sure the audio file has genuine wav format, renaming the file extension will NOT do the trick", 
                    "Unsupported file", 
                    JOptionPane.ERROR_MESSAGE);
            
            playing1 = 0;
            pause1 = 0;
            
            String btnBGMCancelIcon = HappyButtons.desktopPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
            btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnBGMCancelIcon));
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
}
