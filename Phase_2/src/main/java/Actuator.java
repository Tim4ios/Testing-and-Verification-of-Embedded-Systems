public class Actuator {

    private int currentPosition;
    final private int oneMeter = 100;

    public Actuator() {
        //Needs to have the same position as the car????
        this.currentPosition = 0;
        // Initialize the actuator with a starting position
    }

    public void drive(AutoParkingCar.context con) {
        // Simulate moving forward
        con.setPosition(con.getPosition() + oneMeter);
    }

    public void reverse(AutoParkingCar.context con) {
        // Simulate moving backward
        con.setPosition(con.getPosition()  - oneMeter);
    }

}
