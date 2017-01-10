package com.helloworld.lyz.allezmap.util;

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        instance = this;
    }

    public static MyApplication getInstance() {
        // TODO Auto-generated method stub
        return instance;
    }
}
