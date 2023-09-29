import java.util.Arrays;
import java.util.Random;

//Hardcoded the SensorData that we are mocking in the AutoParkingCarTest.
public class SensorData {
    //Flags for make sure that we cannot put a random value on the same "parking spot" again.
    private boolean flagZero = true;
    private boolean flagOne = true;
    private boolean flagTwo = true;

    //Length is the total parking spot meter. (1 Parking spot = 5 meters).
    private final int length = 500;

    public int[] returnSensorData() {
        int[] array = new int[length];
        //Random random = new Random();

        Arrays.fill(array, 1);

        int currentIndex = 0;

        while (currentIndex < length) {

            //Not used since we are mocking the sensorData.
            // Generate a random number (0, 240) to decide the group pattern
            //int groupPattern = random.nextInt(249);


            int parkOne = 4;
            int parkTwo = 14;
            int parkThree = 24;


            /**
             * The sensor data should be mocked such that it represent a street with three parking places of mutually
             * different sizes, one should be not enough for safe parking and the other two enough for parking.
             */
            if (flagZero && currentIndex < 250) {
                // Fill the current group with "0,0,0,0,"
                for (int i = parkOne; i < parkOne + 4; i++) {
                    if (i < parkOne + 4) {
                        array[i] = 0;
                    } else break;

                }
                flagZero = false;

            } else if (flagOne && currentIndex < 250) {
                // Fill the current group with "0,0,0,0,0,"
                for (int i = parkTwo; i < parkTwo + 5; i++) {
                    if (i < parkTwo + 5) {
                        array[i] = 0;
                    } else {

                        break;
                    }
                }
                flagOne = false;

            } else if (flagTwo && currentIndex < 250) {
                // Fill the current group with "0,0,0,0,0,0,"
                for (int i = parkThree; i < parkThree + 6; i++) {
                    if (i < parkThree + 6) {
                        array[i] = 0;
                    } else {
                        break;
                    }
                }
                flagTwo = false;

            }
            if (currentIndex >= 250) {
                for (int i = currentIndex; i < array.length; i++) {
                    array[i] = 'P';
                    //This should simulate the broken halfway of the sensors data.
                    /**
                     * Moreover, one of the sensors should be broken halfway in the middle of the scenario
                     * (i.e., when the car has reached the middle of the street while moving forward) so that it
                     * constantly produces recognizably out-of-bound values
                     */
                }
            }

            currentIndex++;

        }

        return array;
    }
}
