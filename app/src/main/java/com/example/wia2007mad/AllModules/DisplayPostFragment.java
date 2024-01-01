package com.example.wia2007mad.AllModules;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wia2007mad.R;
import com.example.wia2007mad.databinding.FragmentDisplayPostBinding;


public class DisplayPostFragment extends Fragment {

    FragmentDisplayPostBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentDisplayPostBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}