package com.example.gs_app;

public class User {
    public String fullName= null;
    public String email ;

    public String typeUser;


    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public User(){}
    public User (String fullName, String email, String typeUser ){
        this.fullName = fullName;
        this.email = email;
        this.typeUser = typeUser;
    }

}

