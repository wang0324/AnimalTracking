public class FrameDifferenceFilter implements PixelFilter {
    private static final short THRESHOLD = 5;
    private static final short THRESHOLD2 = 10;
    private DImage lastFrame;

    @Override
    public DImage filter(DImage img) {
        if (lastFrame == null) {
            lastFrame = img;
            System.out.println("no frame");
            return img;
        }

        DImage toDisplay = new DImage(img);

        short[][] current = toDisplay.getBWPixelGrid();
        short[][] last = lastFrame.getBWPixelGrid();

        double rs = 0, cs = 0;
        int pixelsAdded = 0;
        for (int r = 0; r < current.length; r++) {
            for (int c = 0; c < current[0].length; c++) {
                short val = (short) Math.abs(current[r][c] - last[r][c]);

                current[r][c] = val;
            }
        }

        toDisplay.setPixels(current);

        lastFrame = img;

        return toDisplay;
    }
}
