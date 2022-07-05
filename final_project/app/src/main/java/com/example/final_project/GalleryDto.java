package com.example.final_project;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Button;

import java.io.Serializable;

public class GalleryDto implements Serializable {
    private Bitmap img;
    String imgTitle="";
    String imgDes="";
    String path="";
    Boolean is_heart = false;
    Uri uri;
    Button commit;
    public Bitmap getImg(){
        return img;
    }
    public String getImgTitle(){
        return imgTitle;
    }
    public String getImgDes() {return imgDes;}
    public String getPath() {return path;}
    public Uri getUri() {return uri;}
    public Boolean getIs_heart() {return is_heart;}
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
    public void setImgDes(String args) {this.imgDes = args;}
    public void setPath(String args) {this.path = args;}
    public void setUri(Uri args) {this.uri = args;}
    public void setIs_heart(Boolean args) {this.is_heart = args;}
}
