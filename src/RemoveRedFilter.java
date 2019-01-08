import processing.core.PApplet;

public class RemoveRedFilter implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        // we don't change the input image at all!
        short[][] blackPixels = new short[img.getHeight()][img.getWidth()];

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j= 0; j < img.getWidth(); j++) {
                blackPixels[i][j] = 100;
            }
        }
        img.setRedChannel(blackPixels);
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }

}