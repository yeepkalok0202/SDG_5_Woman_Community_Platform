package com.example.wia2007mad.AllModules.socialmarket;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wia2007mad.databinding.BusinessListBinding;

import java.util.List;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder> {

    private final List<BusinessData> businessDataList;
    private final BusinessListener businessListener;

    public BusinessAdapter(List<BusinessData> businessDataList, BusinessListener businessListener) {
        this.businessDataList = businessDataList;
        this.businessListener = businessListener;
    }

    @NonNull
    @Override
    public BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BusinessListBinding businessListBinding = BusinessListBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new BusinessViewHolder(businessListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessViewHolder holder, int position) {
        holder.setData(businessDataList.get(position));

        //new code directing to carddetails and update the data
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusinessData businessData = businessDataList.get(position);
                Intent intent = new Intent(view.getContext(), CardDetailsActivity.class);
                intent.putExtra("course", businessData.course);
                intent.putExtra("name", businessData.name);
                intent.putExtra("description", businessData.businessDesc);
                intent.putExtra("imageUrl", businessData.imageUrl);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return businessDataList.size();
    }

    class BusinessViewHolder extends RecyclerView.ViewHolder {
        BusinessListBinding binding;

        BusinessViewHolder(BusinessListBinding businessListBinding) {
            super(businessListBinding.getRoot());
            binding = businessListBinding;
        }

        void setData(BusinessData businessData) {
            Glide.with(binding.businessimageView.getContext())
                    .load(businessData.imageUrl)
                    .into(binding.businessimageView);
            binding.businessname.setText(businessData.name);
            binding.businesscourse.setText(businessData.course);
            binding.businessDesc.setText(businessData.businessDesc);
            binding.getRoot().setOnClickListener(view -> businessListener.onItemClicked(businessData));
        }
    }
}
