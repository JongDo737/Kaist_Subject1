package com.example.final_project;

public class information {
    private String name="";
    private String num="";
    private String key="";
    private String id = "";
    private String email = "";

    public String getName(){
        return name;
    }
    public String getNum(){
        return num;
    }
    public String getKey(){
        return key;
    }
    public String getId(){
        return id;
    }
    public String getEmail() {return email;}

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
}
