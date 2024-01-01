package com.example.wia2007mad.AllModules;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wia2007mad.R;
import com.example.wia2007mad.databinding.FragmentProfileBinding;
import com.example.wia2007mad.databinding.FragmentPublishPostBinding;


public class PublishPostFragment extends Fragment {

    FragmentPublishPostBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentPublishPostBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }
}