import processing.core.PApplet;

public class ColorNoiseFilter implements PixelFilter {

    private static final double PROB_TO_ADD = 0.25;

    @Override
    public DImage processImage(DImage img) {
        short [][] redPixels = img.getRedChannel();
        short [][] bluePixels = img.getBlueChannel();
        short [][] greenPixels = img.getGreenChannel();

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                if (Math.random() < PROB_TO_ADD) {
                    redPixels[i][j] = getRandom(256);
                    bluePixels[i][j] = getRandom(256);
                    greenPixels[i][j] = getRandom(256);
                }
            }
        }
        img.setColorChannels(redPixels, greenPixels, bluePixels);



        return img;
    }

    private short getRandom(int max) {
        return (short)(Math.random()*max);
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }

    public void drawOverlay(PApplet window) {

    }
}