public class AutoParkingCar {
    public context con;
    private int[] ultraSoundSensorOne;
    private int[] ultraSoundSensorTwo;
    private int oneMeter = 100;

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

    }

    /**

     Description:
     MoveForward: : This method moves the car 1 meter forward, queries the two sensors through the isEmpty method
     described below and returns a data structure that contains the current position of the car, and the situation
     of the detected parking places up to now. The car cannot be moved forward beyond the end of the street.

     Pre-condition: Car is not at the end of the street

     Post-condition: Car has moved 1 meter forward and has returned a data structure containing the current position of
     the car and the situation

     Test-cases:
     MoveForwardFromStartTest
     MoveForwardFromEndOfStreet
     ...?
     */
    public context MoveForward() {
        if (con.position >= endOfTheStreet) {
            //start over from start?
            con.position = startOfStreet;
            return null;
        } else {
            con.position = con.position + oneMeter;
            isEmpty();
        }


        return con;
    }


    /**
     Description:
     MoveBackward: The same as above; only it moves the car 1 meter backwards. The car cannot be moved behind if it is
     already at the beginning of the street.

     Pre-condition: Car is not at the beginning of the street

     Post-condition: Car has moved 1 meter backward and has returned a data structure containing the current position of
     the car and the situation.

     Test-cases:
     MoveBackwardsFromStartTest
     MoveBackwardsFromEndOfStreet
     ...?
     */
    public context MoveBackwards() {
        if (con.position <= startOfStreet) {
            System.out.println("Car went to far");
        }

        con.position = con.position - oneMeter;

        return con;
    }

    /**
     Description:
     isNoisy: This method takes in an array of integers and creates a 5 long array based on the cars position, if any
     value in the array exceeds 200 or is below 0 returns true. We then iterate through the array and find the smallest
     and biggest values and if the difference is bigger than 120 then return true. Otherwise, return false

     Pre-condition: Sensor data has at least 1 value

     Post-condition: returns the correct boolean value

     Test-cases:
     sensorDataNotNoisyTest
     sensorDataNoisyTest
     sensorDataNegativeTest
     sensorDataTooBigTest
     ...?
     */
    boolean isNoisy(int[] sensorData) {
        //illegal startvalue
        if(sensorData[0]>200||sensorData[0]<0) return true;

        int max = sensorData[0];
        int min = sensorData[0];



        int countOfOutliars = 0;

        for (int i = 1; i < sensorData.length; i++) {

            if(sensorData[i]<0||sensorData[i]>200) return true;

            if(sensorData[i] > max){

                max = sensorData[i];

            }else if(sensorData[i] < min) min = sensorData[i];

        }
        //If the difference between the largest and smalles data exceeds 120 the data is noisy
        return 120 < Math.abs(max - min);

    }

    /**
     Description:
     isEmpty: This method queries the two ultrasound sensors at least 5 times and filters the noise in their results
     and returns the distance to the nearest object in the right hand side. If one sensor is detected to continuously
     return very noisy output, it should be completely disregarded. You can use averaging or any other statistical
     method to filter the noise from the signals received from the ultrasound sensors.

     Pre-condition: Sensor data has at least 5 values

     Post-condition: the returned distance is inbetween the values 0-200

     Test-cases:
     isEmptyWithTwoWorkingSensorsTest
     isEmptyWithFirstSensorBrokenTest
     isEmptyWithSecondSensorBrokenTest
     isEmptyWithBothSensorsBrokenTest
     ...?
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
                return distance / 5; //average of the 5 values
            }

        }

        if (isNoisy(fiveSensValuesTwo)) {
            //second sensor is noisy and first one is not
            for (int value : fiveSensValuesOne) {
                distance += value;
            }
            return distance / 5; //average of the 5 values
        }


        // Both sensors are reliable, handle this case accordingly
        for (int value : fiveSensValuesOne) {
            distance += value;
        }
        for (int value : fiveSensValuesTwo) {
            distance += value;
        }
        return distance / 10; // average of 10 values (5 from each sensor)

    }


    /**
     Description:
     Park: It moves the car to the beginning of the current 5 meter free stretch of parking place, if it is already
     detected or moves the car forwards towards the end of the street until such a stretch is detected. Then it
     performs a pre-programmed reverse parallel parking maneuver.

     Pre-condition: There is an available parkingspot

     Post-condition: The car should be parked

     Test-cases:
     parkWhenNoSpaceTest
     parkWhenFirstSpaceIsFreeTest
     ...
     ...?
     */
    public context Park() {
        if (isEmpty() > 180) {
            con.situation = true;
            System.out.println("You parked your car");
        }
        return con;
    }


    /**
     Description:
     UnPark: It moves the car forward (and to left) to front of the parking place, if it is parked.

     Pre-condition: That we are not at the end of the street and that we are parked

     Post-condition: The car should be unparked and moved forward

     Test-cases:
     UnparkWhenCarIsParkedTest
     UnparkWhenCarisNotParkedTest
     UnparkWhenAtEndOfStreetTest
     ...?
     */
    public context UnPark() {
        con.situation = false;
        return con;

    }

    /**
     Description:
     WhereIs: This method returns the current position of the car in the street as well as its situation
     (whether it is parked or not).

     Pre-condition: Car exists?

     Post-condition: The position and situation is returned successfully

     Test-cases:
     CarIsParkedTest
     CarisNotParkedTest
     ...
     ...?
     */
    public context WhereIs() {
        return con;

    }


}
