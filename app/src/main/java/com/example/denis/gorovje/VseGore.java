package com.example.denis.gorovje;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.FloatingActionButton;
import android.view.View;


public class VseGore extends AppCompatActivity {
    ApplicationMy am;


    FloatingActionButton gumb;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        am = (ApplicationMy) getApplication();
        setContentView(R.layout.activity_vse_gore);
        recyclerView = (RecyclerView)findViewById(R.id.RecView);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(am.da.getGore());
        recyclerView.setAdapter(mAdapter);
        gumb = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        gumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VseGore.this, SnemanjeLokacije.class);
                VseGore.this.startActivity(intent);
            }
        });
    }

}
