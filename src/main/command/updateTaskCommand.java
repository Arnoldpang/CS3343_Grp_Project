package main.command;

import main.item.Task;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            newDescription = task.getDescription(); // 保持原值
        }

        System.out.print("Input new start time (YYYY-MM-DD HH:mm, or press Enter to skip): ");
        String startStr = sc.nextLine();
        Date newStartTime = task.getStartTime(); // 預設原值
        if (!startStr.isEmpty()) {
            try {
                newStartTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startStr);
            } catch (ParseException e) {
                System.out.println("Invalid start time format. Keeping original.");
            }
        }

        System.out.print("Input new end time (YYYY-MM-DD HH:mm, or press Enter to skip): ");
        String endStr = sc.nextLine();
        Date newEndTime = task.getEndTime(); // 預設原值
        if (!endStr.isEmpty()) {
            try {
                newEndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endStr);
            } catch (ParseException e) {
                System.out.println("Invalid end time format. Keeping original.");
            }
        }

        try {
            task.updateDetails(newDescription, newStartTime, newEndTime);
            System.out.println("Task updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}