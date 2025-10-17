package main.command;

import main.item.Task;
import java.util.Scanner;

public class deleteTaskCommand implements Command {
    public deleteTaskCommand() {}

    @Override
    public void execute(Scanner sc) {
        System.out.print("Input task ID to delete: ");
        int taskId = sc.nextInt();
        sc.nextLine(); // 消耗換行符

        Task task = Task.getTaskById(taskId);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        Task.removeTask(taskId);
        System.out.println("Task deleted successfully.");
    }
}