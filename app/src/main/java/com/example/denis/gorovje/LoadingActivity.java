package com.example.denis.gorovje;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Gora;
import com.example.Point;
import com.example.Pot;
import com.example.ShraniPot;
import com.thoughtworks.xstream.XStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class LoadingActivity extends AppCompatActivity {
    ImageView slika;
    TextView text;
    ApplicationMy am;
    AssetManager AM;
    static InputStream is = null;
    ArrayList<Gora> gore;
    public boolean isDone = false;
    Context context;

    private static final String DATA_MAP = "goremap";
    private static final String FILE_NAME = "gore.json";

    private static final String DATA_MAP1 = "potidatamap";
    private static final String FILE_NAME1 = "poti.json";

    Thread t = new Thread(){
        @Override
        public void run() {
            super.run();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("pridobivam gore: Snežnik");
                }
            });
            try {
                is = AM.open("julijske alpe.xml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            XStream xStream = new XStream();
            Gora[] sneznik = null;
            xStream.alias("ArrayOfGora",Gora[].class);
            xStream.alias("Gora",Gora.class);
            xStream.alias("Pot",Pot.class);
            xStream.alias("Point",Point.class);
            xStream.alias("longnitude", double.class);
            xStream.alias("latnitude", double.class);
            xStream.omitField(Pot.class, "ocena");
            if(is!=null){
                try {
                    sneznik = (Gora[])xStream.fromXML(new InputStreamReader(is, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            try {
                is = AM.open("sneznik.xml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("pridobivam gore: Julijske Alpe");
                }
            });

            Gora[] julAlpe = null;
            if(is!=null){
                try {
                    julAlpe = (Gora[])xStream.fromXML(new InputStreamReader(is, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        try {
            is = AM.open("kamnisko savinjske alpe.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("pridobivam gore: Kamiško Savinjske Alpe");
                }
            });

        Gora[] kamsav = null;
        if(is!=null){
            try {
                kamsav = (Gora[])xStream.fromXML(new InputStreamReader(is, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
            try {
                is = AM.open("karavanke.xml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("pridobivam gore: Karavanke");
                }
            });

            Gora[] karavanke = null;
            if(is!=null){
                try {
                    karavanke = (Gora[])xStream.fromXML(new InputStreamReader(is, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            try {
                is = AM.open("pohorje.xml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("pridobivam gore: Pohorje");
                }
            });

            Gora[] pohorje = null;
            if(is!=null){
                try {
                    pohorje = (Gora[])xStream.fromXML(new InputStreamReader(is, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            try {
                is = AM.open("skofjelosko hribovje.xml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("pridobivam gore: Skofjeloško Hribovje");
                }
            });

            Gora[] skofhrib = null;
            if(is!=null){
                try {
                    skofhrib = (Gora[])xStream.fromXML(new InputStreamReader(is, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            try {
                is = AM.open("vzhodnoslovensko hribovje.xml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("pridobivam gore: Vzhodnoslovensko Hribovje");
                }
            });

            Gora[] vzhhrib = null;
            if(is!=null){
                try {
                    vzhhrib = (Gora[])xStream.fromXML(new InputStreamReader(is, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            gore = new ArrayList<Gora>();
            gore.addAll(Arrays.asList(sneznik));
            gore.addAll(Arrays.asList(julAlpe));
            gore.addAll(Arrays.asList(kamsav));
            gore.addAll(Arrays.asList(karavanke));
            gore.addAll(Arrays.asList(pohorje));
            gore.addAll(Arrays.asList(skofhrib));
            gore.addAll(Arrays.asList(vzhhrib));
            File file = new File(context.getExternalFilesDir(DATA_MAP), FILE_NAME);
            Collections.sort(gore, new Comparator<Gora>() {
                @Override
                public int compare(Gora o1, Gora o2) {
                    return o1.getNaziv().compareTo(o2.getNaziv());
                }
            });
            Gora[] g = gore.toArray(new Gora[gore.size()]);
            boolean shrani = ApplicationJsonGore.save(g, file);
            am.gore = gore;
            isDone = true;
        }
    };

    Thread t2 = new Thread(){
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(isDone){
                                ArrayList<ShraniPot> shraniPot = ApplicationJson.load(poti);
                                if(shraniPot != null){
                                    am.shraniPot = new ArrayList<ShraniPot>(shraniPot);
                                }
                                else{
                                    am.shraniPot = new ArrayList<ShraniPot>();
                                }
                                Intent intent = new Intent(context,GoogleSignInActivity.class);
                                startActivity(intent);
                                isDone = false;
                            }
                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        }
    };

    Gora[] gora;
    File file;
    File poti;

    Thread t3 = new Thread(){
        @Override
        public void run() {
            super.run();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("pridobivam gore");
                }
            });
            gora = ApplicationJsonGore.load(file);
            am.gore = new ArrayList<Gora>(Arrays.asList(gora));
            isDone = true;
            //Generacija ARFF datoteke
            String arff = "";
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("Generiram arff");
                }
            });
            arff += "@relation Poti\n" +
                    "@attribute cas {\"kratko\", \"dolgo\", \"zelo dolgo\"}\n" +
                    "@attribute tezavnost {\"lahko\", \"zahtevno\", \"zelo zahtevno\"}\n" +
                    "@attribute visina {\"nizko\", \"visoko\", \"zelo visoko\"}\n" +
                    "@attribute gorovje {\"julijske alpe\", \"kamnisko savinjske alpe\", \"karavanke\", \"pohorje\", \"sneznik\", \"skofjelosko hribovje\", \"vzhodnoslovensko hribovje\"}\n" +
                    "@attribute ocenaUporabnika {\"0\", \"1\", \"2\", \"3\", \"4\", \"5\"}\n" +
                    "\n" +
                    "@data\n";
            for(int i=0; i<gora.length; i++){
                for(int j=0; j<gora[i].getZacetki().size(); j++){
                    String cas = gora[i].getZacetki().get(j).getCas();
                    String Acas = "";
                    if(Integer.parseInt(cas.substring(0,1)) < 1){
                        Acas = "kratko";
                    }
                    else if(Integer.parseInt(cas.substring(0,1)) < 3){
                        Acas = "dolgo";
                    }
                    else{
                        Acas = "zelo dolgo";
                    }
                    String tezavnost = "";
                    if(gora[i].getZacetki().get(j).getTezavnost().contains("lahka")){
                        tezavnost = "lahko";
                    }
                    else if(gora[i].getZacetki().get(j).getTezavnost().contains("zelo zahtev")){
                        tezavnost = "zelo zahtevno";
                    }
                    else{
                        tezavnost = "zahtevno";
                    }
                    String visina = "";
                    if(gora[i].getVisina()>2000){
                        visina = "zelo visoko";
                    }
                    else if(gora[i].getVisina() > 1000){
                        visina = "visoko";
                    }
                    else{
                        visina = "nizko";
                    }
                    if(gora[i].getZacetki().get(j).getOcena() == null){
                        gora[i].getZacetki().get(j).setOcena("0");
                    }
                    arff += "\"" + Acas + "\"" + "," + "\"" + tezavnost + "\"" + "," +
                            "\"" +visina + "\"" + "," + "\"" + gora[i].getGorovje() + "\"" +","+ gora[i].getZacetki().get(j).getOcena()+ "\n";
                }
            }
            File myFile = new File(Environment.getExternalStorageDirectory().toString()+ "/Poti.arff");
            try {
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter =
                        new OutputStreamWriter(fOut);
                myOutWriter.append(arff);
                myOutWriter.close();
                fOut.close();
                Log.d("FILE", "file saved to: " + Environment.getExternalStorageDirectory().toString()+ "/Poti.arff");

            } catch (Exception e) {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        am = (ApplicationMy) getApplication();
        slika = (ImageView)findViewById(R.id.loading);
        text = (TextView)findViewById(R.id.loadingtext);
        AM = getAssets();
        context = this;
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(1500);
        slika.startAnimation(anim);

        file = new File(this.getExternalFilesDir(DATA_MAP), FILE_NAME);
        poti = new File(this.getExternalFilesDir(DATA_MAP1), FILE_NAME1);
        if(!file.exists()){
            t.start();
        }
        else{
            t3.start();
        }
        t2.start();
    }
}
