package com.example.wia2007mad.AllModules;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.wia2007mad.databinding.SkillBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Skill extends AppCompatActivity implements SkillRecyclerViewInterface {

    private SkillBinding binding;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    ArrayList<SkillModel> filteredlist,originallist=new ArrayList<>();
    SkillAdapter skillAdapter;
    private ProgressDialog progressDialog;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=SkillBinding.inflate(getLayoutInflater());

        Toolbar toolbar=binding.ToolbarSkill;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // for the 'up' button
            // further customization goes here
        }
        binding.skillsearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        setupSkillModel();
        //initialize adapter
        skillAdapter=new SkillAdapter(this,originallist,this);
        skillAdapter.setFilteredList(originallist);
        filteredlist=originallist;

        //set adapter
        binding.skillrecyclerview.setAdapter(skillAdapter);
        binding.skillrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.refreshskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillAdapter.setFilteredList(originallist);
                filteredlist=originallist;
                skillAdapter.notifyDataSetChanged();
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
        for(SkillModel x:originallist){
            if(x.getTitle().toLowerCase().contains(query.toLowerCase())||x.getAuthor().toLowerCase().contains(query.toLowerCase())||x.getDescription().toLowerCase().contains(query.toLowerCase())){
                filteredlist.add(x);
            }
        }
        skillAdapter.setFilteredList(filteredlist);
    }

    private void filterList(String query){
        filteredlist=new ArrayList<>();
        if (TextUtils.isEmpty(query)) {
            // If the query is empty, show all items
            filteredlist.addAll(originallist);
        } else {
            for (SkillModel x : originallist) {
                if(x.getTitle().toLowerCase().contains(query.toLowerCase())||x.getAuthor().toLowerCase().contains(query.toLowerCase())||x.getDescription().toLowerCase().contains(query.toLowerCase())){
                    filteredlist.add(x);
                }
            }
        }

        skillAdapter.setFilteredList(filteredlist);
    }


    public void onItemClick(int position){
        Intent intent=new Intent(Skill.this, SkillDetails.class);
        intent.putExtra("SkillModel",filteredlist.get(position));
        startActivity(intent);
    }

    private void setupSkillModel(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Set your loading message
        progressDialog.setCancelable(true); // Make it non-cancelable
        progressDialog.show();
        firebaseFirestore=FirebaseFirestore.getInstance();
        for(int i=1;i<=4;i++){
            final int finalI = i;
            String documentID="skillbuildingworkshop"+i;
            documentReference=firebaseFirestore.collection("SkillBuildingWorkshop").document(documentID);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        SkillModel skillModel=documentSnapshot.toObject(SkillModel.class);
                        if(skillModel!=null){
                            originallist.add(skillModel);
                        }
                    }
                    if(finalI==4){
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
        skillAdapter.setFilteredList(originallist);
        skillAdapter.notifyDataSetChanged();

        String searchQuery = getIntent().getStringExtra("searchQuery");

        // Call the filterList method with the search query
        if (searchQuery != null && !searchQuery.isEmpty()) {
            filterList(searchQuery);
        }
    }

}