package entities;

public class User {
    String ID;
    String name;

    public User(String id, String user_name){
        ID = id;
        name = user_name;
    }
    public String getID(){
        return ID;
    }
    public String getName(){
        return name;
    }
}
