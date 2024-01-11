package com.example.wia2007mad.AllModules;

import android.content.Context;
import android.text.format.DateFormat;
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
import java.util.Calendar;
import java.util.Locale;

public class Comment_Adapter extends RecyclerView.Adapter<Comment_Adapter.CommentHolder> {

    Context context;
    ArrayList<CommentModel> list;
    String myuid;
    String postid;

    public Comment_Adapter(Context context, ArrayList<CommentModel> list, String myuid, String postid) {
        this.context = context;
        this.list = list;
        this.myuid = myuid;
        this.postid = postid;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_details, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        final String uid = list.get(position).getUid();
        String name = list.get(position).getUname();
        String email = list.get(position).getUemail();
        String image = list.get(position).getUdp();
        final String cid = list.get(position).getcId();
        String comment = list.get(position).getComment();
        String timestamp = list.get(position).getPtime();
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(timestamp));
        String timedate = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

        holder.name.setText(name);
        holder.time.setText(timedate);
        holder.comment.setText(comment);
        Glide.with(context).load(image).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, comment, time;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.loadcomment);
            name = itemView.findViewById(R.id.commentname);
            comment = itemView.findViewById(R.id.commenttext);
            time = itemView.findViewById(R.id.commenttime);
        }
    }
}
