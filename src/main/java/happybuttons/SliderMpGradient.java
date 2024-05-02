package happybuttons;

import java.awt.Color;
import javax.swing.JSlider;

public class SliderMpGradient extends JSlider {

    public Color getTicksColor() {
        return ticksColor;
    }

    public void setTicksColor(Color ticksColor) {
        this.ticksColor = ticksColor;
    }

    public int getTrackSize() {
        return trackSize;
    }

    public void setTrackSize(int trackSize) {
        this.trackSize = trackSize;
    }

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    private Color color1 = new Color(182, 180, 0, 50); // left side of slider gradient
    private Color color2 = new Color(182, 180, 0, 255); // right side of slider gradient
    private Color ticksColor = new Color(0, 0, 0);
    private int trackSize = 5;

    public SliderMpGradient() {
        setUI(new SliderMpGradientUI(this));
    }
}