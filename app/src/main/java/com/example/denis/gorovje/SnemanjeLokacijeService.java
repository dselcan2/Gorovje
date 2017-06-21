package com.example.denis.gorovje;

import android.*;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.Point;
import com.example.Tocka;

import java.util.ArrayList;

/**
 * Created by Denis on 19. 06. 2017.
 */

public class SnemanjeLokacijeService extends Service{

    LocationManager locationManager;
    LocationListener locationListener;
    public ArrayList<Tocka> pot = new ArrayList<Tocka>();
    public double prehojenadolzina;
    public long time;
    public String LatestLen;
    private final IBinder mBinder = new LocalBinder();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */

    public class LocalBinder extends Binder {
        SnemanjeLokacijeService getService() {
            // Return this instance of LocalService so clients can call public methods
            return SnemanjeLokacijeService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        time = System.currentTimeMillis();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(pot.size()>=1){
                    Location loc = new Location("");
                    loc.setLatitude(pot.get(pot.size()-1).getTocka().getLatitude());
                    loc.setLongitude(pot.get(pot.size()-1).getTocka().getLongitude());
                    prehojenadolzina += loc.distanceTo(location);
                }
                double povpHitr = prehojenadolzina/(((System.currentTimeMillis() - time)/1000)%60);
                long millis = System.currentTimeMillis() - time;
                long second = (millis / 1000) % 60;
                long minute = (millis / (1000 * 60)) % 60;
                long hour = (millis / (1000 * 60 * 60)) % 24;
                String time = String.format("%02d:%02d:%02d", hour, minute, second);
                Tocka tocka = new Tocka(new Point(location.getLongitude(), location.getLatitude()), prehojenadolzina, povpHitr, time);
                pot.add(tocka);
                LatestLen = Double.toString(prehojenadolzina);
                Log.d("d","got location");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

}