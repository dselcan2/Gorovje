package com.example.denis.gorovje;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Pot;

import java.util.ArrayList;

/**
 * Created by Denis on 21/03/2017.
 */

public class PotAdapter extends RecyclerView.Adapter<PotAdapter.ViewHolder> {
    private ArrayList<Pot> mDataset;
    ApplicationMy am;
    private Context context;
    public PotAdapter(ArrayList<Pot> myDataset) {
        mDataset = myDataset;
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
                final PopupWindow popupWindow = new PopupWindow(popupView, width-20, (int)(height/2.5), true);
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

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
