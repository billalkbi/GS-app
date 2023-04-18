package com.example.gs_app;

public class Appointment {
    String fullName ;
    String store;
   public String dateAppointment;
    String timeAppointment;
    String reasenOfAppointment;
    String description;

    public Appointment (){}
    public Appointment(String fullName, String store, String dateAppointment, String timeAppointment, String reasenOfAppointment, String description) {
        this.fullName = fullName;
        this.store = store;
        this.dateAppointment = dateAppointment;
        this.timeAppointment = timeAppointment;
        this.reasenOfAppointment = reasenOfAppointment;
        this.description = description;

    }

    public String getFullName() {
        return fullName;
    }

    public String getStore() {
        return store;
    }

    public String getDateAppointment() {
        return dateAppointment;
    }

    public String getTimeAppointment() {
        return timeAppointment;
    }

    public String getReasenOfAppointment() {
        return reasenOfAppointment;
    }

    public String getDescription() {
        return description;
    }


}