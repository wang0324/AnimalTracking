import processing.core.PApplet;

import javax.xml.crypto.Data;

public class DataExplorer extends PApplet{
    private static Point center = new Point(308, 235);

    private static int radiusInPixels = 208;

    private static double diameterInCm = 79;

    private static double cmPerPixel = diameterInCm/(2*radiusInPixels);

//    public void setup() {
//        size(600, 600);
//    }
//
//    public void draw() {
//        background(200);
//        textSize(24);
//        fill(color(255, 0, 0));
//        text("Total Distance traveled (cm): " + Double.toString(dataset.getDistanceTraveled()), 50, 50);
//    }
    public static void main(String[] args) {
        DataSet dataset = new DataSet();
        //PApplet.main(new String[] { "DataExplorer" });
        dataset.loadDataFromFile("Data\\centerData.csv");
        System.out.println("Total Distance traveled (cm): " + dataset.getDistanceTraveled());

        dataset.setDistanceFromWallThreshold(10);
        System.out.println("Total time spent within 10 cm of wall " + dataset.getTimeSpentNearWall() + " seconds");

        System.out.println("Percentage of time spent withn 10 cm is " + (dataset.getTimeSpentNearWall()/dataset.getTotalTime()));

        dataset.setDistanceFromCenterThreshold(10);
        System.out.println("Total time spent 10 cm within center " + dataset.getTimeSpentNearCenter());

        System.out.println(dataset.convertFrameToSecond((int)dataset.getTimeMovingAtSpeed(-1, 3)));

        System.out.println(dataset);


    }

//    public void settings() {
//        size(600, 600);
//    }
}
