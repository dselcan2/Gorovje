package com.example.denis.gorovje;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import com.example.Point;
import com.example.ShraniPot;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SnemanjeLokacije extends AppCompatActivity {

    TextView latitude;
    TextView longitude;
    TextView altitude;
    TextView length;
    TextView elapsedtime;
    Button button;

    LocationManager locationManager;
    LocationListener locationListener;

    ArrayList<Point> pot;

    double visinskizacetek;
    double prehojenadolzina;
    double zacetnicas;
    long time;
    ApplicationMy am;
    boolean snemam;

    final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        am = (ApplicationMy) getApplication();
        setContentView(R.layout.activity_snemanje_lokacije);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }
        latitude = (TextView) findViewById(R.id.textView4);
        longitude = (TextView) findViewById(R.id.textView3);
        altitude = (TextView) findViewById(R.id.textView6);
        length = (TextView) findViewById(R.id.textView11);
        elapsedtime = (TextView) findViewById(R.id.textView12);
        button = (Button) findViewById(R.id.button);
        visinskizacetek = 0;
        prehojenadolzina = 0;
        time = 0;
        snemam = false;
        t.start();
        pot = new ArrayList<Point>();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude.setText("latitude: " + location.getLatitude());
                longitude.setText("longitude: " + location.getLongitude());
                Point tocka = new Point(location.getLongitude(), location.getLatitude());
                pot.add(tocka);
                if(pot.size()>1){
                    Location loc = new Location("");
                    loc.setLatitude(pot.get(pot.size()-2).getLatitude());
                    loc.setLongitude(pot.get(pot.size()-2).getLongitude());
                    prehojenadolzina += loc.distanceTo(location);
                }
                altitude.setText(Double.toString(location.getAltitude()));
                length.setText(Double.toString(prehojenadolzina));
                //Toast.makeText(getApplicationContext(), latitude.getText() + " " + longitude.getText(), Toast.LENGTH_SHORT).show();
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

    public void OnClick(View view) {
        if(!snemam){

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            }
            else{
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                time = System.currentTimeMillis();
                button.setText("nehaj snemati");
                snemam = true;
            }

        }
        else{
            button.setText("zacni snemati");
            snemam = false;
            visinskizacetek = 0;
            prehojenadolzina = 0;
            time = 0;
            if(pot.size() > 0){
                ShraniPot shraniPot = new ShraniPot(new ArrayList<>(pot), (String)elapsedtime.getText(), Double.parseDouble((String)length.getText()), Double.parseDouble((String)altitude.getText()));
                am.da.addShranjenaPot(shraniPot);
                Toast.makeText(this,"dodal", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"nisem zaznal nobene poti", Toast.LENGTH_SHORT).show();
            }
            pot.clear();
        }

    }
    Thread t = new Thread() {

        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(snemam){
                                long millis = System.currentTimeMillis() - time;
                                long second = (millis / 1000) % 60;
                                long minute = (millis / (1000 * 60)) % 60;
                                long hour = (millis / (1000 * 60 * 60)) % 24;

                                String time = String.format("%02d:%02d:%02d", hour, minute, second);
                                elapsedtime.setText(time);
                            }
                            else{
                                elapsedtime.setText("ne snemam");
                            }
                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        }
    };
}
