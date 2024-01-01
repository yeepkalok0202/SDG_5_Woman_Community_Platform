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

public class WorkshopAdapter extends RecyclerView.Adapter<WorkshopAdapter.WorkshopViewHolder> {

    Context context;
    ArrayList<WorkshopModel> workshopModelArrayList;
    private final WorkshopRecyclerViewInterface workshopRecyclerViewInterface;

    public WorkshopAdapter(Context context, ArrayList<WorkshopModel> workshopModelArrayList, WorkshopRecyclerViewInterface workshopRecyclerViewInterface) {
        this.context = context;
        this.workshopModelArrayList = workshopModelArrayList;
        this.workshopRecyclerViewInterface = workshopRecyclerViewInterface;
    }

    public void setFilteredList(ArrayList<WorkshopModel> filteredList){
        this.workshopModelArrayList=filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public WorkshopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.workshop_individualelement,parent,false);
        return new WorkshopViewHolder(view, workshopRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkshopViewHolder holder, int position) {
        holder.virtualtitle.setText(workshopModelArrayList.get(position).getTitle());
        holder.virtualauthor.setText(workshopModelArrayList.get(position).getAuthor());
        Glide.with(holder.itemView.getContext()).load(workshopModelArrayList.get(position).getThumbnailURL()).into(holder.virtualImage);
    }

    @Override
    public int getItemCount() {
        return workshopModelArrayList.size();
    }

    class WorkshopViewHolder extends RecyclerView.ViewHolder {
        TextView virtualtitle,virtualauthor;
        ImageView virtualImage;

        public WorkshopViewHolder(@NonNull View itemView, WorkshopRecyclerViewInterface workshopRecyclerViewInterface) {
            super(itemView);

            virtualauthor=itemView.findViewById(R.id.workshopauthor);
            virtualtitle=itemView.findViewById(R.id.workshoptitle);
            virtualImage=itemView.findViewById(R.id.workshopimage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(workshopRecyclerViewInterface!=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            workshopRecyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}

