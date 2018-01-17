package com.example.denis.gorovje;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.DisplayMetrics;

import com.example.DataAll;
import com.example.Gora;
import com.example.Pot;
import com.example.ShraniPot;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Denis on 02/03/2017.
 */

public class ApplicationMy extends Application implements Serializable {
    public static transient Context context;
    public String userEmail;
    boolean signedIn;
    DataAll da;
    ArrayList<Gora> gore;
    ArrayList<ShraniPot> shraniPot;
    int heightcount;
    int textcount;
    boolean snemam;
    ArrayList<Pot> poti;
    int totalTime;

    @Override
    public void onCreate() {
        super.onCreate();
        da = DataAll.scenarij1();
        gore = new ArrayList<>();
        shraniPot = new ArrayList<>();
        context = this.getApplicationContext();
        heightcount = 0;
        textcount = 0;
        snemam = false;
        poti = new ArrayList<>();
        totalTime = 0;
    }


    public static Context getAppContext() {
        return ApplicationMy.context;
    }
}
