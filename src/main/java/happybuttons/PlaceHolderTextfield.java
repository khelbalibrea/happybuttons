package happybuttons;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PlaceHolderTextfield extends JTextField {
    private String placeholder;

    public PlaceHolderTextfield(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(getText().isEmpty()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.GRAY);
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            g2.drawString(placeholder, getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
            g2.dispose();
        }
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
}