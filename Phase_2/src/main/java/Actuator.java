public class Actuator {
    private int currentPosition;
    final private int oneMeter = 100;
    final private int endOfTheStreet = 50000;
    final private int startOfTheStreet = 0;

    public Actuator() {
        //Needs to have the same position as the car????
        this.currentPosition = 0;
        // Initialize the actuator with a starting position
    }

    public void drive(AutoParkingCar.context con) {
        // Simulate moving forward
        if (con.getPosition() > endOfTheStreet) {
            con.setPosition(startOfTheStreet);
        }
        con.setPosition(currentPosition + oneMeter);
    }

    public void reverse(AutoParkingCar.context con) {
        // Simulate moving backward
        con.setPosition(currentPosition - oneMeter);
    }

}
