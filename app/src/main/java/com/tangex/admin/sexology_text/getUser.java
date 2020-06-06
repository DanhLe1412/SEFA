package com.tangex.admin.sexology_text;

public class getUser {
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String userName ;
    public String image;


    public getUser(){


    }

    public getUser(String userName, String image) {
        this.userName = userName;
        this.image = image;
    }
}
