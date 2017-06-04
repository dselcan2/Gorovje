package com.example.denis.gorovje;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import com.example.Point;
import com.example.ShraniPot;
import com.example.Tocka;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
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

    ArrayList<Tocka> pot;

    double visinskizacetek;
    double prehojenadolzina;
    double zacetnicas;
    long time;
    ApplicationMy am;
    boolean snemam;

    String ime;
    ShraniPot shraniPot;
    Context context;
    String LatestTime;

    private static final String DATA_MAP = "potidatamap";
    private static final String FILE_NAME = "poti.json";

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
        context = this;
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
        LatestTime = "00:00:00";
        t.start();
        pot = new ArrayList<Tocka>();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude.setText("latitude: " + location.getLatitude());
                longitude.setText("longitude: " + location.getLongitude());
                if(pot.size()>=1){
                    Location loc = new Location("");
                    loc.setLatitude(pot.get(pot.size()-1).getTocka().getLatitude());
                    loc.setLongitude(pot.get(pot.size()-1).getTocka().getLongitude());
                    prehojenadolzina += loc.distanceTo(location);
                }
                double povpHitr = prehojenadolzina/(((System.currentTimeMillis() - time)/1000)%60);
                Tocka tocka = new Tocka(new Point(location.getLongitude(), location.getLatitude()), prehojenadolzina, povpHitr, elapsedtime.getText().toString());
                pot.add(tocka);
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
                ime = "";
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("vnesite ime poti");
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ime = input.getText().toString();
                        shraniPot = new ShraniPot(new ArrayList<>(pot), LatestTime, Double.parseDouble((String)length.getText()), ime);
                        am.shraniPot.add(shraniPot);
                        boolean shranil = save(am.shraniPot);
                        if(shranil){
                            Toast.makeText(context,"dodal", Toast.LENGTH_SHORT).show();
                        }
                        pot.clear();
                    }
                });
                builder.show();

            }
            else{
                Toast.makeText(this,"nisem zaznal nobene poti", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public boolean save(ArrayList<ShraniPot> pot){
        File file = new File(this.getExternalFilesDir(DATA_MAP), FILE_NAME);
        return ApplicationJson.save(pot ,file);
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
                                LatestTime = time;
                                elapsedtime.setText(time);
                            }
                            else{
                                elapsedtime.setText("Ne snemam");
                            }
                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        }
    };
}
