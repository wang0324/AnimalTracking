import processing.core.PApplet;

public class FruitNinja implements PixelFilter {

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
                if (difference > 40) {
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
//    BLUR NOT WORKING NEED TO FIX
//    private short[][] blur(short[][] current) {
//        double[][] kernal = {{1.0/9, 1.0/9, 1.0/9}, {1.0/9, 1.0/9, 1.0/9}, {1.0/9, 1.0/9, 1.0/9}};
//        short[][] newimage = new short[current.length][current[0].length];
//        for (int row = 1; row < current.length-1; row++) { // Move the 3x3 region over the image
//            for (int col = 1; col < current[0].length-1; col++) {
//// loop over the 3x3 region
//                double output = 0;
//                for (int r = row-1; r <= row+1; r++) {
//                    for (int c = col-1; c <= col+1; c++) {
//                        double kernelVal = kernal[row-r+1][col-c+1];
//                        int pixelVal = current[row][col];
//                        output += kernelVal*pixelVal;
//                    }
//                }
//                output = output / 9;
//                if (output < 0) output = 0; // clip to 0
//                newimage[row][col] = (short)output;
//            }
//        }
//        return newimage;
//    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }

}