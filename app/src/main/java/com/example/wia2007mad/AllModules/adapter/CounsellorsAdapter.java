package com.example.wia2007mad.AllModules.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wia2007mad.AllModules.listeners.CounsellorListener;
import com.example.wia2007mad.AllModules.model.Counsellor;
import com.example.wia2007mad.databinding.ItemContainerCounsellorBinding;

import java.util.List;

public class CounsellorsAdapter extends RecyclerView.Adapter<CounsellorsAdapter.CounsellorViewHolder>{

    private final List<Counsellor> counsellors;
    private final CounsellorListener counsellorListener;

    public CounsellorsAdapter(List<Counsellor>counsellors, CounsellorListener counsellorListener){
        this.counsellors = counsellors;
        this.counsellorListener = counsellorListener;
    }

    @NonNull
    @Override
    public CounsellorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerCounsellorBinding itemContainerCounsellorBinding = ItemContainerCounsellorBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new CounsellorViewHolder(itemContainerCounsellorBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CounsellorViewHolder holder, int position) {
        holder.setUserData(counsellors.get(position));
    }

    @Override
    public int getItemCount() {
        return counsellors.size();
    }

    class CounsellorViewHolder extends RecyclerView.ViewHolder{

        ItemContainerCounsellorBinding binding;

        CounsellorViewHolder(ItemContainerCounsellorBinding itemContainerUserBinding){
            super(itemContainerUserBinding.getRoot());
            binding = itemContainerUserBinding;
        }

        void setUserData(Counsellor counsellor){
            binding.textName.setText(counsellor.name);
            binding.textEmail.setText(counsellor.email);
            Glide.with(binding.imageProfile.getContext())
                    .load(counsellor.image)
                    .into(binding.imageProfile);
            binding.getRoot().setOnClickListener(view -> counsellorListener.onUserClicked(counsellor));
        }
    }
}
