import processing.core.PApplet;

public interface PixelFilter {
    public DImage processImage(DImage img);
    public void drawOverlay(PApplet window, DImage original, DImage filtered);
}