package com.openclassrooms.realestatemanager.feature.user_profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.EstateViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityProfileBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.util.AgentId;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

  private ActivityProfileBinding binding;
  private EstateViewModel estateViewModel;
  private long AGENT_ID = AgentId.getInstance().getAgentId();
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
    Glide.with(this).load(getDrawable(R.drawable.photo_seb)).circleCrop().into(binding.photoProfile);// take photo since db
    configureViewModel();
    this.setData(AGENT_ID);
    Log.e("agentId", String.valueOf(AgentId.getInstance().getAgentId()));

  }

  private void setData(long agentId){
    estateViewModel.getUser(agentId).observe(this, this::getUserData);
  }

  private void getUserData(User user){
    binding.name.setText(user.getName());
    binding.surname.setText(user.getSurname());
    binding.agency.setText(user.getAgency());
    binding.email.setText(user.getEmail());
    getAllEstateByAgent();

  }
  private void configureViewModel(){
    ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
    this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
  }

  private void getAllEstateByAgent(){
    estateViewModel.getEstatePerAgent(AGENT_ID).observe(this, this::setEstatesNumber);
  }
  private void setEstatesNumber(List<Estate> estateList){
    binding.estates.setText(String.valueOf(estateList.size()));
  }

}