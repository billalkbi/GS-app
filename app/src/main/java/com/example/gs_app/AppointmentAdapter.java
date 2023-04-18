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

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {
    Context context;
    ArrayList<Appointment> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        listener = clickListener;
    }

    public AppointmentAdapter(Context context, ArrayList<Appointment> list) {
        this.context= context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.MyViewHolder holder, int position) {
         Appointment appointment = list.get(position);
         holder.fullname.setText(appointment.getFullName());
         holder.dateAppointment.setText(appointment.dateAppointment);
         holder.hour.setText(appointment.getTimeAppointment());
         holder.problem.setText(appointment.getReasenOfAppointment());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView fullname, dateAppointment, problem, hour;
        ImageView btnDelete,btnModify;
        public  MyViewHolder(@NonNull View itemView,OnItemClickListener listener){
            super(itemView);

            fullname = itemView.findViewById(R.id.TVfullName);
            dateAppointment = itemView.findViewById(R.id.TVdate);
            hour = itemView.findViewById(R.id.TVhour);
            problem = itemView.findViewById(R.id.TVproblem);
            btnDelete= itemView.findViewById(R.id.btnDelete);
            btnModify= itemView.findViewById(R.id.btnModify);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());

                }
            });
        }
    }


}
