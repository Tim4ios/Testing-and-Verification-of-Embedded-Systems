import java.util.Arrays;
import java.util.Random;

public class SensorData {
    boolean flagZero = true;
    boolean flagOne = true;
    boolean flagTwo = true;

    public int[] returnSensorData(int length) {
        int[] array = new int[length];
        Random random = new Random();

        Arrays.fill(array, 1);

        int currentIndex = 0;

        while (currentIndex < length) {

            // Generate a random number (0, 240) to decide the group pattern
            int groupPattern = random.nextInt(240);

            if (flagZero && currentIndex < 250) {
                // Fill the current group with "0,0,0,0,"
                for (int i = groupPattern; i < groupPattern + 4; i++) {
                    if (i < groupPattern + 4) {
                        array[i] = 0;
                    } else break;

                }
                flagZero = false;

            } else if (flagOne && currentIndex < 250) {
                // Fill the current group with "0,0,0,0,0,"
                for (int i = groupPattern; i < groupPattern + 5; i++) {
                    if (i < groupPattern + 5) {
                        array[i] = 0;
                    } else {

                        break;
                    }
                }
                flagOne = false;

            } else if (flagTwo && currentIndex < 250) {
                // Fill the current group with "0,0,0,0,0,0,"
                for (int i = groupPattern; i < groupPattern + 6; i++) {
                    if (i < groupPattern + 6) {
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
                }
            }

            currentIndex++;

        }

        return array;
    }
}
