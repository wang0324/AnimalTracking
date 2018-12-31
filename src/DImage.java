import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.util.Arrays;

public class DImage {
    private PImage img;
    private int width, height;
    private ColorComponents2d channels;

    public DImage(int width, int height) {
        img = new PImage(width, height);
        this.width = width;
        this.height = height;
    }

    public DImage(PImage img) {
        this.img = img;
        this.width = img.width;
        this.height = img.height;
    }

    public DImage(DImage frame) {
        this.width = frame.width;
        this.height = frame.height;
        this.img = new PImage(width, height);
        this.img.loadPixels();
        System.arraycopy(frame.getColorPixelArray(), 0, this.img.pixels, 0, this.img.pixels.length);
        this.img.updatePixels();
    }

    public int[] getColorPixelArray() {
        img.loadPixels();
        return img.pixels;
    }

    public int[][] getColorPixelGrid() {
        return convertTo2dArray( getColorPixelArray(), this.width, this.height );
    }

    public short[] getBWPixelArray() {
        return convertToShortGreyscale( getColorPixelArray() );
    }

    public short[][] getBWPixelGrid() {
        return convertTo2dArray( getBWPixelArray(), this.width, this.height );
    }

    public void setPixels(int[] pixels) {
        img.loadPixels();
        img.pixels = pixels;    // TODO: does this work?
        img.updatePixels();
    }

    public void setPixels(int[][] pixels) {
        img.loadPixels();
        DImage.fill1dArray(pixels, img.pixels);   // TODO: does this work??
        img.updatePixels();
    }

    public void setPixels(short[] pixels) {
        int[] colorPixels = new int[pixels.length];
        fill1dArray(pixels, colorPixels);
        this.setPixels(colorPixels);
    }

    public void setPixels(short[][] pixels) {
        int[] colorPixels = new int[pixels.length*pixels[0].length];
        fill1dArray(pixels, colorPixels);
        this.setPixels(colorPixels);
    }

    // --------------------------------------------------------------------------------------------------------------

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
                pixels[spot++] = color(in.red[r][c], in.green[r][c], in.blue[r][c], in.alpha[r][c]);
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
            pixels[spot++] = color(red[i], green[i], blue[i], alpha[i]);
        }

        return pixels;
    }

    public static int[] combineColorComponents(short[] red, short[] green, short[] blue) {
        // TODO: arg checking
        int[] pixels = new int[red.length];

        int tmp;
        int spot = 0;
        for (int i = 0; i < pixels.length; i++) {
            pixels[spot++] = color(red[i], green[i], blue[i], OPAQUE_ALPHA_VAL);
        }

        return pixels;
    }

    public static int[] convertToRGBGreyscale(int[] pixels) {
        int[] out = new int[pixels.length];

        for (int i = 0; i < out.length; i++) {
            out[i] = getGreyValue(pixels[i]);
        }

        return out;
    }

    public static short[] convertToShortGreyscale(int[] pixels) {
        short[] out = new short[pixels.length];

        for (int i = 0; i < out.length; i++) {
            out[i] = getGreyShortVal(pixels[i]);
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

    public static void fill1dArray(short[] vals, int[] arr) {
        for (int i = 0; i < vals.length; i++) {
            arr[i] = shortToRGBGrey(vals[i]);
        }
    }

    private static int shortToRGBGrey(short val) {
        int num = OPAQUE_ALPHA_VAL;
        num = (num << 8) + val;
        num = (num << 8) + val;
        num = (num << 8) + val;
        return num;
    }

    public PImage getPImage() {
        return img;
    }

    public ColorComponents2d getColorChannels() {
        return DImage.getColorComponents2d(this.getColorPixelGrid());
    }

    public short[][] getRedChannel() {
        if (channels == null) {
            this.channels = getColorChannels();
        }

        return this.channels.red;
    }

    public short[][] getBlueChannel() {
        if (channels == null) {
            this.channels = getColorChannels();
        }

        return this.channels.blue;
    }

    public short[][] getGreenChannel() {
        if (channels == null) {
            this.channels = getColorChannels();
        }

        return this.channels.green;
    }

    public short[][] getAlphaChannel() {
        if (channels == null) {
            this.channels = getColorChannels();
        }

        return this.channels.alpha;
    }

    public void setRedChannel(short[][] red) {
        if (channels == null) {
            this.channels = getColorChannels();
        }

        this.channels.red = red;
        this.setPixels(combineColorComponents(this.channels));
    }

    public void setGreenChannel(short[][] green) {
        if (channels == null) {
            this.channels = getColorChannels();
        }

        this.channels.green = green;
        this.setPixels(combineColorComponents(this.channels));
    }

    public void setBlueChannel(short[][] blue) {
        if (channels == null) {
            this.channels = getColorChannels();
        }

        this.channels.blue = blue;
        this.setPixels(combineColorComponents(this.channels));
    }

    public void setColorChannels(short[][] red, short[][] green, short[][] blue) {
        if (channels == null) {
            this.channels = getColorChannels();
        }
        this.channels.red = red;
        this.channels.green = green;
        this.channels.blue = blue;
        this.setPixels(combineColorComponents(this.channels));
    }

    public void setColorChannels(short[][] red, short[][] green, short[][] blue, short[][] alpha) {
        if (channels == null) {
            this.channels = getColorChannels();
        }
        this.channels.red = red;
        this.channels.green = green;
        this.channels.blue = blue;
        this.channels.alpha = alpha;
        this.setPixels(combineColorComponents(this.channels));
    }

    public void setAlphaChannel(short[][] alpha) {
        if (channels == null) {
            this.channels = getColorChannels();
        }

        this.channels.alpha = alpha;
        this.setPixels(combineColorComponents(this.channels));
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setColorChannels(ColorComponents2d channels) {
        this.setPixels(DImage.combineColorComponents(channels));
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