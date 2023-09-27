import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

class AutoParkingCarTest {
    AutoParkingCar car;
    AutoParkingCar car2;
    private int[] dummyParkingPlace;
    private int[] dummyParkingFirstSpot;

    private int[] dummySens1;
    private int[] dummySens2;

    private int [] mockParkingSpot;
    private SensorData sd;
    Actuator act;

    @BeforeEach
        //Setting up two cars, one that has free parking and other one who hasn't at the start of the street,
        // with sensors and start position and that it is not parked.
    void setupCars() {
        // Initialize dummy sensor data and car context for testing.

        sd = new SensorData();
        mockParkingSpot = sd.returnSensorData(500);


        dummySens1 = new int[]{200, 190, 180, 195, 185};
        dummySens2 = new int[]{180, 179, 193, 191, 199};
        dummyParkingFirstSpot = new int[]{0, 0, 0, 0, 0};

        dummyParkingPlace = AutoParkingCar.generateRandomParking(500, 5);
        AutoParkingCar.context dummyContext = new AutoParkingCar.context(0, false);


        car = new AutoParkingCar(dummySens1, dummySens2, dummyContext, dummyParkingPlace, act);
        car2 = new AutoParkingCar(dummySens1, dummySens2, dummyContext, mockParkingSpot, act);


    }


/**------------------------------------------------------------------------------------------------------------------**/
    /**
     * TESTS FOR WhereIs() METHOD
     **/
    @Test
    void whereIsCarTest() {
        // Test whether the WhereIs method returns car position and situation correctly.
        int pos3 = 300;
        for (int i = 0; i < 3; i++) {
            car.MoveForward();
        }
        // Assert that the position and situation match the expected values.
        Assertions.assertEquals(pos3, car.WhereIs().getPosition());
        Assertions.assertFalse(car.WhereIs().getSituation());

    }

/**------------------------------------------------------------------------------------------------------------------**/
    /**
     * TESTS FOR MoveForwards() METHOD
     **/
    @Test
    void didCarMoveForwardTest() {
        // Test whether the car can move forward correctly.

        for (int i = 0; i < 6; i++) {
            car.MoveForward();
        }
        // Assert that the car's position is updated correctly and not parked.
        Assertions.assertEquals(600, car.con.getPosition());
        Assertions.assertFalse(car.con.getSituation());


    }

    @Test
    void tryToMoveForwardParkTest() {
        // Try to move forward while the car is parked
        car.Park();
        int resultPosition = car.con.getPosition();
        car.MoveForward();
        Assertions.assertEquals(resultPosition, car.con.getPosition());
    }

    @Test
    void didTheCarMoveToEndOfStreetAndStartOverFromTheBeginningTest() {
        // Set the car's position to the end of the street.
        car.con.setPosition(50000);
        // Assert that the car's position is set correctly to the end of the street.
        Assertions.assertEquals(50000, car.con.getPosition());


        // Move the car forward, which should wrap it around to the beginning of the street.
        car.MoveForward();
        // Assert that the car's position is now at the beginning of the street (startOver).
        Assertions.assertEquals(0, car.con.getPosition());
    }
/**------------------------------------------------------------------------------------------------------------------**/
    /**
     * TESTS FOR MoveBackwards() METHOD
     **/
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
        Assertions.assertEquals(pos5, car.con.getPosition());
        Assertions.assertFalse(car.con.getSituation());


    }

    @Test
    void tryToMoveBackwardsWhileParkedTest() {
        // Try to move backward while the car is parked
        car.Park();
        int resultPosition = car.con.getPosition();
        car.MoveBackwards();
        Assertions.assertEquals(resultPosition, car.con.getPosition());
    }

    @Test
    void shouldNotMoveBackwardsIfAtStartTest() {
        // Test so the car can't move out of the street backwards
        car.MoveBackwards();
        Assertions.assertEquals(0, car.con.getPosition());
    }

/**------------------------------------------------------------------------------------------------------------------**/
    /**
     * TESTS FOR Park() METHOD
     **/
    @Test
    void parkCarTest() {
        //unpark car in case it is parked
        car.UnPark();
        car.Park();
        Assertions.assertTrue(car.con.getSituation());
    }

    @Test
    void parkCarWhenParkedTest() {
        //Check that you cannot park while parked
        car.con.setSituation(true);
        car.Park();
        Assertions.assertTrue(car.con.getSituation());
    }
/**------------------------------------------------------------------------------------------------------------------**/
    /**
     * TESTS FOR UnPark() METHOD
     **/
    @Test
    void unParkTest() {
        // Test if the car can be unparked.
        car.MoveForward();
        car.MoveForward();
        car.MoveForward();
        car.Park();
        int resultPosition = car.con.getPosition();
        car.UnPark();
        // Assert that the car is not parked.
        Assertions.assertEquals(resultPosition, car.con.getPosition());
    }

    @Test
    void unParkCarWhenParkedTest() {
        // Check that you cannot unpark if not parked
        car.con.setSituation(false);
        car.UnPark();
        Assertions.assertFalse(car.con.getSituation());

    }
/**------------------------------------------------------------------------------------------------------------------**/
    /**
     * TESTS FOR setPosition() & getPosition() METHOD
     **/
    @Test
    void InvalidInputArgumentNegativePositionTest() {
        //Test the context object for not setting negative values
        car.con.setPosition(-300);
        Assertions.assertEquals(0, car.WhereIs().getPosition());
    }

    @Test
    void InvalidInputArgumentLargePositivePositionTest() {
        //Test the context object for not setting values bigger then the road
        car.con.setPosition(50001);
        Assertions.assertEquals(0, car.WhereIs().getPosition());
    }

/**------------------------------------------------------------------------------------------------------------------**/
    /**
     * TESTS FOR isNoisy() METHOD
     **/
    @Test
    void sensorDataNotNoisyTest() {
        int[] cleanData = {190, 150, 120, 180, 200};
        Assertions.assertFalse(car.isNoisy(cleanData));// Clean data should return false
    }

    @Test
    void sensorDataNoisyTest() {
        int[] noisyData = {20, 150, 10, 200, 115};
        Assertions.assertTrue(car.isNoisy(noisyData)); // Noisy data should return true
    }

    @Test
    void sensorDataNegativeTest() {
        int[] cleanData = {0, 0, 0, 0, -1};
        Assertions.assertTrue(car.isNoisy(cleanData));// Noisy data should return true
    }

    @Test
    void sensorDataTooBigTest() {
        int[] cleanData = {200, 190, 201, 198, 189};
        Assertions.assertTrue(car.isNoisy(cleanData));// Noisy data should return true
    }

/**------------------------------------------------------------------------------------------------------------------**/
    /**
     * TESTS FOR isEmpty() METHOD
     **/
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
        Assertions.assertEquals(expected_result / 10, car.isEmpty());
    }

    @Test
    void isEmptyWithFirstSensorBrokenTest() {
        int[] brokenSensor = {200, 10, 200, 30, 150};
        AutoParkingCar dummyCar = new AutoParkingCar(dummySens1, brokenSensor, new AutoParkingCar.context(0, false), dummyParkingPlace, act);

        int expected_result = 0;

        for (int val : dummySens1) {
            expected_result += val;
        }

        //It the expected result should only take the average of the working sensor, being the second one
        Assertions.assertEquals(expected_result / 5, dummyCar.isEmpty());
    }

    @Test
    void isEmptyWithSecondSensorBrokenTest() {
        int[] brokenSensor = {200, 10, 50, 100, 150};
        AutoParkingCar dummyCar = new AutoParkingCar(brokenSensor, dummySens2, new AutoParkingCar.context(0, false), dummyParkingPlace, act);

        int expected_result = 0;

        for (int val : dummySens2) {
            expected_result += val;
        }

        //It the expected result should only take the average of the working sensor, being the second one
        Assertions.assertEquals(expected_result / 5, dummyCar.isEmpty());
    }

    @Test
    void isEmptyWithBothSensorsBrokenTest() {
        int[] brokenSensorOne = {200, 10, 50, 100, 150};
        int[] brokenSensorTwo = {500000, 10, 50, 100, 150};
        AutoParkingCar dummyCar = new AutoParkingCar(brokenSensorOne, brokenSensorTwo, new AutoParkingCar.context(0, false), dummyParkingPlace, act);

        //Faulty return value indicated with length -1
        Assertions.assertEquals(-1, dummyCar.isEmpty());
    }

    @Test
    void isMockingBackwards()
    {
        System.out.println(Arrays.toString(mockParkingSpot));
        car2.con.setPosition(50000);
        System.out.println(car2.con.getPosition());
        System.out.println(car2.con.getSituation());
        car2.ParkBackwards();
        System.out.println(car2.con.getPosition());
        System.out.println(car2.con.getSituation());
        System.out.println(Arrays.toString(mockParkingSpot));


    }

}