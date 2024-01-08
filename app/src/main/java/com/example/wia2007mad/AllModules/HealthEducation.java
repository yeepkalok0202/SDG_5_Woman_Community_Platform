package com.example.wia2007mad.AllModules;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;

import com.example.wia2007mad.AllModules.adapter.HealthEduAdapter;
import com.example.wia2007mad.AllModules.listeners.HealthEducationListener;
import com.example.wia2007mad.AllModules.model.EduData;
import com.example.wia2007mad.AllModules.utilities.Constants;
import com.example.wia2007mad.R;
import com.example.wia2007mad.databinding.ActivityHealthEducationBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HealthEducation extends AppCompatActivity {

    private ActivityHealthEducationBinding binding;
    List<EduData> eduDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHealthEducationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setListeners();
        getEduData();

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return true;
            }
        });
    }

    private void setListeners(){
        binding.BtnBackEdu.setOnClickListener(view -> onBackPressed());
    }

    private void getEduData(){

        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection(Constants.KEY_COLLECTION_HEALTH_EDUCATION)
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    if(task.isSuccessful() && task.getResult() != null){
                        System.out.println("Successful");
                        for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                            EduData eduData = new EduData();
                            eduData.imageUrl = queryDocumentSnapshot.getString("image");
                            eduData.name = queryDocumentSnapshot.getString("author");
                            eduData.title = queryDocumentSnapshot.getString("title");
                            eduData.url = queryDocumentSnapshot.getString("url");
                            eduDataList.add(eduData);
                        }

                        if(eduDataList.size()>0){
                            HealthEduAdapter healthEduAdapter = new HealthEduAdapter(eduDataList, new HealthEducationListener() {
                                @Override
                                public void onItemClicked(EduData eduData) {
                                    // Handle item click here
                                    Intent intent = new Intent(getApplicationContext(), webViewPage.class);
                                    intent.putExtra("url", eduData.url);
                                    startActivity(intent);
                                }
                            });
                            binding.EducationRecyclerView.setAdapter(healthEduAdapter);
                            binding.EducationRecyclerView.setVisibility(View.VISIBLE);
                        }else{
                            showErrorMessage();
                        }
                    }
                });
    }

    private void filterData(String query) {
        List<EduData> filteredList = new ArrayList<>();
        for (EduData eduData : eduDataList) {
            if (eduData.title.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(eduData);
            }
        }

        // Update the RecyclerView with the filtered list
        HealthEduAdapter healthEduAdapter = new HealthEduAdapter(filteredList, new HealthEducationListener() {
            @Override
            public void onItemClicked(EduData eduData) {
                // Handle item click here
                Intent intent = new Intent(getApplicationContext(), webViewPage.class);
                intent.putExtra("url", eduData.url);
                startActivity(intent);
            }
        });
        binding.EducationRecyclerView.setAdapter(healthEduAdapter);
    }


    private void showErrorMessage(){
        binding.textErrorMessage.setText(String.format("%s", "No data available"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}