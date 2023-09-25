import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SensorDataTest {

    private SensorData sensorData;

    @Mock
    SensorData databaseMock;


    @BeforeEach
    public void SetUp() {

    }

    @Test
    public void testingTheReturnOfTheSensorData() {
    }


    @AfterEach
    public void tearDown() {
        System.out.println("TearDownInteractive");
    }

}
