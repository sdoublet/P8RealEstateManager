package com.openclassrooms.realestatemanager.feature.show_property;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.Estate;
import com.openclassrooms.realestatemanager.databinding.ActivityEstateBinding;

import java.util.ArrayList;
import java.util.List;

public class EstateActivity extends AppCompatActivity {


    private ActivityEstateBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_estate);
        populateData();

    }

    //---------------
    //RECYCLER VIEW
    //---------------

    private void configureRecyclerView(){
        //----------------
        // POPULATE DATA
        //----------------



    }

    public void populateData() {
        List<Estate> estateList = new ArrayList<>();
        estateList.add(new Estate(1, "House", 145000, 245, 8, 3, 1, "Maison de plein pied", R.drawable.photo_seb, "19 rue du bois", 39380,
                "Chamblay", false, "22/08/2019", null, 1, 4.5665, 1.2554));

        EstateAdapter estateAdapter = new EstateAdapter(estateList, this);
        binding.recyclerView.setAdapter(estateAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }
}
