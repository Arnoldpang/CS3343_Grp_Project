package main.command;

import java.util.HashMap;
import java.util.Scanner;

public class manageBookingCommand implements Command{
    static HashMap<Integer, Command> commandList;
    public manageBookingCommand(){
        init();
    }

    @Override
    public void execute(Scanner sc) {
        System.out.print("Input number for next action(1: Display all booking, 2: Create a booking, 3: Cancel a booking): ");
        int input = sc.nextInt();
        sc.nextLine();
        commandList.get(input).execute(sc);
    }

    public void init(){
        commandList = new HashMap<>();
        commandList.put(1, new displayBookingCommand());
        commandList.put(2, new createBookingCommand());
        commandList.put(3, new cancelBookingCommand());
    }
}
