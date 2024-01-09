package com.example.wia2007mad.AllModules;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wia2007mad.databinding.JobBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Job extends AppCompatActivity implements JobRecyclerViewInterface{

    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    ArrayList<JobModel> filteredlist,originallist=new ArrayList<>();
    JobAdapter jobAdapter;
    private JobBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = JobBinding.inflate(getLayoutInflater());

        Toolbar toolbar=binding.ToolbarJob;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // for the 'up' button
            // further customization goes here
        }

        // InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        // imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        binding.jobsearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        setupJobModel();
        //initialize adapter
        jobAdapter= new JobAdapter(this,originallist,this);
        jobAdapter.setFilteredList(originallist);
        filteredlist=originallist;

        //set adapter
        binding.jobrecyclerview.setAdapter(jobAdapter);
        binding.jobrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        binding.refreshjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobAdapter.setFilteredList(originallist);
                filteredlist=originallist;
                jobAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"Refreshing...", Toast.LENGTH_SHORT).show();
            }
        });
        setContentView(binding.getRoot());

    }
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
        for(JobModel x:originallist){
            if(x.getJobTitle().toLowerCase().contains(query.toLowerCase())){
                filteredlist.add(x);
            }
        }
        jobAdapter.setFilteredList(filteredlist);
    }

    private void filterList(String query) {
        filteredlist = new ArrayList<>();

        if (TextUtils.isEmpty(query)) {
            // If the query is empty, show all items
            filteredlist.addAll(originallist);
        } else {
            for (JobModel x : originallist) {
                if (x.getJobTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredlist.add(x);
                }
            }
        }

        jobAdapter.setFilteredList(filteredlist);
    }

    @Override
    public void onItemClick(int position){
        Intent intent=new Intent(Job.this,JobDetails.class);
        intent.putExtra("JobModel",filteredlist.get(position));
        startActivity(intent);
    }

    private void setupJobModel(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Set your loading message
        progressDialog.setCancelable(true); // Make it non-cancelable
        progressDialog.show();
        firebaseFirestore=FirebaseFirestore.getInstance();
        for(int i=1;i<=16;i++){
            final int finalI = i;  // Declare 'finalI' as a final copy of 'i'
            String documentID="joblist"+i;
            documentReference=firebaseFirestore.collection("Joblist").document(documentID);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        JobModel jobModel=documentSnapshot.toObject(JobModel.class);
                        if(jobModel!=null){
                            originallist.add(jobModel);
                        }
                    }
                    if (finalI== 16) {
                        updateRecyclerView();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }
            });
        }
    }

    private void updateRecyclerView() {
        jobAdapter.setFilteredList(originallist);
        jobAdapter.notifyDataSetChanged();

        String searchQuery = getIntent().getStringExtra("searchQuery");

        // Call the filterList method with the search query
        if (searchQuery != null && !searchQuery.isEmpty()) {
            filterList(searchQuery);
        }
    }
}