import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class AutoParkingCarTest {
    AutoParkingCar car;
    private int[] dummySens1;
    private int[] dummySens2;

    @BeforeEach
    void setupCar() {
        dummySens1 = new int[]{100, 110, 105, 108, 115};
        dummySens2 = new int[]{95, 105, 98, 112, 100};
        AutoParkingCar.context dummyContext = new AutoParkingCar.context(0, false);
        car = new AutoParkingCar(dummySens1, dummySens2, dummyContext);
    }

    @Test
    void didCarMoveForwardTest() {
        //car.con.setPosition(0);
        car.MoveForward();
        car.MoveForward();
        car.MoveForward();
        car.MoveForward();
        car.MoveForward();
        car.MoveForward();

        assertEquals(600, car.con.getPosition());
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
    void parkCarTest() {
        for (int i = 0; i < 50; i++) {
            car.MoveForward();
            car.Park();
        }
        int result = car.isEmpty();
        System.out.println(result);
        //car.MoveForward();

        assertEquals(true, car.con.getSituation());
    }

    @Test
    void unParkTest() {
        car.UnPark();
        assertEquals(false, car.con.getSituation());
    }

    @Test
    void whereIsCarTest() {
        car.MoveForward();
        car.MoveForward();
        car.MoveForward();


        assertEquals(300, car.WhereIs().getPosition());
        assertEquals(false, car.WhereIs().getSituation());

    }


    @Test
    void testIsNoisy() {
        int[] noisyData = {20, 150, 10, 200, 115};
        assertTrue(car.isNoisy(noisyData)); // Noisy data should return true
    }

    @Test
    void testIsNotNoisy() {
        int[] cleanData = {190, 150, 120, 180, 200};
        assertFalse(car.isNoisy(cleanData));// Clean data should return false
    }


    @Test
    void testIsEmpty() {
        int result = car.isEmpty();
        int expected_result = 0;

        for (int val : dummySens1) {
            expected_result += val;
        }
        for (int val : dummySens2) {
            expected_result += val;
        }

        assertEquals(expected_result / 10, result);
    }
}