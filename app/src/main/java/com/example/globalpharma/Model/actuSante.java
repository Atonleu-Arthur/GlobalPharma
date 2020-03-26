package com.example.globalpharma.Model;

public class actuSante {

    private String Titre;
    private String Description;
    private int Image;

    public actuSante() {
    }

    public actuSante(String titre, String description, int image) {
        Titre = titre;
        Description = description;
        Image = image;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
