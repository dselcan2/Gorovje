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

    public void swap(ArrayList<ShraniPot> data){
        mDataset.clear();
        mDataset.addAll(data);
        notifyDataSetChanged();
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
        holder.txtHeader.setText(mDataset.get(position).getIme());
        final int imgid = context.getResources().getIdentifier("pot", "drawable", context.getApplicationContext().getPackageName());
        holder.img.setImageResource(imgid);
        holder.CL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MapsActivity.class);
                intent.putExtra("stevilo", poz);
                context.startActivity(intent);
            }
        });
        holder.txtFooter.setText("Dolzina: " + mDataset.get(position).getLength() + " Čas: " + mDataset.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        if(mDataset != null){
            return mDataset.size();
        }
        else return 0;
    }

}
