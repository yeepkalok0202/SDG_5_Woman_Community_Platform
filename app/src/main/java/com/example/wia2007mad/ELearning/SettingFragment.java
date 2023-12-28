package com.example.wia2007mad.ELearning;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wia2007mad.R;
import com.example.wia2007mad.databinding.FragmentSettingBinding;


public class SettingFragment extends Fragment {
    private FragmentSettingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentSettingBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }
}