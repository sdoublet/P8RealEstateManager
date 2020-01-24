package com.openclassrooms.realestatemanager.feature.home;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.EstateViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentMainBinding;
import com.openclassrooms.realestatemanager.feature.add_update_property.AddPropertyActivity;
import com.openclassrooms.realestatemanager.feature.credit_simulator.CreditSimulatorActivity;
import com.openclassrooms.realestatemanager.feature.map.MapsActivity;
import com.openclassrooms.realestatemanager.feature.show_property.EstateActivity;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.EstateAgency;
import com.openclassrooms.realestatemanager.models.Picture;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.util.AgentId;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private EstateAgency estateAgency;
    private FragmentMainBinding binding;
    EstateViewModel estateViewModel;
    Context context;
    private long AGENT_ID = AgentId.getInstance().getAgentId();

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
//        estateAgency.setName("Agence de la fontaine");
//        estateAgency.setAdress("19 rue du bois 39380 CHAMBLAY");
        binding.setAgency(estateAgency);
//        binding.imgLastEntry.setImageResource(R.drawable.country_house);
//        binding.lastVisited.setImageDrawable(getResources().getDrawable(R.drawable.modern_house));
//        binding.mostVisited.setImageDrawable(getResources().getDrawable(R.drawable.manor));
        context = this.getContext();
        onClickSimulator();
        onClickEstate();
        onClickMap();
        onClickAddProperty();
        this.configureViewModel();
        this.getAgent();
        this.getCurrentUser();
        this.getPicture();
        return view;
    }

    //---------------------
    // Configure ViewModel
    //---------------------
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
        // this.estateViewModel.intit(1);
    }


    private void onClickSimulator() {
        binding.buttonSimulator.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CreditSimulatorActivity.class);
            startActivity(intent);
        });
    }

    private void onClickEstate() {
        binding.buttonEstate.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EstateActivity.class);
            startActivity(intent);
        });
    }

    private void onClickMap() {
        binding.buttonMap.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MapsActivity.class);
            startActivity(intent);
        });
    }

    private void onClickAddProperty() {
        binding.buttonAddProperty.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddPropertyActivity.class);
            startActivity(intent);
        });
    }


    private void getAgent() {
        this.estateViewModel.getAllUsers().observe(this, this::setAgencyData);
    }

    private void setAgencyData(List<User> userList) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getAgentId() == AGENT_ID) {
                binding.agencyName.setText(userList.get(i).getAgency());
            }
        }
    }

    private void getCurrentUser() {
        this.estateViewModel.getUser(AgentId.getInstance().getAgentId()).observe(this, this::setAgencyTitle);
        Log.e("agent", String.valueOf(AgentId.getInstance().getAgentId()));
    }

    private void setAgencyTitle(User user) {
        binding.agencyName.setText(user.getAgency());
    }

    // TODO: 22/01/2020 change by agentId
    private void getPicture() {
        this.estateViewModel.getEstateByFilter(new SimpleSQLiteQuery("SELECT * FROM Estate  ORDER BY estateId DESC LIMIT 1")).observe(this, this::getLastPictureFromLastEstate);
        this.estateViewModel.getEstateByFilter(new SimpleSQLiteQuery("SELECT * FROM Estate  ORDER BY price DESC LIMIT 1 ")).observe(this, this::getPictureFromMostValueEstate);
        this.estateViewModel.getEstateByFilter(new SimpleSQLiteQuery("SELECT * FROM Estate  ORDER BY sold DESC LIMIT 1")).observe(this, this::getPictureLastSold);
    }

    private void getLastPictureFromLastEstate(List<Estate> estateList) {
        Estate estate = new Estate();
        for (int i = 0; i < estateList.size(); i++) {
            estate = estateList.get(i);
        }
        this.estateViewModel.getPictureByIdAsc(estate.getEstateId()).observe(this, this::pictureFromLastEstate);
    }

    private void getPictureFromMostValueEstate(List<Estate> estates) {
        Estate estate = new Estate();
        for (int i = 0; i < estates.size(); i++) {
            estate = estates.get(i);
            this.estateViewModel.getPictureByIdAsc(estate.getEstateId()).observe(this, this::pictureFromMostValue);
        }

    }

    private void getPictureLastSold(List<Estate> estateList) {
        Estate estate = new Estate();
        for (int i = 0; i < estateList.size(); i++) {
            estate = estateList.get(i);
            this.estateViewModel.getPictureByIdAsc(estate.getEstateId()).observe(this, this::pictureFromLastSold);
        }
    }

    private void pictureFromLastEstate(Picture picture) {

        if (picture != null) {
            Uri uri = picture.getUri();
            Glide.with(this).load(uri).into(binding.imgLastEntry);

//            Log.e("uriPic", String.valueOf(uri));
//            context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
//                //binding.imgLastEntry.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            } else {
            binding.imgLastEntry.setImageDrawable(getResources().getDrawable(R.drawable.country_house));
        }

    }


    private void pictureFromMostValue(Picture picture) {
        if (picture != null) {
            Uri uri = picture.getUri();
            Glide.with(this).load(uri).into(binding.lastVisited);

//            context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
//                //binding.lastVisited.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            binding.lastVisited.setImageDrawable(getResources().getDrawable(R.drawable.manor));
//        }

    }}


    private void pictureFromLastSold(Picture picture) {
        if (picture != null) {
            Uri uri = picture.getUri();
            Glide.with(this).load(uri).into(binding.mostVisited);

//            context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
//               // binding.mostVisited.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }else {
            Glide.with(this).load(R.drawable.modern_house).into(binding.mostVisited);
            //binding.mostVisited.setImageDrawable(getResources().getDrawable(R.drawable.modern_house));
        }
        }
    }




