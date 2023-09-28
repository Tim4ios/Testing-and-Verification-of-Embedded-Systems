import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActuatorTest {

    public Actuator actuator;
    public AutoParkingCar.context context;

    private int endOfTheStreet = 50000;
    private int startOfStreet = 0;

    private int oneMeter = 100;

    @BeforeEach
    void setup() {
        actuator = new Actuator();
        context = new AutoParkingCar.context(startOfStreet, false);
    }

    @Test
    void startingPositionTest(){
        Assertions.assertEquals(context.getPosition(), 0);
    }


    @Test
    void driveTest() {
        int result = context.getPosition();
        actuator.drive(context);
        Assertions.assertEquals(context.getPosition(), result + oneMeter);
    }

    @Test
    void reverseTest() {
        context.setPosition(100);
        int result = context.getPosition();
        actuator.reverse(context);
        Assertions.assertEquals(context.getPosition(), result - oneMeter);
    }

}
