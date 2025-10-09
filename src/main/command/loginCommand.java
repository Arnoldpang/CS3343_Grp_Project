package main.command;

import main.item.loginStatus;

import java.util.Scanner;

public class loginCommand implements Command {
    @Override
    public void execute(Scanner sc) {
        String username, password;
        System.out.println("Username:");
        username = sc.nextLine();
        System.out.println("Password:");
        password = sc.nextLine();
        loginStatus.login(username,password);
    }

    public loginCommand(){

    }

    private boolean login(String username,String password){
        if (username.equals("admin") || password.equals("admin")){
            return true;
        }else{
            return false;
        }
    }
}
