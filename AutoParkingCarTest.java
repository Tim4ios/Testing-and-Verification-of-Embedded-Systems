import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AutoParkingCarTest {
    AutoParkingCar car;

    Random random = new Random();
    int randomNumber = random.nextInt(121)+80;
    int[] sens1 = {180, 200, randomNumber, 200, 160};
    int[] sens2 = {160, 200, randomNumber, 200, 180};


    @BeforeEach
    void setupCar() {
        car = new AutoParkingCar(sens1, sens2, new AutoParkingCar.context(0, false));
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
    void isSensorEmptyTest() {

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
}