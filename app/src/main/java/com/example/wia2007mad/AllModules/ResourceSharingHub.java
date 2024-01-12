package com.example.wia2007mad.AllModules;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.wia2007mad.R;
import com.example.wia2007mad.databinding.ResourceSharingBinding;

public class ResourceSharingHub extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private ResourceSharingBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ResourceSharingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar=binding.ToolbarResource;
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        binding.backtomainpageelearningbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        appBarConfiguration= new AppBarConfiguration.Builder(R.id.displayPostFragment,R.id.publishPostFragment).build();
        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.FragmentContainerForResourceSharingHub);
        navController=host.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigationViewForResourceSharingHub,navController);
        if(actionBar!=null){
            actionBar.setTitle("");
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

    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController,appBarConfiguration)||super.onSupportNavigateUp();
    }


}
