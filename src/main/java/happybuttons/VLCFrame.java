/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package happybuttons;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 *
 * @author Michael Balibrea
 */
public class VLCFrame extends javax.swing.JFrame {
    MediaListener videoListener = new MediaListener();
    public static ActionListener playAction, checkBoxAction, fitAction;
    MediaPlayerFactory mpf = null;
    String file = "", // str just for file here locally in class
        videoFilename = ""; // currently playing or prepared video
    EmbeddedMediaPlayer emp;
    Dimension dim;
    JFrame frame = this;
    GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
    
    int screenWidth = 0, screenHeight = 0;
    String screenRatio = "", vidOrigRatio = "";
    
    public VLCFrame() {
        super.setTitle("Video");
        initComponents();
        
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(dim);
        
        // set frame icon
        ImageIcon imgIcon = new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png"));
        setIconImage(imgIcon.getImage());
        
        fitAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(MainFrame.chkVLFit == 0) {
                    MainFrame.chkVLFit = 1;
                    emp.setAspectRatio(screenRatio);
                }
                else {
                    MainFrame.chkVLFit = 0;
                    emp.setAspectRatio(vidOrigRatio);
                }
            }
        };
        
        // Initializing actions for checkbox mute in VLC
        checkBoxAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(MainFrame.chkVLMute == 0) {
                    if(emp != null) {
                        emp.mute();
                        MainFrame.chkVLMute = 1;
                    }
                }
                else {
                    if(emp != null) {
                        emp.mute(false);
                        MainFrame.chkVLMute = 0;
                    }
                }
            }
        };
        
        playAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((!(MainFrame.tfVideoLoop.getText().equals(videoFilename))) && !MainFrame.tfVideoLoop.equals("")) { // System.out.println("Selected2: " + selectedItem + "\nVideo2: " + videoFilename);
//                    if(MainFrame.vlStopClicked == 1) {
                        emp.removeMediaPlayerEventListener(videoListener);
                        emp.stop();
                        emp.release();
                        mpf.release();
                        
                        mpf = new MediaPlayerFactory();
                        emp = null;
                        emp = mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(frame));
                        emp.setVideoSurface(mpf.newVideoSurface(canvasMain));

                        file = HappyButtons.documentsPathDoubleSlash + 
                        Utility.strDoubleSlash("\\HappyButtons\\hlvids\\" + 
                                MainFrame.tfVideoLoop.getText() + 
                                ".mp4");
                        videoFilename = MainFrame.tfVideoLoop.getText();
                        emp.prepareMedia(file);
                        emp.addMediaPlayerEventListener(videoListener);
                        emp.play();
                        
                        if(MainFrame.chkVLFit == 0) {
                            emp.setAspectRatio(vidOrigRatio);
                        }
                        else {
                            emp.setAspectRatio(screenRatio);
                        }
//                    }
                }
                else {
                    System.out.println("Play Action else");
                }
            }
        };
        
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), HappyButtons.vlcjPath);
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        
        mpf = new MediaPlayerFactory();
        emp = mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(frame));
        emp.setVideoSurface(mpf.newVideoSurface(canvasMain));
        emp.setVolume(MainFrame.videoMainVolume);
        
        if(screenDevices.length > 1) {
            GraphicsDevice secondScreen = screenDevices[1];
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setLocation(secondScreen.getDefaultConfiguration().getBounds().x, 0); // Set the location to the second screen
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            
            frame.setLayout(new BorderLayout());
            canvasMain.setBackground(Color.BLACK);
            canvasMain.setForeground(Color.BLACK);
            frame.add(canvasMain, BorderLayout.CENTER);
            frame.pack();
            
            DisplayMode displayMode = secondScreen.getDisplayMode();

            screenWidth = displayMode.getWidth();
            screenHeight = displayMode.getHeight();

            String reso = "Playscreen Resolution: " + screenWidth + "x" + screenHeight;

            int ratioWidth = screenWidth / 120;
            int ratioHeight = (int) Math.ceil(screenHeight / 120);
            
            screenRatio = ratioWidth + ":" + ratioHeight;

            MainFrame.tfLastOperation.setText(Utility.shortenText(reso + "  (" + ratioWidth + ":" + ratioHeight + ")", 50));
            MainFrame.tfLastOperation.setToolTipText(reso + "  (" + ratioWidth + ":" + ratioHeight + ")");
        }
        else { // no secondary screen device detected
            GraphicsDevice screen = screenDevices[0];
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            
            frame.setLayout(new BorderLayout());
            canvasMain.setBackground(Color.BLACK);
            canvasMain.setForeground(Color.BLACK);
            frame.add(canvasMain, BorderLayout.CENTER);
            frame.pack();
            
            DisplayMode displayMode = screen.getDisplayMode();

            screenWidth = displayMode.getWidth();
            screenHeight = displayMode.getHeight();

            String reso = "Playscreen Resolution: " + screenWidth + "x" + screenHeight;

            int ratioWidth = screenWidth / 120;
            int ratioHeight = (int) Math.ceil(screenHeight / 120);
            
            screenRatio = ratioWidth + ":" + ratioHeight;

            MainFrame.tfLastOperation.setText(Utility.shortenText(reso + "  (" + ratioWidth + ":" + ratioHeight + ")", 50));
            MainFrame.tfLastOperation.setToolTipText(reso + "  (" + ratioWidth + ":" + ratioHeight + ")");
        }
        
        frame.setVisible(true);
        
        MainFrame.btnStopVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(emp != null) { // System.out.println("Not null");
                    MainFrame.vlcjPlaying = 0;
                    emp.removeMediaPlayerEventListener(videoListener);
                    emp.stop();
                    emp.release();
                    emp = null;
//                    mpf.release();
//                    mpf = null;
                    MainFrame.vlStopClicked = 1;

//                    MainFrame.btnPlayVL.removeActionListener(playAction);
                    MainFrame.chkMuteVL.removeActionListener(checkBoxAction);
                    MainFrame.chkFitVL.removeActionListener(fitAction);
                    frame.dispose();
                }
            }
        });

        videoFilename = MainFrame.tfVideoLoop.getText();
        if(MainFrame.playlistVideoMode == 1) {
            MainFrame.selectedPlaylistVideoItem = videoFilename;
        }
        else {
            MainFrame.selectedLoopVideoItem = videoFilename;
        }
                
        if(!MainFrame.tfVideoLoop.getText().equals("")) {
            file = HappyButtons.documentsPathDoubleSlash + 
                    Utility.strDoubleSlash("\\HappyButtons\\hlvids\\" + 
                            MainFrame.tfVideoLoop.getText() + 
                            ".mp4");
            vidOrigRatio = emp.getAspectRatio();
            
            if(MainFrame.chkVLFit == 0) {
                emp.setAspectRatio(vidOrigRatio);
            }
            else {
                emp.setAspectRatio(screenRatio);
            }
            
            if(MainFrame.fullScreenVL.equals("window")) {
                emp.setFullScreen(false);
            }
            else {
                emp.setFullScreen(true);
            }
            
            emp.prepareMedia(file);
            emp.addMediaPlayerEventListener(videoListener);
            emp.play();
            
            if(MainFrame.chkVLMute == 1) {
                emp.mute(true);
            }
            else {
                emp.mute(false);
            }
            
//            MainFrame.btnPlayVL.addActionListener(playAction);
            MainFrame.chkMuteVL.addActionListener(checkBoxAction);
            MainFrame.chkFitVL.addActionListener(fitAction);
        }
        else {
            MainFrame.tfLastOperation.setText("VIDEO: Nothing to play");
        }
        
//        if(MainFrame.chkVLFit == 0) {
//            emp.setAspectRatio(vidOrigRatio);
//        }
//        else {
//            emp.setAspectRatio(screenRatio);
//        }
    }
    
    public class MediaListener implements MediaPlayerEventListener {
        @Override
        public void opening(MediaPlayer mediaPlayer) {
            
        }

        @Override
        public void buffering(MediaPlayer mediaPlayer, float newCache) {
            
        }

        @Override
        public void playing(MediaPlayer mediaPlayer) {
            
        }

        @Override
        public void paused(MediaPlayer mediaPlayer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void stopped(MediaPlayer mediaPlayer) {
            
        }

        @Override
        public void mediaChanged(MediaPlayer mediaPlayer, libvlc_media_t media, String mrl) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void forward(MediaPlayer mediaPlayer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void backward(MediaPlayer mediaPlayer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void finished(MediaPlayer mediaPlayer) {
            if(MainFrame.playlistVideoMode == 0) { // video loop mode
                if(MainFrame.chkVLLoop == 1){
                    emp.prepareMedia(file);
                    dim = Toolkit.getDefaultToolkit().getScreenSize();
                    emp.play();
                }
                else {
                    mediaPlayer.stop();
//                    MainFrame.btnPlayVL.removeActionListener(playAction);
                    MainFrame.chkMuteVL.removeActionListener(checkBoxAction);
                    MainFrame.chkFitVL.removeActionListener(fitAction);
                    frame.dispose();
                    MainFrame.vlcjPlaying = 0;
                }
            }
            else { // playlist mode
                if(MainFrame.chkVLLoop == 1){
                    emp.prepareMedia(file);
                    dim = Toolkit.getDefaultToolkit().getScreenSize();
                    emp.play();
                }
                else { // System.out.println("Length: " + MainFrame.vidQueue.length);
                    if(MainFrame.vidQueue.length <= 1) {
                        MainFrame.vidQueue = new String[0];
                        MainFrame.vidQueue = MainFrame.vlQueue;
//                        System.out.println("Ubos na");
//                        Utility.testPrintStrArray(MainFrame.vidQueue);
                    }
                    else { 
                        MainFrame.vidQueue = Utility.removeIndexInStrArr(MainFrame.vidQueue, 0);
//                        System.out.println("Meron pa");
//                        Utility.testPrintStrArray(MainFrame.vidQueue);
                    }
                    
//                    mpf.release();
//                    mpf = null;
//                    mpf = new MediaPlayerFactory();
//                    emp = null;
//                    emp = mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(frame));
//                    emp.setVideoSurface(mpf.newVideoSurface(canvasMain));
                    mediaPlayer.stop();
                    
                    file = HappyButtons.documentsPathDoubleSlash + 
                    Utility.strDoubleSlash("\\HappyButtons\\hlvids\\" + 
                            MainFrame.vidQueue[0] + 
                            ".mp4");
                    videoFilename = MainFrame.vidQueue[0];
                    MainFrame.tfVideoLoop.setText(MainFrame.vidQueue[0]);
                    MainFrame.tfVideoLoop.setToolTipText(MainFrame.vidQueue[0]);
                    MainFrame.selectedPlaylistVideoItem = MainFrame.vidQueue[0];
                    MainFrame.tfVideoLoop.moveCaretPosition(0);
                    
                    emp.prepareMedia(file);
                    dim = Toolkit.getDefaultToolkit().getScreenSize();
                    
                    emp.play();
                    
                    if(MainFrame.chkVLFit == 0) {
                        emp.setAspectRatio(vidOrigRatio);
                    }
                    else {
                        emp.setAspectRatio(screenRatio);
                    }
                    
                    MainFrame.chkVLLoop = 0;
                    MainFrame.chkLoopVL.setSelected(false);
                }
            }
            
//            if(MainFrame.chkVLLoop == 1){
//                mediaPlayer.prepareMedia(file);
//                dim = Toolkit.getDefaultToolkit().getScreenSize();
//                mediaPlayer.play();
//            }
//            else {
//                mediaPlayer.stop();
//                MainFrame.btnPlayVL.removeActionListener(playAction);
//                MainFrame.chkMuteVL.removeActionListener(checkBoxAction);
//                MainFrame.chkFitVL.removeActionListener(fitAction);
//                frame.dispose();
//                MainFrame.vlcjPlaying = 0;
//            }
        }

        @Override
        public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void positionChanged(MediaPlayer mediaPlayer, float newPosition) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void seekableChanged(MediaPlayer mediaPlayer, int newSeekable) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void pausableChanged(MediaPlayer mediaPlayer, int newPausable) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void titleChanged(MediaPlayer mediaPlayer, int newTitle) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void snapshotTaken(MediaPlayer mediaPlayer, String filename) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void lengthChanged(MediaPlayer mediaPlayer, long newLength) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void videoOutput(MediaPlayer mediaPlayer, int newCount) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void error(MediaPlayer mediaPlayer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void mediaMetaChanged(MediaPlayer mediaPlayer, int metaType) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void mediaSubItemAdded(MediaPlayer mediaPlayer, libvlc_media_t subItem) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void mediaDurationChanged(MediaPlayer mediaPlayer, long newDuration) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void mediaParsedChanged(MediaPlayer mediaPlayer, int newStatus) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void mediaFreed(MediaPlayer mediaPlayer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void mediaStateChanged(MediaPlayer mediaPlayer, int newState) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void newMedia(MediaPlayer mediaPlayer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void subItemPlayed(MediaPlayer mediaPlayer, int subItemIndex) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void subItemFinished(MediaPlayer mediaPlayer, int subItemIndex) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void endOfSubItems(MediaPlayer mediaPlayer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

        canvasMain = new java.awt.Canvas();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(new java.awt.Color(0, 0, 0));
        setMinimumSize(new java.awt.Dimension(1366, 768));

        canvasMain.setBackground(new java.awt.Color(0, 0, 0));
        canvasMain.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(canvasMain, javax.swing.GroupLayout.PREFERRED_SIZE, 1320, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(canvasMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(VLCFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VLCFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VLCFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VLCFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VLCFrame().setVisible(true);
                }
                catch(Exception e) {
                    
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public java.awt.Canvas canvasMain;
    // End of variables declaration//GEN-END:variables
}
