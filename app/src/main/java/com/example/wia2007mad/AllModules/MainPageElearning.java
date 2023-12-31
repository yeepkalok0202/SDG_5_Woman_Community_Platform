package com.example.wia2007mad.AllModules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wia2007mad.R;
import com.example.wia2007mad.databinding.MainpageELearningBinding;

public class MainPageElearning extends AppCompatActivity {

    private MainpageELearningBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= MainpageELearningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String  article1=getString(R.string.virtualthumbnaillink1),
                article2=getString(R.string.virtualthumbnaillink2),
                article3=getString(R.string.virtualthumbnaillink3);
        Glide.with(this)
                .load(article1)
                .into(binding.workshopimage1);
        Glide.with(this)
                .load(article2)
                .into(binding.workshopimage2);
        Glide.with(this)
                .load(article3)
                .into(binding.workshopimage3);

        binding.virtualbuttongogogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Workshop.class);
                startActivity(intent);
            }
        });

        binding.scholarshipbuttongogogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), ScholarshipGrant.class);
                startActivity(intent);
            }
        });

        binding.workshopcardview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        binding.workshopcardview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        binding.workshopcardview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        binding.scholarshipcardview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.scholarshipcardview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.scholarshipcardview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void showPopup(String titleinput,String contentinput,String urlinput) {
        // Create the dialog
        final Dialog dialog = new Dialog(MainPageElearning.this);
        dialog.setContentView(R.layout.overlay_confirm);

        // Initialize the views
        TextView cancel = dialog.findViewById(R.id.popupcancel),ok=dialog.findViewById(R.id.popupok),
                title=dialog.findViewById(R.id.popuptitle),content=dialog.findViewById(R.id.popupcontent);

        // Set text or other properties if needed
        title.setText(titleinput);

        content.setText(contentinput);
        // Set the close button action
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), webViewPage.class);
                intent.setData(Uri.parse(urlinput));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        // Set the dialog background to transparent
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        // Show the popup dialog
        dialog.show();
    }

}