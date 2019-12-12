package com.openclassrooms.realestatemanager.feature.add_property;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding;
import com.openclassrooms.realestatemanager.models.Estate;

public class UpdateEstateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    ActivityAddPropertyBinding binding;
    private Estate estate;
    private String estateJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_property);
        estateJson = getIntent().getStringExtra("estateJson");
        this.getEstateFromJson();
        configureSpinners();
        displayEstateData();

    }

    private void getEstateFromJson(){
        Gson gson = new Gson();
        estate = gson.fromJson(estateJson, Estate.class);
    }

    //UI
    private void displayEstateData(){
        binding.editPrice.setText(String.valueOf(estate.getPrice()));
        binding.editEstateSurface.setText(String.valueOf(estate.getSurface()));
        binding.editLandSurface.setText(String.valueOf(estate.getSurfaceLand()));
        binding.editDescription.setText(estate.getDescription());
        binding.editAddress.setText(estate.getAddress());
        binding.editZipCode.setText(String.valueOf(estate.getPostalCode()));
        binding.editCity.setText(estate.getCity());

        binding.spinnerRoom.setSelection(estate.getNbRoom());
        
    }
    private void configureSpinners() {
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
}
