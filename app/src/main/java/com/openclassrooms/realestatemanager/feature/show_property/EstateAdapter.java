package com.openclassrooms.realestatemanager.feature.show_property;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.RowEstateBinding;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.Picture;
import com.openclassrooms.realestatemanager.util.MoneyPref;
import com.openclassrooms.realestatemanager.util.Utils;

import java.io.IOException;
import java.util.List;

public class EstateAdapter extends RecyclerView.Adapter<EstateViewHolder> {

    private List<Estate> estateList;
    private List<Picture> pictureList;
    private Context context;


    public EstateAdapter(List<Estate> estateList, Context context) {
        this.estateList = estateList;
        this.context = context;
    }

    public EstateAdapter(List<Estate> estateList, List<Picture> pictureList, Context context) {
        this.estateList = estateList;
        this.pictureList = pictureList;
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
        holder.rowEstateBinding.setEstate(estate);
//        Uri uri;
//        for (int i = 0; i < pictureList.size(); i++) {
//            Picture picture = pictureList.get(i);
//            if (picture.getEstateId() == estate.getEstateId()) {
//                 uri = picture.getUri();
//                Log.e("uri", uri + " " + picture.getEstateId());
//                //holder.rowEstateBinding.imgRowEstate.setImageResource(R.drawable.country_house);
//                try {
//                    // context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
//                    holder.rowEstateBinding.imgRowEstate.setImageBitmap(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    holder.rowEstateBinding.imgRowEstate.setVisibility(View.VISIBLE);
//                    //  Glide.with(context).load(R.drawable.country_house).into(holder.rowEstateBinding.imgRowEstate);
//                    Log.e("tag", "yes" + estate.getEstateId());
//                    Log.e("tag", String.valueOf(picture.getEstateId()));
//                }
//            } else {
//                Log.e("tag", "no");
//                holder.rowEstateBinding.imgRowEstate.setVisibility(View.INVISIBLE);
//            }
//        }
           if (estate.getEstateId()%2==0){
               holder.rowEstateBinding.imgRowEstate.setImageResource(R.drawable.country_house);
           }else {
               holder.rowEstateBinding.imgRowEstate.setImageResource(R.drawable.manor);
           }


        if (estate.isSold()) {
            holder.rowEstateBinding.soldView.setImageResource(R.drawable.sold_house);
            holder.rowEstateBinding.soldView.setVisibility(View.VISIBLE);

        } else {
            holder.rowEstateBinding.soldView.setVisibility(View.INVISIBLE);
        }


//        if (estate.getEstateId() == picture.getEstateId()) {
//            Uri uri = picture.getUri();
//            Glide.with(context).load(uri).into(holder.rowEstateBinding.imgRowEstate);
//        }
//
        if (!MoneyPref.getInstance().isEuro()) {
            holder.rowEstateBinding.rowPrice.setText(estate.getPrice() + " $");

        } else {
            holder.rowEstateBinding.rowPrice.setText(Utils.convertDollarToEuro(estate.getPrice()) + " €");
        }


//    public void setEstate(List<Estate> estates){
//        this.estateList = estates;
//        notifyDataSetChanged();
//    }
    }
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

        Log.e("adapter", String.valueOf(pictureList.size()));
    }


}
