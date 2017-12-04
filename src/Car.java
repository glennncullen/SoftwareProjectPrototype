public class Car {

    private int[] coordinates;
    private int speed;
    private String direction;
    private int[] dimensions;
    private boolean isMoving;
    private int stopLocation;

    public Car(int[] coordinates, int speed, String direction, int[] dimensions, int stopLocation){
        this.coordinates = coordinates;
        this.speed = speed;
        this.direction = direction;
        this.dimensions = dimensions;
        this.stopLocation = stopLocation;
        isMoving = true;
    }

    public int getStopLocation() {
        return stopLocation;
    }

    public void setStopLocation(int stopLocation) {
        this.stopLocation = stopLocation;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }

    public int[] getDimensions() {
        return dimensions;
    }

    public void setDimensions(int[] dimensions) {
        this.dimensions = dimensions;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
