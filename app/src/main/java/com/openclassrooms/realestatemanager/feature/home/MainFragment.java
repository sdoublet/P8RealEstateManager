package com.openclassrooms.realestatemanager.feature.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.EstateActivity;
import com.openclassrooms.realestatemanager.MainActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.EstateAgency;
import com.openclassrooms.realestatemanager.databinding.FragmentMainBinding;
import com.openclassrooms.realestatemanager.databinding.MyHandlers;
import com.openclassrooms.realestatemanager.feature.add_property.AddPropertyActivity;
import com.openclassrooms.realestatemanager.feature.credit_simulator.CreditSimulatorActivity;
import com.openclassrooms.realestatemanager.feature.map.MapActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private EstateAgency estateAgency;
    private FragmentMainBinding binding;
    private MyHandlers handlers;

    public static Fragment newInstance() {
        return new MainFragment();
    }

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        View view = binding.getRoot();
        estateAgency = new EstateAgency();
        estateAgency.setLogo(R.drawable.real_estate);
        estateAgency.setName("Agence de la fontaine");
        estateAgency.setAdress("19 rue du bois 39380 CHAMBLAY");
        binding.setAgency(estateAgency);

        onClickSimulator();
        onClickEstate();
        onClickMap();
        onClickAddProperty();
        return view;
    }


    private void onClickSimulator(){
        binding.buttonSimulator.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CreditSimulatorActivity.class);
            startActivity(intent);
        });
    }

    private void onClickEstate(){
        binding.buttonEstate.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EstateActivity.class);
            startActivity(intent);
        });
    }

    private void onClickMap(){
        binding.buttonMap.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MapActivity.class);
            startActivity(intent);
        });
    }
    private void onClickAddProperty(){
        binding.buttonAddProperty.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddPropertyActivity.class);
            startActivity(intent);
        });
    }

}
