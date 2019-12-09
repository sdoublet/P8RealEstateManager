package com.openclassrooms.realestatemanager.feature.show_property;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.dao.EstateDao;
import com.openclassrooms.realestatemanager.databinding.ActivityEstateBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.util.Divider;
import com.openclassrooms.realestatemanager.util.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

public class EstateActivity extends AppCompatActivity implements View.OnClickListener {


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
        configureRecyclerView();
        configureViewModel();
        getAllEstate();
        onClickrecyclerView();



    }

    //---------------------
    // Configure ViewModel
    //---------------------
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
        this.estateViewModel.intit(AGENT_ID);
    }


    //------------UI--------------

    private void displayEstateWithFilterDesc(int liveData) {
        switch (liveData) {
            case 1:
                this.estateViewModel.displayEstateByPrice().observe(this, this::updateEstateList);
                break;
            case 2:
                this.estateViewModel.displayEstateByNbRoomDesc().observe(this, this::updateEstateList);
                break;
            case 3:
                this.estateViewModel.displayEstateBySurfaceDesc().observe(this, this::updateEstateList);
                break;
            case 4:
                this.estateViewModel.displayEstatebytypeDesc().observe(this, this::updateEstateList);
        }

    }

    private void displayEstateWithFilterAsc(int liveData) {
        switch (liveData) {
            case 1:
                this.estateViewModel.displayEstateByPriceAsc().observe(this, this::updateEstateList);
                break;
            case 4:
                this.estateViewModel.displayEstateBytypeAsc().observe(this, this::updateEstateList);
                break;
            case 2:
                this.estateViewModel.displayEstateByNbRoomAsc().observe(this, this::updateEstateList);
                break;
            case 3:
                this.estateViewModel.displayEstateBySurfaceAsc().observe(this, this::updateEstateList);
        }
    }

    // onClick switch filter
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter_price:
                ifClicked(1, binding.dropPrice);
                break;
            case R.id.filter_room:
                ifClicked(2, binding.dropRoom);
                break;
            case R.id.filter_surface:
                ifClicked(3, binding.dropSurface);
                break;
            case R.id.filter_type:
                ifClicked(4, binding.dropType);
                break;
        }

    }

    private void ifClicked(int liveData, ImageView view) {
        if (!isClicked) {
            displayEstateWithFilterDesc(liveData);
            isClicked = true;
            view.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_drop_up));
        } else {
            displayEstateWithFilterAsc(liveData);
            isClicked = false;
            view.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_drop_down));
        }
    }

    private void getAllEstate() {
        this.estateViewModel.getAllEstates().observe(this, this::updateEstateList);
        Log.e("est", "done");
    }


    private void updateEstateList(List<Estate> estates) {

        this.adapter.updateData(estates);
    }


    //---------------
    //RECYCLER VIEW
    //---------------
    private void configureRecyclerView() {
        List<Estate> estateList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new EstateAdapter(estateList, this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new Divider(this, LinearLayout.VERTICAL));
        binding.recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void onClickrecyclerView(){
        ItemClickSupport.addTo(binding.recyclerView, R.layout.row_estate)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Estate estate = adapter.getEstate(position);
                    Intent intent = new Intent(getApplicationContext(), DetailEstateActivity.class);
                    intent.putExtra("estate_id", estate.getEstateId());
                    startActivity(intent);
                });
    }

    //----------------
    // POPULATE DATA
    //----------------


    public void populateData() {
        List<Estate> estateList = new ArrayList<>();


        estateList.add(new Estate("House", 145000, 245, 120, 8, 3, 1, "Maison de plein pied", "wood",
                "19 rue du bois", 39380, "Chamblay", false, "22/08/2019", null, AGENT_ID, 4.5665, 1.2554, true,
                true, false, false, false, true));


        EstateAdapter estateAdapter = new EstateAdapter(estateList, this);
        binding.recyclerView.setAdapter(estateAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }


}
