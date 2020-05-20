package com.example.globalpharma.util;

import android.app.Application;

import com.example.globalpharma.Model.User;


public class UserClient extends Application {

    private static User user = null;

    public User getUser() {
        return user;
    }

    public static void setUser(User user1) {
        user = user1;
    }

}
