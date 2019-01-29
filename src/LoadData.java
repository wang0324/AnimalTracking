import processing.core.PApplet;

import javax.xml.crypto.Data;

public class LoadData extends PApplet{
    private static Point center = new Point(308, 235);

    private static int radiusInPixels = 208;

    private static double diameterInCm = 79;

    private static double cmPerPixel = diameterInCm/(2*radiusInPixels);

    private static DataSet dataset = new DataSet();
    public void setup() {
        size(600, 600);

        dataset.loadDataFromFile("Data\\data.csv");
    }

    public void draw() {
        background(200);
        textSize(24);
        fill(color(255, 0, 0));
        text("Total Distance traveled (cm): " + Double.toString(dataset.getDistanceTraveled()), 50, 50);
    }
    public static void main(String[] args) {
        PApplet.main(new String[] { "LoadData" });
    }

    public void settings() {
        size(600, 600);
    }
}
