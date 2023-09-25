import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SensorDataTest {
    //@Mock
    //SensorData sensorDataMock;


    @BeforeEach
    public void SetUp() {

    }

    @Test
    public void testingTheReturnOfTheSensorData() {
        SensorData sensorDataMock = Mockito.mock(SensorData.class);
        SensorData sd = new SensorData();
        sensorDataMock.returnSensorData(500);

        System.out.println(Arrays.toString(sd.returnSensorData(500)));

    }


    @AfterEach
    public void tearDown() {
        System.out.println("TearDownInteractive");
    }

}
