package com.example.admin.EduPlanet.bean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    private String Avatorurl;
    private String Intro;

    public String getAvatorurl() {
        return Avatorurl;
    }

    public void setAvatorurl(String avatorurl) {
        Avatorurl = avatorurl;
    }

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String intro) {
        Intro = intro;
    }
}
