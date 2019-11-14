package com.openclassrooms.realestatemanager.databinding;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.openclassrooms.realestatemanager.SecondActivity;

public class MyHandlers {



    public void onClickSimulator(View view){
        Context context = view.getContext();
        Intent intent = new Intent(context, SecondActivity.class);
        context.startActivity(intent);
    }
}
