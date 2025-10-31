package main.command;
import main.item.ScheduleManager;
import main.item.Task;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
public class printCalendarCommand implements Command {
    public printCalendarCommand() {}
    @Override
    public void execute(Scanner sc) {
        List<Task> allTasks = Task.getAllTasks();
        Set<String> majors = new HashSet<>();
        for (Task t : allTasks) {
            majors.add(t.getMajor());
        }
        if (majors.isEmpty()) {
            System.out.println("No majors available.");
            return;
        }
        System.out.println("Available majors: " + String.join(", ", majors));
        System.out.print("Input major to print schedule (or 'all'): ");
        String input = sc.nextLine().trim();
        ScheduleManager manager = new ScheduleManager();
        if (input.equalsIgnoreCase("all")) {
            manager.printSchedule();
        } else if (majors.contains(input)) {
            manager.printScheduleForMajor(input);
        } else {
            System.out.println("Invalid major: " + input);
        }
    }
}