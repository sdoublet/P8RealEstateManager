package com.openclassrooms.realestatemanager.feature.add_property;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.api.ApiGeocoding;
import com.openclassrooms.realestatemanager.api.Result;
import com.openclassrooms.realestatemanager.data.Estate;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding;
import com.openclassrooms.realestatemanager.feature.geolocation.LocationStream;
import com.openclassrooms.realestatemanager.feature.show_property.EstateAdapter;
import com.openclassrooms.realestatemanager.feature.show_property.EstateViewModel;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.util.StorageUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import pub.devrel.easypermissions.EasyPermissions;

import static android.content.ContentValues.TAG;

public class AddPropertyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int REQUEST_CODE = 1234;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};


    private ActivityAddPropertyBinding binding;
    private Estate estate;
    private Disposable disposable;
    private Result apiResult;

    //FOR DATA
    private EstateViewModel estateViewModel;
    private static int USER_ID =1;

    //FILE PURPOSE
   // public static final String FOLDERNAME=  "estateFolder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_property);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        //For array type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerType.setAdapter(adapter);
        binding.spinnerType.setOnItemSelectedListener(this);

        //For array number
        ArrayAdapter<CharSequence> adapterNumber = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerRoom.setAdapter(adapterNumber);
        binding.spinnerBedroom.setAdapter(adapterNumber);
        binding.spinnerBathroom.setAdapter(adapterNumber);
        binding.spinnerRoom.setOnItemSelectedListener(this);
        binding.spinnerBedroom.setOnItemSelectedListener(this);
        binding.spinnerBathroom.setOnItemSelectedListener(this);

        //For array heating
        ArrayAdapter<CharSequence> adapterHeating = ArrayAdapter.createFromResource(this, R.array.heating, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerHeating.setAdapter(adapterHeating);
        binding.spinnerHeating.setOnItemSelectedListener(this);

        //Find location after click
        binding.buttonGeolocation.setOnClickListener(v -> getLocationByGeocoding());

        //save estate
        save();

    }

    //------------------------
    //ACTIONS
    //------------------------
    // Save data after click on save btn
    private void save(){
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ici que je vais faire un insert dans ma base de données
                createEstate();
            }
        });
    }

    //----------------------
    //DATA
    //----------------------
    //Configure ViewModel
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
        this.estateViewModel.intit(USER_ID);
    }

    // get current user



    //Create new Estate
    private void createEstate(){
        try {
            Estate estate = new Estate(USER_ID, binding.spinnerType.getSelectedItem().toString(), Integer.parseInt(binding.editPrice.getText().toString()),
                    Float.parseFloat(binding.editEstateSurface.getText().toString()), Integer.parseInt(binding.spinnerRoom.getSelectedItem().toString()),
                    Integer.parseInt(binding.spinnerBedroom.getSelectedItem().toString()), Integer.parseInt(binding.spinnerBathroom.getSelectedItem().toString()),
                    binding.editDescription.getText().toString(), 1, binding.editAddress.getText().toString(), Integer.parseInt(binding.editZipCode.getText().toString()),
                    binding.editCity.getText().toString(), false, null, null, USER_ID,2.5445, 4.255555);

            Log.e("EstateTag", estate.getAddress() + ", " + estate.getNbRoom());
        }catch (Exception e){
            Toast.makeText(this, "you forget some fields", Toast.LENGTH_SHORT).show();
        }

    }

    // Write on storage
    private void writeOnStorage(){

    }

    // get location by geolocation
    private void getLocation() {
        if (EasyPermissions.hasPermissions(getApplicationContext(), perms)) {
            Task locationResult = fusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Location location = (Location) task.getResult();
                    assert location != null;
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    binding.editLatitude.setText(latitude + "");
                    binding.editLongitude.setText(longitude + "");
                    Log.e("loc", String.valueOf(latitude));
                    Log.e("loc", String.valueOf(longitude));


                } else {
                    Log.d(TAG, "Current location is null. Using defaults.");
                    Log.e(TAG, "Exception: %s", task.getException());
                    Toast.makeText(getApplicationContext(), "unable to get current location", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            EasyPermissions.requestPermissions(this, "you must have permission", REQUEST_CODE, perms);

        }
    }

    private void getLocationByGeocoding() {
        if (EasyPermissions.hasPermissions(getApplicationContext(), perms)) {
            executeHttpRequestWithretrofit("chamblay");
            //Log.e("geocod", location.getLatitude() + "," + location.getLongitude());
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //----------------------
    //HTTP REQUEST
    //----------------------
    public void executeHttpRequestWithretrofit(String address) {
        this.disposable = LocationStream.streamFetchGeocoding(address, String.valueOf(R.string.google_maps_key)).subscribeWith(newObserver());
    }

    private <T> DisposableObserver<T> newObserver() {
        return new DisposableObserver<T>() {
            @Override
            public void onNext(T t) {
                if (t instanceof ApiGeocoding) {
                    apiResult = (Result) ((ApiGeocoding) t).getResults();
                    updateUI((ApiGeocoding) t);
                } else
                    Log.e("TAG", "disposableObserver onNext" + t.getClass());
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    //  Dispose subscription
    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    //-------------------
    //UPDATE UI
    //-------------------
    private void updateUI(ApiGeocoding results) {
        if (results.getResults() != null) {

            binding.editLongitude.setText(results.getResults().get(0).getGeometry().getLocation().getLng().toString());
            binding.editLatitude.setText(results.getResults().get(0).getGeometry().getLocation().getLat().toString());
        }
    }

    // Add property in database
    private void createNewEstate(){

    }

}
