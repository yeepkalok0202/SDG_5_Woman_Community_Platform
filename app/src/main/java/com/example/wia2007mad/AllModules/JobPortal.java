package com.example.wia2007mad.AllModules;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.wia2007mad.R;
import com.example.wia2007mad.databinding.JobPortalBinding;

public class JobPortal extends AppCompatActivity {

    private JobPortalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = JobPortalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String article1 = getString(R.string.skillimagelink1),
                article2 = getString(R.string.skillimagelink2),
                article3 = getString(R.string.skillimagelink3);
        Glide.with(this)
                .load(article1)
                .into(binding.skillimage1);
        Glide.with(this)
                .load(article2)
                .into(binding.skillimage2);
        Glide.with(this)
                .load(article3)
                .into(binding.skillimage3);

        binding.MoreSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Skill.class);
                startActivity(intent);
            }
        });

        binding.skillcardview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(JobPortal.this, Skill.class);
                intent.putExtra("searchQuery","Dan");
                startActivity(intent);

            }
        });
        binding.skillcardview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(JobPortal.this, Skill.class);
                intent.putExtra("searchQuery","Self");
                startActivity(intent);

            }
        });
        binding.skillcardview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(JobPortal.this, Skill.class);
                intent.putExtra("searchQuery","Linda");
                startActivity(intent);

            }
        });

        binding.MoreJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Job.class);
                startActivity(intent);
            }
        });

        binding.CreateResumebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResumeBuilderPg1.class);
                startActivity(intent);

            }
        });
    }
}
