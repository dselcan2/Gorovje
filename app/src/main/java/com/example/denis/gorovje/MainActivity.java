package com.example.denis.gorovje;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.ImageView;
import com.example.Gora;
import com.example.Pot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ApplicationMy Am;
    TextView Gora;
    TextView Opis;
    TextView latitude;
    TextView langnitude;
    ImageView Image;
    int displayGora;

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
        ArrayList<Pot> poti = new ArrayList<Pot>();
        for(int i =0; i<Am.da.getPoti().size();i++){
            if(Am.da.getPoti().get(i).getId() == displayGora){
                poti.add(Am.da.getPoti().get(i));
            }
        }
        mAdapter = new PotAdapter(poti);
        recyclerView.setAdapter(mAdapter);
        update();
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
}
