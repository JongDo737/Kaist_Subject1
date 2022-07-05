package com.example.final_project;

import android.graphics.Bitmap;
import android.widget.Button;

import java.io.Serializable;

public class GalleryDto implements Serializable {
    private Bitmap img;
    String imgTitle="";
    Button commit;
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
    public Button getCommit(){
        return commit;
    }
    public void setCommit(Button  commit){
        this.commit = commit;
    }
}
