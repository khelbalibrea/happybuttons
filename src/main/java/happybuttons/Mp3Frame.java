/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package happybuttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.sound.sampled.LineEvent;
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
    
    public Mp3Frame() {
        initComponents();
        super.setTitle("Music Player");
                
        // set frame icon
        ImageIcon imgIcon = new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png"));
        setIconImage(imgIcon.getImage());
        
        setTheme();
        load();
        sortJList(MainFrame.mlist);
        
        // adding listener when window is disposing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainFrame.mp3FrameOpened = 0;
            }
        });
        
        MainFrame.listenMp3 = (LineEvent event) -> {
            if(event.getType() == LineEvent.Type.STOP) {
                MainFrame.mp3Playing = 0;
                MainFrame.mp3Pause = 0;
                MainFrame.mp3LastFrame = 0;

                String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_ui_18px.png");
                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                
                if(MainFrame.mp3Repeat == 2) {
                    MainFrame.clipMp3.setFramePosition(0);
                    MainFrame.clipMp3.start();

                    MainFrame.mp3Playing = 1;
                    MainFrame.currentMp3Playing = MainFrame.selectedMp3Item;

                    String btnIcon2 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_mp3_16px.png");
                    btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon2));
                }
                else {
                    if(MainFrame.mp3Repeat == 0) { // no repeat
                        MainFrame.mp3Queue = Utility.removeIndexInStrArr(MainFrame.mp3Queue, 0);
                        
                        if(MainFrame.mp3Queue.length != 0) {
                            MainFrame.selectedMp3Item = MainFrame.mp3Queue[0];
                            
                            MainFrame.tfMp3.setText(Utility.shortenText(MainFrame.selectedMp3Item, 18));
                            MainFrame.tfMp3.setToolTipText(MainFrame.selectedMp3Item);
                            lblSongMp3.setText(Utility.shortenText(MainFrame.selectedMp3Item, 18));
                            lblSongMp3.setToolTipText(MainFrame.selectedMp3Item);
                            
                            MainFrame.playPauseMp3(2); // 2 means play next queue
                        }
                        else {
                            MainFrame.clipMp3.removeLineListener(MainFrame.listenMp3);
                            MainFrame.clipMp3.stop();
                            MainFrame.mp3Queue = MainFrame.mp3MainQueue;
                            MainFrame.selectedMp3Item = MainFrame.mp3Queue[0];
                        }
                    }
                    else if(MainFrame.mp3Repeat == 1){ // repeat list
                        MainFrame.mp3Queue = Utility.removeIndexInStrArr(MainFrame.mp3Queue, 0);
                        
                        if(MainFrame.mp3Queue.length != 0) {
                            MainFrame.selectedMp3Item = MainFrame.mp3Queue[0];
                            
                            MainFrame.tfMp3.setText(Utility.shortenText(MainFrame.selectedMp3Item, 18));
                            MainFrame.tfMp3.setToolTipText(MainFrame.selectedMp3Item);
                            lblSongMp3.setText(Utility.shortenText(MainFrame.selectedMp3Item, 18));
                            lblSongMp3.setToolTipText(MainFrame.selectedMp3Item);
                            
                            MainFrame.playPauseMp3(2); // 2 means play next queue
                        }
                        else {
                            MainFrame.clipMp3.removeLineListener(MainFrame.listenMp3);
                            MainFrame.clipMp3.stop();
                            MainFrame.mp3Queue = MainFrame.mp3MainQueue;
//                            Utility.testPrintStrArray(MainFrame.mp3Queue);
                            MainFrame.selectedMp3Item = MainFrame.mp3Queue[0];
                            
                            MainFrame.tfMp3.setText(Utility.shortenText(MainFrame.selectedMp3Item, 18));
                            MainFrame.tfMp3.setToolTipText(MainFrame.selectedMp3Item);
                            lblSongMp3.setText(Utility.shortenText(MainFrame.selectedMp3Item, 18));
                            lblSongMp3.setToolTipText(MainFrame.selectedMp3Item);
                            
                            MainFrame.playPauseMp3(2); // 2 means play next queue
                        }
                    }
                }
            }
        };
        
        listMp3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) { System.out.println("Double click");
                    int index = listMp3.locationToIndex(e.getPoint());
                    if(index != -1) {
                        String selectedItem = (String)MainFrame.mlist.getElementAt(index);
                        lblSongMp3.setText(Utility.shortenText(selectedItem, 18));
                        lblSongMp3.setToolTipText(selectedItem);
                        MainFrame.tfMp3.setText(selectedItem);
                        MainFrame.mp3Playing = 0;
                        sortQueue();
                        MainFrame.selectedMp3Item = selectedItem;
                        
                        MainFrame.playPauseMp3(1); // 1 means list click
                        
                        if(HappyButtons.uiTheme.equals("light")) {
                            String btnIcon3 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_mp3_16px.png");
                            btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon3));
                        }
                        else if(HappyButtons.uiTheme.equals("dark")) {
                            String btnIcon3 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_pause_mp3_16px.png");
                            btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon3));
                        }

                        if(HappyButtons.uiTheme.equals("light")) {
                            String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_12px.png");
                            MainFrame.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                        }
                        else if(HappyButtons.uiTheme.equals("dark"))  {
                            String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_pause_mp3_12px.png");
                            MainFrame.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                        }
                    }
                }
            }
        });
    }
    
    public void load() {
        // Load mp3 Jlist
        if(MainFrame.loadedIndexProfile != -1) {
            String[] arrMp3 = Utility.splitParts(HappyButtons.profileDB[MainFrame.loadedIndexProfile].getStrMp3List());
            String goneMp3s = "";
            String mp3Gone = "";
            int mp3Lost = 0;
            int numbering = 1;

            for(String music : arrMp3) {
                if(!music.equals("")) {
                    File destCheck = new File(HappyButtons.documentsPath + "\\HappyButtons\\mp3s\\" + music + ".wav");
                    if(!destCheck.exists()) {
                        int removedIndex = Utility.findIndexInStrArr(arrMp3, music);
                        arrMp3 = Utility.removeIndexInStrArr(arrMp3, removedIndex);
                        mp3Lost++;
                        if(goneMp3s.equals("")) {
                            goneMp3s = "(" + numbering + ") " + music + ".wav\n";
                            numbering++;
                            mp3Gone = music;
                        }
                        else {
                            goneMp3s = goneMp3s + "(" + numbering + ") " + music + ".wav\n";
                            numbering++;
                            mp3Gone = mp3Gone + ":" + music;
                        }
                    }
                }
            }

            (MainFrame.mlist).removeAllElements();
            for(String music : arrMp3) {
                (MainFrame.mlist).addElement(music);
            }
            
            listMp3.setModel(MainFrame.mlist);
        }
        
        volMp3.setValue(MainFrame.mp3VolumeValue);
        
        // Shuffle button
        if(MainFrame.mp3Shuffle == 0) {
            setElementIcon(0, "off");
        }
        else {
            setElementIcon(0, "on");
        }
        
        // Repeat button
        if(MainFrame.mp3Repeat == 0) {
            setElementIcon(1, "off");
        }
        else if(MainFrame.mp3Repeat == 1) {
            setElementIcon(1, "list");
        }
        else if(MainFrame.mp3Repeat == 2){
            setElementIcon(1, "loop");
        }
        
        if(!MainFrame.selectedMp3Item.equals("")) {
            lblSongMp3.setText(Utility.shortenText(MainFrame.selectedMp3Item, 18));
            listMp3.setSelectedIndex(0);

            if(theme.equals("light")) {
                btnPlayPauseMp3.setBackground(new JButton().getBackground());
                String strIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_mp3_16px.png");
                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(strIcon));
            }
            else if(theme.equals("dark")) {
                btnPlayPauseMp3.setBackground(Color.GRAY);
                String strIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_pause_mp3_16px.png");
                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(strIcon));
            }
        }

        // setup button elements
        if(MainFrame.iconPlayMp3 == 1) {
            if(theme.equals("light")) {
                btnPlayPauseMp3.setBackground(new JButton().getBackground());
                String strIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_ui_18px.png");
                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(strIcon));
            }
            else if(theme.equals("dark")) {
                btnPlayPauseMp3.setBackground(Color.GRAY);
                String strIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_mp3_ui_18px.png");
                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(strIcon));
            }
        }
        else { // iconPlayMp3 == 0
            if(theme.equals("light")) {
                btnPlayPauseMp3.setBackground(new JButton().getBackground());
                String strIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_mp3_16px.png");
                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(strIcon));
            }
            else if(theme.equals("dark")) {
                btnPlayPauseMp3.setBackground(Color.GRAY);
                String strIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_pause_mp3_16px.png");
                btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(strIcon));
            }
        }
        
        if(!MainFrame.strMp3List.equals("")) {
            String[] arr = Utility.strToArr(MainFrame.strMp3List);
            
            for(int i = 0; i < arr.length; i++) {
                MainFrame.mp3MainQueue = Utility.addElementInStrArr(MainFrame.mp3MainQueue, arr[i]);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSongMp3 = new javax.swing.JLabel();
        btnBackMp3 = new javax.swing.JButton();
        btnNextMp3 = new javax.swing.JButton();
        btnPlayPauseMp3 = new javax.swing.JButton();
        volMp3 = new javax.swing.JSlider();
        lblAudio = new javax.swing.JLabel();
        lblLastFrame = new javax.swing.JLabel();
        tfSearch = new javax.swing.JTextField();
        btnDeleteMp3 = new javax.swing.JButton();
        btnAddMp3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listMp3 = new javax.swing.JList<>();
        lblRepeat = new javax.swing.JLabel();
        lblShuffle = new javax.swing.JLabel();
        sliderSongTime = new javax.swing.JSlider();
        lblDuration = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        lblSongMp3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

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

        volMp3.setValue(100);
        volMp3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                volMp3StateChanged(evt);
            }
        });

        lblAudio.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        lblAudio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAudio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAudio.setMaximumSize(new java.awt.Dimension(35, 35));
        lblAudio.setMinimumSize(new java.awt.Dimension(35, 35));
        lblAudio.setPreferredSize(new java.awt.Dimension(35, 35));
        lblAudio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAudioMouseClicked(evt);
            }
        });

        lblLastFrame.setText("0:00:00");

        tfSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tfSearch.setText("Search");
        tfSearch.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btnDeleteMp3.setMaximumSize(new java.awt.Dimension(22, 22));
        btnDeleteMp3.setMinimumSize(new java.awt.Dimension(22, 22));
        btnDeleteMp3.setPreferredSize(new java.awt.Dimension(22, 22));
        btnDeleteMp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteMp3ActionPerformed(evt);
            }
        });

        btnAddMp3.setMaximumSize(new java.awt.Dimension(22, 22));
        btnAddMp3.setMinimumSize(new java.awt.Dimension(22, 22));
        btnAddMp3.setPreferredSize(new java.awt.Dimension(22, 22));
        btnAddMp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMp3ActionPerformed(evt);
            }
        });

        listMp3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        listMp3.setVisibleRowCount(100);
        jScrollPane1.setViewportView(listMp3);

        lblRepeat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRepeat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblRepeat.setMaximumSize(new java.awt.Dimension(35, 35));
        lblRepeat.setMinimumSize(new java.awt.Dimension(35, 35));
        lblRepeat.setPreferredSize(new java.awt.Dimension(35, 35));
        lblRepeat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRepeatMouseClicked(evt);
            }
        });

        lblShuffle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblShuffle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblShuffle.setMaximumSize(new java.awt.Dimension(35, 35));
        lblShuffle.setMinimumSize(new java.awt.Dimension(35, 35));
        lblShuffle.setPreferredSize(new java.awt.Dimension(35, 35));
        lblShuffle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblShuffleMouseClicked(evt);
            }
        });

        sliderSongTime.setEnabled(false);

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
                        .addComponent(lblAudio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(lblShuffle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRepeat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(lblRepeat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblShuffle, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLastFrame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sliderSongTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDuration, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(volMp3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAudio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnPlayPauseMp3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNextMp3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBackMp3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnBackMp3, btnNextMp3, btnPlayPauseMp3});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddMp3, btnDeleteMp3});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblDuration, lblLastFrame, sliderSongTime});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackMp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackMp3ActionPerformed
//        Notification panel = new Notification(HappyButtons.mf, 
//            Notification.Type.INFO, 
//            MainFrame.location, 
//            "Under development",
//            "Previous button still on work"
//        );
//        
//        panel.showNotification();
        prevQueue();
    }//GEN-LAST:event_btnBackMp3ActionPerformed

    public void prevQueue() {
//        System.out.println(MainFrame.prevSong);
        if(MainFrame.prevSong == 0) {
            if(MainFrame.clipMp3.isRunning()) {
                MainFrame.clipMp3.removeLineListener(MainFrame.listenMp3);
                MainFrame.clipMp3.stop();

                MainFrame.clipMp3.setFramePosition(0);
                MainFrame.playPauseMp3(2);

                MainFrame.iconPlayMp3 = 0;
                setElementIcon(2, "pause");
            }
            else {
                MainFrame.clipMp3.setFramePosition(0);
                MainFrame.iconPlayMp3 = 1;
                setElementIcon(2, "play");
            }
        }
        else {
            int index = Utility.getIndexOfStrArrElement(MainFrame.mp3MainQueue, MainFrame.selectedMp3Item);
            System.out.println("Index: " + index);
            
            if(index != 0) {
                String[] newarr = new String[MainFrame.mp3Queue.length + 1];
                
                for(int i = 0; i < MainFrame.mp3Queue.length + 1; i++) {
                    if(i == 0) {
                        newarr[i] = MainFrame.mp3MainQueue[index - 1];
                    }
                    else {
                        newarr[i] = MainFrame.mp3Queue[i - 1];
                    }
                }
                MainFrame.mp3Queue = newarr;
//                System.out.println("in - 1: " + MainFrame.mp3Queue[index - 1]);
                MainFrame.selectedMp3Item = MainFrame.mp3Queue[index - 1];
//                System.out.println("Main Q:");
//                Utility.testPrintStrArray(MainFrame.mp3MainQueue);
//                System.out.println("\nM Q:");
//                Utility.testPrintStrArray(MainFrame.mp3Queue);
                MainFrame.playPauseMp3(2);
                MainFrame.mp3Queue = Utility.removeIndexInStrArr(MainFrame.mp3Queue, 0);
//                System.out.println("After playing");
//                Utility.testPrintStrArray(MainFrame.mp3Queue);
            }
        }
    }
    
    private void btnNextMp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextMp3ActionPerformed
        nextQueue();
    }//GEN-LAST:event_btnNextMp3ActionPerformed

    public void nextQueue() {
        if(MainFrame.clipMp3.isRunning()) {
            MainFrame.clipMp3.removeLineListener(MainFrame.listenMp3);
            MainFrame.clipMp3.stop();
            
            if(MainFrame.mp3Queue.length == 0) {
                if(MainFrame.mp3Repeat == 0) {
                    MainFrame.tfLastOperation.setText(Utility.shortenText("MP is not in repeat mode. Songs in list are done playing", 50));
                    MainFrame.tfLastOperation.setToolTipText("MP is not in repeat mode. Songs in list are done playing");
                    MainFrame.iconPlayMp3 = 1;
                    setElementIcon(2, "play");
                }
                else if(MainFrame.mp3Repeat == 1) {
                    MainFrame.mp3Queue = MainFrame.mp3MainQueue;
                    MainFrame.selectedMp3Item = MainFrame.mp3Queue[0];
                    MainFrame.mp3Queue = Utility.removeIndexInStrArr(MainFrame.mp3Queue, 0);
                    
                    MainFrame.btnNext = 1;
                    MainFrame.iconPlayMp3 = 0;
                    MainFrame.playPauseMp3(2);
                    
                    setElementIcon(2, "pause");
                }
                else if(MainFrame.mp3Repeat == 2) {
                    MainFrame.tfLastOperation.setText(Utility.shortenText("MP is not in repeat mode. Songs in list are done playing", 50));
                    MainFrame.tfLastOperation.setToolTipText("MP is not in repeat mode. Songs in list are done playing");
                    MainFrame.iconPlayMp3 = 1;
                    setElementIcon(2, "play");
                }
            }
            else {
                if(MainFrame.mp3Queue[0].equals(MainFrame.selectedMp3Item)) {
                    MainFrame.mp3Queue = Utility.removeIndexInStrArr(MainFrame.mp3Queue, 0);
                    if(MainFrame.mp3Queue.length == 0) {
                        if(MainFrame.mp3Repeat == 0) {
                            MainFrame.tfLastOperation.setText(Utility.shortenText("MP is not in repeat mode. Songs in list are done playing", 50));
                            MainFrame.tfLastOperation.setToolTipText("MP is not in repeat mode. Songs in list are done playing");
                            MainFrame.iconPlayMp3 = 1;
                            setElementIcon(2, "play");
                        }
                        else if(MainFrame.mp3Repeat == 1) {
                            MainFrame.mp3Queue = MainFrame.mp3MainQueue;
                            MainFrame.selectedMp3Item = MainFrame.mp3Queue[0];
                            MainFrame.mp3Queue = Utility.removeIndexInStrArr(MainFrame.mp3Queue, 0);

                            MainFrame.btnNext = 1;
                            MainFrame.iconPlayMp3 = 0;
                            MainFrame.playPauseMp3(2);

                            setElementIcon(2, "pause");
                        }
                        else if(MainFrame.mp3Repeat == 2) {
                            MainFrame.tfLastOperation.setText(Utility.shortenText("MP is not in repeat mode. Songs in list are done playing", 50));
                            MainFrame.tfLastOperation.setToolTipText("MP is not in repeat mode. Songs in list are done playing");
                            MainFrame.iconPlayMp3 = 1;
                            setElementIcon(2, "play");
                        }
                    }
                    else {
                        MainFrame.selectedMp3Item = MainFrame.mp3Queue[0];
                    }
                }
                else {
                    MainFrame.selectedMp3Item = MainFrame.mp3Queue[0];
                    MainFrame.mp3Queue = Utility.removeIndexInStrArr(MainFrame.mp3Queue, 0);
                }
                
                MainFrame.btnNext = 1;
                MainFrame.iconPlayMp3 = 0;
                MainFrame.playPauseMp3(2);
                setElementIcon(2, "pause");
            }
        }
        else { // clip not running
            if(MainFrame.mp3Queue.length == 0) {
                if(MainFrame.mp3Repeat == 0) {
                    MainFrame.tfLastOperation.setText(Utility.shortenText("MP is not in repeat mode. Songs in list are done playing", 50));
                    MainFrame.tfLastOperation.setToolTipText("MP is not in repeat mode. Songs in list are done playing");
                    MainFrame.iconPlayMp3 = 1;
                }
                else if(MainFrame.mp3Repeat == 1) {
                    MainFrame.mp3Queue = MainFrame.mp3MainQueue;
                    MainFrame.selectedMp3Item = MainFrame.mp3Queue[0];
                    MainFrame.mp3Queue = Utility.removeIndexInStrArr(MainFrame.mp3Queue, 0);
                    
                    MainFrame.btnNext = 1;
                    MainFrame.iconPlayMp3 = 0;
                    MainFrame.playPauseMp3(2);
                }
                else if(MainFrame.mp3Repeat == 2) {
                    MainFrame.tfLastOperation.setText(Utility.shortenText("MP is not in repeat mode. Songs in list are done playing", 50));
                    MainFrame.tfLastOperation.setToolTipText("MP is not in repeat mode. Songs in list are done playing");
                    MainFrame.iconPlayMp3 = 1;
                }
            }
            else {
                MainFrame.mp3Queue = Utility.removeIndexInStrArr(MainFrame.mp3Queue, 0);
                MainFrame.selectedMp3Item = MainFrame.mp3Queue[0];
                MainFrame.btnNext = 1;
                MainFrame.iconPlayMp3 = 0;
                MainFrame.playPauseMp3(2);
            }
        }
    }
    
    private void btnPlayPauseMp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayPauseMp3ActionPerformed
        if(listMp3.getSelectedIndex() != -1) {
            MainFrame.selectedMp3Item = listMp3.getSelectedValue();
        }
        
        if(!MainFrame.selectedMp3Item.equals("")) {
            MainFrame.tfMp3.setText(MainFrame.selectedMp3Item);
            MainFrame.playPauseMp3(0); // 0 means button click
        }
        else {
            MainFrame.tfLastOperation.setText("[MP]:: NOTHING TO PLAY");
        }
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
            fc.setPreferredSize(new Dimension(1000, 600));
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
                        
                        if(MainFrame.strMp3List.equals("")) {
                            MainFrame.mlist.removeAllElements();
                            listMp3.removeAll();
                            MainFrame.strMp3List = Utility.renameListName(file.getName(), "wav");
                        }
                        else {
                            MainFrame.strMp3List = MainFrame.strMp3List + ":" + Utility.renameListName(file.getName(), "wav");
                        }

                        if(!MainFrame.mlist.contains(Utility.renameListName(file.getName(), "wav"))) {
                            MainFrame.mlist.addElement(Utility.renameListName(file.getName(), "wav"));
                            MainFrame.tfLastOperation.setText("[ADDED MP3]:: " + file.getName());
                        }
                        
                        if(!Utility.doesStrArrHasElement(MainFrame.mp3MainQueue, Utility.renameListName(file.getName(), "wav"))) {
                            MainFrame.mp3MainQueue = Utility.addElementInStrArr(MainFrame.mp3MainQueue, Utility.renameListName(file.getName(), "wav"));
                        }
                        
                        if(!Utility.doesStrArrHasElement(MainFrame.mp3Queue, Utility.renameListName(file.getName(), "wav"))) {
                            MainFrame.mp3Queue = Utility.addElementInStrArr(MainFrame.mp3Queue, Utility.renameListName(file.getName(), "wav"));
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

                            EncodingAttributes attrs = new EncodingAttributes();
                            attrs.setOutputFormat("wav");
                            attrs.setAudioAttributes(audio);

                            File source = new File(file.toString());
                            String str = HappyButtons.documentsPath + Utility.strDoubleSlash("\\HappyButtons\\mp3s\\") + Utility.renameListName(file.getName(), "mp3")  + ".wav";
                            File target = new File(str);

                            Encoder encoder = new Encoder();
                            encoder.encode(new MultimediaObject(source), target, attrs);
                        }
                        
                        if(MainFrame.strMp3List.equals("")) {
                            MainFrame.mlist.removeAllElements();
                            listMp3.removeAll();
                            MainFrame.strMp3List = Utility.renameListName(file.getName(), "mp3");
                        }
                        else {
                            MainFrame.strMp3List = MainFrame.strMp3List + ":" + Utility.renameListName(file.getName(), "mp3");
                        }

                        if(!MainFrame.mlist.contains(Utility.renameListName(file.getName(), "mp3"))) {
                            MainFrame.mlist.addElement(Utility.renameListName(file.getName(), "mp3"));
                        }
                        
                        if(!Utility.doesStrArrHasElement(MainFrame.mp3MainQueue, Utility.renameListName(file.getName(), "mp3"))) {
                            MainFrame.mp3MainQueue = Utility.addElementInStrArr(MainFrame.mp3MainQueue, Utility.renameListName(file.getName(), "mp3"));
                        }
                        
                        if(!Utility.doesStrArrHasElement(MainFrame.mp3Queue, Utility.renameListName(file.getName(), "mp3"))) {
                            MainFrame.mp3Queue = Utility.addElementInStrArr(MainFrame.mp3Queue, Utility.renameListName(file.getName(), "mp3"));
                        }

                        listMp3.setModel(MainFrame.mlist);
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                MainFrame.mp3SortedQueue = MainFrame.mp3MainQueue;
//                System.out.println("Sorted queue:\n");
//                Utility.testPrintStrArray(MainFrame.mp3SortedQueue);
                
                if(MainFrame.mp3Shuffle == 1) {
                    shuffle();
                }
                else {
                    sortQueue();
                }
            }
            
            // autosave
            if(returnValue == fc.APPROVE_OPTION) {
                autosave();
            }
            
            
//            LookAndFeel originalLookAndFeel = UIManager.getLookAndFeel();
//            try {
//                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//                
//                
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//            finally {
//                try {
//                    UIManager.setLookAndFeel(originalLookAndFeel);
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }//GEN-LAST:event_btnAddMp3ActionPerformed

    private void volMp3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_volMp3StateChanged
//        float f = (float)volMp3.getValue();
//        
//        if(f == 0) {
//            MainFrame.mp3Audio = 0;
//            
//            // set icon
//            if(theme.equals("light")) {
//                String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\no_audio_16px.png");
//                lblAudio.setIcon(new javax.swing.ImageIcon(icon));
//            }
//            else if(theme.equals("dark")) {
//                String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_no_audio_16px.png");
//                lblAudio.setIcon(new javax.swing.ImageIcon(icon));
//            }
//        }
//        else {
//            MainFrame.mp3Audio = 1;
//            
//            // set icon
//            if(theme.equals("light")) {
//                String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\audio_16px.png");
//                lblAudio.setIcon(new javax.swing.ImageIcon(icon));
//            }
//            else if(theme.equals("dark")) {
//                String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_audio_16px.png");
//                lblAudio.setIcon(new javax.swing.ImageIcon(icon));
//            }
//        }
//                
//        try { // calculation same as BGM
//            if(f >= 50) {
//                MainFrame.mp3Vol = 0 - ((100f - f)*(0.18f));
//            }
//
//            if(f < 50 && f >= 25) {
//                MainFrame.mp3Vol = -9 + (50f - f)*(-0.36f);
//            }
//
//            if(f < 25 && f >= 10) {
//                MainFrame.mp3Vol = -18 + (25f - f)*(-0.8f);
//            }
//
//            if(f < 10) {
//                MainFrame.mp3Vol = -30f + (10f - f)*(-5f);
//            }
//
//            MainFrame.fcMp3.setValue(MainFrame.mp3Vol); // float value
//        }
//        catch(Exception ex) {
//
//        }
//
//        lblAudio.setText(Integer.toString((int)volMp3.getValue()));
        MainFrame.mp3VolChange();
    }//GEN-LAST:event_volMp3StateChanged

    private void btnDeleteMp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteMp3ActionPerformed
        List<String> stringList = listMp3.getSelectedValuesList();
        
        if(!stringList.isEmpty()) { // not empty
            for(int i = 0; i < stringList.size(); i++) {
//                list = Utility.addElementInStrArr(list.length, list, stringList.get(i));
//                list = Utility.removeIndexInStrArr(list, i);
                if(stringList.get(i).equals(MainFrame.selectedMp3Item)) {
                    MainFrame.selectedMp3Item = "";

                    MainFrame.clipMp3.removeLineListener(MainFrame.listenMp3);
                    MainFrame.clipMp3.stop();

                    MainFrame.mp3Playing = 0;
                    MainFrame.mp3Pause = 0;
                    MainFrame.clipMp3 = null;
                    
                    setElementIcon(2, "play");

                    lblSongMp3.setText("");
                }
                
                MainFrame.mlist.removeElement(stringList.get(i));
                
                MainFrame.mp3MainQueue = Utility.removeElementInStrArr(MainFrame.mp3MainQueue, stringList.get(i));
                if(Utility.doesStrArrHasElement(MainFrame.mp3Queue, stringList.get(i))) {
                    MainFrame.mp3Queue = Utility.removeElementInStrArr(MainFrame.mp3Queue, stringList.get(i));
                }
            }
            autosave();
        }
        else {
            MainFrame.tfLastOperation.setText(Utility.shortenText("[DELETE Music]:: Nothing selected", 50));
        }
        
//        if(listMp3.getSelectedIndex() != -1) {
//            String selectedItem = listMp3.getSelectedValue();
//        
//            if(!selectedItem.equals("")) {
//                MainFrame.tfLastOperation.setText("[DELETE BGM]:: " + selectedItem);
//                MainFrame.mlist.removeElement(selectedItem);
//                MainFrame.mp3LastFrame = 0;
//
//                if(selectedItem.equals(MainFrame.selectedMp3Item)) {
//                    MainFrame.selectedMp3Item = "";
//
//                    MainFrame.clipMp3.removeLineListener(MainFrame.listenMp3);
//                    MainFrame.clipMp3.stop();
//
//                    MainFrame.mp3Playing = 0;
//                    MainFrame.mp3Pause = 0;
//                    MainFrame.clipMp3 = null;
//                    
//                    setElementIcon(2, "play");
//
//                    lblSongMp3.setText("");
//                }
//                
//                MainFrame.mp3MainQueue = Utility.removeElementInStrArr(MainFrame.mp3MainQueue, selectedItem);
//                if(Utility.doesStrArrHasElement(MainFrame.mp3Queue, selectedItem)) {
//                    MainFrame.mp3Queue = Utility.removeElementInStrArr(MainFrame.mp3Queue, selectedItem);
//                }
//                
//                Utility.testPrintStrArray(MainFrame.mp3MainQueue);
//                System.out.println(" -> " + MainFrame.mp3MainQueue.length);
//                
//                selectedItem = "";
//                
//                autosave();
//            }
//            else {
//                MainFrame.tfLastOperation.setText("[DELETE Music]:: Nothing selected");
//            }
//        }
//        else {
//            MainFrame.tfLastOperation.setText("[DELETE Music]:: Nothing selected");
//        }
    }//GEN-LAST:event_btnDeleteMp3ActionPerformed

    private void lblShuffleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblShuffleMouseClicked
        MainFrame.mp3MainQueue = new String[0];
        
        if(MainFrame.mp3Shuffle == 1) {
            MainFrame.mp3Shuffle = 0;
            setElementIcon(0, "off");
            sortQueue();
        }
        else {
            MainFrame.mp3Shuffle = 1;
            setElementIcon(0, "on");
            shuffle();
            MainFrame.mp3MainQueue = MainFrame.mp3ShuffledQueue;
        }
    }//GEN-LAST:event_lblShuffleMouseClicked
    
    public static void sortQueue() {
        int countStart = 0, length = 0, ctr = 0;
        
        if(MainFrame.mp3SortedQueue.length != 0) {
            length = MainFrame.mp3SortedQueue.length;
        }
        
        if(!MainFrame.selectedMp3Item.equals("")) {
            countStart = Utility.getIndexOfStrArrElement(MainFrame.mp3SortedQueue, MainFrame.selectedMp3Item);
            
            MainFrame.mp3MainQueue = new String[0];
            for(int i = countStart; i < MainFrame.mp3SortedQueue.length; i++) {
                MainFrame.mp3MainQueue = Utility.addElementInStrArr(MainFrame.mp3MainQueue, MainFrame.mp3SortedQueue[i]);
                ctr++;
            }
            
            if(ctr != length) {
                for(int i = 0; i < countStart; i++) {
                    MainFrame.mp3MainQueue = Utility.addElementInStrArr(MainFrame.mp3MainQueue, MainFrame.mp3SortedQueue[i]);
                }
            }
            
            MainFrame.mp3Queue = null;
            MainFrame.mp3Queue = MainFrame.mp3MainQueue;
        }
        else {
            MainFrame.mp3MainQueue = new String[0];
            MainFrame.mp3MainQueue = MainFrame.mp3SortedQueue;
            MainFrame.mp3Queue = null;
            MainFrame.mp3Queue = MainFrame.mp3MainQueue;
        }
//        System.out.println("From sorted:\n" + Utility.arrToStr(MainFrame.mp3MainQueue));
    }
    
    public static void shuffle() {
        MainFrame.mp3ShuffledQueue = new String[]{};
        int min = 1, max = (MainFrame.mp3SortedQueue.length), randomIndex = -1, countStart = 0;
        int[] exclusion = new int[]{};
        Random random = new Random();
        
        if(!MainFrame.selectedMp3Item.equals("")) {
            exclusion = Utility.addElementInIntArr(exclusion, Utility.getIndexOfStrArrElement(MainFrame.mp3SortedQueue, MainFrame.selectedMp3Item));
            MainFrame.mp3ShuffledQueue = Utility.addElementInStrArr(MainFrame.mp3ShuffledQueue, MainFrame.selectedMp3Item);
            countStart = 1;
        }
        
        for(int i = countStart; i < MainFrame.mp3SortedQueue.length; i++) {
            do {
                randomIndex = random.nextInt(max - min + 1) + min;
            }
            while(Utility.doesIntArrHasElement(exclusion, randomIndex - 1));
            
            exclusion = Utility.addElementInIntArr(exclusion, randomIndex - 1);
            MainFrame.mp3ShuffledQueue = Utility.addElementInStrArr(MainFrame.mp3ShuffledQueue, MainFrame.mp3SortedQueue[randomIndex - 1]);
            
//            System.out.println("Loop: " + i + " -> " + randomIndex + " index is " + MainFrame.mp3SortedQueue[randomIndex - 1]);
        }
        exclusion = new int[]{};
        
        MainFrame.mp3MainQueue = MainFrame.mp3ShuffledQueue;
        MainFrame.mp3Queue = MainFrame.mp3MainQueue;
//        System.out.println("Queue: " + Utility.arrToStr(MainFrame.mp3Queue));
    }
    
    private void lblRepeatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRepeatMouseClicked
        switch (MainFrame.mp3Repeat) {
            case 0:
                MainFrame.mp3Repeat = 1; // 1 is repeat list
                setElementIcon(1, "list");
                break;
            case 1:
                MainFrame.mp3Repeat = 2; // 2 is repeat one song or loop the song
                setElementIcon(1, "loop");
                break;
            case 2:
                MainFrame.mp3Repeat = 0; // 0 is do not repeat list
                setElementIcon(1, "off");
                break;
            default:
                break;
        }
        
        if(MainFrame.mp3Shuffle == 0) {
            MainFrame.mp3MainQueue = new String[0];
            sortQueue();
        }
    }//GEN-LAST:event_lblRepeatMouseClicked

    private void lblAudioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAudioMouseClicked
        if(MainFrame.mp3Audio == 1) {
            MainFrame.iconVolMp3 = volMp3.getValue();
        }
        
        if(MainFrame.mp3Audio == 1) {
            MainFrame.mp3Audio = 0;
            
            // set icon
            if(theme.equals("light")) {
                String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\no_audio_16px.png");
                lblAudio.setIcon(new javax.swing.ImageIcon(icon));
            }
            else if(theme.equals("dark")) {
                String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_no_audio_16px.png");
                lblAudio.setIcon(new javax.swing.ImageIcon(icon));
            }
            volMp3.setValue(0);
            lblAudio.setText("0");
        }
        else {
            MainFrame.mp3Audio = 1;
            
            // set icon
            if(theme.equals("light")) {
                String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\audio_16px.png");
                lblAudio.setIcon(new javax.swing.ImageIcon(icon));
            }
            else if(theme.equals("dark")) {
                String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_audio_16px.png");
                lblAudio.setIcon(new javax.swing.ImageIcon(icon));
            }
            volMp3.setValue(MainFrame.iconVolMp3);
            lblAudio.setText(String.valueOf(MainFrame.iconVolMp3));
        }
    }//GEN-LAST:event_lblAudioMouseClicked

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
            
//            lblShuffle.setOpaque(false);
//            String lblShuffleIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\shuffle_16px.png");
//            lblShuffle.setIcon(new javax.swing.ImageIcon(lblShuffleIcon));
//            
//            lblRepeat.setOpaque(false);
//            String lblRepeatIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\repeat_16px.png");
//            lblRepeat.setIcon(new javax.swing.ImageIcon(lblRepeatIcon));
            
            lblAudio.setOpaque(false);
            String lblAudioIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\audio_16px.png");
            lblAudio.setIcon(new javax.swing.ImageIcon(lblAudioIcon));
            lblAudio.setBackground(new JLabel().getBackground());
            lblAudio.setForeground(new JLabel().getForeground());
            lblAudio.setText("100");
            
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
            
            // ------------------------------------------------------------------------------- LABELS
            lblSongMp3.setBackground(Color.DARK_GRAY);
            lblSongMp3.setForeground(Color.LIGHT_GRAY);
            lblSongMp3.setOpaque(true);
            
            lblLastFrame.setBackground(Color.DARK_GRAY);
            lblLastFrame.setForeground(Color.LIGHT_GRAY);
            lblLastFrame.setOpaque(true);
            
            lblDuration.setBackground(Color.DARK_GRAY);
            lblDuration.setForeground(Color.LIGHT_GRAY);
            lblDuration.setOpaque(true);
            
//            lblShuffle.setOpaque(false);
//            String lblShuffleIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_shuffle_16px.png");
//            lblShuffle.setIcon(new javax.swing.ImageIcon(lblShuffleIcon));
//            
//            lblRepeat.setOpaque(false);
//            String lblRepeatIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_repeat_16px.png");
//            lblRepeat.setIcon(new javax.swing.ImageIcon(lblRepeatIcon));
            
            lblAudio.setOpaque(false);
            String lblAudioIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_audio_16px.png");
            lblAudio.setIcon(new javax.swing.ImageIcon(lblAudioIcon));
            lblAudio.setBackground(Color.DARK_GRAY);
            lblAudio.setForeground(Color.LIGHT_GRAY);
            lblAudio.setText("100");
            
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
    
    public void setElementIcon(int type, String status) { // 0-shuffle, 1-repeat
        if(type == 0) { // shuffle
            if(status.equals("off")) {
                if(theme.equals("light")) {
                    String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\shuffle_off_16px.png");
                    lblShuffle.setIcon(new javax.swing.ImageIcon(icon));
                }
                else if(theme.equals("dark")) {
                    String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_shuffle_off_16px.png");
                    lblShuffle.setIcon(new javax.swing.ImageIcon(icon));
                }
                lblShuffle.setToolTipText("Shuffle off");
            }
            else if(status.equals("on")) {
                if(theme.equals("light")) {
                    String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\shuffle_16px.png");
                    lblShuffle.setIcon(new javax.swing.ImageIcon(icon));
                }
                else if(theme.equals("dark")) {
                    String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_shuffle_16px.png");
                    lblShuffle.setIcon(new javax.swing.ImageIcon(icon));
                }
                lblShuffle.setToolTipText("Shuffle on");
            }
        }
        else if(type == 1) { // repeat
            if(status.equals("off")) {
                if(theme.equals("light")) {
                    String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\repeat_off_16px.png");
                    lblRepeat.setIcon(new javax.swing.ImageIcon(icon));
                }
                else if(theme.equals("dark")) {
                    String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_repeat_off_16px.png");
                    lblRepeat.setIcon(new javax.swing.ImageIcon(icon));
                }
                lblRepeat.setToolTipText("Repeat is off");
            }
            else if(status.equals("list")) {
                if(theme.equals("light")) {
                    String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\repeat_16px.png");
                    lblRepeat.setIcon(new javax.swing.ImageIcon(icon));
                }
                else if(theme.equals("dark")) {
                    String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_repeat_16px.png");
                    lblRepeat.setIcon(new javax.swing.ImageIcon(icon));
                }
                lblRepeat.setToolTipText("Repeat list");
            }
            else if(status.equals("loop")) {
                if(theme.equals("light")) {
                    String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\repeat_one_16px.png");
                    lblRepeat.setIcon(new javax.swing.ImageIcon(icon));
                }
                else if(theme.equals("dark")) {
                    String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_repeat_one_16px.png");
                    lblRepeat.setIcon(new javax.swing.ImageIcon(icon));
                }
                lblRepeat.setToolTipText("Loop selected song");
            }
        }
        else if(type == 2) { // Mp3 Frame play / pause button
            if(status.equals("play")) {
                if(HappyButtons.uiTheme.equals("light")) {
                    String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_ui_18px.png");
                    btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                }
                else if(HappyButtons.uiTheme.equals("dark"))  {
                    String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_mp3_ui_18px.png");
                    btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                }
            }
            else if(status.equals("pause")) {
                if(HappyButtons.uiTheme.equals("light")) {
                    String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pause_mp3_16px.png");
                    btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                }
                else if(HappyButtons.uiTheme.equals("dark"))  {
                    String btnIcon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_pause_mp3_16px.png");
                    btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnIcon1));
                }
            }
        }
    }
    
    public static void sortJList(DefaultListModel list) {
        int n = list.getSize();
        String[] data = new String[n]; System.out.println(n);
        
        for(int i = 0; i < n; i++) {
            data[i] = (String) list.getElementAt(i);
        }
        
        Arrays.sort(data);
        
        (MainFrame.mlist).removeAllElements();
        MainFrame.mp3SortedQueue = new String[0];
        
        for(String music : data) {
            (MainFrame.mlist).addElement(music);
            
            // setup queueing in music player (for shuffle function)
            MainFrame.mp3SortedQueue = Utility.addElementInStrArr(MainFrame.mp3SortedQueue, music);
        }
        
        listMp3.setModel(MainFrame.mlist);
        
        listMp3.setSelectedValue(MainFrame.selectedMp3Item, true);
    }
    
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
//        // BGMs
//        int listBGMSize = MainFrame.listBGM.getModel().getSize();
//        MainFrame.strBGM = "";
//        
//        for(int ctr = 0; ctr < listBGMSize; ctr++){
//            if(ctr == 0) {
//                MainFrame.strBGM = MainFrame.listBGM.getModel().getElementAt(ctr);
//            }
//            else if(ctr > 0 && ctr <= (listBGMSize - 1)) {
//                MainFrame.strBGM = MainFrame.strBGM + ":" + MainFrame.listBGM.getModel().getElementAt(ctr);
//            }
//        }
//        
//        // SFXs
//        int listSFXSize = MainFrame.listSFX.getModel().getSize();
//        MainFrame.strSFX = "";
//        
//        for(int ctr = 0; ctr < listSFXSize; ctr++){
//            if(ctr == 0) {
//                MainFrame.strSFX = MainFrame.listSFX.getModel().getElementAt(ctr);
//            }
//            else if(ctr > 0 && ctr <= (listSFXSize - 1)) {
//                MainFrame.strSFX = MainFrame.strSFX + ":" + MainFrame.listSFX.getModel().getElementAt(ctr);
//            }
//        }
//        
//        // Video Loop videos
//        int cboHappyLoopSize = MainFrame.cboVidLoop.getModel().getSize();
//        MainFrame.strVidLoop = "";
//        
//        for(int ctr = 0; ctr < cboHappyLoopSize; ctr++) {
//            if(ctr == 0) {
//                MainFrame.strVidLoop = MainFrame.cboVidLoop.getModel().getElementAt(ctr);
//            }
//            else if(ctr > 0 && ctr <= (cboHappyLoopSize - 1)) {
//                MainFrame.strVidLoop = MainFrame.strVidLoop + ":" + MainFrame.cboVidLoop.getModel().getElementAt(ctr);
//            }
//        }
        
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
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lblAudio;
    public static javax.swing.JLabel lblDuration;
    public static javax.swing.JLabel lblLastFrame;
    public static javax.swing.JLabel lblRepeat;
    public static javax.swing.JLabel lblShuffle;
    public static javax.swing.JLabel lblSongMp3;
    public static javax.swing.JList<String> listMp3;
    public static javax.swing.JSlider sliderSongTime;
    public static javax.swing.JTextField tfSearch;
    public static javax.swing.JSlider volMp3;
    // End of variables declaration//GEN-END:variables
}
