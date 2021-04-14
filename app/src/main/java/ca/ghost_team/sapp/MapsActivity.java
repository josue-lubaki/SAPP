package ca.ghost_team.sapp;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import ca.ghost_team.sapp.activity.DetailAnnonce;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String titre;
    private String zip;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create Bundle
        Bundle bundle = getIntent().getExtras();
        titre = bundle.getString(DetailAnnonce.MAP_TITRE_REQUEST);
        zip = bundle.getString(DetailAnnonce.MAP_ZIP_REQUEST);
        description = bundle.getString(DetailAnnonce.MAP_DESCRIPTION_REQUEST);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double[] coordonnees = new double[2];

        try {
            coordonnees = getLatLngByZipcode(zip);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(coordonnees[0], coordonnees[1]);
        Marker marker =  mMap.addMarker(new MarkerOptions()
                .position(location)
                .title(titre)
                .snippet(description)
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
        marker.showInfoWindow();
    }

    public double[] getLatLngByZipcode(String zipcode) throws IOException {
        Geocoder geocoder = new Geocoder(this);
        List<Address> liste = geocoder.getFromLocationName(zipcode,1);
        double latitude = liste.get(0).getLatitude();
        double longitude = liste.get(0).getLongitude();


        Log.i("XXXX", "Latitude :" + latitude + "\nLongitude : " + longitude);
        return new double[]{latitude,longitude};
    }
}