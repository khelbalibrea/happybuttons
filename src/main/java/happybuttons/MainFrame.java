/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package happybuttons;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
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
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
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
    public static int sfxPlaying = 0;
    public AudioInputStream media1;
    public static Clip clipBGM1 = null;
    public static Clip clipBGM2 = null;
    public static Clip clipSFX = null;
    public static int lastFrame1 = 0, lastFrame2 = 0;
    public static int chkSinglePlay = 1, chkStopBGM = 0;
    public static int loop1 = 1, loop2 = 1;
    public LineListener listenBGM1, listenBGM2;
    public boolean sfxOperation;
    
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
    public static String loadedProfile = "", savedProfile = "", strBGM = "", strSFX = "";
    
    // UI Components
    public static String sfxGroupName1 = "", sfxGroupName2 = "", sfxGroupName3 = "";
    
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
        
        String btnStopSFXIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\stop_sfx_12px.png");
        btnStopSFX.setIcon(new javax.swing.ImageIcon(btnStopSFXIcon));
        
        String itmNewIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\new_workspace_12px.png");
        itmNew.setIcon(new javax.swing.ImageIcon(itmNewIcon));
        
        String itmSaveIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\save_workspace_12px.png");
        itemSave.setIcon(new javax.swing.ImageIcon(itmSaveIcon));
        
        String itmLoadIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\download_12px.png");
        itmLoad.setIcon(new javax.swing.ImageIcon(itmLoadIcon));
        
        String itmResourceManagerIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\maintenance_12px.png");
        itmResouceManager.setIcon(new javax.swing.ImageIcon(itmResourceManagerIcon));
        
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
        jLabel1 = new javax.swing.JLabel();
        tfBGM1 = new javax.swing.JTextField();
        btnClearBGM1 = new javax.swing.JButton();
        btnStopBGM1 = new javax.swing.JButton();
        btnPlayPauseBGM1 = new javax.swing.JButton();
        volBGM1 = new javax.swing.JSlider();
        lblVolBGM1 = new javax.swing.JLabel();
        chkLoop1 = new javax.swing.JCheckBox();
        panelRow2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tfBGM2 = new javax.swing.JTextField();
        btnClearBGM2 = new javax.swing.JButton();
        btnStopBGM2 = new javax.swing.JButton();
        btnPlayPauseBGM2 = new javax.swing.JButton();
        volBGM2 = new javax.swing.JSlider();
        lblVolBGM2 = new javax.swing.JLabel();
        chkLoop2 = new javax.swing.JCheckBox();
        panelRow3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tfLastOperation = new javax.swing.JTextField();
        togLinkBGMVol = new javax.swing.JToggleButton();
        lblLinkBGMVolumes = new javax.swing.JLabel();
        panelRow6 = new javax.swing.JPanel();
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
        jLabel5 = new javax.swing.JLabel();
        panelSFX2 = new javax.swing.JPanel();
        tfSFXGroup2 = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        panelSFXGroup2 = new javax.swing.JPanel();
        panelR1S2 = new javax.swing.JPanel();
        btnR2SFX01 = new javax.swing.JButton();
        lblR2SFX01 = new javax.swing.JLabel();
        panelR1S3 = new javax.swing.JPanel();
        btnR2SFX02 = new javax.swing.JButton();
        lblR2SFX02 = new javax.swing.JLabel();
        panelR1S5 = new javax.swing.JPanel();
        btnR2SFX04 = new javax.swing.JButton();
        lblR2SFX04 = new javax.swing.JLabel();
        panelR1S4 = new javax.swing.JPanel();
        btnR2SFX03 = new javax.swing.JButton();
        lblR2SFX03 = new javax.swing.JLabel();
        panelR1S9 = new javax.swing.JPanel();
        btnR2SFX08 = new javax.swing.JButton();
        lblR2SFX08 = new javax.swing.JLabel();
        panelR1S6 = new javax.swing.JPanel();
        btnR2SFX05 = new javax.swing.JButton();
        lblR2SFX05 = new javax.swing.JLabel();
        panelR1S8 = new javax.swing.JPanel();
        btnR2SFX07 = new javax.swing.JButton();
        lblR2SFX07 = new javax.swing.JLabel();
        panelR1S7 = new javax.swing.JPanel();
        btnR2SFX06 = new javax.swing.JButton();
        lblR2SFX06 = new javax.swing.JLabel();
        panelR1S11 = new javax.swing.JPanel();
        btnR2SFX09 = new javax.swing.JButton();
        lblR2SFX09 = new javax.swing.JLabel();
        panelR1S12 = new javax.swing.JPanel();
        btnR2SFX10 = new javax.swing.JButton();
        lblR2SFX10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnR2SFX11 = new javax.swing.JButton();
        lblR2SFX11 = new javax.swing.JLabel();
        panelSFX3 = new javax.swing.JPanel();
        tfSFXGroup3 = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        panelSFXGroup3 = new javax.swing.JPanel();
        panelR1S13 = new javax.swing.JPanel();
        btnR3SFX01 = new javax.swing.JButton();
        lblR3SFX01 = new javax.swing.JLabel();
        panelR1S14 = new javax.swing.JPanel();
        btnR3SFX02 = new javax.swing.JButton();
        lblR3SFX02 = new javax.swing.JLabel();
        panelR1S15 = new javax.swing.JPanel();
        btnR3SFX04 = new javax.swing.JButton();
        lblR3SFX04 = new javax.swing.JLabel();
        panelR1S16 = new javax.swing.JPanel();
        btnR3SFX03 = new javax.swing.JButton();
        lblR3SFX03 = new javax.swing.JLabel();
        panelR1S17 = new javax.swing.JPanel();
        btnR3SFX08 = new javax.swing.JButton();
        lblR3SFX08 = new javax.swing.JLabel();
        panelR1S18 = new javax.swing.JPanel();
        btnR3SFX05 = new javax.swing.JButton();
        lblR3SFX05 = new javax.swing.JLabel();
        panelR1S19 = new javax.swing.JPanel();
        btnR3SFX07 = new javax.swing.JButton();
        lblR3SFX07 = new javax.swing.JLabel();
        panelR1S20 = new javax.swing.JPanel();
        btnR3SFX06 = new javax.swing.JButton();
        lblR3SFX06 = new javax.swing.JLabel();
        panelR1S21 = new javax.swing.JPanel();
        btnR3SFX09 = new javax.swing.JButton();
        lblR3SFX09 = new javax.swing.JLabel();
        panelR1S22 = new javax.swing.JPanel();
        btnR3SFX10 = new javax.swing.JButton();
        lblR3SFX10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnR3SFX11 = new javax.swing.JButton();
        lblR3SFX11 = new javax.swing.JLabel();
        btnStopSFX = new javax.swing.JButton();
        panelRadio = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        chkIB = new javax.swing.JCheckBox();
        chkSP = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        itmNew = new javax.swing.JMenuItem();
        itemSave = new javax.swing.JMenuItem();
        itmLoad = new javax.swing.JMenuItem();
        itmTools = new javax.swing.JMenu();
        itmResouceManager = new javax.swing.JMenuItem();
        itmAbout = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1366, 700));
        setResizable(false);
        setSize(new java.awt.Dimension(1366, 733));

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

        listBGM.setDragEnabled(true);
        jScrollPane2.setViewportView(listBGM);

        javax.swing.GroupLayout panelJListLayout = new javax.swing.GroupLayout(panelJList);
        panelJList.setLayout(panelJListLayout);
        panelJListLayout.setHorizontalGroup(
            panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJListLayout.createSequentialGroup()
                .addGroup(panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelJListLayout.createSequentialGroup()
                        .addComponent(btnAddBGM, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteBGM, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(629, 629, 629))
                    .addGroup(panelJListLayout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(panelJListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelJListLayout.createSequentialGroup()
                        .addComponent(btnAddSFX, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteSFX, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDeleteSFX, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)))
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

        jLabel1.setText("BGM1:");

        tfBGM1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
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
        btnStopBGM1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopBGM1ActionPerformed(evt);
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

        volBGM1.setToolTipText("BGM1 volume");
        volBGM1.setValue(100);
        volBGM1.setMaximumSize(new java.awt.Dimension(200, 20));
        volBGM1.setMinimumSize(new java.awt.Dimension(200, 20));
        volBGM1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                volBGM1StateChanged(evt);
            }
        });

        lblVolBGM1.setText("Vol1: 100");
        lblVolBGM1.setMaximumSize(new java.awt.Dimension(60, 22));
        lblVolBGM1.setMinimumSize(new java.awt.Dimension(60, 22));
        lblVolBGM1.setPreferredSize(new java.awt.Dimension(60, 22));

        chkLoop1.setSelected(true);
        chkLoop1.setToolTipText("Loop BGM1");
        chkLoop1.setFocusable(false);
        chkLoop1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLoop1ActionPerformed(evt);
            }
        });

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkLoop1)
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
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblVolBGM1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(chkLoop1))))))
        );

        jLabel4.setText("BGM2:");

        tfBGM2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tfBGM2.setFocusable(false);
        tfBGM2.setName("tfBGM1"); // NOI18N
        tfBGM2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tfBGM2PropertyChange(evt);
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

        btnStopBGM2.setToolTipText("Stop BGM2");
        btnStopBGM2.setMaximumSize(new java.awt.Dimension(22, 22));
        btnStopBGM2.setMinimumSize(new java.awt.Dimension(22, 22));
        btnStopBGM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopBGM2ActionPerformed(evt);
            }
        });

        btnPlayPauseBGM2.setToolTipText("Play or pause BGM2");
        btnPlayPauseBGM2.setMaximumSize(new java.awt.Dimension(22, 22));
        btnPlayPauseBGM2.setMinimumSize(new java.awt.Dimension(22, 22));
        btnPlayPauseBGM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayPauseBGM2ActionPerformed(evt);
            }
        });

        volBGM2.setToolTipText("BGM2 volume");
        volBGM2.setValue(100);
        volBGM2.setMaximumSize(new java.awt.Dimension(200, 20));
        volBGM2.setMinimumSize(new java.awt.Dimension(200, 20));
        volBGM2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                volBGM2StateChanged(evt);
            }
        });

        lblVolBGM2.setText("Vol2: 100");
        lblVolBGM2.setMaximumSize(new java.awt.Dimension(60, 22));
        lblVolBGM2.setMinimumSize(new java.awt.Dimension(60, 22));
        lblVolBGM2.setPreferredSize(new java.awt.Dimension(60, 22));

        chkLoop2.setSelected(true);
        chkLoop2.setToolTipText("Loop BGM2");
        chkLoop2.setFocusable(false);
        chkLoop2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLoop2ActionPerformed(evt);
            }
        });

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkLoop2)
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
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRow2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblVolBGM2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(chkLoop2))))))
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

        lblVolSFX.setText("SFX Vol: 100");
        lblVolSFX.setMaximumSize(new java.awt.Dimension(85, 22));
        lblVolSFX.setMinimumSize(new java.awt.Dimension(85, 22));
        lblVolSFX.setPreferredSize(new java.awt.Dimension(85, 22));

        volSFX.setToolTipText("BGM1 volume");
        volSFX.setValue(100);
        volSFX.setMaximumSize(new java.awt.Dimension(200, 20));
        volSFX.setMinimumSize(new java.awt.Dimension(200, 20));
        volSFX.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                volSFXStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelRow6Layout = new javax.swing.GroupLayout(panelRow6);
        panelRow6.setLayout(panelRow6Layout);
        panelRow6Layout.setHorizontalGroup(
            panelRow6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRow6Layout.createSequentialGroup()
                .addComponent(lblVolSFX, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(volSFX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRow6Layout.setVerticalGroup(
            panelRow6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRow6Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(panelRow6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(volSFX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVolSFX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        panelSFX1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

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
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

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
                .addComponent(lblR1SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
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
            .addGroup(panelSFXGroup1Layout.createSequentialGroup()
                .addComponent(panelR1S01, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelSFXGroup1Layout.createSequentialGroup()
                .addGroup(panelSFXGroup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S09, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S08, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S07, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S06, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S05, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S04, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S03, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S02, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel5.setText("SFX Group Names");

        javax.swing.GroupLayout panelSFX1Layout = new javax.swing.GroupLayout(panelSFX1);
        panelSFX1.setLayout(panelSFX1Layout);
        panelSFX1Layout.setHorizontalGroup(
            panelSFX1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSFX1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSFX1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSFX1Layout.createSequentialGroup()
                        .addComponent(tfSFXGroup1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSFX1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(55, 55, 55)))
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSFXGroup1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSFX1Layout.setVerticalGroup(
            panelSFX1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addComponent(panelSFXGroup1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSFX1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfSFXGroup1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        panelSFX2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        tfSFXGroup2.setEditable(false);
        tfSFXGroup2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfSFXGroup2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tfSFXGroup2.setMaximumSize(new java.awt.Dimension(200, 22));
        tfSFXGroup2.setMinimumSize(new java.awt.Dimension(200, 22));
        tfSFXGroup2.setPreferredSize(new java.awt.Dimension(200, 22));
        tfSFXGroup2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfSFXGroup2MouseClicked(evt);
            }
        });
        tfSFXGroup2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSFXGroup2ActionPerformed(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        panelR1S2.setMaximumSize(new java.awt.Dimension(90, 88));
        panelR1S2.setMinimumSize(new java.awt.Dimension(90, 88));

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

        javax.swing.GroupLayout panelR1S2Layout = new javax.swing.GroupLayout(panelR1S2);
        panelR1S2.setLayout(panelR1S2Layout);
        panelR1S2Layout.setHorizontalGroup(
            panelR1S2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX01, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S2Layout.setVerticalGroup(
            panelR1S2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S2Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S3Layout = new javax.swing.GroupLayout(panelR1S3);
        panelR1S3.setLayout(panelR1S3Layout);
        panelR1S3Layout.setHorizontalGroup(
            panelR1S3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelR1S3Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(btnR2SFX02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(panelR1S3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX02, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S3Layout.setVerticalGroup(
            panelR1S3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S3Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S5Layout = new javax.swing.GroupLayout(panelR1S5);
        panelR1S5.setLayout(panelR1S5Layout);
        panelR1S5Layout.setHorizontalGroup(
            panelR1S5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S5Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX04, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S5Layout.setVerticalGroup(
            panelR1S5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S5Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S4Layout = new javax.swing.GroupLayout(panelR1S4);
        panelR1S4.setLayout(panelR1S4Layout);
        panelR1S4Layout.setHorizontalGroup(
            panelR1S4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX03, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S4Layout.setVerticalGroup(
            panelR1S4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S4Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S9Layout = new javax.swing.GroupLayout(panelR1S9);
        panelR1S9.setLayout(panelR1S9Layout);
        panelR1S9Layout.setHorizontalGroup(
            panelR1S9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S9Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX08, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX08, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S9Layout.setVerticalGroup(
            panelR1S9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S9Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S6Layout = new javax.swing.GroupLayout(panelR1S6);
        panelR1S6.setLayout(panelR1S6Layout);
        panelR1S6Layout.setHorizontalGroup(
            panelR1S6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S6Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX05, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX05, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S6Layout.setVerticalGroup(
            panelR1S6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S6Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S8Layout = new javax.swing.GroupLayout(panelR1S8);
        panelR1S8.setLayout(panelR1S8Layout);
        panelR1S8Layout.setHorizontalGroup(
            panelR1S8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelR1S8Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(btnR2SFX07, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(panelR1S8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX07, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S8Layout.setVerticalGroup(
            panelR1S8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S8Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S7Layout = new javax.swing.GroupLayout(panelR1S7);
        panelR1S7.setLayout(panelR1S7Layout);
        panelR1S7Layout.setHorizontalGroup(
            panelR1S7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S7Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX06, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX06, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S7Layout.setVerticalGroup(
            panelR1S7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S7Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S11Layout = new javax.swing.GroupLayout(panelR1S11);
        panelR1S11.setLayout(panelR1S11Layout);
        panelR1S11Layout.setHorizontalGroup(
            panelR1S11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S11Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX09, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX09, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S11Layout.setVerticalGroup(
            panelR1S11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S11Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S12Layout = new javax.swing.GroupLayout(panelR1S12);
        panelR1S12.setLayout(panelR1S12Layout);
        panelR1S12Layout.setHorizontalGroup(
            panelR1S12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S12Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(btnR2SFX10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(panelR1S12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S12Layout.setVerticalGroup(
            panelR1S12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S12Layout.createSequentialGroup()
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR2SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR2SFX11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR2SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR2SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout panelSFXGroup2Layout = new javax.swing.GroupLayout(panelSFXGroup2);
        panelSFXGroup2.setLayout(panelSFXGroup2Layout);
        panelSFXGroup2Layout.setHorizontalGroup(
            panelSFXGroup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSFXGroup2Layout.createSequentialGroup()
                .addComponent(panelR1S2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelSFXGroup2Layout.setVerticalGroup(
            panelSFXGroup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSFXGroup2Layout.createSequentialGroup()
                .addGroup(panelSFXGroup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelSFX2Layout = new javax.swing.GroupLayout(panelSFX2);
        panelSFX2.setLayout(panelSFX2Layout);
        panelSFX2Layout.setHorizontalGroup(
            panelSFX2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSFX2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfSFXGroup2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSFXGroup2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSFX2Layout.setVerticalGroup(
            panelSFX2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addGroup(panelSFX2Layout.createSequentialGroup()
                .addComponent(panelSFXGroup2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelSFX2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(tfSFXGroup2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelSFX3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        tfSFXGroup3.setEditable(false);
        tfSFXGroup3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfSFXGroup3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tfSFXGroup3.setMaximumSize(new java.awt.Dimension(200, 22));
        tfSFXGroup3.setMinimumSize(new java.awt.Dimension(200, 22));
        tfSFXGroup3.setPreferredSize(new java.awt.Dimension(200, 22));
        tfSFXGroup3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfSFXGroup3MouseClicked(evt);
            }
        });
        tfSFXGroup3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSFXGroup3ActionPerformed(evt);
            }
        });

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        panelR1S13.setMaximumSize(new java.awt.Dimension(90, 88));
        panelR1S13.setMinimumSize(new java.awt.Dimension(90, 88));

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

        javax.swing.GroupLayout panelR1S13Layout = new javax.swing.GroupLayout(panelR1S13);
        panelR1S13.setLayout(panelR1S13Layout);
        panelR1S13Layout.setHorizontalGroup(
            panelR1S13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S13Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX01, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S13Layout.setVerticalGroup(
            panelR1S13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S13Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S14Layout = new javax.swing.GroupLayout(panelR1S14);
        panelR1S14.setLayout(panelR1S14Layout);
        panelR1S14Layout.setHorizontalGroup(
            panelR1S14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelR1S14Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(btnR3SFX02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(panelR1S14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX02, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S14Layout.setVerticalGroup(
            panelR1S14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S14Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S15Layout = new javax.swing.GroupLayout(panelR1S15);
        panelR1S15.setLayout(panelR1S15Layout);
        panelR1S15Layout.setHorizontalGroup(
            panelR1S15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S15Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX04, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S15Layout.setVerticalGroup(
            panelR1S15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S15Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S16Layout = new javax.swing.GroupLayout(panelR1S16);
        panelR1S16.setLayout(panelR1S16Layout);
        panelR1S16Layout.setHorizontalGroup(
            panelR1S16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S16Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX03, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S16Layout.setVerticalGroup(
            panelR1S16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S16Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S17Layout = new javax.swing.GroupLayout(panelR1S17);
        panelR1S17.setLayout(panelR1S17Layout);
        panelR1S17Layout.setHorizontalGroup(
            panelR1S17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S17Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX08, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX08, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S17Layout.setVerticalGroup(
            panelR1S17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S17Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S18Layout = new javax.swing.GroupLayout(panelR1S18);
        panelR1S18.setLayout(panelR1S18Layout);
        panelR1S18Layout.setHorizontalGroup(
            panelR1S18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S18Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX05, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX05, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S18Layout.setVerticalGroup(
            panelR1S18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S18Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S19Layout = new javax.swing.GroupLayout(panelR1S19);
        panelR1S19.setLayout(panelR1S19Layout);
        panelR1S19Layout.setHorizontalGroup(
            panelR1S19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelR1S19Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(btnR3SFX07, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(panelR1S19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX07, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S19Layout.setVerticalGroup(
            panelR1S19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S19Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S20Layout = new javax.swing.GroupLayout(panelR1S20);
        panelR1S20.setLayout(panelR1S20Layout);
        panelR1S20Layout.setHorizontalGroup(
            panelR1S20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S20Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX06, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX06, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S20Layout.setVerticalGroup(
            panelR1S20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S20Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S21Layout = new javax.swing.GroupLayout(panelR1S21);
        panelR1S21.setLayout(panelR1S21Layout);
        panelR1S21Layout.setHorizontalGroup(
            panelR1S21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S21Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX09, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(panelR1S21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX09, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S21Layout.setVerticalGroup(
            panelR1S21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S21Layout.createSequentialGroup()
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

        javax.swing.GroupLayout panelR1S22Layout = new javax.swing.GroupLayout(panelR1S22);
        panelR1S22.setLayout(panelR1S22Layout);
        panelR1S22Layout.setHorizontalGroup(
            panelR1S22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S22Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(btnR3SFX10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(panelR1S22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelR1S22Layout.setVerticalGroup(
            panelR1S22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelR1S22Layout.createSequentialGroup()
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnR3SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblR3SFX11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnR3SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblR3SFX11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout panelSFXGroup3Layout = new javax.swing.GroupLayout(panelSFXGroup3);
        panelSFXGroup3.setLayout(panelSFXGroup3Layout);
        panelSFXGroup3Layout.setHorizontalGroup(
            panelSFXGroup3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSFXGroup3Layout.createSequentialGroup()
                .addComponent(panelR1S13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelR1S22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelSFXGroup3Layout.setVerticalGroup(
            panelSFXGroup3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSFXGroup3Layout.createSequentialGroup()
                .addGroup(panelSFXGroup3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelR1S13, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelSFX3Layout = new javax.swing.GroupLayout(panelSFX3);
        panelSFX3.setLayout(panelSFX3Layout);
        panelSFX3Layout.setHorizontalGroup(
            panelSFX3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSFX3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfSFXGroup3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSFXGroup3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSFX3Layout.setVerticalGroup(
            panelSFX3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator4)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSFX3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tfSFXGroup3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
            .addGroup(panelSFX3Layout.createSequentialGroup()
                .addComponent(panelSFXGroup3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnStopSFX.setToolTipText("Stop SFX");
        btnStopSFX.setMaximumSize(new java.awt.Dimension(22, 22));
        btnStopSFX.setMinimumSize(new java.awt.Dimension(22, 22));
        btnStopSFX.setPreferredSize(new java.awt.Dimension(22, 22));
        btnStopSFX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopSFXActionPerformed(evt);
            }
        });

        panelRadio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("SFX State:");

        chkIB.setText("IB");
        chkIB.setToolTipText("Interrupt BGM");
        chkIB.setEnabled(false);
        chkIB.setFocusable(false);
        chkIB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIBActionPerformed(evt);
            }
        });

        chkSP.setSelected(true);
        chkSP.setText("SP");
        chkSP.setToolTipText("Single play");
        chkSP.setFocusable(false);
        chkSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRadioLayout = new javax.swing.GroupLayout(panelRadio);
        panelRadio.setLayout(panelRadioLayout);
        panelRadioLayout.setHorizontalGroup(
            panelRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRadioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(24, 24, 24)
                .addComponent(chkSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkIB)
                .addContainerGap())
        );
        panelRadioLayout.setVerticalGroup(
            panelRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRadioLayout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addGroup(panelRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(chkIB)
                    .addComponent(chkSP)))
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

        itmTools.setText("Tools");
        itmTools.setName("menuEdit"); // NOI18N

        itmResouceManager.setText("Resources");
        itmResouceManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmResouceManagerActionPerformed(evt);
            }
        });
        itmTools.add(itmResouceManager);

        jMenuBar1.add(itmTools);

        itmAbout.setText("About");
        itmAbout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itmAboutMouseClicked(evt);
            }
        });
        jMenuBar1.add(itmAbout);

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
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(panelRow6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(22, 22, 22)
                                .addComponent(panelRadio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnStopSFX, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(panelRow1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelRow2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelRow3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(panelJList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jSeparator1)))
                    .addComponent(panelSFX1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSFX2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSFX3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelRow6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(btnStopSFX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelRadio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelSFX1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelSFX2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelSFX3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))))
        );

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

            tfLastOperation.setText("[Clear] BGM1: " + tfBGM1.getText());
            tfBGM1.setText("");
        }
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
                }
                
                if(!blist.contains(Utility.renameListName(file.getName()))) {
                    blist.addElement(Utility.renameListName(file.getName()));
                    tfLastOperation.setText("[ADDED BGM]:: " + file.getName());
                }
                
                listBGM.setModel(blist);
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
                }
                
                if(!slist.contains(Utility.renameListName(file.getName()))) {
                    slist.addElement(Utility.renameListName(file.getName()));
                    tfLastOperation.setText("[ADDED SFX]:: " + file.getName());
                }
                
                listSFX.setModel(slist);
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

            tfLastOperation.setText("[Clear] BGM2: " + tfBGM2.getText());
            tfBGM2.setText("");
        }
    }//GEN-LAST:event_btnClearBGM2ActionPerformed

    private void btnPlayPauseBGM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayPauseBGM2ActionPerformed
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
    }//GEN-LAST:event_btnPlayPauseBGM2ActionPerformed

    private void btnDeleteBGMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteBGMActionPerformed
        if(selectedBGMItem != "") {
            tfLastOperation.setText("[DELETE BGM]:: " + selectedBGMItem);
            blist.removeElement(selectedBGMItem);
            selectedBGMItem = "";
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
                    slist.removeElement(selectedSFXItem);
                    selectedSFXItem = "";
                }
            }
            else {
                tfLastOperation.setText("[REMOVE SFX]:: " + selectedSFXItem);
                slist.removeElement(selectedSFXItem);
                selectedSFXItem = "";
            }
            
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

    private void btnR1SFX01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX01ActionPerformed
        playSFX(lblR1SFX01.getText());
    }//GEN-LAST:event_btnR1SFX01ActionPerformed

    private void tfSFXGroup1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfSFXGroup1MouseClicked
        if(evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            tfSFXGroup1.setEditable(true);
        }
    }//GEN-LAST:event_tfSFXGroup1MouseClicked

    private void btnR1SFX02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX02ActionPerformed
        playSFX(lblR1SFX02.getText());
    }//GEN-LAST:event_btnR1SFX02ActionPerformed

    private void btnR1SFX03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX03ActionPerformed
        playSFX(lblR1SFX03.getText());
    }//GEN-LAST:event_btnR1SFX03ActionPerformed

    private void btnR1SFX04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX04ActionPerformed
        playSFX(lblR1SFX04.getText());
    }//GEN-LAST:event_btnR1SFX04ActionPerformed

    private void btnR1SFX05ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX05ActionPerformed
        playSFX(lblR1SFX05.getText());
    }//GEN-LAST:event_btnR1SFX05ActionPerformed

    private void btnR1SFX06ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX06ActionPerformed
        playSFX(lblR1SFX06.getText());
    }//GEN-LAST:event_btnR1SFX06ActionPerformed

    private void btnR1SFX07ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX07ActionPerformed
        playSFX(lblR1SFX07.getText());
    }//GEN-LAST:event_btnR1SFX07ActionPerformed

    private void btnR1SFX08ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX08ActionPerformed
        playSFX(lblR1SFX08.getText());
    }//GEN-LAST:event_btnR1SFX08ActionPerformed

    private void btnR1SFX09ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX09ActionPerformed
        playSFX(lblR1SFX09.getText());
    }//GEN-LAST:event_btnR1SFX09ActionPerformed

    private void btnR1SFX10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX10ActionPerformed
        playSFX(lblR1SFX10.getText());
    }//GEN-LAST:event_btnR1SFX10ActionPerformed

    private void btnR1SFX11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR1SFX11ActionPerformed
        playSFX(lblR1SFX11.getText());
    }//GEN-LAST:event_btnR1SFX11ActionPerformed

    private void itemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSaveActionPerformed
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
        
        SaveFrame saveFrame = new SaveFrame(HappyButtons.mf, true);
        saveFrame.setVisible(true);
        
        if(!saveFrame.isShowing()) {
            if(!"error".equals(savedProfile)) {
                super.setTitle("Happy Buttons - (" + savedProfile + ")");
            }
        }
    }//GEN-LAST:event_itemSaveActionPerformed

    private void itmNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmNewActionPerformed
        tfBGM1.setText("");
    }//GEN-LAST:event_itmNewActionPerformed

    private void tfSFXGroup1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSFXGroup1ActionPerformed
        tfSFXGroup1.setEditable(false);
        sfxGroupName1 = tfSFXGroup1.getText();
    }//GEN-LAST:event_tfSFXGroup1ActionPerformed

    private void tfSFXGroup2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfSFXGroup2MouseClicked
        if(evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            tfSFXGroup2.setEditable(true);
        }
    }//GEN-LAST:event_tfSFXGroup2MouseClicked

    private void tfSFXGroup2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSFXGroup2ActionPerformed
        tfSFXGroup2.setEditable(false);
        sfxGroupName2 = tfSFXGroup2.getText();
    }//GEN-LAST:event_tfSFXGroup2ActionPerformed

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

    private void itmLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmLoadActionPerformed
        LoadFrame loadFrame = new LoadFrame(HappyButtons.mf, true);
        loadFrame.setVisible(true);
        
        if(!loadFrame.isShowing()) {
            if(!"error".equals(loadedProfile)) {
                super.setTitle("Happy Buttons - (" + loadedProfile + ")");
            }
        }
    }//GEN-LAST:event_itmLoadActionPerformed

    private void tfSFXGroup3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfSFXGroup3MouseClicked
        if(evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            tfSFXGroup3.setEditable(true);
        }
    }//GEN-LAST:event_tfSFXGroup3MouseClicked

    private void tfSFXGroup3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSFXGroup3ActionPerformed
        tfSFXGroup3.setEditable(false);
        sfxGroupName3 = tfSFXGroup3.getText();
    }//GEN-LAST:event_tfSFXGroup3ActionPerformed

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
                bgmVol1 = -30.5f + (10f - f)*(-5.5f);
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
                bgmVol2 = -30.5f + (10f - f)*(-5.5f);
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

    private void btnStopSFXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopSFXActionPerformed
        if(clipSFX != null) {
            clipSFX.stop();
        }
    }//GEN-LAST:event_btnStopSFXActionPerformed

    private void tfBGM1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfBGM1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfBGM1KeyPressed

    private void itmResouceManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmResouceManagerActionPerformed
        ResourceManagerFrame rsFrame = new ResourceManagerFrame(HappyButtons.mf, true);
        rsFrame.setVisible(true);
    }//GEN-LAST:event_itmResouceManagerActionPerformed

    private void itmAboutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itmAboutMouseClicked
        AboutFrame aboutFrame = new AboutFrame(HappyButtons.mf, true);
        aboutFrame.setVisible(true);
    }//GEN-LAST:event_itmAboutMouseClicked

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
    private javax.swing.JButton btnR2SFX01;
    private javax.swing.JButton btnR2SFX02;
    private javax.swing.JButton btnR2SFX03;
    private javax.swing.JButton btnR2SFX04;
    private javax.swing.JButton btnR2SFX05;
    private javax.swing.JButton btnR2SFX06;
    private javax.swing.JButton btnR2SFX07;
    private javax.swing.JButton btnR2SFX08;
    private javax.swing.JButton btnR2SFX09;
    private javax.swing.JButton btnR2SFX10;
    private javax.swing.JButton btnR2SFX11;
    private javax.swing.JButton btnR3SFX01;
    private javax.swing.JButton btnR3SFX02;
    private javax.swing.JButton btnR3SFX03;
    private javax.swing.JButton btnR3SFX04;
    private javax.swing.JButton btnR3SFX05;
    private javax.swing.JButton btnR3SFX06;
    private javax.swing.JButton btnR3SFX07;
    private javax.swing.JButton btnR3SFX08;
    private javax.swing.JButton btnR3SFX09;
    private javax.swing.JButton btnR3SFX10;
    private javax.swing.JButton btnR3SFX11;
    private javax.swing.JButton btnStopBGM1;
    private javax.swing.JButton btnStopBGM2;
    private javax.swing.JButton btnStopSFX;
    private javax.swing.JCheckBox chkIB;
    private javax.swing.JCheckBox chkLoop1;
    private javax.swing.JCheckBox chkLoop2;
    private javax.swing.JCheckBox chkSP;
    private javax.swing.JMenuItem itemSave;
    private javax.swing.JMenu itmAbout;
    private javax.swing.JMenuItem itmLoad;
    private javax.swing.JMenuItem itmNew;
    private javax.swing.JMenuItem itmResouceManager;
    private javax.swing.JMenu itmTools;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
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
    private javax.swing.JLabel lblVolBGM1;
    private javax.swing.JLabel lblVolBGM2;
    private javax.swing.JLabel lblVolSFX;
    public static javax.swing.JList<String> listBGM;
    public static javax.swing.JList<String> listSFX;
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
    private javax.swing.JPanel panelR1S11;
    private javax.swing.JPanel panelR1S12;
    private javax.swing.JPanel panelR1S13;
    private javax.swing.JPanel panelR1S14;
    private javax.swing.JPanel panelR1S15;
    private javax.swing.JPanel panelR1S16;
    private javax.swing.JPanel panelR1S17;
    private javax.swing.JPanel panelR1S18;
    private javax.swing.JPanel panelR1S19;
    private javax.swing.JPanel panelR1S2;
    private javax.swing.JPanel panelR1S20;
    private javax.swing.JPanel panelR1S21;
    private javax.swing.JPanel panelR1S22;
    private javax.swing.JPanel panelR1S3;
    private javax.swing.JPanel panelR1S4;
    private javax.swing.JPanel panelR1S5;
    private javax.swing.JPanel panelR1S6;
    private javax.swing.JPanel panelR1S7;
    private javax.swing.JPanel panelR1S8;
    private javax.swing.JPanel panelR1S9;
    private javax.swing.JPanel panelRadio;
    private javax.swing.JPanel panelRight;
    private javax.swing.JPanel panelRow1;
    private javax.swing.JPanel panelRow2;
    private javax.swing.JPanel panelRow3;
    private javax.swing.JPanel panelRow6;
    private javax.swing.JPanel panelSFX1;
    private javax.swing.JPanel panelSFX2;
    private javax.swing.JPanel panelSFX3;
    private javax.swing.JPanel panelSFXGroup1;
    private javax.swing.JPanel panelSFXGroup2;
    private javax.swing.JPanel panelSFXGroup3;
    private javax.swing.JTextField tfBGM1;
    private javax.swing.JTextField tfBGM2;
    private javax.swing.JTextField tfLastOperation;
    public static javax.swing.JTextField tfSFXGroup1;
    public static javax.swing.JTextField tfSFXGroup2;
    public static javax.swing.JTextField tfSFXGroup3;
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
        this.clipSFX = (Clip) AudioSystem.getLine(info);
        this.clipSFX.open(audioStream);
    }
    
    public void playSFX(String sfxName) {
        sfxOperation = true;
        if(!sfxName.equals("blank")){
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

                            clipSFX.start();
                        }
                        catch(IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                            tfLastOperation.setText("[ERROR]:: " + e.toString());
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
}
