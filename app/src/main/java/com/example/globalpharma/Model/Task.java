package com.example.globalpharma.Model;

public class Task {

    private String Titre;
    private String Date;
    private int ImageUn;

    public Task() {
    }

    public Task(String titre, String date, int imageUn) {
        Titre = titre;
        Date = date;
        ImageUn = imageUn;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getImageUn() {
        return ImageUn;
    }

    public void setImageUn(int imageUn) {
        ImageUn = imageUn;
    }
}
