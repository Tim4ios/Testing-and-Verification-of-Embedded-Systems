//Hardcoded the Actuator that we are mocking in the AutoParkingCarTest.
public class Actuator {
    private int currentPosition;
    final private int oneMeter = 100;
    final private int endOfTheStreet = 50000;
    final private int startOfTheStreet = 0;
    AutoParkingCar.context ActContext;

    //Creating a constructor for Actuator so that we can update the context and the position of the car.
    public Actuator(AutoParkingCar.context ActContext) {
        this.ActContext = ActContext;
        this.currentPosition = ActContext.getPosition();
    }

    // Simulate moving forward
    public void drive() {
        //Checking pre-condition that car cannot move beyond street
        if (currentPosition >= (endOfTheStreet - oneMeter)) {
            AutoParkingCar.counter = startOfTheStreet;
            currentPosition = startOfTheStreet;
        } else {
            //Move forward one meter
            currentPosition += oneMeter;
            AutoParkingCar.counter++;
        }
    }

    // Simulate moving backward
    public void reverse() {
        //Checking pre-condition that car cannot move back beyond street
        if (currentPosition <= startOfTheStreet)
            currentPosition = startOfTheStreet;
        else {
            //Move backwards one meter
            currentPosition -= oneMeter;
            AutoParkingCar.counter--;
        }
    }

}
