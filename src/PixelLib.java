import java.awt.*;

public class PixelLib {
    public static final int OPAQUE_ALPHA_VAL = 255;
    public static final int TRANSPARENT_ALPHA_VAL = 0;

    public static ColorComponents2d getColorComponents2d(int[][] rgbPixels) {
        int h = rgbPixels.length;
        int w = rgbPixels[0].length;
        // TODO: arg check not size 0

        ColorComponents2d out = new ColorComponents2d(w, h);
        int spot = 0;    // index into pix

        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                int num = rgbPixels[r][c];
                out.blue[r][c] = (short) (num & 255);
                num = num >> 8;
                out.green[r][c] = (short) (num & 255);
                num = num >> 8;
                out.red[r][c] = (short) (num & 255);
                num = num >> 8;
                out.alpha[r][c] = (short) (num & 255);
            } // for c
        } // for r

        return out;
    }

    public static ColorComponents1d getColorComponents1d(int[][] rgbPixels) {
        int h = rgbPixels.length;
        int w = rgbPixels[0].length;
        // TODO: arg check not size 0

        ColorComponents1d out = new ColorComponents1d(w, h);
        int length = h*w;
        int spot = 0;    // index into pix

        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                int num = rgbPixels[r][c];
                out.blue[spot] = (short) (num & 255);
                num = num >> 8;
                out.green[spot] = (short) (num & 255);
                num = num >> 8;
                out.red[spot] = (short) (num & 255);
                num = num >> 8;
                out.alpha[spot] = (short) (num & 255);
                spot++;
            } // for c
        } // for r

        return out;
    }

    public static ColorComponents2d getColorComponents2d(int[] rgbPixels, int w, int h) {
        ColorComponents2d out = new ColorComponents2d(w, h);
        int spot = 0;    // index into pix

        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                int num = rgbPixels[spot++];
                out.blue[r][c] = (short) (num & 255);
                num = num >> 8;
                out.green[r][c] = (short) (num & 255);
                num = num >> 8;
                out.red[r][c] = (short) (num & 255);
                num = num >> 8;
                out.alpha[r][c] = (short) (num & 255);
            } // for c
        } // for r

        return out;
    }

    public static ColorComponents1d getColorComponents1d(int[] rgbPixels, int w, int h) {
        ColorComponents1d out = new ColorComponents1d(h, w);
        int length = w*h;
        for (int i=0; i < length; i++) {
            int num = rgbPixels[i];
            out.blue[i] = (short) (num & 255);
            num = num >> 8;
            out.green[i] = (short) (num & 255);
            num = num >> 8;
            out.red[i] = (short) (num & 255);
            num = num >> 8;
            out.alpha[i] = (short) (num & 255);
        }

        return out;
    }

    public static int[] combineColorComponents(ColorComponents2d in) {
        int  pixheight = in.height;
        int pixwidth = in.width;
        int[] pixels = new int[pixwidth * pixheight];

        int tmp;
        int spot = 0;
        for (int r = 0; r < pixheight; r++) {
            for (int c = 0; c < pixwidth; c++) {
                pixels[spot++] = PixelLib.color(in.red[r][c], in.green[r][c], in.blue[r][c], in.alpha[r][c]);
            }
        }

        return pixels;
    }

    public static int[] combineColorComponents(short[] red, short[] green, short[] blue, short[] alpha) {
        // TODO: arg checking
        int[] pixels = new int[red.length];

        int tmp;
        int spot = 0;
        for (int i = 0; i < pixels.length; i++) {
            pixels[spot++] = PixelLib.color(red[i], green[i], blue[i], alpha[i]);
        }

        return pixels;
    }

    public static int[] combineColorComponents(short[] red, short[] green, short[] blue) {
        // TODO: arg checking
        int[] pixels = new int[red.length];

        int tmp;
        int spot = 0;
        for (int i = 0; i < pixels.length; i++) {
            pixels[spot++] = PixelLib.color(red[i], green[i], blue[i], OPAQUE_ALPHA_VAL);
        }

        return pixels;
    }

    public static int[] convertToRGBGreyscale(int[] pixels) {
        int[] out = new int[pixels.length];

        for (int i = 0; i < out.length; i++) {
            out[i] = PixelLib.getGreyValue(pixels[i]);
        }

        return out;
    }

    public static short[] convertToShortGreyscale(int[] pixels) {
        short[] out = new short[pixels.length];

        for (int i = 0; i < out.length; i++) {
            out[i] = PixelLib.getGreyShortVal(pixels[i]);
        }

        return out;
    }

    public static short getGreyShortVal(int color) {
        int num = color;
        int blue = num & 255;
        num = num >> 8;
        int green = num & 255;
        num = num >> 8;
        int red = num & 255;
        num = num >> 8;
        int alpha = num & 255;
        int black = (red + green + blue) / 3;
        return (short)black;
    }

    public static int getOpaqueGreyValue(int color) {
        int num = color;
        int blue = num & 255;
        num = num >> 8;
        int green = num & 255;
        num = num >> 8;
        int red = num & 255;
        num = num >> 8;
        int alpha = num & 255;
        int black = (red + green + blue) / 3;
        num = OPAQUE_ALPHA_VAL;
        num = (num << 8) + black;
        num = (num << 8) + black;
        num = (num << 8) + black;
        return num;
    }

    public static int getGreyValue(int color) {
        int num = color;
        int blue = num & 255;
        num = num >> 8;
        int green = num & 255;
        num = num >> 8;
        int red = num & 255;
        num = num >> 8;
        int alpha = num & 255;
        int black = (red + green + blue) / 3;
        num = alpha;
        num = (num << 8) + black;
        num = (num << 8) + black;
        num = (num << 8) + black;
        return num;
    }

    public static int color(int red, int green, int blue) {
        return color(red, green, blue, OPAQUE_ALPHA_VAL);
    }

    public static int color(int red, int green, int blue, int alpha) {
        int tmp = alpha;
        tmp = tmp << 8;
        tmp += red;
        tmp = tmp << 8;
        tmp += green;
        tmp = tmp << 8;
        tmp += blue;
        return tmp;
    }

    public static int getRed(int color) {
        return (color >> 16) & 255;
    }

    public static int getGreen(int color) {
        return (color >> 8) & 255;
    }

    public static int getBlue(int color) {
        return (color & 255);
    }

    public static int getAlpha(int color) {
        return (color >> 24) & 255;
    }

    public static int[][] convertTo2dArray(int[] pixels, int w, int h) {
        int[][] out = new int[h][w];
        int loc = 0;
        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                out[r][c] = pixels[loc];
                loc++;
            }
        }

        return out;
    }

    public static short[][] convertTo2dArray(short[] pixels, int w, int h) {
        short[][] out = new short[h][w];
        int loc = 0;
        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                out[r][c] = pixels[loc];
                loc++;
            }
        }

        return out;
    }

    public static void fill1dArray(int[][] vals, int[] arr) {
        if (arr.length != vals.length*vals[0].length) {
            System.err.println("in fill1dArray: different number of elements in 2d and 1d arrays");
        }

        int loc = 0;
        for (int r = 0; r < vals.length; r++) {
            for (int c = 0; c < vals[r].length; c++) {
                arr[loc++] = vals[r][c];
            }
        }

        // no return necessary because we just changed the values in arr
    }

    public static void fill1dArray(short[][] vals, int[] arr) {
        if (arr.length != vals.length*vals[0].length) {
            System.err.println("in fill1dArray: different number of elements in 2d and 1d arrays");
        }

        int loc = 0;
        for (int r = 0; r < vals.length; r++) {
            for (int c = 0; c < vals[r].length; c++) {
                arr[loc++] = shortToRGBGrey(vals[r][c]);
            }
        }

        // no return necessary because we just changed the values in arr
    }

    private static int shortToRGBGrey(short val) {
        int num = OPAQUE_ALPHA_VAL;
        num = (num << 8) + val;
        num = (num << 8) + val;
        num = (num << 8) + val;
        return num;
    }

    // Data transfer object
    public static class ColorComponents2d {
        public int width, height;
        public short[][] red, green, blue, alpha;

        public ColorComponents2d(int width, int height) {
            this.width = width;
            this.height = height;
            red = new short[height][width];
            green = new short[height][width];
            blue = new short[height][width];
            alpha = new short[height][width];
        }
    }

    // Data transfer object
    public static class ColorComponents1d {
        public short[] red, green, blue, alpha;
        public int width, height;

        public ColorComponents1d(int width, int height) {
            this.width = width;
            this.height = height;
            int length = width*height;
            red = new short[length];
            green = new short[length];
            blue = new short[length];
            alpha = new short[length];
        }
    }
}
