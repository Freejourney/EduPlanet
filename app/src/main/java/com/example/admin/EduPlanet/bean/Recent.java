package com.example.admin.EduPlanet.bean;

import cn.bmob.v3.BmobObject;

public class Recent extends BmobObject{

    private String ItemObjectId;
    private String Title;

    public String getItemObjectId() {
        return ItemObjectId;
    }

    public void setItemObjectId(String itemObjectId) {
        ItemObjectId = itemObjectId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
