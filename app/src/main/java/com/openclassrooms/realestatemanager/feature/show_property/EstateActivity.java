package com.openclassrooms.realestatemanager.feature.show_property;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.openclassrooms.realestatemanager.MainActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.Estate;
import com.openclassrooms.realestatemanager.data.User;
import com.openclassrooms.realestatemanager.databinding.ActivityEstateBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class EstateActivity extends AppCompatActivity {


    private ActivityEstateBinding binding;

    // FOR DATA
    private EstateViewModel estateViewModel;
    private EstateAdapter adapter;
    private static int USER_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_estate);
        populateData();

    }

    //---------------------
    // Configure ViewModel
    //---------------------
    private void configureViewModel(){
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
        this.estateViewModel.intit(USER_ID);
    }



    //---------------
    //RECYCLER VIEW
    //---------------


        //----------------
        // POPULATE DATA
        //----------------


    public void populateData() {
        List<Estate> estateList = new ArrayList<>();
        estateList.add(new Estate(1, "House", 145000, 245, 8, 3, 1, "Maison de plein pied", R.drawable.photo_seb, "19 rue du bois", 39380,
                "Chamblay", false, "22/08/2019", null, 1, 4.5665, 1.2554));

        EstateAdapter estateAdapter = new EstateAdapter(estateList, this);
        binding.recyclerView.setAdapter(estateAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }


}
