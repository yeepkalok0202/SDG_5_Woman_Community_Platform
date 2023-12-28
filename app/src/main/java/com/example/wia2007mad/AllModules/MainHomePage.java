package com.example.wia2007mad.AllModules;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
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
            actionBar.setDisplayHomeAsUpEnabled(true);
            // for the 'up' button
            // further customization goes here
        }
        binding.ToolbarMainPage.setVisibility(View.GONE);

        appBarConfiguration= new AppBarConfiguration.Builder(R.id.homeFragment,R.id.infoFragment,R.id.settingFragment,R.id.profileFragment).build();
        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.FragmentContainerForMainHomePage);
        navController=host.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(NavController controller, NavDestination destination, Bundle arguments) {
                if (destination.getId() == R.id.homeFragment) {
                    binding.ToolbarMainPage.setVisibility(View.GONE);
                } else {
                    binding.ToolbarMainPage.setVisibility(View.VISIBLE);
                    if(destination.getId()==R.id.infoFragment){
                        binding.toolbartitle.setText("Info");
                    }
                    else if(destination.getId()==R.id.settingFragment){
                        binding.toolbartitle.setText("Settings");
                    }
                    else if(destination.getId()==R.id.profileFragment){
                        binding.toolbartitle.setText("My Profile");
                    }
                    else{
                        binding.toolbartitle.setText("Update Profile");
                    }
                }
            }
        });
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
        return NavigationUI.navigateUp(navController,appBarConfiguration)||super.onSupportNavigateUp();
    }

}
