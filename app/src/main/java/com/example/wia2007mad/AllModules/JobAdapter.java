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

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    Context context;
    ArrayList<JobModel> jobModelArrayList;
    private final JobRecyclerViewInterface jobRecyclerViewInterface;

    public JobAdapter(Context context, ArrayList<JobModel> jobModelArrayList, JobRecyclerViewInterface jobRecyclerViewInterface) {
        this.context = context;
        this.jobModelArrayList = jobModelArrayList;
        this.jobRecyclerViewInterface = jobRecyclerViewInterface;
    }

    public void setFilteredList(ArrayList<JobModel> filteredList) {
        this.jobModelArrayList = filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.job_individualelement, parent, false);
        return new JobViewHolder(view, jobRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        holder.jobcontent.setText(jobModelArrayList.get(position).getJobDescription());
        holder.jobdeadline.setText(jobModelArrayList.get(position).getJobDeadline());
        holder.jobtitle.setText(jobModelArrayList.get(position).getJobTitle());
    }

    @Override
    public int getItemCount() {
        return jobModelArrayList.size();
    }
    class JobViewHolder extends RecyclerView.ViewHolder{

        TextView jobtitle,jobdeadline,jobcontent;

        public JobViewHolder(@NonNull View itemView, JobRecyclerViewInterface jobRecyclerViewInterface) {
            super(itemView);

            jobtitle=itemView.findViewById(R.id.jobtitle);
            jobdeadline=itemView.findViewById(R.id.jobdeadline);
            jobcontent=itemView.findViewById(R.id.jobcontent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(jobRecyclerViewInterface !=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            jobRecyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });


        }
    }
}