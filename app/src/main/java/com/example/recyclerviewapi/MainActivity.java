package com.example.recyclerviewapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView mRecyclerView;
    List<Stand> myStands;
    private MyStandsAdapter myAdapter;
    private static final String TAG = "cleassaf";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        loadData();
    }
    public void saveData()
    {
        Log.i(TAG, "data saved here");
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(myStands);
        editor.putString("stand list", json);
        editor.apply();
    }
    private void loadData()
    {

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String json = sharedPreferences.getString("stand list", null);
        Type type = new TypeToken<ArrayList<Stand>>() {}.getType();
        myStands = new Gson().fromJson(json, type);
        if(myStands == null)
        {
            Log.i(TAG, "data null here");
            myStands = new ArrayList<>();
            retrofitCall();
        }
        else
            setAdapter();
    }
    public void setAdapter()
    {
        myAdapter = new MyStandsAdapter(myStands, mRecyclerView.getContext());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(new MyStandsAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                openDetailsActivity(position);
            }
        });
    }
    public void retrofitCall()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rly-chrono.fr/api/stands/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        StandsApi standsApi = retrofit.create(StandsApi.class);
        Call<List<Stand>> call = standsApi.getStands();
        call.enqueue(new Callback<List<Stand>>()
        {
            @Override
            public void onResponse(Call<List<Stand>> call, Response<List<Stand>> response)
            {
                if(!response.isSuccessful())
                {
                    Log.i(TAG, "réponse mais pas successful");
                    return;
                }
                for(Stand x : response.body())
                {
                    myStands.add(x);
                }
                saveData();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<Stand>> call, Throwable t)
            {
                Log.i(TAG, "onFailure carrément");
            }
        });
    }
    public void openDetailsActivity(int position)
    {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("Stand", myStands.get(position));
        startActivity(intent);
    }
}
