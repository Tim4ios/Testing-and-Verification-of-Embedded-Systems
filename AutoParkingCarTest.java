import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AutoParkingCarTest {
    AutoParkingCar car;
    AutoParkingCar car2;

    private int[] dummyBrokenSens1;
    private int[] dummyBrokenSens2;
    private int[] dummyParkingPlace;

    private int[] dummySens1;
    private int[] dummySens2;


    @BeforeEach
        //Setting up two cars, one that has free parking and other one who hasn't at the start of the street, with sensors and start position and that it is not parked.
    void setupCars() {
        // Initialize dummy sensor data and car context for testing.

        dummyBrokenSens1 = new int[]{200, 10, 200, 30, 150};
        dummyBrokenSens2 = new int[]{200, 105, 72, 112, 100};

        dummySens1 = new int[]{200, 190, 180, 195, 185};
        dummySens2 = new int[]{180, 179, 193, 191, 199};
        dummyParkingPlace = AutoParkingCar.generateRandomParking(100, 5);
        AutoParkingCar.context dummyContext = new AutoParkingCar.context(0, false);

        car = new AutoParkingCar(dummySens1, dummySens2, dummyContext, dummyParkingPlace);
        car2 = new AutoParkingCar(dummyBrokenSens1,dummyBrokenSens2,dummyContext,dummyParkingPlace);


    }

    @Test
    void isCarInTheStartOfTheStreetTest() {
        //Make sure that the car is in the start position
        assertEquals(0, car.con.getPosition());

    }

    @Test
    void didCarMoveForwardTest() {
        // Test whether the car can move forward correctly.

        for (int i = 0; i < 6; i++) {
            car.MoveForward();
        }
        // Assert that the car's position is updated correctly and not parked.
        assertEquals(600, car.con.getPosition());
        assertFalse(car.con.getSituation());


    }

    @Test
    void carShouldMoveUntilStopTest() {
        //Setting up variables for testing that it should keep move for 10½ lapse.
        int teenAndAHalfLapse = (10 * 501) + 250;
        int posMiddle = 25000;
        for (int i = 0; i < teenAndAHalfLapse; i++) {
            car.MoveForward();
        }
        assertEquals(posMiddle, car.con.getPosition());
    }

    @Test
    void didTheCarMoveToEndOfStreetAndStartOverFromTheBeginningTest() {
        // Set the car's position to the end of the street.
        car.con.setPosition(50000);
        // Assert that the car's position is set correctly to the end of the street.
        assertEquals(50000, car.con.getPosition());


        // Move the car forward, which should wrap it around to the beginning of the street.
        car.MoveForward();
        // Assert that the car's position is now at the beginning of the street (startOver).
        assertEquals(0, car.con.getPosition());
    }

    @Test
    void shouldNotMoveBackwardsIfAtStartTest() {
        // Test so the car can't move out of the street backwards
        car.MoveBackwards();
        assertEquals(0, car.con.getPosition());
    }

    @Test
    void didCarMoveBackwardsTest() {
        // Test whether the car can move backward correctly.
        int pos5 = 500;
        for (int i = 0; i < 6; i++) {
            car.MoveForward();
            if (i == 5)
                car.MoveBackwards();
        }

        // Assert that the car's position is updated correctly and not parked.
        assertEquals(pos5, car.con.getPosition());
        assertFalse(car.con.getSituation());


    }

    @Test
    void parkCarWithTwoBrokenSensorsTest()
    {
        System.out.println("This is the first array" + Arrays.toString(dummyParkingPlace));
        car2.Park();
        System.out.println("This is the parked array" + Arrays.toString(dummyParkingPlace));
        System.out.println(car2.con.getPosition());
        assertFalse(car2.con.getSituation());

    }

    @Test
    void parkCarTwoWorkingSensorsTest() {
        // Test parking the car repeatedly while moving forward.
       /* for (int i = 0; i < 50; i++) {
            car.MoveForward();
            car.Park();
        }*/

        System.out.println("This is the first array" + Arrays.toString(dummyParkingPlace));
        car.Park();
        System.out.println("This is the parked array" + Arrays.toString(dummyParkingPlace));
        System.out.println(car.con.getPosition());
        assertTrue(car.con.getSituation());
        car.UnPark();
        System.out.println("This is the unParked array" + Arrays.toString(dummyParkingPlace));

    }

    @Test
    void unParkTest() {
        // Test if the car can be unparked.
        car.UnPark();
        // Assert that the car is not parked.
        assertFalse(car.con.getSituation());
    }

    @Test
    void whereIsCarTest() {
        // Test whether the WhereIs method returns car position and situation correctly.
        int pos3 = 300;
        for (int i = 0; i < 3; i++) {
            car.MoveForward();
        }

        // Assert that the position and situation match the expected values.
        assertEquals(pos3, car.WhereIs().getPosition());
        assertFalse(car.WhereIs().getSituation());

    }

    @Test
    void sensorDataNotNoisyTest() {
        int[] cleanData = {190, 150, 120, 180, 200};
        assertFalse(car.isNoisy(cleanData));// Clean data should return false
    }

    @Test
    void sensorDataNoisyTest() {
        int[] noisyData = {20, 150, 10, 200, 115};
        assertTrue(car.isNoisy(noisyData)); // Noisy data should return true
    }

    @Test
    void sensorDataNegativeTest() {
        int[] cleanData = {0, 0, 0, 0, -1};
        assertTrue(car.isNoisy(cleanData));// Noisy data should return true
    }

    @Test
    void sensorDataTooBigTest() {
        int[] cleanData = {200, 190, 201, 198, 189};
        assertTrue(car.isNoisy(cleanData));// Noisy data should return true
    }

    @Test
    void isEmptyWithTwoWorkingSensorsTest() {
        int expected_result = 0;

        // Calculate the expected result based on dummy sensor data.
        for (int val : dummySens1) {


            expected_result += val;
        }
        for (int val : dummySens2) {
            expected_result += val;
        }
        //It the expected result should be the same as the average of the clean date
        assertEquals(expected_result / 10, car.isEmpty());
    }

    @Test
    void isEmptyWithFirstSensorBrokenTest() {
        int[] brokenSensor = {200, 10, 200, 30, 150};
        AutoParkingCar dummyCar = new AutoParkingCar(dummySens1, brokenSensor, new AutoParkingCar.context(0, false), dummyParkingPlace);

        int expected_result = 0;

        for (int val : dummySens1) {
            expected_result += val;
        }

        //It the expected result should only take the average of the working sensor, being the second one
        assertEquals(expected_result / 5, dummyCar.isEmpty());
    }

    @Test
    void isEmptyWithSecondSensorBrokenTest() {
        int[] brokenSensor = {200, 10, 50, 100, 150};
        AutoParkingCar dummyCar = new AutoParkingCar(brokenSensor, dummySens2, new AutoParkingCar.context(0, false), dummyParkingPlace);

        int expected_result = 0;

        for (int val : dummySens2) {
            expected_result += val;
        }

        //It the expected result should only take the average of the working sensor, being the second one
        assertEquals(expected_result / 5, dummyCar.isEmpty());
    }

    @Test
    void isEmptyWithBothSensorsBrokenTest() {
        int[] brokenSensorOne = {200, 10, 50, 100, 150};
        int[] brokenSensorTwo = {500000, 10, 50, 100, 150};
        AutoParkingCar dummyCar = new AutoParkingCar(brokenSensorOne, brokenSensorTwo, new AutoParkingCar.context(0, false), dummyParkingPlace);

        //Faulty return value indicated with length -1
        assertEquals(-1, dummyCar.isEmpty());
    }

    @Test
    void aFullRunTest() {
        car.MoveForward();
        car.MoveForward();
        car.MoveForward();
        car.MoveForward();
        car.MoveForward();
        car.WhereIs();
        car.Park();


    }

}