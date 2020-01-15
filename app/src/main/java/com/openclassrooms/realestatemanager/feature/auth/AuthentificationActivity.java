package com.openclassrooms.realestatemanager.feature.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityAuthentificationBinding;
import com.openclassrooms.realestatemanager.feature.login.LoginActivity;

public class AuthentificationActivity extends AppCompatActivity {

    ActivityAuthentificationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentification);
        this.createAccount();
    }

    private void createAccount(){
        binding.authNewUserBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
    }
}
