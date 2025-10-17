package main.item;

import java.util.*;

import static main.item.Task.tasks;

public class ScheduleManager {
    private static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static final int START_HOUR = 8; // 8 AM
    private static final int END_HOUR = 23; // 11 PM
    private static final int SLOT_DURATION_MIN = 60; // 每槽 60 min

    // 儲存分配：Map<Day, Map<Hour, Task>>
    private Map<String, Map<Integer, Task>> schedule = new HashMap<>();

    public ScheduleManager() {
        for (String day : DAYS) {
            Map<Integer, Task> daySlots = new HashMap<>();
            for (int hour = START_HOUR; hour < END_HOUR; hour++) {
                daySlots.put(hour, null); // 初始空槽
            }
            schedule.put(day, daySlots);
        }
    }

    public void optimizeSchedule() {
        List<Task> allTasks = new ArrayList<>(Task.getAllTasks());
        allTasks.sort(Comparator.comparingInt(Task::getPriority).reversed()); // priority 降序

        // 清空現有分配
        clearSchedule();
        for (Task task : allTasks) {
            task.getRequiredResources().clear(); // 重置房間
        }

        // 為每個任務分配
        List<Resource> allResources = Resource.getAllResources();
        allResources.sort(Comparator.comparingDouble(this::getResourceLoad)  // 先低負載
                .thenComparing(Comparator.comparingInt(Resource::getCapacity).reversed()));

        for (Task task : allTasks) {
            // 動態排序資源：優先利用率低（空置）的，然後容量大
            allResources.sort(Comparator.comparingDouble(Resource::getUtilization)
                    .thenComparing(Comparator.comparingInt(Resource::getCapacity).reversed()));

            boolean allocated = false;
            int slotsNeeded = (int) Math.ceil((double) task.getDurationMinutes() / SLOT_DURATION_MIN);

            outer: for (String day : DAYS) {
                Map<Integer, Task> daySlots = schedule.get(day);
                for (int hour = START_HOUR; hour <= END_HOUR - slotsNeeded; hour++) {
                    // 檢查連續槽是否空
                    boolean free = true;
                    for (int s = 0; s < slotsNeeded; s++) {
                        if (daySlots.get(hour + s) != null) {
                            free = false;
                            break;
                        }
                    }
                    if (!free) continue;

                    // 找合適房間
                    for (Resource res : allResources) {
                        if (res.getCapacity() >= task.getStudentsNumber() && isResourceFree(res, day, hour, slotsNeeded)) {
                            // 分配時間槽
                            for (int s = 0; s < slotsNeeded; s++) {
                                daySlots.put(hour + s, task);
                            }
                            task.getRequiredResources().add(res);
                            updateResourceAvailability(res, day, hour, slotsNeeded, false); // 標記忙碌

                            // 新增：設定任務的分配日子和開始小時
                            task.setAssignedDay(day);
                            task.setAssignedStartHour(hour);

                            allocated = true;
                            break outer;
                        }
                    }
                }
            }
            if (!allocated) {
                System.out.println("Cannot allocate task: " + task.getName() + " (no free slot/room)");
            }
        }
    }

    private boolean isResourceFree(Resource res, String day, int startHour, int slotsNeeded) {
        Calendar cal = Calendar.getInstance();
        cal.set(2025, 9, 17); // October 17, 2025 基準，調整 day
        int dayIndex = Arrays.asList(DAYS).indexOf(day);
        cal.add(Calendar.DAY_OF_YEAR, dayIndex); // 模擬不同日子
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        for (int s = 0; s < slotsNeeded; s++) {
            cal.set(Calendar.HOUR_OF_DAY, startHour + s);
            Date slotDate = cal.getTime();
            if (!res.isAvailableOnDate(slotDate)) {
                return false;
            }
        }
        return true;
    }

    private void updateResourceAvailability(Resource res, String day, int startHour, int slotsNeeded, boolean isAvailable) {
        Calendar cal = Calendar.getInstance();
        cal.set(2025, 9, 17);
        int dayIndex = Arrays.asList(DAYS).indexOf(day);
        cal.add(Calendar.DAY_OF_YEAR, dayIndex);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        for (int s = 0; s < slotsNeeded; s++) {
            cal.set(Calendar.HOUR_OF_DAY, startHour + s);
            Date slotDate = cal.getTime();
            res.updateAvailability(slotDate, isAvailable);
        }
    }

    private void clearSchedule() {
        for (String day : DAYS) {
            Map<Integer, Task> daySlots = schedule.get(day);
            for (int hour = START_HOUR; hour < END_HOUR; hour++) {
                daySlots.put(hour, null);
            }
        }
    }

    public void printSchedule() {
        List<Task> allTasks = Task.getAllTasks();
        Set<String> majors = new HashSet<>();
        for (Task t : allTasks) {
            majors.add(t.getMajor());
        }

        for (String major : majors) {
            System.out.println("\n=== Schedule for Major: " + major + " ===");
            for (String day : DAYS) {
                System.out.println("\n# Day: " + day);
                Map<Integer, Task> daySlots = schedule.get(day);
                for (int hour = START_HOUR; hour < END_HOUR; hour++) {
                    Task t = daySlots.get(hour);
                    String taskInfo = (t != null && t.getMajor().equals(major)) ? t.getName() + " (ID:" + t.getId() + ", Students:" + t.getStudentsNumber() + ")" : "Free";
                    System.out.println(hour + ":00 - " + (hour+1) + ":00 : " + taskInfo);
                }
            }
        }
    }

    private double getResourceLoad(Resource res) {
        if (res.availabilitySchedule.isEmpty()) {
            return 0.0;
        }
        long busyCount = res.availabilitySchedule.values().stream().filter(b -> !b).count(); // false = 忙碌
        return (double) busyCount / res.availabilitySchedule.size();
    }

    // 新增到 Task：靜態 getAllTasks()
    public static List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
}