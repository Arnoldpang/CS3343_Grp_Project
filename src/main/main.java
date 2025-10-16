package main;

import main.command.*;
import main.item.loginStatus;

import java.util.HashMap;
import java.util.Scanner;

public class main {
	static HashMap<Integer, Command> commandList;
    public static Scanner sc;

    public static void main(String[] args) {
    	init();
        boolean login=false;
        do{
            if(!login){
                System.out.print("Input number of commands (0: Exit, 9: Login): ");
                int inputInt =sc.nextInt();
                sc.nextLine();
                commandList.get(inputInt).execute(sc);
            }else{
                // todo
                System.out.print("Input number of commands (0: Exit, 1: logout, 2: manage Resource, 3: manage Task): ");
                int inputInt =sc.nextInt();
                sc.nextLine();
                commandList.get(inputInt).execute(sc);
            }


            login = loginStatus.getInstance().getLoginStatus();
        }while(true);
    }
    public static void init() {
        sc = new Scanner(System.in);
    	commandList = new HashMap<>();
        commandList.put(0, new exitCommand());
        commandList.put(9, new loginCommand());
        commandList.put(1, new logoutCommand());
        commandList.put(2, new manageResourceCommand());
        commandList.put(3, new manageTaskCommand());
    }
}
