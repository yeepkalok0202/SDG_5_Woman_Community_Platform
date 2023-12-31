package com.example.wia2007mad.AllModules;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wia2007mad.databinding.ScholarshipGrantBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ScholarshipGrant extends AppCompatActivity implements ScholarshipRecyclerViewInterface{

    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    ArrayList<ScholarshipModel> filteredlist,originallist=new ArrayList<>();
    ScholarshipAdapter scholarshipAdapter;
    private ScholarshipGrantBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ScholarshipGrantBinding.inflate(getLayoutInflater());

        Toolbar toolbar=binding.ToolbarScholarship;
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // for the 'up' button
            // further customization goes here
        }
        //binding.scholarshipsearchview.requestFocus();

        // Show the keyboard
       // InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
       // imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        binding.scholarshipsearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        setupScholarshipModel();
        //initialize adapter
        scholarshipAdapter= new ScholarshipAdapter(this,originallist,this);
        scholarshipAdapter.setFilteredList(originallist);
        filteredlist=originallist;

        //set adapter
        binding.scholarshiprecyclerview.setAdapter(scholarshipAdapter);
        binding.scholarshiprecyclerview.setLayoutManager(new LinearLayoutManager(this));
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
        for(ScholarshipModel x:originallist){
            if(x.getScholarshipTitle().toLowerCase().contains(query.toLowerCase())){
                filteredlist.add(x);
            }
        }
        scholarshipAdapter.setFilteredList(filteredlist);
    }

    private void filterList(String query) {
        filteredlist = new ArrayList<>();

        if (TextUtils.isEmpty(query)) {
            // If the query is empty, show all items
            filteredlist.addAll(originallist);
        } else {
            for (ScholarshipModel x : originallist) {
                if (x.getScholarshipTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredlist.add(x);
                }
            }
        }

        scholarshipAdapter.setFilteredList(filteredlist);
    }

    @Override
    public void onItemClick(int position){
        Intent intent=new Intent(ScholarshipGrant.this,ScholarshipDetails.class);
        intent.putExtra("ScholarshipModel",filteredlist.get(position));
        startActivity(intent);
    }

    private void setupScholarshipModel(){
        firebaseFirestore=FirebaseFirestore.getInstance();
        for(int i=1;i<=15;i++){
            final int finalI = i;  // Declare 'finalI' as a final copy of 'i'
            String documentID="scholarship"+i;
            documentReference=firebaseFirestore.collection("scholarshipdetails").document(documentID);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        ScholarshipModel scholarshipModel=documentSnapshot.toObject(ScholarshipModel.class);
                        if(scholarshipModel!=null){
                            originallist.add(scholarshipModel);
                        }
                    }
                    if (finalI== 15) {
                        updateRecyclerView();
                    }
                }
            });
        }
    }

    private void updateRecyclerView() {
        scholarshipAdapter.setFilteredList(originallist);
        scholarshipAdapter.notifyDataSetChanged();
    }
}