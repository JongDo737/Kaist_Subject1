package com.example.imagegallery;

import android.graphics.Bitmap;

public class GalleryDto {
    private Bitmap img;
    String imgTitle;

    public Bitmap getImg(){
        return img;
    }
    public String getImgTitle(){
        return imgTitle;
    }
    public void setImg(Bitmap  img){
        this.img = img;
    }
    public void setImgTitle(String imgTitle){
        this.imgTitle = imgTitle;
    }

}
