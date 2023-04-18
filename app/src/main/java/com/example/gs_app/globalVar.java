package com.example.gs_app;

public class globalVar {
    public static User  currentUser = new User();
    public static void reset() {
        currentUser= null;
    }
}
