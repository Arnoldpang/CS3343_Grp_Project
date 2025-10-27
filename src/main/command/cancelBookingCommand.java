package main.command;

import main.item.Booking;
import main.item.loginStatus;

import java.util.Scanner;

public class cancelBookingCommand implements Command{
    @Override
    public void execute(Scanner sc) {
        Booking.cancelBooking(loginStatus.getInstance().getUsername(), sc);
    }
}
