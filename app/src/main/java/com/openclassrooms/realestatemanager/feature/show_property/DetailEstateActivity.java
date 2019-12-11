package com.openclassrooms.realestatemanager.feature.show_property;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityDetailEstateBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;

import pub.devrel.easypermissions.EasyPermissions;

public class DetailEstateActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "TAG";
    public static final String ESTATE_ID = "estate_id";
    private ActivityDetailEstateBinding binding;
    private static final String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private GoogleMap googleMap;
    private EstateViewModel estateViewModel;
    private Estate estate;
    private long estateId;
    private double latitude;
    private double longitude;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_estate);

        estateId = getIntent().getLongExtra(ESTATE_ID, 0);
        Log.e("estateId", String.valueOf(estateId));
        this.configureRecyclerView();
        this.configureViewModel();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_id);
        mapFragment.getMapAsync(this);
        getCurrentEstate(estateId);

        this.soldestate();

    }

    //---------------------
    // Configure ViewModel
    //---------------------
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
        //this.estateViewModel.intit(AGENT_ID);
    }

    //----------------
    //UI
    //----------------

    //Configure recyclerView
    private void configureRecyclerView() {
        // List<Estate> estateList = new ArrayList<>();
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
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
                    googleMap.getUiSettings().setMapToolbarEnabled(false);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(latitude, longitude));
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    markerOptions.title("My position");
                    googleMap.addMarker(markerOptions);
                    Log.e("location", "latitude = " + currentLocation.getLatitude());
                    Log.e("location", "longitude = " + currentLocation.getLongitude());
                } else {
                    Log.d(TAG, "Current location is null. Using defaults.");
                    Log.e(TAG, "Exception: %s", task.getException());
                    Toast.makeText(getApplicationContext(), "unable to get current location", Toast.LENGTH_LONG).show();

                }
            });

        } else {
            EasyPermissions.requestPermissions(this, "you must have permission", 789, perms);
        }
    }

    private void getCurrentEstate(long estateId) {
        this.estateViewModel.getEstateFromId(estateId).observe(this, this::updateUi);
    }


    //Configure data
    private void updateUi(Estate estate) {
        this.estate = estate;
        binding.tvMedia.setText(estate.getCity().toUpperCase());
        binding.tvPriceDetail.setText(String.valueOf(estate.getPrice()) + " $");
        binding.editDescriptionDetail.setText(estate.getDescription());
        binding.tvSurfaceDetail.setText(String.valueOf(estate.getSurface()));
        binding.tvSurfaceLandDetail.setText("Land " + estate.getSurfaceLand());
        binding.tvRoomDetail.setText(String.valueOf(estate.getNbRoom()));
        binding.tvBedroomDetail.setText(String.valueOf(estate.getBedroom()));
        binding.tvBathroomDetail.setText(String.valueOf(estate.getBathroom()));
        binding.tvHeatingDetail.setText(estate.getHeating());
        binding.tvLocationDetail.setText(estate.getAddress() + "\n" + estate.getPostalCode() + "\n" + estate.getCity().toUpperCase());
        latitude = estate.getLatitude();
        longitude = estate.getLongitude();
        if (estate.isSold()){
            binding.soldBtn.setImageDrawable(getDrawable(R.drawable.sold_house_color));
        }else if (!estate.isSold()){
            binding.soldBtn.setImageDrawable(getDrawable(R.drawable.house_for_sale_color));
        }
    }

    //sold house
    private void soldestate() {

            binding.soldBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!estate.isSold()) {
                        AlertDialog alertDialog;
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.Theme_MaterialComponents_Dialog);
                        builder.setMessage("Do you want sold this estate?");
                        builder.setPositiveButton("Yes", (dialog, which) -> {
                            estate.setSold(true);
                            estateViewModel.updateEstate(estate);

                        });
                        builder.setNegativeButton("No", (dialog, which) -> {
                        });
                        alertDialog = builder.create();
                        alertDialog.show();
                        alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                        alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                    }else if (estate.isSold()){
                        AlertDialog alertDialog;
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.Theme_MaterialComponents_Dialog);
                        builder.setMessage("Do you want to restore estate to sale?");
                        builder.setPositiveButton("Yes", (dialog, which) -> {
                            estate.setSold(false);
                            estateViewModel.updateEstate(estate);
                        });
                        builder.setNegativeButton("No", (dialog, which) -> {

                        });
                        alertDialog = builder.create();
                        alertDialog.show();
                        alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                        alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                    }


                } });
        }






}
