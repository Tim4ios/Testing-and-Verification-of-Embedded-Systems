import java.util.Random;

public class AutoParkingCar {
    public context con;
    private int[] ultraSoundSensorOne;
    private int[] ultraSoundSensorTwo;
    private int oneMeter = 100;

    private int parkingCounter = 0;
    private boolean parkingSpot = false;

    public int counter = 0;
    private int[] parkingPlace;

    private int[] parkingSpots;
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
            if (position < 0 || position > 50000)
                position = 0;
            this.position = position;
        }


        public int getPosition() {
            return position;
        }

        public boolean getSituation() {
            return situation;
        }

        public void setSituation(boolean situation) {
            this.situation = situation;
        }
    }


    public AutoParkingCar(int[] sens1, int[] sens2, context con, int[] parkP) {
        this.con = con;
        con.position = 0;
        con.situation = false;
        ultraSoundSensorOne = sens1;
        ultraSoundSensorTwo = sens2;
        parkingPlace = parkP;
    }

    /**
     * Description:
     * generateRandomParking: This method generates a random parking pattern by creating groups of specified size and
     * assigning a random value (0 or 1) to each element within those groups, resulting in an array
     * that simulates parking availability.
     */
    public static int[] generateRandomParking(int length, int groupSize) {
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
        if (con.situation)
            return con;
        if (con.position >= endOfTheStreet) {
            counter = 0;
            con.position = startOfStreet;
        } else {
            con.position = con.position + oneMeter;
            counter++;
            //For actual implementation of a real parking-place.
            /*if (isEmpty() > 180);
                parkingSpots[counter] = 0;
            parkingSpots[counter] = 1;*/
            if (isEmpty() > 180)
                parkingCounter++;


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
        if (con.situation)
            return con;

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
     */
    boolean isNoisy(int[] sensorData) {
        //illegal startvalue, fixes sensorDataNegativeTest & sensorDataTooBigTest
        if (sensorData[0] > 200 || sensorData[0] < 0) return true;

        int max = sensorData[0];
        int min = sensorData[0];


        for (int i = 1; i < sensorData.length; i++) {
            
            //value, fixes sensorDataNegativeTest & sensorDataTooBigTest
            if (sensorData[i] < 0 || sensorData[i] > 200) return true;

            if (sensorData[i] > max) {

                max = sensorData[i];

            } else if (sensorData[i] < min) min = sensorData[i];

        }
        //If the difference between the largest and smallest data exceeds 120 the data is noisy
        //Satisfies sensorDataNoisyTest & sensorDataNotNoisyTest
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
        
        //First sensor is noisy, satisfies isEmptyWithFirstSensorBrokenTest
        if (isNoisy(fiveSensValuesOne)) {

            //Both sensors are noisy/broken, satisfies isEmptyWithBothSensorsBrokenTest
            if (isNoisy(fiveSensValuesTwo)) {
                
                return -1;
              //Second sensor is working  
            } else {

                for (int value : fiveSensValuesTwo) {
                    distance += value;
                }
                return distance / fiveSensValuesTwo.length; //average of the 5 values
            }

        }
        
        //second sensor is noisy and first one is not, , satisfies isEmptyWithSecondSensorBrokenTest
        if (isNoisy(fiveSensValuesTwo)) {
            
            for (int value : fiveSensValuesOne) {
                distance += value;
            }
            return distance / fiveSensValuesOne.length; //average of the 5 values
        }


        // Both sensors are reliable, handle this case accordingly, satisfies isEmptyWithTwoWorkingSensorsTest
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
        //If we already are parked, do nothing
        if (con.situation)
            return con;

        while (parkingPlace[counter] != 0)
            MoveForward();
        if (parkingPlace[counter] == 0 && parkingPlace[counter + 1] == 0 && parkingPlace[counter + 2] == 0 &&
                parkingPlace[counter + 3] == 0 && parkingPlace[counter + 4] == 0) {
            parkingSpot = true;
        }

        if (isEmpty() > 180 && parkingSpot) {
            con.situation = true;
            for (int i = 0; i < 5; i++) {
                parkingPlace[counter++] = 3;
            }
            System.out.println("You parked your car");
        }
        return con;
    }

    /**
     *
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
        //If we already are unParked, do nothing
        if (!con.situation)
            return con;

        con.situation = false;
        int tempCount = counter;
        for (int i = 0; i <= 5 && tempCount < parkingPlace.length; i++) {
            parkingPlace[tempCount] = 0;
            tempCount--;

        }
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
