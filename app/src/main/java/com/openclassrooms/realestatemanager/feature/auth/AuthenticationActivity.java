package com.openclassrooms.realestatemanager.feature.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.EstateViewModel;
import com.openclassrooms.realestatemanager.MainActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityAuthentificationBinding;
import com.openclassrooms.realestatemanager.feature.login.LoginActivity;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.util.SharePreferencesHelper;

import java.util.List;

public class AuthenticationActivity extends AppCompatActivity {

    ActivityAuthentificationBinding binding;
    private EstateViewModel estateViewModel;
    private SharedPreferences sharedPreferences;
    public static final String PREFS = "PREFS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentification);
        this.configureViewModel();
        this.createAccount();
        this.onClickLoginBtn();
    }

    private void createAccount(){
        binding.authNewUserBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
    }

    private void onClickLoginBtn(){
        binding.authBtn.setOnClickListener(v -> checkAccount());
    }

    private void checkAccount(){

        estateViewModel.getAllUsers().observe(this, this::login);
    }

    private void login(List<User> users){
        String name = binding.authName.getText().toString();
        String password = binding.authPassword.getText().toString();
        for (int i=0; i<users.size(); i++){
            if (users.get(i).getName().equals(name) && users.get(i).getPassword().equals(password)){
                Log.e("userPro", String.valueOf(users.get(i).getAgentId()));
                SharePreferencesHelper.getInstance().setAgentId(users.get(i).getAgentId());
                sharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
                sharedPreferences.edit().putLong(PREFS , users.get(i).getAgentId()).apply();
                Toast.makeText(this, "Welcome back " + users.get(i).getSurname() + " " + users.get(i).getName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this, "Your password is not available with this user name", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void configureViewModel(){
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
    }
}
// https://developer.android.com/training/camera/photobasics
