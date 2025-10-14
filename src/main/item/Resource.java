package main.item;

import java.util.HashMap;



public class Resource {
    private static HashMap<String, Resource> Resources = new HashMap<>();
    private static HashMap<String, Resource> DeletedItem = new HashMap<>();
    String name;
    private static enum status{
        AVAILABLE, BORROWED, DELETED
    }
    private status status;

    private Resource(String name){
        this.name = name;
        this.status = status.AVAILABLE;
    }

    public static void createResource(String name){
        Resource rs = new Resource(name);
        Resources.put(name, rs);
    }

    public static Resource getResource(String name){
        return Resources.get(name);
    }

    public static void printResources(){
        System.out.println("Resources:");
        for (String key : Resources.keySet()) {
            System.out.println(key + " - " + Resources.get(key).getStatus());
        }
    }

    public void setStatus(status status){
        this.status = status;
    }

    public void deleteResources(){
        this.setStatus(status.DELETED);
        DeletedItem.put(name, this);
    }

    public void borrowResource(){
        this.setStatus(status.BORROWED);
    }

    public String getStatus(){
        return this.status.toString();
    }
}
