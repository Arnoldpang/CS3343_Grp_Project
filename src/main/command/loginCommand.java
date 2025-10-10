package main.command;

import main.item.loginStatus;

import java.util.Scanner;

public class loginCommand implements Command {
    Scanner scanner;
    @Override
    public void execute(Scanner sc) {
        scanner = sc;
        String username, password;
        System.out.println("Username:");
        username = scanner.next();
        System.out.println("Password:");
        password = scanner.next();
        loginStatus login = loginStatus.getInstance();
        login.login(username, password);
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
