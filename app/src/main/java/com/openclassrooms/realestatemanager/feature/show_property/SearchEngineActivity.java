package com.openclassrooms.realestatemanager.feature.show_property;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.github.guilhe.views.SeekBarRangedView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivitySearchEngineBinding;

public class SearchEngineActivity extends AppCompatActivity {

    ActivitySearchEngineBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_engine);
        this.setUi();
    }

    //UI
    private void setUi() {



        binding.progressPrice.setOnSeekBarRangedChangeListener(new SeekBarRangedView.OnSeekBarRangedChangeListener() {
            @Override
            public void onChanged(SeekBarRangedView view, float minValue, float maxValue) {

            }

            @Override
            public void onChanging(SeekBarRangedView view, float minValue, float maxValue) {
                minValue = binding.progressPrice.getSelectedMinValue();
                maxValue = binding.progressPrice.getSelectedMaxValue();
                binding.editPriceSearchEngine.setText(String.valueOf((int) minValue));
                binding.editPriceSearchEngineMax.setText(String.valueOf((int) maxValue));
            }
        });

        binding.progressRoom.setOnSeekBarRangedChangeListener(new SeekBarRangedView.OnSeekBarRangedChangeListener() {
            @Override
            public void onChanged(SeekBarRangedView view, float minValue, float maxValue) {

            }

            @Override
            public void onChanging(SeekBarRangedView view, float minValue, float maxValue) {
                minValue = binding.progressRoom.getSelectedMinValue();
                maxValue = binding.progressRoom.getSelectedMaxValue();
                binding.editNbRoomSearchEngine.setText(String.valueOf((int) minValue));
                binding.editNbRoomSearchEngineMax.setText(String.valueOf((int) maxValue));
            }
        });

        binding.progressBedroom.setOnSeekBarRangedChangeListener(new SeekBarRangedView.OnSeekBarRangedChangeListener() {
            @Override
            public void onChanged(SeekBarRangedView view, float minValue, float maxValue) {

            }

            @Override
            public void onChanging(SeekBarRangedView view, float minValue, float maxValue) {
                minValue = binding.progressBedroom.getSelectedMinValue();
                maxValue = binding.progressBedroom.getSelectedMaxValue();
                binding.editNbBedroomSearchEngine.setText(String.valueOf((int) minValue));
                binding.editNbBedroomSearchEngineMax.setText(String.valueOf((int) maxValue));
            }
        });

        binding.progressSurface.setOnSeekBarRangedChangeListener(new SeekBarRangedView.OnSeekBarRangedChangeListener() {
            @Override
            public void onChanged(SeekBarRangedView view, float minValue, float maxValue) {

            }

            @Override
            public void onChanging(SeekBarRangedView view, float minValue, float maxValue) {
                minValue = binding.progressSurface.getSelectedMinValue();
                maxValue = binding.progressSurface.getSelectedMaxValue();
                binding.editSurfaceSearchEngine.setText(String.valueOf((int)minValue));
                binding.editSurfaceSearchEngineMax.setText(String.valueOf((int)maxValue));
            }
        });

        binding.progressLandSurface.setOnSeekBarRangedChangeListener(new SeekBarRangedView.OnSeekBarRangedChangeListener() {
            @Override
            public void onChanged(SeekBarRangedView view, float minValue, float maxValue) {

            }

            @Override
            public void onChanging(SeekBarRangedView view, float minValue, float maxValue) {
            minValue = binding.progressLandSurface.getSelectedMinValue();
            maxValue = binding.progressLandSurface.getSelectedMaxValue();
            binding.editLandSurfaceSearchEngine.setText(String.valueOf((int)minValue));
            binding.editLandSurfaceSearchEngineMax.setText(String.valueOf((int)maxValue));
            }
        });

        binding.buttonSearchEngine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EstateActivity.class);
                //intent.putExtra(mes filtres)
                startActivity(intent);
            }
        });

        ///---------------------TUTO AND TIPS FROM TIE------------------------------
        //https://android-arsenal.com/free
        //De Thiependa Seye à tout le monde:  09:24 AM
        //https://developer.android.com/training/permissions/requesting
        //De Thiependa Seye à tout le monde:  09:34 AM
        //https://developer.android.com/training/data-storage/shared/media#java
    }
}
