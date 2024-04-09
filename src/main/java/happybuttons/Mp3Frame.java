/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package happybuttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

/**
 *
 * @author Michael Balibrea
 */
public class Mp3Frame extends javax.swing.JFrame {
    String theme = HappyButtons.uiTheme;
    JFrame thisFrame = this;
    
    
    public static DefaultListModel mlist = new DefaultListModel();
    /**
     * Creates new form Mp3Frame
     */
    public Mp3Frame() {
        initComponents();
        super.setTitle("MP3 Player");
                
        // set frame icon
        ImageIcon imgIcon = new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png"));
        setIconImage(imgIcon.getImage());
        
        setTheme();
        
        // adding listener when window is disposing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainFrame.mp3FrameOpened = 0;
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSongMp3 = new javax.swing.JLabel();
        btnBackMp3 = new javax.swing.JButton();
        btnNextMp3 = new javax.swing.JButton();
        btnPlayPauseMp3 = new javax.swing.JButton();
        volMp3 = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        lblLastFrame = new javax.swing.JLabel();
        tfSearch = new javax.swing.JTextField();
        btnDeleteMp3 = new javax.swing.JButton();
        btnAddMp3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listMp3 = new javax.swing.JList<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        sliderSongTime = new javax.swing.JSlider();
        lblDuration = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        lblSongMp3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSongMp3.setText("Song.mp3");

        btnBackMp3.setFocusable(false);
        btnBackMp3.setMaximumSize(new java.awt.Dimension(35, 35));
        btnBackMp3.setMinimumSize(new java.awt.Dimension(35, 35));
        btnBackMp3.setPreferredSize(new java.awt.Dimension(35, 35));
        btnBackMp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackMp3ActionPerformed(evt);
            }
        });

        btnNextMp3.setFocusable(false);
        btnNextMp3.setMaximumSize(new java.awt.Dimension(35, 35));
        btnNextMp3.setMinimumSize(new java.awt.Dimension(35, 35));
        btnNextMp3.setPreferredSize(new java.awt.Dimension(35, 35));
        btnNextMp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextMp3ActionPerformed(evt);
            }
        });

        btnPlayPauseMp3.setFocusable(false);
        btnPlayPauseMp3.setMaximumSize(new java.awt.Dimension(35, 35));
        btnPlayPauseMp3.setMinimumSize(new java.awt.Dimension(35, 35));
        btnPlayPauseMp3.setPreferredSize(new java.awt.Dimension(35, 35));
        btnPlayPauseMp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayPauseMp3ActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("V");

        lblLastFrame.setText("0:00:00");

        tfSearch.setText("Search");
        tfSearch.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btnDeleteMp3.setMaximumSize(new java.awt.Dimension(22, 22));
        btnDeleteMp3.setMinimumSize(new java.awt.Dimension(22, 22));
        btnDeleteMp3.setPreferredSize(new java.awt.Dimension(22, 22));

        btnAddMp3.setMaximumSize(new java.awt.Dimension(22, 22));
        btnAddMp3.setMinimumSize(new java.awt.Dimension(22, 22));
        btnAddMp3.setPreferredSize(new java.awt.Dimension(22, 22));
        btnAddMp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMp3ActionPerformed(evt);
            }
        });

        listMp3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        listMp3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listMp3.setVisibleRowCount(100);
        jScrollPane1.setViewportView(listMp3);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("R");
        jLabel5.setMaximumSize(new java.awt.Dimension(35, 35));
        jLabel5.setMinimumSize(new java.awt.Dimension(35, 35));
        jLabel5.setPreferredSize(new java.awt.Dimension(35, 35));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("S");
        jLabel6.setMaximumSize(new java.awt.Dimension(35, 35));
        jLabel6.setMinimumSize(new java.awt.Dimension(35, 35));
        jLabel6.setPreferredSize(new java.awt.Dimension(35, 35));

        lblDuration.setText("0:00:00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBackMp3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(btnPlayPauseMp3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNextMp3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(volMp3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(tfSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddMp3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteMp3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblSongMp3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblLastFrame)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sliderSongTime, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBackMp3, btnNextMp3, btnPlayPauseMp3});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteMp3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddMp3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSongMp3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLastFrame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sliderSongTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDuration, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPlayPauseMp3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNextMp3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBackMp3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(volMp3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnBackMp3, btnNextMp3, btnPlayPauseMp3});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddMp3, btnDeleteMp3});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblDuration, lblLastFrame, sliderSongTime});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackMp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackMp3ActionPerformed
        
    }//GEN-LAST:event_btnBackMp3ActionPerformed

    private void btnNextMp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextMp3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNextMp3ActionPerformed

    private void btnPlayPauseMp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayPauseMp3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPlayPauseMp3ActionPerformed

    private void btnAddMp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMp3ActionPerformed
        Object[] options = {"Add from App Resource", "Add from My files"};
        
        int choice = JOptionPane.showOptionDialog(this, "Select path where to get MP3 files",
                "Get MP3 source",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        if(choice == 0){
            AddMp3Frame addMp3Frame = new AddMp3Frame(this, true);
            addMp3Frame.setVisible(true);
        }
        else if(choice == 1) {
            JFileChooser fc = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("MP3 or WAV File", "wav", "mp3");
            fc.setFileFilter(filter);
            fc.setMultiSelectionEnabled(true);
            int returnValue = fc.showOpenDialog(HappyButtons.mf);

            File[] selectedFiles = fc.getSelectedFiles();

            for(File file : selectedFiles) {
                if(Utility.getFileExtension(file.toString()).equalsIgnoreCase("wav")) { // Music file is in wave format
                    try {
                        File mp3Path = new File(HappyButtons.documentsPath + "/HappyButtons/mp3s");

                        if(!mp3Path.exists()){
                            mp3Path.mkdir();
                        }

                        FileChannel src = new FileInputStream(file.getAbsolutePath()).getChannel();
                        File destCheck = new File(HappyButtons.documentsPath + "\\HappyButtons\\mp3s\\" + file.getName());

                        if(!destCheck.exists()) {
                            FileChannel dest = new FileOutputStream(HappyButtons.documentsPath + "\\HappyButtons\\mp3s\\" + file.getName()).getChannel();

                            src.transferTo(0, src.size(), dest);

                            src.close();
                            dest.close();
                        }

                        if(!MainFrame.mlist.contains(Utility.renameListName(file.getName(), "wav"))) {
                            MainFrame.mlist.addElement(Utility.renameListName(file.getName(), "wav"));
                            MainFrame.tfLastOperation.setText("[ADDED MP3]:: " + file.getName());
                        }

                        listMp3.setModel(MainFrame.mlist);
                    }
                    catch(IOException ex) {
                        Notification panel = new Notification(HappyButtons.mf, 
                            Notification.Type.ERROR, 
                            MainFrame.location, 
                            "IO Error",
                            "Error reading/writing file"
                        );
                        panel.showNotification();
                    }
                }
                else { // Music file is in mp3 format
                    try {
                        File mp3Path = new File(HappyButtons.documentsPath + "/HappyButtons/mp3s");

                        if(!mp3Path.exists()){
                            mp3Path.mkdir();
                        }

                        FileChannel src = new FileInputStream(file.getAbsolutePath()).getChannel();
                        String strReplace = Utility.renameListName(file.getName(), "mp3") + ".wav";
                        File destCheck = new File(HappyButtons.documentsPath + "\\HappyButtons\\mp3s\\" + strReplace);

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
                            String str = HappyButtons.documentsPath + Utility.strDoubleSlash("\\HappyButtons\\mp3s\\") + Utility.renameListName(file.getName(), "mp3")  + ".wav";
                            File target = new File(str);

                            Encoder encoder = new Encoder();
                            encoder.encode(new MultimediaObject(source), target, attrs);
                        }

                        if(!MainFrame.mlist.contains(Utility.renameListName(file.getName(), "mp3"))) {
                            MainFrame.mlist.addElement(Utility.renameListName(file.getName(), "mp3"));
                            MainFrame.tfLastOperation.setText("[ADDED MP3]:: " + file.getName());
                        }

                        listMp3.setModel(MainFrame.mlist);
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // autosave
            if(returnValue == fc.APPROVE_OPTION) {
                autosave();
            }
        }
    }//GEN-LAST:event_btnAddMp3ActionPerformed

    public void setTheme() {
        if(theme.equals("light")) { // ******************************************************************************** LIGHT THEME
            // ------------------------------------------------------------------------------- PANELS
            this.getContentPane().setBackground(new JFrame().getBackground());
            
            // ------------------------------------------------------------------------------- BUTTONS
            btnAddMp3.setBackground(new JButton().getBackground());
            String btnAddMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\add_mp3_12px.png");
            btnAddMp3.setIcon(new javax.swing.ImageIcon(btnAddMp3Icon));
            
            btnDeleteMp3.setBackground(new JButton().getBackground());
            String btnDeleteMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\delete_mp3_ui_12px.png");
            btnDeleteMp3.setIcon(new javax.swing.ImageIcon(btnDeleteMp3Icon));
            
            btnBackMp3.setBackground(new JButton().getBackground());
            String btnBackMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\back_mp3_ui_16px.png");
            btnBackMp3.setIcon(new javax.swing.ImageIcon(btnBackMp3Icon));
            
            btnPlayPauseMp3.setBackground(new JButton().getBackground());
            String btnPlayMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_ui_18px.png");
            btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnPlayMp3Icon));
            
            btnNextMp3.setBackground(new JButton().getBackground());
            String btnNextMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\next_mp3_ui_16px.png");
            btnNextMp3.setIcon(new javax.swing.ImageIcon(btnNextMp3Icon));
            
            // ------------------------------------------------------------------------------- LABELS
            lblSongMp3.setBackground(new JLabel().getBackground());
            lblSongMp3.setForeground(new JLabel().getForeground());
            lblSongMp3.setOpaque(true);
            
            lblLastFrame.setBackground(new JLabel().getBackground());
            lblLastFrame.setForeground(new JLabel().getForeground());
            lblLastFrame.setOpaque(true);
            
            lblDuration.setBackground(new JLabel().getBackground());
            lblDuration.setForeground(new JLabel().getForeground());
            lblDuration.setOpaque(true);
            
            // ------------------------------------------------------------------------------- SLIDERS
            volMp3.setBackground(new JSlider().getBackground());
            sliderSongTime.setBackground(new JSlider().getBackground());
            
            // ------------------------------------------------------------------------------- TEXT FIELDS
            tfSearch.setBackground(new JTextField().getBackground());
            tfSearch.setForeground(new JTextField().getForeground());
            
            listMp3.setBackground(new JList().getBackground());
            listMp3.setForeground(new JList().getForeground());
            MainFrame.listBGM.setBorder(BorderFactory.createTitledBorder(null, 
                    "", 
                    TitledBorder.LEFT, 
                    TitledBorder.TOP, 
                    new Font("segoe", Font.BOLD,12), 
                    new JList().getForeground()));
        }
        else if(theme.equals("dark")) { // ******************************************************************************** DARK THEME
            // ------------------------------------------------------------------------------- PANELS
            this.getContentPane().setBackground(Color.DARK_GRAY);
            
            // ------------------------------------------------------------------------------- BUTTONS
            btnAddMp3.setBackground(Color.GRAY);
            String btnAddMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_add_mp3_12px.png");
            btnAddMp3.setIcon(new javax.swing.ImageIcon(btnAddMp3Icon));
            
            btnDeleteMp3.setBackground(Color.GRAY);
            String btnDeleteMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_delete_mp3_ui_12px.png");
            btnDeleteMp3.setIcon(new javax.swing.ImageIcon(btnDeleteMp3Icon));
            
            btnBackMp3.setBackground(Color.GRAY);
            String btnBackMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_back_mp3_ui_16px.png");
            btnBackMp3.setIcon(new javax.swing.ImageIcon(btnBackMp3Icon));
            
            btnPlayPauseMp3.setBackground(Color.GRAY);
            String btnPlayMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_mp3_ui_18px.png");
            btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnPlayMp3Icon));
            
            btnNextMp3.setBackground(Color.GRAY);
            String btnNextMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_next_mp3_ui_16px.png");
            btnNextMp3.setIcon(new javax.swing.ImageIcon(btnNextMp3Icon));
            
            lblSongMp3.setBackground(Color.DARK_GRAY);
            lblSongMp3.setForeground(Color.LIGHT_GRAY);
            lblSongMp3.setOpaque(true);
            
            lblLastFrame.setBackground(Color.DARK_GRAY);
            lblLastFrame.setForeground(Color.LIGHT_GRAY);
            lblLastFrame.setOpaque(true);
            
            lblDuration.setBackground(Color.DARK_GRAY);
            lblDuration.setForeground(Color.LIGHT_GRAY);
            lblDuration.setOpaque(true);
            
            // ------------------------------------------------------------------------------- SLIDERS
            volMp3.setBackground(Color.DARK_GRAY);
            sliderSongTime.setBackground(Color.DARK_GRAY);
            
            // ------------------------------------------------------------------------------- TEXT FIELDS
            tfSearch.setBackground(Color.DARK_GRAY);
            tfSearch.setForeground(Color.LIGHT_GRAY);
            
            // ------------------------------------------------------------------------------- LIST
            listMp3.setBackground(Color.DARK_GRAY);
            listMp3.setForeground(Color.LIGHT_GRAY);
            listMp3.setBorder(BorderFactory.createTitledBorder(null, 
                    "", 
                    TitledBorder.LEFT, 
                    TitledBorder.TOP, 
                    new Font("segoe", Font.BOLD,12), 
                    Color.LIGHT_GRAY));
        }
    }
    
//    public static void setupPanel(String str) {
//        if(str.equals("light")) {
//            thisFrame.getContentPane().setBackground(new JFrame().getBackground());
//        }
//        else if(str.equals("dark")) {
//            this.getContentPane().setBackground(Color.DARK_GRAY);
//        }
//    }
    
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
        
        // Mp3s
        int listMp3Size = listMp3.getModel().getSize();
        MainFrame.strMp3List = "";
        
        for(int ctr = 0; ctr < listMp3Size; ctr++){
            if(ctr == 0) {
                MainFrame.strMp3List = listMp3.getModel().getElementAt(ctr);
            }
            else if(ctr > 0 && ctr <= (listMp3Size - 1)) {
                MainFrame.strMp3List = MainFrame.strMp3List + ":" + listMp3.getModel().getElementAt(ctr);
            }
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
            java.util.logging.Logger.getLogger(Mp3Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mp3Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mp3Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mp3Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Mp3Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAddMp3;
    public static javax.swing.JButton btnBackMp3;
    public static javax.swing.JButton btnDeleteMp3;
    public static javax.swing.JButton btnNextMp3;
    public static javax.swing.JButton btnPlayPauseMp3;
    private javax.swing.JLabel jLabel2;
    public static javax.swing.JLabel jLabel5;
    public static javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lblDuration;
    public static javax.swing.JLabel lblLastFrame;
    public static javax.swing.JLabel lblSongMp3;
    public static javax.swing.JList<String> listMp3;
    public static javax.swing.JSlider sliderSongTime;
    public static javax.swing.JTextField tfSearch;
    public static javax.swing.JSlider volMp3;
    // End of variables declaration//GEN-END:variables
    
//    static class CustomButtonUI extends JButton {
//        public CustomButtonUI() {
//            if(theme.equals("light")) {
//                setColor(new JButton().getBackground());
//                setForeground(Color.DARK_GRAY);
//                colorOver = new Color(128, 128, 128);
//                colorClick = new Color(255, 255, 255); // white
//                borderColor = new Color(192, 192, 192);
//            }
//            else if(theme.equals("dark")) {
//                setColor(Color.DARK_GRAY);
//                setForeground(Color.LIGHT_GRAY);
//                colorOver = new Color(128, 128, 128);
//                colorClick = new Color(255, 255, 255); // white
//                borderColor = new Color(192, 192, 192);
//            }
//            
//            setContentAreaFilled(false);
//            
//            // Mouse listeners
//            addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseEntered(MouseEvent me) {
//                    if(isEnabled()) {
//                        setBackground(colorOver);
//                        over = true;
//                    }
//                }
//                
//                @Override
//                public void mouseExited(MouseEvent me) {
//                    if(isEnabled()) {
//                        setBackground(color);
//                        over = false;
//                    }
//                }
//                
//                @Override
//                public void mousePressed(MouseEvent me) {
//                    if(isEnabled()) {
//                        setBackground(colorClick);
//                    }
//                }
//                
//                @Override
//                public void mouseReleased(MouseEvent me) {
//                    if(isEnabled()) {
//                        if(over) {
//                            setBackground(colorOver);
//                        }
//                        else {
//                            setBackground(color);
//                        }
//                    }
//                }
//            });
//        }
//        
//        public boolean isOver() {
//            return over;
//        }
//
//        public void setOver(boolean over) {
//            this.over = over;
//        }
//
//        public Color getColor() {
//            return color;
//        }
//
//        public void setColor(Color color) {
//            this.color = color;
//            setBackground(color);
//        }
//
//        public Color getColorOver() {
//            return colorOver;
//        }
//
//        public void setColorOver(Color colorOver) {
//            this.colorOver = colorOver;
//        }
//
//        public Color getColorClick() {
//            return colorClick;
//        }
//
//        public void setColorClick(Color colorClick) {
//            this.colorClick = colorClick;
//        }
//
//        public Color getBorderColor() {
//            return borderColor;
//        }
//
//        public void setBorderColor(Color borderColor) {
//            this.borderColor = borderColor;
//        }
//
//        public int getRadius() {
//            return radius;
//        }
//
//        public void setRadius(int radius) {
//            this.radius = radius;
//        }
//        
//        private boolean over;
//        private Color color;
//        private Color colorOver;
//        private Color colorClick;
//        private Color borderColor;
//        private int radius = 500;
//
//        @Override
//        protected void paintComponent(Graphics grphcs) {
//            Graphics2D g2 = (Graphics2D) grphcs;
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            // Paint Border
//            g2.setColor(borderColor);
//            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
//            g2.setColor(getBackground());
//            // Border set 2 Pix
//            g2.fillRoundRect(2, 2, getWidth() - 4,  getHeight() - 4, radius, radius);
//            
//            super.paintComponent(grphcs);
//        }
//    }
}
