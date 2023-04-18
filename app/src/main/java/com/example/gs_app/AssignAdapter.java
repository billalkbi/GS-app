package com.example.gs_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AssignAdapter extends RecyclerView.Adapter<AssignAdapter.MyViewHolder> {
    Context context;
    ArrayList<User> list;
    private OnItemClickListener listener;
    private OnItemClickListener listener2;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        listener = clickListener;
        listener2 = clickListener;
    }

    public AssignAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item3, parent, false);
        return new MyViewHolder(v, listener, listener2);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignAdapter.MyViewHolder holder, int position) {
        User tech = list.get(position);
        holder.fullname.setText(tech.getFullName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fullname;
        Button savebtn;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener, OnItemClickListener listener2) {
            super(itemView);

            fullname = itemView.findViewById(R.id.TVhour);
            savebtn = itemView.findViewById(R.id.selectbtn);
            savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });


        }
    }
}

