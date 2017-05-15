package com.example.denis.gorovje;

import android.app.Application;
import android.content.Context;

import com.example.DataAll;
/**
 * Created by Denis on 02/03/2017.
 */

public class ApplicationMy extends Application {
    public static Context context;
    DataAll da;
    @Override
    public void onCreate() {
        super.onCreate();
        da = DataAll.scenarij1();
        context = this.getApplicationContext();
    }
    public static Context getAppContext() {
        return ApplicationMy.context;
    }
}
