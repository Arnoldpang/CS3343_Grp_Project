package main.command;

import main.item.Task;
import java.util.Scanner;

public class updateTaskCommand implements Command {
    public updateTaskCommand() {}

    @Override
    public void execute(Scanner sc) {
        System.out.print("Input task ID to update: ");
        int taskId = sc.nextInt();
        sc.nextLine(); // 消耗換行符

        Task task = Task.getTaskById(taskId);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        System.out.print("Input new description (or press Enter to skip): ");
        String newDescription = sc.nextLine();
        if (newDescription.isEmpty()) {
            newDescription = task.getDescription();
        }

        System.out.print("Input new students number (or 0 to skip): ");
        int newStudentsNumber = sc.nextInt();
        if (newStudentsNumber == 0) {
            newStudentsNumber = task.getStudentsNumber();
        }
        sc.nextLine(); // 消耗

        System.out.print("Input new duration minutes (or 0 to skip): ");
        long newDurationMinutes = sc.nextLong();
        if (newDurationMinutes == 0) {
            newDurationMinutes = task.getDurationMinutes();
        }
        sc.nextLine(); // 消耗

        try {
            task.updateDetails(newDescription, newStudentsNumber, newDurationMinutes);
            // 可選：重新分配資源，如果人數變了
            // ... (如果需要，呼叫分配邏輯)
            System.out.println("Task updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}