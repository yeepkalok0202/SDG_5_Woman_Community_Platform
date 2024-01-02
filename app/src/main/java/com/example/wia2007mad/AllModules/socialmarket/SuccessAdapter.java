package com.example.wia2007mad.AllModules.socialmarket;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wia2007mad.R;

import java.util.ArrayList;

public class SuccessAdapter extends RecyclerView.Adapter<SuccessAdapter.ViewHolder> {
    private ArrayList<SuccessData> successStories = new ArrayList<>();

    private Context context;

    public SuccessAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.success_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //set text
        holder.successTxt.setText(successStories.get(position).getSuccesstext());
        holder.parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //bind data with intent
                SuccessData successData = successStories.get(position);
                Intent intent = new Intent(v.getContext(), SuccessDetailsActivity.class);
                intent.putExtra("name", successData.getName());
                intent.putExtra("title", successData.getSuccesstext());
                intent.putExtra("story_desc", successData.getStoryDesc());
                intent.putExtra("imageUrl", successData.getImageUrl());
                v.getContext().startActivity(intent);

            }
        });

        //code for image
        Glide.with(context)
                .load(successStories.get(position).getImageUrl())
                .into(holder.successImage);

    }

    @Override
    public int getItemCount() {
        return successStories.size();
    }

    public void setBlogs(ArrayList<SuccessData> successStories) {
        this.successStories = successStories;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView successTxt;
        private CardView parent;
        private ImageView successImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            successTxt = itemView.findViewById(R.id.successblogtext);
            parent = itemView.findViewById(R.id.parent);
            successImage = itemView.findViewById(R.id.successblogimage);
        }
    }
}
