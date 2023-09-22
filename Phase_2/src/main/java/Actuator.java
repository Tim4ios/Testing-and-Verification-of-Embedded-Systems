public class Actuator {
    private int currentPosition;


    public Actuator() {
        // Initialize the actuator with a starting position
        this.currentPosition = 0;
    }

    public void moveForward(int distance) {
        // Simulate moving forward
        currentPosition += distance;
    }

    public void moveBackward(int distance) {
        // Simulate moving backward
        currentPosition -= distance;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
}
