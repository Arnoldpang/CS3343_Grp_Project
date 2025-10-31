package main.item;

import java.util.ArrayList;
import java.util.Scanner;

public class Booking {
    private static ArrayList<Booking> BookingRecords = new ArrayList<>();;
    private String username;
    private Resource rs;
    private ScheduleManager sc;

    private Booking(String username, Resource rs){
        this.username = username;
        this.rs = rs;
    }

    public static void createBooking(String rsString){
        Resource rs = Resource.getResource(rsString);
        if(rs==null)
            System.out.println("Resource not found");
        else{
            Booking newBooking = new Booking(loginStatus.getInstance().getUsername(), rs);
            BookingRecords.add(newBooking);
            System.out.println("Booking created successfully.");
        }

    }

    // Only for Admin print out
    public static void printBooking(){
        System.out.println("All Booking:");
        for (Booking b : BookingRecords){
            System.out.println(b.rs.getName() + " - " + b.username);
        }
        if(BookingRecords.isEmpty())
            System.out.println("Null");
    }

    // View the specific user booking
    public static int printBooking(String username){
        System.out.println(username + " Booking:");
        int count=0;
        for (Booking b : BookingRecords){
            if(username.equals(b.username)) {
                count++;
                System.out.println(b.rs.getName() + " - " + b.username);
            }
        }
        if(count==0) {
            System.out.println("Null");
        }
        return count;
    }

    public static void cancelBooking(String rsString){
        if(BookingRecords.remove(Resource.getResource(rsString)))
            System.out.println("Cancel successfully");
        else
            System.out.println("Cannot cancel related booking, please check again or find admin");

    }
}
