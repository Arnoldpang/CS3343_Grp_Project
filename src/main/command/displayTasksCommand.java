package main.command;

import main.item.Task;
import java.util.Scanner;

public class displayTasksCommand implements Command {
    public displayTasksCommand() {}

    @Override
    public void execute(Scanner sc) {
        Task.printTasks();
    }
}