package com.larisoft.krapa.billboard.mData;


public class Product2{
    public String id;
    public String title;
    public String short_description;
    public String inputPerson;
    public double rating;
    public double price;
    public String image;

    public Product2(String id, String title, String short_description, double rating, double price, String inputPerson, String image ) {
        this.id = id;
        this.title = title;
        this.short_description = short_description;
        this.inputPerson = inputPerson;
        this.rating = rating;
        this.price = price;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getshort_description() {
        return short_description;
    }
    public String getinputPerson() {
        return inputPerson;
    }

    public double getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

}