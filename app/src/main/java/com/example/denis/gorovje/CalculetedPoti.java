package com.example.denis.gorovje;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class CalculetedPoti extends AppCompatActivity {

    ApplicationMy am;
    TextView cas;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculeted_poti);
        am = (ApplicationMy) getApplication();
        cas = (TextView) findViewById(R.id.textView4);
        recyclerView = (RecyclerView) findViewById(R.id.calcPot);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PotAdapter(am.poti);
        recyclerView.setAdapter(mAdapter);
        int cajt = am.totalTime;
        int hour = cajt/60;
        int min = cajt%60;
        cas.setText("Sestevek casa: " + hour + "h" + min + "min");
    }
}
