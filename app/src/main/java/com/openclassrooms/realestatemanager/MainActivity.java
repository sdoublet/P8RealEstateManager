package com.openclassrooms.realestatemanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding;
import com.openclassrooms.realestatemanager.databinding.DrawerHeaderBinding;
import com.openclassrooms.realestatemanager.data.User;


public class MainActivity extends AppCompatActivity {

    private TextView textViewMain;
    private TextView textViewQuantity;

private ActivityMainBinding binding;
private DrawerHeaderBinding headerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
      binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    headerBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.drawer_header,
            binding.navView, false);

    binding.navView.addHeaderView(headerBinding.getRoot());
    User userModel = new User();
    userModel.setName("Seb");
    userModel.setPhoto(R.drawable.real_estate);

    userModel.setEmail("doubletsebastien@sfr.fr");
    headerBinding.setUser(userModel);









//        this.textViewMain = findViewById(R.id.activity_main_activity_text_view_main); //change id to main
//        this.textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity);

//        this.configureTextViewMain();
//        this.configureTextViewQuantity();
    }

//    private void configureTextViewMain(){
//        this.textViewMain.setTextSize(15);
//        this.textViewMain.setText("Le premier bien immobilier enregistré vaut ");
//    }
//
//    private void configureTextViewQuantity(){
//        int quantity = Utils.convertDollarToEuro(100);
//        this.textViewQuantity.setTextSize(20);
//        this.textViewQuantity.setText("" + quantity);// add quotes
//    }
}
