package com.openclassrooms.realestatemanager.feature.show_property;

import android.graphics.Bitmap;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.databinding.RowPhotoDetailBinding;


public class DetailViewHolder extends RecyclerView.ViewHolder {


    public RowPhotoDetailBinding binding;


    public DetailViewHolder(RowPhotoDetailBinding itemBinding) {
        super(itemBinding.getRoot());
        this.binding =  itemBinding;
    }



}
