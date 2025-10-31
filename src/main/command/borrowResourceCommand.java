package main.command;

import main.item.Resource;
import org.junit.jupiter.api.parallel.Resources;

import java.util.Scanner;

public class borrowResourceCommand implements Command{
    public borrowResourceCommand(){}

    @Override
    public void execute(Scanner sc) {
        System.out.print("Input the resource name you want to borrow ");
        String input = sc.nextLine();
        Resource rs = Resource.getResource(input);
        if(rs != null){
            rs.borrowResource();
            System.out.println("Borrowed successfully");
        }
        else{
            System.out.println("Resource not found");
        }

    }
}
