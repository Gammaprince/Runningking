package com.raees.android;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class adapter_timings extends RecyclerView.Adapter<adapter_timings.ViewHolder> {

    Context context;
    String market;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> is_open = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();

    public adapter_timings(Context context, String market, ArrayList<String> name, ArrayList<String> is_open, ArrayList<String> time) {
        this.context = context;
        this.market = market;
        this.name = name;
        this.is_open = is_open;
        this.time = time;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_timing_layout, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        holder.name.setText(time.get(position));
        holder.time.setText(name.get(position));

        if (is_open.get(position).equals("1")) {
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.md_green_800));

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,starline_games.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("market",market).putExtra("timing",time.get(position)));
                }
            });

        } else {
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Market is already closed", Toast.LENGTH_SHORT).show();
                }
            });

            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.md_red_600));
        }

        holder.setIsRecyclable(false);
    }


    @Override
    public int getItemCount() {
        return time.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,time;
        RelativeLayout layout;

        public ViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.layout);
            name = view.findViewById(R.id.name);
            time = view.findViewById(R.id.time);


        }
    }



}
