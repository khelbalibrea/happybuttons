package happybuttons;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JDialog;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 *
 * @author Michael Balibrea
 */
public class Notification extends javax.swing.JComponent {
    private JDialog dialog;
    private Animator animator;
    private final Frame frame;
    private boolean showing;
    private Thread thread;
    private int animate = 10;
    
    private BufferedImage imageShadow;
    private int shadowSize = 6;
    
    private Type type;
    private Location location;
    
    public Notification(Frame frame, Type type, Location location, String message, String description) {
        this.frame = frame;
        this.type = type;
        this.location = location;
        
        initComponents();
        
        String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\notif\\notif_success_24px.png");
        lblIcon.setIcon(new javax.swing.ImageIcon(icon));
        
        String btnCloseIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\notif\\notif_close.png");
        btnClose.setIcon(new javax.swing.ImageIcon(btnCloseIcon));
        
        init(message, description);
        initAnimator();
    }
    
    private void init(String message, String description) {
        if(HappyButtons.uiTheme.equals("light")) {
            setBackground(Color.WHITE);
        }
        else if(HappyButtons.uiTheme.equals("dark")) {
            setBackground(Color.DARK_GRAY);
        }
        
        dialog = new JDialog(frame);
        dialog.setUndecorated(true);
        dialog.setFocusableWindowState(false);
        dialog.setBackground(new Color(0, 0, 0, 0));
        dialog.add(this);
        dialog.setSize(getPreferredSize());
        
        if(type == Type.SUCCESS) {
//            lblIcon.setIcon(new javax.swing.ImageIcon(getClass(0.getResource("/")))); // this is after the Source Package
            String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\notif\\notif_success_24px.png");
            lblIcon.setIcon(new javax.swing.ImageIcon(icon));
        }
        else if(type == Type.INFO) {
            String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\notif\\notif_info.png");
            lblIcon.setIcon(new javax.swing.ImageIcon(icon));
        }
        else if(type == Type.WARNING) {
            String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\notif\\notif_warning.png");
            lblIcon.setIcon(new javax.swing.ImageIcon(icon));
        }
        else if(type == Type.ERROR) {
            String icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\notif\\notif_error_24px.png");
            lblIcon.setIcon(new javax.swing.ImageIcon(icon));
        }
        
        if(HappyButtons.uiTheme.equals("light")) {
            setBackground(Color.WHITE);

            lblMessage.setForeground(Color.BLACK);
            lblMessage.setBackground(Color.WHITE);
            lblMessage.setOpaque(true);

            lblMessageText.setForeground(Color.GRAY);
            lblMessageText.setBackground(Color.WHITE);
            lblMessageText.setOpaque(true);
        }
        else if(HappyButtons.uiTheme.equals("dark")) {
            setBackground(Color.DARK_GRAY);

            lblMessage.setForeground(Color.WHITE);
            lblMessage.setBackground(Color.DARK_GRAY);
            lblMessage.setOpaque(true);

            lblMessageText.setForeground(Color.LIGHT_GRAY);
            lblMessageText.setBackground(Color.DARK_GRAY);
            lblMessageText.setOpaque(true);
        }
        
        lblMessage.setText(message);
        lblMessageText.setText(description);
    }
    
    private void initAnimator() {
        TimingTarget target = new TimingTargetAdapter() {
            private int x;
            private int top;
            private boolean top_to_bot;
            
            @Override
            public void timingEvent(float fraction) {
                if(showing) {
                    float alpha = 1f - fraction;
                    int y = (int) ((1f - fraction) * animate);
                    
                    if(top_to_bot) {
                        dialog.setLocation(x, top + y);
                    }
                    else {
                        dialog.setLocation(x, top - y);
                    }
                    dialog.setOpacity(alpha);
                }
                else {
                    float alpha = fraction;
                    int y = (int) (fraction * animate);
                    
                    if(top_to_bot) {
                        dialog.setLocation(x, top + y);
                    }
                    else {
                        dialog.setLocation(x, top - y);
                    }
                    dialog.setOpacity(alpha);
                }
                repaint();
            }
            
            @Override
            public void begin() {
                if(!showing) {
                    dialog.setOpacity(0f);
                    int margin = 10;
                    int y = 0;
                    
                    if(location == Location.TOP_CENTER) {
                        x = frame.getX() + ((frame.getWidth() - dialog.getWidth()) / 2);
                        y = frame.getY();
                        top_to_bot = true;
                    }
                    else if(location == Location.TOP_RIGHT) {
                        x = frame.getX() + (frame.getWidth() - dialog.getWidth() - margin);
                        y = frame.getY();
                        top_to_bot = true;
                    }
                    else if(location == Location.TOP_LEFT) {
                        x = frame.getX() + margin;
                        y = frame.getY();
                        top_to_bot = true;
                    }
                    else if(location == Location.BOTTOM_CENTER) {
                        x = frame.getX() + ((frame.getWidth() - dialog.getWidth()) / 2);
                        y = frame.getY() + frame.getHeight() - dialog.getHeight();
                        top_to_bot = false;
                    }
                    else if(location == Location.BOTTOM_RIGHT) {
                        x = frame.getX() + (frame.getWidth() - dialog.getWidth() - margin);
                        y = frame.getY() + frame.getHeight() - dialog.getHeight();
                        top_to_bot = false;
                    }
                    else if(location == Location.BOTTOM_LEFT) {
                        x = frame.getX() + margin;
                        y = frame.getY() + frame.getHeight() - dialog.getHeight();
                        top_to_bot = false;
                    }
                    else { // top-center
                        x = frame.getX() + ((frame.getWidth() - dialog.getWidth()) / 2);
                        y = frame.getY();
                        top_to_bot = true;
                    }
                    
                    top = y;
                    dialog.setLocation(x, y);
                    dialog.setVisible(true);
                }
            }
            
            @Override
            public void end() {
                showing = !showing;
                
                if(showing) {
                    thread = new Thread(new Runnable() {
                       @Override
                       public void run() {
                           sleep();
                           closeNotification();
                       }
                    });
                    thread.start();
                }
                else {
                    dialog.dispose();
                }
            }
        };
                
        animator = new Animator(500, target);
        animator.setResolution(5);
    }
    
    public void showNotification() {
        animator.start();
    }
    
    private void closeNotification() {
        if(thread != null && thread.isAlive()) {
            thread.interrupt();
        }
        
        if(animator.isRunning()) {
            if(!showing) {
                animator.stop();
                showing = true;
                animator.start();
            }
        }
        else {
            showing = true;
            animator.start();
        }
    }
    
    private void sleep() {
        try {
            Thread.sleep(2000);
        }
        catch(InterruptedException e) {
            
        }
    }
    
    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.drawImage(imageShadow, 0, 0, null);
        
        int x = shadowSize;
        int y = shadowSize;
        int width = getWidth() - shadowSize * 2;
        int height = getHeight() - shadowSize * 2;
        
        g2.fillRect(x, y, width, height);
        
        if(type == Type.SUCCESS) {
            g2.setColor(new Color(18, 163, 24));
        }
        else if(type == Type.INFO) {
            g2.setColor(new Color(20, 161, 234));
        }
        else if(type == Type.WARNING) {
            g2.setColor(new Color(241, 196, 15));
        }
        else if(type == Type.ERROR) {
            g2.setColor(new Color(209, 67, 90));
        }
        
        g2.fillRect(6, 5, 5, getHeight() - shadowSize * 2 + 1);
        g2.dispose();
        
        super.paint(grphcs);
    }
    
    @Override
    public void setBounds(int i, int i1, int i2, int i3) {
        super.setBounds(i, i1, i2, i3);
        createImageShadow();
    }
    
    private void createImageShadow() {
        imageShadow = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imageShadow.createGraphics();
        g2.drawImage(createShadow(), 0, 0, null);
        g2.dispose();
    }
    
    private BufferedImage createShadow() {
        BufferedImage img = new BufferedImage(getWidth() - shadowSize * 2, getHeight() - shadowSize * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.fillRect(0, 0, img.getWidth(), img.getHeight());
        
        return new NotifShadowRenderer(shadowSize, 0.3f, new Color(100, 100, 100)).createShadow(img);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIcon = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();
        lblMessage = new javax.swing.JLabel();
        lblMessageText = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();

        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        panel.setOpaque(false);

        lblMessage.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMessage.setForeground(new java.awt.Color(38, 38, 38));
        lblMessage.setText("Message");

        lblMessageText.setForeground(new java.awt.Color(127, 127, 127));
        lblMessageText.setText("Message Text");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMessage)
                    .addComponent(lblMessageText))
                .addContainerGap(251, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMessage)
                .addGap(3, 3, 3)
                .addComponent(lblMessageText)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        btnClose.setBorder(null);
        btnClose.setContentAreaFilled(false);
        btnClose.setFocusable(false);
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        closeNotification();
    }//GEN-LAST:event_btnCloseActionPerformed

    public static enum Type {
        SUCCESS, INFO, WARNING, ERROR
    }
    
    public static enum Location {
        TOP_CENTER, TOP_RIGHT, TOP_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT, BOTTOM_LEFT, CENTER
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblMessageText;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
