package com.openclassrooms.realestatemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.navigation.NavigationView;
import com.openclassrooms.realestatemanager.api.apiUsd.ApiUsd;
import com.openclassrooms.realestatemanager.feature.auth.AuthenticationActivity;
import com.openclassrooms.realestatemanager.api.get_current_dollar_value.DollarStream;
import com.openclassrooms.realestatemanager.feature.setting.SettingActivity;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding;
import com.openclassrooms.realestatemanager.databinding.DrawerHeaderBinding;
import com.openclassrooms.realestatemanager.feature.credit_simulator.CreditSimulatorActivity;
import com.openclassrooms.realestatemanager.feature.home.MainFragment;
import com.openclassrooms.realestatemanager.feature.show_property.EstateActivity;
import com.openclassrooms.realestatemanager.feature.user_profile.ProfileActivity;
import com.openclassrooms.realestatemanager.util.SharePreferencesHelper;
import com.openclassrooms.realestatemanager.util.Utils;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ConnectivityManager connectivityManager;

    private ActivityMainBinding binding;
    private DrawerHeaderBinding headerBinding;
    private SharedPreferences sharedPreferences;
    private Disposable disposable;
    private EstateViewModel estateViewModel;
    private double euroValue=0;


    //Fragment identifier
    public static final int FRAGMENT_MAIN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        headerBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.drawer_header,
                binding.navView, false);


        // for connectivity
        Utils.onCheckNetworkStatus(this, binding.drawerLayout);
        Utils.checkNetworkType(this, binding.drawerLayout);

        //for nav header
        binding.navView.addHeaderView(headerBinding.getRoot());

        //for nav item selected
        binding.navView.setNavigationItemSelectedListener(this);

        // for dollar api
        this.executeHttpRequest();

        //frag
       this.displayFragment(FRAGMENT_MAIN);
        //toolbar
       this. setSupportActionBar(binding.toolbar);
       this.configureViewmodel();
        // drawer
        this.configureDrawerLayout();


        //prefs
        this.getPreferencesMoney();
        this.getPreferencesUser();



        this.getDataSinceViewmodel();


    }

    private void configureViewmodel(){
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
    }

    private void getDataSinceViewmodel(){
            estateViewModel.getUser(SharePreferencesHelper.getInstance().getAgentId()).observe(this, this::setHeaderData);
    }

    private void setHeaderData(User user){
            headerBinding.headerUsername.setText(user.getName());
            headerBinding.headerEmail.setText(user.getEmail());

    }

    //Display fragment
    private void displayFragment(int fragmentidentifier) {
        Fragment fragment = new Fragment();
        switch (fragmentidentifier) {
            case MainActivity.FRAGMENT_MAIN:
                fragment = MainFragment.newInstance();
                break;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_view, fragment);
        fragmentTransaction.commit();
    }

    //configure drawer layout
    private void configureDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
                R.string.open_drawer, R.string.close_drawer);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.credit_calculator_menu:
                Intent intent = new Intent(this, CreditSimulatorActivity.class);
                startActivity(intent);
                break;
            case R.id.settings_menu:
                Intent intent1 = new Intent(this, SettingActivity.class);
                startActivity(intent1);
                break;
            case R.id.log_out:
                Intent intent8 = new Intent(this, AuthenticationActivity.class);
                startActivity(intent8);
                break;
            case R.id.all_property:
                Intent intent2 = new Intent(this, EstateActivity.class);
                startActivity(intent2);
                break;
            case R.id.profile_menu:
                Intent intent3 = new Intent(this, ProfileActivity.class);
                startActivity(intent3);
                break;

            case R.id.house_menu:
                Intent intent4 = new Intent(this, EstateActivity.class);
                intent4.putExtra("house", "SELECT * FROM Estate WHERE type = 'House'");
                startActivity(intent4);
                break;
            case R.id.appartement_menu:
                Intent intent5 = new Intent(this, EstateActivity.class);
                intent5.putExtra("flat", "SELECT * FROM Estate WHERE type = 'Flat'");
                startActivity(intent5);
                break;
            case R.id.commercial_menu:
                Intent intent6 = new Intent(this, EstateActivity.class);
                intent6.putExtra("commercial", "SELECT * FROM Estate WHERE type = 'Commercial'");
                startActivity(intent6);
                break;
            case R.id.duplex_menu:
                Intent intent7 = new Intent(this, EstateActivity.class);
                intent7.putExtra("duplex", "SELECT * FROM Estate WHERE type = 'Duplex' OR type = 'Triplex'");
                startActivity(intent7);

        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // get preferences
    private void getPreferencesMoney(){
        sharedPreferences = getSharedPreferences("euro", MODE_PRIVATE);
        boolean euro = sharedPreferences.getBoolean("euro", false);
        if (euro){
            SharePreferencesHelper.getInstance().setEuro(true);
        }else {
            SharePreferencesHelper.getInstance().setEuro(false);
        }
    }
    private void getPreferencesUser(){
        sharedPreferences = getSharedPreferences("PREFS", MODE_PRIVATE);
        SharePreferencesHelper.getInstance().setAgentId(sharedPreferences.getLong("PREFS", 1));
    }





    // get current euro value

    private void getDollarsValue(ApiUsd usd){
        if (usd.getRates().getEUR()!=null){
            euroValue = usd.getRates().getEUR();
            Log.e("dollar", String.valueOf(euroValue));
            SharePreferencesHelper.getInstance().setDollar(euroValue);
        }else
        {
            SharePreferencesHelper.getInstance().setDollar(0.90);
            Log.e("dollar", "does'nt work");
        }

    }

    public  void executeHttpRequest(){
        this.disposable = DollarStream.streamFetchApiUsd().subscribeWith(new DisposableObserver<ApiUsd>() {
            @Override
            public void onNext(ApiUsd usd) {
                getDollarsValue(usd);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("tag", e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }

    //Back pressed drawer
    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();

        }
    }
}
