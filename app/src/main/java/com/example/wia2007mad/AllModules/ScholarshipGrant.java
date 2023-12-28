package com.example.wia2007mad.AllModules;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.wia2007mad.databinding.ScholarshipGrantBinding;

public class ScholarshipGrant extends AppCompatActivity {

    private ScholarshipGrantBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ScholarshipGrantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar=binding.ToolbarScholarship;
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // for the 'up' button
            // further customization goes here
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