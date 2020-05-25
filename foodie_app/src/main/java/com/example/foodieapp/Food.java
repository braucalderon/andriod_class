package com.example.foodieapp;

public class Food {

    private String title;
    private String info;
    private String recipe;
    private final int imageSource;

    public Food(String title, String info, int imageSource){
        this.title = title;
        this.info = info;
        this.imageSource = imageSource;
    }

    public Food(String title, String info, String recipe, int imageSource){
        this.title = title;
        this.info = info;
        this.recipe = recipe;
        this.imageSource = imageSource;
    }


    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public String getRecipe() {
        return recipe;
    }

    public int getImageSource() {
        return imageSource;
    }
}
