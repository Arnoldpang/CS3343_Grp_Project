package main.item;

public class loginStatus {
    private static loginStatus instance;
    private boolean loginStatus;

    public loginStatus(){
        this.loginStatus = false;
    }

    public void login(String username, String password){
        if (username.equals("admin") || password.equals("admin")){
            instance.setLoginStatus(true);
            System.out.println("Login success");
        }else{
            instance.setLoginStatus(false);
        }
    }

    public static loginStatus getInstance(){
        if(instance == null){
            instance = new loginStatus();
        }
        return instance;
    }

    public boolean getLoginStatus(){
        return this.loginStatus;
    }

    public void setLoginStatus(boolean status){
        this.loginStatus = status;
    }
}
