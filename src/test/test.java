package test;

import main.SampleTesting;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class test {

    @Test
    public void test_IE64() throws Exception {
        String testResult = SampleTesting.forTesting();
        assertEquals("Running test",testResult);
    }

}
