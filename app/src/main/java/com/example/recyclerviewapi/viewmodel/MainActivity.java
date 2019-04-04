package com.example.recyclerviewapi.viewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.recyclerviewapi.R;
import com.example.recyclerviewapi.adapter.MyStandsAdapter;
import com.example.recyclerviewapi.model.Stand;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private StandViewModel standViewModel;
    private MyStandsAdapter myStandsAdapter;
    public static Context contextOfApplication;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contextOfApplication = getApplicationContext();
        setTitle("Jojo's Stands list");
        recyclerView = findViewById(R.id.recyclerView);
        standViewModel = ViewModelProviders.of(this).get(StandViewModel.class);
        standViewModel.getArrayListMutableLiveData().observe(this, new Observer<ArrayList<StandViewModel>>()
        {
            @Override
            public void onChanged(@Nullable final ArrayList<StandViewModel> standViewModels)
            {
                myStandsAdapter = new MyStandsAdapter(standViewModels, MainActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(myStandsAdapter);
                myStandsAdapter.setOnItemClickListener(new MyStandsAdapter.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(int position)
                    {
                        openDetailsActivity(standViewModels.get(position));
                    }
                });
            }
        });
    }
    public void openDetailsActivity(StandViewModel standViewModel)
    {
        Intent intent = new Intent(this, DetailsActivity.class);
        Stand stand = new Stand(standViewModel);
        intent.putExtra("stand", stand);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stand_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                myStandsAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
}