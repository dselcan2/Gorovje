package com.example.denis.gorovje;

import android.*;
import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.Gora;
import com.example.Point;
import com.example.Pot;
import com.example.Subject;
import com.thoughtworks.xstream.XStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class TabbedActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    static TabLayout tabLayout;
    Button button;
    ApplicationMy am;

    void Evolution(int h, int m){
        final int optimal = h*60 + m;
        Random r = new Random();
        int population = 10;
        int Narg;
        int tries = 100;
        ArrayList<Subject> pop = new ArrayList<>();
        Subject poti = new Subject();
        for(int i=0; i<population; i++){
            Narg = r.nextInt((10-2)+1) + 2;
            for(int j=0; j<Narg; j++){
                int Npot = 0;
                int Ngora = r.nextInt(am.gore.size());
                while(true){
                    if(am.gore.get(Ngora).getZacetki().size() != 0){
                        Npot = r.nextInt(am.gore.get(Ngora).getZacetki().size());
                        break;
                    }
                    Ngora = r.nextInt(am.gore.size());
                }
                poti.addPot(am.gore.get(Ngora).getZacetki().get(Npot));//get random gora and random pot
                Log.d("pot: ", poti.getPot(j).getCas());
            }
            pop.add(poti);
            poti = new Subject();
            Log.d("pot: ", " ");
        }
        for(int i=0; i<pop.size(); i++){
            Log.d("pop: ", pop.get(i).toString());
        }
        for(int i=0; i<1; i++){
            //evaluation is already done in class

            //termination
            Collections.sort(pop, new Comparator<Subject>(){
                public int compare(Subject s1, Subject s2){
                    //-1 if s1 1 if s2
                    int S1 = Math.abs(optimal- s1.getTime());
                    int S2 = Math.abs(optimal- s2.getTime());
                    return S1 - S2;
                }
            });
            for(int j=0; j<pop.size()/2; j++){
                Log.d("sorted: ", pop.get(j).toString());
                //zbriši preostale
            }
            //selection

            //variation
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        am = (ApplicationMy) getApplication();
        button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            int hour = 8;
            int Minute = 15;
            @Override
            public void onClick(View v) {
                TimePickerDialog mDatePicker;
                mDatePicker = new TimePickerDialog(TabbedActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        Minute = minute;
                        Evolution(hour,minute);
                    }
                }, hour, Minute, true
                );
                mDatePicker.setTitle("Select Time");
                mDatePicker.show();
            }
        });
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
        }

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setText("Vrhovi");
        tabLayout.getTabAt(1).setText("Snemane poti");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public void onResume(){
            super.onResume();
            if(recyclerView != null){
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }

        private RecyclerView recyclerView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                final View rootView = inflater.inflate(R.layout.fragment_tabbed2, container, false);
                final ApplicationMy am = (ApplicationMy) ApplicationMy.getAppContext();
                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.Gore);
                recyclerView.setHasFixedSize(false);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                final RecyclerView.Adapter mAdapter = new MyAdapter(am.gore);
                recyclerView.setAdapter(mAdapter);
                EditText text = (EditText) rootView.findViewById(R.id.editText);
                text.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ArrayList<Gora> temp = new ArrayList<Gora>();
                        for(Gora g: am.gore){
                            if(g.getNaziv().toLowerCase().contains(s.toString().toLowerCase())){
                                temp.add(g);
                            }
                        }
                        ((MyAdapter)mAdapter).updateList(temp);
                    }
                });
                ImageButton textsort = (ImageButton)rootView.findViewById(R.id.imageButton2);

                ImageButton heightsort = (ImageButton)rootView.findViewById(R.id.imageButton3);

                textsort.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        am.heightcount = 0;
                        if(am.textcount%2 == 0){
                            Collections.sort(((MyAdapter)mAdapter).mDataset, new Comparator<Gora>() {
                                @Override
                                public int compare(Gora o1, Gora o2) {
                                    return o1.getNaziv().compareTo(o2.getNaziv());
                                }
                            });
                        }
                        else{
                            Collections.reverse(((MyAdapter)mAdapter).mDataset);
                        }
                        am.textcount++;
                        ((MyAdapter)mAdapter).updateList(((MyAdapter)mAdapter).mDataset);
                    }
                });
                heightsort.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        am.textcount = 0;
                        if(am.heightcount %2 == 0){
                            Collections.sort(((MyAdapter)mAdapter).mDataset, new Comparator<Gora>() {
                                @Override
                                public int compare(Gora o1, Gora o2) {
                                    if(o1.getVisina()> o2.getVisina()){
                                        return -1;
                                    }
                                    else{
                                        return 1;
                                    }
                                }
                            });
                        }
                        else{
                            Collections.reverse(((MyAdapter)mAdapter).mDataset);
                        }
                        am.heightcount++;
                        ((MyAdapter)mAdapter).updateList(((MyAdapter)mAdapter).mDataset);
                    }
                });
                return rootView;
            }
            else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                final View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);
                ApplicationMy am = (ApplicationMy) ApplicationMy.getAppContext();
                recyclerView = (RecyclerView) rootView.findViewById(R.id.RecView);
                recyclerView.setHasFixedSize(false);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                RecyclerView.Adapter mAdapter = new AdapterPot(am.shraniPot);
                recyclerView.setAdapter(mAdapter);
                FloatingActionButton gumb = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
                gumb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(rootView.getContext(), SnemanjeLokacije.class);
                        rootView.getContext().startActivity(intent);
                    }
                });
                /*if(mAdapter.getItemCount() != am.da.getShranjenaPot().size()){
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(rootView.getContext(),"something is odd", Toast.LENGTH_SHORT).show();
                }*/
                //Toast.makeText(rootView.getContext(),"class: " + am.da.getShranjenaPot().size() + " adapter: " + mAdapter.getItemCount(), Toast.LENGTH_SHORT).show();
                return rootView;
            }
            else {
                View rootView = inflater.inflate(R.layout.fragment_tabbed2, container, false);
                return rootView;
            }

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }
    }
}
