package main.command;

import main.item.Booking;
import main.item.loginStatus;

import java.util.Scanner;

public class displayBookingCommand implements Command{
    public displayBookingCommand(){}

    @Override
    public void execute(Scanner sc) {
        String role = loginStatus.getInstance().getRole();
        if(role.equals("ADMIN")){
            Booking.printBooking();
        }else{
            Booking.printBooking(loginStatus.getInstance().getUsername());
        }
    }
}
