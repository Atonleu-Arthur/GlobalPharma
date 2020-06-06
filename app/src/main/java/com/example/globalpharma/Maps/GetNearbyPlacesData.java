package com.example.globalpharma.Maps;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.globalpharma.Model.ClusterMarker;
import com.example.globalpharma.Model.Pharmacy;
import com.example.globalpharma.Model.Pharmacy_Location;
import com.example.globalpharma.Model.UserLocation;
import com.example.globalpharma.Model.Ville;
import com.example.globalpharma.R;
import com.example.globalpharma.util.MyClusterManagerRenderer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private GoogleMap mMap;
    private ArrayList<ClusterMarker> mClusterMarkers = new ArrayList<>();
    private ClusterManager<ClusterMarker> mClusterManager;
    private MyClusterManagerRenderer mClusterManagerRenderer;
    private FirebaseFirestore mDb;
    private Activity activity;
    private Context context;
    private Pharmacy_Location pharmacy_location;
    private ArrayList<UserLocation> mUserLocations = new ArrayList<>();

    String url;

    public  GetNearbyPlacesData(Context context, Activity activity) {
        this.activity = activity;
        this.context = context;

    }

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];

        DownloadURL downloadURL = new DownloadURL();
        try {
            googlePlacesData = downloadURL.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {

        List<HashMap<String, String>> nearbyPlaceList;
        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
        Log.d("nearbyplacesdata", "called parse method");
        showNearbyPlaces(nearbyPlaceList);
    }

    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList) {
        if (mMap != null) {

            if (mClusterManager == null) {
                mClusterManager = new ClusterManager<ClusterMarker>(context, mMap);
            }
            if (mClusterManagerRenderer == null) {
                mClusterManagerRenderer = new MyClusterManagerRenderer(
                        activity,
                        mMap,
                        mClusterManager
                );
                mClusterManager.setRenderer(mClusterManagerRenderer);
            }

            for (int i = 0; i < nearbyPlaceList.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                HashMap<String, String> googlePlace = nearbyPlaceList.get(i);

                String placeName = googlePlace.get("place_name");
                String vicinity = googlePlace.get("vicinity");
                String phone=googlePlace.get("international_phone_number");
                double lat = Double.parseDouble(googlePlace.get("lat"));
                double lng = Double.parseDouble(googlePlace.get("lng"));
                LatLng latLng = new LatLng(lat, lng);
                String snippet = placeName + ":" + vicinity;
                GeoPoint geoPoint = new GeoPoint(lat,lng);
                pharmacy_location=new Pharmacy_Location();
                pharmacy_location.setGeoPoint(geoPoint);
                pharmacy_location.setTimestring(null);
                pharmacy_location.setPharmacy(new Pharmacy(placeName,vicinity,false,phone));
                pharmacy_location.setVille(new Ville("Douala"));


                markerOptions.position(latLng);
                markerOptions.title(placeName + " : " + vicinity);

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        marker.showInfoWindow();
                    }
                });

                int avatar = R.drawable.pharmacy_50px; // Marqueur par defaut
                ClusterMarker newClusterMarker = new ClusterMarker(
                        new LatLng(lat, lng),
"",
                        snippet,

                        avatar
                );


                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
         /*   for(UserLocation userLocation: mUserLocations){

                Log.d(TAG, "addMapMarkers: location: " + userLocation.getGeo_point().toString());
                try{
                    String snippet = "";

                        snippet = "Moi";

                    /*else{
                        snippet = "Determiner le chemin vers " + userLocation.getUser().getUsername() + "?";
                    }*/

                   /* int avatar = R.drawable.pharmacy_50px; // Marqueur par defaut

                    ClusterMarker newClusterMarker = new ClusterMarker(
                            new LatLng(lat, lng),
                            userLocation.getUser().getUsername(),
                            snippet,
                            avatar
                    );
                    mClusterManager.addItem(newClusterMarker);
                    mClusterMarkers.add(newClusterMarker);

                }catch (NullPointerException e){
                    Log.e(TAG, "addMapMarkers: NullPointerException: " + e.getMessage() );
                }

            }*/
                mClusterManager.addItem(newClusterMarker);
                SavePharmacyLocation();
                mClusterMarkers.add(newClusterMarker);

                mClusterManager.cluster();

                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

            }
        }
    }

    public String getNewId()
    {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public void SavePharmacyLocation()
    {

        mDb = FirebaseFirestore.getInstance();
        if(pharmacy_location != null){
            DocumentReference locationRef = mDb
                    .collection("Pharmacy_Location")
                    .document(getNewId());


            locationRef.set(pharmacy_location).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "saveUserLocation: \ninsertion de Pharmacy location." +
                                "\n latitude: " + pharmacy_location.getGeoPoint().getLatitude() +
                                "\n longitude: " + pharmacy_location.getGeoPoint().getLongitude());
                    }
                }
            });
        }
    }
}




