package main.command;

import main.item.Resource;

import java.util.Scanner;

public class displayResourcesCommand implements Command{
    public displayResourcesCommand(){}

    @Override
    public void execute(Scanner sc) {
        Resource.printResources();
    }
}
