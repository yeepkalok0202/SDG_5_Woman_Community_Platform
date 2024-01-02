package com.example.wia2007mad.AllModules.socialmarket;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wia2007mad.databinding.MarketingListBinding;

import java.util.List;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MarketViewHolder> {

    private final List<MarketData>marketDataList;
    private final MarketingListener marketingListener;

    public MarketAdapter(List<MarketData>marketDataList, MarketingListener marketingListener){
        this.marketDataList = marketDataList;
        this.marketingListener = marketingListener;
    }

    @NonNull
    @Override
    public MarketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MarketingListBinding marketingListBinding = MarketingListBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MarketViewHolder(marketingListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketViewHolder holder, int position) {
        holder.setData(marketDataList.get(position));

        //to go to the carddetails and update the data
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarketData marketData = marketDataList.get(position);
                Intent intent = new Intent(view.getContext(), CardDetailsActivity.class);
                intent.putExtra("course", marketData.course);
                intent.putExtra("name", marketData.name);
                intent.putExtra("description", marketData.marketDesc);
                intent.putExtra("imageUrl", marketData.imageUrl);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return marketDataList.size();
    }

    class MarketViewHolder extends RecyclerView.ViewHolder{
        MarketingListBinding binding;

        MarketViewHolder(MarketingListBinding marketingListBinding){
            super(marketingListBinding.getRoot());
            binding = marketingListBinding;
        }

        void setData(MarketData marketData){
            Glide.with(binding.marketingcardviewimage.getContext())
                    .load(marketData.imageUrl)
                    .into(binding.marketingcardviewimage);
            binding.marketingcardviewcourse.setText(marketData.course);
            binding.marketingcardviewname.setText(marketData.name);
            binding.marketDesc.setText(marketData.marketDesc);
            binding.getRoot().setOnClickListener(view -> marketingListener.onItemClicked(marketData));
        }
    }

}
