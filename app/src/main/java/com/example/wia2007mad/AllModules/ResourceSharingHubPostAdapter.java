package com.example.wia2007mad.AllModules;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wia2007mad.R;

import java.util.ArrayList;

public class ResourceSharingHubPostAdapter extends RecyclerView.Adapter<ResourceSharingHubPostAdapter.ResourceSharingHubViewHolder> {
    Context context;
    ArrayList<HubPostModel> hubPostModelArrayList;

    public ResourceSharingHubPostAdapter(Context context, ArrayList<HubPostModel> hubPostModelArrayList) {
        this.context = context;
        this.hubPostModelArrayList = hubPostModelArrayList;
    }
    public void setFilteredList(ArrayList<HubPostModel> filteredList){
        this.hubPostModelArrayList=filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ResourceSharingHubPostAdapter.ResourceSharingHubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.resource_hub_individual_post_element,parent,false);
        return new ResourceSharingHubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceSharingHubPostAdapter.ResourceSharingHubViewHolder holder, int position) {
        //picturetv=profilepic
        //pimagetv=image uploaded
        Glide.with(holder.itemView.getContext()).load(hubPostModelArrayList.get(position).getUdp()).into(holder.picturetv);
        Glide.with(holder.itemView.getContext()).load(hubPostModelArrayList.get(position).getUimage()).into(holder.pimagetv);
        holder.unametv.setText(hubPostModelArrayList.get(position).getUname());
        holder.utimetv.setText(hubPostModelArrayList.get(position).getPtime());
        holder.ptitletv.setText(hubPostModelArrayList.get(position).getTitle());
        holder.descript.setText(hubPostModelArrayList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return hubPostModelArrayList.size();
    }

    class ResourceSharingHubViewHolder extends RecyclerView.ViewHolder{
        //picturetv=profilepic
        //pimagetv=image uploaded
        TextView unametv,utimetv,ptitletv,descript;
        ImageView picturetv,pimagetv;
        public ResourceSharingHubViewHolder(@NonNull View itemView) {
            super(itemView);

            unametv=itemView.findViewById(R.id.unametv);
            utimetv=itemView.findViewById(R.id.utimetv);
            ptitletv=itemView.findViewById(R.id.ptitletv);
            descript=itemView.findViewById(R.id.descript);
            picturetv=itemView.findViewById(R.id.picturetv);
            pimagetv=itemView.findViewById(R.id.pimagetv);
        }
    }
}
