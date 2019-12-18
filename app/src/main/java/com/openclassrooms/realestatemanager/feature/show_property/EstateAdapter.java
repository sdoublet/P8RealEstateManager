package com.openclassrooms.realestatemanager.feature.show_property;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.RowEstateBinding;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.Picture;

import java.util.List;

public class EstateAdapter extends RecyclerView.Adapter<EstateViewHolder> {

    private List<Estate> estateList;
    private List<Picture> pictureList;
    private Context context;


    public EstateAdapter(List<Estate> estateList, Context context) {
        this.estateList = estateList;
        this.context = context;
    }

    @NonNull
    @Override
    public EstateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowEstateBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_estate, parent, false);

        return new EstateViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull EstateViewHolder holder, int position) {
        Estate estate = estateList.get(position);
     //   Picture picture = pictureList.get(0);
        holder.rowEstateBinding.setEstate(estate);
        if (estate.isSold()) {
            holder.rowEstateBinding.soldView.setImageResource(R.drawable.sold_house);
            holder.rowEstateBinding.soldView.setVisibility(View.VISIBLE);

        } else {
            holder.rowEstateBinding.soldView.setVisibility(View.GONE);
        }

//        if (estate.getEstateId() == picture.getEstateId()) {
//            Uri uri = picture.getUri();
//            Glide.with(context).load(uri).into(holder.rowEstateBinding.imgRowEstate);
//        }
//

    }

//    public void setEstate(List<Estate> estates){
//        this.estateList = estates;
//        notifyDataSetChanged();
//    }

    public Estate getEstate(int position) {
        return this.estateList.get(position);
    }

    @Override
    public int getItemCount() {
        return estateList.size();
    }

    public void updateData(List<Estate> estates) {
        this.estateList = estates;
        this.notifyDataSetChanged();
    }

    public void updateDataWithPicture(List<Picture> picture) {
        this.pictureList = picture;
        this.notifyDataSetChanged();
    }


}
