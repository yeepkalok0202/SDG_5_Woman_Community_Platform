package com.example.wia2007mad.AllModules.socialmarket;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;

import com.example.wia2007mad.R;
import com.example.wia2007mad.databinding.MarketingBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Marketing extends AppCompatActivity {

    List<MarketData> marketresourcelist = new ArrayList<>();
    private MarketingBinding binding;
    ImageButton btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketing);

        binding= MarketingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getMarketData();

        //searchview
        binding.marketsearchview.setOnQueryTextListener(new OnQueryTextListener() {
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

        //button from marketing page to home page
        binding.arrowleftmarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void getMarketData(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("marketing_resource")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            MarketData marketdata = new MarketData();
                            marketdata.name = document.getString("name");
                            marketdata.imageUrl = document.getString("image");
                            marketdata.course = document.getString("course");
                            marketdata.marketDesc = document.getString("market_desc");
                            marketdata.url = document.getString("url");
                            //get video link
                            marketdata.videoUrl = document.getString("videoUrl");
                            marketresourcelist.add(marketdata);
                        }
                        //adapter.setResources(marketresourcelist);

                        if(marketresourcelist.size()>0){
                            MarketAdapter marketAdapter = new MarketAdapter(marketresourcelist, new MarketingListener() {


                                public void onItemClicked(MarketData marketData) {

                                    Toast.makeText(getApplicationContext(),  " Selected", Toast.LENGTH_SHORT).show();
                                    /*old code that links to webpage
                                    Intent intent = new Intent(getApplicationContext(), webViewPage.class);
                                    intent.putExtra("url", marketData.url);
                                    startActivity(intent);
                                    */
                                }


                            });
                            binding.recyclerView.setAdapter(marketAdapter);
                        }
                    }

                });
    }

    //show list when searching
    private void filterData(String query) {
        List<MarketData> filteredList = new ArrayList<>();
        for (MarketData marketData : marketresourcelist) {
            if (marketData.course.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(marketData);
            }
        }

        // Update the RecyclerView with the filtered list
        MarketAdapter marketAdapter = new MarketAdapter(filteredList, new MarketingListener() {
            @Override
            public void onItemClicked(MarketData marketData) {
                //old
            }
        });
        binding.recyclerView.setAdapter(marketAdapter);
    }

}