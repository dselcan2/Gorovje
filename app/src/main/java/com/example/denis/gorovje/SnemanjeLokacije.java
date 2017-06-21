package com.example.denis.gorovje;

import android.Manifest;
import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import com.example.Point;
import com.example.ShraniPot;
import com.example.Tocka;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class SnemanjeLokacije extends AppCompatActivity {

    TextView speed;
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
    String LatestLen;

    SnemanjeLokacijeService mService;
    boolean connected = false;

    private static final String DATA_MAP = "potidatamap";
    private static final String FILE_NAME = "poti.json";

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
        speed = (TextView) findViewById(R.id.textView3);
        length = (TextView) findViewById(R.id.textView11);
        elapsedtime = (TextView) findViewById(R.id.textView12);
        button = (Button) findViewById(R.id.button);
        if(isMyServiceRunning(SnemanjeLokacijeService.class)) {
            button.setText("nehaj snemati");
            Intent intent = new Intent(this, SnemanjeLokacijeService.class);
            bindService(intent, mConnection, BIND_AUTO_CREATE);
        }
        t.start();
        /*visinskizacetek = 0;
        prehojenadolzina = 0;
        time = 0;
        snemam = false;
        LatestTime = "00:00:00";
        LatestLen = "0";

        pot = new ArrayList<Tocka>();
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
                speed.setText("Povprečna hitrost: " + Double.toString(povpHitr) + "m/s");
                Tocka tocka = new Tocka(new Point(location.getLongitude(), location.getLatitude()), prehojenadolzina, povpHitr, elapsedtime.getText().toString());
                pot.add(tocka);
                length.setText("Prehojena dolžina: " + Double.toString(prehojenadolzina) + "m");
                LatestLen = Double.toString(prehojenadolzina);
                //Toast.makeText(getApplicationContext(), latitude.getText() + " " + speed.getText(), Toast.LENGTH_SHORT).show();
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
        };*/
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void OnClick(View view) {
        if(!am.snemam){

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            }
            else{
                //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                //time = System.currentTimeMillis();
                Intent intent = new Intent(this, SnemanjeLokacijeService.class);
                try{
                    startService(intent);
                    bindService(intent, mConnection, BIND_AUTO_CREATE);
                    ComponentName mServiceConnection = new ComponentName(this, mConnection.getClass());
                    JobInfo task = new JobInfo.Builder(1, mServiceConnection).setPeriodic(30000).setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).build();
                    JobScheduler jobScheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    jobScheduler.schedule(task);
                }
                catch (Exception e){
                    connected = true;
                }
                button.setText("nehaj snemati");
                am.snemam = true;
            }

        }
        else{
            button.setText("zacni snemati");
            am.snemam = false;
            visinskizacetek = 0;
            prehojenadolzina = mService.prehojenadolzina;
            time = mService.time;
            if(mService.pot.size() > 0){
                final CharSequence[] items = {" Sprehod "," Pohod "};
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
                        shraniPot = new ShraniPot(new ArrayList<>(mService.pot), LatestTime, prehojenadolzina, ime);
                        am.shraniPot.add(shraniPot);
                        boolean shranil = save(am.shraniPot);
                        if(shranil){
                            Toast.makeText(context,"dodal", Toast.LENGTH_SHORT).show();
                        }
                        mService.pot.clear();
                    }
                });
                builder.show();
            }
            else{
                Toast.makeText(this,"nisem zaznal nobene poti", Toast.LENGTH_SHORT).show();
            }
            unbindService(mConnection);
            Intent intent = new Intent(this, SnemanjeLokacijeService.class);
            stopService(intent);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mConnection != null){
            try{
                unbindService(mConnection);
            }
            catch (Exception e){

            }
        }
    }

    public boolean save(ArrayList<ShraniPot> pot){
        File file = new File(this.getExternalFilesDir(DATA_MAP), FILE_NAME);
        return ApplicationJson.save(pot ,file);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
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
                            if(am.snemam && mService!=null){
                                long millis = System.currentTimeMillis() - mService.time;
                                long second = (millis / 1000) % 60;
                                long minute = (millis / (1000 * 60)) % 60;
                                long hour = (millis / (1000 * 60 * 60)) % 24;
                                length.setText("prehojena dolzina: " + round(mService.prehojenadolzina, 2) + "m");
                                double hitrost = mService.prehojenadolzina/((System.currentTimeMillis()-mService.time)/1000)%60;
                                if(Double.isNaN(hitrost) || Double.isInfinite(hitrost)){
                                    speed.setText("povprecna hitrost: 0.00m/s");
                                }
                                else{
                                    speed.setText("povprecna hitrost: " + round(hitrost, 2) + "m/s");
                                }
                                String time = String.format("%02d:%02d:%02d", hour, minute, second);
                                LatestTime = time;
                                elapsedtime.setText(time);
                            }
                            else{
                                elapsedtime.setText("00:00:00");
                            }
                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        }
    };
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SnemanjeLokacijeService.LocalBinder binder = (SnemanjeLokacijeService.LocalBinder) service;
            mService = binder.getService();
            am.snemam = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            am.snemam = false;
        }
    };
}