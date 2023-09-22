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

    private FizzBuzz fissBuss;

    @Mock
    Database databaseMock;


    @Test
    public void testQuery() {
        assertNotNull(databaseMock);
        when(databaseMock.isAvailable()).thenReturn(true);
        Service t = new Service(databaseMock);
        boolean check = t.query("");
        assertTrue(check);
    }

    @Test
    void ensureMockitoReturnsTheConfiguredValue() {

        // define return value for method getUniqueId()
        when(databaseMock.getUniqueId()).thenReturn(42);

        Service service = new Service(databaseMock);
        // use mock in test....
        assertEquals(service.toString(), "Using database with id: 42");
    }

    @BeforeEach
    public void SetUp() {
        fissBuss = new FizzBuzz();
    }


    @Test
    public void testOne() {
        String result = fissBuss.evaluate(1);
        assertEquals("1", result, "Hello there");
    }

    @Test
    public void testTwo() {
        String result = fissBuss.evaluate(2);
        assertEquals("2", result, "Message here: ");
    }

    @Test
    public void testFizzBuzz() {
        assertEquals("FizzBuzz", fissBuss.evaluate(15));
    }

    @Test
    public void testFizz() {
        assertEquals("Fizz", fissBuss.evaluate(3));
    }

    @Test
    public void testBuzz() {
        assertEquals("Buzz", fissBuss.evaluate(5));
    }

    @Test
    public void testLoop() {
        for (int i = 0; i < 1000; i++) {
            if (i % 3 == 0 && i % 5 == 0)
                assertEquals("FizzBuzz", fissBuss.evaluate(i));
            else if (i % 3 == 0)
                assertEquals("Fizz", fissBuss.evaluate(i));
            else if (i % 5 == 0)
                assertEquals("Buzz", fissBuss.evaluate(i));
            else
                assertEquals(String.valueOf(i), fissBuss.evaluate(i));
        }
    }

    @AfterEach
    public void tearDown() {
        System.out.println("TearDownInteractive");
    }

}
