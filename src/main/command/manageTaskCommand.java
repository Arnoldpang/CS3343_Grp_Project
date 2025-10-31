package main.command;
import java.util.HashMap;
import java.util.Scanner;
public class manageTaskCommand implements Command {
    static HashMap<Integer, Command> commandList;
    public manageTaskCommand() {
        init();
    }
    @Override
    public void execute(Scanner sc) {
        System.out.print("Input number for next action(1: Display all tasks, 2: Create a new task, 3: Update a task, 4: Delete a task, 5: Print calendar): ");
        int input = sc.nextInt();
        sc.nextLine(); // 消耗換行符，避免輸入問題
        commandList.get(input).execute(sc);
    }
    public void init() {
        commandList = new HashMap<>();
        commandList.put(1, new displayTasksCommand());
        commandList.put(2, new createTaskCommand());
        commandList.put(3, new updateTaskCommand());
        commandList.put(4, new deleteTaskCommand());
        commandList.put(5, new printCalendarCommand());
    }
}