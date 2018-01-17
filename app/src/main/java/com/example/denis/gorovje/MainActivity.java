package com.example.denis.gorovje;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Gora;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.denis.gorovje.ApplicationMy.context;


public class MainActivity extends AppCompatActivity {


    ApplicationMy Am;
    TextView Gora;
    TextView Opis;
    TextView latitude;
    TextView langnitude;
    ImageView Image;
    String displayGora;
    String xml;
    private ConstraintLayout conLayout;
    Gora gora;

    PopupWindow popupWindow;
    LayoutInflater layoutInflater;

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    String str;

    ArrayList<Vreme> vreme = new ArrayList<>();
    Bitmap[] slika = new Bitmap[3];

    private XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
    private XmlPullParser myparser = xmlFactoryObject.newPullParser();

    public MainActivity() throws XmlPullParserException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Am = (ApplicationMy) getApplication();
        t.start();
        conLayout = (ConstraintLayout) findViewById(R.id.activity_main);
        Gora = (TextView) findViewById(R.id.textView);
        Opis = (TextView) findViewById(R.id.textView2);
        langnitude = (TextView) findViewById(R.id.textView10);
        latitude = (TextView) findViewById(R.id.textView8);
        Image = (ImageView) findViewById(R.id.imageView2);
        recyclerView = (RecyclerView)findViewById(R.id.RecViewPot);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        displayGora = getIntent().getStringExtra("id_gora");
        gora = new Gora();
        for(Gora g: Am.gore){
            if(g.getNaziv().equalsIgnoreCase(displayGora)){
                gora = g;
                Log.d("D", g.getGorovje());
                break;
            }

        }
        mAdapter = new PotAdapter(gora.getZacetki(), Am);
        recyclerView.setAdapter(mAdapter);
        str = "";
        update();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        InputStream is = null;
        try{
            is = new ByteArrayInputStream(xml.getBytes(Charset.defaultCharset()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String slika = "";
        String temp2500 = "";
        String temp2000 = "";
        String temp1500 = "";
        String temp1000 = "";
        String temp500 = "";
        String vr = "";
        int stevec = 1;
        try {
            myparser.setInput(is,null);
            int event = myparser.getEventType();
            while(event != XmlPullParser.END_DOCUMENT){
                String name = myparser.getName();
                switch(event){
                    case XmlPullParser.END_TAG:
                        if(name.equals("metData")){
                            if(stevec  == 3 || stevec == 8 || stevec == 13){
                                vreme.add(new Vreme(vr, temp2500, temp2000, temp1500, temp1000, temp500, slika));
                            }
                            stevec++;
                        }
                        break;
                    case XmlPullParser.START_TAG:
                        if(name.equals("t_level_2500_m")){
                            temp2500 = myparser.nextText();
                        }
                        else if(name.equals("t_level_2000_m")){
                            temp2000 = myparser.nextText();
                        }
                        else if(name.equals("t_level_1500_m")){
                            temp1500 = myparser.nextText();
                        }
                        else if(name.equals("t_level_1000_m")){
                            temp1000 = myparser.nextText();
                        }
                        else if(name.equals("t_level_500_m")){
                            temp500 = myparser.nextText();
                        }
                        else if(name.equals("nn_icon_domain_top-wwsyn_icon")){
                            slika = myparser.nextText();
                        }
                        else if(name.equals("nn_shortText")){
                            vr = myparser.nextText();
                        }
                        break;
                }
                event = myparser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(vreme.size() > 0) {
            String str = vreme.get(0).vreme + " " + vreme.get(0).temp2500 + " " + vreme.get(0).temp2000 + " " + vreme.get(0).temp1500 + " " + vreme.get(0).temp1000 + " " + vreme.get(0).temp500 + " " + vreme.size();
            t2.start();
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void correctWidth(TextView textView, int desiredWidth)
    {
        Paint paint = new Paint();
        Rect bounds = new Rect();

        paint.setTypeface(textView.getTypeface());
        float textSize = textView.getTextSize();
        paint.setTextSize(textSize);
        String text = textView.getText().toString();
        paint.getTextBounds(text, 0, text.length(), bounds);

        while (bounds.width() > desiredWidth)
        {
            textSize--;
            paint.setTextSize(textSize);
            paint.getTextBounds(text, 0, text.length(), bounds);
        }

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    public void update(){
        Gora G = gora;
        correctWidth(Gora, 100);
        Gora.setText(G.getNaziv());
        Opis.setText(Double.toString((int)G.getVisina()) + "m");
        if(G.getTocka() != null){
            langnitude.setText("" + G.getTocka().getLongitude());
            latitude.setText("" + G.getTocka().getLatitude());
        }
        else{
            langnitude.setText("ni podatka");
            latitude.setText("ni podatka");
        }
        Picasso.with(this).load(G.getSlika()).placeholder(R.drawable.icon_not_found).into(Image);
    }

    public static void dimBehind(PopupWindow popupWindow) {
        View container;
        if (popupWindow.getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                container = (View) popupWindow.getContentView().getParent();
            } else {
                container = popupWindow.getContentView();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent().getParent();
            } else {
                container = (View) popupWindow.getContentView().getParent();
            }
        }
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.6f;
        wm.updateViewLayout(container, p);
    }

    public void OnClick(View view){
        if(vreme.size() > 0){
            layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.vremelayout, null);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            popupWindow = new PopupWindow(container, width - 20, height/3, true);
            popupWindow.showAtLocation(conLayout, Gravity.CENTER, 0, height/6 * -1);
            dimBehind(popupWindow);
            ImageView img1 = (ImageView) container.findViewById(R.id.imageView3);
            ImageView img2 = (ImageView) container.findViewById(R.id.imageView7);
            ImageView img3 = (ImageView) container.findViewById(R.id.imageView6);
            TextView txt1 = (TextView) container.findViewById(R.id.textView23);
            TextView txt2 = (TextView) container.findViewById(R.id.textView24);
            TextView txt3 = (TextView) container.findViewById(R.id.textView22);
            TextView temp1 = (TextView) container.findViewById(R.id.textView26);
            TextView temp2 = (TextView) container.findViewById(R.id.textView28);
            TextView temp3 = (TextView) container.findViewById(R.id.textView25);
            img1.setImageBitmap(slika[0]);
            img2.setImageBitmap(slika[1]);
            img3.setImageBitmap(slika[2]);
            txt1.setText(vreme.get(0).vreme);
            txt2.setText(vreme.get(1).vreme);
            txt3.setText(vreme.get(2).vreme);
            double visina = gora.getVisina();
            if(visina > 2500){
                temp1.setText(vreme.get(0).temp2500 + "°C");
                temp2.setText(vreme.get(1).temp2500 + "°C");
                temp3.setText(vreme.get(2).temp2500 + "°C");
            }
            else if(visina > 2000){
                temp1.setText(vreme.get(0).temp2000 + "°C");
                temp2.setText(vreme.get(1).temp2000 + "°C");
                temp3.setText(vreme.get(2).temp2000 + "°C");
            }
            else if(visina > 1500){
                temp1.setText(vreme.get(0).temp1500 + "°C");
                temp2.setText(vreme.get(1).temp1500 + "°C");
                temp3.setText(vreme.get(2).temp1500 + "°C");
            }
            else if(visina > 1000){
                temp1.setText(vreme.get(0).temp1000 + "°C");
                temp2.setText(vreme.get(1).temp1000 + "°C");
                temp3.setText(vreme.get(2).temp1000 + "°C");
            }
            else{
                temp1.setText(vreme.get(0).temp500 + "°C");
                temp2.setText(vreme.get(1).temp500 + "°C");
                temp3.setText(vreme.get(2).temp500 + "°C");
            }
        }
        else {
            Toast.makeText(this,"Ni podatka", Toast.LENGTH_LONG).show();
        }
    }

    Thread t = new Thread(){
        @Override
        public void run(){
            String gorovje = "";
            Gora gor = new Gora();
            for(Gora g: Am.gore){
                if(g.getNaziv().equalsIgnoreCase(displayGora)){
                    gor = g;
                    Log.d("D", g.getGorovje());
                    break;
                }

            }
            if(gor.getGorovje() != null) {


                if (gor.getGorovje().equalsIgnoreCase(Am.da.JULIJSKE_ALPE)) {
                    gorovje = "JULIAN-ALPS";
                } else if (gor.getGorovje().equalsIgnoreCase(Am.da.KAMNISKOSAVINJSE_ALPE)) {
                    gorovje = "KAMNIK-SAVINJA-ALPS";
                } else if (gor.getGorovje().equalsIgnoreCase(Am.da.KARAVANKE)) {
                    gorovje = "KARAVANKE-ALPS";
                } else if (gor.getGorovje().equalsIgnoreCase(Am.da.POHORJE)) {
                    gorovje = "POHORJE";
                } else if (gor.getGorovje().equalsIgnoreCase(Am.da.SNEZNIK)) {
                    gorovje = "SNEZNIK";
                } else if (gor.getGorovje().equalsIgnoreCase(Am.da.SKOFJELOSKO_HRIBOVJE)) {
                    gorovje = "SKOFJELOSKO-HRIBOVJE";
                } else if (gor.getGorovje().equalsIgnoreCase(Am.da.VZHODNOSLOVENSKO_HRIBOVJE)) {
                    gorovje = "EAST-MOUNTAINS";
                }
            }
            URL url = null;
            BufferedReader in = null;
            try {
                url = new URL("http://meteo.arso.gov.si/uploads/probase/www/fproduct/text/sl/forecast_SI_" + gorovje + "_latest.xml");
                URLConnection connection = null;
                connection = url.openConnection();
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                xml = "";
                while((inputLine = in.readLine()) != null){
                    xml += inputLine;
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    Thread t2 = new Thread(){
        @Override
        public void run(){
            for(int i=0; i<vreme.size(); i++){
                String url = "http://www.meteo.si/uploads/meteo/style/img/weather/" + vreme.get(i).slika + ".png";
                slika[i] = null;
                try{
                    InputStream in = new URL(url).openStream();
                    slika[i] = BitmapFactory.decodeStream(in);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}

class Vreme{
    String vreme;
    String slika;
    int temp2500;
    int temp2000;
    int temp1500;
    int temp1000;
    int temp500;

    public Vreme(String vreme, String temp2500, String temp2000, String temp1500, String temp1000, String temp500, String slika){
        this.vreme = vreme;
        this.temp2500 = Integer.parseInt(temp2500);
        this.temp2000 = Integer.parseInt(temp2000);
        this.temp1500 = Integer.parseInt(temp1500);
        this.temp1000 = Integer.parseInt(temp1000);
        this.temp500 = Integer.parseInt(temp500);
        this.slika = slika;
    }
    public Vreme(){}
}
