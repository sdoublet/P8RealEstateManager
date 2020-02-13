package com.openclassrooms.realestatemanager.feature.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivitySettingBinding;
import com.openclassrooms.realestatemanager.util.SharePreferencesHelper;

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
        String euro = SharePreferencesHelper.getInstance().getDollar() + " €";
        binding.euroValue.setText(euro);
        dollarsEuro();

    }

    private void dollarsEuro() {
        if (binding.euro.isChecked()) {
            binding.euro.setText("euros");
            binding.euro.setTextColor(getResources().getColor(R.color.colorAccent));
            binding.euro.setTextSize(20);
            SharePreferencesHelper.getInstance().setEuro(true);
        } else {
            binding.euro.setText("dollars");
            binding.euro.setTextColor(getResources().getColor(R.color.colorAccent));
            binding.euro.setTextSize(20);
        }

        binding.euro.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                buttonView.setText("euros");
                SharePreferencesHelper.getInstance().setEuro(true);
                sharedPreferences.edit().putBoolean("euro", true).apply();
            } else {
                buttonView.setText("dollars");
                SharePreferencesHelper.getInstance().setEuro(false);
                sharedPreferences.edit().putBoolean("euro", false).apply();
            }
        });

    }
}
