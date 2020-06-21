package com.example.globalpharma.Model;

import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.UploadTask;

public class User {

  private String id;
  private String name;
  private String phone;
  private String password;

  public User(String email, String password) {
    email = phone;
    this.password = password;
  }

  public User() {

  }

  public String getPhone() { return phone; }

  public void setPhone(String phone) { this.phone = phone; }

  public String getId() {
    return id;
  }

  public void setId(String id) {
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

  // - - - FIREBASE STORAGE - - -

  public static DatabaseReference getAllUsers(){
    return new Database<User>(new User()).getReference();
  }

  public static UploadTask uploadImage(Uri imageUri, String child){
    return new Database<User>(new User()).uploadImage(imageUri, child);
  }

  public static void deleteImage(String fullUrl){
    new Database<User>(new User()).deleteImage(fullUrl);
  }

  public static void deleteUser(String id){
    new Database<User>(new User()).removeObject(id);
  }
}