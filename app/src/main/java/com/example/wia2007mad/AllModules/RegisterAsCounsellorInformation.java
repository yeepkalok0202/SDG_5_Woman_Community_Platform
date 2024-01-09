package com.example.wia2007mad.AllModules;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wia2007mad.databinding.InformationToRegisterAsCounsellorBinding;

public class RegisterAsCounsellorInformation extends AppCompatActivity {
    InformationToRegisterAsCounsellorBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=InformationToRegisterAsCounsellorBinding.inflate(getLayoutInflater());


        binding.emailtobeverifiedascounsellor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
                selectorIntent.setData(Uri.parse("mailto:"));

                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"kalokyeep@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Request for approval as registered counsellor [Her Journey]");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Proof yourself here...");
                emailIntent.setSelector( selectorIntent );

                // Check if an email app is available
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));

                }
                onBackPressed();
                finish();
            }

        });
        setContentView(binding.getRoot());

    }
}
