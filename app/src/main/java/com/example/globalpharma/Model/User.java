package com.example.globalpharma.Model;

public class User {

    private int id;
    private String name;
    private String phone;
    private String password;

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
