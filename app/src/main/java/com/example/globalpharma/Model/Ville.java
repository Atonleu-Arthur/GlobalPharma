package com.example.globalpharma.Model;

public class Ville {

    private String Nom ;

    public Ville(String Nom)
    {
        this.Nom=Nom;
    }

    public Ville()
    {

    }
    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }
}
