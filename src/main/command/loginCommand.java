package main.command;

import main.item.loginStatus;

import java.util.Scanner;

public class loginCommand implements Command {
    Scanner scanner;
    @Override
    public void execute(Scanner sc) {
        scanner = sc;
        String username, password;
        System.out.print("Username: ");
        username = scanner.next();
        System.out.print("Password: ");
        password = scanner.next();
        loginStatus login = loginStatus.getInstance();
        login.login(username, password);
    }

    public loginCommand(){

    }
}
