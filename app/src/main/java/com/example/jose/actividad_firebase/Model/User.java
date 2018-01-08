package com.example.jose.actividad_firebase.Model;

/**
 * Created by Jose on 14/12/2017.
 */

public class User {

    String userName;
    String email;
    String name;
    String adress;
    String auth;

    public User() {
    }

    public User(String userName, String email, String name, String adress, String auth) {
        this.userName = userName;
        this.email = email;
        this.name = name;
        this.adress = adress;
        this.auth = auth;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
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

}
