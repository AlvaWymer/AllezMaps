package com.helloworld.lyz.allezmap.util;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    public  static Context context;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        instance = this;
        context = this;
    }

    public static MyApplication getInstance() {
        // TODO Auto-generated method stub
        return instance;
    }
}
