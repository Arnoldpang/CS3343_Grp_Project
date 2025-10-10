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
        boolean login = false;
        do{

            System.out.println("Input number of commands ( 0 : Exit, 1 : Login):");
            int inputInt =sc.nextInt();
            commandList.get(inputInt).execute(sc);

            login = loginStatus.getInstance().getLoginStatus();
        }while(login);
    }
    public static void init() {
        sc = new Scanner(System.in);
    	commandList = new HashMap<>();
        commandList.put(0, new exitCommand());
        commandList.put(1, new loginCommand());
    }
}
