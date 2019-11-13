package com.openclassrooms.realestatemanager.feature.credit_simulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityCreditSimulatorBinding;

import java.util.Locale;

public class CreditSimulatorActivity extends AppCompatActivity {


    private ActivityCreditSimulatorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_credit_simulator );

        binding.resultCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              double  p = Double.parseDouble(binding.priceEdit.getText().toString());
               String s = binding.tauxEdit.getText().toString();
               float t = Float.parseFloat(s);
               int d = Integer.parseInt(binding.durationEdit.getText().toString());
                double calcul =(calculResult(p, d, t));
                // result.setText(Double.toString(calcul));
                binding.resultEdit.setText(String.format(Locale.getDefault(), "%.2f $/month", calcul));
            }
        });
    }
    private double calculResult(double price, int duration, float taux){
        double ratePerMonth = (taux / 100.0) / 12.0;
        int totalMonth = duration * 12;

        double result =  (price * ratePerMonth) / (1 - Math.pow(1 + ratePerMonth, -totalMonth));

        return  ((long)(result * 100) / 100.0);
    }
}
