package com.example.denis.gorovje;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.Point;
import com.example.ShraniPot;
import com.example.Tocka;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView ime;
    TextView dolzina;
    TextView cas;
    ShraniPot pot;
    ApplicationMy am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        am = (ApplicationMy) getApplication();
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ime = (TextView) findViewById(R.id.textView14);
        dolzina = (TextView) findViewById(R.id.textView13);
        cas = (TextView) findViewById(R.id.textView5);
        int st = getIntent().getIntExtra("stevilo", 0);
        pot = am.shraniPot.get(st);
        ime.setText(pot.getIme());
        dolzina.setText(Double.toString(round(pot.getLength(),2)) + "m");
        cas.setText(pot.getTime());
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng point;
        ArrayList<Tocka> poti = new ArrayList<>(pot.getPot());
        for(int i=0; i<poti.size()-1; i++){
            point = new LatLng(poti.get(i).getTocka().getLatitude(), poti.get(i).getTocka().getLongitude());
            mMap.addMarker(new MarkerOptions().position(point).title(poti.get(i).getCas()).snippet("Dolžina: " + Double.toString(round(poti.get(i).getDolzina(),2)) + "m Hitrost: " + Double.toString(round(poti.get(i).getPovprecnaHitrost(),2)) + "m/s"));
            mMap.addPolyline(new PolylineOptions().add(point, new LatLng(poti.get(i+1).getTocka().getLatitude(), poti.get(i+1).getTocka().getLongitude())).width(5).color(Color.RED));
        }
        point = new LatLng(poti.get(poti.size()-1).getTocka().getLatitude(), poti.get(poti.size()-1).getTocka().getLongitude());
        mMap.addMarker(new MarkerOptions().position(point)
                .title(poti.get(poti.size()-1).getCas())
        .snippet("Dolžina: " + Double.toString(round(poti.get(poti.size()-1).getDolzina(),2)) + "m Hitrost: " + Double.toString(round(poti.get(poti.size()-1).getPovprecnaHitrost(),2)) + "m/s"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 16.0f));
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
}
