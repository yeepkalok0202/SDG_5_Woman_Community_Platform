package com.example.wia2007mad.AllModules.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wia2007mad.AllModules.listeners.HealthEducationListener;
import com.example.wia2007mad.AllModules.model.EduData;
import com.example.wia2007mad.databinding.ItemContainerEduDataBinding;

import java.util.List;

public class HealthEduAdapter extends RecyclerView.Adapter<HealthEduAdapter.EduDataViewHolder>{

    private final List<EduData> eduDataList;
    private final HealthEducationListener healthEducationListener;

    public HealthEduAdapter(List<EduData>eduDataList, HealthEducationListener healthEducationListener){
        this.eduDataList = eduDataList;
        this.healthEducationListener = healthEducationListener;
    }

    @NonNull
    @Override
    public EduDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerEduDataBinding itemContainerEduDataBinding = ItemContainerEduDataBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new EduDataViewHolder(itemContainerEduDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EduDataViewHolder holder, int position) {
        holder.setData(eduDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return eduDataList.size();
    }

    class EduDataViewHolder extends RecyclerView.ViewHolder{
        ItemContainerEduDataBinding binding;

        EduDataViewHolder(ItemContainerEduDataBinding itemContainerEduDataBinding){
            super(itemContainerEduDataBinding.getRoot());
            binding = itemContainerEduDataBinding;
        }

        void setData(EduData eduData){
            Glide.with(binding.imageView.getContext())
                    .load(eduData.imageUrl)
                    .into(binding.imageView);
            binding.authorName.setText(eduData.name);
            binding.healthEducationTitle.setText(eduData.title);
            binding.getRoot().setOnClickListener(view -> healthEducationListener.onItemClicked(eduData));
        }
    }
}
