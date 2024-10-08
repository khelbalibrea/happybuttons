/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package happybuttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseListener;
import java.beans.VetoableChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

// @author Michael Balibrea (khel) &

public final class MainFrame extends javax.swing.JFrame implements Runnable {
    public Image icon;
    static Timer timer, timerMp;
    static MouseListener rightButton, rightButtonMp3, sliderClick, mp3FieldClick;
    public static ActionEvent actEvt;
    
    // Globals
    public static int bgmVolumeLink = 0;
    public static DefaultListModel blist = new DefaultListModel();
    public static DefaultListModel slist = new DefaultListModel();
    public static DefaultListModel mlist = new DefaultListModel();
    public static int draggedList = -1; // -1 not selected, 0 bgm, 1 sfx
    public static int errorOccurred = 0;
    public static DefaultComboBoxModel cboModelForLoop = new DefaultComboBoxModel();
    public static DefaultComboBoxModel cboModelPlaylist = new DefaultComboBoxModel();
    
    // globals for media playing
    public static int playing1 = 0, playing2 = 0, pause1 = 0, pause2 = 0, mp3Playing = 0, mp3Pause = 0;
    public static int sfxPlaying = 0;
    public AudioInputStream media1;
    public static Clip clipBGM1 = null;
    public static Clip clipBGM2 = null;
    public static Clip clipSFX = null;
    public static Clip clipMp3 = null;
    public LineListener bgm1Listener = null, bgm2Listener = null, sfxListener = null;
    public static ChangeListener mp3SliderListener = null;
    public static VetoableChangeListener frameListener;
    public static int lastFrame1 = 0, lastFrame2 = 0, mp3LastFrame = 0;
    public static int chkSinglePlay = 1, chkStopBGM = 0;
    public static int loop1 = 1, loop2 = 1, loopMp3 = 0;
    public static int mp3Audio = 1, mp3Shuffle = 1, mp3Repeat = 1; // in repeat, 0->do not repeat, 1->repeat all in list, 2->loop selected song
    static public LineListener listenBGM1, listenBGM2, listenMp3;
    public static boolean sfxOperation, mp3FrameFirstLoad = true;
    public static int[] mp3Arr = new int[]{};
    public static String[] mp3SortedQueue = new String[0];
    public static String[] mp3ShuffledQueue = new String[0];
    public static String[] mp3MainQueue = new String[0];
    public static String[] mp3Queue = new String[0];
    public static String[] vlQueue = new String[0]; // it is the queue use for sorting (either shuffle or not) and copied to vidQueue after
    public static String[] vidQueue = new String[0]; // it is the actual list to be played
    public static long mp3Frames = 0;
    public static double mp3CurrentInSeconds, mp3DurationInSeconds;
    public static String mp3PlayTime = "", mp3Duration = "";
    
    static FloatControl fcBGM1, fcBGM2, fcSFX, fcMp3;
    static float bgmVol1 = 100f, bgmVol2 = 100f, sfxVol = 100f, mp3Vol = 100f;
    public static int iconPlayMp3 = 1, iconVolMp3 = 100, btnNext = 0, mp3VolumeValue = 100;
    public static int videoMainVolume = 100;
    
    // Jlist
    File bfolder = new File(HappyButtons.documentsPath + "/HappyButtons/bg/");
    File sfolder = new File(HappyButtons.documentsPath + "/HappyButtons/sfx/");
    public static String selectedBGMItem = "", selectedSFXItem = "", selectedMp3Item = "", selectedLoopVideoItem = "", selectedPlaylistVideoItem = "";
    public static String selectedVideoItem = "";
    
    // Profiles
    public static String profileName1 = "", profileName2 = "", profileName3 = "", profileName4 = "", profileName5 = "";
    public static String loadedProfile = "", savedProfile = "", savingProfile = "", strBGM = "", strSFX = "", strVidLoop = "", strVidList = "", strMp3List = "";
    public static int loadedIndexProfile = -1;
    
    // UI Variables
    public static String currentMp3Playing = null; // currently Mp3 playing
    public static int hour, minute, second; // for time 
    public static int vlcjPlaying = 0, // if VLC is playing, 0 -> not playing; 1 -> playing
            chkVLLoop = 0, // for checkbox VL loop
            chkVLMute = 0, // for checkbox VL mute
            chkVLFit = 0, // for checkbox VL fit
            chkVLShuffle = 0, // for checkbox VL shuffle
            playlistVideoMode = 1, // for checkbox VL playlist mode
            dbLoadedManual = -1, // to check whether load function is manually clicked(1) or auto via load prev profile(0)
            chkShuffle = 0, // for checkbox Shuffle
            mp3FrameOpened = 0, // check is MP3 frame is opened, 0 -> closed; 1 -> opened
            prevSong = 0, // this is in prev button in music player; if 0->song will restart, 1->back to previous song
            prevTimer = 0,
            cboVLType = 1, // 0->forlooping videos, 1->playlist mode
            vlStopClicked = 1, // for allowing to play vl loop when video item is same as the previous
            videoFirstTime = 1, // indicates whether video playing is done first time from the time system is ran. 0 -> not first time, 1 -> first time
            sfxClickCount = 0; // increments whenever the sfx buttons are clicked
    public static String enableAutosave = "on", // autosave status
            startup = "new", // new -> clean workspace after startup; load -> load previous loaded profile in startup
            fullScreenVL = "window", // whether the screen in Video loop is windowed(window) or full screen(full)
            locPopup = "topcenter", // String value for notification popup; used in saving UI
            VLType = "playlist"; // for detecting what combobox type will show in video loop
    public static Notification.Location location = Notification.Location.TOP_CENTER;
    public static Mp3Frame mp3;
    public static VLCFrame vlc;
    
    public MainFrame() {
//        if(HappyButtons.standardScreen) {
//            setExtendedState(this.MAXIMIZED_BOTH);
//        }
//        else {
//            setSize(1366, 730);
//        }
        setSize(1366, 768);
        
        initComponents();
        
        Thread t = new Thread(this);
        t.start();
        
        timerMp = new Timer(5000, (ActionEvent e) -> {
            prevSong = 0;
            prevTimer = 0;
        });
        
        // Line listeners
        sfxListener = (LineEvent event) -> {
            if(event.getType() == LineEvent.Type.STOP) {
                clipSFX = null; fcSFX = null; // System.out.println("SFX stopped" + sfxClickCount);
                clipSFX.removeLineListener(sfxListener);
            }
        };
        
        if(!HappyButtons.firstCheck.equals("")) {
            tfLastOperation.setText(HappyButtons.firstCheck);
            tfLastOperation.moveCaretPosition(0);
            tfLastOperation.setToolTipText(HappyButtons.firstCheck);
        }
        
        if(HappyButtons.mainFolderChk == 1) {
            tfLastOperation.setText("[SYSTEM]:: Some system file resources are missing");
            tfLastOperation.moveCaretPosition(0);
            tfLastOperation.setToolTipText("[SYSTEM]:: Some system file resources are missing");
        }
        
        if(HappyButtons.bgFolderChk == 1) {
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                "App resource \"BG\" folder not found\n\n\"BGM\" folder is created.\nNote that bg sounds involve in some profile saves may gone missing", 
                "CRITICAL FOLDER MISSING", 
                JOptionPane.WARNING_MESSAGE);
            System.out.println("BG folder" + HappyButtons.bgFolderChk);
            
            HappyButtons.bgFolderChk = 0;
        }
        
        if(HappyButtons.sfxFolderChk == 1) {
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                "App resource \"SFX\" folder not found\n\n\"SFX\" folder is created.\nNote that sfx sounds involve in some profile saves may gone missing", 
                "CRITICAL FOLDER MISSING", 
                JOptionPane.WARNING_MESSAGE);
            System.out.println("SFX folder" + HappyButtons.sfxFolderChk);
            
            HappyButtons.sfxFolderChk = 0;
        }
        
        if(HappyButtons.happyloopFolderChk == 1) {
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                "App resource \"VIDEO LOOP\" folder not found\n\n\"Video loop\" folder is created.\nNote that Happy Loop videos involve in some profile saves may gone missing", 
                "CRITICAL FOLDER MISSING", 
                JOptionPane.WARNING_MESSAGE);
            System.out.println("Video folder" + HappyButtons.happyloopFolderChk);
            
            HappyButtons.happyloopFolderChk = 0;
        }
        
        if(HappyButtons.mp3FolderChk == 1) {
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                "App resource \"MP3\" folder not found\n\n\"MP3\" folder is created.\nNote that Mp3 files involve in some profile saves may gone missing", 
                "CRITICAL FOLDER MISSING", 
                JOptionPane.WARNING_MESSAGE);
            System.out.println("Mp3 folder" + HappyButtons.mp3FolderChk);
            
            HappyButtons.mp3FolderChk = 0;
        }
        
        if(HappyButtons.dtbsFolderChk == 1) {
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                "Database folder not found\n\n\"Database\" folder is created.\nNote that new blank database is made. Files you saved may gone missing", 
                "CRITICAL FOLDER MISSING", 
                JOptionPane.WARNING_MESSAGE);
            System.out.println("DTBS folder" + HappyButtons.dtbsFolderChk);
            
            HappyButtons.dtbsFolderChk = 0;
        }

//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
////            UIManager.setLookAndFeel(new NimbusLookAndFeel()); // Set Nimbus look and feel
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                File deletePath = new File(HappyButtons.documentsPath + "/HappyButtons/temp/mp3");
                deleteFolder(deletePath);
            }
        });
        
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
        
        String btnStopSFXIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\stop_sfx_12px.png");
        btnStopSFX.setIcon(new javax.swing.ImageIcon(btnStopSFXIcon));
        
        String btnPlayVLIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_vl_12px.png");
        btnPlayVL.setIcon(new javax.swing.ImageIcon(btnPlayVLIcon));
        
        String btnStopVLIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\stop_vl_12px.png");
        btnStopVL.setIcon(new javax.swing.ImageIcon(btnStopVLIcon));
        
        String itmNewIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\new_workspace_12px.png");
        itmNew.setIcon(new javax.swing.ImageIcon(itmNewIcon));
        
        String itmSaveIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\save_workspace_12px.png");
        itemSave.setIcon(new javax.swing.ImageIcon(itmSaveIcon));
        
        String itmLoadIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\download_12px.png");
        itmLoad.setIcon(new javax.swing.ImageIcon(itmLoadIcon));
        
        String itmResourceManagerIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\maintenance_12px.png");
        itmResourceManager.setIcon(new javax.swing.ImageIcon(itmResourceManagerIcon));
        
        String itmPluginsIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\plugins_12px.png");
        itmPlugins.setIcon(new javax.swing.ImageIcon(itmPluginsIcon));
        
        String itmSettingsIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\settings_12px.png");
        itmSettings.setIcon(new javax.swing.ImageIcon(itmSettingsIcon));
        
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
        btnR1SFX12.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR1SFX13.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR1SFX14.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        
        btnR2SFX01.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR2SFX02.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR2SFX03.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR2SFX04.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR2SFX05.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR2SFX06.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR2SFX07.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR2SFX08.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR2SFX09.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR2SFX10.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR2SFX11.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR2SFX12.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR2SFX13.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR2SFX14.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
//        
        btnR3SFX01.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR3SFX02.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR3SFX03.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR3SFX04.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR3SFX05.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR3SFX06.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR3SFX07.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR3SFX08.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR3SFX09.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR3SFX10.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR3SFX11.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR3SFX12.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR3SFX13.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        btnR3SFX14.setIcon(new javax.swing.ImageIcon(btnSFXIcon));
        
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
        tfMp3.setTransferHandler(new DnDTextfieldBlank());
        
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
        lblR1SFX12.setTransferHandler(new DnDSFXLabels());
        lblR1SFX13.setTransferHandler(new DnDSFXLabels());
        lblR1SFX14.setTransferHandler(new DnDSFXLabels());
        
        lblR2SFX01.setTransferHandler(new DnDSFXLabels());
        lblR2SFX02.setTransferHandler(new DnDSFXLabels());
        lblR2SFX03.setTransferHandler(new DnDSFXLabels());
        lblR2SFX04.setTransferHandler(new DnDSFXLabels());
        lblR2SFX05.setTransferHandler(new DnDSFXLabels());
        lblR2SFX06.setTransferHandler(new DnDSFXLabels());
        lblR2SFX07.setTransferHandler(new DnDSFXLabels());
        lblR2SFX08.setTransferHandler(new DnDSFXLabels());
        lblR2SFX09.setTransferHandler(new DnDSFXLabels());
        lblR2SFX10.setTransferHandler(new DnDSFXLabels());
        lblR2SFX11.setTransferHandler(new DnDSFXLabels());
        lblR2SFX12.setTransferHandler(new DnDSFXLabels());
        lblR2SFX13.setTransferHandler(new DnDSFXLabels());
        lblR2SFX14.setTransferHandler(new DnDSFXLabels());
//        
        lblR3SFX01.setTransferHandler(new DnDSFXLabels());
        lblR3SFX02.setTransferHandler(new DnDSFXLabels());
        lblR3SFX03.setTransferHandler(new DnDSFXLabels());
        lblR3SFX04.setTransferHandler(new DnDSFXLabels());
        lblR3SFX05.setTransferHandler(new DnDSFXLabels());
        lblR3SFX06.setTransferHandler(new DnDSFXLabels());
        lblR3SFX07.setTransferHandler(new DnDSFXLabels());
        lblR3SFX08.setTransferHandler(new DnDSFXLabels());
        lblR3SFX09.setTransferHandler(new DnDSFXLabels());
        lblR3SFX10.setTransferHandler(new DnDSFXLabels());
        lblR3SFX11.setTransferHandler(new DnDSFXLabels());
        lblR3SFX12.setTransferHandler(new DnDSFXLabels());
        lblR3SFX13.setTransferHandler(new DnDSFXLabels());
        lblR3SFX14.setTransferHandler(new DnDSFXLabels());
        
        // -------------------------------------------------------------------------------------------------------------- SLIDER VALUE CLICK -->
        sliderClick = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JComponent sourceComponent = (JComponent) e.getSource();
                if(sourceComponent instanceof JSlider) {
                    JSlider sourceButton = (JSlider) sourceComponent;
                    
                    int newClickPosition = sourceButton.getMaximum() * e.getX() / sourceButton.getWidth();
                    sourceButton.setValue(newClickPosition);
                }
            }
        };
        
        volBGM1.addMouseListener(sliderClick);
        volBGM2.addMouseListener(sliderClick);
        volSFX.addMouseListener(sliderClick);
        
        // -------------------------------------------------------------------------------------------------------------- LEFT CLICK TEXT FIEDLS-->
        mp3FieldClick = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)) {
                    JComponent sourceComponent = (JComponent) e.getSource();
                    if(sourceComponent instanceof JTextField) {
                        JTextField sourceButton = (JTextField) sourceComponent;
                        sourceButton.setText("");
                        
                        if(clipMp3 != null) {
                            clipMp3.removeLineListener(listenMp3);
                            clipMp3.stop();
                            clipMp3.addLineListener(listenMp3);
                            clipMp3 = null;
                            selectedMp3Item = "";
                            Mp3Frame.listMp3.setSelectedIndex(-1);
                            lblCurrentMp3Duration.setText("00:00:00 / 00:00:00");
                            mp3Duration = "";
                            Mp3Frame.lblDuration.setText("00:00:00");
                            
                            // set icon manually
                            if(HappyButtons.uiTheme.equals("light")) {
                                String btnIcon3 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_12px.png");
                                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon3));
                            }
                            else if(HappyButtons.uiTheme.equals("dark")) {
                                String btnIcon3 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_mp3_12px.png");
                                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon3));
                            }
                        }
                    }
                }
                else {
                    openMp3Frame();
                }
            }
        };
        
        // -------------------------------------------------------------------------------------------------------------- RIGHT CLICK LABELS AND FIELDS -->
        rightButton = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)) {
                    JComponent sourceComponent = (JComponent) e.getSource();
                    if(sourceComponent instanceof JLabel) {
                        JLabel sourceButton = (JLabel) sourceComponent;
                        sourceButton.setText("<html><center>blank</center></html>");
                        autosave();
                    }
                }
            }
        };
        
        // -------------------------------------------------------------------------------------------------------------- COMBO BOX -->
        // Set a custom renderer to hide the arrow
//        cboVidLoop.setUI(new BasicComboBoxUI() {
//            @Override
//            protected JButton createArrowButton() {
//                JButton button = new JButton();
//                button.setPreferredSize(new Dimension(0, 0)); // Hide the button
//                button.setBorder(BorderFactory.createEmptyBorder()); // Remove the border
//                return button;
//            }
//        });
        
//        cboVidLoop.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                // Prevent the dropdown from appearing
//                cboVidLoop.setPopupVisible(false);
//            }
//        });
        
//        cboVidLoop.setBackground(new JComboBox().getBackground());
//        cboVidLoop.setBackground(Color.DARK_GRAY);
        
//        rightButtonMp3 = new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if(SwingUtilities.isRightMouseButton(e)) {
//                    tfMp3.removeMouseListener(mp3FieldClick);
//                    JComponent sourceComponent = (JComponent) e.getSource();
//                    if(sourceComponent instanceof JTextField) {
//                        JTextField sourceButton = (JTextField) sourceComponent;
//                        sourceButton.setText("");
//                        
//                        if(clipMp3 != null) {
//                            clipMp3.stop();
//                            clipMp3 = null;
//                        }
//                    }
//                    tfMp3.addMouseListener(mp3FieldClick);
//                }
//            }
//        };
        
        tfMp3.addMouseListener(mp3FieldClick);
        
        lblR1SFX01.addMouseListener(rightButton);
        lblR1SFX02.addMouseListener(rightButton);
        lblR1SFX03.addMouseListener(rightButton);
        lblR1SFX04.addMouseListener(rightButton);
        lblR1SFX05.addMouseListener(rightButton);
        lblR1SFX06.addMouseListener(rightButton);
        lblR1SFX07.addMouseListener(rightButton);
        lblR1SFX08.addMouseListener(rightButton);
        lblR1SFX09.addMouseListener(rightButton);
        lblR1SFX10.addMouseListener(rightButton);
        lblR1SFX11.addMouseListener(rightButton);
        lblR1SFX12.addMouseListener(rightButton);
        lblR1SFX13.addMouseListener(rightButton);
        lblR1SFX14.addMouseListener(rightButton);
        
        lblR2SFX01.addMouseListener(rightButton);
        lblR2SFX02.addMouseListener(rightButton);
        lblR2SFX03.addMouseListener(rightButton);
        lblR2SFX04.addMouseListener(rightButton);
        lblR2SFX05.addMouseListener(rightButton);
        lblR2SFX06.addMouseListener(rightButton);
        lblR2SFX07.addMouseListener(rightButton);
        lblR2SFX08.addMouseListener(rightButton);
        lblR2SFX09.addMouseListener(rightButton);
        lblR2SFX10.addMouseListener(rightButton);
        lblR2SFX11.addMouseListener(rightButton);
        lblR2SFX12.addMouseListener(rightButton);
        lblR2SFX13.addMouseListener(rightButton);
        lblR2SFX14.addMouseListener(rightButton);
        
        lblR3SFX01.addMouseListener(rightButton);
        lblR3SFX02.addMouseListener(rightButton);
        lblR3SFX03.addMouseListener(rightButton);
        lblR3SFX04.addMouseListener(rightButton);
        lblR3SFX05.addMouseListener(rightButton);
        lblR3SFX06.addMouseListener(rightButton);
        lblR3SFX07.addMouseListener(rightButton);
        lblR3SFX08.addMouseListener(rightButton);
        lblR3SFX09.addMouseListener(rightButton);
        lblR3SFX10.addMouseListener(rightButton);
        lblR3SFX11.addMouseListener(rightButton);
        lblR3SFX12.addMouseListener(rightButton);
        lblR3SFX13.addMouseListener(rightButton);
        lblR3SFX14.addMouseListener(rightButton);
        
        // -------------------------------------------------------------------------------------------------------------- HOTKEYS -->
        Action actSfxSP = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(chkSP.isSelected()) {
                    chkSinglePlay = 0;
                    chkSP.setSelected(false);
                }
                else {
                    chkSinglePlay = 1;
                    chkSP.setSelected(true);
                }
            }
        };
        String key = "SP";
        chkSP.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.ALT_DOWN_MASK), key);
        chkSP.getActionMap().put(key, actSfxSP);
        
        // --- Checkbox Loop VL
        Action actChkLoopVL = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(chkLoopVL.isSelected()) {
                    chkVLLoop = 0;
                    chkLoopVL.setSelected(false);
                }
                else {
                    chkVLLoop = 1;
                    chkLoopVL.setSelected(true);
                }
            }
        };
        String keyLoopVL = "CheckVLLoop";
        chkLoopVL.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.ALT_DOWN_MASK), keyLoopVL);
        chkLoopVL.getActionMap().put(keyLoopVL, actChkLoopVL);
        
        // --- Checkbox Mute VL
        Action actChkMuteVL = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(chkMuteVL.isSelected()) {
                    chkVLMute = 0;
                    chkMuteVL.setSelected(false);
                }
                else {
                    chkVLMute = 1;
                    chkMuteVL.setSelected(true);
                }
            }
        };
        String keyMuteVL = "CheckVLMute";
        chkMuteVL.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.ALT_DOWN_MASK), keyMuteVL);
        chkMuteVL.getActionMap().put(keyMuteVL, actChkMuteVL);
        
        // --- Play pause button 1
        Action actBgmPlayPause1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnPlayPauseBGM1ActionPerformed(e);
            }
        };
        String keyPlayPause1 = "Play1";
        btnPlayPauseBGM1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.ALT_DOWN_MASK), keyPlayPause1);
        btnPlayPauseBGM1.getActionMap().put(keyPlayPause1, actBgmPlayPause1);
        
        // --- Play pause button 2
        Action actBgmPlayPause2 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnPlayPauseBGM2ActionPerformed(e);
            }
        };
        String keyPlayPause2 = "Play2";
        btnPlayPauseBGM2.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.ALT_DOWN_MASK), keyPlayPause2);
        btnPlayPauseBGM2.getActionMap().put(keyPlayPause2, actBgmPlayPause2);
        
        // --- Stop button 1
        Action actBgmStop1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStopBGM1ActionPerformed(e);
            }
        };
        String keyStop1 = "Stop1";
        btnStopBGM1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.ALT_DOWN_MASK), keyStop1);
        btnStopBGM1.getActionMap().put(keyStop1, actBgmStop1);
        
        // --- Stop button 2
        Action actBgmStop2 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStopBGM2ActionPerformed(e);
            }
        };
        String keyStop2 = "Stop2";
        btnStopBGM2.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK), keyStop2);
        btnStopBGM2.getActionMap().put(keyStop2, actBgmStop2);
        
        // -------------------------------------------------------------------------------------------------------------- LISTING JLIST -->
//        blistFilesForFolder(bfolder);
//        slistFilesForFolder(sfolder);
        
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
        
        listBGM.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = listBGM.locationToIndex(e.getPoint());
                    if(index != -1) {
                        String selectedItem = (String)blist.getElementAt(index);
                        
                        if(tfBGM1.getText().isBlank()) {
                            tfBGM1.setText(selectedItem);
                        }
                        else {
                            if(playing1 == 0) {
                                if(tfBGM2.getText().isBlank()) {
                                    tfBGM2.setText(selectedItem);
                                }
                                else {
                                    if(playing2 == 1) {
                                        tfBGM1.setText(selectedItem);
                                    }
                                    else {
                                        tfBGM1.setText(selectedItem);
                                    }
                                }
                            }
                            else {
                                if(tfBGM2.getText().isBlank()) {
                                    tfBGM2.setText(selectedItem);
                                }
                                else {
                                    if(playing2 == 1) {
                                        tfLastOperation.setText("BGMs are busy, cannot input selected BGM");
                                        tfLastOperation.setToolTipText("BGMs are busy, cannot input selected BGM");
                                        tfLastOperation.moveCaretPosition(0);
                                    }
                                    else {
                                        tfBGM2.setText(selectedItem);
                                    }
                                }
                            }
                        }
                    }
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
        
        tfLastOperation.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(tfLastOperation.getText().equals("SFX changes")) {
                    visualizeSaving();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(tfLastOperation.getText().equals("SFX changes")) {
                    visualizeSaving();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(tfLastOperation.getText().equals("SFX changes")) {
                    visualizeSaving();
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

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        panelJList = new javax.swing.JPanel();
        btnAddBGM = new javax.swing.JButton();
        btnAddSFX = new javax.swing.JButton();
        lblDeleteSFX = new javax.swing.JLabel();
        btnDeleteBGM = new javax.swing.JButton();
        btnDeleteSFX = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        listSFX = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        listBGM = new javax.swing.JList<>();
        panelRow1 = new javax.swing.JPanel();
        lblBGM1 = new javax.swing.JLabel();
        tfBGM1 = new PlaceHolderTextfield("Drag BGM here or double-click from BGM list");
        btnClearBGM1 = new javax.swing.JButton();
        btnStopBGM1 = new javax.swing.JButton();
        btnPlayPauseBGM1 = new javax.swing.JButton();
        volBGM1 = new happybuttons.SliderBGMGradient();
        lblVolBGM1 = new javax.swing.JLabel();
        chkLoop1 = new javax.swing.JCheckBox();
        panelRow2 = new javax.swing.JPanel();
        lblBGM2 = new javax.swing.JLabel();
        tfBGM2 = new PlaceHolderTextfield("Drag BGM here or double-click from BGM list");
        btnClearBGM2 = new javax.swing.JButton();
        btnStopBGM2 = new javax.swing.JButton();
        btnPlayPauseBGM2 = new javax.swing.JButton();
        volBGM2 = new happybuttons.SliderBGMGradient();
        lblVolBGM2 = new javax.swing.JLabel();
        chkLoop2 = new javax.swing.JCheckBox();
        panelRow3 = new javax.swing.JPanel();
        lblLastOperation = new javax.swing.JLabel();
        tfLastOperation = new javax.swing.JTextField();
        togLinkBGMVol = new javax.swing.JToggleButton();
        lblLinkBGMVolumes = new javax.swing.JLabel();
        btnPrevMp3 = new javax.swing.JButton();
        btnNextMp3 = new javax.swing.JButton();
        lblMp3 = new javax.swing.JLabel();
        tfMp3 = new PlaceHolderTextfield("Click to open Music player window");
        btnPlayPauseMp3 = new javax.swing.JButton();
        lblCurrentMp3Duration = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        volSFX = new happybuttons.SliderSFXGradient();
        lblVolSFX = new javax.swing.JLabel();
        lblSFXState = new javax.swing.JLabel();
        chkSP = new javax.swing.JCheckBox();
        chkIB = new javax.swing.JCheckBox();
        btnStopSFX = new javax.swing.JButton();
        lblVideoLoop = new javax.swing.JLabel();
        panelSFX1 = new javax.swing.JPanel();
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
        panelR1S11 = new javax.swing.JPanel();
        btnR1SFX11 = new javax.swing.JButton();
        lblR1SFX11 = new javax.swing.JLabel();
        panelR1S12 = new javax.swing.JPanel();
        btnR1SFX12 = new javax.swing.JButton();
        lblR1SFX12 = new javax.swing.JLabel();
        panelR1S13 = new javax.swing.JPanel();
        btnR1SFX13 = new javax.swing.JButton();
        lblR1SFX13 = new javax.swing.JLabel();
        panelR1S14 = new javax.swing.JPanel();
        btnR1SFX14 = new javax.swing.JButton();
        lblR1SFX14 = new javax.swing.JLabel();
        panelSFX2 = new javax.swing.JPanel();
        panelR2S01 = new javax.swing.JPanel();
        btnR2SFX01 = new javax.swing.JButton();
        lblR2SFX01 = new javax.swing.JLabel();
        panelR2S02 = new javax.swing.JPanel();
        btnR2SFX02 = new javax.swing.JButton();
        lblR2SFX02 = new javax.swing.JLabel();
        panelR2S04 = new javax.swing.JPanel();
        btnR2SFX04 = new javax.swing.JButton();
        lblR2SFX04 = new javax.swing.JLabel();
        panelR2S03 = new javax.swing.JPanel();
        btnR2SFX03 = new javax.swing.JButton();
        lblR2SFX03 = new javax.swing.JLabel();
        panelR2S08 = new javax.swing.JPanel();
        btnR2SFX08 = new javax.swing.JButton();
        lblR2SFX08 = new javax.swing.JLabel();
        panelR2S05 = new javax.swing.JPanel();
        btnR2SFX05 = new javax.swing.JButton();
        lblR2SFX05 = new javax.swing.JLabel();
        panelR2S07 = new javax.swing.JPanel();
        btnR2SFX07 = new javax.swing.JButton();
        lblR2SFX07 = new javax.swing.JLabel();
        panelR2S06 = new javax.swing.JPanel();
        btnR2SFX06 = new javax.swing.JButton();
        lblR2SFX06 = new javax.swing.JLabel();
        panelR2S09 = new javax.swing.JPanel();
        btnR2SFX09 = new javax.swing.JButton();
        lblR2SFX09 = new javax.swing.JLabel();
        panelR2S10 = new javax.swing.JPanel();
        btnR2SFX10 = new javax.swing.JButton();
        lblR2SFX10 = new javax.swing.JLabel();
        panelR2S11 = new javax.swing.JPanel();
        btnR2SFX11 = new javax.swing.JButton();
        lblR2SFX11 = new javax.swing.JLabel();
        panelR2S12 = new javax.swing.JPanel();
        btnR2SFX12 = new javax.swing.JButton();
        lblR2SFX12 = new javax.swing.JLabel();
        panelR2S13 = new javax.swing.JPanel();
        btnR2SFX13 = new javax.swing.JButton();
        lblR2SFX13 = new javax.swing.JLabel();
        panelR2S14 = new javax.swing.JPanel();
        btnR2SFX14 = new javax.swing.JButton();
        lblR2SFX14 = new javax.swing.JLabel();
        panelSFX3 = new javax.swing.JPanel();
        panelR3S01 = new javax.swing.JPanel();
        btnR3SFX01 = new javax.swing.JButton();
        lblR3SFX01 = new javax.swing.JLabel();
        panelR3S02 = new javax.swing.JPanel();
        btnR3SFX02 = new javax.swing.JButton();
        lblR3SFX02 = new javax.swing.JLabel();
        panelR3S04 = new javax.swing.JPanel();
        btnR3SFX04 = new javax.swing.JButton();
        lblR3SFX04 = new javax.swing.JLabel();
        panelR3S03 = new javax.swing.JPanel();
        btnR3SFX03 = new javax.swing.JButton();
        lblR3SFX03 = new javax.swing.JLabel();
        panelR3S08 = new javax.swing.JPanel();
        btnR3SFX08 = new javax.swing.JButton();
        lblR3SFX08 = new javax.swing.JLabel();
        panelR3S05 = new javax.swing.JPanel();
        btnR3SFX05 = new javax.swing.JButton();
        lblR3SFX05 = new javax.swing.JLabel();
        panelR3S07 = new javax.swing.JPanel();
        btnR3SFX07 = new javax.swing.JButton();
        lblR3SFX07 = new javax.swing.JLabel();
        panelR3S06 = new javax.swing.JPanel();
        btnR3SFX06 = new javax.swing.JButton();
        lblR3SFX06 = new javax.swing.JLabel();
        panelR3S09 = new javax.swing.JPanel();
        btnR3SFX09 = new javax.swing.JButton();
        lblR3SFX09 = new javax.swing.JLabel();
        panelR3S10 = new javax.swing.JPanel();
        btnR3SFX10 = new javax.swing.JButton();
        lblR3SFX10 = new javax.swing.JLabel();
        panelR3S11 = new javax.swing.JPanel();
        btnR3SFX11 = new javax.swing.JButton();
        lblR3SFX11 = new javax.swing.JLabel();
        panelR3S12 = new javax.swing.JPanel();
        btnR3SFX12 = new javax.swing.JButton();
        lblR3SFX12 = new javax.swing.JLabel();
        panelR3S13 = new javax.swing.JPanel();
        btnR3SFX13 = new javax.swing.JButton();
        lblR3SFX13 = new javax.swing.JLabel();
        panelR3S14 = new javax.swing.JPanel();
        btnR3SFX14 = new javax.swing.JButton();
        lblR3SFX14 = new javax.swing.JLabel();
        btnPlayVL = new javax.swing.JButton();
        btnStopVL = new javax.swing.JButton();
        chkLoopVL = new javax.swing.JCheckBox();
        chkMuteVL = new javax.swing.JCheckBox();
        chkFitVL = new javax.swing.JCheckBox();
        chkVLMode = new javax.swing.JCheckBox();
        tfVideoLoop = new PlaceHolderTextfield("Click to select video");
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        itmNew = new javax.swing.JMenuItem();
        itemSave = new javax.swing.JMenuItem();
        itmLoad = new javax.swing.JMenuItem();
        menuPreferences = new javax.swing.JMenu();
        itmUITheme = new javax.swing.JMenuItem();
        itmSettings = new javax.swing.JMenuItem();
        itmTools = new javax.swing.JMenu();
        itmResourceManager = new javax.swing.JMenuItem();
        itmPlugins = new javax.swing.JMenuItem();
        itmSystemTools = new javax.swing.JMenuItem();
        itmAbout = new javax.swing.JMenu();
        jMenuTime = new javax.swing.JMenu();
        itmAS = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1366, 730));
        setName("mainFrame"); // NOI18N
        setResizable(false);
        setSize(new java.awt.Dimension(1366, 730));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelJList.setPreferredSize(new java.awt.Dimension(1354, 180));

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

        listSFX.setDragEnabled(true);
        jScrollPane3.setViewportView(listSFX);

        listBGM.setToolTipText("Double click or drag to BGM slot");
        listBGM.setDragEnabled(true);
        jScrollPane2.setViewportView(listBGM);

        javax.swing.GroupLayout panelJListLayout = new javax.swing.GroupLayout(panelJList);
        panelJList.setLayout(panelJListLayout);
        panelJListLayout.setHorizontalGroup(
            panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJListLayout.createSequentialGroup()
                .addGroup(panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelJListLayout.createSequentialGroup()
                        .addComponent(btnAddBGM, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteBGM, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelJListLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(btnAddSFX, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteSFX, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(lblDeleteSFX, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(560, Short.MAX_VALUE))
                    .addGroup(panelJListLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3))))
        );
        panelJListLayout.setVerticalGroup(
            panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJListLayout.createSequentialGroup()
                .addGroup(panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnAddBGM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddSFX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDeleteSFX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeleteBGM, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeleteSFX, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(panelJList, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 106, 1317, 202));

        panelRow1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBGM1.setText("BGM1:");
        panelRow1.add(lblBGM1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 9, -1, -1));

        tfBGM1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tfBGM1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tfBGM1.setFocusable(false);
        tfBGM1.setName("tfBGM1"); // NOI18N
        tfBGM1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tfBGM1PropertyChange(evt);
            }
        });
        tfBGM1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfBGM1KeyPressed(evt);
            }
        });
        panelRow1.add(tfBGM1, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 6, 500, -1));

        btnClearBGM1.setToolTipText("Clear BGM1 and stop");
        btnClearBGM1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClearBGM1.setMaximumSize(new java.awt.Dimension(22, 22));
        btnClearBGM1.setMinimumSize(new java.awt.Dimension(22, 22));
        btnClearBGM1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearBGM1ActionPerformed(evt);
            }
        });
        panelRow1.add(btnClearBGM1, new org.netbeans.lib.awtextra.AbsoluteConstraints(547, 6, 22, 22));

        btnStopBGM1.setToolTipText("Stop BGM1");
        btnStopBGM1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStopBGM1.setMaximumSize(new java.awt.Dimension(22, 22));
        btnStopBGM1.setMinimumSize(new java.awt.Dimension(22, 22));
        btnStopBGM1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopBGM1ActionPerformed(evt);
            }
        });
        panelRow1.add(btnStopBGM1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1289, 6, 22, 22));

        btnPlayPauseBGM1.setToolTipText("Play or pause BGM1");
        btnPlayPauseBGM1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPlayPauseBGM1.setMaximumSize(new java.awt.Dimension(22, 22));
        btnPlayPauseBGM1.setMinimumSize(new java.awt.Dimension(22, 22));
        btnPlayPauseBGM1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayPauseBGM1ActionPerformed(evt);
            }
        });
        panelRow1.add(btnPlayPauseBGM1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1255, 6, 22, 22));

        volBGM1.setToolTipText("BGM1 volume");
        volBGM1.setValue(100);
        volBGM1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        volBGM1.setMaximumSize(new java.awt.Dimension(200, 20));
        volBGM1.setMinimumSize(new java.awt.Dimension(200, 20));
        volBGM1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                volBGM1StateChanged(evt);
            }
        });
        panelRow1.add(volBGM1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1037, 6, -1, -1));

        lblVolBGM1.setText("Vol1: 100");
        lblVolBGM1.setMaximumSize(new java.awt.Dimension(60, 22));
        lblVolBGM1.setMinimumSize(new java.awt.Dimension(60, 22));
        lblVolBGM1.setPreferredSize(new java.awt.Dimension(60, 22));
        panelRow1.add(lblVolBGM1, new org.netbeans.lib.awtextra.AbsoluteConstraints(971, 8, -1, 20));

        chkLoop1.setSelected(true);
        chkLoop1.setToolTipText("Loop BGM1");
        chkLoop1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chkLoop1.setOpaque(true);
        chkLoop1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLoop1ActionPerformed(evt);
            }
        });
        panelRow1.add(chkLoop1, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 8, -1, -1));

        getContentPane().add(panelRow1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 0, 1317, -1));

        panelRow2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBGM2.setText("BGM2:");
        panelRow2.add(lblBGM2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 9, -1, -1));

        tfBGM2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tfBGM2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tfBGM2.setFocusable(false);
        tfBGM2.setName("tfBGM1"); // NOI18N
        tfBGM2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tfBGM2PropertyChange(evt);
            }
        });
        panelRow2.add(tfBGM2, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 6, 500, -1));

        btnClearBGM2.setToolTipText("Clear BGM2 and stop");
        btnClearBGM2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClearBGM2.setMaximumSize(new java.awt.Dimension(22, 22));
        btnClearBGM2.setMinimumSize(new java.awt.Dimension(22, 22));
        btnClearBGM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearBGM2ActionPerformed(evt);
            }
        });
        panelRow2.add(btnClearBGM2, new org.netbeans.lib.awtextra.AbsoluteConstraints(547, 6, 22, 22));

        btnStopBGM2.setToolTipText("Stop BGM2");
        btnStopBGM2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStopBGM2.setMaximumSize(new java.awt.Dimension(22, 22));
        btnStopBGM2.setMinimumSize(new java.awt.Dimension(22, 22));
        btnStopBGM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopBGM2ActionPerformed(evt);
            }
        });
        panelRow2.add(btnStopBGM2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1289, 6, 22, 22));

        btnPlayPauseBGM2.setToolTipText("Play or pause BGM2");
        btnPlayPauseBGM2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPlayPauseBGM2.setMaximumSize(new java.awt.Dimension(22, 22));
        btnPlayPauseBGM2.setMinimumSize(new java.awt.Dimension(22, 22));
        btnPlayPauseBGM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayPauseBGM2ActionPerformed(evt);
            }
        });
        panelRow2.add(btnPlayPauseBGM2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1255, 6, 22, 22));

        volBGM2.setToolTipText("BGM2 volume");
        volBGM2.setValue(100);
        volBGM2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        volBGM2.setMaximumSize(new java.awt.Dimension(200, 20));
        volBGM2.setMinimumSize(new java.awt.Dimension(200, 20));
        volBGM2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                volBGM2StateChanged(evt);
            }
        });
        panelRow2.add(volBGM2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1037, 6, -1, -1));

        lblVolBGM2.setText("Vol2: 100");
        lblVolBGM2.setMaximumSize(new java.awt.Dimension(60, 22));
        lblVolBGM2.setMinimumSize(new java.awt.Dimension(60, 22));
        lblVolBGM2.setPreferredSize(new java.awt.Dimension(60, 22));
        panelRow2.add(lblVolBGM2, new org.netbeans.lib.awtextra.AbsoluteConstraints(971, 8, -1, 20));

        chkLoop2.setSelected(true);
        chkLoop2.setToolTipText("Loop BGM2");
        chkLoop2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chkLoop2.setOpaque(true);
        chkLoop2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLoop2ActionPerformed(evt);
            }
        });
        panelRow2.add(chkLoop2, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 9, -1, -1));

        getContentPane().add(panelRow2, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 31, 1317, -1));

        panelRow3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLastOperation.setText("Last Operation:");
        panelRow3.add(lblLastOperation, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 9, -1, -1));

        tfLastOperation.setEditable(false);
        tfLastOperation.setForeground(new java.awt.Color(0, 0, 0));
        tfLastOperation.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tfLastOperation.setMaximumSize(new java.awt.Dimension(22, 600));
        tfLastOperation.setMinimumSize(new java.awt.Dimension(22, 600));
        panelRow3.add(tfLastOperation, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 6, 340, -1));

        togLinkBGMVol.setText("OFF");
        togLinkBGMVol.setToolTipText("Toggle this ON when you want to inversely link BGM1 vol and BGM2 vol");
        togLinkBGMVol.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        togLinkBGMVol.setMaximumSize(new java.awt.Dimension(50, 22));
        togLinkBGMVol.setMinimumSize(new java.awt.Dimension(50, 22));
        togLinkBGMVol.setPreferredSize(new java.awt.Dimension(50, 22));
        togLinkBGMVol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                togLinkBGMVolActionPerformed(evt);
            }
        });
        panelRow3.add(togLinkBGMVol, new org.netbeans.lib.awtextra.AbsoluteConstraints(1258, 7, 56, -1));

        lblLinkBGMVolumes.setText("Link BGM Volumes");
        lblLinkBGMVolumes.setToolTipText("");
        panelRow3.add(lblLinkBGMVolumes, new org.netbeans.lib.awtextra.AbsoluteConstraints(1142, 6, -1, 23));

        btnPrevMp3.setToolTipText("Play / pause Mp3");
        btnPrevMp3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrevMp3.setMaximumSize(new java.awt.Dimension(22, 22));
        btnPrevMp3.setMinimumSize(new java.awt.Dimension(22, 22));
        btnPrevMp3.setPreferredSize(new java.awt.Dimension(22, 22));
        btnPrevMp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevMp3ActionPerformed(evt);
            }
        });
        panelRow3.add(btnPrevMp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(788, 6, -1, -1));

        btnNextMp3.setToolTipText("Stop Mp3");
        btnNextMp3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNextMp3.setMaximumSize(new java.awt.Dimension(22, 22));
        btnNextMp3.setMinimumSize(new java.awt.Dimension(22, 22));
        btnNextMp3.setPreferredSize(new java.awt.Dimension(22, 22));
        btnNextMp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextMp3ActionPerformed(evt);
            }
        });
        panelRow3.add(btnNextMp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(844, 6, -1, -1));

        lblMp3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMp3.setText("Music Player:");
        lblMp3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMp3.setMaximumSize(new java.awt.Dimension(22, 16));
        lblMp3.setMinimumSize(new java.awt.Dimension(22, 16));
        lblMp3.setPreferredSize(new java.awt.Dimension(22, 16));
        lblMp3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMp3MouseClicked(evt);
            }
        });
        panelRow3.add(lblMp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(458, 9, 73, -1));

        tfMp3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tfMp3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tfMp3.setFocusable(false);
        tfMp3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfMp3MouseClicked(evt);
            }
        });
        tfMp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfMp3ActionPerformed(evt);
            }
        });
        panelRow3.add(tfMp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(537, 6, 245, -1));

        btnPlayPauseMp3.setToolTipText("Play / pause Mp3");
        btnPlayPauseMp3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPlayPauseMp3.setMaximumSize(new java.awt.Dimension(22, 22));
        btnPlayPauseMp3.setMinimumSize(new java.awt.Dimension(22, 22));
        btnPlayPauseMp3.setPreferredSize(new java.awt.Dimension(22, 22));
        btnPlayPauseMp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayPauseMp3ActionPerformed(evt);
            }
        });
        panelRow3.add(btnPlayPauseMp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(816, 6, -1, 23));

        lblCurrentMp3Duration.setText("00:00:00 / 00:00:00");
        panelRow3.add(lblCurrentMp3Duration, new org.netbeans.lib.awtextra.AbsoluteConstraints(878, 9, -1, -1));

        getContentPane().add(panelRow3, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 66, 1320, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 320, 1317, 10));

        volSFX.setToolTipText("BGM1 volume");
        volSFX.setValue(100);
        volSFX.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        volSFX.setMaximumSize(new java.awt.Dimension(200, 20));
        volSFX.setMinimumSize(new java.awt.Dimension(200, 20));
        volSFX.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                volSFXStateChanged(evt);
            }
        });
        getContentPane().add(volSFX, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 336, -1, -1));

        lblVolSFX.setText("SFX Vol: 100");
        lblVolSFX.setMaximumSize(new java.awt.Dimension(85, 22));
        lblVolSFX.setMinimumSize(new java.awt.Dimension(85, 22));
        lblVolSFX.setPreferredSize(new java.awt.Dimension(85, 22));
        getContentPane().add(lblVolSFX, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 336, 73, -1));

        lblSFXState.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSFXState.setText("SFX State:");
        getContentPane().add(lblSFXState, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 339, -1, -1));

        chkSP.setSelected(true);
        chkSP.setText("SP");
        chkSP.setToolTipText("Single play");
        chkSP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chkSP.setFocusable(false);
        chkSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSPActionPerformed(evt);
            }
        });
        getContentPane().add(chkSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 337, 45, -1));

        chkIB.setText("IB");
        chkIB.setToolTipText("Interrupt BGM");
        chkIB.setEnabled(false);
        chkIB.setFocusable(false);
        chkIB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIBActionPerformed(evt);
            }
        });
        getContentPane().add(chkIB, new org.netbeans.lib.awtextra.AbsoluteConstraints(423, 337, 45, -1));

        btnStopSFX.setToolTipText("Stop SFX");
        btnStopSFX.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStopSFX.setMaximumSize(new java.awt.Dimension(22, 22));
        btnStopSFX.setMinimumSize(new java.awt.Dimension(22, 22));
        btnStopSFX.setPreferredSize(new java.awt.Dimension(22, 22));
        btnStopSFX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopSFXActionPerformed(evt);
            }
        });
        getContentPane().add(btnStopSFX, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 336, -1, -1));

        lblVideoLoop.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblVideoLoop.setText("Videos:");
        getContentPane().add(lblVideoLoop, new org.netbeans.lib.awtextra.AbsoluteConstraints(788, 339, 50, -1));

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
                .addComponent(lblR1SFX01, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
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
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(btnR1SFX02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
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
                .addComponent(lblR1SFX02, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
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
                .addComponent(lblR1SFX04, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
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
                .addComponent(lblR1SFX03, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
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
                .addComponent(lblR1SFX08, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
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
                .addComponent(lblR1SFX05, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
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
                .addComponent(lblR1SFX07, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
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
                .addComponent(lblR1SFX06, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
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
                .addComponent(lblR1SFX09, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
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
                .addComponent(lblR1SFX10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
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

        javax.swing.GroupLayout panelR1S11Layout = new javax.swing.GroupLayout(panelR1S11);
        panelR1S11.setLayout(panelR1S11Layout);
        panelR1S11Layout.setHorizontalGroup(
            panelR1S11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S11Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR1SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR1SFX11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S11Layout.setVerticalGroup(
            panelR1S11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR1SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR1SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR1SFX12.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR1SFX12.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR1SFX12.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR1SFX12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR1SFX12ActionPerformed(evt);
            }
        });

        lblR1SFX12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR1SFX12.setForeground(new java.awt.Color(0, 0, 0));
        lblR1SFX12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR1SFX12.setText("blank");
        lblR1SFX12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR1SFX12.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR1SFX12.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR1SFX12.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR1S12Layout = new javax.swing.GroupLayout(panelR1S12);
        panelR1S12.setLayout(panelR1S12Layout);
        panelR1S12Layout.setHorizontalGroup(
            panelR1S12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S12Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR1SFX12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR1SFX12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S12Layout.setVerticalGroup(
            panelR1S12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR1SFX12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR1SFX12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR1SFX13.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR1SFX13.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR1SFX13.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR1SFX13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR1SFX13ActionPerformed(evt);
            }
        });

        lblR1SFX13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR1SFX13.setForeground(new java.awt.Color(0, 0, 0));
        lblR1SFX13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR1SFX13.setText("blank");
        lblR1SFX13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR1SFX13.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR1SFX13.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR1SFX13.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR1S13Layout = new javax.swing.GroupLayout(panelR1S13);
        panelR1S13.setLayout(panelR1S13Layout);
        panelR1S13Layout.setHorizontalGroup(
            panelR1S13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S13Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR1SFX13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR1SFX13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S13Layout.setVerticalGroup(
            panelR1S13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR1SFX13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR1SFX13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR1SFX14.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR1SFX14.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR1SFX14.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR1SFX14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR1SFX14ActionPerformed(evt);
            }
        });

        lblR1SFX14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR1SFX14.setForeground(new java.awt.Color(0, 0, 0));
        lblR1SFX14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR1SFX14.setText("blank");
        lblR1SFX14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR1SFX14.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR1SFX14.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR1SFX14.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR1S14Layout = new javax.swing.GroupLayout(panelR1S14);
        panelR1S14.setLayout(panelR1S14Layout);
        panelR1S14Layout.setHorizontalGroup(
            panelR1S14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR1SFX14, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelR1S14Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnR1SFX14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelR1S14Layout.setVerticalGroup(
            panelR1S14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnR1SFX14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblR1SFX14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout panelSFX1Layout = new javax.swing.GroupLayout(panelSFX1);
        panelSFX1.setLayout(panelSFX1Layout);
        panelSFX1Layout.setHorizontalGroup(
            panelSFX1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSFX1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
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
                .addComponent(panelR1S11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSFX1Layout.setVerticalGroup(
            panelSFX1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSFX1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(panelSFX1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelR1S01, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSFX1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(panelR1S11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR1S10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR1S09, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR1S08, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR1S07, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR1S06, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR1S05, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR1S04, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR1S03, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR1S02, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelR1S13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(1, 1, 1))
        );

        getContentPane().add(panelSFX1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 364, -1, -1));

        panelR2S01.setMaximumSize(new java.awt.Dimension(90, 88));
        panelR2S01.setMinimumSize(new java.awt.Dimension(90, 88));

        btnR2SFX01.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR2SFX01.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR2SFX01.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR2SFX01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR2SFX01ActionPerformed(evt);
            }
        });

        lblR2SFX01.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR2SFX01.setForeground(new java.awt.Color(0, 0, 0));
        lblR2SFX01.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR2SFX01.setText("blank");
        lblR2SFX01.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR2SFX01.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR2SFX01.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR2SFX01.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR2S01Layout = new javax.swing.GroupLayout(panelR2S01);
        panelR2S01.setLayout(panelR2S01Layout);
        panelR2S01Layout.setHorizontalGroup(
            panelR2S01Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S01Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR2S01Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX01, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR2S01Layout.setVerticalGroup(
            panelR2S01Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S01Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnR2SFX01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblR2SFX01, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR2SFX02.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR2SFX02.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR2SFX02.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR2SFX02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR2SFX02ActionPerformed(evt);
            }
        });

        lblR2SFX02.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR2SFX02.setForeground(new java.awt.Color(0, 0, 0));
        lblR2SFX02.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR2SFX02.setText("blank");
        lblR2SFX02.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR2SFX02.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR2SFX02.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR2SFX02.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR2S02Layout = new javax.swing.GroupLayout(panelR2S02);
        panelR2S02.setLayout(panelR2S02Layout);
        panelR2S02Layout.setHorizontalGroup(
            panelR2S02Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelR2S02Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(btnR2SFX02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(panelR2S02Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX02, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR2S02Layout.setVerticalGroup(
            panelR2S02Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S02Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnR2SFX02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblR2SFX02, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR2SFX04.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR2SFX04.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR2SFX04.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR2SFX04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR2SFX04ActionPerformed(evt);
            }
        });

        lblR2SFX04.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR2SFX04.setForeground(new java.awt.Color(0, 0, 0));
        lblR2SFX04.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR2SFX04.setText("blank");
        lblR2SFX04.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR2SFX04.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR2SFX04.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR2SFX04.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR2S04Layout = new javax.swing.GroupLayout(panelR2S04);
        panelR2S04.setLayout(panelR2S04Layout);
        panelR2S04Layout.setHorizontalGroup(
            panelR2S04Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S04Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR2S04Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX04, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR2S04Layout.setVerticalGroup(
            panelR2S04Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S04Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR2SFX04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR2SFX04, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR2SFX03.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR2SFX03.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR2SFX03.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR2SFX03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR2SFX03ActionPerformed(evt);
            }
        });

        lblR2SFX03.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR2SFX03.setForeground(new java.awt.Color(0, 0, 0));
        lblR2SFX03.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR2SFX03.setText("blank");
        lblR2SFX03.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR2SFX03.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR2SFX03.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR2SFX03.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR2S03Layout = new javax.swing.GroupLayout(panelR2S03);
        panelR2S03.setLayout(panelR2S03Layout);
        panelR2S03Layout.setHorizontalGroup(
            panelR2S03Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S03Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR2S03Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX03, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR2S03Layout.setVerticalGroup(
            panelR2S03Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S03Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR2SFX03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR2SFX03, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR2SFX08.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR2SFX08.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR2SFX08.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR2SFX08.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR2SFX08ActionPerformed(evt);
            }
        });

        lblR2SFX08.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR2SFX08.setForeground(new java.awt.Color(0, 0, 0));
        lblR2SFX08.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR2SFX08.setText("blank");
        lblR2SFX08.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR2SFX08.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR2SFX08.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR2SFX08.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR2S08Layout = new javax.swing.GroupLayout(panelR2S08);
        panelR2S08.setLayout(panelR2S08Layout);
        panelR2S08Layout.setHorizontalGroup(
            panelR2S08Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S08Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX08, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR2S08Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX08, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR2S08Layout.setVerticalGroup(
            panelR2S08Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S08Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR2SFX08, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR2SFX08, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR2SFX05.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR2SFX05.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR2SFX05.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR2SFX05.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR2SFX05ActionPerformed(evt);
            }
        });

        lblR2SFX05.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR2SFX05.setForeground(new java.awt.Color(0, 0, 0));
        lblR2SFX05.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR2SFX05.setText("blank");
        lblR2SFX05.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR2SFX05.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR2SFX05.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR2SFX05.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR2S05Layout = new javax.swing.GroupLayout(panelR2S05);
        panelR2S05.setLayout(panelR2S05Layout);
        panelR2S05Layout.setHorizontalGroup(
            panelR2S05Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S05Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX05, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR2S05Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX05, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR2S05Layout.setVerticalGroup(
            panelR2S05Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S05Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR2SFX05, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR2SFX05, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR2SFX07.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR2SFX07.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR2SFX07.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR2SFX07.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR2SFX07ActionPerformed(evt);
            }
        });

        lblR2SFX07.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR2SFX07.setForeground(new java.awt.Color(0, 0, 0));
        lblR2SFX07.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR2SFX07.setText("blank");
        lblR2SFX07.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR2SFX07.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR2SFX07.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR2SFX07.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR2S07Layout = new javax.swing.GroupLayout(panelR2S07);
        panelR2S07.setLayout(panelR2S07Layout);
        panelR2S07Layout.setHorizontalGroup(
            panelR2S07Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelR2S07Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(btnR2SFX07, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(panelR2S07Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX07, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR2S07Layout.setVerticalGroup(
            panelR2S07Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S07Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR2SFX07, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR2SFX07, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR2SFX06.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR2SFX06.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR2SFX06.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR2SFX06.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR2SFX06ActionPerformed(evt);
            }
        });

        lblR2SFX06.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR2SFX06.setForeground(new java.awt.Color(0, 0, 0));
        lblR2SFX06.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR2SFX06.setText("blank");
        lblR2SFX06.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR2SFX06.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR2SFX06.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR2SFX06.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR2S06Layout = new javax.swing.GroupLayout(panelR2S06);
        panelR2S06.setLayout(panelR2S06Layout);
        panelR2S06Layout.setHorizontalGroup(
            panelR2S06Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S06Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX06, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR2S06Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX06, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR2S06Layout.setVerticalGroup(
            panelR2S06Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S06Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR2SFX06, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR2SFX06, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR2SFX09.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR2SFX09.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR2SFX09.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR2SFX09.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR2SFX09ActionPerformed(evt);
            }
        });

        lblR2SFX09.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR2SFX09.setForeground(new java.awt.Color(0, 0, 0));
        lblR2SFX09.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR2SFX09.setText("blank");
        lblR2SFX09.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR2SFX09.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR2SFX09.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR2SFX09.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR2S09Layout = new javax.swing.GroupLayout(panelR2S09);
        panelR2S09.setLayout(panelR2S09Layout);
        panelR2S09Layout.setHorizontalGroup(
            panelR2S09Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S09Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX09, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR2S09Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX09, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR2S09Layout.setVerticalGroup(
            panelR2S09Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S09Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR2SFX09, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR2SFX09, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR2SFX10.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR2SFX10.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR2SFX10.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR2SFX10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR2SFX10ActionPerformed(evt);
            }
        });

        lblR2SFX10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR2SFX10.setForeground(new java.awt.Color(0, 0, 0));
        lblR2SFX10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR2SFX10.setText("blank");
        lblR2SFX10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR2SFX10.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR2SFX10.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR2SFX10.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR2S10Layout = new javax.swing.GroupLayout(panelR2S10);
        panelR2S10.setLayout(panelR2S10Layout);
        panelR2S10Layout.setHorizontalGroup(
            panelR2S10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S10Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(btnR2SFX10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(panelR2S10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR2S10Layout.setVerticalGroup(
            panelR2S10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR2SFX10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR2SFX10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR2SFX11.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR2SFX11.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR2SFX11.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR2SFX11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR2SFX11ActionPerformed(evt);
            }
        });

        lblR2SFX11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR2SFX11.setForeground(new java.awt.Color(0, 0, 0));
        lblR2SFX11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR2SFX11.setText("blank");
        lblR2SFX11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR2SFX11.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR2SFX11.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR2SFX11.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR2S11Layout = new javax.swing.GroupLayout(panelR2S11);
        panelR2S11.setLayout(panelR2S11Layout);
        panelR2S11Layout.setHorizontalGroup(
            panelR2S11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S11Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR2S11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR2S11Layout.setVerticalGroup(
            panelR2S11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR2SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR2SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR2SFX12.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR2SFX12.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR2SFX12.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR2SFX12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR2SFX12ActionPerformed(evt);
            }
        });

        lblR2SFX12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR2SFX12.setForeground(new java.awt.Color(0, 0, 0));
        lblR2SFX12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR2SFX12.setText("blank");
        lblR2SFX12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR2SFX12.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR2SFX12.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR2SFX12.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR2S12Layout = new javax.swing.GroupLayout(panelR2S12);
        panelR2S12.setLayout(panelR2S12Layout);
        panelR2S12Layout.setHorizontalGroup(
            panelR2S12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S12Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR2S12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR2S12Layout.setVerticalGroup(
            panelR2S12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR2SFX12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR2SFX12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR2SFX13.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR2SFX13.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR2SFX13.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR2SFX13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR2SFX13ActionPerformed(evt);
            }
        });

        lblR2SFX13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR2SFX13.setForeground(new java.awt.Color(0, 0, 0));
        lblR2SFX13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR2SFX13.setText("blank");
        lblR2SFX13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR2SFX13.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR2SFX13.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR2SFX13.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR2S13Layout = new javax.swing.GroupLayout(panelR2S13);
        panelR2S13.setLayout(panelR2S13Layout);
        panelR2S13Layout.setHorizontalGroup(
            panelR2S13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S13Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR2S13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR2S13Layout.setVerticalGroup(
            panelR2S13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR2SFX13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR2SFX13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR2SFX14.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR2SFX14.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR2SFX14.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR2SFX14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR2SFX14ActionPerformed(evt);
            }
        });

        lblR2SFX14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR2SFX14.setForeground(new java.awt.Color(0, 0, 0));
        lblR2SFX14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR2SFX14.setText("blank");
        lblR2SFX14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR2SFX14.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR2SFX14.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR2SFX14.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR2S14Layout = new javax.swing.GroupLayout(panelR2S14);
        panelR2S14.setLayout(panelR2S14Layout);
        panelR2S14Layout.setHorizontalGroup(
            panelR2S14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX14, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelR2S14Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnR2SFX14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelR2S14Layout.setVerticalGroup(
            panelR2S14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR2S14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnR2SFX14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblR2SFX14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout panelSFX2Layout = new javax.swing.GroupLayout(panelSFX2);
        panelSFX2.setLayout(panelSFX2Layout);
        panelSFX2Layout.setHorizontalGroup(
            panelSFX2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSFX2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(panelR2S01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR2S02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR2S03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR2S04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR2S05, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR2S06, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR2S07, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR2S08, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR2S09, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR2S10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR2S11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR2S12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR2S13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR2S14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSFX2Layout.setVerticalGroup(
            panelSFX2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSFX2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(panelSFX2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelR2S01, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR2S12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSFX2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(panelR2S11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR2S10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR2S09, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR2S08, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR2S07, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR2S06, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR2S05, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR2S04, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR2S03, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR2S02, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelR2S13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR2S14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(1, 1, 1))
        );

        getContentPane().add(panelSFX2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 446, -1, -1));

        panelR3S01.setMaximumSize(new java.awt.Dimension(90, 88));
        panelR3S01.setMinimumSize(new java.awt.Dimension(90, 88));

        btnR3SFX01.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR3SFX01.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR3SFX01.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR3SFX01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR3SFX01ActionPerformed(evt);
            }
        });

        lblR3SFX01.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR3SFX01.setForeground(new java.awt.Color(0, 0, 0));
        lblR3SFX01.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR3SFX01.setText("blank");
        lblR3SFX01.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR3SFX01.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR3SFX01.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR3SFX01.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR3S01Layout = new javax.swing.GroupLayout(panelR3S01);
        panelR3S01.setLayout(panelR3S01Layout);
        panelR3S01Layout.setHorizontalGroup(
            panelR3S01Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S01Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR3S01Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX01, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR3S01Layout.setVerticalGroup(
            panelR3S01Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S01Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnR3SFX01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblR3SFX01, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR3SFX02.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR3SFX02.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR3SFX02.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR3SFX02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR3SFX02ActionPerformed(evt);
            }
        });

        lblR3SFX02.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR3SFX02.setForeground(new java.awt.Color(0, 0, 0));
        lblR3SFX02.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR3SFX02.setText("blank");
        lblR3SFX02.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR3SFX02.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR3SFX02.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR3SFX02.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR3S02Layout = new javax.swing.GroupLayout(panelR3S02);
        panelR3S02.setLayout(panelR3S02Layout);
        panelR3S02Layout.setHorizontalGroup(
            panelR3S02Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelR3S02Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(btnR3SFX02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(panelR3S02Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX02, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR3S02Layout.setVerticalGroup(
            panelR3S02Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S02Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnR3SFX02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblR3SFX02, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR3SFX04.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR3SFX04.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR3SFX04.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR3SFX04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR3SFX04ActionPerformed(evt);
            }
        });

        lblR3SFX04.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR3SFX04.setForeground(new java.awt.Color(0, 0, 0));
        lblR3SFX04.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR3SFX04.setText("blank");
        lblR3SFX04.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR3SFX04.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR3SFX04.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR3SFX04.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR3S04Layout = new javax.swing.GroupLayout(panelR3S04);
        panelR3S04.setLayout(panelR3S04Layout);
        panelR3S04Layout.setHorizontalGroup(
            panelR3S04Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S04Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR3S04Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX04, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR3S04Layout.setVerticalGroup(
            panelR3S04Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S04Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR3SFX04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR3SFX04, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR3SFX03.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR3SFX03.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR3SFX03.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR3SFX03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR3SFX03ActionPerformed(evt);
            }
        });

        lblR3SFX03.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR3SFX03.setForeground(new java.awt.Color(0, 0, 0));
        lblR3SFX03.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR3SFX03.setText("blank");
        lblR3SFX03.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR3SFX03.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR3SFX03.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR3SFX03.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR3S03Layout = new javax.swing.GroupLayout(panelR3S03);
        panelR3S03.setLayout(panelR3S03Layout);
        panelR3S03Layout.setHorizontalGroup(
            panelR3S03Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S03Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR3S03Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX03, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR3S03Layout.setVerticalGroup(
            panelR3S03Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S03Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR3SFX03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR3SFX03, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR3SFX08.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR3SFX08.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR3SFX08.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR3SFX08.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR3SFX08ActionPerformed(evt);
            }
        });

        lblR3SFX08.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR3SFX08.setForeground(new java.awt.Color(0, 0, 0));
        lblR3SFX08.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR3SFX08.setText("blank");
        lblR3SFX08.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR3SFX08.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR3SFX08.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR3SFX08.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR3S08Layout = new javax.swing.GroupLayout(panelR3S08);
        panelR3S08.setLayout(panelR3S08Layout);
        panelR3S08Layout.setHorizontalGroup(
            panelR3S08Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S08Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX08, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR3S08Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX08, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR3S08Layout.setVerticalGroup(
            panelR3S08Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S08Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR3SFX08, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR3SFX08, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR3SFX05.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR3SFX05.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR3SFX05.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR3SFX05.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR3SFX05ActionPerformed(evt);
            }
        });

        lblR3SFX05.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR3SFX05.setForeground(new java.awt.Color(0, 0, 0));
        lblR3SFX05.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR3SFX05.setText("blank");
        lblR3SFX05.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR3SFX05.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR3SFX05.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR3SFX05.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR3S05Layout = new javax.swing.GroupLayout(panelR3S05);
        panelR3S05.setLayout(panelR3S05Layout);
        panelR3S05Layout.setHorizontalGroup(
            panelR3S05Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S05Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX05, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR3S05Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX05, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR3S05Layout.setVerticalGroup(
            panelR3S05Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S05Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR3SFX05, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR3SFX05, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR3SFX07.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR3SFX07.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR3SFX07.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR3SFX07.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR3SFX07ActionPerformed(evt);
            }
        });

        lblR3SFX07.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR3SFX07.setForeground(new java.awt.Color(0, 0, 0));
        lblR3SFX07.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR3SFX07.setText("blank");
        lblR3SFX07.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR3SFX07.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR3SFX07.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR3SFX07.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR3S07Layout = new javax.swing.GroupLayout(panelR3S07);
        panelR3S07.setLayout(panelR3S07Layout);
        panelR3S07Layout.setHorizontalGroup(
            panelR3S07Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelR3S07Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(btnR3SFX07, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(panelR3S07Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX07, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR3S07Layout.setVerticalGroup(
            panelR3S07Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S07Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR3SFX07, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR3SFX07, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR3SFX06.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR3SFX06.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR3SFX06.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR3SFX06.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR3SFX06ActionPerformed(evt);
            }
        });

        lblR3SFX06.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR3SFX06.setForeground(new java.awt.Color(0, 0, 0));
        lblR3SFX06.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR3SFX06.setText("blank");
        lblR3SFX06.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR3SFX06.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR3SFX06.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR3SFX06.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR3S06Layout = new javax.swing.GroupLayout(panelR3S06);
        panelR3S06.setLayout(panelR3S06Layout);
        panelR3S06Layout.setHorizontalGroup(
            panelR3S06Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S06Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX06, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR3S06Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX06, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR3S06Layout.setVerticalGroup(
            panelR3S06Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S06Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR3SFX06, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR3SFX06, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR3SFX09.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR3SFX09.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR3SFX09.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR3SFX09.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR3SFX09ActionPerformed(evt);
            }
        });

        lblR3SFX09.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR3SFX09.setForeground(new java.awt.Color(0, 0, 0));
        lblR3SFX09.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR3SFX09.setText("blank");
        lblR3SFX09.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR3SFX09.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR3SFX09.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR3SFX09.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR3S09Layout = new javax.swing.GroupLayout(panelR3S09);
        panelR3S09.setLayout(panelR3S09Layout);
        panelR3S09Layout.setHorizontalGroup(
            panelR3S09Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S09Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX09, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR3S09Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX09, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR3S09Layout.setVerticalGroup(
            panelR3S09Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S09Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR3SFX09, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR3SFX09, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR3SFX10.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR3SFX10.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR3SFX10.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR3SFX10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR3SFX10ActionPerformed(evt);
            }
        });

        lblR3SFX10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR3SFX10.setForeground(new java.awt.Color(0, 0, 0));
        lblR3SFX10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR3SFX10.setText("blank");
        lblR3SFX10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR3SFX10.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR3SFX10.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR3SFX10.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR3S10Layout = new javax.swing.GroupLayout(panelR3S10);
        panelR3S10.setLayout(panelR3S10Layout);
        panelR3S10Layout.setHorizontalGroup(
            panelR3S10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S10Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(btnR3SFX10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(panelR3S10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR3S10Layout.setVerticalGroup(
            panelR3S10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR3SFX10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR3SFX10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR3SFX11.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR3SFX11.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR3SFX11.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR3SFX11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR3SFX11ActionPerformed(evt);
            }
        });

        lblR3SFX11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR3SFX11.setForeground(new java.awt.Color(0, 0, 0));
        lblR3SFX11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR3SFX11.setText("blank");
        lblR3SFX11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR3SFX11.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR3SFX11.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR3SFX11.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR3S11Layout = new javax.swing.GroupLayout(panelR3S11);
        panelR3S11.setLayout(panelR3S11Layout);
        panelR3S11Layout.setHorizontalGroup(
            panelR3S11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S11Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR3S11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR3S11Layout.setVerticalGroup(
            panelR3S11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR3SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR3SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR3SFX12.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR3SFX12.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR3SFX12.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR3SFX12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR3SFX12ActionPerformed(evt);
            }
        });

        lblR3SFX12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR3SFX12.setForeground(new java.awt.Color(0, 0, 0));
        lblR3SFX12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR3SFX12.setText("blank");
        lblR3SFX12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR3SFX12.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR3SFX12.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR3SFX12.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR3S12Layout = new javax.swing.GroupLayout(panelR3S12);
        panelR3S12.setLayout(panelR3S12Layout);
        panelR3S12Layout.setHorizontalGroup(
            panelR3S12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S12Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR3S12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR3S12Layout.setVerticalGroup(
            panelR3S12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR3SFX12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR3SFX12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR3SFX13.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR3SFX13.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR3SFX13.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR3SFX13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR3SFX13ActionPerformed(evt);
            }
        });

        lblR3SFX13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR3SFX13.setForeground(new java.awt.Color(0, 0, 0));
        lblR3SFX13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR3SFX13.setText("blank");
        lblR3SFX13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR3SFX13.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR3SFX13.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR3SFX13.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR3S13Layout = new javax.swing.GroupLayout(panelR3S13);
        panelR3S13.setLayout(panelR3S13Layout);
        panelR3S13Layout.setHorizontalGroup(
            panelR3S13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S13Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR3S13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR3S13Layout.setVerticalGroup(
            panelR3S13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR3SFX13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR3SFX13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnR3SFX14.setMaximumSize(new java.awt.Dimension(22, 22));
        btnR3SFX14.setMinimumSize(new java.awt.Dimension(22, 22));
        btnR3SFX14.setPreferredSize(new java.awt.Dimension(22, 22));
        btnR3SFX14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR3SFX14ActionPerformed(evt);
            }
        });

        lblR3SFX14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 8)); // NOI18N
        lblR3SFX14.setForeground(new java.awt.Color(0, 0, 0));
        lblR3SFX14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR3SFX14.setText("blank");
        lblR3SFX14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblR3SFX14.setMaximumSize(new java.awt.Dimension(48, 78));
        lblR3SFX14.setMinimumSize(new java.awt.Dimension(48, 78));
        lblR3SFX14.setPreferredSize(new java.awt.Dimension(48, 78));

        javax.swing.GroupLayout panelR3S14Layout = new javax.swing.GroupLayout(panelR3S14);
        panelR3S14.setLayout(panelR3S14Layout);
        panelR3S14Layout.setHorizontalGroup(
            panelR3S14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX14, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelR3S14Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnR3SFX14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelR3S14Layout.setVerticalGroup(
            panelR3S14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR3S14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnR3SFX14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblR3SFX14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout panelSFX3Layout = new javax.swing.GroupLayout(panelSFX3);
        panelSFX3.setLayout(panelSFX3Layout);
        panelSFX3Layout.setHorizontalGroup(
            panelSFX3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSFX3Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(panelR3S01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR3S02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR3S03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR3S04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR3S05, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR3S06, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR3S07, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR3S08, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR3S09, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR3S10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR3S11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR3S12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR3S13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR3S14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSFX3Layout.setVerticalGroup(
            panelSFX3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSFX3Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(panelSFX3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelR3S01, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR3S12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSFX3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(panelR3S11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR3S10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR3S09, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR3S08, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR3S07, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR3S06, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR3S05, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR3S04, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR3S03, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelR3S02, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelR3S13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR3S14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(1, 1, 1))
        );

        getContentPane().add(panelSFX3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 528, -1, -1));

        btnPlayVL.setToolTipText("Play Video loop");
        btnPlayVL.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPlayVL.setMaximumSize(new java.awt.Dimension(22, 22));
        btnPlayVL.setMinimumSize(new java.awt.Dimension(22, 22));
        btnPlayVL.setPreferredSize(new java.awt.Dimension(22, 22));
        btnPlayVL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayVLActionPerformed(evt);
            }
        });
        getContentPane().add(btnPlayVL, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 336, -1, -1));

        btnStopVL.setToolTipText("Stop Video loop");
        btnStopVL.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStopVL.setMaximumSize(new java.awt.Dimension(22, 22));
        btnStopVL.setMinimumSize(new java.awt.Dimension(22, 22));
        btnStopVL.setPreferredSize(new java.awt.Dimension(22, 22));
        btnStopVL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopVLActionPerformed(evt);
            }
        });
        getContentPane().add(btnStopVL, new org.netbeans.lib.awtextra.AbsoluteConstraints(1148, 336, -1, -1));

        chkLoopVL.setText("Loop");
        chkLoopVL.setToolTipText("Loop VL (Video loop)");
        chkLoopVL.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chkLoopVL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLoopVLActionPerformed(evt);
            }
        });
        getContentPane().add(chkLoopVL, new org.netbeans.lib.awtextra.AbsoluteConstraints(1176, 338, -1, -1));

        chkMuteVL.setText("Mute");
        chkMuteVL.setToolTipText("Mute VL (Video loop)");
        chkMuteVL.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chkMuteVL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMuteVLActionPerformed(evt);
            }
        });
        getContentPane().add(chkMuteVL, new org.netbeans.lib.awtextra.AbsoluteConstraints(1234, 338, -1, -1));

        chkFitVL.setText("Fit");
        chkFitVL.setToolTipText("Fit / stretch video");
        chkFitVL.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chkFitVL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFitVLActionPerformed(evt);
            }
        });
        getContentPane().add(chkFitVL, new org.netbeans.lib.awtextra.AbsoluteConstraints(1293, 338, -1, -1));

        chkVLMode.setText("Loop Mode");
        chkVLMode.setToolTipText("Video Loop mode");
        chkVLMode.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chkVLMode.setMaximumSize(new java.awt.Dimension(65, 16));
        chkVLMode.setMinimumSize(new java.awt.Dimension(65, 16));
        chkVLMode.setPreferredSize(new java.awt.Dimension(65, 16));
        chkVLMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkVLModeActionPerformed(evt);
            }
        });
        getContentPane().add(chkVLMode, new org.netbeans.lib.awtextra.AbsoluteConstraints(685, 337, 100, 20));

        tfVideoLoop.setEditable(false);
        tfVideoLoop.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tfVideoLoop.setToolTipText("Click to select video");
        tfVideoLoop.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tfVideoLoop.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tfVideoLoop.setFocusable(false);
        tfVideoLoop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfVideoLoopMouseClicked(evt);
            }
        });
        getContentPane().add(tfVideoLoop, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 336, 270, 20));

        jMenuBar1.setName("mbrMain"); // NOI18N
        jMenuBar1.setOpaque(true);

        jMenu1.setText("File");
        jMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenu1.setName("menuFile"); // NOI18N

        itmNew.setMnemonic('N');
        itmNew.setText("New workspace");
        itmNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmNewActionPerformed(evt);
            }
        });
        jMenu1.add(itmNew);

        itemSave.setMnemonic('S');
        itemSave.setText("Save workspace");
        itemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSaveActionPerformed(evt);
            }
        });
        jMenu1.add(itemSave);

        itmLoad.setText("Load profile");
        itmLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmLoadActionPerformed(evt);
            }
        });
        jMenu1.add(itmLoad);

        jMenuBar1.add(jMenu1);

        menuPreferences.setText("Preferences");

        itmUITheme.setText("Theme");
        itmUITheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmUIThemeActionPerformed(evt);
            }
        });
        menuPreferences.add(itmUITheme);

        itmSettings.setText("Settings");
        itmSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmSettingsActionPerformed(evt);
            }
        });
        menuPreferences.add(itmSettings);

        jMenuBar1.add(menuPreferences);

        itmTools.setText("Tools");
        itmTools.setName("menuEdit"); // NOI18N

        itmResourceManager.setText("Resources");
        itmResourceManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmResourceManagerActionPerformed(evt);
            }
        });
        itmTools.add(itmResourceManager);

        itmPlugins.setText("Plugins");
        itmPlugins.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itmPluginsMouseClicked(evt);
            }
        });
        itmPlugins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmPluginsActionPerformed(evt);
            }
        });
        itmTools.add(itmPlugins);

        itmSystemTools.setText("System Tools");
        itmSystemTools.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmSystemToolsActionPerformed(evt);
            }
        });
        itmTools.add(itmSystemTools);

        jMenuBar1.add(itmTools);

        itmAbout.setText("About");
        itmAbout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itmAboutMouseClicked(evt);
            }
        });
        itmAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmAboutActionPerformed(evt);
            }
        });
        jMenuBar1.add(itmAbout);

        jMenuTime.setText("Time: ");
        jMenuTime.setEnabled(false);
        jMenuBar1.add(jMenuTime);

        itmAS.setForeground(new java.awt.Color(0, 153, 0));
        itmAS.setText("AS");
        itmAS.setToolTipText("Autosave is enabled");
        itmAS.setEnabled(false);
        itmAS.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jMenuBar1.add(itmAS);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearBGM1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearBGM1ActionPerformed
        if(!tfBGM1.getText().isEmpty()){
            if(clipBGM1 != null) {
                lastFrame1 = 0;

                clipBGM1.removeLineListener(listenBGM1);
                clipBGM1.stop();
                clipBGM1.addLineListener(listenBGM1);
            }
            clipBGM1 = null;

            String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
            btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));

            playing1 = 0;
            pause1 = 0;

            tfLastOperation.setText("[CLEARED BGM1]:: " + tfBGM1.getText());
            tfLastOperation.setToolTipText("[CLEARED BGM1]:: " + tfBGM1.getText());
            tfLastOperation.moveCaretPosition(0);
            tfBGM1.setText("");
        }
    }//GEN-LAST:event_btnClearBGM1ActionPerformed

    private void togLinkBGMVolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togLinkBGMVolActionPerformed
        if(bgmVolumeLink == 0){
            togLinkBGMVol.setText("ON");
            
            if(clipBGM1 != null && clipBGM1.isRunning()) {
                int valueVol1 = (int)volBGM1.getValue();
                volBGM2.setValue(100 - valueVol1);
            }
            else if(clipBGM2 != null && clipBGM2.isRunning()) {
                int valueVol2 = (int)volBGM2.getValue();
                volBGM1.setValue(100 - valueVol2);
            }
            else {
                int valueVol1 = (int)volBGM1.getValue();
                volBGM2.setValue(100 - valueVol1);
            }
            
            bgmVolumeLink = 1;
        }
        else {
            togLinkBGMVol.setText("OFF");
            bgmVolumeLink = 0;
        }
    }//GEN-LAST:event_togLinkBGMVolActionPerformed

    private void btnPlayPauseBGM1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayPauseBGM1ActionPerformed
        playPauseBgm1();
    }//GEN-LAST:event_btnPlayPauseBGM1ActionPerformed
    
    public void playPauseBgm1() {
        listenBGM1 = (LineEvent event) -> {
            if(event.getType() == LineEvent.Type.STOP) {
                playing1 = 0;
                pause1 = 0;
                lastFrame1 = 0;

                String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon1));

                if(loop1 == 1) {
                    clipBGM1.setFramePosition(0);
                    clipBGM1.start();

                    playing1 = 1;

                    String btnIcon2 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                    btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon2));
                }
            }
        };

        if(clipBGM1 == null) {
            if(tfBGM1.getText().isEmpty()){
                tfLastOperation.setText("[BGM1]: NOTHING TO PLAY");

                errorOccurred = 1;
            }
            else {
                try {
                    String musicPath = bfolder + "\\" + tfBGM1.getText() + ".wav";
                    loadClipBGM1(new File(musicPath));
                    fcBGM1 = (FloatControl)clipBGM1.getControl(FloatControl.Type.MASTER_GAIN);
                    if(volBGM1.getValue() == 100) {
                        fcBGM1.setValue(0f);
                    }
                    else {
                        fcBGM1.setValue(bgmVol1); // float value
                    }

                    clipBGM1.addLineListener(listenBGM1);
                    clipBGM1.start();
                }
                catch(IOException ioe){
                    JOptionPane.showMessageDialog(HappyButtons.mf, 
                            "Specified BGM may be gone missing or suddenly deleted.\nIf not, inform the developer for this bug", 
                            "File IO exception occured", 
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
                            "BGM file may be broken/corrupted. Or make sure the audio file has genuine WAV format.\nChanging the file extension by renaming it will NOT do the trick", 
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
                clipBGM1.removeLineListener(listenBGM1);
                clipBGM1.stop();
                clipBGM1.addLineListener(listenBGM1);
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
            }
            else if(playing1 == 1 && pause1 == 0){
                pause1 = 1;

                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
            }
            else if(playing1 == 1 && pause1 == 1){
                pause1 = 0;

                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
            }

        }
        else {
            errorOccurred = 0;
        }
    }
    
    private void btnAddBGMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBGMActionPerformed
        Object[] options = {"Add from App Resource", "Add from My files"};
        
        int choice = JOptionPane.showOptionDialog(HappyButtons.mf, "Select path where to get BGM files",
                "Get BGM source",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        if(choice == 0){
            AddBGMFrame addBgmFrame = new AddBGMFrame(HappyButtons.mf, true);
            addBgmFrame.setVisible(true);
        }
        else if(choice == 1) {
            JFileChooser fc = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("MP3 or WAV File", "wav", "mp3");
            fc.setFileFilter(filter);
            fc.setMultiSelectionEnabled(true);
            fc.setPreferredSize(new Dimension(1000, 600));
            int returnValue = fc.showOpenDialog(HappyButtons.mf);

            File[] selectedFiles = fc.getSelectedFiles();

            for(File file : selectedFiles) {
                if(Utility.getFileExtension(file.toString()).equalsIgnoreCase("wav")) { // Music file is in wave format
                    try {
                        FileChannel src = new FileInputStream(file.getAbsolutePath()).getChannel();
                        File destCheck = new File(HappyButtons.documentsPath + "\\HappyButtons\\bg\\" + file.getName());

                        if(!destCheck.exists()) {
                            FileChannel dest = new FileOutputStream(HappyButtons.documentsPath + "\\HappyButtons\\bg\\" + file.getName()).getChannel();

                            src.transferTo(0,src.size(),dest);

                            src.close();
                            dest.close();
                        }

                        if(!blist.contains(Utility.renameListName(file.getName(), "wav"))) {
                            blist.addElement(Utility.renameListName(file.getName(), "wav"));
                            tfLastOperation.setText("[ADDED BGM]:: " + file.getName());
                        }

                        listBGM.setModel(blist);
                    }
                    catch(IOException ex) {
                        JOptionPane.showMessageDialog(HappyButtons.mf,
                            "Error reading/writing file",
                            "IO Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
                else { // Music file is in mp3 format
                    try {
                        FileChannel src = new FileInputStream(file.getAbsolutePath()).getChannel();
                        String strReplace = Utility.renameListName(file.getName(), "mp3") + ".wav";
                        File destCheck = new File(HappyButtons.documentsPath + "\\HappyButtons\\bg\\" + strReplace);

                        if(!destCheck.exists()) {
                            AudioAttributes audio = new AudioAttributes();
                            audio.setCodec("pcm_s16le");
                            audio.setBitRate(16);
                            audio.setChannels(1);
                            audio.setSamplingRate(44100);

                            EncodingAttributes attrs = new EncodingAttributes();
                            attrs.setOutputFormat("wav");
                            attrs.setAudioAttributes(audio);

                            File source = new File(file.toString());
                            String str = HappyButtons.documentsPath + Utility.strDoubleSlash("\\HappyButtons\\bg\\") + Utility.renameListName(file.getName(), "mp3")  + ".wav";
                            File target = new File(str);

                            Encoder encoder = new Encoder();
                            encoder.encode(new MultimediaObject(source), target, attrs);
                        }

                        if(!blist.contains(Utility.renameListName(file.getName(), "mp3"))) {
                            blist.addElement(Utility.renameListName(file.getName(), "mp3"));
                            tfLastOperation.setText("[ADDED BGM]:: " + Utility.renameListName(file.getName(), "mp3"));
                            tfLastOperation.setToolTipText("[ADDED BGM]:: " + Utility.renameListName(file.getName(), "mp3"));
                            tfLastOperation.moveCaretPosition(0);
                        }

                        listBGM.setModel(blist);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // autosave
            if(returnValue == fc.APPROVE_OPTION) {
                autosave();
            }
        }
    }//GEN-LAST:event_btnAddBGMActionPerformed

    private void btnAddSFXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSFXActionPerformed
        Object[] options = {"Add from App Resource", "Add from My files"};
        
        int choice = JOptionPane.showOptionDialog(HappyButtons.mf, "Select path where to get SFX files",
                "Get SFX source",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        if(choice == 0) {
            AddSFXFrame addSfxFrame = new AddSFXFrame(HappyButtons.mf, true);
            addSfxFrame.setVisible(true);
        }
        else if(choice == 1) {
//            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            }
//            catch(Exception e) {
//                e.printStackTrace();
//            }
            
            JFileChooser fc = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("MP3 or WAV File", "wav", "mp3");
            fc.setFileFilter(filter);
            fc.setMultiSelectionEnabled(true);
            fc.setPreferredSize(new Dimension(1000, 600));
            int returnValue = fc.showOpenDialog(HappyButtons.mf);

            File[] selectedFiles = fc.getSelectedFiles();

            for(File file : selectedFiles) {
                if(Utility.getFileExtension(file.toString()).equalsIgnoreCase("wav")) { // Music file is in wave format
                    try {
                        FileChannel src = new FileInputStream(file.getAbsolutePath()).getChannel();
                        File destCheck = new File(HappyButtons.documentsPath + "\\HappyButtons\\sfx\\" + file.getName());

                        if(!destCheck.exists()) {
                            FileChannel dest = new FileOutputStream(HappyButtons.documentsPath + "\\HappyButtons\\sfx\\" + file.getName()).getChannel();

                            src.transferTo(0,src.size(),dest);

                            src.close();
                            dest.close();
                        }

                        if(!slist.contains(Utility.renameListName(file.getName(), "wav"))) {
                            slist.addElement(Utility.renameListName(file.getName(), "wav"));
                            tfLastOperation.setText("[ADDED SFX]:: " + file.getName());
                        }

                        listSFX.setModel(slist);
                    }
                    catch(IOException ex) {
                        JOptionPane.showMessageDialog(HappyButtons.mf,
                            "Error reading/writing file",
                            "IO Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
                else { // Music file is in mp3 format
                    try {
                        FileChannel src = new FileInputStream(file.getAbsolutePath()).getChannel();
                        String strReplace = Utility.renameListName(file.getName(), "mp3") + ".wav";
                        File destCheck = new File(HappyButtons.documentsPath + "\\HappyButtons\\sfx\\" + strReplace);

                        if(!destCheck.exists()) {
                            AudioAttributes audio = new AudioAttributes();
                            audio.setCodec("pcm_s16le");
                            audio.setBitRate(16);
                            audio.setChannels(1);
                            audio.setSamplingRate(44100);

                            // audio.setBitRate(new Integer(16));
                            // audio.setChannels(new Integer(1));
                            // audio.setSamplingRate(new Integer(44100));

                            EncodingAttributes attrs = new EncodingAttributes();
                            attrs.setOutputFormat("wav");
                            attrs.setAudioAttributes(audio);

                            File source = new File(file.toString());
                            String str = HappyButtons.documentsPath + Utility.strDoubleSlash("\\HappyButtons\\sfx\\") + Utility.renameListName(file.getName(), "mp3")  + ".wav";
                            File target = new File(str);

                            Encoder encoder = new Encoder();
                            encoder.encode(new MultimediaObject(source), target, attrs);
                        }
                        
                        if(!slist.contains(Utility.renameListName(file.getName(), "mp3"))) {
                            slist.addElement(Utility.renameListName(file.getName(), "mp3"));
                            tfLastOperation.setText("[ADDED SFX]:: " + Utility.renameListName(file.getName(), "mp3"));
                            tfLastOperation.setToolTipText("[ADDED SFX]:: " + Utility.renameListName(file.getName(), "mp3"));
                            tfLastOperation.moveCaretPosition(0);
                        }

                        listSFX.setModel(slist);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            
            // autosave
            if(returnValue == fc.APPROVE_OPTION) {
                autosave();
            }
        }
    }//GEN-LAST:event_btnAddSFXActionPerformed

    private void btnClearBGM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearBGM2ActionPerformed
        if(!tfBGM2.getText().isEmpty()){
            if(clipBGM2 != null) {
                lastFrame2 = 0;

                clipBGM2.removeLineListener(listenBGM2);
                clipBGM2.stop();
                clipBGM2.addLineListener(listenBGM2);
            }
            clipBGM2 = null;

            String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
            btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));

            playing2 = 0;
            pause2 = 0;

            tfLastOperation.setText("[CLEARED BGM2]:: " + tfBGM2.getText());
            tfLastOperation.setToolTipText("[CLEARED BGM2]:: " + tfBGM2.getText());
            tfLastOperation.moveCaretPosition(0);
            tfBGM2.setText("");
        }
    }//GEN-LAST:event_btnClearBGM2ActionPerformed

    private void btnPlayPauseBGM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayPauseBGM2ActionPerformed
        playPauseBgm2();
    }//GEN-LAST:event_btnPlayPauseBGM2ActionPerformed
    
    public void playPauseBgm2() {
        listenBGM2 = (LineEvent event) -> {
            if(event.getType() == LineEvent.Type.STOP) {
                playing2 = 0;
                pause2 = 0;
                lastFrame2 = 0;

                String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon1));

                if(loop2 == 1) {
                    clipBGM2.setFramePosition(0);
                    clipBGM2.start();

                    playing2 = 1;

                    String btnIcon2 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                    btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon2));
                }
            }
        };

        if(clipBGM2 == null) {
            if(tfBGM2.getText().isEmpty()){
                tfLastOperation.setText("[BGM2]: NOTHING TO PLAY");

                errorOccurred = 1;
            }
            else {
                try {
                    String musicPath = bfolder + "\\" + tfBGM2.getText() + ".wav";
                    loadClipBGM2(new File(musicPath));
                    fcBGM2 = (FloatControl)clipBGM2.getControl(FloatControl.Type.MASTER_GAIN);
                    if(volBGM2.getValue() == 100) {
                        fcBGM2.setValue(0f);
                    }
                    else {
                        fcBGM2.setValue(bgmVol2); // float value
                    }

                    clipBGM2.addLineListener(listenBGM2);
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
                            "BGM file may be broken/corrupted. Or make sure the audio file has genuine WAV format.\nChanging the file extension by renaming it will NOT do the trick", 
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
                clipBGM2.removeLineListener(listenBGM2);
                clipBGM2.stop();
                clipBGM2.addLineListener(listenBGM2);
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
            }
            else if(playing2 == 1 && pause2 == 0){
                pause2 = 1;

                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
            }
            else if(playing2 == 1 && pause2 == 1){
                pause2 = 0;

                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
            }

        }
        else {
            errorOccurred = 0;
        }
    }
    
    private void btnDeleteBGMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteBGMActionPerformed
        if(selectedBGMItem != "") {
            tfLastOperation.setText("[REMOVE BGM]:: " + selectedBGMItem);
            tfLastOperation.setToolTipText("[REMOVE BGM]:: " + selectedBGMItem);
            tfLastOperation.moveCaretPosition(0);
            blist.removeElement(selectedBGMItem);
            selectedBGMItem = "";
            
            // autosave
            autosave();
//           File deleteItem = new File(HappyButtons.documentsPathDoubleSlash + "\\HappyButtons\\bg\\" + selectedBGMItem + ".wav");
//            if(deleteItem.delete()) {
//                tfLastOperation.setText("[DELETE BGM]:: " + selectedBGMItem);
//                blist.removeElement(selectedBGMItem);
//                selectedBGMItem = "";
//            }
//            else {
//                JOptionPane.showMessageDialog(HappyButtons.mf, 
//                    "An error occurred when deleting " + selectedBGMItem + ".wav", 
//                    "File deletion error", 
//                    JOptionPane.ERROR_MESSAGE);
//                selectedBGMItem = "";
//            } 
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
            if(Utility.searchSFX(selectedSFXItem) > 0) {
                int confirm = JOptionPane.showConfirmDialog(HappyButtons.mf, 
                                "\'" + selectedSFXItem + "\' is used on one or more of the SFX buttons. Are you sure you want to remove this item from SFX list?\n", 
                                "In-use item", 
                                JOptionPane.YES_NO_OPTION);
                
                if(confirm == 0) {
                    Utility.blankSFXLabel(selectedSFXItem);
                    
                    tfLastOperation.setText("[REMOVE SFX]:: " + selectedSFXItem);
                    tfLastOperation.setToolTipText("[REMOVE SFX]:: " + selectedSFXItem);
                    tfLastOperation.moveCaretPosition(0);
                    slist.removeElement(selectedSFXItem);
                    selectedSFXItem = "";
                }
            }
            else {
                tfLastOperation.setText("[REMOVE SFX]:: " + selectedSFXItem);
                tfLastOperation.setToolTipText("[REMOVE SFX]:: " + selectedSFXItem);
                tfLastOperation.moveCaretPosition(0);
                slist.removeElement(selectedSFXItem);
                selectedSFXItem = "";
            }
            
            // autosave
            autosave();
            
//           File deleteItem = new File(HappyButtons.documentsPathDoubleSlash + "\\HappyButtons\\sfx\\" + selectedSFXItem + ".wav");
//            if(deleteItem.delete()) {
//                tfLastOperation.setText("[DELETE SFX]:: " + selectedSFXItem);
//                slist.removeElement(selectedSFXItem);
//                selectedSFXItem = "";
//            }
//            else {
//                JOptionPane.showMessageDialog(HappyButtons.mf, 
//                    "An error occurred when deleting " + selectedSFXItem + ".wav", 
//                    "File deletion error", 
//                    JOptionPane.ERROR_MESSAGE);
//                selectedSFXItem = "";
//            } 
        }
        else {
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                        "Please select an item first from SFX list", 
                        "Nothing selected", 
                        JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteSFXActionPerformed

    private void itemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSaveActionPerformed
        prepareSave();
        
        SaveFrame saveFrame = new SaveFrame(HappyButtons.mf, true);
        saveFrame.setVisible(true);
        
        if(!saveFrame.isShowing()) {
            if(!"error".equals(savedProfile)) {
                super.setTitle("Happy Buttons - (" + savedProfile + ")");
            }
        }
    }//GEN-LAST:event_itemSaveActionPerformed

    public void prepareSave() {
        // BGMs
        int listBGMSize = listBGM.getModel().getSize();
        strBGM = "";
        
        for(int ctr = 0; ctr < listBGMSize; ctr++){
            if(ctr == 0) {
                strBGM = listBGM.getModel().getElementAt(ctr);
            }
            else if(ctr > 0 && ctr <= (listBGMSize - 1)) {
                strBGM = strBGM + ":" + listBGM.getModel().getElementAt(ctr);
            }
        }
        
        // SFXs
        int listSFXSize = listSFX.getModel().getSize();
        strSFX = "";
        
        for(int ctr = 0; ctr < listSFXSize; ctr++){
            if(ctr == 0) {
                strSFX = listSFX.getModel().getElementAt(ctr);
            }
            else if(ctr > 0 && ctr <= (listSFXSize - 1)) {
                strSFX = strSFX + ":" + listSFX.getModel().getElementAt(ctr);
            }
        }
        
        // Happy Loop videos
        int cboHappyLoopSize = cboModelForLoop.getSize();
        strVidLoop = "";
        
        for(int ctr = 0; ctr < cboHappyLoopSize; ctr++) {
            if(ctr == 0) {
                strVidLoop = cboModelForLoop.getElementAt(ctr).toString();
            }
            else if(ctr > 0 && ctr <= (cboHappyLoopSize - 1)) {
                strVidLoop = strVidLoop + ":" + cboModelForLoop.getElementAt(ctr).toString();
            }
        }
        
        int cboHappyListSize = cboModelPlaylist.getSize();
        strVidList = "";
        
        for(int ctr = 0; ctr < cboHappyListSize; ctr++) {
            if(ctr == 0) {
                strVidList = cboModelPlaylist.getElementAt(ctr).toString();
            }
            else if(ctr > 0 && ctr <= (cboHappyListSize - 1)) {
                strVidList = strVidList + ":" + cboModelPlaylist.getElementAt(ctr).toString();
            }
        }
        
        // Mp3
        strMp3List = "";
        
        for(int ctr = 0; ctr < MainFrame.mlist.size(); ctr++){
            if(ctr == 0) {
                MainFrame.strMp3List = MainFrame.mlist.getElementAt(ctr).toString();
            }
            else if(ctr > 0 && ctr <= (MainFrame.mlist.size() - 1)) {
                MainFrame.strMp3List = MainFrame.strMp3List + ":" + MainFrame.mlist.getElementAt(ctr).toString();
            }
        }
    }
    
    private void itmNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmNewActionPerformed
        if((clipBGM1 != null && clipBGM1.isRunning()) || 
        (clipBGM2 != null && clipBGM2.isRunning()) || 
        (clipMp3 != null && clipMp3.isRunning()) || 
        vlcjPlaying == 1) {
            tfLastOperation.setText("Cannot create new workspace, please STOP running BGM / SFX / Mp3 or playing VIDEO LOOP");
            tfLastOperation.setToolTipText("Cannot create new workspace, please STOP running BGM / SFX / Mp3 or playing VIDEO LOOP");
            tfLastOperation.moveCaretPosition(0);
        }
        else {
            super.setTitle("Happy Buttons");
            
            clipBGM1 = null; clipBGM2 = null; clipSFX = null; clipMp3 = null;
            blist.removeAllElements(); slist.removeAllElements(); mlist.removeAllElements();
            // cboVidLoop.removeAllItems();
            cboModelForLoop.removeAllElements(); cboModelPlaylist.removeAllElements();
            bgmVolumeLink = 0;
            
            draggedList = -1;
            errorOccurred = 0;
            
            playing1 = 0; playing2 = 0; pause1 = 0; pause2 = 0; mp3Playing = 0; mp3Pause = 0;
            sfxPlaying = 0;
            lastFrame1 = 0; lastFrame2 = 0; mp3LastFrame = 0;
            chkSinglePlay = 1; chkStopBGM = 0; chkVLLoop = 1; chkVLMute = 1; chkShuffle = 1;
            loop1 = 1; loop2 = 1; loopMp3 = 1;
            
            bgmVol1 = 100f; bgmVol2 = 100f; sfxVol = 100f; mp3Vol = 100f;
            
            selectedBGMItem = ""; selectedSFXItem = ""; selectedMp3Item = "";
            profileName1 = ""; profileName2 = ""; profileName3 = ""; profileName4 = ""; profileName5 = "";
            loadedProfile = ""; savedProfile = ""; strBGM = ""; strSFX = ""; strVidLoop = ""; strVidList = ""; strMp3List = "";
            loadedIndexProfile = -1;
            
            tfBGM1.setText("");
            tfBGM2.setText("");
            tfMp3.setText("");
            tfMp3.setToolTipText("");
            tfLastOperation.setText("NEW WORKSPACE CREATED");
            tfLastOperation.setToolTipText("NEW WORKSPACE CREATED");
            
            volBGM1.setValue(100); volBGM2.setValue(100); volSFX.setValue(100);
            chkLoop1.setSelected(true); chkLoop2.setSelected(true);
            chkSP.setSelected(true); chkIB.setSelected(false);
            chkLoopVL.setSelected(true); chkMuteVL.setSelected(true); 
            togLinkBGMVol.setSelected(false); togLinkBGMVol.setText("OFF");
            
            lblR1SFX01.setText("blank"); lblR1SFX02.setText("blank"); lblR1SFX03.setText("blank"); lblR1SFX04.setText("blank");
            lblR1SFX05.setText("blank"); lblR1SFX06.setText("blank"); lblR1SFX07.setText("blank"); lblR1SFX08.setText("blank");
            lblR1SFX09.setText("blank"); lblR1SFX10.setText("blank"); lblR1SFX11.setText("blank"); lblR1SFX12.setText("blank");
            lblR1SFX13.setText("blank"); lblR1SFX14.setText("blank");
            
            lblR2SFX01.setText("blank"); lblR2SFX02.setText("blank"); lblR2SFX03.setText("blank"); lblR2SFX04.setText("blank");
            lblR2SFX05.setText("blank"); lblR2SFX06.setText("blank"); lblR2SFX07.setText("blank"); lblR2SFX08.setText("blank");
            lblR2SFX09.setText("blank"); lblR2SFX10.setText("blank"); lblR2SFX11.setText("blank"); lblR2SFX12.setText("blank");
            lblR2SFX13.setText("blank"); lblR2SFX14.setText("blank");
            
            lblR3SFX01.setText("blank"); lblR3SFX02.setText("blank"); lblR3SFX03.setText("blank"); lblR3SFX04.setText("blank");
            lblR3SFX05.setText("blank"); lblR3SFX06.setText("blank"); lblR3SFX07.setText("blank"); lblR3SFX08.setText("blank");
            lblR3SFX09.setText("blank"); lblR3SFX10.setText("blank"); lblR3SFX11.setText("blank"); lblR3SFX12.setText("blank");
            lblR3SFX13.setText("blank"); lblR3SFX14.setText("blank");
            
            HappyButtons.canAutosave = 0;
            DBOperations.indexDB = -1;
            
            mp3SortedQueue = new String[0]; mp3ShuffledQueue = new String[0];
            mp3MainQueue = new String[0]; mp3Queue = new String[0];
            vlQueue = new String[0]; vidQueue = new String[0];
            
            if(mp3 != null) {
                if(mp3.isShowing()) {
                    mp3.volMp3.setValue(100);
                    mp3Audio = 1; mp3Shuffle = 1; mp3Repeat = 1;
                    mp3.listMp3.removeAll();
                    mp3.lblSongMp3.setText("");

                    if(HappyButtons.uiTheme.equals("light")) {
                        String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\shuffle_16px.png");
                        mp3.lblShuffle.setIcon(new javax.swing.ImageIcon(icon));

                        String icon2 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\repeat_16px.png");
                        mp3.lblRepeat.setIcon(new javax.swing.ImageIcon(icon2));
                    }
                    else if(HappyButtons.uiTheme.equals("dark")) {
                        String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_shuffle_16px.png");
                        mp3.lblShuffle.setIcon(new javax.swing.ImageIcon(icon));

                        String icon2 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_repeat_16px.png");
                        mp3.lblRepeat.setIcon(new javax.swing.ImageIcon(icon2));
                    }

                    mp3.lblShuffle.setToolTipText("Shuffle on");
                    mp3.lblRepeat.setToolTipText("Repeat list");

                    mp3.dispose();
                    mp3FrameOpened = 0;
                }
            }
        }
    }//GEN-LAST:event_itmNewActionPerformed

    private void itmLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmLoadActionPerformed
        LoadFrame loadFrame = new LoadFrame(HappyButtons.mf, true);
        loadFrame.setVisible(true);
        
        if(!loadFrame.isShowing()) {
            if(!"error".equals(loadedProfile)) {
                super.setTitle("Happy Buttons - (" + loadedProfile + ")");
            }
        }
    }//GEN-LAST:event_itmLoadActionPerformed

    private void chkSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSPActionPerformed
        if(chkSP.isSelected()) {
            chkSinglePlay = 1;
        }
        else {
            chkSinglePlay = 0;
        }
    }//GEN-LAST:event_chkSPActionPerformed

    private void chkIBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIBActionPerformed
        if(chkIB.isSelected()) {
            chkStopBGM = 1;
        }
        else {
            chkStopBGM = 0;
        }
    }//GEN-LAST:event_chkIBActionPerformed

    private void tfBGM1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tfBGM1PropertyChange
        
    }//GEN-LAST:event_tfBGM1PropertyChange

    private void tfBGM2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tfBGM2PropertyChange
        
    }//GEN-LAST:event_tfBGM2PropertyChange

    private void btnStopBGM1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopBGM1ActionPerformed
        if(clipBGM1 != null) {
            lastFrame1 = 0;

            clipBGM1.removeLineListener(listenBGM1);
            clipBGM1.stop();
            clipBGM1.addLineListener(listenBGM1);

            String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
            btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));

            playing1 = 0;
            pause1 = 0;
            clipBGM1 = null;
        }
    }//GEN-LAST:event_btnStopBGM1ActionPerformed

    private void btnStopBGM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopBGM2ActionPerformed
        if(clipBGM2 != null) {
            lastFrame2 = 0;

            clipBGM2.removeLineListener(listenBGM2);
            clipBGM2.stop();
            clipBGM2.addLineListener(listenBGM2);

            String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
            btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));

            playing2 = 0;
            pause2 = 0;
            clipBGM2 = null;
        }
    }//GEN-LAST:event_btnStopBGM2ActionPerformed

    private void volBGM1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_volBGM1StateChanged
        float f = (float)volBGM1.getValue();
                
        try {
            if(f >= 50) {
                bgmVol1 = 0 - ((100f - f)*(0.18f));
            }

            if(f < 50 && f >= 25) {
                bgmVol1 = -9 + (50f - f)*(-0.36f);
            }

            if(f < 25 && f >= 10) {
                bgmVol1 = -18 + (25f - f)*(-0.8f);
            }

            if(f < 10) {
                bgmVol1 = -30f + (10f - f)*(-5f);
            }

            if(playing1 == 1) {
                fcBGM1.setValue(bgmVol1); // float value
                
//                fcBGM1.shift(fcBGM1.getValue(), bgmVol1, 1);
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
    }//GEN-LAST:event_volBGM1StateChanged

    private void volBGM2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_volBGM2StateChanged
        float f = (float)volBGM2.getValue();
                
        try {
            if(f >= 50) {
                bgmVol2 = 0 - ((100f - f)*(0.18f));
            }

            if(f < 50 && f >= 25) {
                bgmVol2 = -9 + (50f - f)*(-0.36f);
            }

            if(f < 25 && f >= 10) {
                bgmVol2 = -18 + (25f - f)*(-0.8f);
            }

            if(f < 10) {
                bgmVol2 = -30f + (10f - f)*(-5f);
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
    }//GEN-LAST:event_volBGM2StateChanged

    private void volSFXStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_volSFXStateChanged
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
    }//GEN-LAST:event_volSFXStateChanged

    public static void mp3VolChange() {
        float f = (float)mp3.volMp3.getValue();
        
        if(f == 0) {
            MainFrame.mp3Audio = 0;
            
            // set icon
            if(mp3.theme.equals("light")) {
                String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\no_audio_16px.png");
                mp3.lblAudio.setIcon(new javax.swing.ImageIcon(icon));
            }
            else if(mp3.theme.equals("dark")) {
                String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_no_audio_16px.png");
                mp3.lblAudio.setIcon(new javax.swing.ImageIcon(icon));
            }
        }
        else {
            mp3Audio = 1;
            
            // set icon
            if(mp3.theme.equals("light")) {
                String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\audio_16px.png");
                mp3.lblAudio.setIcon(new javax.swing.ImageIcon(icon));
            }
            else if(mp3.theme.equals("dark")) {
                String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_audio_16px.png");
                mp3.lblAudio.setIcon(new javax.swing.ImageIcon(icon));
            }
        }
                
        try { // calculation same as BGM
            if(f >= 50) {
                mp3Vol = 0 - ((100f - f)*(0.18f));
            }

            if(f < 50 && f >= 25) {
                mp3Vol = -9 + (50f - f)*(-0.36f);
            }

            if(f < 25 && f >= 10) {
                mp3Vol = -18 + (25f - f)*(-0.8f);
            }

            if(f < 10) {
                mp3Vol = -30f + (10f - f)*(-5f);
            }

            fcMp3.setValue(mp3Vol); // float value
        }
        catch(Exception ex) {

        }

        mp3VolumeValue = mp3.volMp3.getValue();
        mp3.lblAudio.setText(Integer.toString((int)mp3.volMp3.getValue()));
    }
    
    private void btnStopSFXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopSFXActionPerformed
        if(clipSFX != null) {
            clipSFX.stop();
            clipSFX.close();
            clipSFX = null;
        }
    }//GEN-LAST:event_btnStopSFXActionPerformed

    private void tfBGM1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfBGM1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfBGM1KeyPressed

    private void itmResourceManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmResourceManagerActionPerformed
        ResourceManagerFrame rsFrame = new ResourceManagerFrame(HappyButtons.mf, true);
        rsFrame.setVisible(true);
    }//GEN-LAST:event_itmResourceManagerActionPerformed

    private void itmAboutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itmAboutMouseClicked
        AboutFrame aboutFrame = new AboutFrame(HappyButtons.mf, true);
        aboutFrame.setVisible(true);
    }//GEN-LAST:event_itmAboutMouseClicked

    private void btnR1SFX14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX14ActionPerformed
        playSFX(lblR1SFX14.getText());
    }//GEN-LAST:event_btnR1SFX14ActionPerformed

    private void btnR1SFX13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX13ActionPerformed
        playSFX(lblR1SFX13.getText());
    }//GEN-LAST:event_btnR1SFX13ActionPerformed

    private void btnR1SFX12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX12ActionPerformed
        playSFX(lblR1SFX12.getText());
    }//GEN-LAST:event_btnR1SFX12ActionPerformed

    private void btnR1SFX11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX11ActionPerformed
        playSFX(lblR1SFX11.getText());
    }//GEN-LAST:event_btnR1SFX11ActionPerformed

    private void btnR1SFX10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX10ActionPerformed
        playSFX(lblR1SFX10.getText());
    }//GEN-LAST:event_btnR1SFX10ActionPerformed

    private void btnR1SFX09ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX09ActionPerformed
        playSFX(lblR1SFX09.getText());
    }//GEN-LAST:event_btnR1SFX09ActionPerformed

    private void btnR1SFX06ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX06ActionPerformed
        playSFX(lblR1SFX06.getText());
    }//GEN-LAST:event_btnR1SFX06ActionPerformed

    private void btnR1SFX07ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX07ActionPerformed
        playSFX(lblR1SFX07.getText());
    }//GEN-LAST:event_btnR1SFX07ActionPerformed

    private void btnR1SFX05ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX05ActionPerformed
        playSFX(lblR1SFX05.getText());
    }//GEN-LAST:event_btnR1SFX05ActionPerformed

    private void btnR1SFX08ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX08ActionPerformed
        playSFX(lblR1SFX08.getText());
    }//GEN-LAST:event_btnR1SFX08ActionPerformed

    private void btnR1SFX03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX03ActionPerformed
        playSFX(lblR1SFX03.getText());
    }//GEN-LAST:event_btnR1SFX03ActionPerformed

    private void btnR1SFX04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX04ActionPerformed
        playSFX(lblR1SFX04.getText());
    }//GEN-LAST:event_btnR1SFX04ActionPerformed

    private void btnR1SFX02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX02ActionPerformed
        playSFX(lblR1SFX02.getText());
    }//GEN-LAST:event_btnR1SFX02ActionPerformed

    private void btnR1SFX01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX01ActionPerformed
        playSFX(lblR1SFX01.getText());
    }//GEN-LAST:event_btnR1SFX01ActionPerformed

    private void btnR2SFX01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR2SFX01ActionPerformed
        playSFX(lblR2SFX01.getText());
    }//GEN-LAST:event_btnR2SFX01ActionPerformed

    private void btnR2SFX02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR2SFX02ActionPerformed
        playSFX(lblR2SFX02.getText());
    }//GEN-LAST:event_btnR2SFX02ActionPerformed

    private void btnR2SFX04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR2SFX04ActionPerformed
        playSFX(lblR2SFX04.getText());
    }//GEN-LAST:event_btnR2SFX04ActionPerformed

    private void btnR2SFX03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR2SFX03ActionPerformed
        playSFX(lblR2SFX03.getText());
    }//GEN-LAST:event_btnR2SFX03ActionPerformed

    private void btnR2SFX08ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR2SFX08ActionPerformed
        playSFX(lblR2SFX08.getText());
    }//GEN-LAST:event_btnR2SFX08ActionPerformed

    private void btnR2SFX05ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR2SFX05ActionPerformed
        playSFX(lblR2SFX05.getText());
    }//GEN-LAST:event_btnR2SFX05ActionPerformed

    private void btnR2SFX07ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR2SFX07ActionPerformed
        playSFX(lblR2SFX07.getText());
    }//GEN-LAST:event_btnR2SFX07ActionPerformed

    private void btnR2SFX06ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR2SFX06ActionPerformed
        playSFX(lblR2SFX06.getText());
    }//GEN-LAST:event_btnR2SFX06ActionPerformed

    private void btnR2SFX09ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR2SFX09ActionPerformed
        playSFX(lblR2SFX09.getText());
    }//GEN-LAST:event_btnR2SFX09ActionPerformed

    private void btnR2SFX10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR2SFX10ActionPerformed
        playSFX(lblR2SFX10.getText());
    }//GEN-LAST:event_btnR2SFX10ActionPerformed

    private void btnR2SFX11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR2SFX11ActionPerformed
        playSFX(lblR2SFX11.getText());
    }//GEN-LAST:event_btnR2SFX11ActionPerformed

    private void btnR2SFX12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR2SFX12ActionPerformed
        playSFX(lblR2SFX12.getText());
    }//GEN-LAST:event_btnR2SFX12ActionPerformed

    private void btnR2SFX13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR2SFX13ActionPerformed
        playSFX(lblR2SFX13.getText());
    }//GEN-LAST:event_btnR2SFX13ActionPerformed

    private void btnR2SFX14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR2SFX14ActionPerformed
        playSFX(lblR2SFX14.getText());
    }//GEN-LAST:event_btnR2SFX14ActionPerformed

    private void btnR3SFX01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR3SFX01ActionPerformed
        playSFX(lblR3SFX01.getText());
    }//GEN-LAST:event_btnR3SFX01ActionPerformed

    private void btnR3SFX02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR3SFX02ActionPerformed
        playSFX(lblR3SFX02.getText());
    }//GEN-LAST:event_btnR3SFX02ActionPerformed

    private void btnR3SFX04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR3SFX04ActionPerformed
        playSFX(lblR3SFX04.getText());
    }//GEN-LAST:event_btnR3SFX04ActionPerformed

    private void btnR3SFX03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR3SFX03ActionPerformed
        playSFX(lblR3SFX03.getText());
    }//GEN-LAST:event_btnR3SFX03ActionPerformed

    private void btnR3SFX08ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR3SFX08ActionPerformed
        playSFX(lblR3SFX08.getText());
    }//GEN-LAST:event_btnR3SFX08ActionPerformed

    private void btnR3SFX05ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR3SFX05ActionPerformed
        playSFX(lblR3SFX05.getText());
    }//GEN-LAST:event_btnR3SFX05ActionPerformed

    private void btnR3SFX07ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR3SFX07ActionPerformed
        playSFX(lblR3SFX07.getText());
    }//GEN-LAST:event_btnR3SFX07ActionPerformed

    private void btnR3SFX06ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR3SFX06ActionPerformed
        playSFX(lblR3SFX06.getText());
    }//GEN-LAST:event_btnR3SFX06ActionPerformed

    private void btnR3SFX09ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR3SFX09ActionPerformed
        playSFX(lblR3SFX09.getText());
    }//GEN-LAST:event_btnR3SFX09ActionPerformed

    private void btnR3SFX10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR3SFX10ActionPerformed
        playSFX(lblR3SFX10.getText());
    }//GEN-LAST:event_btnR3SFX10ActionPerformed

    private void btnR3SFX11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR3SFX11ActionPerformed
        playSFX(lblR3SFX11.getText());
    }//GEN-LAST:event_btnR3SFX11ActionPerformed

    private void btnR3SFX12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR3SFX12ActionPerformed
        playSFX(lblR3SFX12.getText());
    }//GEN-LAST:event_btnR3SFX12ActionPerformed

    private void btnR3SFX13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR3SFX13ActionPerformed
        playSFX(lblR3SFX13.getText());
    }//GEN-LAST:event_btnR3SFX13ActionPerformed

    private void btnR3SFX14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR3SFX14ActionPerformed
        playSFX(lblR3SFX14.getText());
    }//GEN-LAST:event_btnR3SFX14ActionPerformed

    private void itmUIThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmUIThemeActionPerformed
        UIThemeFrame uiThemeFrame = new UIThemeFrame(HappyButtons.mf, true);
        uiThemeFrame.setVisible(true);
    }//GEN-LAST:event_itmUIThemeActionPerformed

    private void itmAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmAboutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itmAboutActionPerformed

    public static void btnPlayVLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayVLActionPerformed
        playVid(); // System.out.println("Hi");
    }//GEN-LAST:event_btnPlayVLActionPerformed

    public static void playVid() { // System.out.println("PlayVid"); // MY IF CONDITIONS HERE ARE MAGULO
        if(!tfVideoLoop.getText().equals("")) { // System.out.println("VP: " + cboVidLoop.getSelectedItem());
            if((!HappyButtons.vlcjPath.isEmpty() || !HappyButtons.vlcjPath.isBlank() || 
            !HappyButtons.vlcjPath.equals("") || HappyButtons.vlcjPath != null)){
                if(vlcjPlaying == 0){
                    if(chkVLShuffle == 1) {
                        if(playlistVideoMode == 1) {
                            if(vlQueue.length == 0) { // vlQueue has no item
                                shuffleVLList(0);
                            }
                            else { // vlQueue has item
                                shuffleVLList(0); // it is like re-shuffling
                            }
                            
                            chkVLLoop = 0;
                            chkLoopVL.setSelected(false);
                        }
                        else {
                            shuffleVLList(0);
                            
                            chkVLLoop = 1;
                            chkLoopVL.setSelected(true);
                        }

                        vlcjPlaying = 1;
                        vlc = new VLCFrame();
                    }
                    else { // not shuffled
                        if(playlistVideoMode == 1) {
                            if(vlQueue.length == 0) {
                                sortVLList(0);
                            }
                            else {
                                sortVLList(1);
                            }
                            
                            chkVLLoop = 0;
                            chkLoopVL.setSelected(false);
                        }
                        
                        vlcjPlaying = 1;
                        vlc = new VLCFrame();
                    }
                    vlStopClicked = 0;
                }
                else { // System.out.println("Selected1: " + selectedVideoItem + "\nVideo file name1: " + vlc.videoFilename);
                    if(!MainFrame.tfVideoLoop.equals("")) {
                        if(!(tfVideoLoop.getText().equals(vlc.videoFilename))) {
    //                    if(MainFrame.vlStopClicked == 1) {
                            vlc.emp.removeMediaPlayerEventListener(vlc.videoListener);
                            vlc.emp.stop();
                            vlc.emp.release();
                            vlc.mpf.release();

                            vlc.mpf = new MediaPlayerFactory();
                            vlc.emp = null;
                            vlc.emp = vlc.mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(vlc.frame));
                            vlc.emp.setVideoSurface(vlc.mpf.newVideoSurface(vlc.canvasMain));

                            vlc.file = HappyButtons.documentsPathDoubleSlash + 
                            Utility.strDoubleSlash("\\HappyButtons\\hlvids\\" + 
                                    tfVideoLoop.getText() + 
                                    ".mp4");
                            vlc.videoFilename = selectedVideoItem;
                            vlc.emp.prepareMedia(vlc.file);
                            vlc.emp.addMediaPlayerEventListener(vlc.videoListener);
                            vlc.emp.play();
                            
                            if(MainFrame.chkVLFit == 0) { //System.out.println("No fit");
                                vlc.emp.setAspectRatio(vlc.vidOrigRatio);
                            }
                            else { //System.out.println("Fit");
                                vlc.emp.setAspectRatio(vlc.screenRatio);
                            }
    //                    }
                            if(playlistVideoMode == 1) {
                                chkVLLoop = 0;
                                chkLoopVL.setSelected(false);
                            }
                            else {
                                chkVLLoop = 1;
                                chkLoopVL.setSelected(true);
                            }
                        }
                    }
                }
            }
            else {
                tfLastOperation.setText("VIDEO:: Unable to start video loop. VLCj plugin not found/set");
                tfLastOperation.setToolTipText("VIDEO:: Unable to start video loop. VLCj plugin not found/set");
                tfLastOperation.moveCaretPosition(0);
            }
        }
        else {
            tfLastOperation.setText("[VIDEO]: Nothing to play");
        }
    }
    
    public static void shuffleVLList(int type) {
        if(type == 0) { // vlQueue has no item
            vlQueue = new String[0]; // remove this test line of code
            String[] vlList = Utility.strToArr(strVidList);
            int length = vlList.length, min = 1, randomIndex = -1;
            int[] exclusion = new int[]{};
            Random random = new Random();

            exclusion = Utility.addElementInIntArr(exclusion, Utility.getIndexOfStrArrElement(vlList, tfVideoLoop.getText()));
            vlQueue = Utility.addElementInStrArr(vlQueue, tfVideoLoop.getText());

            for(int i = min; i < length; i++) {
                do {
                    randomIndex = random.nextInt(length - min + 1) + min;
                }
                while(Utility.doesIntArrHasElement(exclusion, randomIndex - 1));

                exclusion = Utility.addElementInIntArr(exclusion, randomIndex - 1);
                vlQueue = Utility.addElementInStrArr(vlQueue, vlList[randomIndex - 1]);
            }
        }
        else if(type == 1) { // vlQueue has item
            String[] vlList = new String[vlQueue.length];
            int startedIndex = Utility.getIndexOfStrArrElement(vlQueue, tfVideoLoop.getText());
            
            for(int i = 0; i < (vlQueue.length); i++) {
                if((startedIndex + i) >= vlQueue.length) {
                    vlList[i] = vlQueue[(startedIndex + i) - (vlQueue.length)];
                }
                else {
                    vlList[i] = vlQueue[startedIndex + i];
                }
            }
            vlQueue = vlList;
        }
        
        vidQueue = vlQueue;
    }
    
    public static void sortVLList(int type) {
        if(type == 0) {
            vlQueue = Utility.strToArr(strVidList);
        }
        else if(type == 1) {
            String[] vlList = new String[vlQueue.length];
            int startedIndex = Utility.getIndexOfStrArrElement(vlQueue, tfVideoLoop.getText());
            
            for(int i = 0; i < (vlQueue.length); i++) {
                if((startedIndex + i) >= vlQueue.length) {
                    vlList[i] = vlQueue[(startedIndex + i) - (vlQueue.length)];
                }
                else {
                    vlList[i] = vlQueue[startedIndex + i];
                }
            }
            
            vlQueue = vlList;
        }
        
        vidQueue = vlQueue;
    }
    
    private void btnStopVLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopVLActionPerformed
        
    }//GEN-LAST:event_btnStopVLActionPerformed

    private void itmPluginsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itmPluginsMouseClicked
        PluginsFrame pluginFrame = new PluginsFrame(HappyButtons.mf, true);
        pluginFrame.setVisible(true);
    }//GEN-LAST:event_itmPluginsMouseClicked

    private void itmPluginsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmPluginsActionPerformed
        PluginsFrame pluginFrame = new PluginsFrame(HappyButtons.mf, true);
        pluginFrame.setVisible(true);
    }//GEN-LAST:event_itmPluginsActionPerformed

    public static void chkLoopVLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLoopVLActionPerformed
        if(chkLoopVL.isSelected()) {
            chkVLLoop = 1;
        }
        else {
            chkVLLoop = 0;
        }
    }//GEN-LAST:event_chkLoopVLActionPerformed

    public static void chkMuteVLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMuteVLActionPerformed
        if(vlcjPlaying == 0) {
            if(chkMuteVL.isSelected()) {
                chkVLMute = 1;
            }
            else {
                chkVLMute = 0;
            }
        }
    }//GEN-LAST:event_chkMuteVLActionPerformed

    private void itmSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmSettingsActionPerformed
        SettingsFrame settings = new SettingsFrame(HappyButtons.mf, true);
        settings.setVisible(true);
    }//GEN-LAST:event_itmSettingsActionPerformed

    private void chkFitVLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFitVLActionPerformed
        if(vlcjPlaying == 0) { // for not doubling
            if(chkFitVL.isSelected()) {
                chkVLFit = 1;
            }
            else {
                chkVLFit = 0;
            }
        }
    }//GEN-LAST:event_chkFitVLActionPerformed

    private void btnPrevMp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevMp3ActionPerformed
//        Notification panel = new Notification(HappyButtons.mf, 
//            Notification.Type.INFO, 
//            MainFrame.location, 
//            "Under development",
//            "Previous button still on work"
//        );
//        panel.showNotification();
        
        mp3.prevQueue();
        
        if(HappyButtons.uiTheme.equals("light")) {
            String btnIcon3 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_mp3_12px.png");
            btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon3));
        }
        else if(HappyButtons.uiTheme.equals("dark")) {
            String btnIcon3 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_pause_mp3_12px.png");
            btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon3));
        }
        
        mp3.setElementIcon(2, "play");
    }//GEN-LAST:event_btnPrevMp3ActionPerformed

    private void btnNextMp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextMp3ActionPerformed
//        Notification panel = new Notification(HappyButtons.mf, 
//            Notification.Type.INFO, 
//            MainFrame.location, 
//            "Under development",
//            "Next button still on work"
//        );
//        panel.showNotification();
        mp3.nextQueue();
        
        if(HappyButtons.uiTheme.equals("light")) {
            String btnIcon3 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_mp3_12px.png");
            btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon3));
        }
        else if(HappyButtons.uiTheme.equals("dark")) {
            String btnIcon3 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_pause_mp3_12px.png");
            btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon3));
        }
        
        mp3.setElementIcon(2, "play");
    }//GEN-LAST:event_btnNextMp3ActionPerformed
   
    private void chkLoop1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLoop1ActionPerformed
        if(chkLoop1.isSelected()) {
            loop1 = 1;
        }
        else {
            loop1 = 0;
        }
    }//GEN-LAST:event_chkLoop1ActionPerformed

    private void chkLoop2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLoop2ActionPerformed
        if(chkLoop2.isSelected()) {
            loop2 = 1;
        }
        else {
            loop2 = 0;
        }
    }//GEN-LAST:event_chkLoop2ActionPerformed

    private void lblMp3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMp3MouseClicked
        openMp3Frame();
    }//GEN-LAST:event_lblMp3MouseClicked

    private void btnPlayPauseMp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayPauseMp3ActionPerformed
        playPauseMp3(0);
    }//GEN-LAST:event_btnPlayPauseMp3ActionPerformed

    public static void prevTime() {
//        System.out.println("\nPrev time: " + prevTimer);
        if(prevTimer == 1) {
            timerMp.stop();
            System.out.println("\nStopped");
        }
        else {
            prevSong = 1;
        }
        
        prevTimer = 1;
        timerMp.start();
    }
    
    public static void playPauseMp3(int type) { // type is 0 - button click, 1 - list click, 2 - queue
        if(mp3Shuffle == 0 && type != 2){
            Mp3Frame.sortQueue();
        }
        if(mp3Shuffle == 1 && type != 2){
            Mp3Frame.shuffle();
        }
        
        if(type == 0) { // button click
            if(!selectedMp3Item.equals("") && Mp3Frame.listMp3.getSelectedIndex() != -1) {
                if(mp3Playing == 0) { // not pause
                    selectedMp3Item = mp3.listMp3.getSelectedValue();
                }
                else {
                    mp3.lblSongMp3.setText(Utility.shortenText(mp3.listMp3.getSelectedValue(), 50));
                }

                if(clipMp3 == null) {
                    if(selectedMp3Item.equals("")){
                        tfLastOperation.setText("[MP]:: NOTHING TO PLAY");
                        tfLastOperation.setToolTipText("[MP]:: NOTHING TO PLAY");

                        errorOccurred = 1;
                        iconPlayMp3 = 1;
                    }
                    else {
                        prevTime();

                        tryCatchMp3();
                        iconPlayMp3 = 0;
                    }
                }
                else { // clip Mp3 is not null
                    if(clipMp3.isRunning()) {
                        mp3LastFrame = clipMp3.getFramePosition();
                        clipMp3.removeLineListener(listenMp3);
                        clipMp3.stop();
                        clipMp3.addLineListener(listenMp3);

                        iconPlayMp3 = 1;
                    }
                    else {
                        if(mp3LastFrame < clipMp3.getFrameLength()) {
                            clipMp3.setFramePosition(mp3LastFrame);
                        }
                        else {
                            clipMp3.setFramePosition(0);
                        }

                        clipMp3.start();
                        prevTime();

                        iconPlayMp3 = 0;
                    }
                }
            }
            else {
                tfLastOperation.setText("[MP]:: NOTHING TO PLAY");
                tfLastOperation.setToolTipText("[MP]:: NOTHING TO PLAY");

                errorOccurred = 1;
                iconPlayMp3 = 1;
            }
        }
        else if(type == 1){ // type == 1, list click
            if(clipMp3 != null) {
                if(clipMp3.isRunning()) {
                    mp3LastFrame = 0;
                    clipMp3.removeLineListener(listenMp3);
                    clipMp3.stop();
                    clipMp3.close();
                    clipMp3 = null;
                    currentMp3Playing = "";
                }
                else {
                    clipMp3 = null;
                    currentMp3Playing = "";
                }
            }
            
            prevTime();
            tryCatchMp3();
            iconPlayMp3 = 0;
        }
        else if(type == 2) { // queue
            clipMp3.removeLineListener(listenMp3);
            clipMp3.stop();
            clipMp3.close();
            clipMp3 = null;
            
            prevTime();
            tryCatchMp3();
            iconPlayMp3 = 0;
        }
        
        if(errorOccurred == 0){
            if(iconPlayMp3 == 1) {
                if(HappyButtons.uiTheme.equals("light")) {
                    String btnIcon3 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_12px.png");
                    btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon3));
                }
                else if(HappyButtons.uiTheme.equals("dark")) {
                    String btnIcon3 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_mp3_12px.png");
                    btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon3));
                }

                if(mp3.isShowing()) {
                    if(HappyButtons.uiTheme.equals("light")) {
                        String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_ui_18px.png");
                        mp3.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                    }
                    else if(HappyButtons.uiTheme.equals("dark"))  {
                        String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_mp3_ui_18px.png");
                        mp3.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                    }
                }
            }
            else {
                if(HappyButtons.uiTheme.equals("light")) {
                    String btnIcon3 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_mp3_12px.png");
                    btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon3));
                }
                else if(HappyButtons.uiTheme.equals("dark")) {
                    String btnIcon3 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_pause_mp3_12px.png");
                    btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon3));
                }
                
                if(mp3.isShowing()) {
                    if(HappyButtons.uiTheme.equals("light")) {
                        String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_mp3_16px.png");
                        mp3.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                    }
                    else if(HappyButtons.uiTheme.equals("dark"))  {
                        String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_pause_mp3_16px.png");
                        mp3.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                    }
                }
            }
        }
        else {
            errorOccurred = 0;
            currentMp3Playing = "";
        }
        
        if(btnNext == 1) {
            if(HappyButtons.uiTheme.equals("light")) {
                String btnIcon3 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_mp3_12px.png");
                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon3));
            }
            else if(HappyButtons.uiTheme.equals("dark")) {
                String btnIcon3 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_pause_mp3_12px.png");
                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon3));
            }
            
            btnNext = 0;
        }
    }
    
    private static void tryCatchMp3() {
        try {
            String musicPath = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\mp3s\\") + MainFrame.selectedMp3Item + ".wav";
            loadClipMp3(new File(musicPath));
            fcMp3 = (FloatControl)clipMp3.getControl(FloatControl.Type.MASTER_GAIN);

            if(mp3.isShowing()){
                if(mp3.volMp3.getValue() == 100) {
                    fcMp3.setValue(0f);
                }
                else {
                    fcMp3.setValue(mp3Vol); // float value
                }
            }
            else {
                if(mp3.volMp3.getValue() == 100) {
                    fcMp3.setValue(0f);
                }
                else {
                    fcMp3.setValue(mp3Vol); // float value
                }
            }

            currentMp3Playing = selectedMp3Item;
            Mp3Frame.lblSongMp3.setText(Utility.shortenText(selectedMp3Item, 50));
            Mp3Frame.lblSongMp3.setToolTipText(selectedMp3Item);
            tfMp3.setText(selectedMp3Item);
            tfMp3.setToolTipText(selectedMp3Item);
            tfMp3.moveCaretPosition(0);
            Mp3Frame.listMp3.setSelectedValue(selectedMp3Item, true);
            clipMp3.addLineListener(listenMp3);
            clipMp3.start();
            
            mp3Duration = Utility.convertSecondsToHMS((int) Math.round(mp3DurationInSeconds));
            Mp3Frame.lblDuration.setText(mp3Duration);
            
            new Timer(100, e -> updateMp3Slider()).start(); // Update slider every 100ms
        }
        catch(IOException ioe){
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                    "Specified Mp3 may be gone missing or suddenly deleted.\nIf not, inform the developer for this bug", 
                    "File IO exception occured", 
                    JOptionPane.ERROR_MESSAGE);

            mp3Playing = 0;
            mp3Pause = 0;
            errorOccurred = 1;
            currentMp3Playing = "";

            if(HappyButtons.uiTheme.equals("light")) {
                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_12px.png");
                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon));
            }
            else if(HappyButtons.uiTheme.equals("dark")) {
                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_mp3_12px.png");
                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon));
            }

            if(mp3.isShowing()) {
                if(HappyButtons.uiTheme.equals("light")) {
                    String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_ui_18px.png");
                    mp3.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                }
                else if(HappyButtons.uiTheme.equals("dark"))  {
                    String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_mp3_ui_18px.png");
                    mp3.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                }
            }

            iconPlayMp3 = 1;
        }
        catch(LineUnavailableException lue){
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                    "LineUnavailableException error has occured. Please inform the developer", 
                    "Line Unavailable Exception", 
                    JOptionPane.ERROR_MESSAGE);

            mp3Playing = 0;
            mp3Pause = 0;
            MainFrame.errorOccurred = 1;
            currentMp3Playing = "";

            if(HappyButtons.uiTheme.equals("light")) {
                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_12px.png");
                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon));
            }
            else if(HappyButtons.uiTheme.equals("dark")) {
                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_mp3_12px.png");
                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon));
            }

            if(mp3.isShowing()) {
                if(HappyButtons.uiTheme.equals("light")) {
                    String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_ui_18px.png");
                    mp3.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                }
                else if(HappyButtons.uiTheme.equals("dark"))  {
                    String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_mp3_ui_18px.png");
                    mp3.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                }
            }

            iconPlayMp3 = 1;
        }
        catch(UnsupportedAudioFileException uafe){
            JOptionPane.showMessageDialog(HappyButtons.mf, 
                    "Mp3 file may be broken/corrupted. Or make sure the audio file has genuine WAV format.\nChanging the file extension by renaming it will NOT do the trick", 
                    "Unsupported file", 
                    JOptionPane.ERROR_MESSAGE);

            mp3Playing = 0;
            mp3Pause = 0;
            errorOccurred = 1;
            currentMp3Playing = "";

            if(HappyButtons.uiTheme.equals("light")) {
                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_12px.png");
                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon));
            }
            else if(HappyButtons.uiTheme.equals("dark")) {
                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_mp3_12px.png");
                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon));
            }

            if(mp3.isShowing()) {
                if(HappyButtons.uiTheme.equals("light")) {
                    String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_ui_18px.png");
                    mp3.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                }
                else if(HappyButtons.uiTheme.equals("dark"))  {
                    String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_mp3_ui_18px.png");
                    mp3.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                }
            }
        }

        iconPlayMp3 = 1;
    }
    
    private static void updateMp3Slider() {
        if(clipMp3 != null && clipMp3.isRunning()) {
            long currentMicroseconds = clipMp3.getMicrosecondPosition();
            long totalMicroseconds = clipMp3.getMicrosecondLength();
            int value = (int) ((currentMicroseconds * 100) / totalMicroseconds);
            Mp3Frame.sliderSongTime.setValue(value);
            
            int currentSeconds = (int) (currentMicroseconds / 1000000);
            String lastFrame = Utility.convertSecondsToHMS(currentSeconds);
            Mp3Frame.lblLastFrame.setText(lastFrame);
            
            // update the Main Frame label
            lblCurrentMp3Duration.setText(lastFrame + " / " + mp3Duration);
        }
    }
    
    public static void mp3SliderClicked() {
        Notification panel = new Notification(HappyButtons.mf, 
            Notification.Type.INFO, 
            MainFrame.location, 
            "Under development",
            "Music seeking is not yet functional"
        );
        panel.showNotification();
        
//        mp3SliderListener = new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                if (!Mp3Frame.sliderSongTime.getValueIsAdjusting()) {
//                    clipMp3.setMicrosecondPosition(Mp3Frame.sliderSongTime.getValue());
//                }
//            }
//        };
//    
//        Mp3Frame.sliderSongTime.addChangeListener(mp3SliderListener);
//        Mp3Frame.sliderSongTime.removeChangeListener(mp3SliderListener);
    }
    
    private void tfMp3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfMp3MouseClicked
//        openMp3Frame();
    }//GEN-LAST:event_tfMp3MouseClicked

    private void chkVLModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkVLModeActionPerformed
        if(chkVLMode.isSelected()) {
            playlistVideoMode = 0;
//            cboVidLoop.setModel(cboModelForLoop);
            VLType = "forloop";
            cboVLType = 0; // forloop
            
            if(vlcjPlaying != 1) {
                chkVLLoop = 1;
                chkLoopVL.setSelected(true);
            }
            
            tfVideoLoop.setText(selectedLoopVideoItem);
            tfVideoLoop.moveCaretPosition(0);
            
//            chkVLMute = 1;
//            chkMuteVL.setSelected(true);
        }
        else {
            playlistVideoMode = 1;
//            cboVidLoop.setModel(cboModelPlaylist);
            VLType = "playlist";
            cboVLType = 1; // playlist
            
            if(vlcjPlaying != 1) {
                chkVLLoop = 0;
                chkLoopVL.setSelected(false);
            }
            
            tfVideoLoop.setText(selectedPlaylistVideoItem);
            tfVideoLoop.moveCaretPosition(0);
            
//            chkVLMute = 0;
//            chkMuteVL.setSelected(false);
        }
        
//        ActionEvent actionEvent = new ActionEvent(new Object(), ActionEvent.ACTION_PERFORMED, "command");
//        checkBoxAction.actionPerformed(actionEvent);
//        System.out.println("CHK mode: " + playlistVideoMode);
    }//GEN-LAST:event_chkVLModeActionPerformed

    private void tfMp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfMp3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfMp3ActionPerformed

    private void itmSystemToolsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmSystemToolsActionPerformed
        SystemToolsFrame frame = new SystemToolsFrame(HappyButtons.mf, true);
        frame.setVisible(true);
    }//GEN-LAST:event_itmSystemToolsActionPerformed

    private void tfVideoLoopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfVideoLoopMouseClicked
        ExplorerVideo videoExplorer = new ExplorerVideo(HappyButtons.mf, true);
        videoExplorer.setVisible(true);
    }//GEN-LAST:event_tfVideoLoopMouseClicked

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
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAddBGM;
    public static javax.swing.JButton btnAddSFX;
    public static javax.swing.JButton btnClearBGM1;
    public static javax.swing.JButton btnClearBGM2;
    public static javax.swing.JButton btnDeleteBGM;
    public static javax.swing.JButton btnDeleteSFX;
    public static javax.swing.JButton btnNextMp3;
    public static javax.swing.JButton btnPlayPauseBGM1;
    public static javax.swing.JButton btnPlayPauseBGM2;
    public static javax.swing.JButton btnPlayPauseMp3;
    public static javax.swing.JButton btnPlayVL;
    public static javax.swing.JButton btnPrevMp3;
    public static javax.swing.JButton btnR1SFX01;
    public static javax.swing.JButton btnR1SFX02;
    public static javax.swing.JButton btnR1SFX03;
    public static javax.swing.JButton btnR1SFX04;
    public static javax.swing.JButton btnR1SFX05;
    public static javax.swing.JButton btnR1SFX06;
    public static javax.swing.JButton btnR1SFX07;
    public static javax.swing.JButton btnR1SFX08;
    public static javax.swing.JButton btnR1SFX09;
    public static javax.swing.JButton btnR1SFX10;
    public static javax.swing.JButton btnR1SFX11;
    public static javax.swing.JButton btnR1SFX12;
    public static javax.swing.JButton btnR1SFX13;
    public static javax.swing.JButton btnR1SFX14;
    public static javax.swing.JButton btnR2SFX01;
    public static javax.swing.JButton btnR2SFX02;
    public static javax.swing.JButton btnR2SFX03;
    public static javax.swing.JButton btnR2SFX04;
    public static javax.swing.JButton btnR2SFX05;
    public static javax.swing.JButton btnR2SFX06;
    public static javax.swing.JButton btnR2SFX07;
    public static javax.swing.JButton btnR2SFX08;
    public static javax.swing.JButton btnR2SFX09;
    public static javax.swing.JButton btnR2SFX10;
    public static javax.swing.JButton btnR2SFX11;
    public static javax.swing.JButton btnR2SFX12;
    public static javax.swing.JButton btnR2SFX13;
    public static javax.swing.JButton btnR2SFX14;
    public static javax.swing.JButton btnR3SFX01;
    public static javax.swing.JButton btnR3SFX02;
    public static javax.swing.JButton btnR3SFX03;
    public static javax.swing.JButton btnR3SFX04;
    public static javax.swing.JButton btnR3SFX05;
    public static javax.swing.JButton btnR3SFX06;
    public static javax.swing.JButton btnR3SFX07;
    public static javax.swing.JButton btnR3SFX08;
    public static javax.swing.JButton btnR3SFX09;
    public static javax.swing.JButton btnR3SFX10;
    public static javax.swing.JButton btnR3SFX11;
    public static javax.swing.JButton btnR3SFX12;
    public static javax.swing.JButton btnR3SFX13;
    public static javax.swing.JButton btnR3SFX14;
    public static javax.swing.JButton btnStopBGM1;
    public static javax.swing.JButton btnStopBGM2;
    public static javax.swing.JButton btnStopSFX;
    public static javax.swing.JButton btnStopVL;
    public static javax.swing.JCheckBox chkFitVL;
    public static javax.swing.JCheckBox chkIB;
    public static javax.swing.JCheckBox chkLoop1;
    public static javax.swing.JCheckBox chkLoop2;
    public static javax.swing.JCheckBox chkLoopVL;
    public static javax.swing.JCheckBox chkMuteVL;
    public static javax.swing.JCheckBox chkSP;
    public static javax.swing.JCheckBox chkVLMode;
    public static javax.swing.JMenuItem itemSave;
    public static javax.swing.JMenu itmAS;
    public static javax.swing.JMenu itmAbout;
    public static javax.swing.JMenuItem itmLoad;
    public static javax.swing.JMenuItem itmNew;
    public static javax.swing.JMenuItem itmPlugins;
    public static javax.swing.JMenuItem itmResourceManager;
    public static javax.swing.JMenuItem itmSettings;
    public static javax.swing.JMenuItem itmSystemTools;
    public static javax.swing.JMenu itmTools;
    public static javax.swing.JMenuItem itmUITheme;
    public static javax.swing.JMenu jMenu1;
    public static javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    public static javax.swing.JMenu jMenuTime;
    public static javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    public static javax.swing.JLabel lblBGM1;
    public static javax.swing.JLabel lblBGM2;
    public static javax.swing.JLabel lblCurrentMp3Duration;
    private javax.swing.JLabel lblDeleteSFX;
    public static javax.swing.JLabel lblLastOperation;
    public static javax.swing.JLabel lblLinkBGMVolumes;
    public static javax.swing.JLabel lblMp3;
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
    public static javax.swing.JLabel lblR1SFX12;
    public static javax.swing.JLabel lblR1SFX13;
    public static javax.swing.JLabel lblR1SFX14;
    public static javax.swing.JLabel lblR2SFX01;
    public static javax.swing.JLabel lblR2SFX02;
    public static javax.swing.JLabel lblR2SFX03;
    public static javax.swing.JLabel lblR2SFX04;
    public static javax.swing.JLabel lblR2SFX05;
    public static javax.swing.JLabel lblR2SFX06;
    public static javax.swing.JLabel lblR2SFX07;
    public static javax.swing.JLabel lblR2SFX08;
    public static javax.swing.JLabel lblR2SFX09;
    public static javax.swing.JLabel lblR2SFX10;
    public static javax.swing.JLabel lblR2SFX11;
    public static javax.swing.JLabel lblR2SFX12;
    public static javax.swing.JLabel lblR2SFX13;
    public static javax.swing.JLabel lblR2SFX14;
    public static javax.swing.JLabel lblR3SFX01;
    public static javax.swing.JLabel lblR3SFX02;
    public static javax.swing.JLabel lblR3SFX03;
    public static javax.swing.JLabel lblR3SFX04;
    public static javax.swing.JLabel lblR3SFX05;
    public static javax.swing.JLabel lblR3SFX06;
    public static javax.swing.JLabel lblR3SFX07;
    public static javax.swing.JLabel lblR3SFX08;
    public static javax.swing.JLabel lblR3SFX09;
    public static javax.swing.JLabel lblR3SFX10;
    public static javax.swing.JLabel lblR3SFX11;
    public static javax.swing.JLabel lblR3SFX12;
    public static javax.swing.JLabel lblR3SFX13;
    public static javax.swing.JLabel lblR3SFX14;
    public static javax.swing.JLabel lblSFXState;
    public static javax.swing.JLabel lblVideoLoop;
    public static javax.swing.JLabel lblVolBGM1;
    public static javax.swing.JLabel lblVolBGM2;
    public static javax.swing.JLabel lblVolSFX;
    public static javax.swing.JList<String> listBGM;
    public static javax.swing.JList<String> listSFX;
    public static javax.swing.JMenu menuPreferences;
    public static javax.swing.JPanel panelJList;
    public static javax.swing.JPanel panelR1S01;
    public static javax.swing.JPanel panelR1S02;
    public static javax.swing.JPanel panelR1S03;
    public static javax.swing.JPanel panelR1S04;
    public static javax.swing.JPanel panelR1S05;
    public static javax.swing.JPanel panelR1S06;
    public static javax.swing.JPanel panelR1S07;
    public static javax.swing.JPanel panelR1S08;
    public static javax.swing.JPanel panelR1S09;
    public static javax.swing.JPanel panelR1S10;
    public static javax.swing.JPanel panelR1S11;
    public static javax.swing.JPanel panelR1S12;
    public static javax.swing.JPanel panelR1S13;
    public static javax.swing.JPanel panelR1S14;
    public static javax.swing.JPanel panelR2S01;
    public static javax.swing.JPanel panelR2S02;
    public static javax.swing.JPanel panelR2S03;
    public static javax.swing.JPanel panelR2S04;
    public static javax.swing.JPanel panelR2S05;
    public static javax.swing.JPanel panelR2S06;
    public static javax.swing.JPanel panelR2S07;
    public static javax.swing.JPanel panelR2S08;
    public static javax.swing.JPanel panelR2S09;
    public static javax.swing.JPanel panelR2S10;
    public static javax.swing.JPanel panelR2S11;
    public static javax.swing.JPanel panelR2S12;
    public static javax.swing.JPanel panelR2S13;
    public static javax.swing.JPanel panelR2S14;
    public static javax.swing.JPanel panelR3S01;
    public static javax.swing.JPanel panelR3S02;
    public static javax.swing.JPanel panelR3S03;
    public static javax.swing.JPanel panelR3S04;
    public static javax.swing.JPanel panelR3S05;
    public static javax.swing.JPanel panelR3S06;
    public static javax.swing.JPanel panelR3S07;
    public static javax.swing.JPanel panelR3S08;
    public static javax.swing.JPanel panelR3S09;
    public static javax.swing.JPanel panelR3S10;
    public static javax.swing.JPanel panelR3S11;
    public static javax.swing.JPanel panelR3S12;
    public static javax.swing.JPanel panelR3S13;
    public static javax.swing.JPanel panelR3S14;
    public static javax.swing.JPanel panelRow1;
    public static javax.swing.JPanel panelRow2;
    public static javax.swing.JPanel panelRow3;
    public static javax.swing.JPanel panelSFX1;
    public static javax.swing.JPanel panelSFX2;
    public static javax.swing.JPanel panelSFX3;
    public static javax.swing.JTextField tfBGM1;
    public static javax.swing.JTextField tfBGM2;
    public static javax.swing.JTextField tfLastOperation;
    public static javax.swing.JTextField tfMp3;
    public static javax.swing.JTextField tfVideoLoop;
    public static javax.swing.JToggleButton togLinkBGMVol;
    public static javax.swing.JSlider volBGM1;
    public static javax.swing.JSlider volBGM2;
    public static javax.swing.JSlider volSFX;
    // End of variables declaration//GEN-END:variables
    
    public void blistFilesForFolder(final File folder) {
        try {
            File[] bFileList = folder.listFiles();
        
            for(File f : bFileList) {
                if(Utility.getFileExtension(f.getName()).equals("wav")){
                    blist.addElement(Utility.renameListName(f.getName(), "wav"));
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
                    slist.addElement(Utility.renameListName(f.getName(), "wav"));
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
        AudioInputStream audiostream = AudioSystem.getAudioInputStream(audioFile);

        AudioFormat format = audiostream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        this.clipBGM1 = (Clip) AudioSystem.getLine(info);
        this.clipBGM1.open(audiostream);
    }
    
    protected void loadClipBGM2(File audioFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream audiostream = AudioSystem.getAudioInputStream(audioFile);

        AudioFormat format = audiostream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        this.clipBGM2 = (Clip) AudioSystem.getLine(info);
        this.clipBGM2.open(audiostream);
    }
    
    public static void loadClipMp3(File audioFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

        AudioFormat format = audioStream.getFormat();
        mp3Frames = audioStream.getFrameLength();
        mp3DurationInSeconds = (mp3Frames + 0.0) / format.getFrameRate();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        clipMp3 = (Clip) AudioSystem.getLine(info);
        clipMp3.open(audioStream);
    }
    
    protected void loadClipSFX(File audioFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream audiostream = AudioSystem.getAudioInputStream(audioFile);

        AudioFormat format = audiostream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        clipSFX = (Clip) AudioSystem.getLine(info);
        clipSFX.open(audiostream);
    }
    
    public void playSFX(String sfxName) {
        sfxOperation = true;
        
        if(sfxName.equals("blank") || 
            sfxName.equals("<html><center>blank</center></html>") || 
            sfxName.equals("null") || 
            sfxName.equals("<html><center>null</center></html>")) {
            tfLastOperation.setText("NO SFX TO PLAY");
//            JOptionPane.showMessageDialog(HappyButtons.mf, 
//                            "Nothing to play", 
//                            "Empty", 
//                            JOptionPane.WARNING_MESSAGE);
        } // if(!sfxName.equals("blank"))
        else {
            if(chkSinglePlay == 0 && chkStopBGM == 0) {
                try {
                    String musicPath = sfolder + "\\" + Utility.cleanSFXNaming(sfxName) + ".wav";
                    loadClipSFX(new File(musicPath));
                    fcSFX = (FloatControl)clipSFX.getControl(FloatControl.Type.MASTER_GAIN);
                    if(volSFX.getValue() == 100) {
                        fcSFX.setValue(6f);
                    }
                    else {
                        fcSFX.setValue(sfxVol); // float value
                    }
                    
                    clipSFX.start();
                }
                catch(IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                    tfLastOperation.setText("[ERROR]:: " + e.toString());
                }
            }
            else if(chkSinglePlay == 1 && chkStopBGM == 0) {
                if(clipSFX != null) {
                    if(clipSFX.isRunning()) {
                        clipSFX.stop();

                        try {
                            String musicPath = sfolder + "\\" + Utility.cleanSFXNaming(sfxName) + ".wav";
                            loadClipSFX(new File(musicPath));
                            fcSFX = (FloatControl)clipSFX.getControl(FloatControl.Type.MASTER_GAIN);
                            
                            if(volSFX.getValue() == 100) {
                                fcSFX.setValue(6f);
                            }
                            else {
                                fcSFX.setValue(sfxVol); // float value
                            }

                            clipSFX.addLineListener(sfxListener);
                            clipSFX.start();
                        }
                        catch(IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                            tfLastOperation.setText("[ERROR]:: " + e.toString());
                        }
                    }
                    else {
//                        sfxClickCount++;
                        
                        try {
                            String musicPath = sfolder + "\\" + Utility.cleanSFXNaming(sfxName) + ".wav";
                            loadClipSFX(new File(musicPath));
                            fcSFX = (FloatControl)clipSFX.getControl(FloatControl.Type.MASTER_GAIN);
                            if(volSFX.getValue() == 100) {
                                fcSFX.setValue(6f);
                            }
                            else {
                                fcSFX.setValue(sfxVol); // float value
                            }

                            clipSFX.start();
                        }
                        catch(IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                            tfLastOperation.setText("[ERROR]:: " + e.toString());
                        }
                    }
                }
                else {
                    try {
                        String musicPath = sfolder + "\\" + Utility.cleanSFXNaming(sfxName) + ".wav";
                        loadClipSFX(new File(musicPath));
                        fcSFX = (FloatControl)clipSFX.getControl(FloatControl.Type.MASTER_GAIN);
                        if(volSFX.getValue() == 100) {
                            fcSFX.setValue(6f);
                        }
                        else {
                            fcSFX.setValue(sfxVol); // float value
                        }
                        
                        sfxPlaying = 1;
                        clipSFX.addLineListener(sfxListener);
                        clipSFX.start();
                    }
                    catch(IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                        sfxPlaying = 0;
                        tfLastOperation.setText("[ERROR]:: " + e.toString());
                    }
                }
            }
            else if(chkSinglePlay == 0 && chkStopBGM == 1) {
                try {
                    String musicPath = sfolder + "\\" + Utility.cleanSFXNaming(sfxName) + ".wav";
                    loadClipSFX(new File(musicPath));
                    fcSFX = (FloatControl)clipSFX.getControl(FloatControl.Type.MASTER_GAIN);
                    if(volSFX.getValue() == 100) {
                        fcSFX.setValue(6f);
                    }
                    else {
                        fcSFX.setValue(sfxVol); // float value
                    }
                }
                catch(IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                    tfLastOperation.setText("[ERROR]:: " + e.toString());
                }
                
                if(clipBGM1 != null) {
                    if(clipBGM1.isRunning()) {
                        lastFrame1 = clipBGM1.getFramePosition();
                        clipBGM1.removeLineListener(listenBGM1);
                        clipBGM1.stop();
                        clipBGM1.addLineListener(listenBGM1);
                        
                        LineListener listener = (LineEvent event) -> {
                            if(event.getType() == LineEvent.Type.STOP) {
                                if(lastFrame1 < clipBGM1.getFrameLength()) {
                                    clipBGM1.setFramePosition(lastFrame1);
                                }
                                else {
                                    clipBGM1.setFramePosition(0);
                                }
                                clipBGM1.start();

                                pause1 = 0;
                                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                                btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
                                btnPlayPauseBGM1.setEnabled(true);
                                btnStopBGM1.setEnabled(true);
                            }
                        };
                        
                        pause1 = 1;
                        String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                        btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
                        btnPlayPauseBGM1.setEnabled(false);
                        btnStopBGM1.setEnabled(false);
                        
                        clipSFX.addLineListener(listener);
                    }
                }
                
                if(clipBGM2 != null) {
                    if(clipBGM2.isRunning()) {
                        lastFrame2 = clipBGM2.getFramePosition();
                        clipBGM2.removeLineListener(listenBGM2);
                        clipBGM2.stop();
                        clipBGM2.addLineListener(listenBGM2);

                        LineListener listener = (LineEvent event) -> {
                            if(event.getType() == LineEvent.Type.STOP) {
                                if(lastFrame2 < clipBGM2.getFrameLength()) {
                                    clipBGM2.setFramePosition(lastFrame2);
                                }
                                else {
                                    clipBGM2.setFramePosition(0);
                                }
                                clipBGM2.start();

                                pause2 = 0;
                                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                                btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
                                btnPlayPauseBGM2.setEnabled(true);
                                btnStopBGM2.setEnabled(true);
                            }
                        };

                        pause2 = 1;
                        String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                        btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
                        btnPlayPauseBGM2.setEnabled(false);
                        btnStopBGM2.setEnabled(false);

                        clipSFX.addLineListener(listener);
                    }
                }
                clipSFX.start();
            }
            else if(chkSinglePlay == 1 && chkStopBGM == 1) {
                if(clipSFX != null) {
                    if(clipSFX.isRunning()) {
                        clipSFX.stop();
                        
                        try {
                            String musicPath = sfolder + "\\" + Utility.cleanSFXNaming(sfxName) + ".wav";
                            loadClipSFX(new File(musicPath));
                            fcSFX = (FloatControl)clipSFX.getControl(FloatControl.Type.MASTER_GAIN);
                            if(volSFX.getValue() == 100) {
                                fcSFX.setValue(6f);
                            }
                            else {
                                fcSFX.setValue(sfxVol); // float value
                            }
                        }
                        catch(IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                            tfLastOperation.setText("[ERROR]:: " + e.toString());
                        }

                        if(clipBGM1 != null) {
                            if(clipBGM1.isRunning()) {
                                lastFrame1 = clipBGM1.getFramePosition();
                                clipBGM1.removeLineListener(listenBGM1);
                                clipBGM1.stop();
                                clipBGM1.addLineListener(listenBGM1);

                                LineListener listener = (LineEvent event) -> {
                                    if(event.getType() == LineEvent.Type.STOP) {
                                        if(lastFrame1 < clipBGM1.getFrameLength()) {
                                            clipBGM1.setFramePosition(lastFrame1);
                                        }
                                        else {
                                            clipBGM1.setFramePosition(0);
                                        }

                                        if(clipSFX.isRunning()) {
                                            clipSFX.stop();
                                        }
                                        clipBGM1.start();

                                        pause1 = 0;
                                        String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                                        btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
                                        btnPlayPauseBGM1.setEnabled(true);
                                        btnStopBGM1.setEnabled(true);
                                    }
                                };

                                pause1 = 1;
                                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                                btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
                                btnPlayPauseBGM1.setEnabled(false);
                                btnStopBGM1.setEnabled(false);

                                clipSFX.addLineListener(listener);
                            }
                        }

                        if(clipBGM2 != null) {
                            if(clipBGM2.isRunning()) {
                                lastFrame2 = clipBGM2.getFramePosition();
                                clipBGM2.removeLineListener(listenBGM2);
                                clipBGM2.stop();
                                clipBGM2.addLineListener(listenBGM2);

                                LineListener listener = (LineEvent event) -> {
                                    if(event.getType() == LineEvent.Type.STOP) {
                                        if(lastFrame2 < clipBGM2.getFrameLength()) {
                                            clipBGM2.setFramePosition(lastFrame2);
                                        }
                                        else {
                                            clipBGM2.setFramePosition(0);
                                        }

                                        if(clipSFX.isRunning()) {
                                            clipSFX.stop();
                                        }

                                        clipBGM2.start();

                                        pause2 = 0;
                                        String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                                        btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
                                        btnPlayPauseBGM2.setEnabled(true);
                                        btnStopBGM2.setEnabled(true);
                                    }
                                };

                                pause2 = 1;
                                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                                btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
                                btnPlayPauseBGM2.setEnabled(false);
                                btnStopBGM2.setEnabled(false);

                                clipSFX.addLineListener(listener);
                            }
                        }
                        clipSFX.start();
                    }
                    else {
                        try {
                            String musicPath = sfolder + "\\" + Utility.cleanSFXNaming(lblR1SFX01.getText()) + ".wav";
                            loadClipSFX(new File(musicPath));
                            fcSFX = (FloatControl)clipSFX.getControl(FloatControl.Type.MASTER_GAIN);
                            if(volSFX.getValue() == 100) {
                                fcSFX.setValue(6f);
                            }
                            else {
                                fcSFX.setValue(sfxVol); // float value
                            }
                        }
                        catch(IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                            tfLastOperation.setText("[ERROR]:: " + e.toString());
                        }

                        if(clipBGM1 != null) {
                            if(clipBGM1.isRunning()) {
                                lastFrame1 = clipBGM1.getFramePosition();
                                clipBGM1.stop();

                                LineListener listener = (LineEvent event) -> {
                                    if(event.getType() == LineEvent.Type.STOP) {
                                        if(lastFrame1 < clipBGM1.getFrameLength()) {
                                            clipBGM1.setFramePosition(lastFrame1);
                                        }
                                        else {
                                            clipBGM1.setFramePosition(0);
                                        }

                                        if(clipSFX.isRunning()) {
                                            clipSFX.stop();
                                        }
                                        clipBGM1.start();

                                        pause1 = 0;
                                        String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                                        btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
                                        btnPlayPauseBGM1.setEnabled(true);
                                        btnStopBGM1.setEnabled(true);
                                    }
                                };

                                pause1 = 1;
                                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                                btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
                                btnPlayPauseBGM1.setEnabled(false);
                                btnStopBGM1.setEnabled(false);

                                clipSFX.addLineListener(listener);
                            }
                        }

                        if(clipBGM2 != null) {
                            if(clipBGM2.isRunning()) {
                                lastFrame2 = clipBGM2.getFramePosition();
                                clipBGM2.stop();

                                LineListener listener = (LineEvent event) -> {
                                    if(event.getType() == LineEvent.Type.STOP) {
                                        if(lastFrame2 < clipBGM2.getFrameLength()) {
                                            clipBGM2.setFramePosition(lastFrame2);
                                        }
                                        else {
                                            clipBGM2.setFramePosition(0);
                                        }

                                        if(clipSFX.isRunning()) {
                                            clipSFX.stop();
                                        }

                                        clipBGM2.start();

                                        pause2 = 0;
                                        String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                                        btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
                                        btnPlayPauseBGM2.setEnabled(true);
                                        btnStopBGM2.setEnabled(true);
                                    }
                                };

                                pause2 = 1;
                                String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                                btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
                                btnPlayPauseBGM2.setEnabled(false);
                                btnStopBGM2.setEnabled(false);

                                clipSFX.addLineListener(listener);
                            }
                        }
                        clipSFX.start();
                    }
                }
                else {
                    try {
                        String musicPath = sfolder + "\\" + Utility.cleanSFXNaming(sfxName) + ".wav";
                        loadClipSFX(new File(musicPath));
                        fcSFX = (FloatControl)clipSFX.getControl(FloatControl.Type.MASTER_GAIN);
                        if(volSFX.getValue() == 100) {
                            fcSFX.setValue(6f);
                        }
                        else {
                            fcSFX.setValue(sfxVol); // float value
                        }
                    }
                    catch(IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                        tfLastOperation.setText("[ERROR]:: " + e.toString());
                    }

                    if(clipBGM1 != null) {
                        if(clipBGM1.isRunning()) {
                            lastFrame1 = clipBGM1.getFramePosition();
                            clipBGM1.stop();

                            LineListener listener = (LineEvent event) -> {
                                if(event.getType() == LineEvent.Type.STOP) {
                                    if(lastFrame1 < clipBGM1.getFrameLength()) {
                                        clipBGM1.setFramePosition(lastFrame1);
                                    }
                                    else {
                                        clipBGM1.setFramePosition(0);
                                    }

                                    if(clipSFX.isRunning()) {
                                        clipSFX.stop();
                                    }
                                    clipBGM1.start();

                                    pause1 = 0;
                                    String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                                    btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
                                    btnPlayPauseBGM1.setEnabled(true);
                                    btnStopBGM1.setEnabled(true);
                                }
                            };

                            pause1 = 1;
                            String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                            btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnIcon));
                            btnPlayPauseBGM1.setEnabled(false);
                            btnStopBGM1.setEnabled(false);

                            clipSFX.addLineListener(listener);
                        }
                    }

                    if(clipBGM2 != null) {
                        if(clipBGM2.isRunning()) {
                            lastFrame2 = clipBGM2.getFramePosition();
                            clipBGM2.stop();

                            LineListener listener = (LineEvent event) -> {
                                if(event.getType() == LineEvent.Type.STOP) {
                                    if(lastFrame2 < clipBGM2.getFrameLength()) {
                                        clipBGM2.setFramePosition(lastFrame2);
                                    }
                                    else {
                                        clipBGM2.setFramePosition(0);
                                    }

                                    if(clipSFX.isRunning()) {
                                        clipSFX.stop();
                                    }

                                    clipBGM2.start();

                                    pause2 = 0;
                                    String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_12px.png");
                                    btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
                                    btnPlayPauseBGM2.setEnabled(true);
                                    btnStopBGM2.setEnabled(true);
                                }
                            };

                            pause2 = 1;
                            String btnIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
                            btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnIcon));
                            btnPlayPauseBGM2.setEnabled(false);
                            btnStopBGM2.setEnabled(false);

                            clipSFX.addLineListener(listener);
                        }
                    }
                    clipSFX.start();
                }
            }
        }
    }
    
    public void autosave() {
        if(enableAutosave.equals("on")) {
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
    
    public void setFrameTitle(int type) {
        if(type == 1) {
            super.setTitle("Happy Buttons - (" + savedProfile + " • Changes saved)");
        }
        else if(type == 2) {
            super.setTitle("Happy Buttons - (Saving changes..)");
        }
    }
    
    public void visualizeSaving() {
        Timer timer1 = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFrameTitle(2);
            }
        });
        
        timer = new Timer(4000, new ActionListener() {
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
    
    public void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        
        if(files != null) {
            for(File f : files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                }
                else {
                    f.delete();
                }
            }
        }
        
        folder.delete();
    }
    
    public static void openMp3Frame() {
        if(mp3FrameOpened == 0) {
            mp3FrameOpened = 1;
            mp3 = new Mp3Frame();
            mp3.setVisible(true);
        }
        else {
            if(loadedIndexProfile != -1) {
                mp3.load();
                mp3.setTheme();
            }
            
            MainFrame.mp3.dispose();
            MainFrame.mp3 = new Mp3Frame();
            MainFrame.mp3.setVisible(true);
        }
        mp3FrameOpened = 1;
    }

    @Override
    public void run() {
        while(true) {
            Calendar cal = Calendar.getInstance();
            
            hour = cal.get(Calendar.HOUR_OF_DAY);
            minute = cal.get(Calendar.MINUTE);
            second = cal.get(Calendar.SECOND);
            
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss aa");
            Date dat = cal.getTime();
            String currentTime = sdf.format(dat);
            
            jMenuTime.setText("Time: " + currentTime);
        }
    }
    
//    static class CustomCheckboxUI extends javax.swing.plaf.basic.BasicCheckBoxUI {
//        public static ComponentUI createUI(JComponent c) {
//            return new CustomCheckboxUI();
//        }
//
//        @Override
//        public void paint(Graphics g, JComponent c) {
//            AbstractButton button = (AbstractButton) c;
//            ButtonModel model = button.getModel();
//            Font font = c.getFont();
//
//            Color tickColor = Color.LIGHT_GRAY; // Change this to the desired color
//            g.setFont(font);
//            g.setColor(Color.LIGHT_GRAY);
//            
//            if(model.isSelected()) {
//                g.setColor(tickColor);
//                g.fillRect(4, 4, 5, 5); // Adjust size and position of the check mark
//            }
//            else {
//                g.setColor(Color.WHITE);
//            }
//        }
//    }
}
