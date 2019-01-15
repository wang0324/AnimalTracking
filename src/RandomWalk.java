import processing.core.PApplet;

import javax.swing.*;
import java.util.ArrayList;

public class RandomWalk implements PixelFilter {

    ArrayList <Point> points;
    private int n;
    public RandomWalk() {
        n = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of circles"));
        points = new ArrayList<Point>();
        for (int i = 0; i < n; i++) {
            points.add(new Point(200, 200));
        }
    }
    @Override
    public DImage processImage(DImage img) {
        // we don't change the input image at all!
        return img;
    }
    // I need to blur the image and then after bluring it it shoulld get rid of the noise


    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }

}