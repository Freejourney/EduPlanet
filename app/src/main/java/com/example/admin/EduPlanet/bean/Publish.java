package com.example.admin.EduPlanet.bean;

import cn.bmob.v3.BmobObject;

public class Publish extends BmobObject {

    private String ItemUID;
    private String[] Pics;
    private String Content;
    private String Owner;
    private String Image;

    public String getItemUID() {
        return ItemUID;
    }

    public void setItemUID(String itemUID) {
        ItemUID = itemUID;
    }

    public String[] getPics() {
        return Pics;
    }

    public void setPics(String[] pics) {
        Pics = pics;
    }


    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
