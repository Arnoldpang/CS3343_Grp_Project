package main.command;

import main.item.Booking;
import main.item.loginStatus;

import java.util.Scanner;

public class cancelBookingCommand implements Command{
    @Override
    public void execute(Scanner sc) {
        String currentUser = loginStatus.getInstance().getUsername();
//        Booking.cancelBooking(loginStatus.getInstance().getUsername(), sc);
        int count = Booking.printBooking(currentUser);
        if(count==0)
            System.out.println("There are no booking for you to cancel");
        else{
            System.out.println("Please input resource name to cancel related booking");
            String rsString = sc.next();
            Booking.cancelBooking(rsString);
        }
    }
}
