import processing.core.PApplet;

public class AnimalTracker implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {

        /*
        Pseudocode: Run color mask, then blur, then run color mask

        Run convex hull algorithm and find center and tail

        In drawoverlay draw a point over center and tail and display coordinates that
        can be used to find patterns in mouse behavior
         */
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }

}