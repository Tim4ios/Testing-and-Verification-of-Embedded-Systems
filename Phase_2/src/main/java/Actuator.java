public class Actuator {
    private int currentPosition;

    public Actuator() {
        // Initialize the actuator with a starting position
        this.currentPosition = 0;
    }

    public void drive(AutoParkingCar.context con) {
        // Simulate moving forward
        con.setPosition(currentPosition-100);    }

    public void reverse(AutoParkingCar.context con) {
        // Simulate moving backward
        con.setPosition(currentPosition-100);
    }

}
