package com.openclassrooms.realestatemanager.util;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityPopupBinding;

public class PopupActivity extends Dialog {


private CheckBox checkBoxSchool;
private CheckBox checkBoxShop;
private CheckBox checkBoxPark;
private CheckBox checkBoxHospital;
private CheckBox checkBoxTransport;
private CheckBox checkBoxAdministration;




    public PopupActivity(@NonNull Context context) {
        super(context, R.style.Theme_AppCompat);
        setContentView(R.layout.activity_popup);
        checkBoxSchool = findViewById(R.id.checkbox_school_popup);
        checkBoxShop = findViewById(R.id.checkbox_shop_popup);
        checkBoxPark = findViewById(R.id.checkbox_park_popup);
        checkBoxHospital = findViewById(R.id.checkbox_hospital_popup);
        checkBoxTransport = findViewById(R.id.checkbox_transport_popup);
        checkBoxAdministration = findViewById(R.id.checkbox_administration_popup);



    }


    public CheckBox getCheckBoxSchool() {
        return checkBoxSchool;
    }

    public void setCheckBoxSchool(CheckBox checkBoxSchool) {
        this.checkBoxSchool = checkBoxSchool;
    }

    public CheckBox getCheckBoxShop() {
        return checkBoxShop;
    }

    public void setCheckBoxShop(CheckBox checkBoxShop) {
        this.checkBoxShop = checkBoxShop;
    }

    public CheckBox getCheckBoxPark() {
        return checkBoxPark;
    }

    public void setCheckBoxPark(CheckBox checkBoxPark) {
        this.checkBoxPark = checkBoxPark;
    }

    public CheckBox getCheckBoxHospital() {
        return checkBoxHospital;
    }

    public void setCheckBoxHospital(CheckBox checkBoxHospital) {
        this.checkBoxHospital = checkBoxHospital;
    }

    public CheckBox getCheckBoxTransport() {
        return checkBoxTransport;
    }

    public void setCheckBoxTransport(CheckBox checkBoxTransport) {
        this.checkBoxTransport = checkBoxTransport;
    }

    public CheckBox getCheckBoxAdministration() {
        return checkBoxAdministration;
    }

    public void setCheckBoxAdministration(CheckBox checkBoxAdministration) {
        this.checkBoxAdministration = checkBoxAdministration;
    }





}
