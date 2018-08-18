package com.example.admin.EduPlanet;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "bb99b319c875fa80d89614d2eac02787");
    }
}
