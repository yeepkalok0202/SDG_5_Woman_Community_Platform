package com.example.wia2007mad.AllModules;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.wia2007mad.databinding.FragmentInfoBinding;

public class InfoFragment extends Fragment {

   private FragmentInfoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentInfoBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

}