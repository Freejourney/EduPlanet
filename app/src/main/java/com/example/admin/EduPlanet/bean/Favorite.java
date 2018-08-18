package com.example.admin.EduPlanet.bean;

import cn.bmob.v3.BmobObject;

public class Favorite extends BmobObject {

    private String ItemUID;

    public String getItemUID() {
        return ItemUID;
    }

    public void setItemUID(String itemUID) {
        ItemUID = itemUID;
    }
}
