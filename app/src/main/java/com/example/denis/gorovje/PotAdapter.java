package com.example.denis.gorovje;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        public ConstraintLayout cl;

        public ViewHolder(View v) {
            super(v);
            txt = (TextView) v.findViewById(R.id.pot);
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

    @Override
    public void onBindViewHolder(PotAdapter.ViewHolder holder, int position) {
        final int poz = position;
        holder.txt.setText(mDataset.get(position).getIme());
        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDataset.get(poz).getPot() != null){
                    double x1 = mDataset.get(poz).getPot().get(0).getLongitude();
                    double y1 = mDataset.get(poz).getPot().get(0).getLatitude();
                    double x2 = mDataset.get(poz).getPot().get(mDataset.get(poz).getPot().size()-1).getLongitude();
                    double y2 = mDataset.get(poz).getPot().get(mDataset.get(poz).getPot().size()-1).getLatitude();
                    String lok = "http://maps.google.com/maps?saddr="+x1+","+y1+"&daddr="+x2+","+y2+"";
                    Uri gmmIntentUri = Uri.parse(lok);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    context.startActivity(mapIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
