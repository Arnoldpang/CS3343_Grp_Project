package main.item;

public class loginStatus {
    public static boolean loginStatus;

    public loginStatus(){
        this.loginStatus = false;
    }

    public static void login(String username, String password){
        if (username.equals("admin") || password.equals("admin")){
            this.loginStatus =  true;
        }else{
            this.loginStatus = false;
        }
    }

    public loginStatus getInstance(){
        return new loginStatus();
    }

    public boolean getLoginStatus(){
        return this.loginStatus;
    }
}
