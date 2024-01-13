package com.example.wia2007mad.AllModules.socialmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.wia2007mad.databinding.SuccessstoriesdetailsBinding;

public class SuccessDetailsActivity extends AppCompatActivity {

    private SuccessstoriesdetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SuccessstoriesdetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get data from Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("story_desc");
        String imageUrl = intent.getStringExtra("imageUrl");

        // Set data to the views
        binding.storiesdetailsname.setText(name);
        binding.storiesdetailstitle.setText(title);
        binding.storiesdetailsdesc.setText(desc);

        //button from success details to list
        binding.arrowbacksuccessstory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Load image using Glide
        Glide.with(this)
                .load(imageUrl)
                .into(binding.storiesdetailsimage);
    }
}