package com.example.wia2007mad.AllModules;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wia2007mad.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResourceSharingHubPostAdapter extends RecyclerView.Adapter<ResourceSharingHubPostAdapter.ResourceSharingHubViewHolder> {
    Context context;
    ArrayList<HubPostModel> hubPostModelArrayList;
    String myuid;
    private DatabaseReference likeref, postref;
    boolean mprocesslike = false;
    public ResourceSharingHubPostAdapter(Context context, ArrayList<HubPostModel> hubPostModelArrayList) {
        this.context = context;
        this.hubPostModelArrayList = hubPostModelArrayList;
        myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        likeref = FirebaseDatabase.getInstance().getReference().child("Likes");
        postref = FirebaseDatabase.getInstance().getReference("Posts");
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
        holder.like.setText(hubPostModelArrayList.get(position).getPlike() + " Likes");
        holder.comment.setText(hubPostModelArrayList.get(position).getPcomment() + " Comments");
        setLikes(holder, hubPostModelArrayList.get(position).getPtime());

        holder.likebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mprocesslike = true;
                final int plike = Integer.parseInt(hubPostModelArrayList.get(position).getPlike());
                final String postid = hubPostModelArrayList.get(position).getPtime();
                //post id is genreated with ptime name
                likeref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (mprocesslike) {
                            if (dataSnapshot.child(postid).hasChild(myuid)) {
                                postref.child(postid).child("plike").setValue("" + (plike - 1));
                                likeref.child(postid).child(myuid).removeValue();
                                mprocesslike = false;
                            } else {
                                postref.child(postid).child("plike").setValue("" + (plike + 1));
                                likeref.child(postid).child(myuid).setValue("Liked");
                                mprocesslike = false;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        holder.commentbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetailsActivity.class);
                intent.putExtra("pid", hubPostModelArrayList.get(position).getPtime());
                context.startActivity(intent);
            }
        });

    }
    private void setLikes(final ResourceSharingHubViewHolder holder, final String pid) {
        likeref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(pid).hasChild(myuid)) {
                    holder.likebt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.afterlike, 0, 0, 0);
                    holder.likebt.setText("Liked");
                } else {
                    holder.likebt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.beforelike, 0, 0, 0);
                    holder.likebt.setText("Like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public int getItemCount() {
        return hubPostModelArrayList.size();
    }

    class ResourceSharingHubViewHolder extends RecyclerView.ViewHolder{
        //picturetv=profilepic
        //pimagetv=image uploaded
        TextView unametv,utimetv,ptitletv,descript,like,comment;
        Button likebt,commentbt;
        ImageView picturetv,pimagetv;
        public ResourceSharingHubViewHolder(@NonNull View itemView) {
            super(itemView);

            unametv=itemView.findViewById(R.id.unametv);
            utimetv=itemView.findViewById(R.id.utimetv);
            ptitletv=itemView.findViewById(R.id.ptitletv);
            descript=itemView.findViewById(R.id.descript);
            picturetv=itemView.findViewById(R.id.picturetv);
            pimagetv=itemView.findViewById(R.id.pimagetv);
            like=itemView.findViewById(R.id.plikeb);
            comment=itemView.findViewById(R.id.pcommentco);
            likebt=itemView.findViewById(R.id.like);
            commentbt=itemView.findViewById(R.id.comment);
        }
    }
}
