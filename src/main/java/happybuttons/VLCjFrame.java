package happybuttons;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class VLCjFrame {
    static JFrame frame;
    
    static MediaPlayerFactory mpf = new MediaPlayerFactory();
    static EmbeddedMediaPlayer emp = mpf.newEmbeddedMediaPlayer(
            new Win32FullScreenStrategy(
                    frame
            )
    );
    public static void main(String[] args) {
        VLCjFrame();
    }
    
    public static void VLCjFrame() {
        frame = new JFrame("Static JFrame Example");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/3-frame.getSize().height/2);
        
        // set frame icon
        ImageIcon imgIcon = new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png"));
        frame.setIconImage(imgIcon.getImage());
        
//        canvasMain.setBackground(Color.BLACK);
//        canvasMain.setVisible(false);
        
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), HappyButtons.vlcjPath);
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        
        // emp.toggleFullScreen();
        emp.setEnableMouseInputHandling(false);
        emp.setEnableKeyInputHandling(false);
        
        String file = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\vid.mp4");
        emp.prepareMedia(file);
        emp.play();
    }
}