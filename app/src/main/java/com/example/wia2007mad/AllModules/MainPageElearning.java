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
    }

    private void showPopup() {
        // Create the dialog
        final Dialog dialog = new Dialog(MainPageElearning.this);
        dialog.setContentView(R.layout.overlay_confirm);

        // Initialize the views
        TextView cancel = dialog.findViewById(R.id.popupcancel),ok=dialog.findViewById(R.id.popupok),
                title=dialog.findViewById(R.id.popuptitle),content=dialog.findViewById(R.id.popupcontent);

        // Set text or other properties if needed
        title.setText("Redirect to Scholar");

        content.setText("You are now on board to external website of women health matters...");
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
                String url = "https://www.google.com";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
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