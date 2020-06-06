package com.example.globalpharma.controller;

import android.content.SharedPreferences;

import com.example.globalpharma.Model.Medication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesManager<T> {
    private SharedPreferences sharedPreferences ;

    public SharedPreferencesManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void addData(String id, T object){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(id, new Gson().toJson(object));
        editor.commit();
    }

    public void addData(String id, List<T> objects){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(id, new Gson().toJson(objects));
        editor.apply();
    }

    public T readData(String name){
        T object = null;
        if(sharedPreferences.contains(name)){
            Type objectType = new TypeToken<T>(){}.getType();
            object = new Gson().fromJson(sharedPreferences.getString(name, ""), objectType);
        }
        return object;
    }

    public List<T> readDatas(String name){
        List<T> objects = new ArrayList<>();
        if(sharedPreferences.contains(name)){
            Type objectType = new TypeToken<List<T>>(){}.getType();
            objects = new Gson().fromJson(name, objectType);
        }
        return objects;
    }

    public void deleteData(String name){

    }

    public void updateData(T object){}
}
