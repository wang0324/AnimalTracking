import processing.core.PApplet;

public class RemoveRedFilter implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        short [][] blackPixels = new short[img.getHeight()][img.getWidth()];
        img.setRedChannel(blackPixels);

        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }

    public void drawOverlay(PApplet window) {

    }
}