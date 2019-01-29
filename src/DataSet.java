import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataSet {

    //Mouse Location
    private ArrayList<Point> centers;

    //Mouse Distance
    private double totalDistance = 0;

    //Time codes for mouse in a region
    private ArrayList<Integer> timeInRegion;

    private final int FPS = 30; //temp variable

    private double radiusOfField = 205;

    private double distanceFromCenterThreshold;

    private double distanceFromWallThreshold;

    private double framesPassed = 0;

    public Point centerOfField = new Point(308, 234);

    public Point previousCenter;

    public DataSet() {
        centers = new ArrayList<>();
        timeInRegion = new ArrayList<>();

    }

    /**
     * Adds a center to the data and updates fields after adding
     *
     * @param center Point object of center
     */
    public void addCenter(Point center) {
        if (centers != null && previousCenter != null) {
            centers.add(center);
            this.totalDistance += calculateDistance(center, previousCenter);
            previousCenter = new Point(center.getRow(), center.getCol());
            ++this.framesPassed;
        } else {
            centers.add(center);
            previousCenter = new Point(center.getRow(), center.getCol());
            ++this.framesPassed;
        }
    }

    public void addCenter(double r, double c) {
        addCenter(new Point(r, c));
    }

    /**
     * Get location at certain time
     *
     * @param time in seconds
     * @return Center of mouse at a certain moment in time
     */
    public Point getLocationAtTime(double time) {
        int frame = convertSecondToFrame(time);
        return centers.get(frame);
    }

    /**
     * Returns speed at a particular time
     *
     * @param time
     * @return Speed at that moment
     */
    public double getSpeedAtTime(double time) {
        int frames = convertSecondToFrame(time);
        return calculateSpeed(calculateDistance(centers.get(frames), centers.get(frames - 1)), time);
    }

    /**
     * Find average speed in a range of time
     *
     * @param start starting time of range
     * @param end   ending time of range
     * @return Average speed in the given range of time
     */
    public double getAverageSpeed(double start, double end) {
        int startingFrame = convertSecondToFrame(start);
        int endingFrame = convertSecondToFrame(end);
        return calculateSpeed(calculateDistance(centers.get(startingFrame), centers.get(endingFrame)), end - start);
    }

    /**
     * Finds average speed up to that time
     *
     * @param time
     * @return Average speed up to that moment in time
     */
    public double getAverageSpeed(double time) {
        return getAverageSpeed(0, time);
    }

    /**
     * Find distance from wall at a particular time
     *
     * @param time
     * @return Distance from Wall at given time
     */
    public double getDistanceFromWall(double time) {
        int frame = convertSecondToFrame(time);
        Point p = centers.get(frame);
        return calculateDistance(p, centerOfField) - radiusOfField;
    }

    /**
     * Finds time spent "close to" wall (user defined)
     *
     * @param threshold distance from wall that would count as "close to" it
     * @return time spent close to wall
     */
    public double getTimeSpentNearWall(double threshold) {
        int frames = 0;
        for (int i = 0; i < centers.size(); i++) {
            Point curPoint = centers.get(i);

            double distance = calculateDistance(curPoint, centerOfField);

            if (distance < radiusOfField - distanceFromCenterThreshold) {
                frames++;
            }
        }
        return convertFrameToSecond(frames);
    }

    /**
     * Finds time spent "close to" center(user defined)
     *
     * @return time spent close to center
     */
    public double getTimeSpentNearCenter() {
        int frames = 0;
        for (int i = 0; i < centers.size(); i++) {
            Point curPoint = centers.get(i);

            double distance = calculateDistance(curPoint, centerOfField);

            if (distance < distanceFromCenterThreshold) {
                frames++;
            }
        }
        return convertFrameToSecond(frames);
    }

    /**
     * Gets total distance traveled
     *
     * @return total distance traveled
     */
    public double getDistanceTraveled() {
        return convertPixelsToCentimeters(totalDistance);
    }


    /**
     * Finds time spent moving within certain speed bounds
     * @param lowerSpeedBound The lower bound of the speed range
     * @param upperSpeedBound The upper bound of the speed range
     * @return Time spent in speed range
     */
    /**
     * Finds time spent moving within certain speed bounds
     *
     * @param lowerSpeedBound The lower bound of the speed range
     * @param upperSpeedBound The upper bound of the speed range
     * @return Time spent in speed range
     */
    public double getTimeMovingAtSpeed(double lowerSpeedBound, double upperSpeedBound) {
        double time = 0;

        //TODO: Implement convertSpeedToPixelsPerFrame
        Point pastPoint = centers.get(0);
        double lower = convertSpeedToPixelsPerFrame(lowerSpeedBound);
        double upper = convertSpeedToPixelsPerFrame(upperSpeedBound);

        for (int i = 1; i < centers.size(); i++) {
            Point curPoint = centers.get(i);

            double distance = calculateDistance(curPoint, pastPoint);

            if (lower < distance && distance < upper) {
                time++;
            }

            pastPoint = curPoint;
        }
        return convertSecondToFrame(time);
    }

    // 79 cm to 420 pixels
    private double convertSpeedToPixelsPerFrame(double speed) {
        return (speed*(1.0/FPS)*(420.0/79.0));
    }

    /**
     * Finds time spent in a user defined region
     *
     * @param center of region
     * @param radius of region
     * @return Time spent in region
     */
    public double getTimeSpentInARegion(Point center, double radius) {
        int time = 0;
        for (Point point : centers) {
            if (calculateDistance(point, center) < radius) {
                time++;
            }
        }
        return convertSecondToFrame(time);
    }

    /**
     * Set field radius
     *
     * @param radius radius you want to set the field diameter to
     */
    public void setRadiusOfField(double radius) {
        if (radius > 0) {
            radiusOfField = radius;
        }
    }

    /**
     * Sets threshold for distance from center
     *
     * @param threshold threshold for getTimeSpentNearCenter()
     */
    public void setDistanceFromCenterThreshold(double threshold) {
        if (threshold > 0) {
            this.distanceFromCenterThreshold = threshold;
        }
    }

    /**
     * Sets threshold for distance from wall
     *
     * @param threshold threshold for getTimeSpentNearWall()
     */
    public void setDistanceFromWallThreshold(double threshold) {
        if (threshold > 0) {
            distanceFromWallThreshold = threshold;
        }
    }

    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private double calculateDistance(Point a, Point b) {
        return calculateDistance(a.getCol(), a.getRow(), b.getCol(), b.getRow());
    }

    private int convertSecondToFrame(double time) {
        return (int)(time * FPS);
    }

    private double convertFrameToSecond(int frames) {
        return (double)(frames/FPS);
    }

    private double calculateSpeed(double distance, double time) {
        return distance / time;
    }

    private double convertPixelsToCentimeters(double pixels) {
        return pixels*(25.0/400.0);
    }

    private void writeDataToFile(String filePath, String data) {
        File outFile = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            writer.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readFileAsString(String filepath) {
        StringBuilder output = new StringBuilder();

        try (Scanner scanner = new Scanner(new File(filepath))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                output.append(line + System.getProperty("line.separator"));
            }

        } catch (IOException e){
            e.printStackTrace();
        }
        return output.toString();
    }

    public void saveDataToFile(String file) {
        String data = "";
        for (Point p: centers) {
            data += (Double.toString(p.getCol()) + ", " + Double.toString(p.getRow()) + "\n");
        }
        writeDataToFile("Data\\data.csv", data);
    }
    public void loadDataFromFile(String file){
        String fileData = readFileAsString(file);
        for (String pointString: fileData.split(System.getProperty("line.separator"))){
            pointString.trim();
            String coordinateString[] = pointString.split(",");
            double x = Double.parseDouble(coordinateString[0]);
            double y = Double.parseDouble(coordinateString[1]);
            Point temp = new Point(y,x);
            centers.add(temp);
        }
    }

}
