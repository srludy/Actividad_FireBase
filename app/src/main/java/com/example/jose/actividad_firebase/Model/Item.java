package com.example.jose.actividad_firebase.Model;


public class Item {

    String name;
    String description;
    String category;
    String price;

    public Item(String name, String description, String category, String price) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }
    public Item(){

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
