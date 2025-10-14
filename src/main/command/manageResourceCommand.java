package main.command;

import java.util.HashMap;
import java.util.Scanner;

public class manageResourceCommand implements Command{
    static HashMap<Integer, Command> commandList;
    public manageResourceCommand(){
        init();
    }

    @Override
    public void execute(Scanner sc){
        System.out.print("Input number for next action(1: Display all resource, 2: Create a new resource, 3: Borrow a resource, 4: Deleted a resource): ");
        int input = sc.nextInt();
        commandList.get(input).execute(sc);
    }

    public void init(){
        commandList.put(1, new displayResourcesCommand());
        commandList.put(2, new createNewResourceCommand());
        commandList.put(3, new borrowResourceCommand());
        commandList.put(4, new deleteResourceCommand());
    }
}
