package com.example.globalpharma.Views;

/*
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GpsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
/*
public class GpsFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {/*
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int MY_PERMISSION_CODE = 1000;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    SupportMapFragment mapFragment;
    Button button;
    private double lattitude , longitude;
    private Location mLastConnection;
    private Marker mMarker;
    private LocationRequest mLocationRequest;
    IGoogleAPIService mService;
    private Button Pharmacieprox;


    public GpsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static GpsFragment newInstance(String param1, String param2) {
        GpsFragment fragment = new GpsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate  (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mService= Common.getGoogleAPIService();
            Pharmacieprox.findViewById(R.id.btnPharmacie);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                checkLocationPermission();
            }
         /*   Pharmacieprox.setOnClickListener(v -> nearByPlace("Pharmacie"));

        }
    }

    /*private void nearByPlace(final String placeType) {
        mMap.clear();
        String url=getUrl (lattitude,longitude,placeType);
        mService.getNearByPlaces(url)
                .enqueue(new Callback<MyPlaces>() {
                    @Override
                    public void onResponse(Call<MyPlaces> call, Response<MyPlaces> response) {
                        if(response.isSuccessful())
                        {
                            for(int i=0;i<response.body().getResults().length;i++)
                            {
                                MarkerOptions markerOptions=new MarkerOptions();
                                Results googlePlace =response.body().getResults()[i];
                                double lat =Double.parseDouble(googlePlace.getGeometry().getLocation().getLat());
                                double lng =Double.parseDouble(googlePlace.getGeometry().getLocation().getLng());
                                String placeName=googlePlace.getName();
                                String vicinity =googlePlace.getVicinity();
                                LatLng latLng=new LatLng(lat,lng);
                                markerOptions.position(latLng);
                                markerOptions.title(placeName);
                                if(placeType.equals("Pharmacie"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacy));
                                //Pour l'implementation des pharmacie des gardes
                                /**  else if(placeType.equals("Pharmacie"))
                                 markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacy));**/
                               /* mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MyPlaces> call, Throwable t) {

                    }
                });
    }

    /*  private String getUrl(double lattitude, double longitude, String placeType) {
      StringBuilder googlePlaceUrl =new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("localisation"+lattitude+longitude);
        googlePlaceUrl.append("&type="+placeType);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+getResources().getString(R.string.Apiplaces));
        Log.d("getUrl",googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }*/
/*
    private boolean checkLocationPermission() {
        if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION))

                ActivityCompat.requestPermissions(getActivity(),new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },MY_PERMISSION_CODE);
            else

                ActivityCompat.requestPermissions(getActivity(),new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },MY_PERMISSION_CODE);
            return false;
        }
        else
            return true;
    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case MY_PERMISSION_CODE:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if(mGoogleApiClient==null)
                            buildGoogleApiClien();
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                    Toast.makeText(getContext(),"Permission refusée",Toast.LENGTH_SHORT).show();
            }
            break;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_gps, container, false);
        mapFragment=(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googlemap);

        mapFragment.getMapAsync(this);
        mService=Common.getGoogleAPIService();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {

                buildGoogleApiClien();
                mMap.setMyLocationEnabled(true);

            }
        }
        else
        {
            buildGoogleApiClien();
            mMap.setMyLocationEnabled(true);
        }


    }

    private synchronized void buildGoogleApiClien() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000).setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastConnection =location;
        if(mMarker!=null)
            mMarker.remove();

        lattitude=location.getLatitude();
        longitude=location.getLongitude();

        LatLng latLng = new LatLng(lattitude,longitude);
        MarkerOptions markerOptions =new MarkerOptions()
                .position(latLng)
                .title("Votre position")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMarker=mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        if(mGoogleApiClient!=null)
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
    }

*//*
}
*/