package com.example.wia2007mad.AllModules;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.wia2007mad.databinding.SkillDetailsBinding;

public class SkillDetails extends AppCompatActivity {

    private WebChromeClient.CustomViewCallback customViewCallback;
    private View customView;

    protected SkillDetailsBinding binding;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=SkillDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Toolbar toolbar=binding.ToolbarSkillDetails;
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // for the 'up' button
            actionBar.setTitle("");
            // further customization goes here
        }


        SkillModel skillModel=getIntent().getParcelableExtra("SkillModel");
        if(skillModel!=null){

            //video embedding
            binding.skillvideo.loadData(skillModel.getVideoURL(),"text/html","utf-8");
            binding.skillvideo.getSettings().setJavaScriptEnabled(true);
            binding.skillvideo.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onShowCustomView(View view, CustomViewCallback callback) {
                    customView=view;
                    customViewCallback=callback;
                    binding.fullscreenmode.addView(view);
                    binding.skillvideo.setVisibility(View.GONE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }

                @SuppressLint("SourceLockedOrientationActivity")
                @Override
                public void onHideCustomView() {
                    binding.fullscreenmode.removeView(customView);
                    customView=null;
                    binding.skillvideo.setVisibility(View.VISIBLE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

                    if(customViewCallback!=null){
                        customViewCallback.onCustomViewHidden();
                        customViewCallback=null;
                    }
                }
            });
            binding.skilltitle.setText(skillModel.getTitle());
            binding.skilldescription.setText(skillModel.getDescription());
            binding.skillauthorindetailsview.setText(skillModel.getAuthor());
            System.out.println(skillModel.getDescription()+" im here see xia");
        }


        OnBackPressedCallback callback= new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed(){
                if(customView!=null){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        binding.skillvideo.getWebChromeClient().onHideCustomView();
                    }
                }
                else{
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this,callback);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Handle the configuration change
        // For WebView, no specific action is required here
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