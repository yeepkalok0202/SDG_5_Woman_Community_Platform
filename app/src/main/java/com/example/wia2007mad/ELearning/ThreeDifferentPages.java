package com.example.wia2007mad.ELearning;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.wia2007mad.databinding.ActivityThreeDifferentPagesBinding;

import com.example.wia2007mad.R;

public class ThreeDifferentPages extends AppCompatActivity {

    private ActivityThreeDifferentPagesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityThreeDifferentPagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

}