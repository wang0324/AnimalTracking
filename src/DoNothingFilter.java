public class DoNothingFilter implements PixelFilter {

    @Override
    public DImage filter(DImage img) {
        // don't change the input array at all!
        return img;
    }
}
