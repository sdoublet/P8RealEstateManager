package com.openclassrooms.realestatemanager.feature.credit_simulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityCreditSimulatorBinding;

public class CreditSimulatorActivity extends AppCompatActivity {


    private ActivityCreditSimulatorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_credit_simulator );

    }
}
