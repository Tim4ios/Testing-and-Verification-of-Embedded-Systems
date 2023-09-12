import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AutoParkingCarTest {
    AutoParkingCar car;
    int[] sens1 = new int[5];
    int[] sens2 = new int[5];


    @BeforeEach
    void setupCar() {
        car = new AutoParkingCar(sens1, sens2, new AutoParkingCar.context(0, false));
    }

    @Test
    void didCarMoveForwardTest() {
        car.con.setPosition(50000);
        car.MoveForward();

        assertNull(car.MoveForward());
        assertEquals(50000, car.con.getPosition());
        assertEquals(false, car.con.getSituation());

    }

    @Test
    void didCarMoveBackwardsTest() {
        car.MoveForward();
        car.MoveBackwards();

        assertEquals(0, car.con.getPosition());
        assertEquals(false, car.con.getSituation());


    }

    @Test
    void isSensorEmptyTest() {

    }

    @Test
    void parkCarTest() {
    }

    @Test
    void unParkTest() {
    }

    @Test
    void whereIsCarTest() {
        car.MoveForward();
        car.MoveForward();
        car.MoveForward();


        assertEquals(300, car.con.getPosition());
        assertEquals(false, car.con.getSituation());

    }
}