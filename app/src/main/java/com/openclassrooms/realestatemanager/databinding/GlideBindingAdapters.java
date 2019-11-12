package com.openclassrooms.realestatemanager.databinding;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;

public class GlideBindingAdapters {
    @BindingAdapter("image_resource")
    public static void setImageResource(ImageView view, int imageUrl){
        Context context = view.getContext();

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);

        Glide.with(context)
                .setDefaultRequestOptions(options)
                .load(imageUrl)
                .into(view);
    }

    //for nav header
    @BindingAdapter("user_photo")
    public static void setPhotoResource(ImageView view, int photoUrl){
        Context context = view.getContext();

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);

        Glide.with(context)
                .setDefaultRequestOptions(options)
                .load(photoUrl)
                .circleCrop()
                .into(view);
    }
}
