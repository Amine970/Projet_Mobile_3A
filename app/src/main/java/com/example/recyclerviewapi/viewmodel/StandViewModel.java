package com.example.recyclerviewapi.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.recyclerviewapi.model.Stand;
import com.example.recyclerviewapi.remote.UserRepository;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StandViewModel extends ViewModel
{
    public String stand_name;
    public String stand_user;
    public String stand_image;
    public String user_image;
    public String apparition_part_fk;
    public String stand_power;
    public String stand_id;

    public MutableLiveData<ArrayList<StandViewModel>> arrayListMutableLiveData = new MutableLiveData<>();

    private ArrayList<StandViewModel> arrayList;
    private UserRepository userRepository;

    public String getStand_image() {
        return stand_image;
    }

    @BindingAdapter({"stand_image"})

    public static void loadStandImage(ImageView imageView, String stand_image)
    {
        Picasso.with(imageView.getContext()).load(stand_image).into(imageView);
    }
    public String getStand_power() {
        return stand_power;
    }

    public StandViewModel()
    {
        userRepository = new UserRepository();
        arrayListMutableLiveData = userRepository.getArrayListMutableLiveData();
    }

    public String getStand_name() {
        return stand_name;
    }

    public String getStand_user() {
        return stand_user;
    }



    public String getUserImage() {
        return user_image;
    }

    public StandViewModel(Stand stand)
    {
        stand_name  = stand.getStand_name();
        stand_user = stand.getStand_user();
        stand_image = stand.getStand_image();
        user_image = stand.getUser_image();
        apparition_part_fk = stand.getApparition_part_fk();
        stand_power = stand.getStand_power();
        stand_id = stand.getStand_id();
    }

    public MutableLiveData<ArrayList<StandViewModel>> getArrayListMutableLiveData()
    {

        return arrayListMutableLiveData;
    }
}
