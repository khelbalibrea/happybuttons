/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package happybuttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 *
 * @author Michael Balibrea
 */
public class PluginsFrame extends javax.swing.JDialog {
    Color darkGreen = new Color(0, 140, 0);
    /**
     * Creates new form PluginsFrame
     */
    public PluginsFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        super.setTitle("Plugins");
        initComponents();
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/3-this.getSize().height/2);
        
        // set frame icon
        ImageIcon imgIcon = new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png"));
        setIconImage(imgIcon.getImage());
        
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                check();
            }
        });
        
        lblBuiltIn.setVisible(false);
        lblCheck.setVisible(false);
        
        tfVlcjPath.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    lblCheck.setVisible(false);
                    timer.setRepeats(false);
                    timer.start();
                }
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                lblCheck.setVisible(false);
                timer.setRepeats(false);
                timer.start();
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                lblCheck.setVisible(false);
                timer.setRepeats(false);
                timer.start();
            }
        });
        tfVlcjPath.setFocusable(false);
        tfVlcjPath.setText((HappyButtons.uiDB[0]).getVlcjPath());
        
        loadFrame();
    }
    
    public void autosave() {
        UIProfile ui = new UIProfile();
        HappyButtons.dbo.autoSaveUISettings(HappyButtons.uiDB, ui);
    }
    
    public void loadFrame() {
        boolean chk = checkPlugins(HappyButtons.documentsPath + "\\HappyButtons\\plugins\\vlcj");
        
        if(!chk) {
            rbtnBuiltIn.setSelected(false);
            rbtnBuiltIn.setEnabled(false);
            lblBuiltIn.setVisible(true);
            lblBuiltIn.setText("VLCj plugins not found");
            lblBuiltIn.setForeground(Color.RED);
            
            rbtnVlcjPath.setSelected(true);
            tfVlcjPath.setFocusable(true);
            tfVlcjPath.setText(HappyButtons.vlcjPath);
        }
        else {
            rbtnBuiltIn.setEnabled(true);
            rbtnBuiltIn.setSelected(true);
            lblBuiltIn.setVisible(true);
            lblBuiltIn.setText("VLCj plugins found, all good!");
            lblBuiltIn.setForeground(darkGreen);
            HappyButtons.vlcjPath = HappyButtons.documentsPath + "\\HappyButtons\\plugins\\vlcj";
            autosave();
            
            rbtnVlcjPath.setSelected(false);
            tfVlcjPath.setFocusable(false);
        }
    }
    
    public boolean checkPlugins(String path) {
        boolean check = false;
        
        File vlcPluginFolder = new File(path + "\\plugins");
        File vlcLib1 = new File(path + "\\libvlc.dll");
        File vlcLib2 = new File(path + "\\libvlccore.dll");
        
        if((vlcPluginFolder.exists() && vlcLib1.exists() && vlcLib2.exists())) {
            check = true;
            
            HappyButtons.vlcjPath = path;
        }
        
        return check;
    }
    
    public void check() {
        File vPath = new File(tfVlcjPath.getText());
        
        if(vPath.exists()) {
            lblCheck.setVisible(true);
            lblCheck.setText("Path found");
            lblCheck.setForeground(darkGreen);
            
            Timer pluginCheck = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean chk = checkPlugins(tfVlcjPath.getText());
                    
                    if(chk) {
                        lblCheck.setText("VLC plugins found");
                        lblCheck.setForeground(darkGreen);
                        
                        autosave();
                        (MainFrame.btnPlayVL).setEnabled(true);
                    }
                    else {
                        lblCheck.setText("VLC plugins not found in path provided..");
                        lblCheck.setForeground(Color.RED);
                        
                        HappyButtons.vlcjPath = "";
                        (MainFrame.btnPlayVL).setEnabled(false);
                        autosave();
                    }
                }
            });
            
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pluginCheck.setRepeats(false);
                    pluginCheck.start();
                }
            });
            
            timer.setRepeats(false);
            timer.start();
        }
        else {
            lblCheck.setVisible(true);
            lblCheck.setText("Path not found");
            lblCheck.setForeground(Color.RED);
            (MainFrame.btnPlayVL).setEnabled(false);
            HappyButtons.vlcjPath = "";
            autosave();
            
//            Timer timer = new Timer(5000, new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    lblCheck.setVisible(false);
//                }
//            });
//            
//            timer.setRepeats(false);
//            timer.start();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        tabPanel = new javax.swing.JTabbedPane();
        panelVlcj = new javax.swing.JPanel();
        rbtnBuiltIn = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        rbtnVlcjPath = new javax.swing.JRadioButton();
        lblBuiltIn = new javax.swing.JLabel();
        tfVlcjPath = new javax.swing.JTextField();
        lblCheck = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(630, 380));
        setMinimumSize(new java.awt.Dimension(630, 380));
        setResizable(false);

        panelVlcj.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonGroup1.add(rbtnBuiltIn);
        rbtnBuiltIn.setText("Built-in");
        rbtnBuiltIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnBuiltInActionPerformed(evt);
            }
        });
        panelVlcj.add(rbtnBuiltIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Plugin path:");
        panelVlcj.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 130, 30));

        buttonGroup1.add(rbtnVlcjPath);
        rbtnVlcjPath.setText("Specified path");
        rbtnVlcjPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnVlcjPathActionPerformed(evt);
            }
        });
        panelVlcj.add(rbtnVlcjPath, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        lblBuiltIn.setText("Text");
        panelVlcj.add(lblBuiltIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 280, -1));
        panelVlcj.add(tfVlcjPath, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 330, -1));

        lblCheck.setText("jLabel3");
        panelVlcj.add(lblCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 330, 20));

        tabPanel.addTab("VLCj", panelVlcj);

        getContentPane().add(tabPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbtnBuiltInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnBuiltInActionPerformed
        HappyButtons.vlcjPath = HappyButtons.documentsPath + "\\HappyButtons\\plugins\\vlcj";
    }//GEN-LAST:event_rbtnBuiltInActionPerformed

    private void rbtnVlcjPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnVlcjPathActionPerformed
        tfVlcjPath.setFocusable(true);
        tfVlcjPath.setText(HappyButtons.uiDB[0].getVlcjPath());
        
        check();
    }//GEN-LAST:event_rbtnVlcjPathActionPerformed

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
            java.util.logging.Logger.getLogger(PluginsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PluginsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PluginsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PluginsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PluginsFrame dialog = new PluginsFrame(new javax.swing.JFrame(), true);
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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblBuiltIn;
    private javax.swing.JLabel lblCheck;
    private javax.swing.JPanel panelVlcj;
    private javax.swing.JRadioButton rbtnBuiltIn;
    private javax.swing.JRadioButton rbtnVlcjPath;
    private javax.swing.JTabbedPane tabPanel;
    private javax.swing.JTextField tfVlcjPath;
    // End of variables declaration//GEN-END:variables
}
