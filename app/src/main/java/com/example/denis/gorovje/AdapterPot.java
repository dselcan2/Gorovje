package com.example.denis.gorovje;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Gora;
import com.example.Point;
import com.example.ShraniPot;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by Denis on 20/03/2017.
 */

public class AdapterPot extends RecyclerView.Adapter<AdapterPot.ViewHolder> {

    private static final String DATA_MAP = "potidatamap";
    private static final String FILE_NAME = "poti.json";

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
        holder.itemView.setLongClickable(true);
        holder.txtHeader.setText(mDataset.get(position).getIme());
        if(mDataset.get(position).getLength() > 1000){
            holder.img.setImageResource(R.drawable.longpot);
        }
        else{
            holder.img.setImageResource(R.drawable.pot);
        }
        holder.CL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MapsActivity.class);
                intent.putExtra("stevilo", poz);
                context.startActivity(intent);
            }
        });
        holder.CL.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(context);
                builder.setTitle("Zbriši vnos?").setMessage("Ali ste prepričani, da želite zbrisati pot?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDataset.remove(poz);
                        ApplicationJson.save(mDataset, new File(context.getExternalFilesDir(DATA_MAP), FILE_NAME));
                        notifyDataSetChanged();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                }).setCancelable(false).show();
                return true;
            }
        });
        holder.txtFooter.setText("Dolzina: " + round(mDataset.get(position).getLength(),2) + " Čas: " + mDataset.get(position).getTime());
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public int getItemCount() {
        if(mDataset != null){
            return mDataset.size();
        }
        else return 0;
    }

}
