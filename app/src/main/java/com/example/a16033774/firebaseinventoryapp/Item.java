package com.example.a16033774.firebaseinventoryapp;

import java.io.Serializable;

public class Item implements Serializable {
    String id;
    String phoneName;
    Double price;

    public Item() {
    }

    public Item(String phoneName, Double price) {
        this.phoneName = phoneName;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return phoneName;
    }
}
