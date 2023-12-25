package com.example.wia2007mad.ELearning;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.wia2007mad.R;
import com.example.wia2007mad.databinding.MainHomePageBinding;

public class MainHomePage extends AppCompatActivity {
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private MainHomePageBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.ToolbarMainPage);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // for the 'up' button
            // further customization goes here
        }
        if (isHomeFragmentActive()) {
            binding.ToolbarMainPage.setVisibility(View.GONE);
        } else {
            binding.ToolbarMainPage.setVisibility(View.VISIBLE);
        }
        appBarConfiguration= new AppBarConfiguration.Builder(R.id.homeFragment,R.id.infoFragment,R.id.settingFragment,R.id.profileFragment).build();
        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.FragmentContainer);
        navController=host.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController);

    }
  /*  @Override
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
    } */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item,navController)||super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.global_menu, menu);
        return true;
    }

    private boolean isHomeFragmentActive() {
        // Replace this with your logic to determine if the Home Fragment is active
        // For example, you can check the current destination of the NavController
        return navController.getCurrentDestination().getId() == R.id.homeFragment;
    }
}
