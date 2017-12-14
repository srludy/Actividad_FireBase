package com.example.jose.actividad_firebase;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Jose on 14/12/2017.
 */

public class RecyclerAdapter_MainAdmin extends RecyclerView.Adapter<RecyclerAdapter_MainAdmin.ViewHolder>{

    ArrayList<User> users;
    public RecyclerAdapter_MainAdmin(ArrayList<User> users){
        this.users = users;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public RecyclerAdapter_MainAdmin.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter_MainAdmin.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
