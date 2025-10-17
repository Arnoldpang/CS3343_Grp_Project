package main.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Task {
    static HashMap<Integer, Task> tasks = new HashMap<>(); // 靜態儲存所有任務，以 ID 為 key
    private static int nextId = 1; // 自動生成 ID

    private int id;
    private String name;
    private String description;
    private int studentsNumber; // 學生人數
    private long durationMinutes; // 持續時間（分鐘）
    private int priority; // 1-10，越高越優先
    private List<Resource> requiredResources; // 所需資源列表（自動分配）
    private String assignedDay; // 新增：分配的日子 (e.g., "Monday")
    private int assignedStartHour; // 新增：開始小時 (e.g., 8 for 8:00 AM)

    private enum Status { // 任務狀態
        PENDING, IN_PROGRESS, COMPLETED
    }
    private Status status;

    private Task(String name, String description, int studentsNumber, long durationMinutes, int priority, List<Resource> requiredResources) {
        this.id = nextId++;
        this.name = name;
        this.description = description;
        this.studentsNumber = studentsNumber;
        this.durationMinutes = durationMinutes;
        this.priority = priority;
        this.requiredResources = (requiredResources != null) ? requiredResources : new ArrayList<>();
        this.status = Status.PENDING;
        this.assignedDay = null; // 初始為 null，調度時設定
        this.assignedStartHour = -1; // 初始為 -1，表示未分配

        // 驗證：人數 > 0，持續時間 > 0
        if (studentsNumber <= 0 || durationMinutes <= 0) {
            throw new IllegalArgumentException("Students number and duration must be positive.");
        }
    }

    public static void createTask(String name, String description, int studentsNumber, long durationMinutes, int priority, List<Resource> requiredResources) {
        Task task = new Task(name, description, studentsNumber, durationMinutes, priority, requiredResources);
        tasks.put(task.getId(), task);
    }

    public static Task getTaskById(int id) {
        return tasks.get(id);
    }

    public static List<Task> getAllTasks() { // 新增：取得所有任務的列表
        return new ArrayList<>(tasks.values());
    }

    public static void printTasks() {
        System.out.println("Tasks:");
        for (Integer key : tasks.keySet()) {
            Task t = tasks.get(key);
            String resourceNames = t.getRequiredResources().isEmpty() ? "None" : t.getRequiredResources().get(0).getName(); // 假設單一資源
            String timeInfo = (t.assignedDay != null) ? " (" + t.assignedDay + " at " + t.assignedStartHour + ":00)" : " (Unassigned)";
            System.out.println("ID: " + key + " - Name: " + t.getName() + " - Students: " + t.getStudentsNumber() + " - Duration: " + t.getDurationMinutes() + " min" +
                    " - Priority: " + t.getPriority() + " - Resource: " + resourceNames + timeInfo + " - Status: " + t.getStatus());
        }
    }

    public static void removeTask(int id) {
        tasks.remove(id);
    }

    // Getter 方法
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getStudentsNumber() { return studentsNumber; }
    public long getDurationMinutes() { return durationMinutes; }
    public int getPriority() { return priority; }
    public String getStatus() { return status.toString(); }
    public List<Resource> getRequiredResources() { return requiredResources; }
    public String getAssignedDay() { return assignedDay; } // 新增 getter
    public int getAssignedStartHour() { return assignedStartHour; } // 新增 getter

    // Setter 方法（供調度使用）
    public void setAssignedDay(String day) {
        this.assignedDay = day;
    }

    public void setAssignedStartHour(int hour) {
        if (hour >= 0 && hour < 24) {
            this.assignedStartHour = hour;
        } else {
            throw new IllegalArgumentException("Invalid hour.");
        }
    }

    // 更新方法（調整為更新新屬性，如果需要）
    public void updateDetails(String newDescription, int newStudentsNumber, long newDurationMinutes) {
        this.description = newDescription;
        this.studentsNumber = newStudentsNumber;
        this.durationMinutes = newDurationMinutes;
        if (studentsNumber <= 0 || durationMinutes <= 0) {
            throw new IllegalArgumentException("Students number and duration must be positive.");
        }
    }

    // ... (其他方法，如果有)
}