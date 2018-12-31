import processing.core.PApplet;

public class ColorNoiseFilter implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] blue = img.getBlueChannel();

        for (int r = 0; r < img.getHeight(); r++) {
            for (int c = 0; c < img.getWidth(); c++) {
                if (r % 2 == c % 2) {
                    red[r][c] = (short)(Math.random()*256);
                } else {
                    blue[r][c] = (short)(Math.random()*256);
                }
            }
        }

        img.setRedChannel(red);
        img.setBlueChannel(blue);
        return img;
    }

    @Override
    public void drawOverlay(PApplet window) {

    }
}