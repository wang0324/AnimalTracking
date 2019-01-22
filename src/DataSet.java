import java.util.ArrayList;

public class DataSet {

    //Mouse Location
    private ArrayList<Point> centers;

    //Mouse Distance
    private double totalDistance;

    //Time codes for mouse in a region
    private ArrayList<Integer> timeInRegion;

    private double currentSpeed;

    private double timePassed;

    private final int FPS = 30; //temp variable

    private double diameterOfField;

    private double distanceFromCenterThreshold;

    private double distanceFromWallThreshold;

    public DataSet() {
        centers = new ArrayList<>();
        timeInRegion = new ArrayList<>();

    }

    public DataSet(double diameter) {
        centers = new ArrayList<>();
        timeInRegion = new ArrayList<>();
        this.diameterOfField = diameter;
    }

    /**
     * Adds a center to the data and updates fields after adding
     * @param center Point object of center
     */
    public void addCenter(Point center) {
        if (center != null) {
            centers.add(center);
        }
    }

    /**
     * Get location at certain time
     * @param time
     * @return Center of mouse at a certain moment in time
     */
    public Point getLocation(double time) {
        return null;
    }

    /**
     * Returns speed at a particular time
     * @param time
     * @return Speed at that moment
     */
    public double getCurrentSpeed(double time) {
        return 0;
    }

    /**
     * Finds average speed up to that time
     * @param time
     * @return Average speed up to that moment in time
     */
    public double getAverageSpeed(double time) {
        return 0;
    }

    /**
     * Find average speed in a range of time
     * @param start starting time of range
     * @param end ending time of range
     * @return Average speed in the given range of time
     */
    public double getAverageSpeed(double start, double end) {
        return 0;
    }

    /**
     * Find distance from wall at a particular time
     * @param time
     * @return Distance from Wall at given time
     */
    public double getDistanceFromWall(double time) {
        return 0;
    }

    /**
     * Finds time spent "close to" wall (user defined)
     * @param threshold distance from wall that would count as "close to" it
     * @return time spent close to wall
     */
    public double getTimeSpentNearWall(double threshold) {
        return 0;
    }

    /**
     * Finds time spent "close to" center(user defined)
     * @return time spent close to center
     */
    public double getTimeSpentNearCenter() {
        return 0;
    }

    /**
     * Gets total distance traveled
     * @return total distance traveled
     */
    public double getDistanceTraveled() {
        return 0;
    }


    /**
     * Finds time spent moving within certain speed bounds
     * @param lowerSpeedBound The lower bound of the speed range
     * @param upperSpeedBound The upper bound of the speed range
     * @return Time spent in speed range
     */
    public double getTimeMovingAtSpeed(double lowerSpeedBound, double upperSpeedBound) {
        return 0;
    }

    /**
     * Finds time spent in a user defined region
     * @param center of region
     * @param radius of region
     * @return Time spent in region
     */
    public double getTimeSpentInARegion(double center, double radius) {
        return 0;
    }

    /**
     * Set field diameter
     * @param diameter diameter you want to set the field diameter to
     */
    public void setDiameterOfField(double diameter) {

    }

    /**
     * Sets threshold for distance from center
     * @param threshold threshold for getTimeSpentNearCenter()
     */
    public void setDistanceFromCenterThreshold(double threshold) {
        if (threshold > 0) {
            this.distanceFromCenterThreshold = threshold;
        }
    }

    /**
     * Sets threshold for distance from wall
     * @param threshold threshold for getTimeSpentNearWall()
     */
    public void setDistanceFromWallThreshold(double threshold) {

    }
}
