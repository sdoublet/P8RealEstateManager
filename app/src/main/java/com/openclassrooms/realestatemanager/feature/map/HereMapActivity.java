package com.openclassrooms.realestatemanager.feature.map;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.here.sdk.core.GeoCoordinates;
import com.here.sdk.mapviewlite.LoadSceneCallback;
import com.here.sdk.mapviewlite.MapImage;
import com.here.sdk.mapviewlite.MapImageFactory;
import com.here.sdk.mapviewlite.MapMarker;
import com.here.sdk.mapviewlite.MapMarkerImageStyle;
import com.here.sdk.mapviewlite.MapStyle;
import com.here.sdk.mapviewlite.SceneError;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityHereMapBinding;

import pub.devrel.easypermissions.EasyPermissions;

public class HereMapActivity extends AppCompatActivity {

    private static final String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private static final String TAG = "tag";
    private static final int REQUEST_CODE = 12345;
    private MapMarker marker;

    private ActivityHereMapBinding binding;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_here_map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        binding.mapView.onCreate(savedInstanceState);
        loadScene();

    }

    private void loadScene() {
        if (EasyPermissions.hasPermissions(this, perms)){
        binding.mapView.getMapScene().loadScene(MapStyle.NORMAL_DAY, new LoadSceneCallback() {
            @Override
            public void onLoadScene(@Nullable SceneError sceneError) {
                if (sceneError == null) {
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            binding.mapView.getCamera().setTarget(new GeoCoordinates(location.getLatitude(), location.getLongitude()));
                            binding.mapView.getCamera().setZoomLevel(14);
                            MapImage mapImage = MapImageFactory.fromResource(getResources(), R.drawable.pin);
                            marker = new MapMarker(new GeoCoordinates(location.getLatitude(), location.getLongitude()));
                            MapMarkerImageStyle mapMarkerImageStyle = new MapMarkerImageStyle();
                            mapMarkerImageStyle.setScale(0.07f);
                            marker.addImage(mapImage, mapMarkerImageStyle);
                            binding.mapView.getMapScene().addMapMarker(marker);
                        }
                    });


                } else {
                    Log.d(TAG, "onLoadScene failed" + sceneError.toString());
                }
            }
        });
    }else  EasyPermissions.requestPermissions(this, "you must have permission", REQUEST_CODE, perms);}

    @Override
    protected void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
    }
}
