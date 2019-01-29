import processing.core.PApplet;

public class FrameDifference implements PixelFilter {

    DImage previousImage;

    @Override
    public DImage processImage(DImage img) {
        if (previousImage == null) {
            previousImage = new DImage(img);
        }

        short[][] current = img.getBWPixelGrid();
        short[][] previous = previousImage.getBWPixelGrid();

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                int difference = Math.abs(current[i][j] - previous[i][j]);
                if (difference > 10) {
                    current[i][j] = 255;
                }
                else {
                    current[i][j] = 0;
                }
            }
           // current = blur(current);
        }

        previousImage = new DImage(img);

        img.setPixels(current);

        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }

}