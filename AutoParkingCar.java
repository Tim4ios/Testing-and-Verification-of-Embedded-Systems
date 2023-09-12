import java.util.Arrays;
import java.util.Random;

public class AutoParkingCar {
    private double[] ultraSoundSensorOne;
    private double[] ultraSoundSensorTwo;
    private int carPos;

    private int oneMeter = 100;

    private int endOfTheStreet = 50000;
    private boolean isParked;

    public AutoParkingCar(double[] sens1, double[] sens2) {
        //Car startValues
        carPos = 0;
        isParked = false;
        ultraSoundSensorOne = sens1;
        ultraSoundSensorTwo = sens2;
    }

    void MoveForward() {
        carPos = carPos + oneMeter;
        isEmpty();
        if(carPos >= endOfTheStreet)
            System.out.println("Car went to far");

    }

    void MoveBackwards() {

    }

    double isEmpty() {
        double Au;
        isNoisy(car);


        return distance;

    }

    boolean isNoisy(double[] sensorData) {

    }

    void Park() {
    }

    void UnPark() {
    }

    void WhereIs() {
    }

    class ParkingData {
        private int position;
        private String situation;

        public ParkingData(int position, String situation) {
            this.position = position;
            this.situation = situation;
        }

        public int getPosition() {
            return position;
        }

        public String getSituation() {
            return situation;
        }
    }


}
