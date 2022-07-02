package com.example.publicdata;

public class FoodDataDto {
    private String name;
    private Float calorie;
    private Float carbohydrate;
    private Float protein;
    private Float fat;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Float getCalorie() {
        return calorie;
    }
    public void setCalorie(Float calorie) {
        this.calorie = calorie;
    }
    public Float getCarbohydrate() {
        return carbohydrate;
    }
    public void setCarbohydrate(Float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
    public Float getProtein() {
        return protein;
    }
    public void setProtein(Float protein) {
        this.protein = protein;
    }
    public Float getFat() {
        return fat;
    }
    public void setFat(Float fat) {
        this.fat = fat;
    }
}
