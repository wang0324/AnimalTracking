import processing.core.PApplet;

public class DoNothingFilter implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        // don't change the input array at all!
        return img;
    }

    @Override
    public void drawOverlay(PApplet window) {
        window.fill(255, 0, 0);
        window.ellipse(0, 0, 10, 10);
    }
}