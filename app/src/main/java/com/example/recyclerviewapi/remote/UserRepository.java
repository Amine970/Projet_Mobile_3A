package com.example.recyclerviewapi.remote;

import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;

import android.util.Log;

import com.example.recyclerviewapi.model.Stand;
import com.example.recyclerviewapi.viewmodel.MainActivity;
import com.example.recyclerviewapi.viewmodel.StandViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static android.content.Context.MODE_PRIVATE;

public class UserRepository
{
    private static final String TAG = "cleassaf";
    public MutableLiveData<ArrayList<StandViewModel>> arrayListMutableLiveData;
    private ArrayList<StandViewModel> myStandsViews;
    private List<Stand> standsArrayList;
    public MutableLiveData<ArrayList<StandViewModel>> getArrayListMutableLiveData()
    {
        SharedPreferences sharedPreferences = MainActivity.getContextOfApplication().getSharedPreferences("shared preferences", MODE_PRIVATE);
        String json = sharedPreferences.getString("stand list", null);
        Type type = new TypeToken<ArrayList<Stand>>(){}.getType();
        standsArrayList = new Gson().fromJson(json, type);
        if (standsArrayList != null)
        {
            Log.i(TAG, "ce n'est pas nul");
            StandViewModel standViewModel;
            myStandsViews = new ArrayList<>();
            arrayListMutableLiveData = new MutableLiveData<>();
            for(Stand x : standsArrayList)
            {
                standViewModel = new StandViewModel(x);
                myStandsViews.add(standViewModel);
            }
            arrayListMutableLiveData.setValue(myStandsViews);
            return arrayListMutableLiveData;
        }
        else
        {
            standsArrayList = new ArrayList<>();
            Log.i(TAG, "est nul");
            arrayListMutableLiveData = new MutableLiveData<>();
            StandsApi standsApi = RetrofitClass.getApiService();
            Call<List<Stand>> call = standsApi.getStands();
            call.enqueue(new Callback<List<Stand>>()
            {
                @Override
                public void onResponse(Call<List<Stand>> call, Response<List<Stand>> response)
                {
                    StandViewModel standViewModel;
                    myStandsViews = new ArrayList<>();
                    if (!response.isSuccessful()) {
                        Log.i(TAG, "réponse mais pas successful");
                        return;
                    }
                    for (Stand x : response.body()) {
                        standsArrayList.add(x);
                        standViewModel = new StandViewModel(x);
                        myStandsViews.add(standViewModel);
                    }
                    arrayListMutableLiveData.setValue(myStandsViews);
                    saveData();
                }

                @Override
                public void onFailure(Call<List<Stand>> call, Throwable t) {
                    Log.i(TAG, "onFailure carrément");
                }
            });

            return arrayListMutableLiveData;
        }

    }

    public void saveData()
    {
        Log.i(TAG, "data saved here");
        SharedPreferences sharedPreferences = MainActivity.getContextOfApplication().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(standsArrayList);
        editor.putString("stand list", json);
        editor.apply();
    }
}
