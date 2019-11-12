package com.openclassrooms.realestatemanager;

import android.os.Bundle;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.openclassrooms.realestatemanager.data.User;
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding;
import com.openclassrooms.realestatemanager.databinding.DrawerHeaderBinding;
import com.openclassrooms.realestatemanager.feature.home.MainFragment;


public class MainActivity extends AppCompatActivity {


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

        //for nav header
        binding.navView.addHeaderView(headerBinding.getRoot());
        User userModel = new User();
        userModel.setName("Seb");
        userModel.setPhoto(R.drawable.real_estate);
        userModel.setEmail("doubletsebastien@sfr.fr");
        headerBinding.setUser(userModel);

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
    private void configureDrawerLayout(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
                R.string.open_drawer, R.string.close_drawer);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

}
