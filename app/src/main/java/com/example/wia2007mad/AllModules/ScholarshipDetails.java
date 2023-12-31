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
import com.example.wia2007mad.databinding.ScholarshipDetailsBinding;

public class ScholarshipDetails extends AppCompatActivity {


    protected ScholarshipDetailsBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ScholarshipDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar=binding.ToolbarScholarshipDetails;
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // for the 'up' button
            actionBar.setTitle("");
            // further customization goes here
        }

        ScholarshipModel scholarshipModel=getIntent().getParcelableExtra("ScholarshipModel");
        if(scholarshipModel!=null){
            binding.scholarshipdetailstitle.setText(scholarshipModel.getScholarshipTitle());
            binding.scholarshipdetailsdeadline.setText("Deadline: "+scholarshipModel.getScholarshipDeadline());
            binding.scholarshipdetailscontent.setText(scholarshipModel.getScholarshipDescription());
            Glide.with(this).load(scholarshipModel.getScholarshipImageURL()).into(binding.scholarshipdetailsimageview);
            binding.applynowbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ScholarshipDetails.this, webViewPage.class);
                    String urltoload=scholarshipModel.getScholarshipURL();
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
