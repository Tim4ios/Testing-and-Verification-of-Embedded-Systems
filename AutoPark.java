public class AutoPark {

    /*Methods
    MoveForward: This method moves the car 1 meter forward, queries the two sensors through the isEmpty method described
     below and returns a data structure that contains the current position of the car, and the situation of the detected
     parking places up to now. The car cannot be moved forward beyond the end of the street.

    isEmpty: This method queries the two ultrasound sensors at least 5 times and filters the noise in their results and
     returns the distance to the nearest object in the right hand side. If one sensor is detected to continuously return
     very noisy output, it should be completely disregarded. You can use averaging or any other statistical method to
     filter the noise from the signals received from the ultrasound sensors.

    MoveBackward: The same as above; only it moves the car 1 meter backwards. The car cannot be moved behind if it is
      already at the beginning of the street.

    Park: It moves the car to the beginning of the current 5 meter free stretch of parking place, if it is already
      detected or moves the car forwards towards the end of the street until such a stretch is detected. Then it
      performs a pre-programmed reverse parallel parking maneuver.

    UnPark: It moves the car forward (and to left) to front of the parking place, if it is parked.

    WhereIs: This method returns the current position of the car in the street as well as its situation
      (whether it is parked or not).
     */

    public AutoPark() {
        //define in centimeter?
        double carX;
        double carY;
    }

    void MoveForward(){}

    void MoveBackwards(){}

    boolean isEmpty(){return true;}

    void Park(){}

    void UnPark(){}

    void WhereIs(){}


}
