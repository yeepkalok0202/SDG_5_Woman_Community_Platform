package com.example.wia2007mad.AllModules.socialmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;

import com.example.wia2007mad.AllModules.WorkshopModel;
import com.example.wia2007mad.databinding.MarketingCardDetailsBinding;

public class MarketingDetails extends AppCompatActivity {
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View customView;

    protected MarketingCardDetailsBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MarketingCardDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Retrieve data from intent
        String course = getIntent().getStringExtra("course");
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String imageUrl = getIntent().getStringExtra("imageUrl");


        // Set data to UI elements
        binding.carddetailscourse.setText(course);
        binding.carddetailsname.setText(name);
        binding.carddetailsdesc.setText(description);




        String videoUrl = getIntent().getStringExtra("videoUrl");
        if (videoUrl != null) {
            binding.marketvideo.loadData(videoUrl, "text/html", "utf-8");
            binding.marketvideo.getSettings().setJavaScriptEnabled(true);
            // Add WebChromeClient and other necessary configurations
        }

        //video
        WorkshopModel workshopModel=getIntent().getParcelableExtra("MarketData");
        if(workshopModel!=null){

            //video embedding
            binding.marketvideo.loadData(workshopModel.getVideoURL(),"text/html","utf-8");
            binding.marketvideo.getSettings().setJavaScriptEnabled(true);
            binding.marketvideo.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onShowCustomView(View view, CustomViewCallback callback) {
                    customView=view;
                    customViewCallback=callback;
                    binding.fullscreenmode.addView(view);
                    binding.marketvideo.setVisibility(View.GONE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }

                @SuppressLint("SourceLockedOrientationActivity")
                @Override
                public void onHideCustomView() {
                    binding.fullscreenmode.removeView(customView);
                    customView=null;
                    binding.marketvideo.setVisibility(View.VISIBLE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

                    if(customViewCallback!=null){
                        customViewCallback.onCustomViewHidden();
                        customViewCallback=null;
                    }
                }
            });

        }

        //button from details to previous list page
        binding.arrowbackpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }







}
