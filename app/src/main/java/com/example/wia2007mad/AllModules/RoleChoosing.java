package com.example.wia2007mad.AllModules;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wia2007mad.AllModules.utilities.PreferenceManager;
import com.example.wia2007mad.databinding.ChoosingRoleFirstimerBinding;
import com.example.wia2007mad.databinding.InformationToRegisterAsCounsellorBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RoleChoosing extends AppCompatActivity {
    ChoosingRoleFirstimerBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private PreferenceManager preferenceManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ChoosingRoleFirstimerBinding.inflate(getLayoutInflater());
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();


        binding.communityuserchoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RoleChoosing.this, Login.class);
                startActivity(intent);
            }
        });
        setContentView(binding.getRoot());
        binding.counsellorchoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RoleChoosing.this, RegisterAsCounsellorInformation.class);
                startActivity(intent);
            }
        });
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        preferenceManager = new PreferenceManager(getApplicationContext());

        SharedPreferences sharedPreferences= getSharedPreferences("AppPrefs",MODE_PRIVATE);
        boolean isLoggedOut=sharedPreferences.getBoolean("isLoggedOut",false);
        if(currentUser != null && !isLoggedOut){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
    }
}
