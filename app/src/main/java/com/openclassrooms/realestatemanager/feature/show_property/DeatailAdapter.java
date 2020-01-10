package com.openclassrooms.realestatemanager.feature.show_property;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.RowPhotoDetailBinding;
import com.openclassrooms.realestatemanager.models.Picture;

import java.io.IOException;
import java.util.List;

public class DeatailAdapter extends RecyclerView.Adapter<DetailViewHolder> {

    private List<Picture> pictureList;
    private Context context;
   // private Uri uri;
    private Bitmap bitmap;

    public DeatailAdapter(List<Picture> pictureList, Context context) {
        this.pictureList = pictureList;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowPhotoDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_photo_detail, parent, false);

        return new DetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {

            Picture picture = pictureList.get(position);
           Uri uri = picture.getUri();
//            try {
//                context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
//                Log.e("bmpUri", String.valueOf(uri));

//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.e("tag", String.valueOf(picture.getEstateId()));
//            }
//            Log.e("bmpUrii", String.valueOf(uri));
            Glide.with(context).load(uri).centerCrop().into(holder.binding.rowImg);




    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    public void updatePictureData(List<Picture> pictureList) {
        this.pictureList = pictureList;
        this.notifyDataSetChanged();

    }
}
