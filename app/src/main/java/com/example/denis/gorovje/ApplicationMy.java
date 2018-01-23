package com.example.denis.gorovje;

import android.app.Application;
import android.content.Context;

import com.example.DataAll;
import com.example.Gora;
import com.example.Pot;
import com.example.ShraniPot;

import java.io.Serializable;
import java.util.ArrayList;

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
    boolean prvic;

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
        prvic = true;
    }


    public static Context getAppContext() {
        return ApplicationMy.context;
    }
}
