package com.example.jose.actividad_firebase;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jose.actividad_firebase.Model.User;

import java.util.ArrayList;

/**
 * Created by Jose on 14/12/2017.
 */

public class RecyclerAdapter_Users extends RecyclerView.Adapter<RecyclerAdapter_Users.ViewHolder>{

    ArrayList<User> users;

    public RecyclerAdapter_Users(ArrayList<User> users){
        this.users = users;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView email;
        TextView name;
        TextView adress;

        public ViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.CV_ItemName);
            email = (TextView)itemView.findViewById(R.id.CV_ItemDescription);
            name = (TextView) itemView.findViewById(R.id.CV_Name);
            adress = (TextView) itemView.findViewById(R.id.CV_UserAdress);
        }
    }


    @Override
    public RecyclerAdapter_Users.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_user, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter_Users.ViewHolder holder, int position) {
        holder.userName.setText(users.get(position).getUserName());
        holder.email.setText(users.get(position).getEmail());
        holder.name.setText(users.get(position).getName());
        holder.adress.setText(users.get(position).getAdress());

    }

    @Override
    public int getItemCount()
    {
        if(users.isEmpty()){
            return 0;
        }else{
            return users.size();
        }
    }
}
