package com.openclassrooms.realestatemanager.feature.show_property;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.dao.EstateDao;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.databinding.ActivityEstateBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.util.Divider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EstateActivity extends AppCompatActivity {


    private ActivityEstateBinding binding;

    // FOR DATA
    private EstateViewModel estateViewModel;
    private List<Estate> estateList;
    private EstateAdapter adapter;
    EstateDao estateDao;
    private boolean isClicked = false;
    private static long AGENT_ID = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_estate);
   //     getAllEstate();
        configureRecyclerView();
        configureViewModel();
        getAllEstate();
        onClickFilter();

      //  populateData();
        try {

            Log.e("db", String.valueOf(estateViewModel.getEstateFromId(1).getValue().getAddress()));

        }catch (Exception e){
            Log.e("db", e.getMessage());
        }

    }

    //---------------------
    // Configure ViewModel
    //---------------------
    private void configureViewModel(){
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
        this.estateViewModel.intit(AGENT_ID);
    }


    //------------UI--------------

    private void displayEstateWithFilter(){

                this.estateViewModel.displayEstateByPrice().observe(this, this::updateEstateList);
    }

    private void displayEstateWithFilterAsc(){
        estateViewModel.displayEstateByPriceAsc().observe(this, this::updateEstateList);
    }
    private void onClickFilter(){
        binding.filterPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClicked){
                    displayEstateWithFilter();
                    isClicked = true;
                    binding.drop.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_drop_up));
                }else {
                    displayEstateWithFilterAsc();
                    isClicked = false;
                    binding.drop.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_drop_down));
                }

            }
        });
    }

    private void getAllEstate(){
        this.estateViewModel.getAllEstates().observe(this, this::updateEstateList);
        Log.e("est", "done");
    }



    private void updateEstateList(List<Estate> estates){

        this.adapter.updateData(estates);
    }



    //---------------
    //RECYCLER VIEW
    //---------------
    private void configureRecyclerView(){
        List<Estate> estateList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new EstateAdapter(estateList, this );
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new Divider(this, LinearLayout.VERTICAL));
        binding.recyclerView.setLayoutManager(linearLayoutManager);

    }

        //----------------
        // POPULATE DATA
        //----------------


    public void populateData() {
        List<Estate> estateList = new ArrayList<>();


       estateList.add(new Estate( "House", 145000, 245, 120, 8, 3, 1, "Maison de plein pied", "wood",
               "19 rue du bois", 39380, "Chamblay", false, "22/08/2019", null, AGENT_ID, 4.5665, 1.2554, true,
               true, false, false, false, true));


       EstateAdapter estateAdapter = new EstateAdapter(estateList, this);
       binding.recyclerView.setAdapter(estateAdapter);
       binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }


}
