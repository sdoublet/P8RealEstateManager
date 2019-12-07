package com.openclassrooms.realestatemanager.feature.show_property;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.databinding.RowEstateBinding;

import java.util.List;

public class EstateAdapter extends RecyclerView.Adapter<EstateViewHolder> {

    private List<Estate> estateList;
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
       // holder.rowEstateBinding.rowCity.setText(estate.getCity());
        holder.rowEstateBinding.setEstate(estate);
        //holder.rowEstateBinding.imgRowEstate.setImageResource(R.drawable.country_house);
       // holder.rowEstateBinding.rowPrice.setText((int) estate.getPrice());
       // holder.rowEstateBinding.rowType.setText(estate.getType());
    }

    public void setEstate(List<Estate> estates){
        this.estateList = estates;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return estateList.size();
    }

    public void updateData(List<Estate> estates){
        this.estateList = estates;
        this.notifyDataSetChanged();
    }
}
