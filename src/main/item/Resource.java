package main.item;

import java.util.HashMap;

public class Resource {
    private static HashMap<String, Resource> Resources = new HashMap<>();
    private static HashMap<String, Resource> DeletedItem = new HashMap<>();
    private String name;

    private Resource(String name){
        this.name = name;
    }

    public void createResource(String name){
        Resource rs = new Resource(name);
        Resources.put(name, rs);
    }

    public Resource getResource(String name){
        return Resources.get(name);
    }

    public void printResources(){
        System.out.println("Resources:");
        for (String key : Resources.keySet()) {
            System.out.println(key);
        }
    }

    public void deleteResources(String name){
        Resource rs = Resources.remove(name);
        DeletedItem.put(name, rs);
    }
}
