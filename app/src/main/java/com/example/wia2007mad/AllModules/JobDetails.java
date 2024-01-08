package com.example.wia2007mad.AllModules;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.wia2007mad.databinding.JobDetailsBinding;

public class JobDetails extends AppCompatActivity {


    protected JobDetailsBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=JobDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar=binding.ToolbarJobDetails;
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // for the 'up' button
            actionBar.setTitle("");
            // further customization goes here
        }

        JobModel jobModel=getIntent().getParcelableExtra("JobModel");
        if(jobModel!=null){
            binding.jobdetailstitle.setText(jobModel.getJobTitle());
            binding.jobdetailsdeadline.setText("Deadline: "+jobModel.getJobDeadline());
            binding.jobdetailscontent.setText(jobModel.getJobDescription());
            Glide.with(this).load(jobModel.getJobImageURL()).into(binding.jobdetailsimageview);
            binding.applynowbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(JobDetails.this, webViewPage.class);
                    String urltoload=jobModel.getJobURL();
                    intent.putExtra("url",urltoload);
                    startActivity(intent);
                }
            });
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks
        switch (item.getItemId()) {
            case android.R.id.home:
                // User clicked 'Up' button, so close this activity and return to parent
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}