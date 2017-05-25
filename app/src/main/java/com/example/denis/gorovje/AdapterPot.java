package com.example.denis.gorovje;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Gora;
import com.example.Point;
import com.example.ShraniPot;

import java.util.ArrayList;

/**
 * Created by Denis on 20/03/2017.
 */

public class AdapterPot extends RecyclerView.Adapter<AdapterPot.ViewHolder> {

    private ArrayList<ShraniPot> mDataset;
    ApplicationMy am;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView img;
        public ConstraintLayout CL;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.pot);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            img = (ImageView) v.findViewById(R.id.icon);
            CL = (ConstraintLayout) v.findViewById(R.id.CLayout);
            context = v.getContext();
        }
    }

    public void add(int position, ShraniPot item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterPot(ArrayList<ShraniPot> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public AdapterPot.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listview,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int poz = position;
        holder.txtHeader.setText("testna");
        int imgid = context.getResources().getIdentifier("pot", "drawable", context.getApplicationContext().getPackageName());
        holder.img.setImageResource(imgid);
        final String[] str = new String[3];
        str[0] = "IME";
        str[1] = Double.toString(mDataset.get(poz).getLength());
        str[2] = mDataset.get(poz).getTime();
        final double[] pot = new double[mDataset.get(poz).getPot().size()*2];
        int stevec = 0;
        for(int i=0; i<mDataset.get(poz).getPot().size(); i+=2){
            pot[i] = mDataset.get(poz).getPot().get(stevec).getLatitude();
            pot[i+1] = mDataset.get(poz).getPot().get(stevec).getLongitude();
            stevec++;
        }
        holder.CL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MapsActivity.class);
                intent.putExtra("str", str);
                intent.putExtra("pot", pot);
                context.startActivity(intent);
            }
        });
        holder.txtFooter.setText("Dolzina: " + mDataset.get(position).getLength() + " Čas: " + mDataset.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
