package main.command;

import main.item.Resource;
import main.item.Task;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class createTaskCommand implements Command {
    public createTaskCommand() {}

    @Override
    public void execute(Scanner sc) {
        System.out.print("Input task name: ");
        String name = sc.nextLine();

        System.out.print("Input task description: ");
        String description = sc.nextLine();

        System.out.print("Input start time (YYYY-MM-DD HH:mm): ");
        String startStr = sc.nextLine();
        System.out.print("Input end time (YYYY-MM-DD HH:mm): ");
        String endStr = sc.nextLine();

        System.out.print("Input priority (1-10): ");
        int priority = sc.nextInt();
        sc.nextLine(); // 消耗換行符

        // 解析日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date startTime = sdf.parse(startStr);
            Date endTime = sdf.parse(endStr);

            // 處理 requiredResources（輸入資源名稱，用逗號分隔）
            System.out.print("Input required resource names (comma-separated, or leave empty): ");
            String resourcesStr = sc.nextLine();
            List<Resource> requiredResources = new ArrayList<>();
            if (!resourcesStr.isEmpty()) {
                String[] resourceNames = resourcesStr.split(",");
                for (String resName : resourceNames) {
                    Resource res = Resource.getResource(resName.trim());
                    if (res != null) {
                        requiredResources.add(res);
                    } else {
                        System.out.println("Resource " + resName + " not found. Skipping.");
                    }
                }
            }

            Task.createTask(name, description, startTime, endTime, priority, requiredResources);
            System.out.println("Task created successfully.");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Task creation failed.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}