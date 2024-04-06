package happybuttons;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicFileChooserUI;

public class CustomFileChooserUI extends BasicFileChooserUI {
    public static ComponentUI createUI(JComponent c) {
        return new CustomFileChooserUI((JFileChooser) c);
    }

    public CustomFileChooserUI(JFileChooser fileChooser) {
        super(fileChooser);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Override and customize UI methods as needed
    
}