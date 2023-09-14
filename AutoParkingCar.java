import java.util.Random;

public class AutoParkingCar {
    public context con;
    private int[] ultraSoundSensorOne;
    private int[] ultraSoundSensorTwo;
    private int oneMeter = 100;

    private boolean parkingSpot = false;

    public int counter = 0;
    private int[] parkingPlace;
    private int carPos = 0;
    private int endOfTheStreet = 50000;
    private int startOfStreet = 0;

    public static class context {
        private int position;
        private boolean situation;

        public context(int position, boolean situation) {
            this.position = position;
            this.situation = situation;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public void setSituation(boolean situation) {
            this.situation = situation;
        }

        public int getPosition() {
            return position;
        }

        public boolean getSituation() {
            return situation;
        }
    }


    public AutoParkingCar(int[] sens1, int[] sens2, context con) {
        this.con = con;
        con.position = 0;
        con.situation = false;
        ultraSoundSensorOne = sens1;
        ultraSoundSensorTwo = sens2;
        parkingPlace = generateRandomParking(500, 5);

    }

    public int[] generateRandomParking(int length, int groupSize) {
        int[] array = new int[length];
        Random random = new Random();

        int currentIndex = 0;

        while (currentIndex < length) {
            int groupStartIndex = currentIndex;
            int groupEndIndex = Math.min(currentIndex + groupSize, length);

            // Generate a random number (0 or 1) for each element in the current group
            int randomValue = random.nextInt(2);

            // Fill the current group with the same random value
            for (int i = groupStartIndex; i < groupEndIndex; i++) {
                array[i] = randomValue;
            }

            // Move to the next group
            currentIndex = groupEndIndex;
        }
        return array;
    }

    /**
     * Description:
     * MoveForward: : This method moves the car 1 meter forward, queries the two sensors through the isEmpty method
     * described below and returns a data structure that contains the current position of the car, and the situation
     * of the detected parking places up to now. The car cannot be moved forward beyond the end of the street.
     * <p>
     * Pre-condition: Car is not at the end of the street
     * <p>
     * Post-condition: Car has moved 1 meter forward and has returned a data structure containing the current position of
     * the car and the situation
     * <p>
     * Test-cases:
     * MoveForwardFromStartTest
     * MoveForwardFromEndOfStreet
     * ...?
     */
    public context MoveForward() {
        if (con.position >= endOfTheStreet) {
            //start over from start?

            con.position = startOfStreet;
            return null;
        } else {
            con.position = con.position + oneMeter;
            counter++;
            isEmpty();
        }


        return con;
    }


    /**
     * Description:
     * MoveBackward: The same as above; only it moves the car 1 meter backwards. The car cannot be moved behind if it is
     * already at the beginning of the street.
     * <p>
     * Pre-condition: Car is not at the beginning of the street
     * <p>
     * Post-condition: Car has moved 1 meter backward and has returned a data structure containing the current position of
     * the car and the situation.
     * <p>
     * Test-cases:
     * MoveBackwardsFromStartTest
     * MoveBackwardsFromEndOfStreet
     * ...?
     */
    public context MoveBackwards() {

        con.position = con.position - oneMeter;

        if (con.position <= startOfStreet) {
            con.position = 0;
        }

        counter--;
        return con;
    }

    /**
     * Description:
     * isNoisy: This method takes in an array of integers and creates a 5 long array based on the cars position, if any
     * value in the array exceeds 200 or is below 0 returns true. We then iterate through the array and find the smallest
     * and biggest values and if the difference is bigger than 120 then return true. Otherwise, return false
     * <p>
     * Pre-condition: Sensor data has at least 1 value
     * <p>
     * Post-condition: returns the correct boolean value
     * <p>
     * Test-cases:
     * sensorDataNotNoisyTest
     * sensorDataNoisyTest
     * sensorDataNegativeTest
     * sensorDataTooBigTest
     * ...?
     */
    boolean isNoisy(int[] sensorData) {
        //illegal startvalue
        if (sensorData[0] > 200 || sensorData[0] < 0) return true;

        int max = sensorData[0];
        int min = sensorData[0];


        for (int i = 1; i < sensorData.length; i++) {

            if (sensorData[i] < 0 || sensorData[i] > 200) return true;

            if (sensorData[i] > max) {

                max = sensorData[i];

            } else if (sensorData[i] < min) min = sensorData[i];

        }
        //If the difference between the largest and smalles data exceeds 120 the data is noisy
        return 120 < Math.abs(max - min);

    }

    /**
     * Description:
     * isEmpty: This method queries the two ultrasound sensors at least 5 times and filters the noise in their results
     * and returns the distance to the nearest object in the right hand side. If one sensor is detected to continuously
     * return very noisy output, it should be completely disregarded. You can use averaging or any other statistical
     * method to filter the noise from the signals received from the ultrasound sensors.
     * <p>
     * Pre-condition: Sensor data has at least 5 values
     * <p>
     * Post-condition: the returned distance is inbetween the values 0-200
     * <p>
     * Test-cases:
     * isEmptyWithTwoWorkingSensorsTest
     * isEmptyWithFirstSensorBrokenTest
     * isEmptyWithSecondSensorBrokenTest
     * isEmptyWithBothSensorsBrokenTest
     * ...?
     */
    int isEmpty() {
        int distance = 0;
        int context = carPos / 100;
        int[] fiveSensValuesOne = new int[5];
        int[] fiveSensValuesTwo = new int[5];

        for (int i = 0; i < 5; i++) {
            fiveSensValuesOne[i] = ultraSoundSensorOne[context];
            fiveSensValuesTwo[i] = ultraSoundSensorTwo[context];
            context++;
        }

        if (isNoisy(fiveSensValuesOne)) {
            //First sensor is noisy

            if (isNoisy(fiveSensValuesTwo)) {
                //Both sensors are noisy/broken
                return -1;
            } else {

                //Second sensor is working
                for (int value : fiveSensValuesTwo) {
                    distance += value;
                }
                System.out.println(distance);
                return distance / fiveSensValuesTwo.length; //average of the 5 values
            }

        }

        if (isNoisy(fiveSensValuesTwo)) {
            //second sensor is noisy and first one is not
            for (int value : fiveSensValuesOne) {
                distance += value;
            }
            return distance / fiveSensValuesOne.length; //average of the 5 values
        }


        // Both sensors are reliable, handle this case accordingly
        for (int value : fiveSensValuesOne) {
            distance += value;
        }
        for (int value : fiveSensValuesTwo) {
            distance += value;
        }
        return distance / (fiveSensValuesOne.length + fiveSensValuesTwo.length); // average of 10 values (5 from each sensor)

    }


    /**
     * Description:
     * Park: It moves the car to the beginning of the current 5 meter free stretch of parking place, if it is already
     * detected or moves the car forwards towards the end of the street until such a stretch is detected. Then it
     * performs a pre-programmed reverse parallel parking maneuver.
     * <p>
     * Pre-condition: There is an available parkingspot
     * <p>
     * Post-condition: The car should be parked
     * <p>
     * Test-cases:
     * parkWhenNoSpaceTest
     * parkWhenFirstSpaceIsFreeTest
     * ...
     * ...?
     */
    public context Park() {
        for (int i = 0; i <= parkingPlace.length - 5; i++) {
            if (parkingPlace[i] == 0 && parkingPlace[i + 1] == 0 && parkingPlace[i + 2] == 0 &&
                    parkingPlace[i + 3] == 0 && parkingPlace[i + 4] == 0) {
                MoveForward();
                parkingSpot = true;
                break;
            }
        }
            if (isEmpty() > 180 && parkingSpot) {
                con.situation = true;
                System.out.println("You parked your car");
            }
            return con;
        }



    /**
     * Description:
     * UnPark: It moves the car forward (and to left) to front of the parking place, if it is parked.
     * <p>
     * Pre-condition: That we are not at the end of the street and that we are parked
     * <p>
     * Post-condition: The car should be unparked and moved forward
     * <p>
     * Test-cases:
     * UnparkWhenCarIsParkedTest
     * UnparkWhenCarisNotParkedTest
     * UnparkWhenAtEndOfStreetTest
     * ...?
     */
    public context UnPark() {
        con.situation = false;
        return con;

    }

    /**
     * Description:
     * WhereIs: This method returns the current position of the car in the street as well as its situation
     * (whether it is parked or not).
     * <p>
     * Pre-condition: Car exists?
     * <p>
     * Post-condition: The position and situation is returned successfully
     * <p>
     * Test-cases:
     * CarIsParkedTest
     * CarisNotParkedTest
     * ...
     * ...?
     */
    public context WhereIs() {
        return con;

    }

}
