package com.example.jose.actividad_firebase.Model;


public class Item {

    String name;
    String description;
    String category;
    String price;
    String userUID;
    String key;
    public Item(String name, String description, String category, String price, String userUID, String key) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.userUID = userUID;
        this.key = key;
    }

    public Item(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
