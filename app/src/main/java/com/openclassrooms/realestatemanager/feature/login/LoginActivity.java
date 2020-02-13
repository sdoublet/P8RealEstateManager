package com.openclassrooms.realestatemanager.feature.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.EstateViewModel;
import com.openclassrooms.realestatemanager.MainActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityLoginBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.util.SharePreferencesHelper;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private User user;
    private EstateViewModel estateViewModel;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        this.configureViewModel();
        this.onClickLoginBtn();
        this.getUserForFirstLogin();
    }

    private void alertDialog(){
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_Dialog);
        builder.setTitle("First visit")
                .setMessage("Welcome and thanks to choose RealEstateManager app for your business. \nTo use it, you must identify yourself for the first time. \nHave a good experience")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("hello", "hello");
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(alertDialog.BUTTON_NEUTRAL).setTextColor(Color.WHITE);
        alertDialog.getButton(alertDialog.BUTTON_NEUTRAL).setGravity(Gravity.CENTER_HORIZONTAL);


    }

    private void onClickLoginBtn() {
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUser();
                //create new user in db
                insertNewUser();
                getAllUsers();



            }
        });
    }

    private void setUser() {
        user = new User();
        user.setName(binding.loginName.getText().toString());
        user.setEmail(binding.loginEmail.getText().toString());
        user.setSurname(binding.loginSurname.getText().toString());
        user.setPassword(binding.loginPassword.getText().toString());
        user.setAgency(binding.loginAgency.getText().toString());

    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
    }

    private void insertNewUser() {
        if (user.getName() != null && user.getEmail() != null && !user.getName().equals("") && !user.getEmail().equals("")
                && user.getSurname() != null && !user.getSurname().equals("") && user.getAgency() != null && !user.getAgency().equals("")
                && user.getPassword() != null && !user.getPassword().equals("")) {
            estateViewModel.insertNewUser(user);
            SharePreferencesHelper.getInstance().setAgentId(user.getAgentId());
            Toast.makeText(this, "Welcome " + user.getSurname(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void getAllUsers() {
        this.estateViewModel.getAllUsers().observe(this, this::showAllUsers);
    }

    private void getUserForFirstLogin(){
        this.estateViewModel.getAllUsers().observe(this, this::firstLogin);

    }

    private void firstLogin(List<User> users){
        if (users.size()<1){
            this.alertDialog();
        }
    }

    private void showAllUsers(List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            Log.e("userId", String.valueOf(users.get(i).getAgentId()) + " " + users.get(i).getName() + " " + users.get(i).getEmail() + users.get(i).getPassword());
            if (users.get(i).getName() == null || users.get(i).getName().equals("") || users.get(i).getEmail() == null || users.get(i).getEmail().equals("")) {
                Log.e("userIdNull", String.valueOf(users.get(i).getAgentId()) + users.get(i).getPassword());
               // estateViewModel.deleteUser(users.get(i).getAgentId());
                Toast.makeText(this, "You forget a field", Toast.LENGTH_LONG).show();

            }
        }
    }


}
