package com.example.gs_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppointmentAdapter_tech extends RecyclerView.Adapter<AppointmentAdapter_tech.MyViewHolder> {
    Context context;
    ArrayList<Appointment> list;
    private OnItemClickListener listener;
    private OnItemClickListener listener2;

    public AppointmentAdapter_tech() {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onModifyClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        listener = clickListener;
        listener2 = clickListener;
    }

    public AppointmentAdapter_tech(Context context, ArrayList<Appointment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AppointmentAdapter_tech.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item2, parent, false);
        return new MyViewHolder(v, listener, listener2);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter_tech.MyViewHolder holder, int position) {
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fullname, dateAppointment, problem, hour;
        ImageView btnDelete,btnModify;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener, OnItemClickListener listener2) {
            super(itemView);

            fullname = itemView.findViewById(R.id.TVfullName);
            dateAppointment = itemView.findViewById(R.id.TVdate);
            hour = itemView.findViewById(R.id.TVhour);
            problem = itemView.findViewById(R.id.TVproblem);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnModify = itemView.findViewById(R.id.btnassign);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

            btnModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onModifyClick(getAdapterPosition());
                }
            });

        }
    }
}
