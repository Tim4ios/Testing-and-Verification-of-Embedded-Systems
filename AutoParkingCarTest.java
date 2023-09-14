import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AutoParkingCarTest {
    AutoParkingCar car;
    private int[] dummyNoSens1;
    private int[] dummyNoSens2;
    private int[] parkingPlace;


    AutoParkingCar yesParkCar;
    private int[] dummyYesSens1;
    private int[] dummyYesSens2;


    @BeforeEach
        //Setting up two cars, one that has free parking and other one who hasn't at the start of the street, with sensors and start position and that it is not parked.
    void setupCars() {
        // Initialize dummy sensor data and car context for testing.
        dummyNoSens1 = new int[]{100, 110, 105, 108, 115};
        dummyNoSens2 = new int[]{95, 105, 98, 112, 100};
        dummyYesSens1 = new int[]{200, 190, 180, 195, 185};
        dummyYesSens2 = new int[]{180, 179, 193, 191, 199};
        AutoParkingCar.context dummyContext = new AutoParkingCar.context(0, false);

        car = new AutoParkingCar(dummyNoSens1, dummyNoSens2, dummyContext);
        yesParkCar = new AutoParkingCar(dummyYesSens1, dummyYesSens2, dummyContext);
        parkingPlace = car.generateRandomParking(500, 5);

    }

    @Test
    void isCarInTheStartOfTheStreetTest() {
        //Make sure that the car is in the start position
        assertEquals(0, car.con.getPosition());
        assertEquals(0, yesParkCar.con.getPosition());

    }

    @Test
    void testGenerateRandomArrayForZeroRow() {
        // Check if there is a row of (0, 0, 0, 0, 0) in the generated array.
        boolean zeroRowFound = false;
        for (int i = 0; i <= parkingPlace.length - 5; i++) {
            if (parkingPlace[i] == 0 && parkingPlace[i + 1] == 0 && parkingPlace[i + 2] == 0 &&
                    parkingPlace[i + 3] == 0 && parkingPlace[i + 4] == 0) {
                zeroRowFound = true;
                break;
            }
        }
        assertTrue(zeroRowFound);
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
    void carShouldMoveUntillStopTest() {
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
    void parkCarTest() {
        // Test parking the car repeatedly while moving forward.
       /* for (int i = 0; i < 50; i++) {
            car.MoveForward();
            car.Park();
        }*/

        System.out.println(Arrays.toString(parkingPlace));

        car.WhereIs();
        car.Park();
        // Assert that the car is not parked (isEmpty() should not indicate a parking space).
        assertFalse(car.con.getSituation());
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

        for (int val : dummyNoSens1) {
            expected_result += val;
        }
        for (int val : dummyNoSens2) {
            expected_result += val;
        }
        //It the expected result should be the same as the average of the clean date
        assertEquals(expected_result / 10, car.isEmpty());
    }

    @Test
    void isEmptyWithFirstSensorBrokenTest() {
        int[] brokenSensor = {200,10,200,30,150};
        AutoParkingCar dummyCar = new AutoParkingCar(dummyNoSens1,brokenSensor,new AutoParkingCar.context(0,false));

        int expected_result = 0;

        for (int val : dummyNoSens1) {
            System.out.println(val);
            expected_result += val;
        }

        //It the expected result should only take the average of the working sensor, being the second one
        assertEquals(expected_result / 5, dummyCar.isEmpty());
    }

    @Test
    void isEmptyWithSecondSensorBrokenTest() {
        int[] brokenSensor = {200,10,50,100,150};
        AutoParkingCar dummyCar = new AutoParkingCar(brokenSensor,dummyNoSens2,new AutoParkingCar.context(0,false));

        int expected_result = 0;

        for (int val : dummyNoSens2) {
            System.out.println(val);
            expected_result += val;
        }

        //It the expected result should only take the average of the working sensor, being the second one
        assertEquals(expected_result / 5, dummyCar.isEmpty());
    }

    @Test
    void isEmptyWithBothSensorsBrokenTest() {
        int[] brokenSensorOne = {200,10,50,100,150};
        int[] brokenSensorTwo = {500000,10,50,100,150};
        AutoParkingCar dummyCar = new AutoParkingCar(brokenSensorOne,brokenSensorTwo,new AutoParkingCar.context(0,false));

        //Faulty return value indicated with length -1
        assertEquals(-1 , dummyCar.isEmpty());
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