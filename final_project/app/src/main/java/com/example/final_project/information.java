package com.example.final_project;

import android.net.Uri;

import java.net.URL;

public class information {
    private String name="";
    private String num="";
    private String key="";
    private String id = "";
    private long long_id = 0;
    private String email = "";
    private Uri url;

    public String getName(){
        return name;
    }
    public String getNum(){
        return num;
    }
    public String getKey(){ return key; }
    public String getId(){
        return id;
    }
    public String getEmail() {return email;}
    public Uri getUrl() {return url;}
    public long getLong_id() {return long_id;}


    public void setName(String args){
        name = args;
    }
    public void setNum(String args){
        num = args;
    }
    public void setKey(String args){
        key = args;
    }
    public void setId(String args){
        id = args;
    }
    public void setEmail(String args) {email = args;}
    public void setUrl(Uri args) {url = args;}
    public void setLong_id(long args) {long_id = args;}
}
