import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;


import java.util.Arrays;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutoParkingCarTest {
    @Mock
    SensorData mockedSensorData;
    AutoParkingCar car;
    AutoParkingCar car2;
    AutoParkingCar car3;
    private int[] dummyParkingPlace;
    private int[] dummyParkingFirstSpot;

    private int[] dummySens1;
    private int[] dummySens2;

    private int[] mockParkingSpot;
    private SensorData sd;
    Actuator act;

    @BeforeEach
        //Setting up two cars, one that has free parking and other one who hasn't at the start of the street,
        // with sensors and start position and that it is not parked.
    void setupCars() {
        // Initialize dummy sensor data and car context for testing.
        mockedSensorData = Mockito.mock(SensorData.class);

        sd = new SensorData();
        mockParkingSpot = sd.returnSensorData(500);

        dummySens1 = new int[]{200, 190, 180, 195, 185};
        dummySens2 = new int[]{180, 179, 193, 191, 199};
        dummyParkingFirstSpot = new int[]{0, 0, 0, 0, 0};

        AutoParkingCar.context dummyContext = new AutoParkingCar.context(0, false);

        car = new AutoParkingCar(mockParkingSpot,dummyContext,act);
        //car2 = new AutoParkingCar(dummySens1, dummySens2, dummyContext, mockParkingSpot, act);


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

    @Test
    void willeMockitoTest()
    {
      when(mockedSensorData.returnSensorData(500)).thenReturn(new int[] {1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});

    }
    @Test
    void isMockingBackwards() {
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