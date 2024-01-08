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

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillViewHolder> {

    Context context;
    ArrayList<SkillModel> skillModelArrayList;
    private final SkillRecyclerViewInterface skillRecyclerViewInterface;

    public SkillAdapter(Context context, ArrayList<SkillModel> skillModelArrayList, SkillRecyclerViewInterface skillRecyclerViewInterface) {
        this.context = context;
        this.skillModelArrayList = skillModelArrayList;
        this.skillRecyclerViewInterface = skillRecyclerViewInterface;
    }

    public void setFilteredList(ArrayList<SkillModel> filteredList){
        this.skillModelArrayList=filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.skill_individualelement,parent,false);
        return new SkillViewHolder(view, skillRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        holder.virtualtitle.setText(skillModelArrayList.get(position).getTitle());
        holder.virtualauthor.setText(skillModelArrayList.get(position).getAuthor());
        Glide.with(holder.itemView.getContext()).load(skillModelArrayList.get(position).getThumbnailURL()).into(holder.virtualImage);
    }

    @Override
    public int getItemCount() {
        return skillModelArrayList.size();
    }

    class SkillViewHolder extends RecyclerView.ViewHolder {
        TextView virtualtitle,virtualauthor;
        ImageView virtualImage;

        public SkillViewHolder(@NonNull View itemView, SkillRecyclerViewInterface skillRecyclerViewInterface) {
            super(itemView);

            virtualauthor=itemView.findViewById(R.id.skillauthor);
            virtualtitle=itemView.findViewById(R.id.skilltitle);
            virtualImage=itemView.findViewById(R.id.skillimage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(skillRecyclerViewInterface!=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            skillRecyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
