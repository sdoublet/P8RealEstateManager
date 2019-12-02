package com.openclassrooms.realestatemanager.feature.show_property;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.here.sdk.core.GeoCoordinates;
import com.here.sdk.mapviewlite.LoadSceneCallback;
import com.here.sdk.mapviewlite.MapStyle;
import com.here.sdk.mapviewlite.SceneError;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.Estate;
import com.openclassrooms.realestatemanager.databinding.ActivityDetailEstateBinding;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static android.content.ContentValues.TAG;

public class DetailEstateActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "TAG" ;
    private ActivityDetailEstateBinding binding;
    private static final String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private GoogleMap googleMap;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_estate);

        configureRecyclerView();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
       // binding.mapViewLiteDetail.onCreate(savedInstanceState);
        //loadScene();

    }

    //----------------
    //UI
    //----------------

    //Configure recyclerView
    private void configureRecyclerView(){
        List<Estate> estateList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerViewDetail.setLayoutManager(layoutManager);

        // TODO: 02/12/2019 créer l'adapter et le viewholder
        // TODO: 02/12/2019 créer une base pour les photos
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        displayCurrentLocation(googleMap);
    }

    private void displayCurrentLocation(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (EasyPermissions.hasPermissions(getApplicationContext(), perms)) {
            googleMap.setMyLocationEnabled(true);
            Task locationResult = fusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Location currentLocation = (Location) task.getResult();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 13));
                    googleMap.getUiSettings().setMapToolbarEnabled(false);
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    markerOptions.position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
//                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//                    markerOptions.title("My position");
//                    marker = mMap.addMarker(markerOptions);
                    Log.e("location", "latitude = " + currentLocation.getLatitude());
                    Log.e("location", "longitude = " + currentLocation.getLongitude());
                } else {
                    Log.d(TAG, "Current location is null. Using defaults.");
                    Log.e(TAG, "Exception: %s", task.getException());
                    Toast.makeText(getApplicationContext(), "unable to get current location", Toast.LENGTH_LONG).show();
                    LatLng mDefaultLocation = new LatLng(-34, 151);
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
//                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                }
            });

        } else {
            EasyPermissions.requestPermissions(this, "you must have permission", 789, perms);
        }
    }

    //Load scene for map
//    private void loadScene(){
//        if (EasyPermissions.hasPermissions(this, perms)){
//            binding.mapViewLiteDetail.getMapScene().loadScene(MapStyle.NORMAL_DAY, new LoadSceneCallback() {
//                @Override
//                public void onLoadScene(@Nullable SceneError sceneError) {
//                    if (sceneError == null){
//
//                                double longitude = 5.705733;
//                                double latitude = 46.992974;
//                                binding.mapViewLiteDetail.getCamera().setTarget(new GeoCoordinates(latitude, longitude));
//                                binding.mapViewLiteDetail.getCamera().setZoomLevel(18);
//
//
//                    }else{
//                        Log.d(TAG, "onLoadScene failed" + sceneError.toString());
//                    }
//                }
//            });
//        }else EasyPermissions.requestPermissions(this, "you must have permission", 555444);
//    }
}
