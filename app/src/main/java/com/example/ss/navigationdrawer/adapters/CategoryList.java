package com.example.ss.navigationdrawer.adapters;

public class CategoryList {

    public String id;
    public String category_name;
    public String image_path;
    public String type;


    public CategoryList(String id, String category_name, String image_path, String type) {
        this.id = id;
        this.category_name = category_name;
        this.image_path = image_path;
        this.type=type;
    }
}
