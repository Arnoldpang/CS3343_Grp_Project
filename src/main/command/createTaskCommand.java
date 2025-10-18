package main.command;

import main.item.Resource;
import main.item.Task;
import main.item.ScheduleManager; // 假設 ScheduleManager 在 main.item
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class createTaskCommand implements Command {
    public createTaskCommand() {}

    @Override
    public void execute(Scanner sc) {

        System.out.print("Input major (e.g., computer science, bio): "); // 新增輸入
        String major = sc.nextLine();
        System.out.print("Input number of classes to create: ");
        int numClasses = sc.nextInt();
        sc.nextLine(); // 消耗換行符

        List<TaskInfo> taskInfos = new ArrayList<>();
        for (int i = 0; i < numClasses; i++) {
            System.out.println("Class " + (i + 1) + ":");
            System.out.print("Input class name: ");
            String name = sc.nextLine();

            System.out.print("Input class description: ");
            String description = sc.nextLine();

            System.out.print("Input students number: ");
            int studentsNumber = sc.nextInt();
            sc.nextLine();

            System.out.print("Input duration (minutes): ");
            long durationMinutes = sc.nextLong();
            sc.nextLine();

            System.out.print("Input priority (1-10): ");
            int priority = sc.nextInt();
            sc.nextLine();

            taskInfos.add(new TaskInfo(name, description, studentsNumber, durationMinutes, priority, major));
        }

        // 現在創建所有 Task
        for (TaskInfo info : taskInfos) {
            Task.createTask(info.name, info.description, info.studentsNumber, info.durationMinutes, info.priority,info.major); // requiredResources 後續分配
        }


        ScheduleManager manager = new ScheduleManager();
        manager.optimizeSchedule();


        System.out.println("Tasks created and schedule allocated successfully.");
    }

    private static class TaskInfo {
        String name;
        String description;
        int studentsNumber;
        long durationMinutes;
        int priority;
        String major;

        TaskInfo(String name, String description, int studentsNumber, long durationMinutes, int priority, String major) {
            this.name = name;
            this.description = description;
            this.studentsNumber = studentsNumber;
            this.durationMinutes = durationMinutes;
            this.priority = priority;
            this.major = major;
        }
    }
}