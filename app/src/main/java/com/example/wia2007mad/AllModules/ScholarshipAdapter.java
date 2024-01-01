package com.example.wia2007mad.AllModules;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wia2007mad.R;

import java.util.ArrayList;

public class ScholarshipAdapter extends RecyclerView.Adapter<ScholarshipAdapter.ScholarshipViewHolder> {

    Context context;
    ArrayList<ScholarshipModel> scholarshipModelArrayList;
    private final ScholarshipRecyclerViewInterface scholarshipRecyclerViewInterface;

    public ScholarshipAdapter(Context context, ArrayList<ScholarshipModel> scholarshipModelArrayList, ScholarshipRecyclerViewInterface scholarshipRecyclerViewInterface) {
        this.context = context;
        this.scholarshipModelArrayList = scholarshipModelArrayList;
        this.scholarshipRecyclerViewInterface = scholarshipRecyclerViewInterface;
    }

    public void setFilteredList(ArrayList<ScholarshipModel> filteredList) {
        this.scholarshipModelArrayList = filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ScholarshipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.scholarship_grant_individualelement, parent, false);
        return new ScholarshipViewHolder(view, scholarshipRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ScholarshipViewHolder holder, int position) {
        holder.scholarshipcontent.setText(scholarshipModelArrayList.get(position).getScholarshipDescription());
        holder.scholarshipdeadline.setText(scholarshipModelArrayList.get(position).getScholarshipDeadline());
        holder.scholarshiptitle.setText(scholarshipModelArrayList.get(position).getScholarshipTitle());
    }

    @Override
    public int getItemCount() {
        return scholarshipModelArrayList.size();
    }
    class ScholarshipViewHolder extends RecyclerView.ViewHolder{

        TextView scholarshiptitle,scholarshipdeadline,scholarshipcontent;

        public ScholarshipViewHolder(@NonNull View itemView, ScholarshipRecyclerViewInterface scholarshipRecyclerViewInterface) {
            super(itemView);

            scholarshiptitle=itemView.findViewById(R.id.scholarshiptitle);
            scholarshipdeadline=itemView.findViewById(R.id.scholarshipdeadline);
            scholarshipcontent=itemView.findViewById(R.id.scholarshipcontent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(scholarshipRecyclerViewInterface !=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            scholarshipRecyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });


        }
    }
}


