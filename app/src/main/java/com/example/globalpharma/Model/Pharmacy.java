package com.example.globalpharma.Model;

public class Pharmacy {

    private  String PlaceName;
    private String Vicinoty;
    private  boolean Degarde;
    private String Phone;

    public  Pharmacy()
    {

    }
    public Pharmacy(String placename,String vicinity,boolean degarde,String phone)
    {
        this.PlaceName=placename;
        this.Vicinoty=vicinity;
        this.Degarde=degarde;
        this.Phone=phone;
    }
    public Pharmacy(String placename,String vicinity)
    {
        this.PlaceName=placename;
        this.Vicinoty=vicinity;

    }
    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setDegarde(boolean degarde) {
        Degarde = degarde;
    }

    public boolean isDegarde() {
        return Degarde;
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }

    public String getVicinoty() {
        return Vicinoty;
    }

    public void setVicinoty(String vicinoty) {
        Vicinoty = vicinoty;
    }
}
