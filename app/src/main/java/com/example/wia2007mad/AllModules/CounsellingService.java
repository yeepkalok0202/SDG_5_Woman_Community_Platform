package com.example.wia2007mad.AllModules;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wia2007mad.R;

public class CounsellingService extends AppCompatActivity {

    ImageButton btnBackFromCounselling;
    Button btnPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counselling_service);

        btnBackFromCounselling = findViewById(R.id.BtnBackFromCounselling);
        btnPatient = findViewById(R.id.BtnPatient);

        btnBackFromCounselling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                //startActivity(new Intent(CounsellingService.this, HealthHome.class));
            }
        });

        btnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CounsellingService.this, recentConversation.class));
            }
        });

    }
}