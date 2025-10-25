package test;

import main.SampleTesting;
import main.command.createTaskCommand;
import main.item.Resource;
import main.item.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.io.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class test {
    @BeforeEach
    public void setUp() {
        Resource.createResource("Computer", 10);
    }

    @Test
    public void createResourceTest() {
        Resource rs = Resource.getResource("Computer");
        Resource res = Resource.getResource("Laptop");

        assertNotNull(rs);
        assertEquals(rs.getCapacity(), 10);
        assertEquals(rs.getStatus(), "AVAILABLE");
        assertEquals(rs.getUtilization(), 0.0);
        assertNull(res);
    }

    @Test
    public void borrowResourceTest() {
        Resource rs = Resource.getResource("Computer");

        rs.borrowResource();
        assertEquals(rs.getStatus(), "BORROWED");

        rs.borrowResource();
        assertEquals(rs.getStatus(), "BORROWED");
    }

    @Test
    public void deleteResourcesTest() {
        Resource rs = Resource.getResource("Computer");

        rs.deleteResources();
        assertNull(rs);
        assertEquals(rs.getStatus(), "DELETED");
    }

    @Test
    public void setCapacityTest() {
        Resource rs = Resource.getResource("Computer");

        rs.setCapacity(20);
        assertEquals(rs.getCapacity, 20);

        rs.setCapacity(-1);
        assertEquals(rs.getCapacity, 20);
    }

    @Test
    public void availabilityScheduleTest() {
        Resource rs = Resource.getResource("Computer");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.add(Calendar.DAY_OF_MONTH, 1);
        c2.add(Calendar.DAY_OF_MONTH, 2);
        Date d1 = new Date();
        Date d2 = c1.getTime();
        Date d3 = c2.getTime();

        assertFalse(rs.isAvailableOnDate(d1));
        assertEquals(rs.getUtilization(), 0.0);

        rs.updateAvailability(d1, true);
        rs.updateAvailability(d2, true);
        rs.updateAvailability(d3, false);
        assertTrue(rs.isAvailableOnDate(d1));
        assertEquals(rs.getUtilization(), 66.67, 0.01);
    }

    public void printResourcesTestOne() { // https://stackoverflow.com/questions/32241057/how-to-test-a-print-method-in-java-using-junit
    	ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Resource.printResources();
        String expected = "Resources:\r\nComputer - AVAILABLE (Capacity: 10, Utilization: 0.0%)\r\n";
        assertEquals(expected,outContent.toString());
    }
}
