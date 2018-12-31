import processing.core.PApplet;

public class RemoveRedFilter implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        DImage.ColorComponents2d channels = img.getColorChannels();

        for (int r = 0; r < img.getHeight(); r++) {
            for (int c = 0; c < img.getWidth(); c++) {
                channels.red[r][c] = 0;         // set all pixels in red channel to 0
            }
        }

        img.setColorChannels(channels);
        return img;
    }

    @Override
    public void drawOverlay(PApplet window) {
        window.fill(255, 0, 0);
        window.ellipse(0, 0, 10, 10);
    }
}