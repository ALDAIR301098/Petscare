package com.petscare.org.vista.adaptadores.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petscare.org.R;

public class AdaptadorFeed extends RecyclerView.Adapter<AdaptadorFeed.MyViewHolder> {

    String[] list;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_adaptador,parent,false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView6.setText(list[position]);

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView textView6;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView6 = itemView.findViewById(R.id.textView);
        }
    }
}
