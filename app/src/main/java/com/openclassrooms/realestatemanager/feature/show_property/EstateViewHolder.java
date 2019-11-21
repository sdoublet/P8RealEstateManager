package com.openclassrooms.realestatemanager.feature.show_property;


import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.databinding.RowEstateBinding;

public class EstateViewHolder extends RecyclerView.ViewHolder {

    public RowEstateBinding rowEstateBinding;

    public EstateViewHolder(RowEstateBinding itemBinding) {
        super(itemBinding.getRoot());
        this.rowEstateBinding = itemBinding;
    }


}
