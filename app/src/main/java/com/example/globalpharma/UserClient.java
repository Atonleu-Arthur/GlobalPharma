package com.example.globalpharma;

import android.app.Application;

import com.example.globalpharma.Model.User;

public class UserClient extends Application {

    private User user = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}