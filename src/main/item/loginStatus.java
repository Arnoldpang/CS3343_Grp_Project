package main.item;

public class loginStatus {
    private static loginStatus instance;
    private boolean loginStatus;
    private role currentUserRole;
    private String username, password;
    private static enum role{
        ADMIN, USER;
    }

    private loginStatus(){
        this.loginStatus = false;
    }

    public void login(String username, String password){
        if (username.equals("admin") && password.equals("admin")){
            instance.setLoginStatus(true);
            instance.setCurrentUserRole(role.ADMIN);
            System.out.println("Login success");
        }
    }

    public void logout(){
        instance.setLoginStatus(false);
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

    public void setCurrentUserRole(role role){
        this.currentUserRole = role;
    }

    public String getRole(){
        return this.currentUserRole.toString();
    }

    public String getUsername(){
        return this.username;
    }
}
