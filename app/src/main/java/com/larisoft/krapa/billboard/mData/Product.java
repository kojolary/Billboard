package com.larisoft.krapa.billboard.mData;

import java.util.List;

public class Product{
    public int id;
    public String title;
    public String short_description;
    public String rating;
    public String price;

    public Product(int id, String title, String short_description, String rating, String price) {
        this.id = id;
        this.title = title;
        this.short_description = short_description;
        this.rating = rating;
        this.price = price;
        }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getshort_description() {
        return short_description;
    }

    public String getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }
}
