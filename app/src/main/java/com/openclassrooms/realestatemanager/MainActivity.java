package com.openclassrooms.realestatemanager;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.openclassrooms.realestatemanager.feature.setting.SettingActivity;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding;
import com.openclassrooms.realestatemanager.databinding.DrawerHeaderBinding;
import com.openclassrooms.realestatemanager.feature.credit_simulator.CreditSimulatorActivity;
import com.openclassrooms.realestatemanager.feature.home.MainFragment;
import com.openclassrooms.realestatemanager.feature.show_property.DetailEstateActivity;
import com.openclassrooms.realestatemanager.feature.show_property.EstateActivity;
import com.openclassrooms.realestatemanager.feature.user_profile.ProfileActivity;
import com.openclassrooms.realestatemanager.util.Utils;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ConnectivityManager connectivityManager;

    private ActivityMainBinding binding;
    private DrawerHeaderBinding headerBinding;


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
        User userModel = new User();
        userModel.setName("Seb");
       // userModel.setPhoto(R.drawable.photo_seb);
        userModel.setEmail("doubletsebastien@sfr.fr");
        headerBinding.setUser(userModel);
        //for nav item selected
        binding.navView.setNavigationItemSelectedListener(this);

        //frag
        displayFragment(FRAGMENT_MAIN);
        //toolbar
        setSupportActionBar(binding.toolbar);
        // drawer
        configureDrawerLayout();
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
            case R.id.all_property:
                Intent intent2 = new Intent(this, EstateActivity.class);
                startActivity(intent2);
                break;
            case R.id.profile_menu:
                Intent intent3 = new Intent(this, ProfileActivity.class);
                startActivity(intent3);
                break;

        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
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
