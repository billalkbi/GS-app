package com.example.gs_app;

public class User {
    public String fullName= null;
    public String email ;

    public String typeUser;



    public User(){}
    public User (String fullName, String email, String typeUser ){
        this.fullName = fullName;
        this.email = email;
        this.typeUser = typeUser;
    }

}

