import java.util.Arrays;
import java.util.Random;

public class AutoParkingCar {
    private int[] ultraSoundSensorOne;
    private int[] ultraSoundSensorTwo;
    private int carPos;

    public AutoParkingCar(int[] sens1, int[] sens2) {
        //Car startValues
        double carPos = 0;
        boolean isParked = false;
        ultraSoundSensorOne = sens1;
        ultraSoundSensorTwo = sens2;
    }

    void MoveForward() {
        int oneMeter = 100;
        carPos = carPos + oneMeter;
        isEmpty();


    }

    void MoveBackwards() {

    }

    int isEmpty() {
        int distance = 0;
        int context = carPos / 100;
        int[] fiveSensValuesOne = new int[5];
        int[] fiveSensValuesTwo = new int[5];

        // Populate the sensor value arrays
        for (int i = 0; i < 5; i++) {
            fiveSensValuesOne[i] = ultraSoundSensorOne[context];
            fiveSensValuesTwo[i] = ultraSoundSensorTwo[context];
            context++;
        }

        if (isNoisy(fiveSensValuesOne)) {
            if (isNoisy(fiveSensValuesTwo)) {
                // Both sensors are unreliable
                System.out.println("Both sensors are unreliable");
                return -1;
            } else {
                // First sensor is noisy
                for (int value : fiveSensValuesTwo) {
                    distance += value;
                }
                System.out.println("First sensor is noisy");
            }
        } else if (isNoisy(fiveSensValuesTwo)) {
            // Second sensor is noisy
            for (int value : fiveSensValuesOne) {
                distance += value;
            }
            System.out.println("Second sensor is noisy");
        } else {
            // Both sensors are reliable
            for (int value : fiveSensValuesOne) {
                distance += value;
            }
            for (int value : fiveSensValuesTwo) {
                distance += value;
            }
            System.out.println("Both sensors are reliable");
        }

        // Calculate and return the average distance
        return distance / 10; // average of 10 values (5 from each sensor)
    }

    boolean isNoisy(int[] sensorData){
        int countOfOutliars = 0;

        for (int i=1; i < sensorData.length; i++) {
            if(100 < Math.abs((int)(sensorData[i-1] - sensorData[i]))){
                countOfOutliars++;
            }
        }

        return  countOfOutliars > 2;
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
