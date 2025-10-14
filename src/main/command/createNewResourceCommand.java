package main.command;

import main.item.Resource;

import java.util.Scanner;

public class createNewResourceCommand implements Command{
    public createNewResourceCommand(){}


    @Override
    public void execute(Scanner sc) {
        System.out.print("Please input resource name: ");
        Resource.createResource(sc.next());
        System.out.println("Resource create Successfully");
    }
}
