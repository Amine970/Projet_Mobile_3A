package com.example.recyclerviewapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recyclerviewapi.model.Stand;
import com.squareup.picasso.Picasso;


public class DetailsActivity extends AppCompatActivity
{
    private ImageView userImage;
    private TextView userName;
    private TextView standPower;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        Stand stand = intent.getParcelableExtra("stand");
        userImage = findViewById(R.id.userImage);
        userName = findViewById(R.id.userName);
        standPower = findViewById(R.id.standPower);
        userName.setText(stand.getStand_user());
        standPower.setText(stand.getStand_power());
        Picasso.with(this).load(stand.getUser_image()).fit().into(userImage);

    }
}
