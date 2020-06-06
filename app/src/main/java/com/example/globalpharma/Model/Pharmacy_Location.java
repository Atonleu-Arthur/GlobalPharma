package com.example.globalpharma.Model;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Pharmacy_Location {
    private GeoPoint geoPoint;
    private Pharmacy pharmacy;
    private @ServerTimestamp Date timestring;
    private Boolean degarde;
    private Ville ville;
    private String Pharmacy_Location;

    public Pharmacy_Location()
    {

    }
    public Pharmacy_Location(String Pharmacy_Location ,Date timestring,GeoPoint geoPoint,Pharmacy pharmacy,Ville ville,Boolean degarde)
    {
        this.geoPoint=geoPoint;
        this.pharmacy=pharmacy;
        this.timestring=timestring;
        this.ville=ville;
        this.degarde=degarde;
        this.Pharmacy_Location=Pharmacy_Location;

    }
    public Pharmacy_Location(Date timestring,GeoPoint geoPoint,Pharmacy pharmacy,Ville ville)
    {
        this.geoPoint=geoPoint;
        this.pharmacy=pharmacy;
        this.timestring=timestring;
        this.ville=ville;

    }
    public Pharmacy_Location(Pharmacy pharmacy)
    {

        this.pharmacy=pharmacy;


    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public Date getTimestring() {
        return timestring;
    }

    public void setTimestring(Date timestring) {
        this.timestring = timestring;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

}
