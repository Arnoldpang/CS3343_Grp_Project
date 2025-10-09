package main;

import main.command.*;

import java.util.HashMap;
import java.util.Scanner;

public class main {
	static HashMap<Integer, Command> commandList;
    static Scanner sc;

    public static void main(String[] args) {
    	init();
        boolean login = false;
        do{
            System.out.println("Input number of commands ( 0 : Exit, 1 : Login):");
            int inputInt =sc.nextInt();
            if(inputInt == 0){
                login = false;
                commandList.get(0).execute(sc);
            }else if(inputInt == 1){
                login = true;
                commandList.get(1).execute(sc);
            }


        }while(login);
    }
    public static void init() {
        sc = new Scanner(System.in);
    	commandList = new HashMap<>();
        commandList.put(0, new exitCommand());
        commandList.put(1, new loginCommand());
    }
}
