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
        //MAN KAN INTE PARKERA OM INTE SENSORERNA SÄGER ATT DET FINNS EN PLATS, HALLÅ
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
        int[] brokenSensor = {200,10,200,30,150};
        AutoParkingCar dummyCar = new AutoParkingCar(dummySens1,brokenSensor,new AutoParkingCar.context(0,false));

        int expected_result = 0;

        for (int val : dummySens1) {
            System.out.println(val);
            expected_result += val;
        }

        //It the expected result should only take the average of the working sensor, being the second one
        assertEquals(expected_result / 5, dummyCar.isEmpty());
    }

    @Test
    void isEmptyWithSecondSensorBrokenTest() {
        int[] brokenSensor = {200,10,50,100,150};
        AutoParkingCar dummyCar = new AutoParkingCar(brokenSensor,dummySens2,new AutoParkingCar.context(0,false));

        int expected_result = 0;

        for (int val : dummySens2) {
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

}