/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package happybuttons;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.ToolTipManager;
import javax.swing.border.EmptyBorder;
import java.util.regex.*;

public class ExplorerVideo extends javax.swing.JDialog {
    DefaultListModel<ImageLabel> model = new DefaultListModel<>();
    static MouseListener videoSelect;
    int ctrVideoNotLoaded = 0;
    String notLoadedVids[] = {};
    public static String selectedItem = "";
    JDialog dialog = this;
    String vidList[] = {};
    Timer just, now;
    
    public ExplorerVideo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
        if(MainFrame.chkVLMode.isSelected()) {
            super.setTitle("Open Video [LOOP mode]");
        }
        else {
            super.setTitle("Open Video [PLAYLIST mode]");
        }
        
        setLayout(new BorderLayout());
        
        videoSelect = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                e.consume();
//                if(SwingUtilities.isRightMouseButton(e)) {
//                    JComponent sourceComponent = (JComponent) e.getSource();
//                    if(sourceComponent instanceof JLabel) {
//                        JLabel sourceButton = (JLabel) sourceComponent;
//                        sourceButton.setText("<html><center>blank</center></html>");
//                    }
//                }
            }
        };
        
        just = new Timer(3000, (ActionEvent e) -> {
            buttonChangeText(0);
        });
        
        now = new Timer(3000, (ActionEvent e) -> {
            buttonChangeText(1);
        });
        
        if(MainFrame.cboVLType == 1) { // video playlist mode
            for(int ctr = 0; ctr < MainFrame.cboModelPlaylist.getSize(); ctr++) {
                vidList = Utility.addElementInStrArr(vidList, MainFrame.cboModelPlaylist.getElementAt(ctr).toString());
            }
        }
        else { // for loop mode
            for(int ctr = 0; ctr < MainFrame.cboModelForLoop.getSize(); ctr++) {
                vidList = Utility.addElementInStrArr(vidList, MainFrame.cboModelForLoop.getElementAt(ctr).toString());
            }
        }
        
        // Load images and add them to the model with labels
        try {
            for(int i = 0; i < vidList.length; i++) {
                File f = new File(HappyButtons.documentsPath + "\\HappyButtons\\data\\thumbnails\\" + vidList[i] + ".png");
                
                if(f.exists()) {
                    BufferedImage img = ImageIO.read(f);
                    String label = Utility.prepareLabelNaming(Utility.shortenText(vidList[i], 20)); // Change this to your desired label
                    model.addElement(new ImageLabel(new ImageIcon(img), label, vidList[i]));
                }
                else {
                    ctrVideoNotLoaded++;
                    notLoadedVids = Utility.addElementInStrArr(notLoadedVids, vidList[i]);
                }
            }
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        
        initComponents();
        
        listVideos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) { // System.out.println("2 click " + selectedItem);
                    if(!MainFrame.tfVideoLoop.getText().equals(selectedItem)) {
                        
                    }
                    if(MainFrame.playlistVideoMode == 1) {
                        MainFrame.selectedPlaylistVideoItem = selectedItem;
                    }
                    else {
                        MainFrame.selectedLoopVideoItem = selectedItem;
                    }

                    MainFrame.tfVideoLoop.setText(selectedItem);
                    MainFrame.tfVideoLoop.setToolTipText(selectedItem);
                    MainFrame.tfVideoLoop.moveCaretPosition(0);

//                    dialog.setVisible(false);

                    if(MainFrame.videoFirstTime == 1) {
                        MainFrame.videoFirstTime = 0;
                        
                        MainFrame.playVid();
                    }
                    
                    if(MainFrame.videoFirstTime == 1) {
                        MainFrame.playVid();
                    }
                    else {
                        MainFrame.btnPlayVLActionPerformed(null);
                    }
                }
            }
        });
        
        // set frame icon
        ImageIcon imgIcon = new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png"));
        setIconImage(imgIcon.getImage());
        
        setupTheme();
    }
                            
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        listVideos = new JList<>(model);
        btnPlayNow = new javax.swing.JButton();
        btnJustSelect = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblWarning = new javax.swing.JLabel(ctrVideoNotLoaded + " video(s) not listed, probably no thumbnail generated with it");
        tfSearch = new PlaceHolderTextfield("Search here");
        tfSearch.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        
        tfSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSearchBSKeyReleased(evt);
            }
        });
        
        btnPlayNow.setText("Play now");
        btnJustSelect.setText("Just select");
        
        listVideos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        listVideos.setVisibleRowCount(-1); // Set to -1 for all rows to be visible
        listVideos.setCellRenderer(new ImageLabelRenderer());
        listVideos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Add the JList to a JScrollPane
        jScrollPane1 = new JScrollPane(listVideos);
        this.add(jScrollPane1, BorderLayout.CENTER);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setModal(true);
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);
        
        lblWarning.setForeground(new java.awt.Color(255, 0, 0));
        Font font = new Font("Segoe UI", Font.PLAIN, 11); // Font name, style, size
        lblWarning.setFont(font);
        
        try {
            BufferedImage cursorImg = javax.imageio.ImageIO.read(new File(HappyButtons.documentsPath + "/HappyButtons/res/sys_question_mark_32px.png")); // Replace with your image path
            Cursor questionMarkCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "Question Mark Cursor");
            lblWarning.setCursor(questionMarkCursor); // Set custom question mark cursor
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        // Set the tooltip display duration
        ToolTipManager.sharedInstance().setDismissDelay(10000);

        if(ctrVideoNotLoaded > 0) {
            String lblText = "";
            for(int ctr = 0; ctr < notLoadedVids.length; ctr++) {
                if(ctr == (notLoadedVids.length - 1)) {
                    lblText = lblText + "(" + (ctr + 1) + ") " + notLoadedVids[ctr] + " ";
                }
                else {
                    lblText = lblText + "(" + (ctr + 1) + ") " + notLoadedVids[ctr];
                }
            }
            lblWarning.setToolTipText("Click to view");
            
            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                    .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addComponent(lblWarning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(18, 18, 18)
                    .addComponent(btnPlayNow)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnJustSelect)
                )
                
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnJustSelect)
                        .addComponent(btnPlayNow)
                        .addComponent(lblWarning))
                )
            );
            
            lblWarning.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    VideoNotFoundListFrame frame = new VideoNotFoundListFrame(dialog, true, notLoadedVids);
                    frame.setVisible(true);
                }
            });
            
        }
        else {
//            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
//            jPanel1.setLayout(jPanel1Layout);
//            jPanel1Layout.setHorizontalGroup(
//                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
//                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.CENTER)
//                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
//                    .addContainerGap(218, Short.MAX_VALUE)
//                    .addComponent(btnJustSelect)
//                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                    .addComponent(btnPlayNow)
//                    .addContainerGap())
//            );
//            jPanel1Layout.setVerticalGroup(
//                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
//                .addGroup(jPanel1Layout.createSequentialGroup()
//                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
//                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                        .addComponent(btnJustSelect)
//                        .addComponent(btnPlayNow))
//                    .addContainerGap())
//            );
            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                    .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addComponent(btnPlayNow)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnJustSelect)
                )
                
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnJustSelect)
                        .addComponent(btnPlayNow)
                    )
                )
            );
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        
        btnJustSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJustSelectActionPerformed(evt);
            }
        });
        
        btnPlayNow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayNowActionPerformed(evt);
            }
        });

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for(javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ExplorerVideo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExplorerVideo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExplorerVideo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExplorerVideo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ExplorerVideo dialog = new ExplorerVideo(new javax.swing.JFrame(), true);
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
    
    // Custom class to hold an image and a label
    static class ImageLabel {
        private ImageIcon image;
        private String label;
        private String tooltip;

        public ImageLabel(ImageIcon image, String label, String tooltip) {
            this.image = image;
            this.label = label;
            this.tooltip = tooltip;
        }

        public ImageIcon getImage() {
            return image;
        }

        public String getLabel() {
            return label;
        }
        
        public String getTooltip() {
            return tooltip;
        }
    }
    
    private void btnJustSelectActionPerformed(java.awt.event.ActionEvent evt) {
        if(!selectedItem.equals("")) {
            if(MainFrame.playlistVideoMode == 1) {
                MainFrame.selectedPlaylistVideoItem = selectedItem;
            }
            else {
                MainFrame.selectedLoopVideoItem = selectedItem;
            }
            
            MainFrame.tfVideoLoop.setText(selectedItem);
            MainFrame.tfVideoLoop.setToolTipText(selectedItem);
            MainFrame.tfVideoLoop.moveCaretPosition(0);
            
//            selectedItem = "";
            dialog.setVisible(false);
        }
        else {
            btnJustSelect.setForeground(new Color(164, 0, 0));
            btnJustSelect.setBackground(new Color(255, 113, 113));
            btnJustSelect.setText("Nothing selected");
            
            just.stop();
            just.start();
        }
    }
    
    private void btnPlayNowActionPerformed(java.awt.event.ActionEvent evt) {
        if(!selectedItem.equals("")) {
            if(MainFrame.playlistVideoMode == 1) {
                MainFrame.selectedPlaylistVideoItem = selectedItem;
            }
            else {
                MainFrame.selectedLoopVideoItem = selectedItem;
            }
            
            MainFrame.tfVideoLoop.setText(selectedItem);
            MainFrame.tfVideoLoop.setToolTipText(selectedItem);
            MainFrame.tfVideoLoop.moveCaretPosition(0);
            
            dialog.setVisible(false);
            
            if(MainFrame.videoFirstTime == 1) {
                MainFrame.videoFirstTime = 0;
                
                MainFrame.playVid();
            }
            
//            selectedItem = "";

//            new java.awt.event.ActionListener() {
//                public void actionPerformed(java.awt.event.ActionEvent evt) {
//                    MainFrame.btnPlayVLActionPerformed(evt);
//                }
//            };
//            new java.awt.event.ActionListener() {
//                public void actionPerformed(java.awt.event.ActionEvent evt) {
//                    evtPlay = evt;
//                }
//            }


            //do not merge with the code above, stay as is
            if(MainFrame.videoFirstTime == 1) {
                MainFrame.playVid();
            }
            else {
                MainFrame.btnPlayVLActionPerformed(null);
            }
        }
        else {
            btnPlayNow.setForeground(new Color(164, 0, 0));
            btnPlayNow.setBackground(new Color(255, 113, 113));
            btnPlayNow.setText("Nothing selected");
            
            now.stop();
            now.start();
        }
    }
    
    public void buttonChangeText(int button) {
        if(button == 0) { // just select
            btnJustSelect.setForeground(new JButton().getForeground());
            btnJustSelect.setText("Just Select");
            
            if(HappyButtons.uiTheme.equals("light")) {
                btnJustSelect.setForeground(new JButton().getForeground());
                btnJustSelect.setBackground(new JButton().getBackground());
            }
            else if(HappyButtons.uiTheme.equals("dark")) {
                btnJustSelect.setForeground(Color.WHITE);
                btnJustSelect.setBackground(Color.DARK_GRAY);
            }
        }
        else if(button == 1) { // play now
            btnPlayNow.setForeground(new JButton().getForeground());
            btnPlayNow.setText("Play Now");
            
            if(HappyButtons.uiTheme.equals("light")) {
                btnPlayNow.setForeground(new JButton().getForeground());
                btnPlayNow.setBackground(new JButton().getBackground());
            }
            else if(HappyButtons.uiTheme.equals("dark")) {
                btnPlayNow.setForeground(Color.WHITE);
                btnPlayNow.setBackground(Color.DARK_GRAY);
            }
        }
    }
    
    // Custom cell renderer for the JList
    static class ImageLabelRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if(value instanceof ImageLabel) {
                ImageLabel imageLabel = (ImageLabel) value;
                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                JLabel imageLabelComponent = new JLabel(imageLabel.getImage());
                JLabel textLabel = new JLabel(imageLabel.getLabel(), SwingConstants.CENTER);
                textLabel.setPreferredSize(new Dimension(155, 30));

                panel.add(imageLabelComponent, BorderLayout.CENTER);
                panel.add(textLabel, BorderLayout.SOUTH);
                panel.setToolTipText(imageLabel.getTooltip());
                
                // Set an empty border (margin) around the panel
                panel.setBorder(new EmptyBorder(15, 15, 15, 15)); // top, left, bottom, right

                // Highlight the panel if selected
                if(isSelected) {
                    panel.setBackground(Color.LIGHT_GRAY);
                    selectedItem = imageLabel.getTooltip();
                }
                else {
                    panel.setBackground(Color.WHITE);
                }

                return panel;
            }
            
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }
    
    public void setupTheme() {
        if(HappyButtons.uiTheme.equals("light")) { // ------------------------------------------------------------------ LIGHT THEME -->
            // --------------------------------------------------------------------------------- FRAME -->
            dialog.getContentPane().setBackground(new JPanel().getBackground());
            
            // --------------------------------------------------------------------------------- PANELS -->
            jPanel1.setBackground(new JPanel().getBackground());
            
            // --------------------------------------------------------------------------------- BUTTONS -->
            btnJustSelect.setForeground(new JButton().getForeground());
            btnJustSelect.setBackground(new JButton().getBackground());
            
            btnPlayNow.setForeground(new JButton().getForeground());
            btnPlayNow.setBackground(new JButton().getBackground());
        }
        else if(HappyButtons.uiTheme.equals("dark")) { // ------------------------------------------------------------------ DARK THEME -->
            // --------------------------------------------------------------------------------- FRAME -->
            dialog.getContentPane().setBackground(Color.LIGHT_GRAY);
            
            // --------------------------------------------------------------------------------- PANELS -->
            jPanel1.setBackground(Color.LIGHT_GRAY);
            
            // --------------------------------------------------------------------------------- BUTTONS -->
            btnJustSelect.setForeground(Color.WHITE);
            btnJustSelect.setBackground(Color.DARK_GRAY);
            
            btnPlayNow.setForeground(Color.WHITE);
            btnPlayNow.setBackground(Color.DARK_GRAY);
        }
    }
    
    private void tfSearchBSKeyReleased(java.awt.event.KeyEvent evt) {
        model.removeAllElements();
        
        if(tfSearch.getText().equals("")) {
            reloadContent();
        }
        else {
            searchContent(tfSearch.getText());
        }
    }
    
    public void searchContent(String searchItem) {
        String search = ".*" + searchItem + ".*";
        Pattern pattern = Pattern.compile(search, Pattern.CASE_INSENSITIVE);
        
        try {
            for(int i = 0; i < vidList.length; i++) {
                Matcher matcher = pattern.matcher(vidList[i]);
                
                if(matcher.find()) {
                    File f = new File(HappyButtons.documentsPath + "\\HappyButtons\\data\\thumbnails\\" + vidList[i] + ".png");
                
                    if(f.exists()) {
                        BufferedImage img = ImageIO.read(f);
                        String label = Utility.prepareLabelNaming(Utility.shortenText(vidList[i], 20)); // Change this to your desired label
                        model.addElement(new ImageLabel(new ImageIcon(img), label, vidList[i]));
                    }
                }
            }
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
    }
    
    private void reloadContent() {
        try {
            for(int i = 0; i < vidList.length; i++) {
                File f = new File(HappyButtons.documentsPath + "\\HappyButtons\\data\\thumbnails\\" + vidList[i] + ".png");
                
                if(f.exists()) {
                    BufferedImage img = ImageIO.read(f);
                    String label = Utility.prepareLabelNaming(Utility.shortenText(vidList[i], 20)); // Change this to your desired label
                    model.addElement(new ImageLabel(new ImageIcon(img), label, vidList[i]));
                }
            }
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
    }
    
    public static javax.swing.JButton btnPlayNow;
    private javax.swing.JButton btnJustSelect;
    public JList<ImageLabel> listVideos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblWarning;
    private javax.swing.JTextField tfSearch;
}
