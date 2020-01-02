package com.openclassrooms.realestatemanager.feature.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivitySettingBinding;
import com.openclassrooms.realestatemanager.util.MoneyPref;

public class SettingActivity extends AppCompatActivity {


    private ActivitySettingBinding binding;
    SharedPreferences sharedPreferences;
    boolean euro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        sharedPreferences = getSharedPreferences("euro", MODE_PRIVATE);
        euro = sharedPreferences.getBoolean("euro", false);
        binding.euro.setChecked(euro);
        dollarsEuro();

    }

    private void dollarsEuro() {
        if (binding.euro.isChecked()){binding.euro.setText("euros");
        MoneyPref.getInstance().setEuro(true);
        }else binding.euro.setText("dollars");

       binding.euro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked){
                   buttonView.setText("euros");
                   MoneyPref.getInstance().setEuro(true);
                   sharedPreferences.edit().putBoolean("euro", true).apply();
               }else{
                   buttonView.setText("dollars");
                   MoneyPref.getInstance().setEuro(false);
                   sharedPreferences.edit().putBoolean("euro", false).apply();
               }
           }
       });

    }
}
