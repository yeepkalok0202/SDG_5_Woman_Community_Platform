package com.example.wia2007mad.AllModules;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.example.wia2007mad.R;
import com.example.wia2007mad.databinding.SplashScreenBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    private static int SPLASH_SCREEN= 5000;
    //Variable for animation
    Animation topAnim, bottomAnim,quote1Anim,quote2Anim;
    SplashScreenBinding splashScreenBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        splashScreenBinding=SplashScreenBinding.inflate(getLayoutInflater());
        setContentView(splashScreenBinding.getRoot());

        //Animations
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        quote1Anim=AnimationUtils.loadAnimation(this,R.anim.quote1);
        quote2Anim=AnimationUtils.loadAnimation(this,R.anim.quote2);

        splashScreenBinding.imageView.setAnimation(topAnim);
        splashScreenBinding.textView.setAnimation(bottomAnim);
        splashScreenBinding.textView2.setAnimation(quote1Anim);
        splashScreenBinding.textView3.setAnimation(quote2Anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);


    }
}