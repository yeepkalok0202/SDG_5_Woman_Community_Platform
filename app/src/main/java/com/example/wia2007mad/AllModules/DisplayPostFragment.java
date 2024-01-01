package com.example.wia2007mad.AllModules;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wia2007mad.R;
import com.example.wia2007mad.databinding.FragmentDisplayPostBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;


public class DisplayPostFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<HubPostModel> filteredlist,originallist=new ArrayList<>();
    ResourceSharingHubPostAdapter resourceSharingHubPostAdapter;
    private ProgressDialog progressDialog;

    FragmentDisplayPostBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentDisplayPostBinding.inflate(inflater,container,false);

        binding.resourcehubsearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                submitList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        //input and setup data
        setupResourcePostModel();


        //adapter matters
        resourceSharingHubPostAdapter= new ResourceSharingHubPostAdapter(getContext(),originallist);
        resourceSharingHubPostAdapter.setFilteredList(originallist);
        filteredlist=originallist;

        //set adapter
        binding.resourcehubrecyclerview.setAdapter(resourceSharingHubPostAdapter);
        binding.resourcehubrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.refreshresourcehubpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resourceSharingHubPostAdapter.setFilteredList(originallist);
                filteredlist=originallist;
                Collections.sort(filteredlist,HubPostModel.descendingnameComparator);
                resourceSharingHubPostAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"Refreshing...", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }

    private void submitList(String query){
        filteredlist=new ArrayList<>();
        for(HubPostModel x:originallist){
            if(x.getTitle().toLowerCase().contains(query.toLowerCase())||x.getDescription().toLowerCase().contains(query.toLowerCase())||x.getUname().toLowerCase().contains(query.toLowerCase())){
                filteredlist.add(x);
            }
        }
        Collections.sort(filteredlist,HubPostModel.descendingnameComparator);
        resourceSharingHubPostAdapter.setFilteredList(filteredlist);
    }

    private void filterList(String query) {
        filteredlist = new ArrayList<>();

        if (TextUtils.isEmpty(query)) {
            // If the query is empty, show all items
            filteredlist.addAll(originallist);
        } else {
            for (HubPostModel x : originallist) {
                if(x.getTitle().toLowerCase().contains(query.toLowerCase())||x.getDescription().toLowerCase().contains(query.toLowerCase())||x.getUname().toLowerCase().contains(query.toLowerCase())){
                    filteredlist.add(x);
                }
            }
        }
        Collections.sort(filteredlist,HubPostModel.descendingnameComparator);
        resourceSharingHubPostAdapter.setFilteredList(filteredlist);
    }

    private void updateRecyclerView() {
        resourceSharingHubPostAdapter.setFilteredList(originallist);
        Collections.sort(originallist,HubPostModel.descendingnameComparator);
        resourceSharingHubPostAdapter.notifyDataSetChanged();

    }

    private void setupResourcePostModel(){

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading..."); // Set your loading message
        progressDialog.setCancelable(true); // Make it non-cancelable
        progressDialog.show();
        firebaseDatabase=FirebaseDatabase.getInstance("https://authenticationmodule-bebd2-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference=firebaseDatabase.getReference("Posts");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    originallist.clear();
                    for(DataSnapshot dataSnapshot1:snapshot.getChildren()){
                        HubPostModel postdata=dataSnapshot1.getValue(HubPostModel.class);
                        if(postdata!=null){
                            originallist.add(postdata);
                        }
                    }
                    updateRecyclerView(); // Call updateRecyclerView here
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

    }
}