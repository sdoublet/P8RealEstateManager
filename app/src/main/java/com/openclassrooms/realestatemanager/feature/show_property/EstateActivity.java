package com.openclassrooms.realestatemanager.feature.show_property;

import android.Manifest;
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
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.openclassrooms.realestatemanager.EstateViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.dao.EstateDao;
import com.openclassrooms.realestatemanager.databinding.ActivityEstateBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.Picture;
import com.openclassrooms.realestatemanager.util.Divider;
import com.openclassrooms.realestatemanager.util.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class EstateActivity extends AppCompatActivity implements View.OnClickListener {


    private ActivityEstateBinding binding;

    // FOR DATA
    private EstateViewModel estateViewModel;
    private List<Estate> estateList;
    private List<Long> estateId;
    private EstateAdapter adapter;
    EstateDao estateDao;
    private boolean isClicked = false;
    private static long AGENT_ID = 1;
    List<Picture> pictureIdd = new ArrayList<>();
    public static final String perms = Manifest.permission.READ_EXTERNAL_STORAGE;
    private String queryy;
    private String queryHouse;
    private String queryFlat;
    private String queryCommercial;
    private String queryDuplex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_estate);

        perm();
        configureRecyclerView();
        configureViewModel();
        getAllPictures();
        onClickrecyclerView();


        queryByIntent();


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

    // show estate by intent since other activity
    private void queryByIntent() {
        Intent intent = getIntent();
        queryy = intent.getStringExtra("query");
        queryHouse = intent.getStringExtra("house");
        queryFlat = intent.getStringExtra("flat");
        queryCommercial = intent.getStringExtra("commercial");
        queryDuplex = intent.getStringExtra("duplex");
        if (queryy != null) {
            this.estateViewModel.getEstateByFilter(new SimpleSQLiteQuery(queryy)).observe(this, this::updateEstateList);
        }else if (queryHouse!=null){
            this.estateViewModel.getEstateByFilter(new SimpleSQLiteQuery(queryHouse)).observe(this, this::updateEstateList);
        }else if (queryFlat!=null) {
            this.estateViewModel.getEstateByFilter(new SimpleSQLiteQuery(queryFlat)).observe(this, this::updateEstateList);
        }else if (queryCommercial!=null){
            this.estateViewModel.getEstateByFilter(new SimpleSQLiteQuery(queryCommercial)).observe(this, this::updateEstateList);
        }else if (queryDuplex!=null){
            this.estateViewModel.getEstateByFilter(new SimpleSQLiteQuery(queryDuplex)).observe(this, this::updateEstateList);
        } else {
            getAllEstate();
        }
    }


    private void displayEstateWithFilterDesc(int liveData) {
        switch (liveData) {
            case 1:
                this.estateViewModel.getEstateByFilter(new SimpleSQLiteQuery("SELECT * FROM Estate ORDER BY price DESC")).observe(this, this::updateEstateList);
                break;
            case 2:
                this.estateViewModel.getEstateByFilter(new SimpleSQLiteQuery("SELECT * FROM Estate ORDER BY nbRoom DESC")).observe(this, this::updateEstateList);
                break;
            case 3:
                this.estateViewModel.getEstateByFilter(new SimpleSQLiteQuery("SELECT * FROM Estate ORDER BY surface DESC")).observe(this, this::updateEstateList);
                break;
            case 4:
                this.estateViewModel.getEstateByFilter(new SimpleSQLiteQuery("SELECT * FROM Estate ORDER BY type DESC")).observe(this, this::updateEstateList);
                break;
            case 5:
                this.estateViewModel.displaySoldEstateDesc().observe(this, this::updateEstateList);
                break;
        }

    }

    private void displayEstateWithFilterAsc(int liveData) {
        switch (liveData) {
            case 1:

                this.estateViewModel.getEstateByFilter(new SimpleSQLiteQuery("SELECT * FROM Estate ORDER BY price ASC")).observe(this, this::updateEstateList);
                break;
            case 4:

                this.estateViewModel.getEstateByFilter(new SimpleSQLiteQuery("SELECT * FROM Estate ORDER BY type ASC")).observe(this, this::updateEstateList);

                break;
            case 2:

                this.estateViewModel.getEstateByFilter(new SimpleSQLiteQuery("SELECT * FROM Estate ORDER BY nbroom ASC")).observe(this, this::updateEstateList);


                break;
            case 3:

                this.estateViewModel.getEstateByFilter(new SimpleSQLiteQuery("SELECT * FROM Estate ORDER BY surface ASC")).observe(this, this::updateEstateList);

                break;
            case 5:
                this.estateViewModel.displaySoldEstateAsc().observe(this, this::updateEstateList);
                break;
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
            case R.id.filter_sold:
                ifClicked(5, binding.dropSold);
                break;
            case R.id.filter_more:
                Intent intent = new Intent(getApplicationContext(), SearchEngineActivity.class);
                startActivity(intent);
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
        this.estateViewModel.getAllEstates().observe(this, this::allEstateForPicture);
        Log.e("est", "done");

    }


    private void updateEstateList(List<Estate> estates) {

        this.adapter.updateData(estates);
    }

    private void allEstateForPicture(List<Estate> estates) {
        for (int i = 0; i < estates.size(); i++) {
            long estateId = estates.get(i).getEstateId();
            for (int j = 0; j < pictureIdd.size(); j++) {
                if (pictureIdd.get(j).getEstateId() == estateId) {
                    Log.e("same", String.valueOf(pictureIdd.size()));
                    Log.e("same", estateId + "= " + pictureIdd.get(j));
                    this.estateViewModel.getPictureByIdAsc(estateId).observe(this, this::updateUiWithPicture);
                } else {
                    Log.e("same", "pas trouvé");
                }
            }

        }
    }

    private void updateUiWithPicture(Picture picture) {
        List<Picture> pictureList = new ArrayList<>();
        for (int i = 0; i < pictureIdd.size(); i++) {
            pictureList.add(picture);

        }

        Log.e("pic", picture.getEstateId() + " " + picture.getPhotoId());
        Log.e("pic", String.valueOf(pictureList.size()));
        adapter.updateDataWithPicture(pictureList);
    }

    private void getAllPictures() {
        this.estateViewModel.getAllPictures().observe(this, this::takePictureId);
    }

    private List<Picture> takePictureId(List<Picture> pictures) {
        // pour chaque estateId, si il y a des photos recupere la premiere

        for (int i = 0; i < pictures.size(); i++) {
            Log.e("pictureId", String.valueOf(pictures.get(i).getEstateId()));
            Log.e("pictureId", String.valueOf(pictures.get(i).getPhotoId()));
            long idd = pictures.get(i).getEstateId();
            pictureIdd.add(pictures.get(i));


        }
        return pictureIdd;


    }


    // je dois recupere la totalité des images
    // recuperer la première par estateId
    //L'afficher dans la recyclerview

    //---------------
    //RECYCLER VIEW
    //---------------
    private void configureRecyclerView() {
        List<Estate> estateList = new ArrayList<>();
        // List<Picture> pictureList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new EstateAdapter(estateList, pictureIdd, this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new Divider(this, LinearLayout.VERTICAL));
        binding.recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void onClickrecyclerView() {
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

    //----------------TEST FOR PICTURE---------------------
    // je dois recuperer la photo prinicpale
    // je dois donc faire une requete de la photo avec l'id de l'estate
    // je dois faire un get mainPhoto depuis le ViewModel
    //donc je dois modifier mon repository
    // et mon dao
    // donc je dois avoir une ref de la photo principale
    //donc mon utilisateur doit pouvoir definir la photo principale
    //peut etre en agrandissant les photos ou sinon par defaut la mainPhoto ou les 2

//https://android-arsenal.com/free
//https://github.com/stfalcon-studio/StfalconPriceRangeBar-android?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=7325

    private void perm() {
        if (EasyPermissions.hasPermissions(getApplicationContext(), perms)) {
            Log.e("tag", "ok");
        } else {
            EasyPermissions.requestPermissions(this, "ss", 555, perms);
        }
    }
}
