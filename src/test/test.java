package test;

import main.item.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class test {
    @BeforeEach
    public void setUp() {}

    @Test
    public void createResourceTest() {
    	Resource.createResource("Computer", 10);
        Resource rc = Resource.getResource("Computer");
        assertEquals(10, rc.getCapacity());
        assertEquals("AVAILABLE", rc.getStatus());
        assertEquals(0.0, rc.getUtilization());
        
        Resource.createResource("Speaker", 2);
        Resource rs = Resource.getResource("Speaker");
        assertEquals(2, rs.getCapacity());
        assertEquals("AVAILABLE", rc.getStatus());
        assertEquals(0.0, rc.getUtilization());
    }
    
    @Test
    public void getResourceTest() {
    	Resource.createResource("Computer", 10);
    	Resource rc = Resource.getResource("Computer");
    	assertEquals("Computer", rc.getName());
    	
        Resource.createResource("Laptop", 5);
    	Resource rl = Resource.getResource("Laptop");
    	assertEquals("Laptop", rl.getName());
    	
    	Resource.createResource("Keyboard", 9);
    	Resource rk = Resource.getResource("Keyboard");
    	assertEquals("Keyboard", rk.getName());
    }

    @Test
    public void borrowResourceTest() {
    	Resource.createResource("Computer", 10);
        Resource rc = Resource.getResource("Computer"); 
        assertEquals(rc.getStatus(), "AVAILABLE");
        rc.borrowResource();
        assertEquals("BORROWED", rc.getStatus());
        rc.borrowResource();
        assertEquals("BORROWED", rc.getStatus());
        
        Resource.createResource("Speaker", 2);
        Resource rs = Resource.getResource("Speaker");
        assertEquals("AVAILABLE", rs.getStatus());
        rs.borrowResource();
        assertEquals("BORROWED", rs.getStatus());
    }

    @Test
    public void deleteResourceTest() {
    	Resource.createResource("Computer", 10);
        Resource rc = Resource.getResource("Computer");
        rc.deleteResources();
        assertNull(Resource.getResource("Computer"));
        assertEquals("DELETED", rc.getStatus());
    	
    	Resource.createResource("Keyboard", 9);
        Resource rk = Resource.getResource("Keyboard");
        rk.deleteResources();
        assertNull(Resource.getResource("Keyboard"));
        assertEquals("DELETED", rc.getStatus());
    }

    @Test
    public void setCapacityTest() {
    	Resource.createResource("Computer", 10);
        Resource rc = Resource.getResource("Computer");
        rc.setCapacity(20);
        assertEquals(20, rc.getCapacity());
        rc.setCapacity(-1);
        assertEquals(20, rc.getCapacity());
        
        Resource.createResource("Keyboard", 9);
        Resource rk = Resource.getResource("Keyboard");        
        rk.setCapacity(-1);
        assertEquals(9, rk.getCapacity());
    }

    @Test
    public void availabilityScheduleTest() {
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.DAY_OF_MONTH, 1);
        Calendar c2 = Calendar.getInstance();
        c2.add(Calendar.DAY_OF_MONTH, 2);
        Date d1 = new Date();
        Date d2 = c1.getTime();
        Date d3 = c2.getTime();

        Resource.createResource("Computer", 10);
        Resource rc = Resource.getResource("Computer");
        assertFalse(rc.isAvailableOnDate(d1));
        assertEquals(rc.getUtilization(), 0.0);
        rc.updateAvailability(d1, true);
        rc.updateAvailability(d2, true);
        rc.updateAvailability(d3, false);
        assertTrue(rc.isAvailableOnDate(d1));
        assertEquals(66.67, rc.getUtilization(), 0.01);
        
        Resource.createResource("Keyboard", 9);
    	Resource rk = Resource.getResource("Keyboard");
        assertFalse(rk.isAvailableOnDate(d2));
        assertEquals(rk.getUtilization(), 0.0);
        rk.updateAvailability(d1, false);
        rk.updateAvailability(d2, true);
        rk.updateAvailability(d3, false);
        assertFalse(rk.isAvailableOnDate(d1));
        assertEquals(33.33, rk.getUtilization(), 0.01);
    }

    @Test
    public void printResourceTestOne() { // https://stackoverflow.com/questions/32241057/how-to-test-a-print-method-in-java-using-junit
    	Resource.createResource("Computer", 10);
    	ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    	String expected = String.format("Resources:%nComputer - AVAILABLE (Capacity: 10, Utilization: 0.0%%)%n"); // https://stackoverflow.com/questions/207947/how-do-i-get-a-platform-independent-new-line-character
    	System.setOut(new PrintStream(outContent));
        Resource.printResources();
        assertEquals(expected, outContent.toString());
    }
    
    @Test
    public void printResourceTestTwo() { // https://stackoverflow.com/questions/32241057/how-to-test-a-print-method-in-java-using-junit
        Resource.createResource("Keyboard", 9);
    	Resource rk = Resource.getResource("Keyboard");
    	ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    	String expected = String.format("Resources:%nNull%n");
        System.setOut(new PrintStream(outContent));
        rk.deleteResources();
        Resource.printResources();
        assertEquals(expected, outContent.toString());
    }
    
    @Test
    public void returnResourceTest() {
    	Resource.createResource("Computer", 10);
    	Resource rc = Resource.getResource("Computer");
    	rc.returnResource();
    	assertEquals("AVAILABLE", rc.getStatus());
    	rc.borrowResource();
    	rc.returnResource();
    	assertEquals("AVAILABLE", rc.getStatus());
    	rc.borrowResource();
    	rc.deleteResources();
    	rc.returnResource();
    	assertEquals("DELETED", rc.getStatus());
    	
    	Resource.createResource("Speaker", 2);
    	Resource rs = Resource.getResource("Speaker");
    	rs.borrowResource();
    	rs.borrowResource();
    	rs.borrowResource();
    	rs.returnResource();
    	assertEquals("AVAILABLE", rs.getStatus());
    	rs.deleteResources();
    	rs.borrowResource();
    	rs.returnResource();
    	assertEquals("DELETED", rs.getStatus());
    }
}
