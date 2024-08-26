/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package happybuttons;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author balib
 */
public class UpdateLogFrame extends javax.swing.JDialog {
    String theme = HappyButtons.uiTheme;
    
    public UpdateLogFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        super.setTitle("Updates Log");
        
        initComponents();
        
        ImageIcon imgIcon = new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png"));
        setIconImage(imgIcon.getImage());
        
        // Create a StyledDocument
        StyledDocument doc = textUpdatesLog.getStyledDocument();
        
        // Create attribute sets for styles
        SimpleAttributeSet boldAttrs = new SimpleAttributeSet();
        StyleConstants.setBold(boldAttrs, true);
        
        SimpleAttributeSet plainAttrs = new SimpleAttributeSet();
        
        try {
            doc.insertString(doc.getLength(), "[September 1, 2024]\n", boldAttrs);
            doc.insertString(doc.getLength(), "• [Add] Search bar in Resources Table [BGM and SFX, Video loop, Mp3].\n\n", plainAttrs);
            doc.insertString(doc.getLength(), "[August 23, 2024]\n", boldAttrs);
            doc.insertString(doc.getLength(), "• [Add] Mp3 text field in Main Frame can be cleared via right click. The music will also stop.\n"
                    + "• [Add] Updates log under About Frame.\n"
                    + "• [Add] System Tools menu tab for Video thumbnail checker and Image resizer.\n"
                    + "• [Modify] Bigger Mp3 UI.\n"
                    + "• [Modify] Minor changes for database location path.\n" 
                    + "• [Modify] Video explorer look and feel (LAF) frame.\n"
                    + "• [Modify] Made some changes in main user interface (UI).\n"
                    + "• [Modify] Video playlist is now selected first instead of video loop in Video Selection.\n\n", plainAttrs);
            doc.insertString(doc.getLength(), "[July 30, 2024]\n", boldAttrs);
            doc.insertString(doc.getLength(), "• [Added] Mp3 slider for song duration and current duration, and timer for it.\n"
                    + "• [Add] Mp3 song timer in Main Frame also.\n\n", plainAttrs);
            doc.insertString(doc.getLength(), "[June 2024]\n", boldAttrs);
            doc.insertString(doc.getLength(), "• [Added] Confirmation when saving a profile.\n"
                    + "• [Add] Set video volume down to 90% in order to SFX be heard.\n"
                    + "• [Add] Added UI in adjusting video volume, under Settings.\n"
                    + "• [Modify] Updated About frame.\n"
                    + "• [Modify] Set dark theme notification to \'Night forest\'.\n• ", plainAttrs);
            doc.insertString(doc.getLength(), "Bug fix:\n", boldAttrs);
            doc.insertString(doc.getLength(), "     - Fix video loop stop button where it does not stop the video but rather opens another frame.\n"
                    + "     - Fix bug where Mp3 list is not loaded when loading a profile.", plainAttrs);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        setupTheme();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnClose = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        textUpdatesLog = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        textUpdatesLog.setEditable(false);
        jScrollPane2.setViewportView(textUpdatesLog);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(261, 261, 261)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(262, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

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
            java.util.logging.Logger.getLogger(UpdateLogFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateLogFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateLogFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateLogFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UpdateLogFrame dialog = new UpdateLogFrame(new javax.swing.JFrame(), true);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane textUpdatesLog;
    // End of variables declaration//GEN-END:variables

    private void setupTheme() {
        // Setting up theme
        if(theme.equals("light")) {
            // ------------------------------------------------------------------------------- PANELS
            this.getContentPane().setBackground(new JDialog().getBackground());
            
            // ------------------------------------------------------------------------------- BUTTONS
            btnClose.setBackground(new JButton().getBackground());
            
            // ------------------------------------------------------------------------------- TEXT PANES
            textUpdatesLog.setForeground(new JTextPane().getForeground());
            textUpdatesLog.setBackground(new JTextPane().getBackground());
        }
        if(theme.equals("dark")) {
            // ------------------------------------------------------------------------------- PANELS
            this.getContentPane().setBackground(Color.DARK_GRAY);
            
            // ------------------------------------------------------------------------------- BUTTONS
            btnClose.setBackground(Color.GRAY);
            
            // ------------------------------------------------------------------------------- TEXT PANES
            textUpdatesLog.setForeground(Color.LIGHT_GRAY);
            textUpdatesLog.setBackground(Color.DARK_GRAY);
        }
    }

}
