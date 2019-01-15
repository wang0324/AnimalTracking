import processing.core.PApplet;

public class BWFilter implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        short [][] pixels = img.getBWPixelGrid();
        img.setPixels(pixels                                          );
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }

    public void drawOverlay(PApplet window) {

    }
}