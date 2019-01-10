import processing.core.PApplet;

import javax.swing.*;

public class DrawGrid implements PixelFilter {
    private int n;

    public DrawGrid() {
        n = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of grid lines"));
    }

    @Override
    public DImage processImage(DImage img) {
        // we don't change the input image at all!
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        int r = 0, c = 0;
        int horizontalWidth = original.getHeight()/n;
        int verticalWidth = original.getWidth()/n;
        for (int i= 0; i < n; i++) {
            window.line(0, (i*horizontalWidth),original.getWidth(), (i*horizontalWidth));

            window.line((i*verticalWidth), 0, (i*verticalWidth), original.getHeight());
        }

    }

}