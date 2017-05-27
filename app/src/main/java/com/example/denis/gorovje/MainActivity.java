package com.example.denis.gorovje;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Gora;
import com.example.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // i'lll need dis: http://api.openweathermap.org/data/2.5/forecast?lat=46.4802353&lon=14.276609&APPID=184a26cf06cbd44f86560d24dab152a5&mode=xml&units=metric

    ApplicationMy Am;
    TextView Gora;
    TextView Opis;
    TextView latitude;
    TextView langnitude;
    ImageView Image;
    int displayGora;
    String xml;

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Am = (ApplicationMy) getApplication();
        Gora = (TextView) findViewById(R.id.textView);
        Opis = (TextView) findViewById(R.id.textView2);
        langnitude = (TextView) findViewById(R.id.textView10);
        latitude = (TextView) findViewById(R.id.textView8);
        Image = (ImageView) findViewById(R.id.imageView2);
        recyclerView = (RecyclerView)findViewById(R.id.RecViewPot);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        displayGora = getIntent().getIntExtra("id_gora",0);
        ArrayList<Point> poti = new ArrayList<Point>();
        Gora gora = Am.da.getGore().get(displayGora);
        mAdapter = new PotAdapter(gora.getZacetki(), gora.getNaziv());
        recyclerView.setAdapter(mAdapter);
        update();
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,xml,Toast.LENGTH_SHORT).show();
    }

    public void update(){
        Gora G = Am.da.getGore().get(displayGora);
        Gora.setText(G.getNaziv());
        Opis.setText(G.getOpis());
        langnitude.setText("" + G.getTocka().getLongitude());
        latitude.setText("" + G.getTocka().getLatitude());
        int resourceId = getResources().getIdentifier(G.getSlika(), "drawable", getApplicationContext().getPackageName());
        Image.setImageResource(resourceId);
    }

    Thread t = new Thread(){
        @Override
        public void run(){
            String gorovje = "";
            if(Am.da.getGore().get(displayGora).getGorovje() == Am.da.JULIJSKE_ALPE){
                gorovje = "JULIAN-ALPS";
            }
            else if(Am.da.getGore().get(displayGora).getGorovje() == Am.da.KAMNISKOSAVINJSE_ALPE){
                gorovje = "KAMNIK-SAVINJA-ALPS";
            }
            else if(Am.da.getGore().get(displayGora).getGorovje() == Am.da.KARAVANKE){
                gorovje = "KARAVANKE-ALPS";
            }
            else if(Am.da.getGore().get(displayGora).getGorovje() == Am.da.POHORJE){
                gorovje = "POHORJE";
            }
            else if(Am.da.getGore().get(displayGora).getGorovje() == Am.da.SNEZNIK){
                gorovje = "SNEZNIK";
            }
            else if(Am.da.getGore().get(displayGora).getGorovje() == Am.da.SKOFJELOSKO_HRIBOVJE){
                gorovje = "SKOFJELOSKO-HRIBOVJE";
            }
            else if(Am.da.getGore().get(displayGora).getGorovje() == Am.da.VZHODNOSLOVENSKO_HRIBOVJE){
                gorovje = "EAST-MOUNTAINS";
            }
            URL url = null;
            try {
                url = new URL("http://meteo.arso.gov.si/uploads/probase/www/fproduct/text/sl/forecast_SI_" + gorovje + "_latest.xml");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            URLConnection connection = null;
            try {
                connection = url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String inputLine;
            xml = "";
            try {
                while((inputLine = in.readLine()) != null){
                    xml += inputLine;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
