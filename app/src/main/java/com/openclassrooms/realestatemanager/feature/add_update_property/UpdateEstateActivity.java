package com.openclassrooms.realestatemanager.feature.add_update_property;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.EstateViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.api.ApiGeocoding;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding;
import com.openclassrooms.realestatemanager.feature.geolocation.LocationStream;
import com.openclassrooms.realestatemanager.feature.show_property.EstateViewHolder;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.util.Utils;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

public class UpdateEstateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    ActivityAddPropertyBinding binding;
    private EstateViewModel estateViewModel;
    private Estate estate;
    private String estateJson;
    private Disposable disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_property);
        estateJson = getIntent().getStringExtra("estateJson");
        this.getEstateFromJson();
        this.configureViewModel();
        this.configureSpinners();
        this.displayEstateData();
        this.updateDb();

    }

    private void getEstateFromJson() {
        Gson gson = new Gson();
        estate = gson.fromJson(estateJson, Estate.class);
    }

    //UI
    private void displayEstateData() {
        binding.editPrice.setText(String.valueOf(estate.getPrice()));
        binding.editEstateSurface.setText(String.valueOf(estate.getSurface()));
        binding.editLandSurface.setText(String.valueOf(estate.getSurfaceLand()));
        binding.editDescription.setText(estate.getDescription());
        binding.editAddress.setText(estate.getAddress());
        binding.editZipCode.setText(String.valueOf(estate.getPostalCode()));
        binding.editCity.setText(estate.getCity());
        binding.checkboxSchool.setChecked(estate.isSchool());
        binding.checkboxShop.setChecked(estate.isShop());
        binding.checkboxPark.setChecked(estate.isPark());
        binding.checkboxHospital.setChecked(estate.isHospital());
        binding.checkboxTransport.setChecked(estate.isTransport());
        binding.checkboxAdministration.setChecked(estate.isAdministration());


    }

    private void configureSpinners() {

        int spinnerSelection;
        //For array type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);

        spinnerSelection = adapter.getPosition(estate.getType());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerType.setAdapter(adapter);
        binding.spinnerType.setSelection(spinnerSelection);
        binding.spinnerType.setOnItemSelectedListener(this);
        //For array number
        ArrayAdapter<CharSequence> adapterNumber = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Room
        spinnerSelection = adapterNumber.getPosition(String.valueOf(estate.getNbRoom()));
        binding.spinnerRoom.setAdapter(adapterNumber);
        binding.spinnerRoom.setSelection(spinnerSelection);
        //Bedroom
        spinnerSelection = adapterNumber.getPosition(String.valueOf(estate.getBedroom()));
        binding.spinnerBedroom.setAdapter(adapterNumber);
        binding.spinnerBedroom.setSelection(spinnerSelection);
        //Bathroom
        spinnerSelection = adapterNumber.getPosition(String.valueOf(estate.getBathroom()));
        binding.spinnerBathroom.setAdapter(adapterNumber);
        binding.spinnerBathroom.setSelection(spinnerSelection);
        //click listener
        binding.spinnerRoom.setOnItemSelectedListener(this);
        binding.spinnerBedroom.setOnItemSelectedListener(this);
        binding.spinnerBathroom.setOnItemSelectedListener(this);

        //For array heating
        ArrayAdapter<CharSequence> adapterHeating = ArrayAdapter.createFromResource(this, R.array.heating, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelection = adapterHeating.getPosition(estate.getHeating());
        binding.spinnerHeating.setAdapter(adapterHeating);
        binding.spinnerHeating.setSelection(spinnerSelection);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
    }

    //UPDATE UI
    private void updateUi() {
        estateViewModel.updateEstate(estate);

    }

    //save onclick save btn
    private void updateDb() {
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeValueOfEstate();

                Toast.makeText(getApplicationContext(), "Your estate is update", Toast.LENGTH_SHORT).show();
                String address = binding.editAddress.getText().toString() + " " + binding.editZipCode.getText().toString() + " "
                        + binding.editCity.getText().toString() + ", " + Utils.localeCountry(getApplicationContext());
                executeHttpRequestWithretrofit(address);
                Log.e("onclick", "ok");
                updateUi();
            }
        });
    }

    //take value
    private void takeValueOfEstate() {
        estate.setPrice(Integer.parseInt(binding.editPrice.getText().toString()));
        estate.setDescription(binding.editDescription.getText().toString());
        estate.setAddress(binding.editAddress.getText().toString());
        estate.setPostalCode(Integer.parseInt(binding.editZipCode.getText().toString()));
        estate.setCity(binding.editCity.getText().toString());
        estate.setSurface(Float.parseFloat(binding.editEstateSurface.getText().toString()));
        estate.setSurfaceLand(Float.parseFloat(binding.editLandSurface.getText().toString()));
        estate.setType(binding.spinnerType.getSelectedItem().toString());
        estate.setNbRoom(Integer.parseInt(binding.spinnerRoom.getSelectedItem().toString()));
        estate.setBedroom(Integer.parseInt(binding.spinnerBedroom.getSelectedItem().toString()));
        estate.setBathroom(Integer.parseInt(binding.spinnerBathroom.getSelectedItem().toString()));
        estate.setHeating(binding.spinnerHeating.getSelectedItem().toString());


    }

    // TODO: 14/12/2019 put execute http request for new address
    //----------------------
    //HTTP REQUEST
    //----------------------
    public void executeHttpRequestWithretrofit(String address) {
        this.disposable = LocationStream.streamFetchGeocoding(address, BuildConfig.google_maps_api_key).subscribeWith(new DisposableObserver<ApiGeocoding>() {
            @Override
            public void onNext(ApiGeocoding apiGeocoding) {

                estate.setLatitude(apiGeocoding.getResults().get(0).getGeometry().getLocation().getLat());
                estate.setLongitude(apiGeocoding.getResults().get(0).getGeometry().getLocation().getLng());
                updateUi();


            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "on error" + e.getMessage() + e.getLocalizedMessage() + e.fillInStackTrace() + e.getCause());
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    int code = httpException.code();
                    String message = httpException.getMessage();

                    Log.e("TAG", code + message + httpException.response());
                }

            }

            @Override
            public void onComplete() {
                Log.e("TAG", "on complete");
            }
        });
    }

}
