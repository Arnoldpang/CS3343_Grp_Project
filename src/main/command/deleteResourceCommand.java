package main.command;

import main.item.Resource;

import java.util.Scanner;

public class deleteResourceCommand implements Command{
    public deleteResourceCommand(){}

    @Override
    public void execute(Scanner sc) {
        System.out.println("Input target resource name");
        String input = sc.next();
        Resource rs = Resource.getResource(input);
        rs.deleteResources();
    }
}
