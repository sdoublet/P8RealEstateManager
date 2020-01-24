package com.openclassrooms.realestatemanager.feature.show_property;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ceylonlabs.imageviewpopup.ImagePopup;
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
import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.EstateViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityDetailEstateBinding;
import com.openclassrooms.realestatemanager.feature.add_update_property.UpdateEstateActivity;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.Picture;
import com.openclassrooms.realestatemanager.util.ItemClickSupport;
import com.openclassrooms.realestatemanager.util.MoneyPref;
import com.openclassrooms.realestatemanager.util.PopupActivity;
import com.openclassrooms.realestatemanager.util.Utils;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class DetailEstateActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "TAG";
    public static final String ESTATE_ID = "estate_id";
    public static final String ESTATE_FROM_MAP = "estate_map_id";
    private ActivityDetailEstateBinding binding;
    private static final String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private GoogleMap googleMap;
    private EstateViewModel estateViewModel;
    private Estate estate;
    private long estateId = 0;
    private long allId;
    private double latitude;
    private double longitude;
    private DetailAdapter adapter;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_estate);

        //jsonIntent = getIntent().getStringExtra("estateJson");
        estateId = getIntent().getLongExtra(ESTATE_ID, 0);

        if (estateId == 0) {
            allId = Long.parseLong(getIntent().getStringExtra(ESTATE_FROM_MAP));
        } else {
            allId = estateId;
        }
        Log.e("estateId", String.valueOf(estateId));
        this.configureRecyclerView();
        this.configureViewModel();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_id);
        mapFragment.getMapAsync(this);

        getCurrentEstate(allId);


        this.soldEstate();
        this.updateEstate();
        this.showPoi();
        this.getPhotos(estateId);
        this.onClickRecyclerView();


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
        List<Picture> pictureList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adapter = new DetailAdapter(pictureList, this);
        binding.recyclerViewDetail.setAdapter(adapter);
        binding.recyclerViewDetail.setLayoutManager(layoutManager);


    }

    private void onClickRecyclerView() {
        ItemClickSupport.addTo(binding.recyclerViewDetail, R.layout.row_photo_detail)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Picture picture = adapter.getPicture(position);
                    Uri uri = picture.getUri();
                    String uriString = uri.toString();
                    if (uriString.contains("content")){
                        ImagePopup imagePopup = new ImagePopup(this);
                        imagePopup.setBackgroundColor(Color.BLACK);
                        imagePopup.setFullScreen(true);
                        imagePopup.setHideCloseIcon(true);
                        imagePopup.setImageOnClickClose(true);
                        imagePopup.initiatePopupWithPicasso(uri);
                        imagePopup.viewPopup();
                        Log.e("popup", String.valueOf(position) + uri);
                    }else {
                        Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath());
                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                        ImagePopup imagePopup = new ImagePopup(this);
                        imagePopup.setBackgroundColor(Color.BLACK);
                        imagePopup.setFullScreen(true);
                        imagePopup.setHideCloseIcon(true);
                        imagePopup.setImageOnClickClose(true);
                        imagePopup.initiatePopup(drawable);
                        imagePopup.viewPopup();

                    }

                });
    }


    //FOR MAP
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

    private void getPhotos(long estateId) {
        this.estateViewModel.getAllPicturesFromEstate(estateId).observe(this, this::updatePictureRecyclerView);
    }

    private void updatePictureRecyclerView(List<Picture> pictureList) {
        List<Picture> pictureList1 = new ArrayList<>(pictureList);
        this.adapter.updatePictureData(pictureList1);
        Log.e("DetailPics", String.valueOf(pictureList1.size()));

    }


    //Configure data
    private void updateUi(Estate estate) {

        this.estate = estate;

        binding.editDescriptionDetail.setText(estate.getDescription());
        binding.tvMedia.setText(estate.getCity().toUpperCase());
        // show price in dollars or euros
        if (!MoneyPref.getInstance().isEuro()) {
            binding.tvPriceDetail.setText(Utils.stringFromatPrice(estate.getPrice()) + " $");

        } else {
            binding.tvPriceDetail.setText(Utils.stringFromatPrice(Utils.convertDollarToEuro(estate.getPrice())) + " €");
        }
        binding.tvSurfaceDetail.setText(String.valueOf(estate.getSurface()));
        binding.tvSurfaceLandDetail.setText("Land " + estate.getSurfaceLand());
        binding.tvRoomDetail.setText(String.valueOf(estate.getNbRoom()));
        binding.tvBedroomDetail.setText(String.valueOf(estate.getBedroom()));
        binding.tvBathroomDetail.setText(String.valueOf(estate.getBathroom()));
        binding.tvHeatingDetail.setText(estate.getHeating());
        binding.tvLocationDetail.setText(estate.getAddress() + "\n" + estate.getPostalCode() + "\n" + estate.getCity().toUpperCase());
        latitude = estate.getLatitude();
        longitude = estate.getLongitude();
        if (estate.isSold()) {
            binding.soldBtn.setImageDrawable(getDrawable(R.drawable.sold_house_color));
            binding.tvSoldDate.setText("Sold the " + estate.getSoldDate());
            binding.tvSoldDate.setVisibility(View.VISIBLE);
        } else if (!estate.isSold()) {
            binding.soldBtn.setImageDrawable(getDrawable(R.drawable.house_for_sale_color));
            binding.tvSoldDate.setText("Entry the " + estate.getEntryDate());
            binding.tvSoldDate.setVisibility(View.VISIBLE);

        }
        if (estate.getEntryDate() != null) {
            Log.e("entry", estate.getEntryDate());
        }

    }

    //sold house
    private void soldEstate() {

        binding.soldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!estate.isSold()) {
                    AlertDialog alertDialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.Theme_MaterialComponents_Dialog);
                    builder.setMessage("Do you want sold this estate?");
                    builder.setPositiveButton("Yes", (dialog, which) -> {
                        estate.setSold(true);
                        estate.setSoldDate(Utils.convertDate());
                        estateViewModel.updateEstate(estate);


                    });
                    builder.setNegativeButton("No", (dialog, which) -> {
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                    alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                    alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                } else if (estate.isSold()) {
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


            }
        });
    }

    //show points of interest
    private void showPoi() {

        binding.btnPoi.setOnClickListener(v -> {
            PopupActivity popupActivity = new PopupActivity(DetailEstateActivity.this);
            popupActivity.getCheckBoxSchool().setChecked(estate.isSchool());
            popupActivity.getCheckBoxShop().setChecked(estate.isShop());
            popupActivity.getCheckBoxPark().setChecked(estate.isPark());
            popupActivity.getCheckBoxHospital().setChecked(estate.isHospital());
            popupActivity.getCheckBoxTransport().setChecked(estate.isTransport());
            popupActivity.getCheckBoxAdministration().setChecked(estate.isAdministration());
            popupActivity.show();

        });
    }


    //Update Estate
    private void updateEstate() {
        binding.modeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateEstateActivity.class);
                Gson gson = new Gson();
                String estateJson = gson.toJson(estate);
                intent.putExtra("estateJson", estateJson);
                startActivity(intent);

            }
        });
    }


}
