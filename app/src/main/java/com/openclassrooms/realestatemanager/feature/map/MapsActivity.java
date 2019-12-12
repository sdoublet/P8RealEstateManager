package com.openclassrooms.realestatemanager.feature.map;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.feature.show_property.EstateViewModel;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static android.content.ContentValues.TAG;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    public static final String API_KEY = String.valueOf(R.string.google_maps_key);
    private static final int REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 12f;
    private FusedLocationProviderClient fusedLocationProviderClient;


    private GoogleMap mMap;
    private Marker marker;
    private EstateViewModel estateViewModel;
    private Estate estate;
    private List<Estate> estates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        this.configureViewModel();
        this.getAllEstate();
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

        displayCurrentLocation(googleMap);
    }

    private void displayCurrentLocation(GoogleMap googleMap) {
        mMap = googleMap;

        if (EasyPermissions.hasPermissions(getApplicationContext(), perms)) {
            mMap.setMyLocationEnabled(true);
            Task locationResult = fusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Location currentLocation = (Location) task.getResult();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM));
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    markerOptions.title("My position");
                    marker = mMap.addMarker(markerOptions);
                    Log.e("location", "latitude = " + currentLocation.getLatitude());
                    Log.e("location", "longitude = " + currentLocation.getLongitude());
                } else {
                    Log.d(TAG, "Current location is null. Using defaults.");
                    Log.e(TAG, "Exception: %s", task.getException());
                    Toast.makeText(getApplicationContext(), "unable to get current location", Toast.LENGTH_LONG).show();
                    LatLng mDefaultLocation = new LatLng(-34, 151);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                }
            });

        } else {
            EasyPermissions.requestPermissions(this, "you must have permission", REQUEST_CODE, perms);
        }
    }

    //---------------------
    // Configure ViewModel
    //---------------------
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);

    }

    //get all estate
    private void getAllEstate() {
        this.estateViewModel.getAllEstates().observe(this, this::displayEstateonMap);
        Log.e("est", "done");
    }

    //Diplay estate on map
    private void displayEstateonMap(List<Estate> estateList){
        //pour tout les estates de la list
        //recupère latlng de chaque
        //met un marqueur sur chaque
        this.estates = estateList;
        for (int i=0; i<estateList.size(); i++){
            estate = estates.get(i);
            marker = mMap.addMarker(new MarkerOptions()
            .position(new LatLng(estate.getLatitude(), estate.getLongitude()))
            .title(String.valueOf(estate.getPrice())));
          marker.setIcon(BitmapDescriptorFactory.defaultMarker());

        }





    }
    private Drawable resize(Drawable image) {
           Bitmap b = ((BitmapDrawable)image).getBitmap();
           Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 10, 10, false);
           return new BitmapDrawable(getResources(), bitmapResized);
       }




    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
