/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package happybuttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 *
 * @author Michael Balibrea
 */
public class AboutFrame extends javax.swing.JDialog {
    String theme = HappyButtons.uiTheme;

    /**
     * Creates new form AboutFrame
     */
    public AboutFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/3-this.getSize().height/2);
        
        ImageIcon imgIcon = new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png"));
        setIconImage(imgIcon.getImage());
        
        ImageIcon hbIcon = new ImageIcon(new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        lblHappyButtons.setIcon(hbIcon);
        
        ImageIcon minobaIcon = new ImageIcon(new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\minoba.png")).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        lblMinoba.setIcon(minobaIcon);
        
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

        lblIcon = new javax.swing.JLabel();
        lblHappyButtons = new javax.swing.JLabel();
        lblMinoba = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblUpdateLog = new javax.swing.JLabel("<html><u>Open updates log</u></html>");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About");
        setMaximumSize(new java.awt.Dimension(400, 200));
        setMinimumSize(new java.awt.Dimension(400, 200));
        setPreferredSize(new java.awt.Dimension(400, 200));
        setResizable(false);

        lblHappyButtons.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        lblHappyButtons.setText("Happy Buttons");

        jLabel1.setText("MI-chael   NO-jor   BA-librea      (Minoba)");

        jLabel2.setText("Email: balibreamichael@gmail.com");

        lblUpdateLog.setForeground(new java.awt.Color(153, 153, 255));
        lblUpdateLog.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblUpdateLog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUpdateLogMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblMinoba, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHappyButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUpdateLog))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblHappyButtons, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMinoba, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblUpdateLog)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblUpdateLogMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUpdateLogMouseClicked
        UpdateLogFrame frame = new UpdateLogFrame(HappyButtons.mf, true);
        frame.setVisible(true);
    }//GEN-LAST:event_lblUpdateLogMouseClicked

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
            java.util.logging.Logger.getLogger(AboutFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AboutFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AboutFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AboutFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AboutFrame dialog = new AboutFrame(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblHappyButtons;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblMinoba;
    private javax.swing.JLabel lblUpdateLog;
    // End of variables declaration//GEN-END:variables

    private void setupTheme() {
        // Setting up theme
        if(theme.equals("light")) {
            // ------------------------------------------------------------------------------- PANELS
            this.getContentPane().setBackground(new JDialog().getBackground());
            
            // ------------------------------------------------------------------------------- LABELS
            lblIcon.setBackground(new JLabel().getBackground());
            lblIcon.setForeground(new JLabel().getForeground());
            lblIcon.setOpaque(true);
            
            lblHappyButtons.setBackground(new JLabel().getBackground());
            lblHappyButtons.setForeground(new JLabel().getForeground());
            lblHappyButtons.setOpaque(true);
            
            lblMinoba.setBackground(new JLabel().getBackground());
            lblMinoba.setForeground(new JLabel().getForeground());
            lblMinoba.setOpaque(true);
            
            jLabel1.setBackground(new JLabel().getBackground());
            jLabel1.setForeground(new JLabel().getForeground());
            jLabel1.setOpaque(true);
            
            jLabel2.setBackground(new JLabel().getBackground());
            jLabel2.setForeground(new JLabel().getForeground());
            jLabel2.setOpaque(true);
        }
        if(theme.equals("dark")) {
            // ------------------------------------------------------------------------------- PANELS
            this.getContentPane().setBackground(Color.DARK_GRAY);
            
            // ------------------------------------------------------------------------------- LABELS
            lblIcon.setBackground(Color.DARK_GRAY);
            lblIcon.setForeground(Color.LIGHT_GRAY);
            lblIcon.setOpaque(true);
            
            lblHappyButtons.setBackground(Color.DARK_GRAY);
            lblHappyButtons.setForeground(Color.LIGHT_GRAY);
            lblHappyButtons.setOpaque(true);
            
            lblMinoba.setBackground(Color.DARK_GRAY);
            lblMinoba.setForeground(Color.LIGHT_GRAY);
            lblMinoba.setOpaque(true);
            
            jLabel1.setBackground(Color.DARK_GRAY);
            jLabel1.setForeground(Color.LIGHT_GRAY);
            jLabel1.setOpaque(true);
            
            jLabel2.setBackground(Color.DARK_GRAY);
            jLabel2.setForeground(Color.LIGHT_GRAY);
            jLabel2.setOpaque(true);
            
        }
    }
}
