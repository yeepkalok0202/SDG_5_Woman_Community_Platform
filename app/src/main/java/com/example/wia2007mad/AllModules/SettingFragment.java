package com.example.wia2007mad.AllModules;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wia2007mad.databinding.FragmentSettingBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.time.Duration;


public class SettingFragment extends Fragment {
    private FragmentSettingBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentSettingBinding.inflate(inflater,container,false);

        binding.changepasswordbuttoninsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ResetPassword.class);
                startActivity(intent);
            }
        });

        binding.logoutbuttoninsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Signing you out...",Toast.LENGTH_LONG).show();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Navigate back to the LoginActivity (Main Activity)
                        Intent intent = new Intent(getActivity(), Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        editor.putBoolean("isLoggedOut",true);
                        editor.apply();
                        // Finish the current activity

                        getActivity().finish();
                    }
                }, 2000);
            }});
        return binding.getRoot();

    }
}