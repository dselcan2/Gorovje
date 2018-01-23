package com.example.denis.gorovje;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Gora;
import com.example.Pot;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Denis on 21/03/2017.
 */

public class PotAdapter extends RecyclerView.Adapter<PotAdapter.ViewHolder> {
    private ArrayList<Pot> mDataset;
    ApplicationMy am;
    private Context context;
    Boolean runing;
    public PotAdapter(ArrayList<Pot> myDataset, ApplicationMy AM) {
        mDataset = myDataset;
        am = AM;
        runing = false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txt;
        public ImageView img;
        public ConstraintLayout cl;

        public ViewHolder(View v) {
            super(v);
            txt = (TextView) v.findViewById(R.id.pot);
            img = (ImageView) v.findViewById(R.id.icon2);
            cl = (ConstraintLayout) v.findViewById(R.id.PotLayout);
            context = v.getContext();
        }
    }

    public void add(int position, Pot item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public PotAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.potilayout,parent,false);
        PotAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
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

    @Override
    public void onBindViewHolder(PotAdapter.ViewHolder holder, final int position) {
        final int poz = position;
        holder.txt.setText(mDataset.get(position).getIme());
        String tezavnost = mDataset.get(position).getTezavnost();
        if(tezavnost.contains("lahka")){
            holder.img.setImageResource(R.drawable.eazypot);
        }
        else if(tezavnost.contains("zelo zahtev")){
            holder.img.setImageResource(R.drawable.hardpot);
        }
        else if(tezavnost.contains("zahtev")){
            holder.img.setImageResource(R.drawable.pot1);
        }
        else{
            holder.img.setImageResource(R.drawable.pot1);
        }
        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popuppot, null);
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                final PopupWindow popupWindow = new PopupWindow(popupView, width-20, (height/2), true);
                popupWindow.setBackgroundDrawable(null);
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                dimBehind(popupWindow);
                TextView ime = (TextView) popupView.findViewById(R.id.textView19);
                ime.setText(mDataset.get(position).getIme());
                TextView dolzina = (TextView) popupView.findViewById(R.id.textView20);
                dolzina.setText(mDataset.get(position).getCas());
                TextView tezavnost = (TextView) popupView.findViewById(R.id.textView15);
                tezavnost.setText(mDataset.get(position).getTezavnost());
                Button gumb = (Button) popupView.findViewById(R.id.button2);
                gumb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mDataset.get(poz) != null){
                            try{
                                String str = "google.navigation:q=" + mDataset.get(position).getZacetek().getLongitude() + "," + mDataset.get(position).getZacetek().getLatitude();
                                Uri gmmIntentUri = Uri.parse(str);
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                context.startActivity(mapIntent);
                            }
                            catch (Exception e){
                                Toast.makeText(context, "ni podatkov", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(context, "ni podatkov", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                final ImageView Irating = (ImageView) popupView.findViewById(R.id.imageView5);
                if(mDataset.get(position).getOcena() == "1"){
                    Irating.setImageResource(R.drawable.rating_1);
                }
                else if(mDataset.get(position).getOcena() == "2"){
                    Irating.setImageResource(R.drawable.rating_2);
                }
                else if(mDataset.get(position).getOcena() == "3"){
                    Irating.setImageResource(R.drawable.rating_3);
                }
                else if(mDataset.get(position).getOcena() == "4"){
                    Irating.setImageResource(R.drawable.rating_4);
                }
                else if(mDataset.get(position).getOcena() == "5"){
                    Irating.setImageResource(R.drawable.rating_5);
                }
                final Spinner rating = (Spinner) popupView.findViewById(R.id.spinner);
                String[] arraySpinner = new String[]{"ni ocene", "1", "2", "3", "4", "5"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, arraySpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                rating.setAdapter(adapter);
                if(mDataset.get(position).getOcena() != null){
                    rating.setSelection(Integer.parseInt(mDataset.get(position).getOcena()));
                }
                else{
                    rating.setSelection(0);
                }
                final Thread t = new Thread(){
                    @Override
                    public void run() {
                        while(true){
                            if(runing){
                                File file = new File(context.getExternalFilesDir("goremap"), "gore.json");
                                Collections.sort(am.gore, new Comparator<Gora>() {
                                    @Override
                                    public int compare(Gora o1, Gora o2) {
                                        return o1.getNaziv().compareTo(o2.getNaziv());
                                    }
                                });
                                Gora[] g = am.gore.toArray(new Gora[am.gore.size()]);
                                boolean shrani = ApplicationJsonGore.save(g, file);
                                Log.d("thread", "shranil " + shrani);
                                runing = false;
                            }
                        }
                    }
                };
                t.start();
                rating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                        if(runing){
                            Toast.makeText(context, "nekaj je narobe prosimo počakajte trenutek", Toast.LENGTH_SHORT).show();
                            if(mDataset.get(position).getOcena() == null){
                                mDataset.get(position).setOcena("0");
                            }
                            else{
                                rating.setSelection(Integer.parseInt(mDataset.get(position).getOcena()));
                            }
                        }
                        else{
                            Pot pot = new Pot(mDataset.get(position));
                            pot.setOcena(""+(position2));
                            if(!pot.getOcena().equals(mDataset.get(position).getOcena())){
                                mDataset.set(position, pot);
                                runing = true;
                            }
                            if(position2 == 0){
                                Irating.setImageResource(R.drawable.rating_not_rated);
                            }
                            else if(position2 == 1){
                                Irating.setImageResource(R.drawable.rating_1);
                            }
                            else if(position2 == 2){
                                Irating.setImageResource(R.drawable.rating_2);
                            }
                            else if(position2 == 3){
                                Irating.setImageResource(R.drawable.rating_3);
                            }
                            else if(position2 == 4){
                                Irating.setImageResource(R.drawable.rating_4);
                            }
                            else if(position2 == 5){
                                Irating.setImageResource(R.drawable.rating_5);
                            }
                        }
                        /*File file = new File(context.getExternalFilesDir("goremap"), "gore.json");
                        Collections.sort(am.gore, new Comparator<Gora>() {
                            @Override
                            public int compare(Gora o1, Gora o2) {
                                return o1.getNaziv().compareTo(o2.getNaziv());
                            }
                        });
                        Gora[] g = am.gore.toArray(new Gora[am.gore.size()]);
                        boolean shrani = ApplicationJsonGore.save(g, file);*/
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
