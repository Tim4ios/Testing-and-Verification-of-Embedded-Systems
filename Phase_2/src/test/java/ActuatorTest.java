import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ActuatorTest {

    AutoParkingCar car;

    private int[] dummyParkingPlace;
    private int[] dummyParkingFirstSpot;

    private int[] dummySens1;
    private int[] dummySens2;

    @Test
    public void testMoveForward() {
        // Create a mock actuator
        Actuator mockActuator = Mockito.mock(Actuator.class);

        dummySens1 = new int[]{200, 190, 180, 195, 185};
        dummySens2 = new int[]{180, 179, 193, 191, 199};
        dummyParkingFirstSpot = new int[]{0, 0, 0, 0, 0};

        dummyParkingPlace = AutoParkingCar.generateRandomParking(500, 5);
        AutoParkingCar.context dummyContext = new AutoParkingCar.context(0, false);
        // Perform actions in Phase 1 that should call moveForward on the actuator
        AutoParkingCar car = new AutoParkingCar(dummySens1, dummySens2, dummyContext, dummyParkingPlace,mockActuator);

        // Verify that the moveForward method was called with the correct argument
        //Mockito.verify(mockActuator).moveForward(5); // Assuming Phase 1 moved forward by 5 units

        // You can also assert other conditions based on the interactions
        //assertEquals(5, mockActuator.getCurrentPosition());
    }
}