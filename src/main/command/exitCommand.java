package main.command;

import java.util.Scanner;

public class exitCommand implements Command{
    public exitCommand() {}

    @Override
    public void execute(Scanner sc) {
        System.exit(0);
    }
}
