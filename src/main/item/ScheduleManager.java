package main.item;
import java.util.*;
import static main.item.Task.tasks;
public class ScheduleManager {
    private static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static final int START_HOUR = 8; // 8 AM
    private static final int END_HOUR = 23; // 11 PM
    private static final int SLOT_DURATION_MIN = 60; // 每槽 60 min
    public ScheduleManager() {
    }
    public void optimizeSchedule() {
        List<Task> allTasks = new ArrayList<>(Task.getAllTasks());
        allTasks.sort(Comparator.comparingInt(Task::getPriority).reversed()
                .thenComparing(Comparator.comparingInt(Task::getStudentsNumber).reversed())); // Add sorting by students desc
// 初始化所有資源的可用性
        initializeAvailability();
// 清空現有分配
        for (Task task : allTasks) {
            task.getRequiredResources().clear(); // 重置房間
            task.setAssignedDay(null);
            task.setAssignedStartHour(-1);
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
                for (int hour = START_HOUR; hour <= END_HOUR - slotsNeeded; hour++) {
// 找合適房間
                    for (Resource res : allResources) {
                        if (res.getCapacity() >= task.getStudentsNumber() && isResourceFree(res, day, hour, slotsNeeded)) {
// 檢查同 major 是否時間衝突
                            boolean timeConflict = false;
                            for (Task other : allTasks) {
                                if (other != task && other.getMajor().equals(task.getMajor()) &&
                                        other.getAssignedDay() != null && other.getAssignedDay().equals(day)) {
                                    int otherStart = other.getAssignedStartHour();
                                    int otherSlots = (int) Math.ceil((double) other.getDurationMinutes() / SLOT_DURATION_MIN);
                                    int otherEnd = otherStart + otherSlots - 1;
                                    int thisStart = hour;
                                    int thisEnd = hour + slotsNeeded - 1;
                                    if (Math.max(thisStart, otherStart) <= Math.min(thisEnd, otherEnd)) {
                                        timeConflict = true;
                                        break;
                                    }
                                }
                            }
                            if (timeConflict) continue;
// 分配
                            task.getRequiredResources().add(res);
                            updateResourceAvailability(res, day, hour, slotsNeeded, false);
                            task.setAssignedDay(day);
                            task.setAssignedStartHour(hour);
                            allocated = true;
                            break outer;
                        }
                    }
                }
            }
            if (!allocated) {
                System.out.println("Could not allocate task: " + task.getName());
            }
        }
    }
    private void initializeAvailability() {
        List<Resource> allResources = Resource.getAllResources();
        Calendar cal = Calendar.getInstance();
        cal.set(2025, 9, 17); // 基準日期，假設為 Monday
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        for (int d = 0; d < DAYS.length; d++) {
            if (d > 0) {
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
            for (int hour = START_HOUR; hour < END_HOUR; hour++) {
                cal.set(Calendar.HOUR_OF_DAY, hour);
                Date slotDate = cal.getTime();
                for (Resource res : allResources) {
                    res.updateAvailability(slotDate, true);
                }
            }
        }
    }
    private boolean isResourceFree(Resource res, String day, int startHour, int slotsNeeded) {
        Calendar cal = Calendar.getInstance();
        cal.set(2025, 9, 17); // 基準
        int dayIndex = Arrays.asList(DAYS).indexOf(day);
        cal.add(Calendar.DAY_OF_YEAR, dayIndex);
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
                Map<Integer, String> slotInfo = new HashMap<>();
                for (Task t : allTasks) {
                    if (t.getMajor().equals(major) && t.getAssignedDay() != null && t.getAssignedDay().equals(day)) {
                        int start = t.getAssignedStartHour();
                        int slots = (int) Math.ceil((double) t.getDurationMinutes() / SLOT_DURATION_MIN);
                        for (int s = 0; s < slots; s++) {
                            int h = start + s;
                            slotInfo.put(h, t.getName() + " (ID:" + t.getId() + ", Students:" + t.getStudentsNumber() + ")");
                        }
                    }
                }
                for (int hour = START_HOUR; hour < END_HOUR; hour++) {
                    String taskInfo = slotInfo.getOrDefault(hour, "Free");
                    System.out.println(hour + ":00 - " + (hour + 1) + ":00 : " + taskInfo);
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