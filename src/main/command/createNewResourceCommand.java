package main.command;

import main.item.Resource;

import java.util.Scanner;

public class createNewResourceCommand implements Command {
    public createNewResourceCommand() {}

    @Override
    public void execute(Scanner sc) {
        System.out.print("Please input resource capacity: ");
        int capacity = sc.nextInt();
        sc.nextLine();
        System.out.print("Please input resource name: ");
        String name = sc.nextLine();
        Resource.createResource(name, capacity);
        System.out.println("Resource create Successfully");
    }
}