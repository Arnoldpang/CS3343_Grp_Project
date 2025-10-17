package main.command;

import main.item.loginStatus;

import java.util.Scanner;

public class logoutCommand implements Command{
    Scanner scanner;
    @Override
    public void execute(Scanner sc) {
        loginStatus login = loginStatus.getInstance();
        login.logout();
    }

    public logoutCommand(){

    }
}
