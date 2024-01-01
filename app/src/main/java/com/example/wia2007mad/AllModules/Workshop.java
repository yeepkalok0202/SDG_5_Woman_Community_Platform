package com.example.wia2007mad.AllModules;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wia2007mad.databinding.WorkshopBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Workshop extends AppCompatActivity implements WorkshopRecyclerViewInterface {

    private WorkshopBinding binding;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    ArrayList<WorkshopModel> filteredlist,originallist=new ArrayList<>();
    WorkshopAdapter workshopAdapter;
    private ProgressDialog progressDialog;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=WorkshopBinding.inflate(getLayoutInflater());

        Toolbar toolbar=binding.ToolbarWorkshop;
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // for the 'up' button
            // further customization goes here
        }
        binding.workshopsearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        //input
        setupWorkshopModel();
        //initialize adapter
        workshopAdapter=new WorkshopAdapter(this,originallist,this);
        workshopAdapter.setFilteredList(originallist);
        filteredlist=originallist;

        //set adapter
        binding.workshoprecyclerview.setAdapter(workshopAdapter);
        binding.workshoprecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.refreshworkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workshopAdapter.setFilteredList(originallist);
                filteredlist=originallist;
                workshopAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"Refreshing...", Toast.LENGTH_SHORT).show();
            }
        });

        setContentView(binding.getRoot());

    }
    //input

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks
        switch (item.getItemId()) {
            case android.R.id.home:
                // User clicked 'Up' button, so close this activity and return to parent
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void submitList(String query){
        filteredlist=new ArrayList<>();
        for(WorkshopModel x:originallist){
            if(x.getTitle().toLowerCase().contains(query.toLowerCase())||x.getAuthor().toLowerCase().contains(query.toLowerCase())||x.getDescription().toLowerCase().contains(query.toLowerCase())){
                filteredlist.add(x);
            }
        }
        workshopAdapter.setFilteredList(filteredlist);
    }

    private void filterList(String query){
        filteredlist=new ArrayList<>();
        if (TextUtils.isEmpty(query)) {
            // If the query is empty, show all items
            filteredlist.addAll(originallist);
        } else {
            for (WorkshopModel x : originallist) {
                if(x.getTitle().toLowerCase().contains(query.toLowerCase())||x.getAuthor().toLowerCase().contains(query.toLowerCase())||x.getDescription().toLowerCase().contains(query.toLowerCase())){
                    filteredlist.add(x);
                }
            }
        }

        workshopAdapter.setFilteredList(filteredlist);
    }


    public void onItemClick(int position){
        Intent intent=new Intent(Workshop.this, WorkshopDetails.class);
        intent.putExtra("WorkshopModel",filteredlist.get(position));
        startActivity(intent);
    }

    private void setupWorkshopModel(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Set your loading message
        progressDialog.setCancelable(true); // Make it non-cancelable
        progressDialog.show();
        firebaseFirestore=FirebaseFirestore.getInstance();
        for(int i=1;i<=15;i++){
            final int finalI = i;
            String documentID="virtual"+i;
            documentReference=firebaseFirestore.collection("VirtualWorkshopAndWebinar").document(documentID);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        WorkshopModel workshopModel=documentSnapshot.toObject(WorkshopModel.class);
                        if(workshopModel!=null){
                            originallist.add(workshopModel);
                        }
                    }
                    if(finalI==15){
                        updateRecyclerView();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                }
            });
        }
    }

    private void updateRecyclerView(){
        workshopAdapter.setFilteredList(originallist);
        workshopAdapter.notifyDataSetChanged();

        String searchQuery = getIntent().getStringExtra("searchQuery");

        // Call the filterList method with the search query
        if (searchQuery != null && !searchQuery.isEmpty()) {
            filterList(searchQuery);
        }
    }

}
