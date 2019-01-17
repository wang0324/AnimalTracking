import processing.core.PApplet;

public class AnimalTracker implements PixelFilter {
//TODO: Implement 3 methods
    /*
3 kinds of methods:
    Configuration Methods: (setup ahead of time
    -Set regions of interest (radius of inner egion, threshold for "close to" wall
    - Define cm per pixel
    - Define seconds per frame
    Methods to add data:
    -dataset.addMouseLocation(x,y)
    -dataset.addMouseSpeed(currentSpeed)
    Methods to report data at the end:
    -dataset.getAverageSpeed()
    - dataset.getAverageSpeed(2,10)

    API Design:
    - Easy to learn and memorize
    - Leads to readble code
    - Hard to misuse
    - Easy to extend
    - Complete
 */
    //radius 210
    //Center (308, 234) x, y
    private static short[][] out;
    private static short[][] out2;

    private int avgX = 0, avgY = 0;

    private static double [][] mask = {
            {0.003765, 0.015019, 0.023792, 0.015019, 0.003765},
            {0.015019, 0.059912, 0.094907, 0.059912, 0.015019},
            {0.023792, 0.094907, 0.150342, 0.094907, 0.023792},
            {0.015019, 0.059912, 0.094907, 0.059912, 0.015019},
            {0.003765, 0.015019, 0.023792, 0.015019, 0.003765}
    };

    private short red = 20 ;
    private short green = 20;
    private short blue = 20;

    private static final Point center = new Point(310, 40);

    private static final int THRESHOLD = 30;

    @Override
    public DImage processImage(DImage img) {
    //TODO: Include tail and filter surroundings at the same time
        /*
        Pseudocode: Run color mask, then blur, then run color mask

        Run convex hull algorithm and find center and tail

        In drawoverlay draw a point over center and tail and display coordinates that
        can be used to find patterns in mouse behavior
         */

        int[][] pixels2d = img.getColorPixelGrid();
        out = new short[img.getHeight()][img.getWidth()];
        out2 = new short[img.getHeight()][img.getWidth()];
        DImage.ColorComponents2d image = img.getColorChannels();
        performThreshold(image, out);
        performBlur(out, out2);
        performSecondThreshold(out2);

        double totalX = 0, totalY = 0, count = 0;

        for (int i= 0; i < out2.length; i++) {
            for (int j = 0; j < out2[i].length; j++) {
                if (out2[i][j] > 200) {
                    totalX += j;
                    totalY += i;
                    count++;
                }
            }
        }
        avgX = (int)(totalX/count);
        avgY = (int)(totalY/count);
        // we don't change the input image at all!
//        short[][] blackPixels = new short[img.getHeight()][img.getWidth()];
//
//        for (int i = 0; i < img.getHeight(); i++) {
//            for (int j= 0; j < img.getWidth(); j++) {
//                blackPixels[i][j] = 100;
//            }
//        }
        img.setPixels(out2);

        return img;
    }

    private void performSecondThreshold(short[][] out2) {
        for (int r = 0; r < out.length; r++) {
            for (int c = 0; c < out[0].length; c++) {

                if (out2[r][c] < 220) {
                    out2[r][c] = 0;
                } else {
                    out2[r][c] = 255;
                }
            }
        }

    }

    private void performBlur(short[][] out, short[][] out2) {
        for (int r = 0; r < out.length - mask.length - 1; r++) {
            for (int c = 0; c < out[0].length - mask.length - 1; c++) {

                int outputColor = calculateOutputFrom(r, c, out, mask);
                out2[r][c] = (short) (outputColor / getKernalWeight());
                if (out2[r][c] < 0) out2[r][c] = 0;
                if (out2[r][c] > 255) out2[r][c] = 255;
            }
        }
    }

    private int calculateOutputFrom(int r, int c, short[][] im, double[][] kernal) {
        double out = 0;
        for (int i = 0; i < kernal.length; i++) {
            for (int j = 0; j < kernal[i].length; j++) {
                out += im[r + i][c + j] * kernal[i][j];
            }
        }

        return (int)out;
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

    public double distance(short r1, short c1, short r2, short c2) {
        return Math.sqrt(Math.pow(r1-r2, 2) + Math.pow(c1-c2, 2));
    }

    public double getKernalWeight() {
        double sum = 0;
        for (int i = 0; i< mask.length; i++) {
            for (int j = 0; j < mask[i].length; j++) {
                sum += mask[i][j];
            }
        }
        if (sum <= 0) {
            return 1;
        }
        return sum;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        window.fill(0,0,0);
        window.ellipse(308, 234, 5,5);

        window.fill(255, 0, 0);
        window.ellipse(avgX, avgY, 10, 10);

        window.line(308, 234, avgX, avgY);
    }

}