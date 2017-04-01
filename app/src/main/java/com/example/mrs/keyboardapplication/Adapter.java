package com.example.mrs.keyboardapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mrs on 2017/4/1.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.Viewholder> {
    private Context context;
    private ArrayList<String> mdatas = new ArrayList<>();
    public Adapter(Context context){

        this.context = context;
        for (int i = 0; i < 20; i++) {
            mdatas.add(String.valueOf(i));
        }
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,parent,false));
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        holder.textView.setText(mdatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{
        TextView textView;
        public Viewholder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
