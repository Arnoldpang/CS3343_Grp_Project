package main.command;

import main.item.Resource;
import java.util.Date; // 用於日期
import java.text.SimpleDateFormat; // 用於解析日期字串
import java.text.ParseException; // 處理解析錯誤
import java.util.Scanner;

public class updateResourceCommand implements Command {
    public updateResourceCommand() {}

    @Override
    public void execute(Scanner sc) {
        System.out.print("Input the resource name to update: ");
        String resourceName = sc.next();

        Resource rs = Resource.getResource(resourceName);
        if (rs == null) {
            System.out.println("Resource not found.");
            return;
        }

        // 讓使用者選擇更新類型
        System.out.print("Choose update type (1: Capacity, 2: Availability): ");
        int updateType = sc.nextInt();

        if (updateType == 1) {
            // 更新 capacity
            System.out.print("Input new capacity: ");
            int newCapacity = sc.nextInt();
            rs.setCapacity(newCapacity); // 使用 setter 更新
            System.out.println("Capacity updated successfully.");
        } else if (updateType == 2) {
            // 更新 availability（輸入日期並設定可用）
            System.out.print("Input the date to update (YYYY-MM-DD): ");
            String dateStr = sc.next();
            System.out.print("Set available? (true/false): ");
            boolean isAvailable = sc.nextBoolean();

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date updateDate = sdf.parse(dateStr);

                rs.updateAvailability(updateDate, isAvailable); // 使用新方法更新
                System.out.println("Availability updated successfully for " + dateStr);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        } else {
            System.out.println("Invalid update type.");
        }
    }
}