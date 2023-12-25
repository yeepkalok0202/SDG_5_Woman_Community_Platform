package com.example.wia2007mad.ELearning;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wia2007mad.R;
import com.example.wia2007mad.databinding.FragmentUpdateProfileBinding;


public class UpdateProfileFragment extends Fragment {

    private FragmentUpdateProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentUpdateProfileBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}