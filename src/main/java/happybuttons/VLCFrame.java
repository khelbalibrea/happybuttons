/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package happybuttons;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
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
    MediaPlayerEventAdapter adapter = new MediaPlayerEventAdapter();
    ActionListener checkBoxAction, playAction;
    String file = "", videoFilename = "";
    Dimension dim;
    JFrame frame = this;
    EmbeddedMediaPlayer emp;
//    EmbeddedMediaPlayerComponent component = new EmbeddedMediaPlayerComponent();
    
//    static MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
    
    /**
     * Creates new form VLCFrame
     */
    public VLCFrame() {
        super.setTitle("Video");
        initComponents();
        
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(dim);
        
        // set frame icon
        ImageIcon imgIcon = new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png"));
        setIconImage(imgIcon.getImage());
        canvasMain.setBackground(Color.BLACK);
        
        // Initializing actions
        checkBoxAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(MainFrame.chkVLMute == 0) {
                    MainFrame.chkVLMute = 1;
                    emp.mute();
                }
                else {
                    MainFrame.chkVLMute = 0;
                    emp.mute(false);
                }
            }
        };
        
        playAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!((MainFrame.cboVidLoop).getSelectedItem().toString()).equals(videoFilename)) {
                    emp.removeMediaPlayerEventListener(videoListener);
                    emp.stop();
                    
                    file = HappyButtons.documentsPathDoubleSlash + 
                    Utility.strDoubleSlash("\\HappyButtons\\hlvids\\" + 
                            (MainFrame.cboVidLoop).getSelectedItem() + 
                            ".mp4");
                    videoFilename = (MainFrame.cboVidLoop).getSelectedItem().toString();
                    emp.prepareMedia(file);
                    emp.addMediaPlayerEventListener(videoListener);
                    emp.play();
                }
            }
        };
        
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), HappyButtons.vlcjPath);
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        
        MediaPlayerFactory mpf = new MediaPlayerFactory();
        emp = mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(frame));
        emp.setVideoSurface(mpf.newVideoSurface(canvasMain));
        frame.setVisible(true);
        
        MainFrame.btnStopVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.vlcjPlaying = 0;
                emp.removeMediaPlayerEventListener(videoListener);
                emp.stop();
                emp.release();
                MainFrame.btnPlayVL.removeActionListener(playAction);
                MainFrame.chkMuteVL.removeActionListener(checkBoxAction);
                frame.dispose();
            }
        });

        videoFilename = (MainFrame.cboVidLoop).getSelectedItem().toString();
        if((MainFrame.cboVidLoop).getSelectedItem() != null) {
            file = HappyButtons.documentsPathDoubleSlash + 
                    Utility.strDoubleSlash("\\HappyButtons\\hlvids\\" + 
                            MainFrame.cboVidLoop.getSelectedItem() + 
                            ".mp4");
            emp.prepareMedia(file);
            emp.addMediaPlayerEventListener(videoListener);
            emp.play();
            
            if(MainFrame.chkVLMute == 1) {
                emp.mute(true);
            }
            else {
                emp.mute(false);
            }
            
            MainFrame.btnPlayVL.addActionListener(playAction);
            MainFrame.chkMuteVL.addActionListener(checkBoxAction);
        }
        else {
            MainFrame.tfLastOperation.setText("No video to play");
        }
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
            if(MainFrame.chkVLLoop == 1){
                mediaPlayer.prepareMedia(file);
                dim = Toolkit.getDefaultToolkit().getScreenSize();
                mediaPlayer.play();
            }
            else {
                mediaPlayer.stop();
                MainFrame.btnPlayVL.removeActionListener(playAction);
                MainFrame.chkMuteVL.removeActionListener(checkBoxAction);
                frame.dispose();
                MainFrame.vlcjPlaying = 0;
            }
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(null);
        setMinimumSize(new java.awt.Dimension(1366, 768));
        setPreferredSize(new java.awt.Dimension(1366, 768));

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
                new VLCFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Canvas canvasMain;
    // End of variables declaration//GEN-END:variables
}
