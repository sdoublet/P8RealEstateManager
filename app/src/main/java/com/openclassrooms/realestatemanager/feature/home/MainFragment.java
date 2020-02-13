package com.openclassrooms.realestatemanager.feature.home;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.openclassrooms.realestatemanager.util.CheckTabletDevice;
import com.openclassrooms.realestatemanager.util.EstateValues;
import com.openclassrooms.realestatemanager.util.SharePreferencesHelper;
import com.openclassrooms.realestatemanager.util.Utils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private EstateViewModel estateViewModel;
    private Context context;

    private long AGENT_ID = SharePreferencesHelper.getInstance().getAgentId();

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
        EstateAgency estateAgency = new EstateAgency();
        estateAgency.setLogo(R.drawable.real_estate);
        binding.setAgency(estateAgency);

        context = this.getContext();
        onClickSimulator();
        onClickEstate();
        onClickMap();
        onClickAddProperty();
        this.configureViewModel();
        this.getAgent();
        this.getCurrentUser();
        this.getPicture();
        this.forTest();
        this.setUiTabMode();
        return view;

    }

    private void forTest() {
        estateViewModel.getAllPicturesFromEstate(2).observe(this, this::getNbPict);
    }

    private void getNbPict(List<Picture> pictures) {
        Log.e("testPict", String.valueOf(pictures.size()));
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
        this.estateViewModel.getUser(SharePreferencesHelper.getInstance().getAgentId()).observe(this, this::setAgencyTitle);
        Log.e("agent", String.valueOf(SharePreferencesHelper.getInstance().getAgentId()));
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
        EstateValues.getInstance().setLastEntry(estate.getEntryDate());
        this.estateViewModel.getPictureByIdAsc(estate.getEstateId()).observe(this, this::pictureFromLastEstate);
    }

    private void getPictureFromMostValueEstate(List<Estate> estates) {
        Estate estate = new Estate();
        for (int i = 0; i < estates.size(); i++) {
            estate = estates.get(i);

        }
        EstateValues.getInstance().setMostValue(estate.getPrice());
        this.estateViewModel.getPictureByIdAsc(estate.getEstateId()).observe(this, this::pictureFromMostValue);

    }

    private void getPictureLastSold(List<Estate> estateList) {
        Estate estate = new Estate();
        for (int i = 0; i < estateList.size(); i++) {
            estate = estateList.get(i);
        }
        EstateValues.getInstance().setLastSold(estate.getSoldDate());
        this.estateViewModel.getPictureByIdAsc(estate.getEstateId()).observe(this, this::pictureFromLastSold);

    }

    private void pictureFromLastEstate(Picture picture) {

        if (picture != null) {
            Uri uri = picture.getUri();
            String uriString = uri.toString();
            if (uriString.contains("content")) {
                Glide.with(this).load(uri).into(binding.imgLastEntry);

            } else {
                Glide.with(this).load(uri.getPath()).into(binding.imgLastEntry);
            }

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
            String urisString = uri.toString();
            if (urisString.contains("content")) {
                Glide.with(this).load(uri).into(binding.lastVisited);

            } else {
                Glide.with(this).load(uri.getPath()).into(binding.lastVisited);
            }

//            context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
//                //binding.lastVisited.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        } else {
            binding.lastVisited.setImageDrawable(getResources().getDrawable(R.drawable.manor));
        }

    }


    private void pictureFromLastSold(Picture picture) {
        if (picture != null) {
            Uri uri = picture.getUri();
            String uriString = uri.toString();
            if (uriString.contains("content")) {
                Glide.with(this).load(uri).into(binding.mostVisited);
            } else {
                Glide.with(this).load(uri.getPath()).into(binding.mostVisited);
            }
//            context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
//               // binding.mostVisited.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        } else {
            Glide.with(this).load(R.drawable.modern_house).into(binding.mostVisited);
            //binding.mostVisited.setImageDrawable(getResources().getDrawable(R.drawable.modern_house));
        }
    }

    //--------------------TABLET MODE---------------------------

    private void getAllEstates() {
        this.estateViewModel.getEstatePerAgent(AGENT_ID).observe(this, this::uiForAllEstates);
    }

    private void uiForAllEstates(List<Estate> estates) {
        binding.nbEstate.setText("Estates in your catalog: " + estates.size());
        for (int i = 0; i < estates.size(); i++) {
            int sold = 0;
            int nbFlat = 0;
            int nbHouse = 0;
            int nbDuplex = 0;
            int nbTriplex = 0;
            if (estates.get(i).isSold()) {
                sold++;
            }
            if (estates.get(i).getType().equals("Flat")) {
                nbFlat++;
            }
            if (estates.get(i).getType().equals("House")) {
                nbHouse++;
            }
             if (estates.get(i).getType().equals("Duplex")) {
                nbDuplex++;
            }
             if (estates.get(i).getType().equals("Triplex")) {
                nbTriplex++;
            }

            binding.nbSold.setText("Sold:       " + sold);
            binding.nbFlat.setText("Flats:      " + nbFlat);
            binding.nbHouse.setText("Houses: " + nbHouse);
            binding.nbDuplex.setText("Duplex:   " + nbDuplex);
            binding.nbTriplex.setText("Triplex:   " + nbTriplex);

        }
    }

    private void setUiTabMode() {
        if (CheckTabletDevice.isTablet(context)&&CheckTabletDevice.isLand(context)) {
            binding.dollarValue.setText("Dollar value: " + SharePreferencesHelper.getInstance().getDollar());
            binding.lastEntry.setText("last entry the: " + EstateValues.getInstance().getLastEntry());
            binding.lastSold.setText("Last sold the: " + EstateValues.getInstance().getLastSold());
            binding.mostValue.setText("Most value: " + Utils.stringFromatPrice(EstateValues.getInstance().getMostValue()) + " $");
            getAllEstates();

        }
    }
}




