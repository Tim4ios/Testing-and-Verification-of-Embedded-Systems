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

    /**
     * Description:
     * returnSensorData: Generates a simulated array of sensor data representing a street with parking spots.
     * It includes flags to ensure that the same parking spot is not randomly marked again and simulates a broken sensor
     * that produces out-of-bound values when the car reaches the middle of the street.
     * <p>
     * Pre-condition: "flagZero", "flagOne", and "flagTwo" boolean flags should be initially set to true,
     * indicating that no parking spot has been marked yet.
     * The `length` variable should be set to the total length of the street in meters (e.g., 500 meters),
     * and it should be greater than 0.
     * <p>
     * Post-condition:
     * The method returns an integer array of sensor data, where each element in the array represents the state of a
     * specific meter on the street.
     * The array contains "0" to represent available parking spots and "P" to represent broken sensors that produce
     * out-of-bound values.
     * The flags "flagZero", "flagOne", and "flagTwo" are updated to false once parking spots are marked to ensure
     * they are not marked again.
     * The generated sensor data array represents a street with three parking spots of mutually different sizes, with one
     * parking spot being insufficient for safe parking and the other two being adequate for parking.
     * The sensor data is broken halfway through the scenario, specifically when the car reaches the middle of the street,
     * by marking the remaining elements of the array with 'P' to simulate a broken sensor.
     * <p>
     * Test-cases:
     *
     *
     */
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
