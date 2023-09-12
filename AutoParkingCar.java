public class AutoParkingCar {
    private double[] ultraSoundSensorOne;
    private double[] ultraSoundSensorTwo;
    private int carPos;
    private boolean isParked;

    public AutoParkingCar(double[] sens1, double[] sens2) {
        //Car startValues
        carPos = 0;
        isParked = false;
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

    ParkingData WhereIs() {
        return new ParkingData(carPos, isParked);
    }

    class ParkingData {
        private int position;
        private boolean situation;

        public ParkingData(int position, boolean situation) {
            this.position = position;
            this.situation = situation;
        }

        public int getPosition() {
            return position;
        }

        public boolean getSituation() {
            return situation;
        }
    }


}
