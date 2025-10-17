package main.item;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Task {
    private static HashMap<Integer, Task> tasks = new HashMap<>(); // 靜態儲存所有任務，以 ID 為 key
    private static int nextId = 1; // 自動生成 ID

    private int id;
    private String name;
    private String description;
    private Date startTime;
    private Date endTime;
    private int priority; // 1-10，越高越優先
    private List<Resource> requiredResources; // 所需資源列表

    private enum Status { // 任務狀態
        PENDING, IN_PROGRESS, COMPLETED
    }
    private Status status;

    private Task(String name, String description, Date startTime, Date endTime, int priority, List<Resource> requiredResources) {
        this.id = nextId++;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.requiredResources = (requiredResources != null) ? requiredResources : new ArrayList<>();
        this.status = Status.PENDING;

        // 新增計算/驗證：檢查時間有效性
        if (endTime.before(startTime)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }
    }

    public static void createTask(String name, String description, Date startTime, Date endTime, int priority, List<Resource> requiredResources) {
        Task task = new Task(name, description, startTime, endTime, priority, requiredResources);
        tasks.put(task.getId(), task);
    }

    public static Task getTaskById(int id) {
        return tasks.get(id);
    }

    public static void printTasks() {
        System.out.println("Tasks:");
        for (Integer key : tasks.keySet()) {
            Task t = tasks.get(key);
            System.out.println("ID: " + key + " - Name: " + t.getName() + " - Status: " + t.getStatus() + " - Priority: " + t.getPriority() + " - Duration: " + t.calculateDuration() + " ms");
        }
    }

    public static void removeTask(int id) {
        tasks.remove(id);
    }


    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; } // 新增
    public Date getStartTime() { return startTime; } // 新增
    public Date getEndTime() { return endTime; } // 新增
    public int getPriority() { return priority; }
    public String getStatus() { return status.toString(); }
    public List<Resource> getRequiredResources() { return requiredResources; } // 新增，如果需要

    // 更新方法（例如，更新描述或時間，包含重新驗證）
    public void updateDetails(String newDescription, Date newStartTime, Date newEndTime) {
        this.description = newDescription;
        this.startTime = newStartTime;
        this.endTime = newEndTime;
        if (endTime.before(startTime)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }
    }

    // 新增計算：持續時間（毫秒）
    public long calculateDuration() {
        return endTime.getTime() - startTime.getTime();
    }


}