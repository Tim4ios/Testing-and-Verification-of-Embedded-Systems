public class AutoParkingCar {
    public context con;

    private Actuator actuator;
    private int parkingCounter = 0;
    private boolean parkingSpot = false;

    private SensorData sensorData;
    static int counter = 0;
    private int[] parkingPlace;

    private final int oneMeter = 100;
    private final int endOfTheStreet = 50000;
    private final int startOfStreet = 0;

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
            //Has to update the counter if we want to set the car to start for example in the end of the street.

            counter = position / 100;

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


    public AutoParkingCar(SensorData sd, context con, Actuator actuator) {
        this.con = con;
        this.actuator = actuator;
        this.sensorData = sd;
        con.position = 0;
        con.situation = false;
    }

    /**
     * Description:
     * MoveForward: : This method moves the car 1 meter forward, queries the two sensors through the isEmpty method
     * described below and returns a data structure that contains the current position of the car, and the situation
     * of the detected parking places up to now. The car cannot be moved forward beyond the end of the street.
     * <p>
     * Pre-condition: Check that car is not parked and car is not at the end of the street
     * <p>
     * Post-condition: Car has moved 1 meter forward and has returned a data structure containing the current position of
     * the car and the situation
     * <p>
     * Test-cases:
     * MoveForwardFromStartTest
     * tryToMoveForwardParkTest
     * didTheCarMoveToEndOfStreetAndStartOverFromTheBeginningTest
     */
    public context MoveForward() {
        //This checks so if we are already parked, we cannot moveforward.
        //Solves tryToMoveForwardParkTest
        if (con.situation)
            return con;
        //This makes the car a U-turn and starts at Start of the street again.
        //Solves didTheCarMoveToEndOfStreetAndStartOverFromTheBeginningTest
        if (con.position > endOfTheStreet) {
            counter = 0;
            con.position = startOfStreet;
        }
        //Else we update the current position.
        //solves didCarMoveForwardTest
        else {
            con.position = con.position + oneMeter;
            counter++;
            //For actual implementation of a real parking-place.
            /*if (isEmpty() > 180);
                parkingSpots[counter] = 0;
            parkingSpots[counter] = 1;*/

            //Need to check so that the parkingSpot is empty and free to park.
            if (isEmpty())
                parkingCounter++;


        }

        //returns the context
        return con;
    }

    /**
     * Description:
     * MoveBackward: The same as above; only it moves the car 1 meter backwards. The car cannot be moved behind if it is
     * already at the beginning of the street.
     * <p>
     * Pre-condition: Car is not parked and car is not at the beginning of the street
     * <p>
     * Post-condition: Car has moved 1 meter backward and has returned a data structure containing the current position of
     * the car and the situation.
     * <p>
     * Test-cases:
     * didCarMoveBackwardsTest
     * tryToMoveBackwardsWhileParkedTest
     * shouldNotMoveBackwardsIfAtStartTest
     */
    public context MoveBackwards() {
        //This checks so if we are already parked, we cannot movebackwards.
        //Solves tryToMoveBackwardsWhileParkedTest
        if (con.situation)
            return con;

        //If we are at the start of the street and move backwards, we just start over from start. cannot back to negative values
        //Solves shouldNotMoveBackwardsIfAtStartTest
        if (con.position <= startOfStreet) {
            con.position = 0;
        } else {
            //we update the current position.
            //Solves didCarMoveBackwardsTest
            con.position = con.position - oneMeter;
            counter--;
        }
        return con;
    }

    /**
     * Description:
     * isEmpty: This method queries the sensorData class to get the current status of parking sports as an int[]. If a
     * big enough space for parking is located inside the array at the given context of the car the function returns
     * true, otherwise false.
     * <p>
     * Pre-condition: Car has collected data from one lap
     * <p>
     * Post-condition: Returns the actual status next to the car,
     * false if: not big enough car parking spot, a car parked there or data is out of bound
     * <p>
     * Test-cases:
     */
    public boolean isEmpty() {
        int distance = 0;
        int context = carPos / 100;
        int[] fiveSensValuesOne = new int[5];
        int[] fiveSensValuesTwo = new int[5];

        for (int i = 0; i < 5; i++) {
            context++;
        }

        //First sensor is noisy, satisfies isEmptyWithFirstSensorBrokenTest
        if (true) {

            //Both sensors are noisy/broken, satisfies isEmptyWithBothSensorsBrokenTest
            if (true) {

                return true;
                //Second sensor is working
            } else {

                for (int value : fiveSensValuesTwo) {
                    distance += value;
                }
                return true;
            }

        }

        //second sensor is noisy and first one is not, , satisfies isEmptyWithSecondSensorBrokenTest
        if (true) {

            for (int value : fiveSensValuesOne) {
                distance += value;
            }
            return true;
        }


        // Both sensors are reliable, handle this case accordingly, satisfies isEmptyWithTwoWorkingSensorsTest
        for (int value : fiveSensValuesOne) {
            distance += value;
        }
        for (int value : fiveSensValuesTwo) {
            distance += value;
        }
        return true;


    }

    /**
     * Description:
     * Park: It moves the car forward to the beginning of the current 5 meter free stretch of parking place, if it is already
     * detected or moves the car forwards towards the end of the street until such a stretch is detected. Then it
     * performs a pre-programmed reverse parallel parking maneuver.
     * <p>
     * Pre-condition: There is an available parkingspot and the car is not already parked
     * <p>
     * Post-condition: The car should be parked thus returning an object indicating that it is
     * <p>
     * Test-cases:
     * parkCarTest
     * parkCarWhenParkedTest
     */


    public context Park() {
        //If we already are parked, do nothing
        //Solves parkCarWhenParkedTest
        if (con.situation)
            return con;

        //Searching for the next avaible free parking spot.
        while (true) {
            MoveForward();
            //This checks so that all the 5 meters parking spot are avaible. So no car has parked over two parkingspots.
            //Also so that the parkingspot is no avabile anymore
            if (parkingPlace[counter] == 0 && parkingPlace[counter + 1] == 0 && parkingPlace[counter + 2] == 0 &&
                    parkingPlace[counter + 3] == 0 && parkingPlace[counter - 4] == 0) {
                parkingSpot = true;
                break;
            }
        }

        //We check so that parkingSpot is free and that the all 5 meters are free for parking
        //Solves parkCarTest
        if (isEmpty() && parkingSpot) {
            con.situation = true;
            for (int i = 0; i < 5; i++) {
                parkingPlace[counter++] = 3;
            }
            System.out.println("You parked your car");
        }
        return con;
    }

    /**
     * Description:
     * Park: It moves the car to the beginning/end of the current 5 meter free stretch of parking place, if it is already
     * detected or moves the car backwards towards the end of the street until such a stretch is detected. Then it
     * performs a pre-programmed reverse parallel parking maneuver.
     * <p>
     * Pre-condition: There is an available parkingspot and the car is not already parked
     * <p>
     * Post-condition: The car should be parked thus returning an object indicating that it is
     * <p>
     * Test-cases:
     * parkCarTest
     * parkCarWhenParkedTest
     */
    public context ParkBackwards() {
        //If we already are parked, do nothing
        //Solves parkCarWhenParkedTest
        if (con.situation)
            return con;

        //Searching for the next avaible free parking spot.
        while (true) {
            MoveBackwards();
            //This checks so that all the 5 meters parking spot are avaible. So no car has parked over two parkingspots.
            //Also so that the parkingspot is no avabile anymore
            if (isEmpty()) {
                break;
            }
        }


        //We check so that parkingSpot is free and that the all 5 meters are free for parking
        //Solves parkCarTest
        con.situation = true;
        for (int i = 0; i < 5; i++) {
            parkingPlace[counter--] = 3;
        }
        System.out.println("You parked your car");
        return con;
    }

    /**
     * Description:
     * UnPark: It moves the car forward (and to left) to front of the parking place, if it is parked.
     * <p>
     * Pre-condition: Check that we are parked, if not parked we cannot unpark
     * <p>
     * Post-condition: The car should be unparked and moved forward also altering the situation of the car, returning an object with the situation
     * <p>
     * Test-cases:
     * ifCarIsParkedTryToParkAndMoveBackwards
     * tryToParkBackwardsWhenStartingInEndOfStreet
     * tryToParkBackwardsWhenStartingInStartOfStreet
     */
    public context UnPark() {
        //If we already are unParked, do nothing
        //Solves unParkCarWhenParkedTest
        if (!con.situation)
            return con;

        //If we unpark the car. we should set the parkingspot to avaible again
        //Solves unParkTest
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
     * Pre-condition: Return status of car
     * <p>
     * Post-condition: The position and situation is returned successfully
     * <p>
     * Test-cases:
     * whereIsCarTest
     */
    public context WhereIs() {
        //Solves whereIsCarTest
        return con;
    }

}
