/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package happybuttons;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author balib
 */
public class ListVideosFrame extends JDialog {
    public ListVideosFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Image and Label Grid Example");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Create a DefaultListModel to hold the image-label pairs
            DefaultListModel<ImageLabel> model = new DefaultListModel<>();

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

            // Create the JList and set the model
            JList<ImageLabel> imageList = new JList<>(model);
            imageList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            imageList.setVisibleRowCount(-1); // Set to -1 for all rows to be visible
            imageList.setCellRenderer(new ImageLabelRenderer());
            imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // Add the JList to a JScrollPane
            JScrollPane scrollPane = new JScrollPane(imageList);
            frame.add(scrollPane, BorderLayout.CENTER);

            frame.setSize(700, 500);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/3-frame.getSize().height/2);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
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

                // Highlight the panel if selected
                if(isSelected) {
                    panel.setBackground(Color.LIGHT_GRAY);
                    System.out.println(textLabel.getText());
                }
                else {
                    panel.setBackground(Color.WHITE);
                }

                return panel;
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }
}
