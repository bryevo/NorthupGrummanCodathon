package com.example.brye.northrupgrummanapp;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import java.util.List;

public class EditDirections extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_directions);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
