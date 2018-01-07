package com.example.jose.actividad_firebase;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jose.actividad_firebase.Model.Item;

import java.util.ArrayList;

/**
 * Created by Jose on 14/12/2017.
 */

public class RecyclerAdapter_Items extends RecyclerView.Adapter<RecyclerAdapter_Items.ViewHolder>{

    ArrayList<Item> items;

    public RecyclerAdapter_Items(ArrayList<Item> items){
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView description;
        TextView price;


        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.CV_ItemName);
            description = (TextView)itemView.findViewById(R.id.CV_ItemDescription);
            price = (TextView) itemView.findViewById(R.id.CV_ItemPrice);

        }
    }

    public void updateAdapter(ArrayList<Item> items){

        this.items = items;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerAdapter_Items.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter_Items.ViewHolder holder, int position) {
        holder.name.setText(items.get(position).getName());
        holder.description.setText(items.get(position).getDescription());
        holder.price.setText(items.get(position).getPrice());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
