package main.item;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
public class Resource {
    private static HashMap<String, Resource> Resources = new HashMap<>();
    private static HashMap<String, Resource> DeletedItem = new HashMap<>();
    private String name; // 修改：從 public 改為 private 以符合封裝
    private int capacity; // 新增：資源容量
    HashMap<Date, Boolean> availabilitySchedule; // 新增：可用性時間表（日期 -> 是否可用）
    private enum status { // 修改：從 static enum 改為 private enum
        AVAILABLE, BORROWED, DELETED
    }
    private status status;
    private Resource(String name, int capacity) { // 修改：constructor 新增 capacity 參數，並初始化 availabilitySchedule
        this.name = name;
        this.capacity = capacity;
        this.status = status.AVAILABLE;
        this.availabilitySchedule = new HashMap<>(); // 初始化為空 Map，可後續新增日期
    }
    public static void createResource(String name, int capacity) { // 修改：createResource 新增 capacity 參數
        Resource rs = new Resource(name, capacity);
        Resources.put(name, rs);
    }
    public static Resource getResource(String name){
        for (Resource r : Resources.values()){
            if (r.getName().toLowerCase().equals(name.toLowerCase())) {
                return r;
            }
        }
        return null;
    }
    public static void printResources(){
        System.out.println("Resources:");
        for (String key : Resources.keySet()) {
            Resource rs = Resources.get(key);
            System.out.println(key + " - " + rs.getStatus() + " (Capacity: " + rs.getCapacity() + ", Utilization: " + rs.getUtilization() + "%)");
        }
        if(Resources.isEmpty())
            System.out.println("Null");
    }
    private void setStatus(status status) { // 修改：從 public 改為 private，內部使用
        this.status = status;
    }
    public void deleteResources() {
        this.setStatus(status.DELETED);
        DeletedItem.put(name, this);
        Resources.remove(name); // 新增：真正從 Resources 中移除
        this.availabilitySchedule.clear(); // 新增：清理可用性時間表
    }
    public void borrowResource() {
        if (this.status == status.AVAILABLE) { // 新增：檢查是否可用，避免重複借用
            this.setStatus(status.BORROWED);
        } else {
            System.out.println("Resource is not available for borrowing.");
        }
    }
    public void returnResource(){
        if (this.status == status.BORROWED) { // 新增：檢查是否可用，避免重複借用
            this.setStatus(status.AVAILABLE);
        } else if(this.status == status.DELETED) {
            System.out.println("Cannot return deleted resource.");
        }else {
            System.out.println("Resource is available for borrowing. No need to return.");
        }
    }
    public String getStatus(){
        return this.status.toString();
    }
    // 新增：getter 和 setter for capacity
    public int getCapacity() {
        return this.capacity;
    }
    public void setCapacity(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
        } else {
            System.out.println("Capacity must be positive.");
        }
    }
    public String getName(){return this.name;}
    // 新增：更新特定日期的可用性
    public void updateAvailability(Date date, boolean isAvailable) {
        this.availabilitySchedule.put(date, isAvailable);
    }
    public boolean isAvailableOnDate(Date date) {
        return this.availabilitySchedule.getOrDefault(date, true); // 改為 true
    }
    // 新增：計算利用率（忙碌比例）
    public double getUtilization() {
        if (availabilitySchedule.isEmpty()) {
            return 0.0;
        }
        long busyCount = availabilitySchedule.values().stream().filter(b -> !b).count();
        return (double) busyCount / availabilitySchedule.size() * 100;
    }
    public static List<Resource> getAllResources() {
        return new ArrayList<>(Resources.values());
    }
}