package com.openclassrooms.realestatemanager.feature.show_property;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.github.guilhe.views.SeekBarRangedView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivitySearchEngineBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchEngineActivity extends AppCompatActivity {

    ActivitySearchEngineBinding binding;
    String queryString=  "";
    List<Object> args = new ArrayList<>();
    DatePickerDialog dpd;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_engine);
        this.setUi();
        this.datePickerDialog();
    }

    // Create query to send to EstateActivity
    private void createQuery(){
        queryString+="SELECT * FROM Estate ";

       int minPrice = (int) binding.progressPrice.getSelectedMinValue();
       int maxPrice = (int) binding.progressPrice.getSelectedMaxValue();
       queryString+= "WHERE price >= " + minPrice + " AND price <= " + maxPrice;

       int nbRoom = (int) binding.progressRoom.getSelectedMinValue();
       int maxNbRoom  = (int) binding.progressRoom.getSelectedMaxValue();
       queryString+= " AND nbRoom >= " + nbRoom + " AND nbRoom <= " +maxNbRoom;

       int nbBedroom = (int) binding.progressBedroom.getSelectedMinValue();
       int maxNbBedroom = (int) binding.progressBedroom.getSelectedMaxValue();
       queryString+= " AND bedroom >= " + nbBedroom + " AND bedroom <= " + maxNbBedroom;

       float surface = binding.progressSurface.getSelectedMinValue();
       float maxSurface = binding.progressSurface.getSelectedMaxValue();
       queryString+= " AND surface >= " + surface + " AND surface <= " + maxSurface;

       int landSurface = (int) binding.progressLandSurface.getSelectedMinValue();
       int maxLandSurface = (int) binding.progressLandSurface.getSelectedMaxValue();
       queryString+= " AND surfaceLand >= " + landSurface + " AND surfaceLand <= " + maxLandSurface;

       boolean school = binding.checkboxSchool.isChecked();
       boolean shop = binding.checkboxShop.isChecked();
       boolean park = binding.checkboxPark.isChecked();
       boolean hospital = binding.checkboxHospital.isChecked();
       boolean transport = binding.checkboxTransport.isChecked();
       boolean administration = binding.checkboxAdministration.isChecked();


        if (binding.cityFilter.getText().length()>2){
            String city = binding.cityFilter.getText().toString();
            queryString+= " AND city = '" + city + "'";
        }

        if (school){queryString+= " AND school = '1' ";}
        if (shop){queryString+=" AND shop = '1'";}
        if (park){queryString+= " AND park = '1'";}
        if (hospital){queryString += " AND hospital = '1'";}
        if (transport){queryString+= " AND transport ='1'";}
        if (administration){queryString += " AND administration = '1'";}
       Log.e("query", queryString);
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
                args.add(minValue);
                maxValue = binding.progressPrice.getSelectedMaxValue();
                args.add(maxValue);
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



        binding.buttonSearchEngine.setOnClickListener(v -> {
            createQuery();
            Intent intent = new Intent(getApplicationContext(), EstateActivity.class);
            intent.putExtra("query", queryString);
            Log.e("query", queryString);
            Log.e("values", String.valueOf(binding.progressPrice.getSelectedMinValue()));
            Log.e("values", "max value " + binding.progressPrice.getSelectedMaxValue());
            startActivity(intent);
        });



        ///---------------------TUTO AND TIPS FROM TIE------------------------------
        //https://android-arsenal.com/free
        //De Thiependa Seye à tout le monde:  09:24 AM
        //https://developer.android.com/training/permissions/requesting
        //De Thiependa Seye à tout le monde:  09:34 AM
        //https://developer.android.com/training/data-storage/shared/media#java
    }
    private void datePickerDialog(){
        binding.btnSinceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(SearchEngineActivity.this , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                        binding.btnSinceDate.setText("Since " +mDayOfMonth + "/" + (mMonth+1) + "/" + mYear);
                    }
                }, day, month, year);
                dpd.show();
            }
        });

        binding.btnToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(SearchEngineActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                        binding.btnToDate.setText("To " +  +mDayOfMonth + "/" + (mMonth+1) + "/" + mYear);
                    }
                }, day, month, year);
                dpd.show();
            }
        });
    }
}
