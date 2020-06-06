package com.example.globalpharma.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Serialization<T> {
    private Gson gson;


    public Serialization() {
         gson = new Gson();
    }

    public String serialize(T object){
        return gson.toJson(object);
    }

    public T deserialize(String object){
        Type type = new TypeToken<T>(){}.getType();
        return gson.fromJson(object, type);
    }

    public String serializeList(List<T> objects){
        return gson.toJson(objects);
    }

    public T deserializeList(String objects){
        Type type = new TypeToken<List<T>>(){}.getType();
        return gson.fromJson(objects, type);
    }
}
