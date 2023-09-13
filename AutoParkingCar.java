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

    public context MoveBackwards() {
        if (con.position <= startOfStreet) {
            System.out.println("Car went to far");
        }

        con.position = con.position - oneMeter;

        return con;
    }

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


    public context Park() {
        if (isEmpty() > 180) {
            con.situation = true;
            System.out.println("You parked your car");
        }
        return con;
    }

    public context UnPark() {
        con.situation = false;
        return con;

    }

    public context WhereIs() {
        return con;

    }


}
