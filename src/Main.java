import processing.core.PApplet;
import processing.video.Capture;
import processing.video.Movie;

import javax.swing.*;
import java.io.File;

/**
 * Side-by-side webcam view for image filtering
 * by David Dobervich
 */
public class Main extends PApplet {
    private static final int WEBCAM_WIDTH = 640;
    private static final int WEBCAM_HEIGHT = 480;
    private static final int WEBCAM = 1;
    private static int displayWidth, displayHeight;

    private static Capture webcam;
    private static Movie movie;

    private static boolean currentlyViewingFilteredImage = false;
    private static int source;
    private DImage frame, filteredFrame, oldFilteredFrame;
    private boolean loading = false;

    private PixelFilter filter;

    public void settings() {
        displayVideoSourceChoiceDialog();
        size(800, 800);
    }

    private void displayVideoSourceChoiceDialog() {
        Object[] options = {"Load mp4 from disk",
                "Use a webcam"};
        this.source = JOptionPane.showOptionDialog(null,
                "What video source would you like to use?",
                "Video source",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        if (source == WEBCAM) {
            displayHeight = WEBCAM_HEIGHT;
            displayWidth = WEBCAM_WIDTH;
        } else {
            String userDirLocation = System.getProperty("user.dir");
            File userDir = new File(userDirLocation);
            JFileChooser fc = new JFileChooser(userDir);
            int returnVal = fc.showOpenDialog(null);
            File file = fc.getSelectedFile();

            String moviePath = file.getAbsolutePath();

            this.movie = new Movie(this, moviePath);
        }
    }

    public void setup() {
        imageMode(PApplet.CENTER);
        if (source != WEBCAM && movie == null) {
            System.err.println("No mp4 file loaded, switching to webcam as video source");
            source = WEBCAM;
        } else if (source != WEBCAM && movie != null) {
            movie.play();
        } else if (source == WEBCAM && webcam == null) {
            webcam = new Capture(this, WEBCAM_WIDTH, WEBCAM_HEIGHT);
            webcam.start();
        }
    }

    public void draw() {
        // TODO: re-organize this so currentlyViewingFiltered image is top level if
        if (frame != null && filteredFrame != null) {
            if (!loading) {
                draw(frame, filteredFrame);
            } else if (oldFilteredFrame != null){
                draw(frame, oldFilteredFrame);
            }
        }
    }

    public void draw(DImage original, DImage filtered) {
        if (currentlyViewingFilteredImage) {
            drawFrame(filtered, width/2, height/2);
        } else {
            drawFrame(original, width / 2, height / 2);
        }
    }

    public void drawFrame(DImage f, int x, int y) {
        image(f.getPImage(), x, y);

        if (filter != null) {
            pushMatrix();
            translate(x, y);

            filter.drawOverlay(this);

            popMatrix();
        }
    }

    public void captureEvent(Capture c) {
        loading = true;
        oldFilteredFrame = filteredFrame;

        c.read();
        frame = new DImage(c.get());
        filteredFrame = new DImage(frame);
        filteredFrame = runFilters(filteredFrame);

        loading = false;
    }

    public void movieEvent(Movie m) {
        loading = true;
        m.read();
        frame = new DImage(m.get());
        filteredFrame = new DImage(frame);

        filteredFrame = runFilters(filteredFrame);

        loading = false;
    }

    private DImage runFilters(DImage frameToFilter) {
        if (filter != null) return filter.processImage(frameToFilter);
        return frameToFilter;
    }

    public void keyReleased() {
        if (key == 'f' || key == 'F') {
            this.filter = loadNewFilter();
        }

        if (key == 's' || key == 'S') {
            currentlyViewingFilteredImage = !currentlyViewingFilteredImage;
        }
    }

    private PixelFilter loadNewFilter() {
        String name = JOptionPane.showInputDialog("Type the name of your processImage class");
        PixelFilter f = null;
        try {
            Class c = Class.forName(name);
            f = (PixelFilter)c.newInstance();
        } catch (Exception e) {
            System.err.println("Something went wrong when instantiating your processImage! " + e.getMessage());
            System.err.println(e.getMessage());
        }

        return f;
    }

    public static void main(String[] args) {
        PApplet.main("Main", args);
    }
}

