package main.command;

import main.item.Booking;

import java.util.Scanner;

public class createBookingCommand implements Command{
    public createBookingCommand(){}

    @Override
    public void execute(Scanner sc) {
        System.out.print("Please input resource name:");
        String rsString = sc.next();
        Booking.createBooking(rsString);
    }
}
