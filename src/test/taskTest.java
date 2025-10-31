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

public class taskTest {
    @BeforeEach
    public void setUp() {
    	Task.createTask("test", "test", new Date(2025,10,29,16,0), new Date(2025,10,29,17,0), 1, null);
    	// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Date.html
    }

    @Test
    public void createTaskTest() {
    	Task tsk = Task.getTaskById(2);
    	assertEquals(tsk,Task.getTaskById(2));
    }
    
    @Test
    public void getDescriptionTest() {
    	Task tsk = Task.getTaskById(2);
    	String expected = "test";
    	assertEquals(expected,tsk.getDescription());
    }
    
    @Test
    public void getStartTimeTest() {
    	Task tsk = Task.getTaskById(2);
    	Date expected = new Date(2025,10,29,16,0);
    	assertEquals(expected,tsk.getStartTime());
    }
    
    @Test
    public void getEndTimeTest() {
    	Task tsk = Task.getTaskById(2);
    	Date expected = new Date(2025,10,29,17,0);
    	assertEquals(expected,tsk.getEndTime());
    }
    
    @Test
    public void getRequiredResourcesTest() {
    	List<Resource> resources = new ArrayList<Resource>();
    	Resource.createResource("Computer", 10);
    	Resource res = Resource.getResource("Computer");
    	resources.add(res);
    	Task.createTask("test", "test", new Date(2025,10,29,16,0), new Date(2025,10,29,17,0), 0, resources);
    	assertEquals(resources,Task.getTaskById(10).getRequiredResources());
    }
    
    @Test
    public void exceptionHandlingTest() {
    	Exception exception = assertThrows(IllegalArgumentException.class, () -> Task.createTask("test", "test", new Date(2025,10,29,18,0), new Date(2025,10,29,17,0), 10, null));
    	//https://docs.junit.org/current/user-guide/#running-tests
    	assertEquals(exception.getMessage(),"End time must be after start time.");
    }
    
    @Test
    public void updateDetailsTest() {
    	Task tsk = Task.getTaskById(3);
    	tsk.updateDetails("Updated!",new Date(2025,10,31,16,0), new Date(2025,10,31,17,0));
    	assertEquals("Updated!",tsk.getDescription());
    	assertEquals(new Date(2025,10,31,16,0),tsk.getStartTime());
    	assertEquals(new Date(2025,10,31,17,0),tsk.getEndTime());
    	tsk.updateDetails("Updated again!",new Date(2025,11,1,16,0), new Date(2025,10,31,17,0));
    	Exception exception = assertThrows(IllegalArgumentException.class, () -> tsk.updateDetails("Updated again!",new Date(2025,11,1,16,0), new Date(2025,10,31,17,0)));
    	assertEquals(exception.getMessage(),"End time must be after start time.");
    }
    
    @Test
    public void removeTasksTest() {
    	Task.removeTask(1);
    	assertNull(Task.getTaskById(1));
    }
    
    @Test
    public void printTasksTest() {
    	for (int i=2;i<11;i++) {
    		Task.removeTask(i);
    	}
    	ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Task.printTasks();
        String expected = String.format("Tasks:%nID: 11 - Name: test - Status: PENDING - Priority: 1 - Duration: 3600000 ms%n");
        assertEquals(expected,outContent.toString());
    }
}
	 
