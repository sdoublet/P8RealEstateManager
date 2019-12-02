package com.openclassrooms.realestatemanager.feature.show_property;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.Estate;
import com.openclassrooms.realestatemanager.databinding.ActivityDetailEstateBinding;

import java.util.ArrayList;
import java.util.List;

public class DetailEstateActivity extends AppCompatActivity {

    private ActivityDetailEstateBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_estate);

        configureRecyclerView();

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
}
