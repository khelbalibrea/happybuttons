/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package happybuttons;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ExplorerVideo extends javax.swing.JDialog {
    DefaultListModel<ImageLabel> model = new DefaultListModel<>();
    static MouseListener videoSelect;
    
    public ExplorerVideo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        super.setTitle("Open Video");
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
        
        // Load images and add them to the model with labels
        try {
            for(int i = 1; i <= 3; i++) { // Adjust the range based on your images
                BufferedImage img = ImageIO.read(new File("C:\\Users\\balib\\Documents\\HappyButtons\\dtbs\\thumbnails\\" + i + ".png"));
                String label = "Image " + i; // Change this to your desired label
                model.addElement(new ImageLabel(new ImageIcon(img), label));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        initComponents();
        
        // set frame icon
        ImageIcon imgIcon = new ImageIcon(HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave.png"));
        setIconImage(imgIcon.getImage());
    }
                            
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        listVideos = new JList<>(model);
        btnPlayNow = new javax.swing.JButton();
        btnJustSelect = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        
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
        setMaximumSize(new java.awt.Dimension(700, 600));
        setMinimumSize(new java.awt.Dimension(700, 600));
        setModal(true);
        setPreferredSize(new java.awt.Dimension(700, 600));
        setResizable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(218, Short.MAX_VALUE)
                .addComponent(btnJustSelect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPlayNow)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnJustSelect)
                    .addComponent(btnPlayNow))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ExplorerVideo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExplorerVideo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExplorerVideo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
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

        public ImageLabel(ImageIcon image, String label) {
            this.image = image;
            this.label = label;
        }

        public ImageIcon getImage() {
            return image;
        }

        public String getLabel() {
            return label;
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

                panel.add(imageLabelComponent, BorderLayout.CENTER);
                panel.add(textLabel, BorderLayout.SOUTH);
                
                // Set an empty border (margin) around the panel
                panel.setBorder(new EmptyBorder(15, 15, 15, 15)); // top, left, bottom, right

                // Highlight the panel if selected
                if(isSelected) {
                    panel.setBackground(Color.LIGHT_GRAY);
                }
                else {
                    panel.setBackground(Color.WHITE);
                }

                return panel;
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }
    
    private javax.swing.JButton btnPlayNow;
    private javax.swing.JButton btnJustSelect;
    public JList<ImageLabel> listVideos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jPanel1;
}
