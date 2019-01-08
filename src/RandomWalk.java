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

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        for (Point p: points) {
            window.fill(255,0,0);
            window.ellipse(p.getX(), p.getY(), 10, 10);
            p.takeRandomStep();
        }
    }

}