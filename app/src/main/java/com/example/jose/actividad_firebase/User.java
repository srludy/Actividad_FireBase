package com.example.jose.actividad_firebase;

/**
 * Created by Jose on 14/12/2017.
 */

public class User {
    int id;
    String userName;
    String email;
    String name;
    String adress;
    String type;

    public User(int id, String userName, String email, String name, String adress, String type) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.name = name;
        this.adress = adress;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
