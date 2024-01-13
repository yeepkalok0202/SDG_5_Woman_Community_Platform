package com.example.wia2007mad.AllModules.socialmarket;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.wia2007mad.databinding.CardviewdetailspageBinding;

public class CardDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CardviewdetailspageBinding binding = CardviewdetailspageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieve data from intent
        String course = getIntent().getStringExtra("course");
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String imageUrl = getIntent().getStringExtra("imageUrl");

        // Set data to UI elements
        binding.carddetailscourse.setText(course);
        binding.carddetailsname.setText(name);
        binding.carddetailsdesc.setText(description);

        //button from details to previous list page
        binding.arrowbackpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Load image using Glide
        Glide.with(this)
                .load(imageUrl)
                .into(binding.carddetailsimage);
    }
}
