import processing.core.PApplet;

public class AnimalTracker implements PixelFilter {

    private static short[][] out;
    private static short[][] out2;

    private static final int THRESHOLD = 100;

    @Override
    public DImage processImage(DImage img) {

        /*
        Pseudocode: Run color mask, then blur, then run color mask

        Run convex hull algorithm and find center and tail

        In drawoverlay draw a point over center and tail and display coordinates that
        can be used to find patterns in mouse behavior
         */

        int[][] pixels2d = img.getColorPixelGrid();

        DImage.ColorComponents2d image = img.getColorChannels();
        performThreshold(image, out);
        performBlur(out, out2);
        performSecondThreshold(out2);
        // we don't change the input image at all!
        short[][] blackPixels = new short[img.getHeight()][img.getWidth()];

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j= 0; j < img.getWidth(); j++) {
                blackPixels[i][j] = 100;
            }
        }

        return img;
    }

    private void performBlur(short[][] out, short[][] out2) {
        for (int r = 0; r < out.length - kernel.length - 1; r++) {
            for (int c = 0; c < out[0].length - kernel.length - 1; c++) {

                int outputColor = calculateOutputFrom(r, c, out, kernel);
                out2[r][c] = (short) (outputColor / kernelWeight);
                if (out2[r][c] < 0) out2[r][c] = 0;
                if (out2[r][c] > 255) out2[r][c] = 255;
            }
        }
    }

    private int calculateOutputFrom(int r, int c, short[][] im, short[][] kernal) {
        int out = 0;
        for (int i = 0; i < kernal.length; i++) {
            for (int j = 0; j < kernal[i].length; j++) {
                out += im[r + i][c + j] * kernal[i][j];
            }
        }

        return out;
    }

    private void performThreshold(DImage.ColorComponents2d img, short[][] out) {
        for (int r = 0; r < out.length; r++) {
            for (int c = 0; c < out[0].length; c++) {
                double dist = distance(red, img.red[r][c], green, img.green[r][c], blue, img.blue[r][c]);
                if (dist > THRESHOLD) {
                    out[r][c] = 0;
                } else {
                    out[r][c] = 255;
                }
            }
        }
    }

    public double distance(short r1, short r2, short g1, short g2, short b1, short b2) {
        int dr = r2 - r1;
        int dg = g2 - g1;
        int db = b2 - b1;

        return Math.sqrt(dr * dr + dg * dg + db * db);
    }


    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }

}