package com.example.publicdata;

public class FoodDataDto {
    private String name;
    private Float calorie = 0.0f;
    private Float carbohydrate = 0.0f;
    private Float protein = 0.0f;
    private Float fat = 0.0f;
    private boolean check = true;
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
    public boolean getCheck() {
        return check;
    }
    public void setCheck(boolean check){
        this.check = check;
    }
    // add Data
    public void addName(String name) {this.name += ", "+name;    }
    public void addCalorie(Float calorie) {this.calorie += calorie;    }
    public void addCarbohydrate(Float carbohydrate) {
        this.carbohydrate += carbohydrate;
    }
    public void addProtein(Float protein) {
        this.protein += protein;
    }
    public void addFat(Float fat) {
        this.fat += fat;
    }

}
