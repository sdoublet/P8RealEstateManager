package com.openclassrooms.realestatemanager.feature.show_property;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
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
import com.openclassrooms.realestatemanager.util.SharePreferencesHelper;
import com.openclassrooms.realestatemanager.util.Utils;

import java.util.List;

public class EstateAdapter extends RecyclerView.Adapter<EstateViewHolder> {

    private List<Estate> estateList;
    private List<Picture> pictureList;
    private Context context;
    private Bitmap bitmap;
    private Uri uri;
    private RowEstateBinding binding;


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

         binding = DataBindingUtil.inflate(inflater, R.layout.row_estate, parent, false);

        return new EstateViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull EstateViewHolder holder, int position) {
        Estate estate = estateList.get(position);
        Log.e("adapterId", String.valueOf(estate.getAgentId()));
        holder.rowEstateBinding.setEstate(estate);

        for (int i = 0; i < pictureList.size(); i++) {
            if (pictureList.get(i) != null) {
                Picture picture = pictureList.get(i);

                Log.e("EstateAdapterPic", String.valueOf(picture.getUri() + " " + picture.getEstateId()) + estate.getCity());
                if (picture.getEstateId() == estate.getEstateId()) {
                    Log.e("EstatePictureList", String.valueOf(picture.getEstateId()));
                    Uri uri = picture.getUri();
                    String uriString = uri.toString();
                    Glide.with(context).load(uri.getPath()).centerCrop().into(holder.rowEstateBinding.imgRowEstate);
                    // to make difference between content and storage provide
//                    if (uriString.contains("content")) {
//                        Glide.with(context).load(uri).centerInside().into(holder.rowEstateBinding.imgRowEstate);

//                    } else {
//                        Glide.with(context).load(uri.getPath()).centerInside().into(holder.rowEstateBinding.imgRowEstate);

//                    }
                    Log.e("uri", "yes " + uri + " " + picture.getEstateId() + " " + position);

                    //   Glide.with(context).load(uri).centerCrop().into(holder.rowEstateBinding.imgRowEstate);
//            try {
//                context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//                 bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
//                Glide.with(context).load(uri).centerCrop().into(holder.rowEstateBinding.imgRowEstate);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//           // Glide.with(context).load(bitmap).centerCrop().into(holder.rowEstateBinding.imgRowEstate);


                }
            }

        }


        this.isEstateSold(holder, estate);

        this.moneyPref(holder, estate);

    }


    //Catch money preference
    private void moneyPref(@NonNull EstateViewHolder holder, Estate estate) {
        if (!SharePreferencesHelper.getInstance().isEuro()) {
            holder.rowEstateBinding.rowPrice.setText(Utils.stringFromatPrice(estate.getPrice()) + " $");

        } else {
            holder.rowEstateBinding.rowPrice.setText(Utils.stringFromatPrice(Utils.convertDollarToEuro(estate.getPrice())) + " €");
        }
    }

    //Show sold icon
    private void isEstateSold(@NonNull EstateViewHolder holder, Estate estate) {
        if (estate.isSold()) {
            holder.rowEstateBinding.soldView.setImageResource(R.drawable.sold_house);
            holder.rowEstateBinding.soldView.setVisibility(View.VISIBLE);

        } else {
            holder.rowEstateBinding.soldView.setVisibility(View.INVISIBLE);
        }
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
        Log.e("estateSize", String.valueOf(estateList.size()));
    }

    public void updateDataWithPicture(List<Picture> picture) {
        this.pictureList = picture;
        this.notifyDataSetChanged();

        Log.e("adapter", String.valueOf(pictureList.size()));
    }


}
